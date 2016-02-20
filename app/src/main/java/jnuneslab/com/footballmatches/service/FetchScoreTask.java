package jnuneslab.com.footballmatches.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.data.MatchesContract;
import jnuneslab.com.footballmatches.util.Util;


/**
 * FetchScoreTask class responsible to get all the data of all matches filtered by the selected user leagues and return the data formatted into a vector
 */
public class FetchScoreTask extends AsyncTask<Void, Void, Void> {

    // Log Tag
    public static final String LOG_TAG = FetchScoreTask.class.getSimpleName();

    // Broadcast sent when matches fetch is done
    public static final String BROADCAST_MATCHES_UPDATED = "jnuneslab.com.footballmatches.BROADCAST_MATCHES_UPDATED";
    private final Context mContext;

    /**
     * Constructor
     *
     * @param context Application context
     */
    public FetchScoreTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        // Parameter to get the matches that will be played in the current day and in the next two days
        getData("n3");

        // Parameter to get the previous matches of day and the two days before
        getData("p3");

        return null;
    }


    /**
     * Method responsible for getting the all data related with matches of previous and next matches
     *
     * @param timeFrame - String parameter (n<number> for future matches and p<number> for previous matches)
     */
    private void getData(String timeFrame) {

        // Creating fetch BASE URL
        final String BASE_URL = "http://api.football-data.org/alpha/fixtures";

        // Time Frame parameter to determine days
        final String QUERY_TIME_FRAME = "timeFrame";

        Uri fetch_build = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(QUERY_TIME_FRAME, timeFrame).build();

        // Log.v(LOG_TAG, "API Get URL: " + fetch_build.toString()); //log spam

        HttpURLConnection httpConnection = null;
        BufferedReader reader = null;
        String jsonData = null;


        try {

            // Make the request to the API, for this case is necessary to put the api key inside a X-Auth-Token property. Otherwise the API will block after 10 attempts
            URL fetch = new URL(fetch_build.toString());
            httpConnection = (HttpURLConnection) fetch.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.addRequestProperty("X-Auth-Token", mContext.getString(R.string.api_key));
            httpConnection.connect();

            // Get the input stream
            InputStream inputStream = httpConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            // If the response was empty, return because there is nothing to do
            if (inputStream == null) {
                return;
            }

            // Read the input stream into a buffer that will be the jsonData
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            // Return in case of empty stream
            if (buffer.length() == 0) {
                return;
            }

            jsonData = buffer.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception fetching data" + e.getMessage());
        } finally {

            // Close the connection
            if (httpConnection != null) {
                httpConnection.disconnect();
            }

            // Close the stream
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error Closing Stream");
                }
            }
        }

        try {
            if (jsonData != null) {

                // Check if the data contains any matches. If not, call processJson with the dummy data
                JSONArray matches = new JSONObject(jsonData).getJSONArray("fixtures");
                if (matches.length() == 0) {
                    // If there is no data, call the function on dummy data. This is the expected behavior during the off season.
                    // processJSONdata(mContext.getString(R.string.dummy_data), mContext.getApplicationContext(), false);
                    return;
                }

                // Start to parser the data
                Log.d(LOG_TAG, "JSON data." + jsonData);
                processJSONdata(jsonData, mContext, true);
            } else {

                // Could not Connect
                Log.d(LOG_TAG, "Could not connect to server.");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    private void processJSONdata(String JSONdata, Context mContext, boolean isReal) {

        // Get all the leagues available
        Map<Integer, String> allLeagues = Util.getLeagues(mContext);

        // Remove the leagues that user doesn't want specified in Shared Preferences
        for (Map.Entry<String, ?> entry : PreferenceManager.getDefaultSharedPreferences(mContext).getAll().entrySet()) {
            if (entry.getValue().equals(false)) {
                allLeagues.remove(Integer.parseInt(entry.getKey()));
            }
        }

        // API link headers
        final String API_SEASON_LINK = "http://api.football-data.org/alpha/soccerseasons/";
        final String API_MATCH_LINK = "http://api.football-data.org/alpha/fixtures/";
        final String API_FIXTURES = "fixtures";
        final String API_LINKS = "_links";
        final String API_SOCCER_SEASON = "soccerseason";
        final String API_SELF = "self";
        final String API_MATCH_DATE = "date";
        final String API_HOME_TEAM = "homeTeamName";
        final String API_AWAY_TEAM = "awayTeamName";
        final String API_RESULT = "result";
        final String API_HOME_GOALS = "goalsHomeTeam";
        final String API_AWAY_GOALS = "goalsAwayTeam";
        final String API_MATCH_DAY = "matchday";

        // Matches data
        String sLeague, sDate, sTime, sHome, sAway, sHomeGoals, sAwayGoals, sMatchId, sMatchDay;

        try {
            JSONArray matches = new JSONObject(JSONdata).getJSONArray(API_FIXTURES);

            // ContentValues of the matches
            Vector<ContentValues> values = new Vector<>(matches.length());
            for (int i = 0; i < matches.length(); i++) {

                // Parsing the link to get only league ID
                JSONObject match_data = matches.getJSONObject(i);
                sLeague = match_data.getJSONObject(API_LINKS).getJSONObject(API_SOCCER_SEASON).getString("href");
                sLeague = sLeague.replace(API_SEASON_LINK, "");

                // Check if the league fetched is one of the leagues the user desire to see
                if (allLeagues.containsKey(Integer.parseInt(sLeague))) {
                    sMatchId = match_data.getJSONObject(API_LINKS).getJSONObject(API_SELF).
                            getString("href");
                    sMatchId = sMatchId.replace(API_MATCH_LINK, "");

                    // If it is not real, change the match ID of the dummy data so that will all go into the database
                    if (!isReal) {
                        sMatchId = sMatchId + Integer.toString(i);
                    }

                    // Formatting date and time
                    sDate = match_data.getString(API_MATCH_DATE);
                    sTime = sDate.substring(sDate.indexOf("T") + 1, sDate.indexOf("Z"));
                    sDate = sDate.substring(0, sDate.indexOf("T"));
                    SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                    match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        Date parsedDate = match_date.parse(sDate + sTime);
                        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
                        newDate.setTimeZone(TimeZone.getDefault());
                        sDate = newDate.format(parsedDate);
                        sTime = sDate.substring(sDate.indexOf(":") + 1);
                        sDate = sDate.substring(0, sDate.indexOf(":"));

                        // If it is not real, changes the dummy data's date to match our current date range.
                        if (!isReal) {
                            Date fragmentDate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
                            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
                            sDate = mformat.format(fragmentDate);
                        }
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "error parsing date" + e.getMessage());
                    }

                    // Get the values of the matches data
                    sHome = match_data.getString(API_HOME_TEAM);
                    sAway = match_data.getString(API_AWAY_TEAM);
                    sHomeGoals = match_data.getJSONObject(API_RESULT).getString(API_HOME_GOALS);
                    sAwayGoals = match_data.getJSONObject(API_RESULT).getString(API_AWAY_GOALS);
                    sMatchDay = match_data.getString(API_MATCH_DAY);
                    ContentValues matchValues = new ContentValues();

                    // Add the values to the array
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_MATCH_ID, sMatchId);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_MATCH_DATE, sDate);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_TIME, sTime);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_HOME_TEAM, sHome);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_AWAY_TEAM, sAway);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_HOME_GOALS, sHomeGoals);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_AWAY_GOALS, sAwayGoals);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_LEAGUE_KEY, sLeague);
                    matchValues.put(MatchesContract.MatchesEntry.COLUMN_MATCH_DAY, sMatchDay);

                    // Add values
                    values.add(matchValues);

                    //log spam
                    //Log.v(LOG_TAG, "MatchID: " + sMatchId + " \nDate: " + sDate + " \nTime: " + sTime + " \nHome: " + sHome + " \nAway: " + sAway + " \nHomeGoals: " + sHomeGoals + " \nAwayGoals: " + sAwayGoals + " \nLeague: " + sLeague);
                }
            }

            // Prepare the vector to return
            ContentValues[] insert_data = new ContentValues[values.size()];
            values.toArray(insert_data);
            int insertedRows = mContext.getContentResolver().bulkInsert(
                    MatchesContract.BASE_CONTENT_URI, insert_data);

            mContext.sendBroadcast(new Intent(BROADCAST_MATCHES_UPDATED).setPackage(mContext.getPackageName()));
            Log.v(LOG_TAG, "Successfully Inserted : " + String.valueOf(insertedRows));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON Exception: " + e.getMessage());
        }

    }
}


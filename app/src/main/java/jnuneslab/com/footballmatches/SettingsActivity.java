package jnuneslab.com.footballmatches;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import java.util.Map;

import jnuneslab.com.footballmatches.data.MatchesContract;

/**
 * Created by Walter on 10/02/2016.
 */
public class SettingsActivity  extends PreferenceActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.pref_general);

     //   Cursor cursor = getApplicationContext().getContentResolver().query(MatchesContract.LeagueEntry.CONTENT_URI, null, null, null, null);

        Map<Integer,String> mapLeagues = Util.getLeagues(getApplicationContext());

        for (Map.Entry<Integer, String> entry : mapLeagues.entrySet()) {
            CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
            checkBoxPreference.setKey(entry.getKey().toString());
            checkBoxPreference.setTitle(entry.getValue());
            checkBoxPreference.setChecked(true);
            getPreferenceScreen().addPreference(checkBoxPreference);
            Log.v("map values","fanoll" + entry.getKey() + ": " + entry.getValue().toString());
        }


    }
}
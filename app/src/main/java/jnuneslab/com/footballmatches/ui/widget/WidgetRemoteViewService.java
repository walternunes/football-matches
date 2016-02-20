package jnuneslab.com.footballmatches.ui.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.data.MatchesContract;
import jnuneslab.com.footballmatches.util.Util;

/**
 * Created by Walter on 20/02/2016.
 */
public final class WidgetRemoteViewService extends RemoteViewsService {
    public static final String TAG = WidgetRemoteViewService.class.getSimpleName();

    private interface MatchesQuery {
        String[] PROJECTION = {
                MatchesContract.LeagueEntry.COLUMN_LEAGUE_NAME,
                MatchesContract.MatchesEntry.COLUMN_HOME_TEAM,
                MatchesContract.MatchesEntry.COLUMN_AWAY_TEAM,
                MatchesContract.MatchesEntry.COLUMN_HOME_GOALS,
                MatchesContract.MatchesEntry.COLUMN_AWAY_GOALS,
                MatchesContract.MatchesEntry.COLUMN_MATCH_ID
        };

        int LEAGUE = 0;
        int HOME = 1;
        int AWAY = 2;
        int HOME_GOALS = 3;
        int AWAY_GOALS = 4;
        int MATCH_ID = 5;
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor mCursor = null;

            @Override
            public void onCreate() {
                Log.d(TAG, "onCreate");
            }

            @Override
            public void onDataSetChanged() {

                if (mCursor != null) {
                    mCursor.close();
                }

                // Call clear identity in order to able to call the provider from remoteView once the provider is not exported neither grantURIpermission
                final long identityToken = Binder.clearCallingIdentity();
                Uri uri = MatchesContract.MatchesEntry.buildScoreWithDate();

                // Formatting date
                String formatString = "yyyy-MM-dd";
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                String todayDate = format.format(new Date());

                mCursor = getContentResolver().query(uri,
                        MatchesQuery.PROJECTION,
                        null,
                        new String[]{todayDate},
                        null);

                // Restore original Identity
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (mCursor != null) {
                    mCursor.close();
                    mCursor = null;
                }
            }

            @Override
            public int getCount() {
                return mCursor == null ? 0 : mCursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        mCursor == null || !mCursor.moveToPosition(position)) {
                    return null;
                }
                final RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_scores_list_item);

                String homeTeamName = mCursor.getString(MatchesQuery.HOME);
                String awayTeamName = mCursor.getString(MatchesQuery.AWAY);
                String league = mCursor.getString(MatchesQuery.LEAGUE);
                String score = Util.getScores(
                        mCursor.getInt(MatchesQuery.HOME_GOALS), mCursor.getInt(MatchesQuery.AWAY_GOALS));

                // Set text views
                views.setTextViewText(R.id.widget_item_league, league);
                views.setTextViewText(R.id.widget_item_home_name, homeTeamName);
                views.setTextViewText(R.id.widget_item_away_name, awayTeamName);
                views.setTextViewText(R.id.widget_item_score, score);

                // Set images views
                views.setImageViewResource(R.id.widget_item_home_crest, Util.getTeamCrestByTeamName(homeTeamName));
                views.setImageViewResource(R.id.widget_item_away_crest, Util.getTeamCrestByTeamName(awayTeamName));
                /*
                // Set content descriptions for accessibility
                views.setContentDescription(R.id.widget_item_home_crest, homeTeamName);
                views.setContentDescription(R.id.widget_item_away_crest, awayTeamName);
                views.setContentDescription(R.id.widget_item_home_name, homeTeamName);
                views.setContentDescription(R.id.widget_item_away_name, awayTeamName);
                views.setContentDescription(R.id.widget_item_league, league);
                views.setContentDescription(R.id.widget_item_score, score);
*/
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_scores_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (mCursor.moveToPosition(position))
                    return mCursor.getLong(MatchesQuery.MATCH_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
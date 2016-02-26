package jnuneslab.com.footballmatches.widget;

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

                // Call clear identity in order to be able to call the provider from remoteView once the provider is not exported neither grantURIpermission
                final long identityToken = Binder.clearCallingIdentity();
                Uri uri = MatchesContract.MatchesEntry.buildScoreWithDate();

                // Formatting date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String todayDate = format.format(new Date());

                mCursor = getContentResolver().query(uri,
                        Util.MatchesQuery.PROJECTION,
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

                String homeTeamName = mCursor.getString(Util.MatchesQuery.HOME_NAME);
                String awayTeamName = mCursor.getString(Util.MatchesQuery.AWAY_NAME);
                String league = mCursor.getString(Util.MatchesQuery.LEAGUE_NAME);
                String matchTime = mCursor.getString(Util.MatchesQuery.MATCH_START_TIME);
                String score = Util.getScores(
                        mCursor.getInt(Util.MatchesQuery.HOME_GOALS), mCursor.getInt(Util.MatchesQuery.AWAY_GOALS));

                // Set text views
                views.setTextViewText(R.id.widget_item_league, league);
                views.setTextViewText(R.id.widget_item_home_name, homeTeamName);
                views.setTextViewText(R.id.widget_item_away_name, awayTeamName);
                views.setTextViewText(R.id.widget_item_score, score);
                views.setTextViewText(R.id.widget_item_matchtime, matchTime);

                // Set images views
                views.setImageViewResource(R.id.widget_item_home_crest, Util.getTeamCrestByTeamName(homeTeamName));
                views.setImageViewResource(R.id.widget_item_away_crest, Util.getTeamCrestByTeamName(awayTeamName));

                // Set content descriptions for accessibility
                views.setContentDescription(R.id.widget_item_home_crest, homeTeamName);
                views.setContentDescription(R.id.widget_item_away_crest, awayTeamName);
                views.setContentDescription(R.id.widget_item_home_name, homeTeamName);
                views.setContentDescription(R.id.widget_item_away_name, awayTeamName);
                views.setContentDescription(R.id.widget_item_league, league);
                views.setContentDescription(R.id.widget_item_score, score);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_scores_list_item);
            }

            @Override
            public int getViewTypeCount() {
                // Return only one view once it will shown only Today matches
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (mCursor.moveToPosition(position))
                    return mCursor.getLong(Util.MatchesQuery.MATCH_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
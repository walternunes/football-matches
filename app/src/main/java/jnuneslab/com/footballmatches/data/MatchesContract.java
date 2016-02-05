package jnuneslab.com.footballmatches.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Database Matches contract
 * Created by Walter on 02/02/2016.
 */
public abstract class MatchesContract {

    // Provider content authority
    public static final String CONTENT_AUTHORITY = "jnuneslab.com.footballmatches";

    // URI base of content authority
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths
    public static final String PATH_MATCHES = "matches";


    /**
     * Inner class that defines the table contents of the matches table
     */
    public static final class MatchesEntry implements BaseColumns {

        // Matches table name
        public static final String TABLE_NAME = "matches";

        // Columns
        public static final String COLUMN_MATCH_ID = "match_id";
        public static final String COLUMN_MATCH_DAY = "match_day";
        public static final String COLUMN_LEAGUE = "league_id";
        public static final String COLUMN_MATCH_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_HOME_TEAM = "home";
        public static final String COLUMN_AWAY_TEAM = "away";
        public static final String COLUMN_HOME_GOALS = "home_goals";
        public static final String COLUMN_AWAY_GOALS = "away_goals";

        // Content Types used
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MATCHES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MATCHES;

        /**
         * Creates the URI of the league path
         * @return the URI of the league
         */
        public static Uri buildScoreWithLeague() {
            return BASE_CONTENT_URI.buildUpon().appendPath("league").build();
        }

        /**
         * Creates the URI of the Score path
         * @return the URI of the score
         */
        public static Uri buildScoreWithId() {
            return BASE_CONTENT_URI.buildUpon().appendPath("id").build();
        }

        /**
         * Creates the URI of the date path
         * @return the URI of the date
         */
        public static Uri buildScoreWithDate() {
            return BASE_CONTENT_URI.buildUpon().appendPath("date").build();
        }
    }
}
package jnuneslab.com.footballmatches.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Matches content provider
 * Created by Walter on 02/02/2016.
 */
public class MatchesProvider extends ContentProvider {

    // Log tag
    private static final String TAG = MatchesProvider.class.getSimpleName();

    // URI Matcher
    private UriMatcher muriMatcher = buildUriMatcher();
    private static MatchesDBHelper mOpenHelper;

    private static final int MATCHES = 100;
    private static final int MATCHES_WITH_LEAGUE = 101;
    private static final int MATCHES_WITH_ID = 102;
    private static final int MATCHES_WITH_DATE = 103;

    // league = ?
    private static final String SCORES_BY_LEAGUE = MatchesContract.MatchesEntry.COLUMN_LEAGUE + " = ?";

    // date like ?
    private static final String SCORES_BY_DATE = MatchesContract.MatchesEntry.COLUMN_MATCH_DATE + " LIKE ?";

    // match_id = ?
    private static final String SCORES_BY_ID = MatchesContract.MatchesEntry.COLUMN_MATCH_ID + " = ?";

    /*
        UriMatcher match each URI to the LEAGUE, MATCH_ID, MATCH_DATE
     */
    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MatchesContract.BASE_CONTENT_URI.toString();

        // create a corresponding code for each type of URI
        matcher.addURI(authority, null, MATCHES);
        matcher.addURI(authority, "league", MATCHES_WITH_LEAGUE);
        matcher.addURI(authority, "id", MATCHES_WITH_ID);
        matcher.addURI(authority, "date", MATCHES_WITH_DATE);

        return matcher;
    }

    private int matchURI(Uri uri) {
        String link = uri.toString();

        if (link.contentEquals(MatchesContract.BASE_CONTENT_URI.toString())) {
            return MATCHES;
        } else if (link.contentEquals(MatchesContract.MatchesEntry.buildScoreWithDate().toString())) {
            return MATCHES_WITH_DATE;
        } else if (link.contentEquals(MatchesContract.MatchesEntry.buildScoreWithId().toString())) {
            return MATCHES_WITH_ID;
        } else if (link.contentEquals(MatchesContract.MatchesEntry.buildScoreWithLeague().toString())) {
            return MATCHES_WITH_LEAGUE;
        }

        return -1;
    }

    @Override
    public boolean onCreate() {

        // Create Matches DB Helper
        mOpenHelper = new MatchesDBHelper(getContext());
        return false;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Don't need to update values
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        final int match = muriMatcher.match(uri);
        switch (match) {
            case MATCHES:
                return MatchesContract.MatchesEntry.CONTENT_TYPE;
            case MATCHES_WITH_LEAGUE:
                return MatchesContract.MatchesEntry.CONTENT_TYPE;
            case MATCHES_WITH_ID:
                return MatchesContract.MatchesEntry.CONTENT_ITEM_TYPE;
            case MATCHES_WITH_DATE:
                return MatchesContract.MatchesEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        int match = matchURI(uri);

        switch (match) {
            case MATCHES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MatchesContract.MatchesEntry.TABLE_NAME,
                        projection, null, null, null, null, sortOrder);
                break;
            case MATCHES_WITH_DATE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MatchesContract.MatchesEntry.TABLE_NAME,
                        projection, SCORES_BY_DATE, selectionArgs, null, null, sortOrder);
                break;
            case MATCHES_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MatchesContract.MatchesEntry.TABLE_NAME,
                        projection, SCORES_BY_ID, selectionArgs, null, null, sortOrder);
                break;
            case MATCHES_WITH_LEAGUE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MatchesContract.MatchesEntry.TABLE_NAME,
                        projection, SCORES_BY_LEAGUE, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Only use bulkInsert
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(muriMatcher.match(uri)));
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (matchURI(uri)) {

            case MATCHES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(MatchesContract.MatchesEntry.TABLE_NAME, null, value,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                Log.w(TAG, uri.toString());
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Don't need to delete values
        return 0;
    }
}
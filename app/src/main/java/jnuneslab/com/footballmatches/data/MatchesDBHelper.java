package jnuneslab.com.footballmatches.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jnuneslab.com.footballmatches.data.MatchesContract.MatchesEntry;
import jnuneslab.com.footballmatches.data.MatchesContract.LeagueEntry;

/**
 * Matches DB Helper
 */
public class MatchesDBHelper extends SQLiteOpenHelper {

    // Full name of the database
    public static final String DATABASE_NAME = "matches.db";

    // Version of the database
    private static final int DATABASE_VERSION = 1;

    // DB Helper constructor
    public MatchesDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_LEAGUES_TABLE = "CREATE TABLE " + LeagueEntry.TABLE_NAME + " (" +
                LeagueEntry._ID + " INTEGER PRIMARY KEY," +
                LeagueEntry.COLUMN_LEAGUE_NAME + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_MATCHES_TABLE = "CREATE TABLE " + MatchesEntry.TABLE_NAME + " ("
                + MatchesEntry._ID + " INTEGER PRIMARY KEY,"
                + MatchesEntry.COLUMN_MATCH_ID + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_MATCH_DATE + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_MATCH_DAY + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_TIME + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_LEAGUE_KEY + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_HOME_TEAM + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_AWAY_TEAM + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_HOME_GOALS + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_AWAY_GOALS + " TEXT NOT NULL,"
                + " FOREIGN KEY (" + MatchesEntry.COLUMN_LEAGUE_KEY + ") REFERENCES " +
                        LeagueEntry.TABLE_NAME + " (" + LeagueEntry._ID + "), "
                + " UNIQUE (" + MatchesEntry.COLUMN_MATCH_ID + ") ON CONFLICT REPLACE"
                + " );";

        // Create tables
        db.execSQL(SQL_CREATE_LEAGUES_TABLE);
        db.execSQL(SQL_CREATE_MATCHES_TABLE);

        // Start transaction to populate existing leagues provided by API
        db.beginTransaction();
        populateLeague(db);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading and create the database again.
        db.execSQL("DROP TABLE IF EXISTS " + LeagueEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MatchesEntry.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Method used to execute the insertion of the initial league names in a DB transaction
     * @param db SQLite Database
     */
    private void populateLeague(SQLiteDatabase db){

        db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 394, 'BundesLiga'); ");
        db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 398, 'Premier League'); ");
        db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 399, 'Primeira Division'); ");
        db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 401, 'Serie A'); ");
        db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 405, 'Champions League'); ");
       // db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 402, 'Primera Liga'); ");
       // db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 400, 'Segunda Division'); " );
       // db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 395, 'BundesLiga'); " );
       // db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 403, 'BundesLiga'); ");
       // db.execSQL("INSERT OR REPLACE INTO " + LeagueEntry.TABLE_NAME + " VALUES ( 404, 'Eredivisie'); ");
    }
}
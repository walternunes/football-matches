package jnuneslab.com.footballmatches.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jnuneslab.com.footballmatches.data.MatchesContract.MatchesEntry;

/**
 * Matches DB Helper
 * Created by Walter on 02/02/2016.
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
        final String CreateMatchesTable = "CREATE TABLE " + MatchesEntry.TABLE_NAME + " ("
                + MatchesEntry._ID + " INTEGER PRIMARY KEY,"
                + MatchesEntry.COLUMN_MATCH_ID + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_MATCH_DATE + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_MATCH_DAY + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_TIME + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_LEAGUE + " INTEGER NOT NULL,"
                + MatchesEntry.COLUMN_HOME_TEAM + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_AWAY_TEAM + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_HOME_GOALS + " TEXT NOT NULL,"
                + MatchesEntry.COLUMN_AWAY_GOALS + " TEXT NOT NULL,"

                + " UNIQUE (" + MatchesEntry.COLUMN_MATCH_ID + ") ON CONFLICT REPLACE"
                + " );";

        // Create matches table
        db.execSQL(CreateMatchesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading and create the database again.
        db.execSQL("DROP TABLE IF EXISTS " + MatchesEntry.TABLE_NAME);
        onCreate(db);
    }
}
package jnuneslab.com.footballmatches.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.data.MatchesContract;

/**
 * Util class containing common methods
 */
public final class Util {

    // Mapping columns
    private static final Integer COL_LEAGUE_ID = 0;
    private static final Integer COL_LEAGUE_NAME = 1;

    // Map containing all Image resources Id, the key of map is the name of team given by the API
    private static final Map<String, Integer> mapTeamImages = new HashMap<>();

    // Map containing all Flag Image resources Id, the key of map is the name of the league given by the API
    private static final Map<String, Integer> mapFlagImages = new HashMap<>();

    // Shared Preferences name
    private static String PREF_UPDATE_DATE = "FootballMatchesDatePref";

    // Value that register the date when it was made last Fetch
    private static String LAST_UPDATE_VALUE = "lastUpdate";

    // Projection query interface
    public interface MatchesQuery {
        String[] PROJECTION = {
                MatchesContract.MatchesEntry.COLUMN_MATCH_ID,
                MatchesContract.MatchesEntry.COLUMN_TIME,
                MatchesContract.MatchesEntry.COLUMN_HOME_TEAM,
                MatchesContract.MatchesEntry.COLUMN_AWAY_TEAM,
                MatchesContract.MatchesEntry.COLUMN_HOME_GOALS,
                MatchesContract.MatchesEntry.COLUMN_AWAY_GOALS,
                MatchesContract.LeagueEntry.COLUMN_LEAGUE_NAME
        };

        int MATCH_ID = 0;
        int MATCH_START_TIME = 1;
        int HOME_NAME = 2;
        int AWAY_NAME = 3;
        int HOME_GOALS = 4;
        int AWAY_GOALS = 5;
        int LEAGUE_NAME = 6;
    }

    // Insert all the values into mapTeamImages HashMap. This is inserted only one time
    static{

        // La liga insert
        mapTeamImages.put("Levante UD", R.drawable.img_levante_ud);
        mapTeamImages.put("FC Barcelona", R.drawable.img_fc_barcelona);
        mapTeamImages.put("Real Betis", R.drawable.img_real_betis);
        mapTeamImages.put("Valencia CF", R.drawable.img_valencia_cf);
        mapTeamImages.put("RC Celta de Vigo", R.drawable.img_celta_vigo);
        mapTeamImages.put("Sevilla FC", R.drawable.img_sevilla_fc);
        mapTeamImages.put("Granada CF", R.drawable.im_granada_cf);
        mapTeamImages.put("Real Madrid CF", R.drawable.img_real_madrid_cf);
        mapTeamImages.put("RC Deportivo La Coruna", R.drawable.img_deportivo_de_la_coruna);
        mapTeamImages.put("Real Sociedad de Fútbol", R.drawable.img_real_sociedad);
        mapTeamImages.put("RCD Espanyol", R.drawable.img_rcd_espanyol);
        mapTeamImages.put("Getafe CF", R.drawable.img_getafe_cf);
        mapTeamImages.put("Club Atlético de Madrid", R.drawable.img_atlhetic_madrid);
        mapTeamImages.put("UD Las Palmas", R.drawable.img_ud_las_palmas);
        mapTeamImages.put("Rayo Vallecano de Madrid", R.drawable.img_rayo_vallecano);
        mapTeamImages.put("Málaga CF", R.drawable.img_malaga_cf);
        mapTeamImages.put("Athletic Club", R.drawable.img_athletic_bilbao);
        mapTeamImages.put("Sporting Gijón", R.drawable.img_sporting_de_gijon);
        mapTeamImages.put("Villarreal CF", R.drawable.img_villarreal_cf);
        mapTeamImages.put("SD Eibar", R.drawable.img_sd_eibar);

        // Bundesliga insert
        mapTeamImages.put("FC Bayern München", R.drawable.img_bayernmunchen);
        mapTeamImages.put("Hamburger SV", R.drawable.img_hambuger_sv);
        mapTeamImages.put("FC Augsburg", R.drawable.img_fc_augsburg);
        mapTeamImages.put("Hertha BSC", R.drawable.img_hertha_bsc);
        mapTeamImages.put("Bayer Leverkusen", R.drawable.img_bayer_04_leverkusen);
        mapTeamImages.put("TSG 1899 Hoffenheim", R.drawable.img_tsg_1899_hoffenheim);
        mapTeamImages.put("SV Darmstadt 98", R.drawable.img_sv_darmstadt_98);
        mapTeamImages.put("Hannover 96", R.drawable.img_hannover_96);
        mapTeamImages.put("1. FSV Mainz 05", R.drawable.img_fsv_mainz_05);
        mapTeamImages.put("FC Ingolstadt 04", R.drawable.img_fc_ingolstadt_04);
        mapTeamImages.put("Werder Bremen", R.drawable.img_sv_werder_bremen);
        mapTeamImages.put("FC Schalke 04", R.drawable.img_fcshalke04);
        mapTeamImages.put("Borussia Dortmund", R.drawable.img_borrusia_dortumund);
        mapTeamImages.put("Bor. Mönchengladbach", R.drawable.img_borussia_m_nchengladbach);
        mapTeamImages.put("VfL Wolfsburg", R.drawable.img_vfl_wolfsburg);
        mapTeamImages.put("Eintracht Frankfurt", R.drawable.img_eintracht_frankfurt);
        mapTeamImages.put("VfB Stuttgart", R.drawable.img_vfb_stuttgart);
        mapTeamImages.put("1. FC Köln", R.drawable.img_fc_koln);

        // Serie A
        mapTeamImages.put("Hellas Verona FC", R.drawable.img_hellas_verona_fc);
        mapTeamImages.put("AS Roma", R.drawable.img_as_roma);
        mapTeamImages.put("Juventus Turin", R.drawable.img_juventus);
        mapTeamImages.put("Udinese Calcio", R.drawable.img_udinese);
        mapTeamImages.put("US Cittá di Palermo", R.drawable.img_palermo);
        mapTeamImages.put("Genoa CFC", R.drawable.img_genoa);
        mapTeamImages.put("US Sassuolo Calcio", R.drawable.img_us_sassuolo);
        mapTeamImages.put("SSC Napoli", R.drawable.img_napoli);
        mapTeamImages.put("UC Sampdoria", R.drawable.img_sampdoria);
        mapTeamImages.put("Carpi FC", R.drawable.img_carpi_fc_1909);
        mapTeamImages.put("SS Lazio", R.drawable.img_lazio);
        mapTeamImages.put("Bologna FC", R.drawable.img_bologna);
        mapTeamImages.put("FC Internazionale Milano", R.drawable.img_inter_milan);
        mapTeamImages.put("Atalanta BC", R.drawable.img_atalanta_bc);
        mapTeamImages.put("Frosinone Calcio", R.drawable.img_frosinone_calcio);
        mapTeamImages.put("Torino FC", R.drawable.img_torino_fc);
        mapTeamImages.put("ACF Fiorentina", R.drawable.img_acf_fiorentina);
        mapTeamImages.put("AC Milan", R.drawable.img_ac_milan);
        mapTeamImages.put("Empoli FC", R.drawable.img_empoli_fc);
        mapTeamImages.put("AC Chievo Verona", R.drawable.img_chievo_verona);


        // Premier League
        mapTeamImages.put("Manchester United FC", R.drawable.img_manchester_united_fc);
        mapTeamImages.put("Tottenham Hotspur FC", R.drawable.img_tottenham);
        mapTeamImages.put("AFC Bournemouth", R.drawable.img_afc_bournemouth);
        mapTeamImages.put("Aston Villa FC", R.drawable.img_aston_villa);
        mapTeamImages.put("Everton FC", R.drawable.img_everton_fc_logo);
        mapTeamImages.put("Watford FC", R.drawable.img_watford);
        mapTeamImages.put("Leicester City FC", R.drawable.img_leicester);
        mapTeamImages.put("Sunderland AFC", R.drawable.img_logo_sunderland);
        mapTeamImages.put("Norwich City FC", R.drawable.img_norwich_city_fc);
        mapTeamImages.put("Crystal Palace FC", R.drawable.img_crystal_palace_fc);
        mapTeamImages.put("Chelsea FC", R.drawable.img_chelsea_fc);
        mapTeamImages.put("Swansea City FC", R.drawable.img_swansea_city);
        mapTeamImages.put("Newcastle United FC", R.drawable.img_newcastle_united_logo);
        mapTeamImages.put("Southampton FC", R.drawable.img_fc_southampton);
        mapTeamImages.put("Arsenal FC", R.drawable.img_arsenal_fc);
        mapTeamImages.put("West Ham United FC", R.drawable.img_west_ham_united_fc);
        mapTeamImages.put("Stoke City FC", R.drawable.img_stoke_city_fc);
        mapTeamImages.put("Liverpool FC", R.drawable.img_liverpool_fc);
        mapTeamImages.put("West Bromwich Albion FC", R.drawable.img_west_bromwich_albion);
        mapTeamImages.put("Manchester City FC", R.drawable.img_manchester_city);


        // Map league flags
        mapFlagImages.put("BundesLiga", R.drawable.de);
        mapFlagImages.put("Premier League", R.drawable.en);
        mapFlagImages.put("Primeira Division", R.drawable.es);
        mapFlagImages.put("Serie A", R.drawable.it);
        mapFlagImages.put("Champions League", R.drawable.eu);
    }

    /**
     * Method responsible for get all leagues names from the database and return a Map of it. The key is the unique Id of the league
     * @param context - Application context
     * @return - Map containing the pair <LeagueID, LeagueName>
     */
    public static Map<Integer, String> getLeagues(Context context) {
        Cursor cursor = context.getContentResolver().query(MatchesContract.LeagueEntry.CONTENT_URI, null, null, null, null);
        Map<Integer, String> mapLeague = new HashMap<>();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                mapLeague.put(cursor.getInt(COL_LEAGUE_ID), cursor.getString(COL_LEAGUE_NAME));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mapLeague;
    }

    /**
     * Method responsible for formatting the Scores String
     * @param homeGoals - Value that will appear on the left side of the score
     * @param awayGoals  - Value that will appear on the right side of the score
     * @return - String formatted
     */
    public static String getScores(int homeGoals, int awayGoals) {

        // Case the params are -1, it means the game has not been played yet
        if (homeGoals < 0 || awayGoals < 0) {
            return "  -  ";
        } else {
            return String.valueOf(homeGoals) + " - " + String.valueOf(awayGoals);
        }
    }

    /**
     * Method responsible for get the team crest resource image Id
     * @param teamName - Name of team that is the key of the hashMap
     * @return - int - that is the resource id image
     */
    public static int getTeamCrestByTeamName(String teamName) {

        Integer resourceId = mapTeamImages.get(teamName);

        // Check if the name is contained in HashMap (already loaded), otherwise return default pic
        if(resourceId != null){
            return resourceId;
        }else return   R.drawable.img_no_photo_icon;

    }

    /**
     * Method responsible for get the team crest resource image Id
     * @param leagueName - Name of league that is the key of the hashMap
     * @return - int - that is the resource id image
     */
    public static int getFlagByLeagueName(String leagueName) {

        Integer resourceId = mapFlagImages.get(leagueName);

        // Check if the name is contained in HashMap (already loaded), otherwise return default pic
        if(resourceId != null){
            return resourceId;
        }else return   R.drawable.img_no_photo_icon;

    }

    /**
     * Method responsible for get the team crest resource image Id
     * @param leagueName - Name of league that is the key of the hashMap
     * @return - int - that is the resource id image
     */
    public static String getFlagDescriptionByLeague(Context context, String leagueName) {

        switch (leagueName){
            case "Champions League": return context.getResources().getString(R.string.flag_champions_league);
            case "Serie A": return context.getResources().getString(R.string.flag_italy);
            case "Primeira Division": return context.getResources().getString(R.string.flag_spain);
            case "BundesLiga": return context.getResources().getString(R.string.flag_germany);
            case "Premier League": return context.getResources().getString(R.string.flag_england);
            default: return context.getResources().getString(R.string.flag_unknown);
        }
    }

    /**
     * Method responsible for insert/update the value of the date when it was done the last Fetch
     * @param context
     */
    public static void writeLastUpdatePref(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_UPDATE_DATE, Context.MODE_PRIVATE).edit();
        editor.putLong(LAST_UPDATE_VALUE, System.currentTimeMillis());
        editor.commit();
    }

    /**
     * Method responsible for read the shared preferences and check if current date is higher than 12 hours from the last update
     * @param context  - Application context
     * @return boolean - true if it is necessary to make a new Fetch
     */
    public static boolean readLastUpdatePref(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_UPDATE_DATE, Context.MODE_PRIVATE);
        long lastUpdate = pref.getLong(LAST_UPDATE_VALUE, -1);

        // Check if the shared preferences exists or if the current date is higher than 12 hours from the last update
        if(lastUpdate == -1 || (System.currentTimeMillis() - lastUpdate) > 43200000 ){
            return true;
        }else return false;
    }
}
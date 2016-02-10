package jnuneslab.com.footballmatches;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import jnuneslab.com.footballmatches.data.MatchesContract;

/**
 * Created by jwfilho on 02/02/2016.
 */
public final class Util {

    private static final Integer INDEX_LEAGUE_ID = 0;
    private static final Integer INDEX_LEAGUE_NAME = 1;

    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static final Map<String, Integer> mapLeagueNames = new HashMap<String, Integer>();;

    // Load the hashmap for the very first time only
    static{

        // La liga insert
        mapLeagueNames.put("Levante UD", R.drawable.img_levante_ud);
        mapLeagueNames.put("FC Barcelona", R.drawable.img_fc_barcelona);
        mapLeagueNames.put("Real Betis", R.drawable.img_real_betis);
        mapLeagueNames.put("Valencia CF", R.drawable.img_valencia_cf);
        mapLeagueNames.put("RC Celta de Vigo", R.drawable.img_celta_vigo);
        mapLeagueNames.put("Sevilla FC", R.drawable.img_sevilla_fc);
        mapLeagueNames.put("Granada CF", R.drawable.im_granada_cf);
        mapLeagueNames.put("Real Madrid CF", R.drawable.img_real_madrid_cf);
        mapLeagueNames.put("RC Deportivo La Coruna", R.drawable.img_deportivo_de_la_coruna);
        mapLeagueNames.put("Real Sociedad de Fútbol", R.drawable.img_real_sociedad);
        mapLeagueNames.put("RCD Espanyol", R.drawable.img_rcd_espanyol);
        mapLeagueNames.put("Getafe CF", R.drawable.img_getafe_cf);
        mapLeagueNames.put("Club Atlético de Madrid", R.drawable.img_atlhetic_madrid);
        mapLeagueNames.put("UD Las Palmas", R.drawable.img_ud_las_palmas);
        mapLeagueNames.put("Rayo Vallecano de Madrid", R.drawable.img_rayo_vallecano);
        mapLeagueNames.put("Málaga CF", R.drawable.img_malaga_cf);
        mapLeagueNames.put("Athletic Club", R.drawable.img_athletic_bilbao);
        mapLeagueNames.put("Sporting Gijón", R.drawable.img_sporting_de_gijon);
        mapLeagueNames.put("Villarreal CF", R.drawable.img_villarreal_cf);
        mapLeagueNames.put("SD Eibar", R.drawable.img_sd_eibar);

        // Bundesliga insert
        mapLeagueNames.put("FC Bayern München", R.drawable.img_bayernmunchen);
        mapLeagueNames.put("Hamburger SV", R.drawable.img_hambuger_sv);
        mapLeagueNames.put("FC Augsburg", R.drawable.img_fc_augsburg);
        mapLeagueNames.put("Hertha BSC", R.drawable.img_hertha_bsc);
        mapLeagueNames.put("Bayer Leverkusen", R.drawable.img_bayer_04_leverkusen);
        mapLeagueNames.put("TSG 1899 Hoffenheim", R.drawable.img_tsg_1899_hoffenheim);
        mapLeagueNames.put("SV Darmstadt 98", R.drawable.img_sv_darmstadt_98);
        mapLeagueNames.put("Hannover 96", R.drawable.img_hannover_96);
        mapLeagueNames.put("1. FSV Mainz 05", R.drawable.img_fsv_mainz_05);
        mapLeagueNames.put("FC Ingolstadt 04", R.drawable.img_fc_ingolstadt_04);
        mapLeagueNames.put("Werder Bremen", R.drawable.img_sv_werder_bremen);
        mapLeagueNames.put("FC Schalke 04", R.drawable.img_fcshalke04);
        mapLeagueNames.put("Borussia Dortmund", R.drawable.img_borrusia_dortumund);
        mapLeagueNames.put("Bor. Mönchengladbach", R.drawable.img_borussia_m_nchengladbach);
        mapLeagueNames.put("VfL Wolfsburg", R.drawable.img_vfl_wolfsburg);
        mapLeagueNames.put("Eintracht Frankfurt", R.drawable.img_eintracht_frankfurt);
        mapLeagueNames.put("VfB Stuttgart", R.drawable.img_vfb_stuttgart);
        mapLeagueNames.put("1. FC Köln", R.drawable.img_fc_koln);

        // Serie A
        mapLeagueNames.put("Hellas Verona FC", R.drawable.img_hellas_verona_fc);
        mapLeagueNames.put("AS Roma", R.drawable.img_as_roma);
        mapLeagueNames.put("Juventus Turin", R.drawable.img_juventus);
        mapLeagueNames.put("Udinese Calcio", R.drawable.img_udinese);
        mapLeagueNames.put("US Cittá di Palermo", R.drawable.img_palermo);
        mapLeagueNames.put("Genoa CFC", R.drawable.img_genoa);
        mapLeagueNames.put("US Sassuolo Calcio", R.drawable.img_us_sassuolo);
        mapLeagueNames.put("SSC Napoli", R.drawable.img_napoli);
        mapLeagueNames.put("UC Sampdoria", R.drawable.img_sampdoria);
        mapLeagueNames.put("Carpi FC", R.drawable.img_carpi_fc_1909);
        mapLeagueNames.put("SS Lazio", R.drawable.img_lazio);
        mapLeagueNames.put("Bologna FC", R.drawable.img_bologna);
        mapLeagueNames.put("FC Internazionale Milano", R.drawable.img_inter_milan);
        mapLeagueNames.put("Atalanta BC", R.drawable.img_atalanta_bc);
        mapLeagueNames.put("Frosinone Calcio", R.drawable.img_frosinone_calcio);
        mapLeagueNames.put("Torino FC", R.drawable.img_torino_fc);
        mapLeagueNames.put("ACF Fiorentina", R.drawable.img_acf_fiorentina);
        mapLeagueNames.put("AC Milan", R.drawable.img_ac_milan);
        mapLeagueNames.put("Empoli FC", R.drawable.img_empoli_fc);
        mapLeagueNames.put("AC Chievo Verona", R.drawable.img_chievo_verona);


        // Premier League
        mapLeagueNames.put("Manchester United FC", R.drawable.img_manchester_united_fc);
        mapLeagueNames.put("Tottenham Hotspur FC", R.drawable.img_tottenham);
        mapLeagueNames.put("AFC Bournemouth", R.drawable.img_afc_bournemouth);
        mapLeagueNames.put("Aston Villa FC", R.drawable.img_aston_villa);
        mapLeagueNames.put("Everton FC", R.drawable.img_everton_fc_logo);
        mapLeagueNames.put("Watford FC", R.drawable.img_watford);
        mapLeagueNames.put("Leicester City FC", R.drawable.img_leicester);
        mapLeagueNames.put("Sunderland AFC", R.drawable.img_logo_sunderland);
        mapLeagueNames.put("Norwich City FC", R.drawable.img_norwich_city_fc);
        mapLeagueNames.put("Crystal Palace FC", R.drawable.img_crystal_palace_fc);
        mapLeagueNames.put("Chelsea FC", R.drawable.img_chelsea_fc);
        mapLeagueNames.put("Swansea City FC", R.drawable.img_swansea_city);
        mapLeagueNames.put("Newcastle United FC", R.drawable.img_newcastle_united_logo);
        mapLeagueNames.put("Southampton FC", R.drawable.img_fc_southampton);
        mapLeagueNames.put("Arsenal FC", R.drawable.img_arsenal_fc);
        mapLeagueNames.put("West Ham United FC", R.drawable.img_west_ham_united_fc);
        mapLeagueNames.put("Stoke City FC", R.drawable.img_stoke_city_fc);
        mapLeagueNames.put("Liverpool FC", R.drawable.img_liverpool_fc);
        mapLeagueNames.put("West Bromwich Albion FC", R.drawable.img_west_bromwich_albion);
        mapLeagueNames.put("Manchester City FC", R.drawable.img_manchester_city);

    }

    public static Map<Integer, String> getLeagues(Context context) {
        Cursor cursor = context.getContentResolver().query(MatchesContract.LeagueEntry.CONTENT_URI, null, null, null, null);
        Map<Integer, String> mapLeague = new HashMap<Integer, String>();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                mapLeague.put(cursor.getInt(INDEX_LEAGUE_ID), cursor.getString(INDEX_LEAGUE_NAME));
                cursor.moveToNext();
            }

        }
        return mapLeague;
    }

    public static String getMatchDay(int match_day, int league_num) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return "Group Stages, Matchday : 6";
            } else if (match_day == 7 || match_day == 8) {
                return "First Knockout round";
            } else if (match_day == 9 || match_day == 10) {
                return "QuarterFinal";
            } else if (match_day == 11 || match_day == 12) {
                return "SemiFinal";
            } else {
                return "Final";
            }
        } else {
            return "Matchday : " + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return "? - ?";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(String teamname) {

        Integer resourceId = mapLeagueNames.get(teamname);

        // Check if the name is contained in hashmap (already loaded), otherwise return default pic
        if(resourceId != null){
            return resourceId;
        }else return   R.drawable.img_no_photo_icon;

    }

}
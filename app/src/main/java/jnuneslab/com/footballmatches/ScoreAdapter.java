package jnuneslab.com.footballmatches;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 * Created by Walter on 23/11/2015.
 */
public class ScoreAdapter  extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    // Map columns
    public static final int COL_ID = 1;
    public static final int COL_MATCHDAY = 2;
    public static final int COL_LEAGUE = 3;
    public static final int COL_MATCHSTARTTIME = 4;
    public static final int COL_MATCHLENGTH = 5;
    public static final int COL_HOME = 6;
    public static final int COL_AWAY = 7;
    public static final int COL_HOME_GOALS = 8;
    public static final int COL_AWAY_GOALS = 9;
    public static final int COL_LEAGUE_NAME = 11;

    public double detail_match_id = 0;
    private String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";

    private Cursor mCursor;
    private Context mContext;
    private LayoutInflater  mInflater;

    public ScoreAdapter(Fragment fragment)
    {
        mInflater = LayoutInflater.from(fragment.getActivity());
        mContext = fragment.getActivity();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(mInflater.inflate(R.layout.scores_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        mCursor.moveToPosition(position);
        holder.homeNameTxtView.setText(mCursor.getString(COL_HOME));
        Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 1><" + mCursor.getString(10) + " >2<" + mCursor.getString(2) + " >3<" + mCursor.getString(3) + " >4<" + mCursor.getString(4) + " >5<" + mCursor.getString(5) + " >6<" + mCursor.getString(6) + " >7<" + mCursor.getString(7) + " >8<" + mCursor.getString(8) + " >9<" + mCursor.getString(9) ) ;
        holder.awayNameTxtView.setText(mCursor.getString(COL_AWAY));
        holder.leagueNameTxtView.setText(mCursor.getString(COL_LEAGUE_NAME));
        holder.matchTimeTxtView.setText(mCursor.getString(COL_MATCHSTARTTIME));
        Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 2" + mCursor.getInt(COL_HOME_GOALS) + " " + mCursor.getInt(COL_AWAY_GOALS));
        Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 3" + Util.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS)));
               holder.scoreTxtView.setText(Util.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS)));
        holder.match_id = mCursor.getDouble(COL_ID);
        holder.homeFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_HOME)));
        holder.awayFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_AWAY)
        ));

        LayoutInflater vi = (LayoutInflater) mContext.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.fragment_main, null);
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView homeNameTxtView;
        protected TextView awayNameTxtView;
        protected TextView leagueNameTxtView;
        protected TextView scoreTxtView;
        protected TextView matchTimeTxtView;
        protected ImageView homeFlagImgView;
        protected ImageView awayFlagImgView;
        protected double match_id;

        public ViewHolder(View view) {
            super(view);
            homeNameTxtView = (TextView) view.findViewById(R.id.home_name);
            awayNameTxtView = (TextView) view.findViewById(R.id.away_name);
            leagueNameTxtView = (TextView) view.findViewById(R.id.league_textview);
            scoreTxtView = (TextView) view.findViewById(R.id.score_item_score);
            matchTimeTxtView = (TextView) view.findViewById(R.id.matchtime_textview);
            homeFlagImgView = (ImageView) view.findViewById(R.id.home_crest);
            awayFlagImgView = (ImageView) view.findViewById(R.id.away_crest);
        }
    }
}
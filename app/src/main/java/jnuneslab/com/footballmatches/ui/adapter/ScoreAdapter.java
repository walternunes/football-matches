package jnuneslab.com.footballmatches.ui.adapter;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.util.Util;

/**
 * ScoreAdapter class responsible to handle all the matches information to be shown in each CardView
 */
public class ScoreAdapter  extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    // Mapping columns
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

    private Cursor mCursor;
    private LayoutInflater  mInflater;

    public ScoreAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getActivity());
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

        // Get current cursor position
        mCursor.moveToPosition(position);

        // Populate the matches values into the ViewHolder that will be presented in the CardView
        holder.homeNameTxtView.setText(mCursor.getString(COL_HOME));
        holder.awayNameTxtView.setText(mCursor.getString(COL_AWAY));
        holder.leagueNameTxtView.setText(mCursor.getString(COL_LEAGUE_NAME));
        holder.matchTimeTxtView.setText(mCursor.getString(COL_MATCHSTARTTIME));
        holder.scoreTxtView.setText(Util.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS)));
        holder.match_id = mCursor.getDouble(COL_ID);
        holder.homeFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_HOME)));
        holder.awayFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_AWAY)
        ));

    }

    /**
     * ViewHolder Class responsible for bind TextView and ImageView
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Holder fields
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
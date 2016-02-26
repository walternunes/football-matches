package jnuneslab.com.footballmatches.ui.adapter;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.util.Util;

/**
 * ScoreAdapter class responsible to handle all the matches information to be shown in each CardView
 */
public class ScoreAdapter  extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    // Mapping columns


    private Cursor mCursor;
    private LayoutInflater  mInflater;

    // Number of different leagues
    private int totalLeagues;

    // Position of the cursor to control the grouping of matches according to each league
    private int currentCursorPosition;

    public ScoreAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getActivity());
    }

    public void swapCursor(Cursor newCursor) {

        mCursor = newCursor;
        totalLeagues = getNumberLeagues();
        if(newCursor == null)currentCursorPosition = 0;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    /**
     * Method responsible to return the number of different leagues in that day that will be used to calculate how many cardview will be shown
     * @return int - number of different leagues
     */
    private int getNumberLeagues(){
        Set<String> setAllLeagues = new TreeSet<>();

        // if there is no data return
        if(mCursor == null){
            return  0;
        }

        // Get the initial position and move the cursor to the first position
        int initialPos = mCursor.getPosition();
        mCursor.moveToFirst();

        // Insert the leagues into a Tree set in order to not insert repeated leagues
        for(int i = 0; i < mCursor.getCount(); i++, mCursor.moveToNext()){
            setAllLeagues.add(mCursor.getString(Util.MatchesQuery.LEAGUE_NAME));
        }

        // Return the cursor to the orignal position
        mCursor.moveToPosition(initialPos);
        return setAllLeagues.size();
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? totalLeagues : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Each viewHolder will be represented by on CardView
        return new ViewHolder(mInflater.inflate(R.layout.league_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Check if the current position is the last position
        if( currentCursorPosition >= mCursor.getCount()) return;

        // Move to the current position and get the current league
        mCursor.moveToPosition(currentCursorPosition);
        String currentLeague = mCursor.getString(Util.MatchesQuery.LEAGUE_NAME);

        // Start to populate the content of card view
        for (int i = 0; mCursor.getPosition() < mCursor.getCount() && mCursor.getString(Util.MatchesQuery.LEAGUE_NAME).equals(currentLeague); i++, mCursor.moveToNext()) {

            // Inflate the content matches layout and create a viewHolder for each match
            View r = LayoutInflater.from(mInflater.getContext()).inflate(R.layout.scores_list_item, holder.linearLayout, false);
            ContentViewHolder matchViewHolder = new ContentViewHolder(r);

            // If it is the first position - insert the header with the League name, otherwise remove the textView
            if(i == 0) {
                matchViewHolder.leagueNameTxtView.setVisibility(View.VISIBLE);
                matchViewHolder.leagueNameTxtView.setText(mCursor.getString(Util.MatchesQuery.LEAGUE_NAME));
            }else matchViewHolder.leagueNameTxtView.setVisibility(View.GONE);

            // Populate the content
            matchViewHolder.homeNameTxtView.setText(mCursor.getString(Util.MatchesQuery.HOME_NAME));
            matchViewHolder.awayNameTxtView.setText(mCursor.getString(Util.MatchesQuery.AWAY_NAME));
            matchViewHolder.matchTimeTxtView.setText(mCursor.getString(Util.MatchesQuery.MATCH_START_TIME));
            matchViewHolder.scoreTxtView.setText(Util.getScores(mCursor.getInt(Util.MatchesQuery.HOME_GOALS), mCursor.getInt(Util.MatchesQuery.AWAY_GOALS)));
            matchViewHolder.match_id = mCursor.getDouble(Util.MatchesQuery.MATCH_ID);
            matchViewHolder.homeFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                    mCursor.getString(Util.MatchesQuery.HOME_NAME)));
            matchViewHolder.awayFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                    mCursor.getString(Util.MatchesQuery.AWAY_NAME)));

            // Add the view into linearLayout
            holder.linearLayout.addView(r);
         }

        // Update the variable with the current cursor position
        currentCursorPosition = mCursor.getPosition();


    }

    /**
     * ViewHolder Class responsible for bind the LinearLayout (that will be expanded) into CardView
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        protected LinearLayout linearLayout;

        /**
         * HolderView constructor
         * @param view - View
         */
        public ViewHolder(View view) {
            super(view);
            linearLayout =((LinearLayout)view.findViewById(R.id.league_linear_layout));
        }
    }

    /**
     * ViewHolder Class responsible for bind the contents of the CardView - ImageView and TextView
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder
    {
        // Content holder fields (inside CardView)
        protected TextView homeNameTxtView;
        protected TextView awayNameTxtView;
        protected TextView leagueNameTxtView;
        protected TextView scoreTxtView;
        protected TextView matchTimeTxtView;
        protected ImageView homeFlagImgView;
        protected ImageView awayFlagImgView;
        protected double match_id;

        /**
         * HolderView constructor
         * @param view - View
         */
        public ContentViewHolder(View view) {
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
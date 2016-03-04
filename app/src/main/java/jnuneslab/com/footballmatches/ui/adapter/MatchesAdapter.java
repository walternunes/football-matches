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
 * MatchesAdapter class responsible to handle all the matches information as a list
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private Cursor mCursor;
    private LayoutInflater mInflater;

    public MatchesAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getActivity());
    }

    public void swapCursor(Cursor newCursor) {

        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    /**
     * Method responsible for add ImageView and TextView according to the LeagueName. In case of removal the View is gone
     * @param isShown - Parameter to remove or add the Views
     * @param holder - Parent ViewHolder to manipulate the View
     * @param leagueName - Name of the league
     */
    private void showHeader(boolean isShown, ViewHolder holder, String leagueName){

        if(isShown){
            holder.leagueNameTxtView.setVisibility(View.VISIBLE);
            holder.leagueNameTxtView.setText(leagueName);
            holder.countryFlagImgView.setVisibility(View.VISIBLE);
            holder.countryFlagImgView.setImageResource(Util.getFlagByLeagueName(leagueName));
            holder.countryFlagImgView.setContentDescription(Util.getFlagDescriptionByLeague(mInflater.getContext(), leagueName));
        }else{
            holder.leagueNameTxtView.setVisibility(View.GONE);
            holder.countryFlagImgView.setVisibility(View.GONE);
        }
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
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Move to the current position and get the current league
        mCursor.moveToPosition(position);

        // Populate the content
        holder.homeNameTxtView.setText(mCursor.getString(Util.MatchesQuery.HOME_NAME));
        holder.awayNameTxtView.setText(mCursor.getString(Util.MatchesQuery.AWAY_NAME));
        holder.matchTimeTxtView.setText(mCursor.getString(Util.MatchesQuery.MATCH_START_TIME));
        holder.scoreTxtView.setText(Util.getScores(mCursor.getInt(Util.MatchesQuery.HOME_GOALS), mCursor.getInt(Util.MatchesQuery.AWAY_GOALS)));
        holder.homeFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(Util.MatchesQuery.HOME_NAME)));
        holder.awayFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(Util.MatchesQuery.AWAY_NAME)));

        // If it is the first position - insert the header with the League name, otherwise remove the textView and imageView
        if (mCursor.isFirst()) {
            showHeader(true, holder, mCursor.getString(Util.MatchesQuery.LEAGUE_NAME));
        } else {

            // Get the current league because the cursor will be changed to the previous position
            String currentLeague = mCursor.getString(Util.MatchesQuery.LEAGUE_NAME);
            mCursor.moveToPrevious();
            if (!mCursor.getString(Util.MatchesQuery.LEAGUE_NAME).equals(currentLeague)) {
                showHeader(true, holder, currentLeague);
            } else {
                showHeader(false, holder, null);
            }
        }
    }

    /**
     * ViewHolder Class responsible for populate the content of each match (line in the listView)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView homeNameTxtView;
        protected TextView awayNameTxtView;
        protected TextView leagueNameTxtView;
        protected TextView scoreTxtView;
        protected TextView matchTimeTxtView;
        protected ImageView homeFlagImgView;
        protected ImageView awayFlagImgView;
        protected ImageView countryFlagImgView;

        /**
         * HolderView constructor
         *
         * @param view - View
         */
        public ViewHolder(View view) {
            super(view);
            homeNameTxtView = (TextView) view.findViewById(R.id.home_name);
            awayNameTxtView = (TextView) view.findViewById(R.id.away_name);
            leagueNameTxtView = (TextView) view.findViewById(R.id.league_textview);
            scoreTxtView = (TextView) view.findViewById(R.id.score_item_score);
            matchTimeTxtView = (TextView) view.findViewById(R.id.matchtime_textview);
            homeFlagImgView = (ImageView) view.findViewById(R.id.home_crest);
            awayFlagImgView = (ImageView) view.findViewById(R.id.away_crest);
            countryFlagImgView = (ImageView) view.findViewById(R.id.country_flag);
        }
    }
}
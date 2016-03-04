package jnuneslab.com.footballmatches.ui.fragment;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jnuneslab.com.footballmatches.service.FetchScoreTask;
import jnuneslab.com.footballmatches.ui.MultiSwipeRefreshLayout;
import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.data.MatchesContract;
import jnuneslab.com.footballmatches.ui.adapter.MatchesAdapter;
import jnuneslab.com.footballmatches.util.Util;

/**
 * Fragment used by Main Activity
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, MultiSwipeRefreshLayout.CanChildScrollUpCallback, SwipeRefreshLayout.OnRefreshListener  {

    private MatchesAdapter mMatchesAdapter;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private String[] mFragmentDate = new String[1];
    private ImageView mNoMatchesImageView;
    private TextView mNoMatchesTextView;

    public static final int SCORES_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Bind Image and Text to be shown when there is no matches
        mNoMatchesImageView = (ImageView) rootView.findViewById(R.id.no_matches_img_view);
        mNoMatchesTextView = (TextView) rootView.findViewById(R.id.no_matches_txt_view);

        //  Set the date passed from the arguments to this fragment
        setFragmentDate(getArguments().getString(ScrollTabFragment.STATE_DATE_FRAGMENT));

        // Configure SwipeRefresh Layout
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setCanChildScrollUpCallback(this);

        // Get the view and create adapter
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.score_list_view);
        mMatchesAdapter = new MatchesAdapter(this);

        // Bind the adapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mMatchesAdapter);

       // Show no matches on create
       showNoView(true);
       return rootView;
    }

    /**
     * Method responsible for show the view telling there is no matches in that day
     * @param isShown - boolean
     */
    private void showNoView(boolean isShown){
        if(isShown){
            mNoMatchesImageView.setVisibility(View.VISIBLE);
            mNoMatchesTextView.setVisibility(View.VISIBLE);
        }else{
            mNoMatchesImageView.setVisibility(View.GONE);
            mNoMatchesTextView.setVisibility(View.GONE);
        }
    }

    /**
     * Setter Fragment Date
     * @param date - Date that identifies the fragment
     */
    public void setFragmentDate(String date){
            mFragmentDate[0] = date;
    }

    /**
     * Method responsible for control the refresh icon
     * @param refreshing - boolean
     */
    private void postRefreshing(final boolean refreshing) {

        // Check if layout is not null and then add or remove the refresh icon according to the parameter
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.post(new Runnable() {
                @Override public void run() {
                    if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(refreshing);
                }
            });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            getLoaderManager().initLoader(SCORES_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), MatchesContract.MatchesEntry.buildScoreWithDate(),
                Util.MatchesQuery.PROJECTION, null, mFragmentDate,  MatchesContract.LeagueEntry.COLUMN_LEAGUE_NAME + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        // Check if it was find matches in that day and make the TextView gone
        if(cursor.getCount() > 0)
          showNoView(false);

        // Swap the cursor and cancel the refresh icon
        mMatchesAdapter.swapCursor(cursor);
        postRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mMatchesAdapter.swapCursor(null);
        showNoView(true);
        postRefreshing(false);
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return mRecyclerView != null && ViewCompat.canScrollVertically(mRecyclerView, -1);
    }

    @Override
    public void onRefresh() {
        updateMatches();
    }

    private void updateMatches(){

        // First delete old values from DB
        getContext().getContentResolver().delete(MatchesContract.BASE_CONTENT_URI, null, null);

        // Fetch new data
        new FetchScoreTask(getContext()).execute();
    }
}
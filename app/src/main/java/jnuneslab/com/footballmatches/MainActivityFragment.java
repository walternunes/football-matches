package jnuneslab.com.footballmatches;

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

import jnuneslab.com.footballmatches.data.MatchesContract;

/**
 * Fragment used by Main Activity
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, MultiSwipeRefreshLayout.CanChildScrollUpCallback, SwipeRefreshLayout.OnRefreshListener  {

    private ScoreAdapter mScoreAdapter;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private String[] fragmentDate = new String[1];

    public static final int SCORES_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //  Set the date passed from the arguments to this fragment
        setFragmentDate(getArguments().getString(ScrollTabFragment.STATE_DATE_FRAGMENT));

        // Configure SwipeRefresh Layout
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setCanChildScrollUpCallback(this);

        // Get the view and create adapter
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.score_list_view);
        mScoreAdapter = new ScoreAdapter(this);

        // Bind the adapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mScoreAdapter);
        return rootView;
    }

    /**
     * Setter Fragment Date
     * @param date - Date that identifies the fragment
     */
    public void setFragmentDate(String date){
            fragmentDate[0] = date;
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
                null, null, fragmentDate, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            cursor.moveToNext();
        }

        // Swap the cursor and cancel the refresh icon
        mScoreAdapter.swapCursor(cursor);
        postRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mScoreAdapter.swapCursor(null);
        postRefreshing(false);
    }


    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return mRecyclerView != null && ViewCompat.canScrollVertically(mRecyclerView, -1);
    }

    @Override
    public void onRefresh() {

        // First delete old values from DB and request to fetch new data
        getContext().getContentResolver().delete(MatchesContract.BASE_CONTENT_URI, null,null);
        new FetchScoreTask(getContext()).execute();
    }
}

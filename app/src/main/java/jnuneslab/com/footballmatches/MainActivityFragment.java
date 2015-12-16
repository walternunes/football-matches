package jnuneslab.com.footballmatches;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ScoreAdapter mScoreAdapter;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final ListView score_list = (ListView) rootView.findViewById(R.id.score_list_view);
        mScoreAdapter = new ScoreAdapter(getActivity());
        score_list.setAdapter(mScoreAdapter);

         new FetchScoreTask(getContext(), mScoreAdapter).execute();
        return rootView;
    }
}

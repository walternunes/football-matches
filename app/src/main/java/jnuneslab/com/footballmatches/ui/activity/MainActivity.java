package jnuneslab.com.footballmatches.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.data.MatchesContract;
import jnuneslab.com.footballmatches.service.FetchScoreTask;
import jnuneslab.com.footballmatches.ui.fragment.ScrollTabFragment;
import jnuneslab.com.footballmatches.util.Util;

/**
 * MainActivity class
 */
public class MainActivity extends AbstractActivity {

    private ScrollTabFragment mScrollTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
         mScrollTabFragment = (ScrollTabFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_scores_pager);

        if(Util.readLastUpdatePref(getApplicationContext())){
            getApplicationContext().getContentResolver().delete(MatchesContract.BASE_CONTENT_URI, null, null);

            // Fetch new data
            new FetchScoreTask(getApplicationContext()).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

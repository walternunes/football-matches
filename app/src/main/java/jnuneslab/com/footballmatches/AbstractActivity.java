package jnuneslab.com.footballmatches;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;


public abstract class AbstractActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }
}

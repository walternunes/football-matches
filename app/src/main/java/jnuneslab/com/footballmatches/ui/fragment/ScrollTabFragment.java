package jnuneslab.com.footballmatches.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import jnuneslab.com.footballmatches.R;

/**
 * ScrollTabFragment class is responsible to handle the Pager menu (toolbar)
 */
public final class ScrollTabFragment extends Fragment {

    // Total number of tab present in toolbar
    public static final int NUM_PAGES = 5;

    // Start position of toolbar. Start position is zero
    public static final int TODAY_POSITION = 2;

    // State fragment identifier to be add as argument in the fragment
    public static final String STATE_DATE_FRAGMENT = "date_fragment";

    private MainActivityFragment[] viewFragments = new MainActivityFragment[NUM_PAGES];
    private ScrollTabAdapter mPagerScrollAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    /**
     * Method responsible for create a new instance of MainActivityFragment and add an argument containing a date
     * @param date - String containing the date
     * @return - The instance of the MainActivityFragment
     */
    public static MainActivityFragment newInstance(String date) {

        // Create a bundle and add the date into it
        Bundle args = new Bundle();
        args.putString(STATE_DATE_FRAGMENT, date);

        // Create a new instance and set the arguments
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scroll_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize variables
        mPagerScrollAdapter = new ScrollTabAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        mTabLayout.setTabsFromPagerAdapter(mPagerScrollAdapter);

        // Create a new fragment and calculate the date time for each tab
        for (int i = 0; i < NUM_PAGES; i++) {
            Date fragmentDate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            viewFragments[i] = ScrollTabFragment.newInstance(dateFormat.format(fragmentDate));
        }

        // Set the listener to be able to change the tabs by clicking in the item directly (without swipe)
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {  }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {  }
        });

        // Set initial values in ViewPager
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setOffscreenPageLimit(NUM_PAGES);
        mViewPager.setAdapter(mPagerScrollAdapter);
        mViewPager.setCurrentItem(TODAY_POSITION);
    }

    /**
     * ScrollTab Adapter class responsible to handle the values that will be inserted in each tab
     */
    private class ScrollTabAdapter extends FragmentStatePagerAdapter {

        /**
         * Constructor
         *
         * @param fm Fragment Mananger
         */
        public ScrollTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return viewFragments[i];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }


        /**
         * Returns the page title for the top indicator
         *
         * @param position of the tab sequence starting with 0
         * @return Charsequence with the name of day
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return getDayName(getActivity(), System.currentTimeMillis() + ((position - 2) * 86400000));
        }

        /**
         * Parameterize the date to get current day
         *
         * @param context      - context of the application
         * @param dateInMillis - date in milliseconds of the current date
         * @return String      - Contains the name of day
         */
        public String getDayName(Context context, long dateInMillis) {

            // Get current time
            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);

            // Manual check in order to change the day text
            if (julianDay == currentJulianDay) {
                return context.getString(R.string.today);
            } else if (julianDay == currentJulianDay + 1) {
                return context.getString(R.string.tomorrow);
            } else if (julianDay == currentJulianDay - 1) {
                return context.getString(R.string.yesterday);
            } else {
                Time time = new Time();
                time.setToNow();

                // Day format is set according to the week name (monday, tuesday ...)
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMillis);
            }
        }
    }
}

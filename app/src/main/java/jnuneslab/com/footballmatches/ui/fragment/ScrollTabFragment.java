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
import android.util.Log;
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
    private static MainActivityFragment newInstance(String date) {

        // Create a bundle and add the date into it
        Bundle args = new Bundle();
        args.putString(STATE_DATE_FRAGMENT, date);

        // Create a new instance and set the arguments
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Method responsible for create each instance of each page. This method should be called in order to refresh the title and the content when the day is updated
     */
    private void bindFragmentInPager(){
        // Create a new fragment and calculate the date time for each tab
        for (int i = 0; i < NUM_PAGES; i++) {
            Date fragmentDate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
            SimpleDateFormat dateFormat = new SimpleDateFormat(getContext().getString(R.string.date_format_simple));
            viewFragments[i] = ScrollTabFragment.newInstance(dateFormat.format(fragmentDate));
        }

        mTabLayout.setTabsFromPagerAdapter(mPagerScrollAdapter);
        mViewPager.setCurrentItem(TODAY_POSITION);
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

        // Set the listener to be able to change the tabs by clicking in the item directly (without swipe)
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Set initial values in ViewPager
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setOffscreenPageLimit(NUM_PAGES);
        mViewPager.setAdapter(mPagerScrollAdapter);

        // Create the fragments for each page
        bindFragmentInPager();

    }


    @Override
    public void onResume() {
        super.onResume();

        // Get the current day that is marked as Today in the pager
        String currentInstanceDay = viewFragments[TODAY_POSITION].getFragmentDate();
        if (currentInstanceDay != null) {

            // Format the application current day and the real current day
            currentInstanceDay = currentInstanceDay.substring(currentInstanceDay.length() - 2);
            SimpleDateFormat dateFormat = new SimpleDateFormat(getContext().getString(R.string.date_format_day));
            String currentDay = dateFormat.format(System.currentTimeMillis());

            // Check if the current application is really the current day
            if (!currentInstanceDay.equals(currentDay)) {

                // Refresh the pager and fragments because it is necessary to update the dates
                bindFragmentInPager();
                mPagerScrollAdapter.notifyDataSetChanged();
            }
        }
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
        public int getItemPosition(Object item) {
            return POSITION_NONE;
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
                // Day format is set according to the week name (monday, tuesday ...)
                SimpleDateFormat dayFormat = new SimpleDateFormat(getContext().getString(R.string.date_format_week));
                return dayFormat.format(dateInMillis);
            }
        }
    }
}

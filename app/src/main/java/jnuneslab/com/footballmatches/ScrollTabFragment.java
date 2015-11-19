package jnuneslab.com.footballmatches;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Walter.
 */
public class ScrollTabFragment extends Fragment {

    public static final int NUM_PAGES = 5;
    public static final int TODAY_POSITION = 2;

    public ViewPager mPagerHandler;
    private PageScrollAdapter mPagerScrollAdapter;
    private MainActivityFragment[] viewFragments = new MainActivityFragment[NUM_PAGES];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_scroll_tab, container, false);
        mPagerHandler = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerScrollAdapter = new PageScrollAdapter(getChildFragmentManager());
        for (int i = 0;i < NUM_PAGES;i++)
        {
            // Create the fragments for each day of the week
            viewFragments[i] = new MainActivityFragment();
        }
        mPagerHandler.setAdapter(mPagerScrollAdapter);
        mPagerHandler.setCurrentItem(TODAY_POSITION);
        return rootView;
    }

    private class PageScrollAdapter extends FragmentStatePagerAdapter
    {

        /**
         * Constructor
         * @param fm Fragment Mananger
         */
        public PageScrollAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i)
        {
            return viewFragments[i];
        }

        @Override
        public int getCount()
        {
            return NUM_PAGES;
        }


        /**
         * Returns the page title for the top indicator
         * @param position of the tab sequence starting with 0
         * @return Charsequence with the name of day
         */
        @Override
        public CharSequence getPageTitle(int position){
            return getDayName(getActivity(),System.currentTimeMillis()+((position-2)*86400000));
        }

        /**
         * Parameterized the date to get current day
         * @param context - context of the application
         * @param dateInMillis - date in milliseconds of the current date
         * @return String - Contains the name of day
         */
        public String getDayName(Context context, long dateInMillis) {

            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);

            // Manual check to change the day text
            if (julianDay == currentJulianDay) {
                return context.getString(R.string.today);
            } else if ( julianDay == currentJulianDay +1 ){
                return context.getString(R.string.tomorrow);
            } else if ( julianDay == currentJulianDay -1){
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
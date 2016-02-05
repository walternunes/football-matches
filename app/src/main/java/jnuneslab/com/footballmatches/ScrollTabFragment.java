package jnuneslab.com.footballmatches;

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

/**
 * Created by Walter.
 */
public final class ScrollTabFragment extends Fragment {

    public static final int NUM_PAGES = 5;
    public static final int TODAY_POSITION = 2;

    private ScrollTabAdapter mPagerScrollAdapter;
    private MainActivityFragment[] viewFragments = new MainActivityFragment[NUM_PAGES];
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scroll_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO check current state first

        mPagerScrollAdapter = new ScrollTabAdapter( getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        mTabLayout.setTabsFromPagerAdapter(mPagerScrollAdapter);


        // TODO use listener to get current tab

        for(int i = 0; i < NUM_PAGES; i++){
            Date fragmentdate = new Date(System.currentTimeMillis()+((i-2)*86400000));
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
            viewFragments[i] =  new MainActivityFragment();
            viewFragments[i].setFragmentDate(mformat.format(fragmentdate));
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setOffscreenPageLimit(NUM_PAGES);
        mViewPager.setAdapter(mPagerScrollAdapter);
        mViewPager.setCurrentItem(TODAY_POSITION);
    }

    private class ScrollTabAdapter extends FragmentStatePagerAdapter
    {

        /**
         * Constructor
         * @param fm Fragment Mananger
         */
        public ScrollTabAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i){
             return viewFragments[i];
        }

        @Override
        public int getCount(){
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
         * Parameterize the date to get current day
         * @param context - context of the application
         * @param dateInMillis - date in milliseconds of the current date
         * @return String - Contains the name of day
         */
        public String getDayName(Context context, long dateInMillis) {

            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);

            // Manual check in order to change the day text
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

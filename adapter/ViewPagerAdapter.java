package majorproject.amity.smarttourist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import majorproject.amity.smarttourist.fragments.DestinationGalleryFragment;
import majorproject.amity.smarttourist.fragments.DestinationInfoFragment;
import majorproject.amity.smarttourist.fragments.FoodFragment;
import majorproject.amity.smarttourist.fragments.HotelFragment;
import majorproject.amity.smarttourist.fragments.POIFragment;
import majorproject.amity.smarttourist.fragments.WebViewFragment;

/**
 * Created by vijim_000 on 8/5/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }


    @Override
    public Fragment getItem(int position) {
        Log.d("tab try", "in around me");
        if (position == 0) // if the position is 0 we are returning the First tab
        {
            HotelFragment hotelFragment = new HotelFragment();
            hotelFragment.getInstance(2);
            return hotelFragment;
        } else if (position == 1) {
            FoodFragment foodFragment = new FoodFragment();
            foodFragment.getInstance(2);
            return foodFragment;
        } else {
            POIFragment poiFragment = new POIFragment();
            poiFragment.getInstance(2);
            return poiFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}

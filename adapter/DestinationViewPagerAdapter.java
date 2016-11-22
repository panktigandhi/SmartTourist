package majorproject.amity.smarttourist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import majorproject.amity.smarttourist.fragments.DestinationGalleryFragment;
import majorproject.amity.smarttourist.fragments.DestinationInfoFragment;
import majorproject.amity.smarttourist.fragments.WebViewFragment;

/**
 * Created by vijim_000 on 6/24/2016.
 */
public class DestinationViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public DestinationViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Log.d("tab try", "inside here");
            DestinationInfoFragment destinationInfoFragment = new DestinationInfoFragment();
            destinationInfoFragment.getInstance(1,1);
            return destinationInfoFragment;
//            HotelFragment hotelFragment = new HotelFragment();
//            hotelFragment.getInstance(2);
//            return hotelFragment;
        } else if (position == 1) {
            DestinationGalleryFragment destinationGalleryFragment = new DestinationGalleryFragment();
            return destinationGalleryFragment;
//            FoodFragment foodFragment = new FoodFragment();
//            foodFragment.getInstance(2);
//            return foodFragment;
        } else {
            WebViewFragment webViewFragment = new WebViewFragment();
            webViewFragment.getInstance("http://dubai360.com/#!s=2513-mercato-shopping-mall-outside&l=en");
            return webViewFragment;
//            POIFragment poiFragment = new POIFragment();
//            poiFragment.getInstance(2);
//            return poiFragment;
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

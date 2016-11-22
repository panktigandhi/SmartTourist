package majorproject.amity.smarttourist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import majorproject.amity.smarttourist.adapter.DestinationViewPagerAdapter;
import majorproject.amity.smarttourist.adapter.ViewPagerAdapter;
import majorproject.amity.smarttourist.utils.SlidingTabLayout;

public class DestinationReachedActivity extends AppCompatActivity {

    ViewPager pager; DestinationViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Info","Gallery", "360 guide"};
    int numOfTabs = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_reached);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new DestinationViewPagerAdapter(getSupportFragmentManager(), Titles, numOfTabs);
        pager = (ViewPager) findViewById(R.id.pagerDestination);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabsDestination);
        tabs.setDistributeEvenly(true);// To make the Tabs Fixed set this true

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }

        });

        tabs.setViewPager(pager);

    }

}

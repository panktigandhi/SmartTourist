package majorproject.amity.smarttourist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.appevents.AppEventsLogger;

import majorproject.amity.smarttourist.adapter.ViewPagerAdapter;
import majorproject.amity.smarttourist.fragments.FoodFragment;
import majorproject.amity.smarttourist.fragments.HotelFragment;
import majorproject.amity.smarttourist.fragments.POIFragment;
import majorproject.amity.smarttourist.utils.FontCache;
import majorproject.amity.smarttourist.utils.SlidingTabLayout;

public class AroundMeActivity extends AppCompatActivity {

    int type=2; ImageButton rest,hotel,poi;
    public static AppEventsLogger loggerForAroundMe;

    ViewPager pager; ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Hotels", "Food", "POI"};
    int numOfTabs = 3;
    Button guide, itinerary, aroundme, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me);
        loggerForAroundMe = AppEventsLogger.newLogger(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numOfTabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);// To make the Tabs Fixed set this true

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }

        });

        tabs.setViewPager(pager);

        guide = (Button) findViewById(R.id.btnguide);
        itinerary = (Button) findViewById(R.id.btnitenerary);
        aroundme = (Button) findViewById(R.id.btnaroundme);
        settings = (Button) findViewById(R.id.btnsettings);

        setFontForButtons();

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                finish();
            }
        });

        itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String response = ShowAlertDialogWithListview();
                Intent i = new Intent(getApplicationContext(), ItineraryActivity.class);
                //i.putExtra("itinerary_type", response);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                finish();
            }
        });

    }
    public void checkFragment() {
        if(type == 1){
            FoodFragment f = new FoodFragment();
            //getSupportActionBar().setTitle("Restaurants in Dubai");
            f.getInstance(2);
            replaceFragment(f,"Food Fragment",true);
        }
        else if(type == 2){
            HotelFragment h = new HotelFragment();
            //getSupportActionBar().setTitle("Hotels in Dubai");
            h.getInstance(2);
            replaceFragment(h,"Hotels Fragment",true);
        }
        else if(type == 3){
            POIFragment p = new POIFragment();
            //getSupportActionBar().setTitle("POIs in Dubai");
            p.getInstance(2);
            replaceFragment(p,"POI Fragment",true);
        }

    }

    public void replaceFragment(Fragment fragment, String tag, boolean shouldAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (shouldAddToBackStack)
            ft.addToBackStack(tag);
        else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        //ft.replace(R.id.fragment_around_me, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AroundMeActivity.this.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public void setFontForButtons(){
        guide.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        itinerary.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        aroundme.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        settings.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
    }
}

package majorproject.amity.smarttourist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import majorproject.amity.smarttourist.fragments.CurrencyConverterFragment;
import majorproject.amity.smarttourist.fragments.DestinationInfoFragment;
import majorproject.amity.smarttourist.fragments.EventsFragment;
import majorproject.amity.smarttourist.fragments.ExploreDubaiFragment;
import majorproject.amity.smarttourist.fragments.FoodFragment;
import majorproject.amity.smarttourist.fragments.HotelFragment;
import majorproject.amity.smarttourist.fragments.POIFragment;
import majorproject.amity.smarttourist.fragments.SingleMapFragment;

public class ContainerActivity extends AppCompatActivity {
    int type;
    int status;
    String name;
    float lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent in = getIntent();
        type= in.getIntExtra("type",0);
        status = in.getIntExtra("status",0);
//        sendToContainer = in.getParcelableExtra("sendToContainer");
//        type = in.getIntExtra("Type",0);
//        status = in.getIntExtra("Status", 0);
        name = in.getStringExtra("name");
        lat = in.getFloatExtra("lat",0.0f);
        lon = in.getFloatExtra("lon",0.0f);
        checkFragment();
    }
    public void checkFragment() {
        if(type == 1){
            FoodFragment f = new FoodFragment();
            getSupportActionBar().setTitle("Restaurants in Dubai");
            f.getInstance(status);
            replaceFragment(f,"Food Fragment",true);
        }
        else if(type == 2){
            HotelFragment h = new HotelFragment();
            getSupportActionBar().setTitle("Hotels in Dubai");
            h.getInstance(status);
            replaceFragment(h,"Hotels Fragment",true);
        }
        else if(type == 3){
            POIFragment p = new POIFragment();
            getSupportActionBar().setTitle("POIs in Dubai");
            p.getInstance(status);
            replaceFragment(p,"POI Fragment",true);
        }
        else if(type == 4){
            EventsFragment e = new EventsFragment();
            getSupportActionBar().setTitle("Events in Dubai");
            e.getInstance(status);
            replaceFragment(e,"Hotels Fragment",true);
        }
        else if(type == 5){
            CurrencyConverterFragment c = new CurrencyConverterFragment();
            getSupportActionBar().setTitle("Convert Currency");
            c.getInstance();
            replaceFragment(c,"Currency Fragment",true);
        }
        else if(type == 6){
            ExploreDubaiFragment ed = new ExploreDubaiFragment();
            getSupportActionBar().setTitle("Explore Dubai");
            ed.getInstance();
            replaceFragment(ed,"ExploreDubai Fragment",true);
        }
        else if(type==7){
            SingleMapFragment sf = new SingleMapFragment();
            getSupportActionBar().setTitle("Map of-");
            Log.d("status-container", status+"");
            sf.getInstance(status,new LatLng(lat, lon),"Map");
            replaceFragment(sf,"SingleMap Fragment",true);
        }
    }

    public void replaceFragment(Fragment fragment, String tag, boolean shouldAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (shouldAddToBackStack)
            ft.addToBackStack(tag);
        else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        ft.replace(R.id.containerFragment, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DestinationInfoFragment myFragment = (DestinationInfoFragment)getSupportFragmentManager().findFragmentByTag("TextInfo Fragment");
        if (!(myFragment != null && myFragment.isVisible())) {
            // top of stack is not web text info
            Log.d("can remove","not visible");
            ContainerActivity.this.finish();
            overridePendingTransition(R.anim.stay, R.anim.right_slide_out);
        }
        else {
            Log.d("can remove","visible or not null");
        }

    }
}

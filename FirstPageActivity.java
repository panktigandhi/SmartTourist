package majorproject.amity.smarttourist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;

import majorproject.amity.smarttourist.utils.FontCache;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class FirstPageActivity extends AppCompatActivity {

    ImageButton hotel,rest,poi,events,news,currency;
    Button guide, itinerary, aroundme, settings;
    String alertResponse;

    // Add to each long-lived activity
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    // for Android, you should also log app deactivation
    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        hotel = (ImageButton) findViewById(R.id.hotels_menu);
        rest = (ImageButton) findViewById(R.id.rest_menu);
        poi = (ImageButton) findViewById(R.id.poi_menu);
        events = (ImageButton) findViewById(R.id.events_menu);
        news = (ImageButton) findViewById(R.id.news_menu);
        currency = (ImageButton) findViewById(R.id.currency_menu);

        guide = (Button) findViewById(R.id.btnguide);
        itinerary = (Button) findViewById(R.id.btnitenerary);
        aroundme = (Button) findViewById(R.id.btnaroundme);
        settings = (Button) findViewById(R.id.btnsettings);

        setFontForButtons();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        aroundme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AroundMeActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                finish();
            }
        });

        itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithListview();

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

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContainerActivity.class);
                i.putExtra("type", 1);
                i.putExtra("status", 1);
//                Bundle mBundle = new Bundle();
//                mBundle.putParcelable("sendToContainer", s);
//                i.putExtras(mBundle);
                startActivity(i);
                overridePendingTransition(R.anim.right_slide_in, R.anim.stay);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContainerActivity.class);
                i.putExtra("type",2); i.putExtra("status",1);
                startActivity(i);
                overridePendingTransition(R.anim.right_slide_in, R.anim.stay);
            }
        });

        poi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContainerActivity.class);
                i.putExtra("type",3); i.putExtra("status",1);
                startActivity(i);
                overridePendingTransition(R.anim.right_slide_in, R.anim.stay);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContainerActivity.class);
                i.putExtra("type",4); i.putExtra("status", 1);
                startActivity(i);
                overridePendingTransition(R.anim.right_slide_in, R.anim.stay);
            }
        });

        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContainerActivity.class);
                i.putExtra("type",5); i.putExtra("status", 0);
                startActivity(i);
                overridePendingTransition(R.anim.right_slide_in, R.anim.stay);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContainerActivity.class);
                i.putExtra("type",6); i.putExtra("status", 0);
                startActivity(i);
                overridePendingTransition(R.anim.right_slide_in, R.anim.stay);
            }
        });

        loadImages();
    }

    public void loadImages(){
        Resources resources = SmartTouristApp.getContext().getResources();
        int r = SmartTouristApp.getContext().getResources().getIdentifier("hotels", "drawable", SmartTouristApp.getContext().getPackageName());
        hotel.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, r, 100, 250));
        r = SmartTouristApp.getContext().getResources().getIdentifier("rest", "drawable", SmartTouristApp.getContext().getPackageName());
        rest.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, r, 100, 250));
        r = SmartTouristApp.getContext().getResources().getIdentifier("poi", "drawable", SmartTouristApp.getContext().getPackageName());
        poi.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, r, 100, 250));
        r = SmartTouristApp.getContext().getResources().getIdentifier("events", "drawable", SmartTouristApp.getContext().getPackageName());
        events.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, r, 100, 250));
        r = SmartTouristApp.getContext().getResources().getIdentifier("explore", "drawable", SmartTouristApp.getContext().getPackageName());
        news.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, r, 100, 250));
        r = SmartTouristApp.getContext().getResources().getIdentifier("currency", "drawable", SmartTouristApp.getContext().getPackageName());
        currency.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, r, 100, 250));
        //imageView.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, R.id.hotels, 100, 100));
    }

    public String ShowAlertDialogWithListview()
    {
        List<String> categories = new ArrayList<String>();
        categories.add("Mnaually create an itinerary");
        categories.add("Create an itinerary using our TripMaker - custom algorithm");

        //Create sequence of items
        final CharSequence[] mCurrency = categories.toArray(new String[categories.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choose the type of itinerary:");
        dialogBuilder.setItems(mCurrency, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String a = mCurrency[item].toString();  //Selected item in listview
                alertResponse = a;
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
        return alertResponse;
    }

    public void setFontForButtons(){
        guide.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        itinerary.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        aroundme.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        settings.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
    }
}

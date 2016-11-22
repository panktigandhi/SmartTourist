package majorproject.amity.smarttourist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import majorproject.amity.smarttourist.fragments.DatePickerFragment;
import majorproject.amity.smarttourist.utils.FontCache;

public class ItineraryActivity extends AppCompatActivity {

    //ImageButton sightseeing,activities,shopping,culture,park,architecture,next;
    //boolean[] touched = new boolean[6];
    //List<String> interest = new ArrayList<>();
    static String type;
    Button guide, itinerary, aroundme, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        type = getIntent().getStringExtra("itinerary_type");
        DatePickerFragment f = new DatePickerFragment();
        getSupportActionBar().setTitle("Itinerary creation");
        replaceFragment(f, "DatePicker fragment", true);
//        shopping = (ImageButton) findViewById(R.id.shopping);
//        sightseeing = (ImageButton) findViewById(R.id.sightseeing);
//        activities = (ImageButton) findViewById(R.id.activities);
//        culture = (ImageButton) findViewById(R.id.culture);
//        architecture = (ImageButton) findViewById(R.id.architecture);
//        park = (ImageButton) findViewById(R.id.parks);
//        next = (ImageButton) findViewById(R.id.btnItinerary);
//        loadListeners();

        guide = (Button) findViewById(R.id.btnguide);
        itinerary = (Button) findViewById(R.id.btnitenerary);
        aroundme = (Button) findViewById(R.id.btnaroundme);
        settings = (Button) findViewById(R.id.btnsettings);

        setFontForButtons();

        aroundme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AroundMeActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                finish();
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
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

   /* void loadListeners(){
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[2]) {
                    shopping.setBackgroundResource(R.drawable.webview_2);
                    interest.add("Shopping");
                    touched[2] = true;
                } else {
                    shopping.setBackgroundResource(R.drawable.webview_1);
                    interest.remove("Shopping");
                    touched[2] = false;
                }
            }
        });
        sightseeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[0]) {
                    sightseeing.setBackgroundResource(R.drawable.webview_1);
                    interest.add("Sightseeing");
                    touched[0] = true;
                } else {
                    sightseeing.setBackgroundResource(R.drawable.webview_1);
                    interest.remove("Sightseeing");
                    touched[0] = false;
                }
            }
        });
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[1]) {
                    activities.setBackgroundResource(R.drawable.webview_2);
                    interest.add("Activities");
                    touched[1] = true;
                } else {
                    activities.setBackgroundResource(R.drawable.webview_2);
                    interest.remove("Activities");
                    touched[1] = false;
                }
            }
        });
        culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[3]){
                    culture.setBackgroundResource(R.drawable.webview_4);
                    interest.add("Sports");
                    touched[3] = true; }
                else{
                    culture.setBackgroundResource(R.drawable.webview_4);
                    interest.remove("Sports");
                    touched[3] = false;
                }}
        });
        architecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[4]){
                    architecture.setBackgroundResource(R.drawable.webview_5);
                    interest.add("Culture");
                    touched[4] = true; }
                else{
                    architecture.setBackgroundResource(R.drawable.webview_5);
                    interest.remove("Culture");
                    touched[4] = false;
                }}
        });
        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[5]){
                    park.setBackgroundResource(R.drawable.webview_6);
                    interest.add("Park");
                    touched[5] = true; }
                else{
                    park.setBackgroundResource(R.drawable.webview_6);
                    interest.remove("Park");
                    touched[5] = false;
                }}
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ItineraryCreator.interestsSelected = interest;
//                ItineraryCreator k = new ItineraryCreator();
//                //k.getListData();
            }
        });

        //seekBar.getProgressDrawable().setColorFilter("<Color you wish>", PorterDuff.Mode.MULTIPLY);
    }*/

    public void replaceFragment(Fragment fragment, String tag, boolean shouldAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (shouldAddToBackStack)
            ft.addToBackStack(tag);
        else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        ft.replace(R.id.replaceFragment, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ItineraryActivity.this.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public void setFontForButtons(){
        guide.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        itinerary.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        aroundme.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        settings.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
    }

}

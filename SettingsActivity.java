package majorproject.amity.smarttourist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import majorproject.amity.smarttourist.utils.FontCache;

public class SettingsActivity extends AppCompatActivity {

    Button guide, itinerary, aroundme, settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{user-id}/picture",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("fb_pic", response.toString());
                    }
                }
        ).executeAsync();

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

        itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String response = ShowAlertDialogWithListview();
                Intent i = new Intent(getApplicationContext(), ItineraryActivity.class);
                //i.putExtra("itinerary_type",response);
                startActivity(i);
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
    }

    public void setFontForButtons(){
        guide.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        itinerary.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        aroundme.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        settings.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
    }
}

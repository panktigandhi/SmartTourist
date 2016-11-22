package majorproject.amity.smarttourist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.facebook.appevents.AppEventsLogger;

import majorproject.amity.smarttourist.models.Events;
import majorproject.amity.smarttourist.models.HotelInfo;
import majorproject.amity.smarttourist.models.Poi;
import majorproject.amity.smarttourist.models.RestaurantDetails;
import majorproject.amity.smarttourist.utils.MyTextView;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class CardViewClickActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    String title; String id; int position; String type;
    ImageView imgToolbar;
    MyTextView description, location, amenities,url;
    RatingBar guest_rating;
    Button map, rate, fav, directions, visited;
    AppEventsLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_click);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logger = AppEventsLogger.newLogger(this);

        position = getIntent().getIntExtra("position", 0);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        imgToolbar = (ImageView) findViewById(R.id.image);
        description = (MyTextView) findViewById(R.id.desc);
        location = (MyTextView) findViewById(R.id.location);
        amenities = (MyTextView) findViewById(R.id.amenities);
        url = (MyTextView) findViewById(R.id.details);
        guest_rating = (RatingBar) findViewById(R.id.rating);

        map = (Button) findViewById(R.id.show_in_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContainerActivity.class);
                i.putExtra("type",7);
                if(type.equals("hotel")) {
                    Log.d("status-card","sent 0");
                    i.putExtra("status", 0); }
                else if(type.equals("rest")) {
                    Log.d("status-card","sent 1");
                    i.putExtra("status", 1); }
                else {
                    Log.d("status-card","sent 2");
                    i.putExtra("status",2); }

                i.putExtra("name",title);
                i.putExtra("lat",getLat(type));
                i.putExtra("lon",getLon(type));
                startActivity(i);
            }
        });
        rate = (Button) findViewById(R.id.rate_now);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplication(), "Share your thoughts with others", Toast.LENGTH_LONG).show();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                String mymsg="I have successfully reached my target:\nFeeling excited. :)";
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, mymsg);
                startActivity(Intent.createChooser(sharingIntent, "Share your thoughts with others using"));
            }
        });
        fav = (Button) findViewById(R.id.fav_add);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TheSmartTourist.favorites.add(TheSmartTourist.globalPOI.get(position));
                fav.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                logger.logEvent("favorite button pressed");
            }
        });
        directions = (Button) findViewById(R.id.directionsTo);
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DirectionsActivity.class);
                i.putExtra("lat",getLat(type));
                i.putExtra("lon",getLon(type));
                startActivity(i);
            }
        });
        visited = (Button) findViewById(R.id.visited);
        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visited.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                TheSmartTourist.no_of_places_visited+=1;
                logger.logEvent("visited button pressed");
            }
        });
        dynamicToolbarColor(); toolbarTextAppearance();
        addContent();
    }

    void dynamicToolbarColor(){
        Context context = SmartTouristApp.getContext();
        int resourceId = 0;
        if(type.equals("rest")) {
            Log.d("rest", id);
            resourceId = context.getResources().getIdentifier("r_" + id, "drawable", SmartTouristApp.getContext().getPackageName()); }
        else if(type.equals("hotel")) {
            Log.d("hotel",id);
            resourceId = context.getResources().getIdentifier("h_" + id, "drawable", SmartTouristApp.getContext().getPackageName()); }
        else if(type.equals("poi")) {
            Log.d("poi",id+"");
            resourceId = context.getResources().getIdentifier("p_" + id, "drawable", SmartTouristApp.getContext().getPackageName()); }
        else if(type.equals("events")) {
            Log.d("events",id);
            resourceId = context.getResources().getIdentifier("e_" + id, "drawable", SmartTouristApp.getContext().getPackageName()); }


        Bitmap bitmap = TheSmartTourist.decodeSampledBitmapFromResource(getResources(), resourceId, 100, 100);
        imgToolbar.setImageBitmap(bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }

    void toolbarTextAppearance(){
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    void addContent(){
        if(type.equals("hotel")) {
            HotelInfo h = TheSmartTourist.globalHotelInfo.get(position);
            description.setText(h.Description);
            location.setText(h.Location.StreetAddress);
            url.setText(h.DetailsUrl);
            String list = "";
            for(String s : h.AmenityList.Amenity) {
                list = s + "\n";
            }
            amenities.setText(list);
            amenities.setText("");
            guest_rating.setRating(Float.parseFloat(h.GuestRating)); }

        else if(type.equals("rest")){
            RestaurantDetails r = TheSmartTourist.globalRestInfo.get(position);
            description.setText(r.phone_numbers);
            location.setText(r.getLocation().address);
            url.setText(r.url);
            amenities.setText(r.average_cost_for_two);
            guest_rating.setRating(Float.parseFloat(r.user_rating.aggregate_rating));
        }

        else if(type.equals("poi")){
            Poi r = TheSmartTourist.globalPOI.get(position);
            description.setText(r.features);
            location.setText(r.location);
            url.setText(r.website);
            amenities.setText(r.cost);
            guest_rating.setRating(r.local_rating);
        }

        else if(type.equals("events")){
            Events r = TheSmartTourist.globalEvents.get(position);
            description.setText(r.description);
            location.setText(r.venue);
            url.setText(r.website);
            amenities.setText(r.admission);
            guest_rating.setRating(0.0f);
        }
    }

    public float getLat(String type){
        if(type.equals("hotel")) {
            return Float.valueOf(TheSmartTourist.globalHotelInfo.get(position).Location.GeoLocation.Latitude);
        }
        else if(type.equals("rest")){
            return Float.parseFloat(TheSmartTourist.globalRestInfo.get(position).getLocation().latitude);
        }
        else if(type.equals("poi")){
            return (float)TheSmartTourist.globalPOI.get(position).lat;
        }
        else {
            return 0.0f;
        }
    }

    public float getLon(String type){
        if(type.equals("hotel")) {
            return Float.parseFloat(TheSmartTourist.globalHotelInfo.get(position).Location.GeoLocation.Longitude);
        }
        else if(type.equals("rest")){
            return Float.parseFloat(TheSmartTourist.globalRestInfo.get(position).getLocation().longitude);
        }
        else if(type.equals("poi")){
            return (float) TheSmartTourist.globalPOI.get(position).lon;
        }
        else {
            return 0.0f;
        }
    }


}



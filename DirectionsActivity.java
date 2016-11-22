package majorproject.amity.smarttourist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import majorproject.amity.smarttourist.adapter.DirectionsExpandableListAdapter;
import majorproject.amity.smarttourist.models.MapDirections;
import majorproject.amity.smarttourist.models.Steps;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class DirectionsActivity extends AppCompatActivity {

    DirectionsExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    //FrameLayout mapForDirections;
    float lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lat = getIntent().getFloatExtra("lat",0.0f);
        lon = getIntent().getFloatExtra("lon",0.0f);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        getListData();
    }

    public void getListData(){
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        LatLng dest = new LatLng(lat,lon);
        LatLng source = new LatLng(TheSmartTourist.getUserLat(),TheSmartTourist.getUserLon());

        String sourceString = TheSmartTourist.getUserLat()+","+TheSmartTourist.getUserLon();
        String destString = lat+","+lon;
        Ion.getDefault(this).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
        Ion.with(this)
                .load("https://maps.googleapis.com/maps/api/directions/json?origin="+sourceString+"&destination="+destString+"&mode=transit")
                        //&key=AIzaSyB2KsuY7olY2DigaekkJ
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Log.d("DirectionsActivity", "Error loading directions" + e.toString());
                            Toast.makeText(getApplicationContext(), "error loading directions", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Log.d("Directions", result.toString());
                            MapDirections r = (MapDirections) new Gson().fromJson(result.toString(), new TypeToken<MapDirections>() {
                            }.getType());
                            if (r.routes.size() > 0) {
                            MapDirections.Routes route = r.routes.get(0);
                            MapDirections.Legs l = route.legs.get(0);
                            List<Steps> s = l.steps;
                            for (Steps p : s) {
                                Log.d("header", p.html_instructions);
                                listDataHeader.add(p.html_instructions);
                                List<String> newList = new ArrayList<String>();
                                if (p.steps != null) {
                                    List<Steps> st = p.steps;
                                    for (Steps pq : st) {
                                        Log.d("child", "found a child for " + p.html_instructions + pq.html_instructions);
                                        //Log.d("child",pq.html_instructions);
                                        if (pq.html_instructions != null) {
                                            newList.add(pq.html_instructions);
                                        }
                                    }
                                }
                                listDataChild.put(p.html_instructions, newList);
                            }
                            listAdapter = new DirectionsExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
                            // setting list adapter
                            expListView.setAdapter(listAdapter);
                        }
                            else {
                                //create alert view that no public transport route available
                            }
                        }
                    }
                });
    }

    public void addMapPolyline(){
//        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
//        for (int z = 0; z < list.size(); z++) {
//            LatLng point = list.get(z);
//            options.add(point);
//        }
//        line = myMap.addPolyline(options);
    }


}

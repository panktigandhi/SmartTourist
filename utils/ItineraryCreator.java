package majorproject.amity.smarttourist.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import majorproject.amity.smarttourist.SmartTouristApp;
import majorproject.amity.smarttourist.fragments.FoodFragment;
import majorproject.amity.smarttourist.models.Poi;

/**
 * Created by vijim_000 on 5/31/2016.
 */

//geofence geome
public class ItineraryCreator {

    private static final int slow=6, medium=8, fast = 10;
    public List<String> interestsSelected = new ArrayList<>();
    private List<Poi> avlPoi = new ArrayList<>();
    private HashMap<Integer,Float> distances = new HashMap<>();
    String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+"&destinations="+"&key=YOUR_API_KEY";
    private float budget = 100;

    private void getListData(){
//        if(TheSmartTourist.globalPOI == null){
//            parseJSON();
//        }
        String check = "";
        SharedPreferences mPrefs = SmartTouristApp.getContext().getSharedPreferences("com.amity.smarttourist", Context.MODE_PRIVATE);
        check = mPrefs.getString("interests","");
        String[] split = check.split(",");
        for(String j: split){
            interestsSelected.add(j);
        }

        for(Poi element: TheSmartTourist.globalPOI ){
            Log.d("original", element.type);
            for(String i: interestsSelected){
                if(element.type.contains(i)){
                    avlPoi.add(element);
                }
            }
        }

        Collections.sort(avlPoi, new Comparator<Poi>() {
            public int compare(Poi p1, Poi p2) {
                return Double.compare(p2.rating, p1.rating);
            }
        });

        for (Poi a: avlPoi){
            Log.d("new", a.name + " " + a.rating);
        }
        createItinerary();
    }

    public List<Poi> createItinerary(){
        getListData();
        List<Poi> day1 = new ArrayList<>();

        Poi start = avlPoi.get(0);
        String[] cost1 = new String[2];
        cost1 = (start.cost).split(";");
        if(Float.parseFloat(cost1[0]) < budget) {
            budget = budget - Float.parseFloat(cost1[0]);
        }
        Log.d("endloc",start.lat+","+start.lon);
        while(!(avlPoi.isEmpty())){

        day1.add(start); avlPoi.remove(start);
        Location startLoc = new Location("");
        startLoc.setLatitude(start.lat); startLoc.setLongitude(start.lon);
        float min = 1000000000; Poi minPOI = new Poi(); minPOI.name = "none"; minPOI.cost="0";
        for(Poi end: avlPoi){
            Location endLoc = new Location("");
            Log.d("endloc",end.lat+","+end.lon);
            endLoc.setLatitude(end.lat); endLoc.setLongitude(end.lon);
            //distances.put(end.idpoi,startLoc.distanceTo(endLoc));
            if(min > startLoc.distanceTo(endLoc)){
                min = startLoc.distanceTo(endLoc);
                minPOI = end;
            }
        }
            //Burj Khalifa 125;95
        Log.d("min POI is",minPOI.name);
            String[] cost = new String[2];
            if(minPOI.cost.contains(";")){
                cost = (minPOI.cost).split(";");}
            else{cost[0]=minPOI.cost;}
        if(Float.parseFloat(cost[0]) < budget) {
            start = minPOI; budget = budget - Float.parseFloat(cost[0]);
            Log.d("cost remaining",minPOI.name+": "+budget);
        }
        else { avlPoi.remove(minPOI); }
        //day1.add(minPOI); avlPoi.remove(minPOI);
        }

        for(Poi x: day1){
            Log.d("final",x.name);
        }

        return day1;
    }

    public void getDistances(){
        //https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=40.6655101,-73.89188969999998&destinations=40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.659569%2C-73.933783%7C40.729029%2C-73.851524%7C40.6860072%2C-73.6334271%7C40.598566%2C-73.7527626%7C40.659569%2C-73.933783%7C40.729029%2C-73.851524%7C40.6860072%2C-73.6334271%7C40.598566%2C-73.7527626&key=YOUR_API_KEY
    }

}



package majorproject.amity.smarttourist.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import majorproject.amity.smarttourist.AroundMeActivity;
import majorproject.amity.smarttourist.CardViewClickActivity;
import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.adapter.RestaurantAdapter;
import majorproject.amity.smarttourist.models.RestaurantDetails;
import majorproject.amity.smarttourist.models.RestaurantsZomato;
import majorproject.amity.smarttourist.utils.TheSmartTourist;


public class FoodFragment extends Fragment {

    List<RestaurantDetails> sTrips = new ArrayList<>();
    List<RestaurantDetails> filtered = new ArrayList<>();

    RestaurantAdapter adapter; RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Button sortBy, filterBy;
    int status;

    public FoodFragment() {
        // Required empty public constructor
    }

    public FoodFragment getInstance(int s) {
        status = s;
        return new FoodFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_general, container, false);
        sortBy = (Button) v.findViewById(R.id.sortBtn);
        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), sortBy);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu_sort, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        sortItemsInList((String) item.getTitle());
                        //Toast.makeText(getActivity(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

        filterBy = (Button) v.findViewById(R.id.filterBtn);
        filterBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), filterBy);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu_filter_rest, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        filterItemsInList((String) item.getTitle());
                        //Toast.makeText(getActivity(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerGeneral);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshGeneral);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //getData();
            }
        });
            if(TheSmartTourist.AppBarOffset)
                    mSwipeRefreshLayout.setEnabled(true);
            else
                    mSwipeRefreshLayout.setEnabled(false);

        if(status==1) getJSON();
        else getAroundMe();
        return v;
        }
    private void getJSON(){
        String json = null;

        try {
            InputStream is = getActivity().getAssets().open("food.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            RestaurantsZomato r = (RestaurantsZomato) new Gson().fromJson(json, new TypeToken<RestaurantsZomato>(){}.getType());
            JsonParser p = new JsonParser();
            JsonObject newjson = p.parse(json).getAsJsonObject();
            JsonObject nearby_restaurants = newjson.get("nearby_restaurants").getAsJsonObject();
            Log.d("RestDetails",nearby_restaurants.toString());
            JsonObject rest = nearby_restaurants.get("1").getAsJsonObject();
            Log.d("RestZomato", rest.toString());
            JsonObject details = rest.get("restaurant").getAsJsonObject();
            Log.d("RestZomato", details.toString());
            Set<Map.Entry<String, JsonElement>> objects =  nearby_restaurants.entrySet();

            for (Map.Entry<String, JsonElement> entry : objects) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                if (entry.getValue() != null) {
                    JsonObject ob=entry.getValue().getAsJsonObject();
                    if (ob != null) {
                        //RestaurantDetails is created to read value of "restaurant"
                        JsonObject restaurant = ob.get("restaurant").getAsJsonObject();
                        RestaurantDetails rd = new Gson().fromJson(restaurant.toString(), RestaurantDetails.class);
                        Log.d("Name ",rd.name);
                        if (rd.getZomatoRating().aggregate_rating != null){
                            Log.d("Rest"+rd.name,rd.getZomatoRating().aggregate_rating); }
                        else {
                            Log.d("Rest"+rd.name,"not found");
                        }
                        sTrips.add(rd);
                    }
                }
            }
            // sTrips = (List<RestaurantDetails>) new Gson().fromJson(nearby_restaurants, new TypeToken<List<RestaurantDetails>>() {}.getType());
            TheSmartTourist.globalRestInfo = sTrips;
            adapter = new RestaurantAdapter(sTrips); recyclerView.setAdapter(adapter);
            mSwipeRefreshLayout.setRefreshing(false);
            ((RestaurantAdapter) adapter).setOnItemClickListener(new RestaurantAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                    i.putExtra("type","rest");
                    i.putExtra("title",sTrips.get(position).name);
                    i.putExtra("id",sTrips.get(position).id);
                    i.putExtra("position",position);
                    startActivity(i);
                }
            });

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void getData() {
                Ion.getDefault(getActivity()).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
                Ion.with(getActivity())
                        .load("https://developers.zomato.com/api/v2.1/geocode?lat=25.260693&lon=55.295853")
                        .setHeader("user-key","db3e2fc3c465417905f424b0c2aff1b9")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null) {
                                    Log.d("ScheduleTripFragment", "Error loading food"+e.toString());
                                    Toast.makeText(getActivity(), "error loading food", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else{
                                    Log.d("FoodFragment", result.toString());
                                    RestaurantsZomato r = (RestaurantsZomato) new Gson().fromJson(result.toString(), new TypeToken<RestaurantsZomato>(){}.getType());
                                    JsonObject nearby_restaurants = result.get("nearby_restaurants").getAsJsonObject();
                                    Log.d("RestDetails",nearby_restaurants.toString());
                                    JsonObject rest = nearby_restaurants.get("1").getAsJsonObject();
                                    Log.d("RestZomato", rest.toString());
                                    JsonObject details = rest.get("restaurant").getAsJsonObject();
                                    Log.d("RestZomato", details.toString());
                                    Set<Map.Entry<String, JsonElement>> objects =  nearby_restaurants.entrySet();

                                    for (Map.Entry<String, JsonElement> entry : objects) {
                                        System.out.println(entry.getKey() + "/" + entry.getValue());
                                        if (entry.getValue() != null) {
                                            JsonObject ob=entry.getValue().getAsJsonObject();
                                            if (ob != null) {
                                                //RestaurantDetails is created to read value of "restaurant"
                                                JsonObject restaurant = ob.get("restaurant").getAsJsonObject();
                                                RestaurantDetails rd = new Gson().fromJson(restaurant.toString(), RestaurantDetails.class);
                                                Log.d("Name ",rd.name);
                                                if (rd.getZomatoRating().aggregate_rating != null){
                                                    Log.d("Rest"+rd.name,rd.getZomatoRating().aggregate_rating); }
                                                else {
                                                    Log.d("Rest"+rd.name,"not found");
                                                }
                                                sTrips.add(rd);
                                            }
                                        }
                                    }
                                   // sTrips = (List<RestaurantDetails>) new Gson().fromJson(nearby_restaurants, new TypeToken<List<RestaurantDetails>>() {}.getType());
                                    TheSmartTourist.globalRestInfo = sTrips;
                                    adapter = new RestaurantAdapter(sTrips); recyclerView.setAdapter(adapter);
                                   mSwipeRefreshLayout.setRefreshing(false);
                                     ((RestaurantAdapter) adapter).setOnItemClickListener(new RestaurantAdapter.MyClickListener() {
                                        @Override
                                        public void onItemClick(int position, View v) {
                                            Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                                            i.putExtra("type","rest");
                                            i.putExtra("title",sTrips.get(position).name);
                                            i.putExtra("id",sTrips.get(position).id);
                                            i.putExtra("position",position);
                                            startActivity(i);
                                        }
                                    });
                                }
                            }
                        });
            }

    private void getAroundMe(){
        AroundMeActivity.loggerForAroundMe.logEvent("food around me checked");
        double lat = TheSmartTourist.getUserLat();
        double lon = TheSmartTourist.getUserLon();
        Ion.getDefault(getActivity()).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
        Ion.with(getActivity())
                .load("https://developers.zomato.com/api/v2.1/geocode?lat=" + lat + "&lon=" + lon)
                .setHeader("user-key", "db3e2fc3c465417905f424b0c2aff1b9")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Log.d("ScheduleTripFragment", "Error loading food" + e.toString());
                            Toast.makeText(getActivity(), "error loading food", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Log.d("FoodFragment", result.toString());
                            RestaurantsZomato r = (RestaurantsZomato) new Gson().fromJson(result.toString(), new TypeToken<RestaurantsZomato>() {
                            }.getType());
                            JsonObject nearby_restaurants = result.get("nearby_restaurants").getAsJsonObject();
                            Log.d("RestDetails", nearby_restaurants.toString());
                            JsonObject rest = nearby_restaurants.get("1").getAsJsonObject();
                            Log.d("RestZomato", rest.toString());
                            JsonObject details = rest.get("restaurant").getAsJsonObject();
                            Log.d("RestZomato", details.toString());
                            Set<Map.Entry<String, JsonElement>> objects = nearby_restaurants.entrySet();

                            for (Map.Entry<String, JsonElement> entry : objects) {
                                System.out.println(entry.getKey() + "/" + entry.getValue());
                                if (entry.getValue() != null) {
                                    JsonObject ob = entry.getValue().getAsJsonObject();
                                    if (ob != null) {
                                        //RestaurantDetails is created to read value of "restaurant"
                                        JsonObject restaurant = ob.get("restaurant").getAsJsonObject();
                                        RestaurantDetails rd = new Gson().fromJson(restaurant.toString(), RestaurantDetails.class);
                                        Log.d("Name ", rd.name);
                                        if (rd.getZomatoRating().aggregate_rating != null) {
                                            Log.d("Rest" + rd.name, rd.getZomatoRating().aggregate_rating);
                                        } else {
                                            Log.d("Rest" + rd.name, "not found");
                                        }
                                        sTrips.add(rd);
                                    }
                                }
                            }
                            TheSmartTourist.globalRestInfo = sTrips;
                            adapter = new RestaurantAdapter(sTrips);
                            recyclerView.setAdapter(adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                            ((RestaurantAdapter) adapter).setOnItemClickListener(new RestaurantAdapter.MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                                    i.putExtra("type", "rest");
                                    i.putExtra("title", sTrips.get(position).name);
                                    i.putExtra("id", sTrips.get(position).id);
                                    i.putExtra("position", position);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });

    }

    void sortItemsInList(String sortType){
        switch (sortType){
            case "Name":
                Collections.sort(sTrips, new Comparator<RestaurantDetails>() {
                    public int compare(RestaurantDetails p1, RestaurantDetails p2) {
                        return p1.name.compareToIgnoreCase(p2.name);
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(Low to High)":
                Collections.sort(sTrips, new Comparator<RestaurantDetails>() {
                    public int compare(RestaurantDetails p1, RestaurantDetails p2) {
                        return Double.compare(Double.parseDouble(p1.average_cost_for_two), Double.parseDouble(p2.average_cost_for_two));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(High to Low)":
                Collections.sort(sTrips, new Comparator<RestaurantDetails>() {
                    public int compare(RestaurantDetails p1, RestaurantDetails p2) {
                        return Double.compare(Double.parseDouble(p2.average_cost_for_two), Double.parseDouble(p1.average_cost_for_two));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Rating":
                Collections.sort(sTrips, new Comparator<RestaurantDetails>() {
                    public int compare(RestaurantDetails p1, RestaurantDetails p2) {
                        return Double.compare(Double.parseDouble(p2.user_rating.aggregate_rating), Double.parseDouble(p1.user_rating.aggregate_rating));
                    }
                });
                adapter.notifyDataSetChanged();
                break;
        }
    }

    void filterItemsInList(String filterType){
        //"Indian", "Fast Food", "Chinese", "North Indian", "South Indian"
        sTrips = new ArrayList<>();
        switch (filterType){
            case "Indian":
                for(RestaurantDetails e: TheSmartTourist.globalRestInfo) {
                    if (e.cuisines.contains("Indian")) {
                        sTrips.add(e);
                    }
                }
                adapter = new RestaurantAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Fast Food":
                for(RestaurantDetails e: TheSmartTourist.globalRestInfo) {
                    if (e.cuisines.contains("Fast Food")) {
                        sTrips.add(e);
                    }
                }
                adapter = new RestaurantAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Chinese":
                for(RestaurantDetails e: TheSmartTourist.globalRestInfo) {
                    if (e.cuisines.contains("Chinese")) {
                        sTrips.add(e);
                    }
                }
                adapter = new RestaurantAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "North Indian":
                for(RestaurantDetails e: TheSmartTourist.globalRestInfo) {
                    if (e.cuisines.equals("North Indian")) {
                        sTrips.add(e);
                    }
                }
                adapter = new RestaurantAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "South Indian":
                for(RestaurantDetails e: TheSmartTourist.globalRestInfo) {
                    if (e.cuisines.contains("South Indian")) {
                        sTrips.add(e);
                    }
                }
                adapter = new RestaurantAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "All": getJSON(); break;
        }
    }

}




package majorproject.amity.smarttourist.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import majorproject.amity.smarttourist.AroundMeActivity;
import majorproject.amity.smarttourist.CardViewClickActivity;
import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.adapter.PoiAdapter;
import majorproject.amity.smarttourist.adapter.SimpleItemTouchHelperCallback;
import majorproject.amity.smarttourist.models.Poi;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class POIFragment extends Fragment {
    ImageButton imgButton,imgButton1,imgButton2,imgButton3,imgButton4,imgButton5;
    boolean[] touched = new boolean[6];
    ImageButton btnItinerary;
    List<Poi> sTrips = new ArrayList<>();
    PoiAdapter adapter; RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int status; Button sortBy, filterBy;

    public POIFragment() {
        // Required empty public constructor
    }

    public POIFragment getInstance(int s) {
        status = s;
        return new POIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                popup.getMenuInflater().inflate(R.menu.popup_menu_filter_poi, popup.getMenu());

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

        if(status==1) checkJSON();
        else getAroundMe();
        return v;
//        imgButton = (ImageButton) v.findViewById(R.id.imageButton);
//        imgButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!touched[0])
//                {imgButton.setBackgroundResource(R.drawable.shoppingmallblur);
//                    touched[0] = true;}
//                else {
//                    imgButton.setBackgroundResource(R.drawable.shoppingmall);
//                    touched[0] = false;
//                }
//               // Toast.makeText(getActivity(), "You clicked on Shopping", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        btnItinerary = (ImageButton) v.findViewById(R.id.btnItinerary);
//        btnItinerary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), FinalItinerary.class));
//            }
//        });
//
//        imgButton1 = (ImageButton) v.findViewById(R.id.imageButton2);
//        imgButton1.setOnClickListener(imageButton2Handler);
//        imgButton2 = (ImageButton) v.findViewById(R.id.imageButton3);
//        imgButton2.setOnClickListener(imageButton3Handler);
//        imgButton3 = (ImageButton) v.findViewById(R.id.imageButton4);
//        imgButton3.setOnClickListener(imageButton4Handler);
//        imgButton4 = (ImageButton) v.findViewById(R.id.imageButton5);
//        imgButton4.setOnClickListener(imageButton5Handler);
//        imgButton5 = (ImageButton) v.findViewById(R.id.imageButton6);
//        imgButton5.setOnClickListener(imageButton6Handler);
    }
//    View.OnClickListener imageButton2Handler = new View.OnClickListener() {
//
//        public void onClick(View v) {
//            if (!touched[1]){
//                imgButton1.setBackgroundResource(R.drawable.roadtripblur);
//                touched[1] = true; }
//            else{
//                imgButton1.setBackgroundResource(R.drawable.roadtrip);
//                touched[1] = false;
//            }
//        }
//    };
//    View.OnClickListener imageButton3Handler = new View.OnClickListener() {
//
//        public void onClick(View v) {
//            if (!touched[2]){
//                imgButton2.setBackgroundResource(R.drawable.parkblur);
//                touched[2] = true; }
//            else{
//                imgButton2.setBackgroundResource(R.drawable.park);
//                touched[2] = false; }
//
//        }
//    };
//    View.OnClickListener imageButton4Handler = new View.OnClickListener() {
//
//        public void onClick(View v) {
//            if (!touched[3]){
//                imgButton3.setBackgroundResource(R.drawable.sportblur);
//                touched[3] = true; }
//            else{
//                imgButton3.setBackgroundResource(R.drawable.sport);
//                touched[3] = false; }
//        }
//    };
//    View.OnClickListener imageButton5Handler = new View.OnClickListener() {
//
//        public void onClick(View v) {
//            if (!touched[4]){
//                imgButton4.setBackgroundResource(R.drawable.wildlifeblur);
//                touched[4] = true; }
//            else{
//                imgButton4.setBackgroundResource(R.drawable.wildlife);
//                touched[4] = false; }
//        }
//    };
//    View.OnClickListener imageButton6Handler = new View.OnClickListener() {
//
//        public void onClick(View v) {
//            if (!touched[5]){
//                imgButton5.setBackgroundResource(R.drawable.burjalarabblur);
//                touched[5] = true; }
//            else{
//                imgButton5.setBackgroundResource(R.drawable.burjalarab);
//                touched[5] = false; }
//        }
//    };

    public void checkJSON(){
        String json = null;

        try {
            InputStream is = getActivity().getAssets().open("poi.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            sTrips = (List<Poi>) new Gson().fromJson(json, new TypeToken<List<Poi>>() {}.getType());
            TheSmartTourist.globalPOI = sTrips;
            adapter = new PoiAdapter(sTrips);
            recyclerView.setAdapter(adapter);

            for(Poi e: sTrips){
                Log.d("poi",e.name+" "+e.cost);
            }
            ((PoiAdapter) adapter).setOnItemClickListener(new PoiAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                    i.putExtra("type", "poi");
                    i.putExtra("title", sTrips.get(position).name);
                    i.putExtra("id", sTrips.get(position).idpoi+"");
                    Log.d("test_poi", "" + sTrips.get(position).idpoi);
                    i.putExtra("position", position);
                    startActivity(i);
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

   /* private void getData() {
        Ion.getDefault(getActivity()).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
        Ion.with(getActivity())
                //home 192.168.1.101
                .load("POST","http://192.168.1.101/TheSmartTourist/Service1.svc/getPOI")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            Log.d("inside e!= null", "Error loading POI" + e.toString());
                            Toast.makeText(getActivity(), "Error loading POI", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Log.d("POIFragment", result.toString());
                            sTrips = (List<Poi>) new Gson().fromJson(result.toString(), new TypeToken<List<Poi>>() {}.getType());
                            // sTrips = (List<RestaurantDetails>) new Gson().fromJson(nearby_restaurants, new TypeToken<List<RestaurantDetails>>() {}.getType());
                            TheSmartTourist.globalPOI = sTrips;
                            adapter = new PoiAdapter(sTrips);
                            recyclerView.setAdapter(adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                            ((PoiAdapter) adapter).setOnItemClickListener(new PoiAdapter.MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                                            i.putExtra("type","poi");
                                            i.putExtra("title",sTrips.get(position).name);
                                            i.putExtra("id",sTrips.get(position).idpoi);
                                            i.putExtra("position",position);
                                            startActivity(i);
                                }
                            });
                        }
                    }
                });
    } */

    private void getAroundMe(){
        sTrips = new ArrayList<>();
        double lat = TheSmartTourist.getUserLat();
        double lon = TheSmartTourist.getUserLon();

        Bundle parameters = new Bundle();
        parameters.putDouble("Latitude",lat);
        parameters.putDouble("Longitude",lon);
        AroundMeActivity.loggerForAroundMe.logEvent("poi around me checked",parameters);

        Location start = new Location("");
        start.setLatitude(lat); start.setLongitude(lon);
        for(Poi element: TheSmartTourist.globalPOI){
            double latNew = element.lat;
            double lonNew = element.lon;
            Location end = new Location("");
            end.setLatitude(latNew); end.setLongitude(lonNew);
            float dist = start.distanceTo(end);
            Log.d("test_distance", "distance is:" + dist / 1000);
            if(dist<8000){
                sTrips.add(element);
            }
            adapter = new PoiAdapter(sTrips);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    void sortItemsInList(String sortType){
        switch (sortType){
            case "Name":
                Collections.sort(sTrips, new Comparator<Poi>() {
                    public int compare(Poi p1, Poi p2) {
                        return p1.name.compareToIgnoreCase(p2.name);
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(Low to High)":
                Collections.sort(sTrips, new Comparator<Poi>() {
                    public int compare(Poi p1, Poi p2) {
                        String[] result1 = p1.cost.split(";");
                        String[] result2 = p2.cost.split(";");
                        return Double.compare(Double.parseDouble(result1[0]), Double.parseDouble(result2[0]));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(High to Low)":
                Collections.sort(sTrips, new Comparator<Poi>() {
                    public int compare(Poi p1, Poi p2) {
                        String[] result1 = p1.cost.split(";");
                        String[] result2 = p2.cost.split(";");
                        return Double.compare(Double.parseDouble(result2[0]), Double.parseDouble(result1[0]));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Rating":
                Collections.sort(sTrips, new Comparator<Poi>() {
                    public int compare(Poi p1, Poi p2) {
                        return Double.compare(p2.rating, p1.rating);
                    }
                });
                adapter.notifyDataSetChanged();
                break;
        }
    }

    void filterItemsInList(String filterType) {
        sTrips = new ArrayList<>();
        switch (filterType){
            case "Shopping":
                for(Poi e: TheSmartTourist.globalPOI) {
                    if (e.type.equals("Shopping")) {
                        sTrips.add(e);
                    }
                }
                adapter = new PoiAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Sightseeing":
                for(Poi e: TheSmartTourist.globalPOI){
                    if(e.type.equals("Sightseeing")){
                        sTrips.add(e);
                    }
                }
                adapter = new PoiAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Activities":
                for(Poi e: TheSmartTourist.globalPOI){
                    if(e.type.equals("Activities")){
                        sTrips.add(e);
                    }
                }
                adapter = new PoiAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Culture":
                for(Poi e: TheSmartTourist.globalPOI){
                    if(e.type.equals("Culture")){
                        sTrips.add(e);
                    }
                }
                adapter = new PoiAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Parks and Beaches":
                for(Poi e: TheSmartTourist.globalPOI){
                    if(e.type.equals("Parks and Beaches")){
                        sTrips.add(e);
                    }
                }
                adapter = new PoiAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "All": checkJSON(); break;
        }
    }
}

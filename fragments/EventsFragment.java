package majorproject.amity.smarttourist.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

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

import majorproject.amity.smarttourist.CardViewClickActivity;
import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.adapter.EventsAdapter;
import majorproject.amity.smarttourist.models.Events;
import majorproject.amity.smarttourist.models.Poi;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class EventsFragment extends Fragment {

    List<Events> sTrips = new ArrayList<>();
    EventsAdapter adapter; RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int status; Button sortBy, filterBy;
    public EventsFragment() {
        // Required empty public constructor
    }

    public EventsFragment getInstance(int s) {
        status = s;
        return new EventsFragment();
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
                        Toast.makeText(getActivity(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
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
                popup.getMenuInflater().inflate(R.menu.popup_menu_filter_events, popup.getMenu());

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
        return v;
    }

    private void checkJSON(){
        String json;
        try {
            InputStream is = getActivity().getAssets().open("events.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            sTrips = (List<Events>) new Gson().fromJson(json, new TypeToken<List<Events>>() {}.getType());

            TheSmartTourist.globalEvents = sTrips;
            adapter = new EventsAdapter(sTrips);
            recyclerView.setAdapter(adapter);
            ((EventsAdapter) adapter).setOnItemClickListener(new EventsAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                    i.putExtra("type", "events");
                    i.putExtra("title", sTrips.get(position).name);
                    i.putExtra("id", sTrips.get(position).id+"");
                    i.putExtra("position", position);
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
                .load("POST","http://10.8.4.103/TheSmartTourist/Service1.svc/getEvents")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            Log.d("EventsFragment", "Error loading events" + e.toString());
                            Toast.makeText(getActivity(), "Error loading events", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Log.d("EventFragment", result.toString());
                            sTrips = (List<Events>) new Gson().fromJson(result.toString(), new TypeToken<List<Events>>() {
                            }.getType());
                            // sTrips = (List<RestaurantDetails>) new Gson().fromJson(nearby_restaurants, new TypeToken<List<RestaurantDetails>>() {}.getType());
                            TheSmartTourist.globalEvents = sTrips;
                            adapter = new EventsAdapter(sTrips);
                            recyclerView.setAdapter(adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                            ((EventsAdapter) adapter).setOnItemClickListener(new EventsAdapter.MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                                            i.putExtra("type","events");
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

    void sortItemsInList(String sortType){
        switch (sortType){
            case "Name":
                Collections.sort(sTrips, new Comparator<Events>() {
                    public int compare(Events p1, Events p2) {
                        return p1.name.compareToIgnoreCase(p2.name);
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(Low to High)":
                Collections.sort(sTrips, new Comparator<Events>() {
                    public int compare(Events p1, Events p2) {
                        return Double.compare(Double.parseDouble(p1.admission), Double.parseDouble(p2.admission));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(High to Low)":
                Collections.sort(sTrips, new Comparator<Events>() {
                    public int compare(Events p1, Events p2) {
                        return Double.compare(Double.parseDouble(p2.admission), Double.parseDouble(p1.admission));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Rating":break;
        }
    }

    void filterItemsInList(String filterType) {
        sTrips = new ArrayList<>();
        switch (filterType){
            case "Lifestyle":
                for(Events e: TheSmartTourist.globalEvents) {
                    if (e.category.contains("Lifestyle")) {
                        sTrips.add(e);
                    }
                }
                adapter = new EventsAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Live Entertainment":
                for(Events e: TheSmartTourist.globalEvents) {
                    if (e.category.contains("Live Entertainment")) {
                        sTrips.add(e);
                    }
                }
                adapter = new EventsAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Family":
                for(Events e: TheSmartTourist.globalEvents) {
                    if (e.category.contains("Family")) {
                        sTrips.add(e);
                    }
                }
                adapter = new EventsAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Sports":
                for(Events e: TheSmartTourist.globalEvents) {
                    if (e.category.contains("Sports")) {
                        sTrips.add(e);
                    }
                }
                adapter = new EventsAdapter(sTrips);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "All": checkJSON(); break;
        }
    }
}

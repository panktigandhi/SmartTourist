package majorproject.amity.smarttourist.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import majorproject.amity.smarttourist.AroundMeActivity;
import majorproject.amity.smarttourist.CardViewClickActivity;
import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.adapter.HotelAdapter;
import majorproject.amity.smarttourist.models.HotelInfo;
import majorproject.amity.smarttourist.models.Hotels;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class HotelFragment extends Fragment {

    List<HotelInfo> hotelInfo = new ArrayList<>();
    HotelAdapter adapter; RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int status; Button sortBy, filterBy;

    public HotelFragment() {
        // Required empty public constructor
    }

    public HotelFragment getInstance(int s) {
        status = s;
        return new HotelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                popup.getMenuInflater().inflate(R.menu.popup_menu_filter_hotel, popup.getMenu());

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
                getData();
            }
        });

          if(TheSmartTourist.AppBarOffset)
                    mSwipeRefreshLayout.setEnabled(true);
            else
                    mSwipeRefreshLayout.setEnabled(false);

        if(status==1)getJSON();
        else getAroundMe();
        return v;
    }

    private void getJSON(){
        String json = null;

        try {
            InputStream is = getActivity().getAssets().open("hotel.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JsonParser p = new JsonParser();
            JsonObject jsonObject = p.parse(json).getAsJsonObject();
            JsonArray jsonArray = (jsonObject.getAsJsonObject("HotelInfoList")).getAsJsonArray("HotelInfo");
            Log.d("HotelsFragment",jsonArray.toString());
            hotelInfo = (List<HotelInfo>) new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HotelInfo>>() {}.getType());
            for (HotelInfo h :hotelInfo) {
            Log.d("HotelsFragment",h.HotelID+" "+h.StarRating); }
            TheSmartTourist.globalHotelInfo = hotelInfo;

            adapter = new HotelAdapter(hotelInfo);
            recyclerView.setAdapter(adapter);
            mSwipeRefreshLayout.setRefreshing(false);
            ((HotelAdapter) adapter).setOnItemClickListener(new HotelAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                    i.putExtra("type","hotel");
                    i.putExtra("position",position);
                    i.putExtra("title",hotelInfo.get(position).Name);
                    i.putExtra("id",hotelInfo.get(position).HotelID);
                    startActivity(i);
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getData() {
        //apikey: Lp0jnTXDA2jhKwDFXA22kGu5A6quQAg3
        Ion.getDefault(getActivity()).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
        Ion.with(getActivity())
                .load("http://terminal2.expedia.com:80/x/hotels?maxhotels=10&location=25.260693%2C55.295853&radius=5km&apikey=Lp0jnTXDA2jhKwDFXA22kGu5A6quQAg3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            Hotels sTrips = (Hotels) new Gson().fromJson(result.toString(), new TypeToken<Hotels>() {}.getType());
                            JsonArray a = (result.getAsJsonObject("HotelInfoList")).getAsJsonArray("HotelInfo");
                            hotelInfo = (List<HotelInfo>) new Gson().fromJson(a.toString(), new TypeToken<List<HotelInfo>>() {}.getType());
                            TheSmartTourist.globalHotelInfo = hotelInfo;
                            Log.d("HotelsFragment",a.toString());
                            adapter = new HotelAdapter(hotelInfo);
                            recyclerView.setAdapter(adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                            ((HotelAdapter) adapter).setOnItemClickListener(new HotelAdapter.MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                   Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                                    i.putExtra("type","hotel");
                                    i.putExtra("position",position);
                                    i.putExtra("title",hotelInfo.get(position).Name);
                                    i.putExtra("id",hotelInfo.get(position).HotelID);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
    }

    public void getAroundMe(){

        AroundMeActivity.loggerForAroundMe.logEvent("hotel around me checked");

       /* String loc = TheSmartTourist.getUserLat()+"%2C"+TheSmartTourist.getUserLon();
        Ion.getDefault(getActivity()).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
        Ion.with(getActivity())
                .load("http://terminal2.expedia.com:80/x/hotels?maxhotels=10&location="+loc+"&radius=5km&apikey=Lp0jnTXDA2jhKwDFXA22kGu5A6quQAg3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Hotels sTrips = (Hotels) new Gson().fromJson(result.toString(), new TypeToken<Hotels>() {
                            }.getType());
                            JsonArray a = (result.getAsJsonObject("HotelInfoList")).getAsJsonArray("HotelInfo");
                            hotelInfo = (List<HotelInfo>) new Gson().fromJson(a.toString(), new TypeToken<List<HotelInfo>>() {}.getType());
                            Log.d("HotelsFragment", a.toString());
                            adapter = new HotelAdapter(hotelInfo);
                            recyclerView.setAdapter(adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                            ((HotelAdapter) adapter).setOnItemClickListener(new HotelAdapter.MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getActivity(), CardViewClickActivity.class);
                                    i.putExtra("type", "hotel");
                                    i.putExtra("position", position);
                                    i.putExtra("title", hotelInfo.get(position).Name);
                                    i.putExtra("id", hotelInfo.get(position).HotelID);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
*/
    }

    void sortItemsInList(String sortType){
        switch (sortType){
            case "Name":
                Collections.sort(hotelInfo, new Comparator<HotelInfo>() {
                    public int compare(HotelInfo p1, HotelInfo p2) {
                        return p1.Name.compareToIgnoreCase(p2.Name);
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            /*case "Cost(Low to High)":
                Collections.sort(hotelInfo, new Comparator<HotelInfo>() {
                    public int compare(HotelInfo p1, HotelInfo p2) {
                        return Double.compare(Double.parseDouble(p1.), Double.parseDouble(p2.average_cost_for_two));
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case "Cost(High to Low)":
                Collections.sort(hotelInfo, new Comparator<HotelInfo>() {
                    public int compare(RestaurantDetails p1, RestaurantDetails p2) {
                        return Double.compare(Double.parseDouble(p2.average_cost_for_two), Double.parseDouble(p1.average_cost_for_two));
                    }
                });
                adapter.notifyDataSetChanged();
                break;*/

            case "Rating":
                Collections.sort(hotelInfo, new Comparator<HotelInfo>() {
                    public int compare(HotelInfo p1, HotelInfo p2) {
                        return Double.compare(Double.parseDouble(p2.GuestRating), Double.parseDouble(p1.GuestRating));
                    }
                });
                adapter.notifyDataSetChanged();
                break;
        }
    }

    void filterItemsInList(String filterType) {
        hotelInfo = new ArrayList<>();
        switch (filterType){
            case "3 star":
                for(HotelInfo e: TheSmartTourist.globalHotelInfo) {
                    if (Double.parseDouble(e.StarRating)==3.0) {
                        hotelInfo.add(e);
                    }
                }
                adapter = new HotelAdapter(hotelInfo);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case "4 star":
                for(HotelInfo e: TheSmartTourist.globalHotelInfo) {
                    if (Double.parseDouble(e.StarRating)==4.0) {
                        hotelInfo.add(e);
                    }
                }
                adapter = new HotelAdapter(hotelInfo);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case "5 star":
                for(HotelInfo e: TheSmartTourist.globalHotelInfo) {
                    if (Double.parseDouble(e.StarRating)==5.0) {
                        hotelInfo.add(e);
                    }
                }
                adapter = new HotelAdapter(hotelInfo);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Any":
                getJSON(); break;
        }
    }

}

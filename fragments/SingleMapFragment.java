package majorproject.amity.smarttourist.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.utils.MyTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    LatLng end;
    int status;
    String name;

    public SingleMapFragment() {
        // Required empty public constructor
    }

    public SingleMapFragment getInstance(int s, LatLng e, String n) {
        status = s;
        end = e;
        Log.d("mapDebug",e.latitude+" "+e.longitude);
        name = n;
        return new SingleMapFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_maps, container, false);
        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            Log.d("mapDebug","came here");
            mapFragment = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.map, mapFragment);
            fragmentTransaction.commit();
        }
        mapFragment.getMapAsync(this);
        return v;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("latlon", "inside on map ready");
        if (map == null) {
            Toast.makeText(getActivity(), "Sorry Mapping Not Supported with device", Toast.LENGTH_LONG).show();
        } else {
            map.setMyLocationEnabled(true);
            mMap = map;
            load();
        }
    }

    public void load() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(end)      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                        // Sets the tilt of the camera to 30 degrees .tilt(40)
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Log.d("status-fragment", status + "");
        Marker marker;
        if (status == 0)
        {   Log.d("mapDebug","inside hotel");
            marker = mMap.addMarker(new MarkerOptions()
                    .position(end)
                    .title(name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_marker)));}
        else if (status == 1)
        {   Log.d("mapDebug","inside marker");
            marker = mMap.addMarker(new MarkerOptions()
                    .position(end)
                    .title(name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rest_marker)));}
        else if (status == 2)
        {   Log.d("mapDebug","inside poi");
            marker = mMap.addMarker(new MarkerOptions()
                    .position(end)
                    .title(name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker)));}
        /*
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return getInfoContents(arg0);
                //View vMapInfo = ((MapsActivity.this).getLayoutInflater().inflate(R.layout.map_info_layout, null));
                //return vMapInfo;
            }

            @Override
            public View getInfoContents(Marker arg0) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.map_info_layout, null);
                t1 = (MyTextView) v.findViewById(R.id.VehicleIDView2);
                t2 = (MyTextView) v.findViewById(R.id.DriverView2);
                t3 = (MyTextView) v.findViewById(R.id.LocationView2);
                String[] parts = display.split("\\\n");
                t1.setText(parts[0]);
                t2.setText(parts[1]);
                t3.setText(parts[2]);
                return v;
            }
        });
        */
        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return true;
    }

}


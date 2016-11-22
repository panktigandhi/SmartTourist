package majorproject.amity.smarttourist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;
import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.adapter.ItineraryAdapter;
import majorproject.amity.smarttourist.adapter.SimpleItemTouchHelperCallback;
import majorproject.amity.smarttourist.models.Poi;
import majorproject.amity.smarttourist.utils.ItineraryCreator;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class ItineraryDisplayFragment extends Fragment {

    List<String> interests; float pace_preference;
    ItineraryAdapter adapter; RecyclerView recyclerView;
    ItemTouchHelper touchHelper;
    Button prev, next;

    public ItineraryDisplayFragment() {
        // Required empty public constructor
    }

    public ItineraryDisplayFragment getInstance(float p, List<String> n) {
        pace_preference = p;
        interests = n;
        return new ItineraryDisplayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_itinerary_display, container, false);

        prev = (Button) v.findViewById(R.id.prevDayButton);
        next = (Button) v.findViewById(R.id.nextDayButton);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerItinerary);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ItineraryCreator k = new ItineraryCreator();
        k.interestsSelected = interests;
        //List<Poi> a = k.createItinerary();
        List<Poi> a = TheSmartTourist.globalPOI;
        adapter = new ItineraryAdapter(a);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to prev day
                Toast.makeText(getActivity(),"Go to prev",Toast.LENGTH_LONG).show();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to next day
                Toast.makeText(getActivity(),"Go to next",Toast.LENGTH_LONG).show();
            }
        });


        return v;
    }

//    @Override
//    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        touchHelper.startDrag(viewHolder);
//    }





}

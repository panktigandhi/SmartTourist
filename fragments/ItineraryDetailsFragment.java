package majorproject.amity.smarttourist.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.utils.MyTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItineraryDetailsFragment extends Fragment {

    SeekBar pace; ImageButton activities, culture, shopping, sightseeing, parks;
    //activities, culture, shopping, sightseeing, parks;
    List<String> interest = new ArrayList<>(); float pace_preference;
    MyTextView pace_string;
    ImageButton next;
    String from,to; EditText budget;
    boolean[] touched = new boolean[6];

    public ItineraryDetailsFragment() {
        // Required empty public constructor
    }

    public ItineraryDetailsFragment getInstance(String f, String t) {
        from = f; to = t;
        return new ItineraryDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.itinerary_details_layout, container, false);

        pace = (SeekBar) v.findViewById(R.id.paceSlider);
//        activities = (SeekBar) v.findViewById(R.id.activitiesSlider);
//        culture = (SeekBar) v.findViewById(R.id.cultureSlider);
//        shopping = (SeekBar) v.findViewById(R.id.shoppingSlider);
//        sightseeing = (SeekBar) v.findViewById(R.id.sightseeingSlider);
//        parks = (SeekBar) v.findViewById(R.id.parksSlider);
        budget = (EditText) v.findViewById(R.id.txtBudget);
        pace_string = (MyTextView) v.findViewById(R.id.paceText);
        next = (ImageButton) v.findViewById(R.id.btnSubmit);
        shopping = (ImageButton) v.findViewById(R.id.shopping);
        sightseeing = (ImageButton) v.findViewById(R.id.sightseeing);
        activities = (ImageButton) v.findViewById(R.id.activities);
        culture = (ImageButton) v.findViewById(R.id.culture);
        parks = (ImageButton) v.findViewById(R.id.parks);

        setListeners();
        return v;
    }

    void setListeners(){
        pace.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar.getProgress() > 0 && seekBar.getProgress() < 5) {
                    pace_string.setText("Low");
                    pace_preference = 0;
                } else if (seekBar.getProgress() > 5 && seekBar.getProgress() < 10) {
                    pace_string.setText("Medium");
                    pace_preference = 0.5f;
                } else if (seekBar.getProgress() == 10) {
                    pace_string.setText("High");
                    pace_preference = 1;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

   /* activities.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if(seekBar.getProgress()>0){
                //if(!interests.contains("Activities"))
                    interests+="Activities,";
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });
        sightseeing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar.getProgress() > 0) {
                    //if(!interests.contains("Sightseeing"))
                        interests+="Sightseeing,";
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        shopping.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekBar.getProgress()>0){
                    //if(!interests.contains("Shopping"))
                        interests+="Shopping,";
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        culture.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekBar.getProgress()>0){
                    //if(!interests.contains("Culture"))
                        interests+="Culture,";
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        parks.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekBar.getProgress()>0){
                    //if(!interests.contains("Parks"))
                        interests+="Parks,";
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });*/

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[2]) {
                    shopping.setBackgroundResource(R.drawable.webview_2);
                    interest.add("Shopping");
                    touched[2] = true;
                } else {
                    shopping.setBackgroundResource(R.drawable.webview_1);
                    interest.remove("Shopping");
                    touched[2] = false;
                }
            }
        });
        sightseeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[0]) {
                    sightseeing.setBackgroundResource(R.drawable.webview_1);
                    interest.add("Sightseeing");
                    touched[0] = true;
                } else {
                    sightseeing.setBackgroundResource(R.drawable.webview_1);
                    interest.remove("Sightseeing");
                    touched[0] = false;
                }
            }
        });
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[1]) {
                    activities.setBackgroundResource(R.drawable.webview_2);
                    interest.add("Activities");
                    touched[1] = true;
                } else {
                    activities.setBackgroundResource(R.drawable.webview_2);
                    interest.remove("Activities");
                    touched[1] = false;
                }
            }
        });
        culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[3]){
                    culture.setBackgroundResource(R.drawable.webview_4);
                    interest.add("Sports");
                    touched[3] = true; }
                else{
                    culture.setBackgroundResource(R.drawable.webview_4);
                    interest.remove("Sports");
                    touched[3] = false;
                }}
        });

        parks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!touched[4]) {
                    parks.setBackgroundResource(R.drawable.webview_6);
                    interest.add("Park");
                    touched[4] = true;
                } else {
                    parks.setBackgroundResource(R.drawable.webview_6);
                    interest.remove("Park");
                    touched[4] = false;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interest.size()!=0 && budget.getText().toString().trim().length() > 0) {
                ItineraryDisplayFragment a = new ItineraryDisplayFragment();
                a.getInstance(pace_preference,interest);
                FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                fragTransaction.replace(R.id.replaceFragment,a, "ItineraryDisplay Fragment");
                fragTransaction.addToBackStack(null);
                fragTransaction.commit(); }
            }
        });

}

}

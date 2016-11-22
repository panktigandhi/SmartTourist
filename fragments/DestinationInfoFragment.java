package majorproject.amity.smarttourist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.utils.MyTextView;

public class DestinationInfoFragment extends Fragment {

    int type, value; MyTextView text;

    public DestinationInfoFragment() {
    }

    public DestinationInfoFragment getInstance(int t, int val){
        type = t; value = val;
        return new DestinationInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text_info, container, false);
        text = (MyTextView) v.findViewById(R.id.txtView);
        switch(type){
            case 1:
                text.setText("inside a good place");
                break;
            case 2: break;
            case 3: break;
        }
        return v;
    }

}

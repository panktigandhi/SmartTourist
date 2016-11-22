package majorproject.amity.smarttourist.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.adapter.ImageAdapter;


public class DestinationGalleryFragment extends Fragment {
    ImageAdapter myImageAdapter;
    int numOfImages = 4;
    int placeNumber = 1;

    public DestinationGalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_destination_gallery, container, false);
        GridView gridview = (GridView) v.findViewById(R.id.gridview);
        myImageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(myImageAdapter);

//        String ExternalStorageDirectoryPath = Environment
//                .getExternalStorageDirectory()
//                .getAbsolutePath();
//
//        String targetPath = ExternalStorageDirectoryPath + "/test/";
//
//        Toast.makeText(getActivity(), targetPath, Toast.LENGTH_LONG).show();
//        File targetDirector = new File(targetPath);
//
//        File[] files = targetDirector.listFiles();
//        for (File file : files){
//            myImageAdapter.add(file.getAbsolutePath());
//        }
        for (int i=1; i<= numOfImages; i++){
            //myImageAdapter.add("place_"+placeNumber+"_"+i);
            myImageAdapter.add("news_"+i);
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prompt = (String)parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), prompt, Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

}

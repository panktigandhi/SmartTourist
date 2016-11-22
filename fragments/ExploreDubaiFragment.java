package majorproject.amity.smarttourist.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.TextInfoActivity;

public class ExploreDubaiFragment extends Fragment {

    Button n1,n2,n3,n4,n5,w1,w2,w3,w4,w5,w6,w7,w8;
    ListView general, practical;

    public ExploreDubaiFragment() {
        // Required empty public constructor
    }

    public ExploreDubaiFragment getInstance(){
        return new ExploreDubaiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_explore_dubai, container, false);
        general = (ListView) v.findViewById(R.id.listGeneral);
        practical = (ListView) v.findViewById(R.id.listPractical);
        n1 = (Button) v.findViewById(R.id.news1); n2 = (Button) v.findViewById(R.id.news2);
        n3 = (Button) v.findViewById(R.id.news3); n4 = (Button) v.findViewById(R.id.news4);
        n5 = (Button) v.findViewById(R.id.news5);
        w1 = (Button) v.findViewById(R.id.web1); w2 = (Button) v.findViewById(R.id.web2);
        w3 = (Button) v.findViewById(R.id.web3); w4 = (Button) v.findViewById(R.id.web4);
        w5 = (Button) v.findViewById(R.id.web5); w6 = (Button) v.findViewById(R.id.web6);
        w7 = (Button) v.findViewById(R.id.web7); w8 = (Button) v.findViewById(R.id.web8);
        setUpList();
        setListeners();
        return v;
    }

    public void setUpList(){
        String[] values = new String[] { "Introduction", "Etymology", "Geography", "Climate", "Population", "Culture" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
        general.setAdapter(adapter);
        setListViewHeightBasedOnChildren(general);
        general.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInfo(2,position);
            }
        });

        String[] values1 = new String[] { "Getting Around", "Dress Code", "Good to Know"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values1);
        practical.setAdapter(adapter1);
        setListViewHeightBasedOnChildren(practical);
        practical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInfo(3,position);
            }
        });
    }

    public void setListeners(){

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(1, 1);
            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(1,2);
            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(1, 3);
            }
        });
        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(1, 4);
            }
        });
        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(1, 5);
            }
        });
        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=260-dubai-museum-05-entrance-to-galleries&l=en");
            }
        });
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=3053-al-mamzar-beach-park-corniche-3&l=en");
            }
        });
        w3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=2452-burj-al-arab-entrance-outside&l=en");
            }
        });
        w4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=2463-atlantis-the-palm-hotel-lobby-south&l=en");
            }
        });
        w5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=1520-dubai-mall-level-1-fashion-avenue-outside-etro&l=en");
            }
        });
        w6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=1286-jumeirah-mosque-exterior-front&l=en");
            }
        });
        w7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=314-mall-of-the-emirates-18&l=en");
            }
        });
        w8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView("http://dubai360.com/#!s=2513-mercato-shopping-mall-outside&l=en");
            }
        });
    }

    public void showWebView(String url){
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.getInstance(url);
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                    fragTransaction.replace(R.id.replaceFragment,webViewFragment, "WebView Fragment");
                    fragTransaction.addToBackStack(null);
                    fragTransaction.commit();
    }


    public void showInfo(int type, int k){
        //type = 1:news ; 2:general ; 3:practical
//        DestinationInfoFragment destInfoFragment = new DestinationInfoFragment();
//        textInfoFragment.getInstance(type,k);
//        FragmentTransaction fragTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragTransaction.replace(R.id.replaceFragment,textInfoFragment,"TextInfo Fragment");
//        fragTransaction.addToBackStack(null);
//        fragTransaction.commit();

        Intent i = new Intent(getActivity(), TextInfoActivity.class);
        i.putExtra("type",type); i.putExtra("value",k);
        startActivity(i);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.MATCH_PARENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}

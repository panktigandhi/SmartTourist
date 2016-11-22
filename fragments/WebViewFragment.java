package majorproject.amity.smarttourist.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import majorproject.amity.smarttourist.ContainerActivity;
import majorproject.amity.smarttourist.DestinationReachedActivity;
import majorproject.amity.smarttourist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    WebView wv1; String url;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public WebViewFragment getInstance(String urlToDisplay) {
        url = urlToDisplay;
        return new WebViewFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        ((DestinationReachedActivity) getActivity()).getSupportActionBar().hide();
        wv1 = (WebView) v.findViewById(R.id.webView);
        //   wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);

        return v;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}

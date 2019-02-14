package edu.temple.browser;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Tabs extends Fragment {
    View view;
    WebView webView;


    //ViewPager viewPager;
    onUrlChange callBack;
    public static final String TAG = "FragmentBrowser";


    public Tabs() {
        // Required empty public constructor
    }


    public interface onUrlChange{
        public void onUrlChange(String url);
    }


    public static final String ARG_OBJECT = "object";



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onUrlChange) {
            callBack = (onUrlChange) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //You can enable JavaScript in your browser by making a call to:
        //webView.getSettings().setJavaScriptEnabled(true);
        view =inflater.inflate(R.layout.fragment_tabs, container, false);

        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        Log.i(TAG, "Created new fragment view.");
        return view;
    }

    public String goBacktoHistory(){
        if(webView.canGoBack()) {
            webView.goBack();
        }

        return webView.getUrl();
    }
    public String goForwardtoHistory(){
        if(webView.canGoForward()) {
            webView.goForward();
        }
        return webView.getUrl();
    }
    public String getCurrentURL(){
        return webView.getUrl();
    }
    public void refreshURL(String tmpUrl){
        webView.loadUrl(tmpUrl);
        Log.i(TAG, "Attempted refreshURL.");
    }
    public void reloadWebview(){
        webView.reload();
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }
}

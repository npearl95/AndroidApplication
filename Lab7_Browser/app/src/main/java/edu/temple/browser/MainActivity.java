package edu.temple.browser;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements Tabs.onUrlChange{
    public static final String TAG = "FragmentBrowser";
    Toolbar toolbar;
    EditText urlEditText;
    List<Tabs> tabList = new ArrayList<Tabs>();
    int currentTab= -1;


    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        urlEditText = findViewById(R.id.urlEditText);
        View.OnFocusChangeListener ofcListener = new FocusChangeListener();
        urlEditText.setOnFocusChangeListener(ofcListener);

        //Added Go Button
        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Go button has been clicked.");
                try {
                    String tmpUrl = urlEditText.getText().toString();
                    if(!(tmpUrl.startsWith("http"))){
                        tmpUrl = "http://" + tmpUrl;
                        urlEditText.setText(tmpUrl);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("url", tmpUrl);
                    //if no tabs are created (app just started), create a new tab
                    //if tabs exist already, get reference to currently displayed tab
                    //send URL to tab to display
                    if(tabList.size()==0) {
                        final Tabs webFragment = new Tabs();
                        final String finalURL = tmpUrl;
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, webFragment)
                                .runOnCommit(new Runnable() {
                                    @Override
                                    public void run() {
                                        webFragment.refreshURL(finalURL);
                                    }
                                })
                                .commit();
                        tabList.add(webFragment);
                        currentTab++;
                        Log.d(TAG, "currentTab: " + currentTab);
                    }else{
                        tabList.get(currentTab).refreshURL(tmpUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //Added Pre button
        findViewById(R.id.preButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabList.get(currentTab).goBacktoHistory();
                //final String tempURL=tabList.get(currentTab).getCurrentURL();
                //urlEditText.setText(tempURL);
            }
        });


        //Added Next button
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabList.get(currentTab).goForwardtoHistory();
                //final String tempURL=tabList.get(currentTab).getCurrentURL();
                //urlEditText.setText(tempURL);
            }


        });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();

        switch(itemSelected){
            case R.id.action_previous:
                if(currentTab > 0) {
                    currentTab=currentTab-1;
                    final String finalURL = tabList.get(currentTab).getCurrentURL();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, tabList.get(currentTab))
                            .runOnCommit(new Runnable() {
                                @Override
                                public void run() {
                                    tabList.get(currentTab).refreshURL(finalURL);
                                }
                            })
                            .commit();
                    urlEditText.setText(finalURL);
                    Log.d(TAG, "currentTab: " + currentTab);

                }


                break;

            case R.id.action_new:
                urlEditText.setText("");
                //createNewTab();
                Tabs webFragment = new Tabs();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, webFragment)
                        .addToBackStack(null)
                        .commit();
                tabList.add(webFragment);
                currentTab++;
                Log.d(TAG, "action_new currentTab: " + currentTab);




                break;

            case R.id.action_next:
                if(currentTab < tabList.size()-1){
                    currentTab=currentTab+1;
                    final String finalURL = tabList.get(currentTab).getCurrentURL();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, tabList.get(currentTab))
                            .runOnCommit(new Runnable() {
                                @Override
                                public void run() {
                                    tabList.get(currentTab).refreshURL(finalURL);
                                }
                            })
                            .commit();
                    urlEditText.setText(finalURL);
                    Log.d(TAG, "currentTab: " + currentTab);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUrlChange(String url) {
        urlEditText.setText(url);
    }

    //hide virtual keyboard when edit text not in focus
    private class FocusChangeListener implements View.OnFocusChangeListener {
        public void onFocusChange(View v, boolean hasFocus){
            if(v.getId() == R.id.urlEditText && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}
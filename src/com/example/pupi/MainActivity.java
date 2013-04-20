package com.example.pupi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

/**
 * This demonstrates the use of action bar tabs and how they interact
 * with other action bar features.
 */
public class MainActivity extends Activity {
   
    private FragmentTabHost tHost;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        tHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tHost.setup(this, getFragmentManager(), R.id.);
 
        /** Defining Tab Change Listener event. This is invoked when tab is changed */
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
 
            @Override
            public void onTabChanged(String tabId) {
                FragmentManager fm =   getFragmentManager();
                MapDisplayFragment findFragment = (MapDisplayFragment) fm.findFragmentByTag("find");
                ListDisplayFragment historyFragment = (ListDisplayFragment) fm.findFragmentByTag("history");
                ListDisplayFragment messageFragment = (ListDisplayFragment) fm.findFragmentByTag("message");
                ListDisplayFragment Fragment = (ListDisplayFragment) fm.findFragmentByTag("history");
                FragmentTransaction ft = fm.beginTransaction();
 
                /** Detaches the androidfragment if exists */
                if(findFragment!=null)
                    ft.detach(findFragment);
 
                /** Detaches the applefragment if exists */
                if(historyFragment!=null)
                    ft.detach(historyFragment);
 
                /** If current tab is android */
                if(tabId.equalsIgnoreCase("android")){
 
                    if(findFragment==null){
                        /** Create AndroidFragment and adding to fragmenttransaction */
                        ft.add(R.id.realtabcontent,new MapDisplayFragment(), "android");
                    }else{
                        /** Bring to the front, if already exists in the fragmenttransaction */
                        ft.attach(findFragment);
                    }
 
                }else{    /** If current tab is apple */
                    if(historyFragment==null){
                        /** Create AppleFragment and adding to fragmenttransaction */
                        ft.add(R.id.realtabcontent,new ListDisplayFragment(), "apple");
                     }else{
                        /** Bring to the front, if already exists in the fragmenttransaction */
                        ft.attach(historyFragment);
                    }
                }
                ft.commit();
            }
        };
 
        /** Setting tabchangelistener for the tab */
        tHost.setOnTabChangedListener(tabChangeListener);
 
        /** Defining tab builder for Andriod tab */
        TabHost.TabSpec tSpecFind = tHost.newTabSpec("find");
        tSpecFind.setIndicator("Find",getResources().getDrawable(android.R.drawable.ic_dialog_map));
//         tSpecAndroid.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecFind);
 
        /** Defining tab builder for Apple tab */
        TabHost.TabSpec tSpecHistory = tHost.newTabSpec("history");
        tSpecHistory.setIndicator("History",getResources().getDrawable(android.R.drawable.ic_dialog_info));
//        tSpecApple.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecHistory);
 
        /** Defining tab builder for Apple tab */
        TabHost.TabSpec tSpecMessage = tHost.newTabSpec("message");
        tSpecMessage.setIndicator("History",getResources().getDrawable(android.R.drawable.ic_dialog_info));
//        tSpecApple.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecMessage);
 
        /** Defining tab builder for Apple tab */
        TabHost.TabSpec tSpecAccount = tHost.newTabSpec("account");
        tSpecAccount.setIndicator("History",getResources().getDrawable(android.R.drawable.ic_dialog_info));
//        tSpecApple.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecAccount);
 
       
    }


}

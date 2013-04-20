package com.example.pupi;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Window;

/**
 * This demonstrates the use of action bar tabs and how they interact
 * with other action bar features.
 */
public class MainActivity extends FragmentActivity {
   
    private FragmentTabHost mTabHost;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        

        final ActionBar bar = getActionBar();
        if(bar!=null){
        	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        	bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);  
        }
        
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
 
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("Home"),
                MapDisplayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("track").setIndicator("Track"),
               ListDisplayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("account").setIndicator("Account"),
        		ListDisplayFragment.class, null);

    }


}

package com.example.pupi;

import java.util.Timer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

/**
 * This demonstrates the use of action bar tabs and how they interact
 * with other action bar features.
 */
public class MainActivity extends FragmentActivity {
   
    private FragmentTabHost mTabHost;
    public static String userId;
    private Timer myTimer;

    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        userId = getIntent().getStringExtra("USERID");
        Log.d("USERID",userId);
        
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
 
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("Home"),
                HomeFragment.class, null);
      
        mTabHost.addTab(mTabHost.newTabSpec("track").setIndicator("Track"),
               TrackFragment.class, null); 
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator("Profile"),
        		ProfileFragment.class, null);

        

    }
    
    public void createPost(View view){
		Intent i = new Intent(this,NewPostActivity.class);
		startActivity(i);
	}
    
    public void viewPost(View view){
    	Intent i = new Intent(this,PostDetailActivity.class);
    	startActivity(i);
    }
  

}

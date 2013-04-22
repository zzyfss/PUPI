package com.example.pupi;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * This demonstrates the use of action bar tabs and how they interact
 * with other action bar features.
 */
public class MainActivity extends FragmentActivity {
   
    private FragmentTabHost mTabHost;
    public static String userId;
    public List<PUPIPost> posts;
    private Timer myTimer;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        userId = getIntent().getStringExtra("USERID").toString();
        Log.d("USERID",userId);
        
        posts = new ArrayList<PUPIPost>();
        
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
 
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("Home"),
                HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("track").setIndicator("Track"),
               ListDisplayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator("Profile"),
        		ProfileFragment.class, null);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask(){
        	public void run() {
				new AsyncRefresher().execute();
			}
        },300, 30000);

    }
    
    private class AsyncRefresher extends AsyncTask{

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			String result = PHPLoader.getStringFromPhp(PHPLoader.GETPOST_PHP,new ArrayList());
			result = result.substring(8);
			//Log.d("Server-result",result);
			String[] temp = result.split("/newline");
			for(int i=0; i<temp.length; i++){
				//Log.d("Index", Integer.toString(i));
				PUPIPost p = new PUPIPost(temp[i]);
				Log.d("Each Post",temp[i]);
				posts.add(p);
			}	
			return null;
		}    	
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

package com.example.pupi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPostActivity extends Activity {
	
	private Button btn_add_post;
	private EditText edtx_post_title;
	private EditText edtx_post_reward;
	private EditText edtx_post_loc;
	private EditText edtx_post_content;
	
	private String post_title = "";
	private String post_reward = "";
	private String post_loc = "";
	private String post_content = "";
	List new_post;
	
	private double network_longitude = -888;
	private double network_latitude = -888;
	private double gps_longitude = -888;
	private double gps_latitude = -888;
	private double post_longitude = -888;//final value sent to server
	private double post_latitude = -888;//final value sent to server
	
	private ProgressDialog mDilg;

	private MediaPlayer success_post_sound;
	private MediaPlayer fail_post_sound;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);
		
		btn_add_post = (Button)findViewById(R.id.btn_add_post);
		edtx_post_title = (EditText)findViewById(R.id.editText_post_title);
		edtx_post_reward = (EditText)findViewById(R.id.editText_post_reward);
		edtx_post_loc = (EditText)findViewById(R.id.editText_post_loc);
		edtx_post_content = (EditText)findViewById(R.id.editText_post_content);
		
		success_post_sound = MediaPlayer.create(this, R.raw.success_sound);
		fail_post_sound = MediaPlayer.create(this, R.raw.hint_low);
		
		/* Use the LocationManager class to obtain locations from network*/
		LocationManager mlocManager_network = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener_network = new MyLocationListener_network();
		//find location by network
		mlocManager_network.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener_network);
		
		/* Use the LocationManager class to obtain locations from GPS*/
		LocationManager mlocManager_gps = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener_gps = new MyLocationListener_gps();		
		//find location by GPS
		mlocManager_gps.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener_gps);

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_post, menu);
		return true;
	}
	
	public void submitPost(View v){
		
		//get information from user input
		post_title = edtx_post_title.getText().toString();
		post_reward = edtx_post_reward.getText().toString();
		post_loc = edtx_post_loc.getText().toString();
		post_content = edtx_post_content.getText().toString();
		
		
		//judge the best between network and gps
        if(network_longitude == -888 || network_latitude == -888){
        	if(gps_longitude != -888 && gps_latitude != -888){
        		//if network is down but gps is good, use gps value
        		post_longitude = gps_longitude;
        		post_latitude = gps_latitude;
        	}
        	else{
        		//if both are down, toast error
        		Toast.makeText( getApplicationContext(), "Location service unavailable", Toast.LENGTH_SHORT ).show();
        		return;
        	}
        }
        else if(gps_longitude == -888 || gps_latitude == -888){
        	if(network_longitude != -888 && network_latitude != -888){
        		//if gps is down but network is good, use network value
        		post_longitude = network_longitude;
        		post_latitude = network_latitude;
        	}
        	else{
        		//if both are down, toast error
        		Toast.makeText( getApplicationContext(), "Location service unavailable", Toast.LENGTH_SHORT ).show();
        		return;
        	}
        }
        else{
        	//if both are good, use Network value
    		post_longitude = network_longitude;
    		post_latitude = network_latitude;
        }
					
        if(post_longitude == -888 || post_latitude == -888){//if can't get location
        	Toast.makeText(getBaseContext(),"Location service unavailable", Toast.LENGTH_SHORT).show();
			return;	
        }
		
	
		
		if(post_title.equals("")){
			Toast.makeText(getBaseContext(),"Please fill out Problem Title", Toast.LENGTH_SHORT).show();
			return;		
		}
		else if(post_loc.equals("")){
			Toast.makeText(getBaseContext(),"Please fill out Building Room", Toast.LENGTH_SHORT).show();
			return;			
		}
		else{
			
			PUPIPost p = new PUPIPost();
			
			p.setTitle(post_title);
			p.setReward(post_reward);
			p.setLocation(post_loc);
			p.setContent(post_content);
			p.setPoster(MainActivity.userId);		
			p.setLocx(post_latitude);//latitude
			p.setLocy(post_longitude);//longitude
						
			new_post = p.getPostPackage();
			mDilg= ProgressDialog.show(this,"","Posting...",false,true);
			new AsyncPostAgent().execute(this);
			
		}
		
		
	
		

		
		
	}
	
	private class AsyncPostAgent extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			String resultString = null;
			resultString = PHPLoader.getStringFromPhp(PHPLoader.POST_PHP,new_post);
			return resultString;
		}

		@Override
		protected void onPostExecute(Object result) {
			mDilg.dismiss();
			if(((String)result).startsWith("success")){
				success_post_sound.start();
				Toast.makeText(getApplicationContext(), "Post Success!", Toast.LENGTH_SHORT).show();
				finish();
			}else if(((String)result).startsWith("fail")){
				fail_post_sound.start();
				Toast.makeText(getApplicationContext(), "Server Busy. Please try again later.", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	
	/* Class My Location Listener */
	private class MyLocationListener_network implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc)
		{
			
			loc.getLatitude();
			loc.getLongitude();
			
			network_latitude = loc.getLatitude();
			network_longitude = loc.getLongitude();


		}

		@Override
		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "Network Disabled! please open network.", Toast.LENGTH_SHORT ).show();
			network_longitude = -888;
			network_latitude = -888;
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "Network Enabled!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	}/* End of Class MyLocationListener */
		
		
		
		
		
		
		
		public class MyLocationListener_gps implements LocationListener
		{
			@Override
			public void onLocationChanged(Location loc)
			{
				
				loc.getLatitude();
				loc.getLongitude();
				
				gps_latitude = loc.getLatitude();
				gps_longitude = loc.getLongitude();


			}

			@Override
			public void onProviderDisabled(String provider)
			{
				Toast.makeText( getApplicationContext(), "GPS Disabled! please open GPS.", Toast.LENGTH_SHORT ).show();
				gps_longitude = -888;
				gps_latitude = -888;
			}

			@Override
			public void onProviderEnabled(String provider)
			{
				Toast.makeText( getApplicationContext(), "GPS Enabled!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras)
			{
			}


	}/* End of Class MyLocationListener */
		
	
	
	
	
}



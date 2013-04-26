package com.example.pupi;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDisplayFragment extends SupportMapFragment
implements LoaderManager.LoaderCallbacks<List<PUPIPost>>, Callback{

	private final double PURDUE_LATITUDE=40.427796;
	private final double PURDUE_LONGITUDE=-86.913736;
	private final int PURDUE_ZOOM_MAGNITUDE=17;
	
	List<PUPIPost> mPosts=null;
	// The value is the corresponding post of Key marker
	HashMap<Marker,PUPIPost> markerPostMapping = new HashMap<Marker, PUPIPost>();
	public Handler mHandler=null;

	private double network_latitude=-888;
	private double network_longitude=-888;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mHandler = new Handler(this);
		// Prepare Loader
		getLoaderManager().initLoader(0, null, this);	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);


		GoogleMap map = getMap();
		if(map==null){
			Toast.makeText(getActivity(), "Google Map service unvailable.", Toast.LENGTH_SHORT).show();
			return;
		}
		map.setOnMarkerClickListener(new SimpleMarkerListener());
		
		/* Use the LocationManager class to obtain locations from network*/
		LocationManager mlocManager_network = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener_network = new MapLocationListener_network();
		
		//find location by network
		mlocManager_network.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener_network);
		
		LatLng initLoc;
		if(network_longitude==-888){
			Log.d("DEBUG","Location load failed");
			initLoc = new LatLng(PURDUE_LATITUDE,PURDUE_LONGITUDE);
		}
		else{
			initLoc = new LatLng(network_latitude, network_longitude);
		}
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLoc,PURDUE_ZOOM_MAGNITUDE));
		
	}

	public Loader<List<PUPIPost>> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader with no arguments, so it is simple.

		// Map view loads public posts
		return new PUPIPostLoader(getActivity(),true);
	}

	public void addMarker(){
		GoogleMap map = getMap();
		if(map==null){
			Toast.makeText(getActivity(), "Google Map service unvailable.", Toast.LENGTH_SHORT).show();
			return;
		}
		if(mPosts!=null){
			for(PUPIPost p :mPosts){
				LatLng loc = new LatLng(p.getLocx(),p.getLocy());
				Marker marker = map.addMarker(new MarkerOptions().position(loc).title("abc"));
				markerPostMapping.put(marker, p);
			}
		}
	}

	@Override
	public void onLoadFinished(Loader<List<PUPIPost>> loader, List<PUPIPost> data) {
		// Set the new data in the adapter.
		mPosts = data;
		Log.d("DEBUG","MAP success");
		addMarker();
	}

	@Override
	public void onLoaderReset(Loader<List<PUPIPost>> loader) {
		// Clear the data in the adapter.
		markerPostMapping.clear();
		mPosts = null;
	}

	private class SimpleMarkerListener implements OnMarkerClickListener {

		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(),PostDetailActivity.class);
			PUPIPost p = markerPostMapping.get(marker);
			intent.putExtra("POSTER",p.getPoster());
			intent.putExtra("HELPER", p.getHelper());
			intent.putExtra("LOCATION",p.getLocation());
			intent.putExtra("TITLE",p.getTitle());
			intent.putExtra("CONTENT", p.getContent());
			intent.putExtra("REWARD", p.getReward());
			intent.putExtra("POST_ID", p.getPost_id());
			getActivity().startActivity(intent);
			return true;
		}


	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if(msg.what==0){
			getLoaderManager().restartLoader(0, null, this);
			Log.d("DEBUG","Refresh Map");
		}
		return false;
	}
	
	private class MapLocationListener_network implements LocationListener
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
			Toast.makeText(getActivity(), "Network Disabled! please open network.", Toast.LENGTH_SHORT ).show();
			network_longitude = -888;
			network_latitude = -888;
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			Toast.makeText(getActivity(), "Network Enabled!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	}/* End of Class MyLocationListener */

}

package com.example.pupi;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
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
implements LoaderManager.LoaderCallbacks<List<PUPIPost>>{
	
	private final double PURDUE_LATITUDE=40.427796;
	private final double PURDUE_LONGITUDE=-86.913736;
	private final int PURDUE_ZOOM_MAGNITUDE=16;
	
	List<PUPIPost> mPosts=null;
	// The value is the corresponding post of Key marker
	HashMap<Marker,PUPIPost> markerPostMapping = new HashMap<Marker, PUPIPost>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		// Prepare Loader
		getLoaderManager().initLoader(0, null, this);		
		
		GoogleMap map = getMap();
		if(map==null){
			Toast.makeText(getActivity(), "Google Map service unvailable.", Toast.LENGTH_SHORT).show();
			return;
		}
		map.setOnMarkerClickListener(new SimpleMarkerListener());
		LatLng initLoc = new LatLng(PURDUE_LATITUDE,PURDUE_LONGITUDE);
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
				Marker marker = map.addMarker(new MarkerOptions().position(loc));
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
	
}

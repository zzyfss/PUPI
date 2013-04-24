package com.example.pupi;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDisplayFragment extends SupportMapFragment
implements LoaderManager.LoaderCallbacks<List<PUPIPost>>{
	

	List<PUPIPost> mPosts=null;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0, null, this);
	}

	public Loader<List<PUPIPost>> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader with no arguments, so it is simple.
		return new PUPIPostLoader(getActivity());
	}

	public void addMarker(){
		if(mPosts!=null){
			GoogleMap map = getMap();
			for(PUPIPost p :mPosts){
				LatLng loc = new LatLng(p.getLocx(),p.getLocy());
				Marker myloc = map.addMarker(new MarkerOptions().position(loc));
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
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
		mPosts = null;
	}
	
}

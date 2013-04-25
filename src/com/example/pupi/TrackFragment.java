package com.example.pupi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TrackFragment extends Fragment{

	private ListDisplayFragment mListDisplay;
	private View mView;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mListDisplay = ListDisplayFragment.newInstance(false);
		mView = inflater.inflate(R.layout.fragment_track,
				container, false);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.container_track,mListDisplay);
		transaction.commit();
		return mView;
	}
	
	public void refreshPost(View view){
		
	}


}

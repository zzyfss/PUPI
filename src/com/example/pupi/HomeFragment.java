package com.example.pupi;

import com.google.android.gms.maps.SupportMapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HomeFragment extends Fragment{
	
	private SupportMapFragment mMapDisplay;
	private ListDisplayFragment mListDisplay;
	private View mView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
	
		
		if(mView!=null){
			((ViewGroup)mView.getParent()).removeView(mView);
			return mView;
		}
		mView = inflater.inflate(R.layout.fragment_home,
				container, false);

		if (mView.findViewById(R.id.fragment_container) != null) {

			Log.d("View", "Create");

			mMapDisplay = new SupportMapFragment();
			mListDisplay = new ListDisplayFragment();

			// In case this activity was started with special instructions from an Intent,
			// pass the Intent's extras to the fragment as arguments
			// Add the fragment to the 'fragment_container' FrameLayout

			Switch s = (Switch)mView.findViewById(R.id.btn_switch_mode);

			s.setOnCheckedChangeListener(new simpleOnCheckedListener());
			s.setChecked(true);

		}
		return mView;
	}


	private class simpleOnCheckedListener implements CompoundButton.OnCheckedChangeListener {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack so the user can navigate back
			if (isChecked){
				Log.d("Switch", "Checked");
				transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				transaction.replace(R.id.fragment_container,mMapDisplay);
			}
			else{
				transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);	
				transaction.replace(R.id.fragment_container,mListDisplay);

			}
			// Commit the transaction
			transaction.commit();
		}
	}
}

package com.example.pupi;

import java.util.List;

import com.example.pupi.ListDisplayFragment.PostListAdapter;
import com.google.android.gms.maps.SupportMapFragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HomeFragment extends Fragment 
{

	private MapDisplayFragment mMapDisplay;
	private ListDisplayFragment mListDisplay;
	private View mView;
	private Button btn_refresh;
	private boolean isMapMode=true;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMapDisplay = new MapDisplayFragment();
		mListDisplay = ListDisplayFragment.newInstance(true);
	}



	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		mView = inflater.inflate(R.layout.fragment_home,
				container, false);
		Switch s = (Switch)mView.findViewById(R.id.btn_switch_mode);

		s.setOnCheckedChangeListener(new simpleOnCheckedListener());
		s.setChecked(true);

		btn_refresh = (Button)mView.findViewById(R.id.btn_home_refresh);
		btn_refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isMapMode){
					mMapDisplay.mHandler.sendEmptyMessage(0);
				}
				else{
					mListDisplay.mHandler.sendEmptyMessage(0);
				}

			}

		});
		return mView;
	}



	// Map/List switch
	private class simpleOnCheckedListener implements CompoundButton.OnCheckedChangeListener {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack so the user can navigate back
			if (isChecked){
				transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				transaction.replace(R.id.fragment_container,mMapDisplay);
				isMapMode=true;
			}
			else{
				transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);	
				transaction.replace(R.id.fragment_container,mListDisplay);
				isMapMode=false;
			}
			// Commit the transaction
			transaction.commit();
		}
	}


}

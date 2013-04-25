package com.example.pupi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TrackFragment extends Fragment{

	private ListDisplayFragment mListDisplay;
	private View mView;
	private Button btn_refresh;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		mView = inflater.inflate(R.layout.fragment_track,
				container, false);
		btn_refresh = (Button)mView.findViewById(R.id.btn_track_refresh);
		btn_refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListDisplay.mHandler.sendEmptyMessage(0);
				Log.d("DEBUG", "click");
			}
			
		});
		
		mListDisplay = ListDisplayFragment.newInstance(false);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.container_track,mListDisplay);
		transaction.commit();
		
		return mView;
	}
	
	


}

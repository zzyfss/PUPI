package com.example.pupi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Switch;

public class ListDisplayFragment extends ListFragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		    View view = inflater.inflate(R.layout.fragment_list_mode,
		        container, false);

		    return view;
		  }
}

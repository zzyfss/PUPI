package com.example.pupi;

import android.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListDisplayFragment extends ListFragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		    View view = inflater.inflate(R.layout.fragment_list_mode,
		        container, false);
	
		    return view;
		  }

}

package com.example.pupi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment{



	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_profile,
				container, false);
		((Button)view.findViewById(R.id.btn_edit)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(),EditProfileActivity.class);
				intent.putExtra("NAME", ((TextView) view.findViewById(R.id.txt_account_name)).getText().toString());
				intent.putExtra("EMAIL", ((TextView) view.findViewById(R.id.txt_account_email)).getText().toString());
				intent.putExtra("INTRO", ((TextView) view.findViewById(R.id.txt_account_intro)).getText().toString());
				v.getContext().startActivity(intent);
			}

		});
		return view;
	}
	
}

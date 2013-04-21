package com.example.pupi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditProfileActivity extends Activity {

	private EditText name;
	private EditText email;
	private EditText intro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		name = (EditText)findViewById(R.id.editText_account_name);
		email = (EditText)findViewById(R.id.editText_account_email);
		intro = (EditText)findViewById(R.id.editText_account_intro);
		
	
		name.setText(getIntent().getStringExtra("NAME"));
		email.setText(getIntent().getStringExtra("EMAIL"));
		intro.setText(getIntent().getStringExtra("INTRO"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}
	
	public void updateProfile(View v){
		
	}

}

package com.example.pupi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
		new AsyncUpdateinfoAgent().execute(this);
	}

private class AsyncUpdateinfoAgent extends AsyncTask{
		
		@Override
		protected Object doInBackground(Object... params) {
				String username = MainActivity.userId;
				String resultString = null;
				List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
				nameValPair.add(new BasicNameValuePair("username", username));
				nameValPair.add(new BasicNameValuePair("name",name.getText().toString()));
				nameValPair.add(new BasicNameValuePair("email",email.getText().toString()));
				nameValPair.add(new BasicNameValuePair("info",intro.getText().toString()));
				resultString = PHPLoader.getStringFromPhp(PHPLoader.NAME_PHP,nameValPair);
				return resultString;
		}
		
		@Override
		protected void onPostExecute(Object result) {
		
			String  stringResult=(String)result;
		//	System.out.println(s.length()+s+s);
			if(stringResult.contains("success")){
				Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(getBaseContext(), "Fail", Toast.LENGTH_SHORT).show();
			}
		}
	}
}

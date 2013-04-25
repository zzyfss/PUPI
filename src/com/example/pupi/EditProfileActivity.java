package com.example.pupi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends Activity {

	private EditText name;
	private EditText email;
	private EditText intro;
	private ProgressDialog mDilg;

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
		mDilg= ProgressDialog.show(this,"","Updating...",false,true);
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
			mDilg.dismiss();
			//	System.out.println(s.length()+s+s);
			if(stringResult.contains("success")){
				Toast.makeText(getBaseContext(), "Update success", Toast.LENGTH_SHORT).show();
				Message msg = new Message();
				Bundle b = new Bundle();
				b.putString("NAME", name.getText().toString());
				b.putString("EMAIL", email.getText().toString());
				b.putString("INTRO", intro.getText().toString());
				msg.obj=b;
				msg.what=0;
				ProfileFragment.mHandler.sendMessage(msg);  
				finish();
			}else{
				Toast.makeText(getBaseContext(), "Server error. Please try again.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}

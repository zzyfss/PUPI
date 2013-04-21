package com.example.pupi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class LoginActivity extends Activity {

	private Activity mActivity;
	private ViewGroup mContainerView;
	private Button btn_signUp;
	private Button btn_signIn;
	private TextView txt_error;
	private EditText edit_userName;
	private EditText edit_pwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mActivity = this;

		mContainerView = (ViewGroup) findViewById(R.id.hidenContainer);
		btn_signIn = (Button)findViewById(R.id.btn_signIn);
		btn_signUp = (Button)findViewById(R.id.btn_signUp);
		txt_error = (TextView)findViewById(R.id.txt_error);
		edit_userName =(EditText)findViewById(R.id.editText_userName);
		edit_pwd = (EditText)findViewById(R.id.editText_pwd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void signIn(View view){
		if(btn_signIn.getText().equals("Sign In")){
			new AsyncLoginAgent().execute(this);
/*			ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

			if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED 
			    ||  conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING  ) {

				new AsyncLoginAgent().execute(this);
			    //notify user you are online

			}
			else if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED 
			    ||  conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
				Toast.makeText(getApplicationContext(), "Please check your Internet configuration",Toast.LENGTH_SHORT).show();
			    //notify user you are not online

			}
*/			
		}
		else{
			btn_signIn.setText("Sign In");
			btn_signUp.setText("Sign Up");
			mContainerView.removeViewAt(0);
		}
/*		if(btn_signIn.getText().equals("Sign In")){
			Intent intent = new Intent(this,MainActivity.class);
			
			// Connect to server
			
			startActivity(intent); 
			//overridePendingTransition(R.animator.card_flip_left_in, R.animator.card_flip_left_out);
			
			finish();
		}
		else{
			btn_signIn.setText("Sign In");
			btn_signUp.setText("Sign Up");
			mContainerView.removeViewAt(0);
		}
		*/
	}
	private class AsyncLoginAgent extends AsyncTask{
		
		@Override
		protected Object doInBackground(Object... params) {
				String username = edit_userName.getText().toString();
				String password = edit_pwd.getText().toString();
				String resultString = null;
				List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
				nameValPair.add(new BasicNameValuePair("username", username));
				nameValPair.add(new BasicNameValuePair("password", password));
				resultString = PHPLoader.getStringFromPhp(PHPLoader.LOGIN_PHP,nameValPair);
				return resultString;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			if(((String)result).startsWith("succ")){
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Login fail, please try again.", Toast.LENGTH_SHORT).show();
			}
		}
	}


	public void signUp(View view){
		//		 mActivity.findViewById()
		if(btn_signUp.getText().equals("Sign Up")){
			addItem();
			btn_signIn.setText("Cancel");
			btn_signUp.setText("OK");
		}
		else{
			// Check password field first
			EditText edit_pwd_a =(EditText)mContainerView.findViewById(R.id.editText_pwd_again);
			String name=edit_userName.getText().toString();
			String pwd = edit_pwd.getText().toString();
			if(pwd.isEmpty() || name.isEmpty() ){
				txt_error.setText("User Name or Password can't be empty.");
				return;
			}
			else if(!edit_pwd_a.getText().toString().equals(pwd)){
				txt_error.setText("Passwords don't match.\n");
				return;
			}
			// Connect to server
			new AsyncRegisterAgent().execute(this);
		}

	}
private class AsyncRegisterAgent extends AsyncTask{
		
		@Override
		protected Object doInBackground(Object... params) {
				String username = edit_userName.getText().toString();
				String password = edit_pwd.getText().toString();
				String resultString = null;
				List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
				nameValPair.add(new BasicNameValuePair("username", username));
				nameValPair.add(new BasicNameValuePair("password", password));
				resultString = PHPLoader.getStringFromPhp(PHPLoader.REGISTER_PHP,nameValPair);
				return resultString;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			if(((String)result).startsWith("success")){
				Toast.makeText(getApplicationContext(), "Sign up success!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Unavaliable username, Please try again", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void addItem() {
		// Instantiate a new "row" view.
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.view_sign_up, mContainerView, false);

		// Set the text in the new row to a random country.
		// Because mContainerView has android:animateLayoutChanges set to true,
		// adding this view is automatically animated.
		mContainerView.addView(newView, 0);
	}
	
}

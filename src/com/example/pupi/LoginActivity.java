package com.example.pupi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

	private ViewGroup mContainerView;
	private Button btn_signUp;
	private Button btn_signIn;
	private EditText edit_userName;
	private EditText edit_pwd;
	private ProgressDialog dilg_progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mContainerView = (ViewGroup) findViewById(R.id.hidenContainer);
		btn_signIn = (Button)findViewById(R.id.btn_signIn);
		btn_signUp = (Button)findViewById(R.id.btn_signUp);
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
			String name=edit_userName.getText().toString();
			String pwd = edit_pwd.getText().toString();
			if(pwd.isEmpty() || name.isEmpty() ){
				Toast.makeText(getApplicationContext(), "User Name or Password can't be empty.", Toast.LENGTH_SHORT).show();
				return;
			}
			dilg_progress= ProgressDialog.show(this,"","Loading...",false,true);
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
		else if(btn_signIn.getText().equals("Cancel")){
			btn_signIn.setText("Sign In");
			btn_signUp.setText("Sign Up");
			mContainerView.removeViewAt(0);
		}
	}
	private class AsyncLoginAgent extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			String username = edit_userName.getText().toString();
			String password = edit_pwd.getText().toString();
			try{
				password = SHA1.SHA1(password);
			}
			catch (Exception e){
				Log.d("sha1_rege",password);
			}
			String resultString = null;
			List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
			nameValPair.add(new BasicNameValuePair("username", username));
			nameValPair.add(new BasicNameValuePair("password", password));
			resultString = PHPLoader.getStringFromPhp(PHPLoader.LOGIN_PHP,nameValPair);
			return resultString;
		}

		@Override
		protected void onPostExecute(Object result) {
			dilg_progress.dismiss();
			String r = (String)result;
			if(r.startsWith("succ")){
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				intent.putExtra("USERID",edit_userName.getText().toString());
				startActivity(intent);
				finish();
			}else if(r.startsWith("fail")){
				Toast.makeText(getApplicationContext(), "Username/Password pair not found.", Toast.LENGTH_SHORT).show();	
			}
			else if(r.equals("IOE")){
				Toast.makeText(getApplicationContext(), "Connection error. Please check internet configurations", Toast.LENGTH_SHORT).show();	
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
			// Check user and password fields first
			EditText edit_pwd_a =(EditText)mContainerView.findViewById(R.id.editText_pwd_again);
			String name=edit_userName.getText().toString();
			String pwd = edit_pwd.getText().toString();
			if(pwd.isEmpty() || name.isEmpty() ){
				Toast.makeText(getApplicationContext(), "User Name or Password can't be empty.", Toast.LENGTH_SHORT).show();
				return;
			}
			else if(!edit_pwd_a.getText().toString().equals(pwd)){
				Toast.makeText(getApplicationContext(), "Passwords don't match.\n", Toast.LENGTH_SHORT).show();
				return;
			}
			// Connect to server
			dilg_progress= ProgressDialog.show(this,"","Loading...",false,true);
			new AsyncRegisterAgent().execute(this);
		}

	}
	private class AsyncRegisterAgent extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			String username = edit_userName.getText().toString();
			String password = edit_pwd.getText().toString();
			try{
				password = SHA1.SHA1(password);
			}
			catch(Exception e){
				Log.d("sha1_rege",password);
			}
			String resultString = null;
			List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
			nameValPair.add(new BasicNameValuePair("username", username));
			nameValPair.add(new BasicNameValuePair("password", password));
			resultString = PHPLoader.getStringFromPhp(PHPLoader.REGISTER_PHP,nameValPair);
			return resultString;
		}

		@Override
		protected void onPostExecute(Object result) {
			dilg_progress.dismiss();
			String r = (String)result;
			if(r.startsWith("success")){
				Toast.makeText(getApplicationContext(), "Sign up success!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				intent.putExtra("USERID",edit_userName.getText().toString());
				startActivity(intent);
				finish();
			}else if(r.startsWith("fail")){
				Toast.makeText(getApplicationContext(), "Username Unavailable. Please try again", Toast.LENGTH_SHORT).show();
			}
			else if(r.equals("IOE")){
				Toast.makeText(getApplicationContext(), "Connection error. Please check internet configurations", Toast.LENGTH_SHORT).show();	
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

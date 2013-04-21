package com.example.pupi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
			if(!edit_pwd_a.getText().equals(edit_pwd.getText())){
				txt_error.setText("Passwords don't match.\nå");
				return;
			}
			
			
			// Connect to server
			
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

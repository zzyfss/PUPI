package com.example.pupi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private Activity mActivity;
	private ViewGroup mContainerView;
	private Button btn_signUp;
	private Button btn_signIn;
	private TextView txt_error;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mActivity = this;

		mContainerView = (ViewGroup) findViewById(R.id.hidenContainer);
		btn_signIn = (Button)findViewById(R.id.btn_signIn);
		btn_signUp = (Button)findViewById(R.id.btn_signUp);
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
			startActivity(intent);
			//overridePendingTransition(R.animator.card_flip_left_in, R.animator.card_flip_left_out);

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

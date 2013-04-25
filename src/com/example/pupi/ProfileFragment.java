package com.example.pupi;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements Callback{
	private TextView text_name;
	private TextView text_email;
	private TextView text_intro;
	private ProgressDialog mDlg;
	public static Handler mHandler;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_profile,
				container, false);
		text_name = (TextView) view.findViewById(R.id.txt_account_name);
		text_email = (TextView) view.findViewById(R.id.txt_account_email);
		text_intro = (TextView) view.findViewById(R.id.txt_account_intro);
		
		((Button)view.findViewById(R.id.btn_edit)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(),EditProfileActivity.class);
				intent.putExtra("NAME", text_name.getText().toString());
				intent.putExtra("EMAIL", text_email.getText().toString());
				intent.putExtra("INTRO", text_intro.getText().toString());
				v.getContext().startActivity(intent);
				
			}
			
		});
		
		
		ImageButton btn_picture = (ImageButton)view.findViewById(R.id.btn_profile_picture);
		if(MainActivity.userId.equalsIgnoreCase("zheng")){
			btn_picture.setBackgroundResource(R.drawable.zheng);
		}else if(MainActivity.userId.equalsIgnoreCase("sun")){
			btn_picture.setBackgroundResource(R.drawable.sun);
		}else if(MainActivity.userId.equalsIgnoreCase("mao")){
			btn_picture.setBackgroundResource(R.drawable.mao);
		}else if(MainActivity.userId.equalsIgnoreCase("king")){
			btn_picture.setBackgroundResource(R.drawable.king);
		}else{
			btn_picture.setBackgroundResource(R.drawable.guest);
		}
		
		
		return view;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDlg= ProgressDialog.show(getActivity(),"","Loading...",false,true);
		mHandler = new Handler(this);
		new AsyncGetinfoAgent().execute(this);
		
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
	}
	
	private class AsyncGetinfoAgent extends AsyncTask{
		

		@Override
		protected Object doInBackground(Object... params) {
					
				String username = MainActivity.userId;
				String resultString = null;
				List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
				nameValPair.add(new BasicNameValuePair("username", username));
				resultString = PHPLoader.getStringFromPhp(PHPLoader.GETINFO_PHP,nameValPair);
				return resultString;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			String stringResult = (String)result;
			String name = stringResult.substring(stringResult.indexOf("####name:")+9,stringResult.indexOf("####email:"));
			String email = stringResult.substring(stringResult.indexOf("####email:")+10,stringResult.indexOf("####info:"));
			String intro = stringResult.substring(stringResult.indexOf("####info:")+9,stringResult.length());
			text_name.setText(name);
			text_email.setText(email);
			text_intro.setText(intro);
			mDlg.dismiss();
			
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle b = (Bundle) msg.obj;
		text_name.setText(b.getString("NAME"));
		text_email.setText(b.getString("EMAIL"));
		text_intro.setText(b.getString("INTRO"));
		return false;
	}
	
}

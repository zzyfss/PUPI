package com.example.pupi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Feedback extends Activity {

	private EditText nameField;
	private EditText emailField;
	private EditText feedbackField;
	private EditText extraSubject;
	private Spinner feedbackSpinner;
	private CheckBox responseCheckbox;
	private Button sendFeedback;
	private String defaultSubject;
	private final String developerEmail="nate.radebaugh@gmail.com"; //developer email

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_feedback);	
		
		nameField = (EditText) findViewById(R.id.edit_name);
		
		emailField = (EditText) findViewById(R.id.edit_email);
		
		feedbackField = (EditText) findViewById(R.id.edit_feedbackBody);
		
		extraSubject = (EditText) findViewById(R.id.edit_subject);
		
		feedbackSpinner = (Spinner) findViewById(R.id.spinner_feedbackType);

		responseCheckbox = (CheckBox) findViewById(R.id.checkbox_response);
		
		sendFeedback = (Button) findViewById(R.id.btn_sendFeedback);
		
		defaultSubject = getString(R.string.feedbacksubjectdefault);

		sendFeedback.setOnClickListener(new feedbackListener());

	}

	class feedbackListener implements OnClickListener{
    	public void onClick(View v){
    		
    		//check name
    		if(nameField.getText().toString().equals("")){
    			Toast.makeText(Feedback.this,getString(R.string.feedbackerror1), Toast.LENGTH_SHORT).show();
    			return;
    		}
    		else{
	    		//check email
	    		Pattern regex=Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]*@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	    		Matcher inputEmail=regex.matcher(emailField.getText().toString());
	    		if(!inputEmail.matches()){
	    			Toast.makeText(Feedback.this,getString(R.string.feedbackerror5), Toast.LENGTH_SHORT).show();
	    		}
	    		
	    		else if(emailField.getText().toString().equals("")){
	    			Toast.makeText(Feedback.this,getString(R.string.feedbackerror2), Toast.LENGTH_SHORT).show();
	    		}
	    		else if(extraSubject.getText().toString().equals("")){
	    			Toast.makeText(Feedback.this,getString(R.string.feedbackerror3), Toast.LENGTH_SHORT).show();
	    		}
	    		else if(feedbackField.getText().toString().equals("")){
	    			Toast.makeText(Feedback.this,getString(R.string.feedbackerror4), Toast.LENGTH_SHORT).show();
	    		}
	    		else
	    		{
	        		String name = nameField.getText().toString();
	        		String email = emailField.getText().toString();
	        		String extraSubject = Feedback.this.extraSubject.getText().toString();
	        		boolean bRequiresResponse = responseCheckbox.isChecked();
	        		String responseNeeded;
	        		if(bRequiresResponse)
	        		{
	        			responseNeeded=("Requires a respone").toUpperCase();
	        		}
	        		else
	        			responseNeeded="Doesn't require a response";
	        		String feedback = feedbackField.getText().toString()+"\n"+name+"\t"+email+"\n"+responseNeeded;
	        		String feedbackType = feedbackSpinner.getSelectedItem().toString();
	        		String messageSubject = defaultSubject+" ("+feedbackType+") "+extraSubject;
	        		/* Create the Intent */
	        		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	
	        		/* Fill it with Data */
	        		emailIntent.setType("plain/text");
	        		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{developerEmail});
	        		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, messageSubject);
	        		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, feedback);
	
	        		/* Send it off to the Activity-Chooser */
	        		Feedback.this.startActivity(Intent.createChooser(emailIntent, "Complete action using"));
	    		}
    		}
        }
    }
}

package com.example.pupi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.Toast;

public class PHPLoader {

	final static String LOGIN_PHP = "http://web.ics.purdue.edu/~maog/login.php";
	final static String POST_PHP = "http://web.ics.purdue.edu/~maog/post.php";
	final static String HELP_PHP = "http://web.ics.purdue.edu/~maog/help.php";
	final static String NAME_PHP = "http://web.ics.purdue.edu/~maog/name.php";
	final static String REGISTER_PHP = "http://web.ics.purdue.edu/~maog/register.php";
	final static String GETPOST_PHP = "http://web.ics.purdue.edu/~maog/getpost.php";
	final static String GETINFO_PHP = "http://web.ics.purdue.edu/~maog/getinfo.php";

	static String getStringFromPhp(String in,List list){
		
		String resultString = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(in);
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			resultString = convertStreamToString(is);
		}catch(ClientProtocolException e){
			Log.d("Login", "Clp");
			//e.printStackTrace();
		}catch(IOException e){
			Log.d("Login", "IOE");
			return "IOE";
		}
		return resultString;
	}
	private static String convertStreamToString(InputStream is) {
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

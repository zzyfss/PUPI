package com.example.pupi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PUPIPost {
	private int post_id;
	private String poster;
	private String helper;
	private String location;
	private double locx;
	private double locy;
	private Timestamp time;
	private String title;
	private String content;
	private String reward;
	
	//pull down
	public PUPIPost(String string){
		String[] array = string.split("*#xx#");
		String[] keys = new String[array.length];
		String[] values = new String[array.length];
		for(int i=0; i<array.length; i++){
			String[] item = array[i].split(":");
			keys[i] = item[0];
			values[i] = item[1];
		}
	}
	
	
	public int getPost_id(){
		return post_id;
	}
	
	public String getPoster(){
		return poster;
	}
	
	public String getHelper(){
		return helper;
	}
	
	public String getLocation(){
		return location;
	}
	
	public double getLocx(){
		return locx;
	}
	
	public double getLocy(){
		return locy;
	}
	
	public Timestamp getTimestamp(){
		return time;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getReward(){
		return reward;
	}
	
	//Upload to server
	public List getPostPackage(){
		List<NameValuePair> nameValPair = new ArrayList<NameValuePair>();
		String keys[] = {"poster", "location", "locx", "locy", "title", "content", "reward"};
		String values[] = {poster, location, Double.toString(locx), Double.toString(locy), title, content, reward};
		for (int i=0; i<keys.length; i++){
			nameValPair.add(new BasicNameValuePair(keys[i], values[i]));
		}
		return nameValPair;
	}
	
	
	public void setPost_id(int post_id){
		this.post_id = post_id;
	}
	
	public void setPoster(String poster){
		this.poster = poster;
	}
	
	public void setHelper(String helper){
		this.helper = helper;
	}

	public void setLocation(String location){
		this.location = location;
	}
	
	public void setLocx(double locx){
		this.locx = locx;
	}
	
	public void setLocy(double locy){
		this.locy = locy;
	}
	
	public void setTimestamp(Timestamp timestamp){
		this.time = timestamp;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setReward(String reward){
		this.reward = reward;
	}

}

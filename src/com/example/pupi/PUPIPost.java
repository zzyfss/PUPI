package com.example.pupi;


import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
		string = string.substring(4);
		String[] array = string.split("####");
		String[] keys = new String[array.length];
		String[] values = new String[array.length];
		
		for(int i=0; i<array.length; i++){
			//System.out.println(array[i]);
			int index = array[i].indexOf(":");
			keys[i] = array[i].substring(0, index);
			
			values[i] = array[i].substring(index+1, array[i].length());
		}
		poster = values[0];
		helper = values[1];
		location = values[2];
		locx = Double.parseDouble(values[3]);
		locy = Double.parseDouble(values[4]);
		///////??? time string to Timestamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try{
			date = sdf.parse(values[5]);
			time = new Timestamp(date.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		title = values[6];
		content = values[7];
		reward = values[8];
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
	
	public Timestamp getTime(){
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
	
	public void setTime(Timestamp time){
		this.time = time;
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


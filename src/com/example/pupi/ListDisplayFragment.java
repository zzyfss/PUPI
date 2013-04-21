package com.example.pupi;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListDisplayFragment extends ListFragment{

	private ListView mListView;
	private LayoutInflater mInflater;
	// for test
	private String titles[]={"CS180","CS240","CS252","a","b","c","d","e"};
	private String rooms[]={"LWSN B131","LWSN 1143","LWSN COMMON","a","b","c","d","e"};
	private String rewards[]={"coke","chips","None","a","b","c","d","e"};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_mode,
				container, false);
		mInflater = inflater;
		ListView list = (ListView) view.findViewById(android.R.id.list);
		list.setAdapter(new PostListAdapter());
		
		return view;
	}
	
	private class PostListAdapter extends BaseAdapter{
		
		int N=titles.length; // the number of items in ListView


		public int getCount() {
		   // TODO Auto-generated method stub
		   return N;
	   	}

		public Object getItem(int position) {
	   		// TODO Auto-generated method stub
	   		return position;
	   	}

	   	public long getItemId(int position) {
		// TODO Auto-generated method stub
	   		return position;
	   	}
	   	

		public View getView(int position, View convertView, ViewGroup parent) {
	   		// TODO Auto-generated method stub
	   		LinearLayout mItem;
	   		if(convertView==null){
	   			mItem = (LinearLayout) mInflater.inflate(R.layout.list_item_post, null);
	   			((TextView)mItem.findViewById(R.id.txt_listItem_post_title)).setText(titles[position]);
	   			((TextView)mItem.findViewById(R.id.txt_listItem_post_room)).setText(rooms[position]);
	   			((TextView)mItem.findViewById(R.id.txt_listItem_post_reward)).setText(rewards[position]);
            } else {
                mItem = (LinearLayout)convertView;
                ((TextView)mItem.findViewById(R.id.txt_listItem_post_title)).setText(titles[position]);
	   			((TextView)mItem.findViewById(R.id.txt_listItem_post_room)).setText(rooms[position]);
	   			((TextView)mItem.findViewById(R.id.txt_listItem_post_reward)).setText(rewards[position]);
                
            }
	   		return mItem;
	   	}
	}
}

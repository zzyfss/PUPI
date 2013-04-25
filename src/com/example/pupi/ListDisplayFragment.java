package com.example.pupi;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListDisplayFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<List<PUPIPost>>{

	private ListView mListView;
	private LayoutInflater mInflater;
	public PostListAdapter mAdapter;
	private boolean isPublic;

	static ListDisplayFragment newInstance(boolean isPublic) {
        ListDisplayFragment f = new ListDisplayFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putBoolean("PUBLIC", isPublic);
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        isPublic = getArguments() != null ? getArguments().getBoolean("PUBLIC") : false;
        if(isPublic){
        	Log.d("DEBUG","isPublic");   	
        }

    }
    
	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		  
		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		setEmptyText("No posts");

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new PostListAdapter(getActivity());
		setListAdapter(mAdapter);

		// Start out with a progress indicator.
		setListShown(false);
		
		
		
		getLoaderManager().initLoader(0, null, this);
		
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				PUPIPost p = (PUPIPost) parent.getAdapter().getItem(position);
				Intent intent = new Intent(getActivity(), PostDetailActivity.class);
				intent.putExtra("POSTER",p.getPoster());
				intent.putExtra("HELPER", p.getHelper());
				intent.putExtra("LOCATION",p.getLocation());
				intent.putExtra("TITLE",p.getTitle());
				intent.putExtra("CONTENT", p.getContent());
				intent.putExtra("REWARD", p.getReward());
				intent.putExtra("POST_ID", p.getPost_id());
				startActivity(intent);
			}
        });
	}

	public Loader<List<PUPIPost>> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader with no arguments, so it is simple.
		return new PUPIPostLoader(getActivity(),isPublic);
	}

	@Override
	public void onLoadFinished(Loader<List<PUPIPost>> loader, List<PUPIPost> data) {
		// Set the new data in the adapter.
		mAdapter.setData(data);

	}

	@Override
	public void onLoaderReset(Loader<List<PUPIPost>> loader) {
		// Clear the data in the adapter.
		mAdapter.setData(null);
	}

	class PostListAdapter extends ArrayAdapter<PUPIPost>{
		private final LayoutInflater mInflater;

		public PostListAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_2);
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			setNotifyOnChange(true);
		}

		public void setData(List<PUPIPost> data) {
			clear();
			if (data != null) {
				addAll(data);
			}
			Log.d("DEBUG","Refresh List");
			// The list should now be shown.
			if (isResumed()) {
				setListShown(true);
			}
			else{
				setListShownNoAnimation(true);
			}
			
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout mContainer;

			PUPIPost item = getItem(position);
			if(convertView==null){
				mContainer = (LinearLayout) mInflater.inflate(R.layout.list_item_post, null);
			} else {
				mContainer = (LinearLayout)convertView;
			}
			((TextView)mContainer.findViewById(R.id.txt_listItem_post_title)).setText(item.getTitle());
			((TextView)mContainer.findViewById(R.id.txt_listItem_post_room)).setText(item.getLocation());
			((TextView)mContainer.findViewById(R.id.txt_listItem_post_reward)).setText(item.getReward());

			return mContainer;
		}
	}
}


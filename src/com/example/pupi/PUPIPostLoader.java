package com.example.pupi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class PUPIPostLoader extends AsyncTaskLoader<List<PUPIPost>> {

	List<PUPIPost> mPosts;

	public PUPIPostLoader(Context context) {
		super(context);

		// Retrieve the package manager for later use; note we don't
		// use 'context' directly but instead the save global application
		// context returned by getContext().

	}

	/**
	 * This is where the bulk of our work is done.  This function is
	 * called in a background thread and should generate a new set of
	 * data to be published by the loader.
	 */
	@Override public List<PUPIPost> loadInBackground() {
		// Retrieve all known applications.
		List<PUPIPost> posts = new ArrayList<PUPIPost>();
		String result = PHPLoader.getStringFromPhp(PHPLoader.GETPOST_PHP,new ArrayList());
		if(result.contains("failphpusucks")){
			return posts;
		}
		result = result.substring(8);
		//Log.d("Server-result",result);
		String[] temp = result.split("/newline");
		for(int i=0; i<temp.length; i++){
			//Log.d("Index", Integer.toString(i));
			PUPIPost p = new PUPIPost(temp[i]);
			Log.d("Posts",temp[i]);
			posts.add(p);
		}	

		// Done!
		return posts;
	}

	/**
	 * Called when there is new data to deliver to the client.  The
	 * super class will take care of delivering it; the implementation
	 * here just adds a little more logic.
	 */
	@Override public void deliverResult(List<PUPIPost> posts) {
		if (isReset()) {
			// An async query came in while the loader is stopped.  We
			// don't need the result.
			if (posts != null) {
				onReleaseResources(posts);
			}
		}
		List<PUPIPost> oldPosts  =posts;
		mPosts=posts;

		if (isStarted()) {
			// If the Loader is currently started, we can immediately
			// deliver its results.
			super.deliverResult(posts);
		}

		// At this point we can release the resources associated with
		// 'oldApps' if needed; now that the new result is delivered we
		// know that it is no longer in use.
		if (oldPosts != null) {
			onReleaseResources(oldPosts);
		}
	}

	/**
	 * Handles a request to start the Loader.
	 */
	@Override protected void onStartLoading() {
		if (mPosts != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mPosts);
		}

		if (takeContentChanged() || mPosts == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
	}

	/**
	 * Handles a request to stop the Loader.
	 */
	@Override protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	/**
	 * Handles a request to cancel a load.
	 */
	@Override public void onCanceled(List<PUPIPost> posts) {
		super.onCanceled(posts);

		// At this point we can release the resources associated with 'apps'
		// if needed.
		onReleaseResources(posts);
	}

	/**
	 * Handles a request to completely reset the Loader.
	 */
	@Override protected void onReset() {
		super.onReset();

		// Ensure the loader is stopped
		onStopLoading();

		// At this point we can release the resources associated with 'apps'
		// if needed.
		if (mPosts != null) {
			onReleaseResources(mPosts);
			mPosts = null;
		}

	}

	/**
	 * Helper function to take care of releasing resources associated
	 * with an actively loaded data set.
	 */
	protected void onReleaseResources(List<PUPIPost> posts) {
		// For a simple List<> there is nothing to do.  For something
		// like a Cursor, we would close it here.
	}
}
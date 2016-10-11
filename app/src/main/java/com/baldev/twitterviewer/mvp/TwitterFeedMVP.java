package com.baldev.twitterviewer.mvp;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.baldev.twitterviewer.model.DTOs.Tweet;

import java.util.List;

public interface TwitterFeedMVP {

	interface View {
		void onLoadCompleted(List<Tweet> tweets);

		void onLoadFailed();

		void startLoading();
	}

	interface Presenter extends OnRefreshListener {
		void unsubscribe();

		@Override
		void onRefresh();

		void getTweetsBySearchTerm(String searchTerm);

		void storeDataToRetain(List<Tweet> tweets, String query);
	}
}

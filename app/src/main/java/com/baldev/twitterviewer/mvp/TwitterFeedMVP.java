package com.baldev.twitterviewer.mvp;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.baldev.twitterviewer.model.DTOs.Tweet;

import java.util.List;

public interface TwitterFeedMVP {

	interface View {
		@NonNull
		String getSearchQuery();

		void startLoading();

		void onNewData(List<Tweet> tweets);
	}

	interface Presenter extends OnRefreshListener {
		@Override
		void onRefresh();

		void unsubscribe();

		void getTweetsBySearchTerm(String searchTerm);

		void storeDataToRetain(List<Tweet> tweets, String query);
	}
}

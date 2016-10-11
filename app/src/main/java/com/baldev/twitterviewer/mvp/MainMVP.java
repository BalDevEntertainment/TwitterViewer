package com.baldev.twitterviewer.mvp;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.baldev.twitterviewer.model.DTOs.Tweet;

import java.util.List;

public interface MainMVP {

	interface View {
		void onLoadCompleted(List<Tweet> tweets);

		void onLoadFailed();
	}

	interface Presenter extends OnRefreshListener {
		void unsubscribe();

		@Override
		void onRefresh();

		void getTweetsBySearchTerm(String searchTerm);
	}
}

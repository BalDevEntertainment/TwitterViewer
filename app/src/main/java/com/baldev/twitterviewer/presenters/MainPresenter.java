package com.baldev.twitterviewer.presenters;

import com.baldev.twitterviewer.mvp.MainActivityMVP;
import com.baldev.twitterviewer.mvp.MainActivityMVP.View;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP;

import javax.inject.Inject;

public class MainPresenter implements MainActivityMVP.Presenter {

	private final View view;

	TwitterFeedMVP.View twitterFeedView;

	@Inject
	public MainPresenter(View view, TwitterFeedMVP.View twitterFeedView) {
		this.view = view;
		this.twitterFeedView = twitterFeedView;
	}

	public void storeDataToRetain() {
		twitterFeedView.storeDataToRetain();
	}
}

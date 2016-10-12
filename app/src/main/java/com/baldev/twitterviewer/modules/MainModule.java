package com.baldev.twitterviewer.modules;

import android.support.v4.app.FragmentManager;

import com.baldev.twitterviewer.R;
import com.baldev.twitterviewer.mvp.MainActivityMVP.Presenter;
import com.baldev.twitterviewer.mvp.MainActivityMVP.View;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP;
import com.baldev.twitterviewer.presenters.MainPresenter;
import com.baldev.twitterviewer.views.TwitterFeedFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
	private final FragmentManager fragmentManager;
	private final View view;

	public MainModule(View view, FragmentManager fragmentManager) {
		this.view = view;
		this.fragmentManager = fragmentManager;
	}

	@Provides
	public View provideView() {
		return this.view;
	}

	@Provides
	public Presenter providePresenter(View view, TwitterFeedMVP.View twitterFeedView) {
		return new MainPresenter(view, twitterFeedView);
	}

	@Provides
	public TwitterFeedMVP.View provideFragment() {
		return (TwitterFeedFragment) fragmentManager.findFragmentById(R.id.fragment_twitter_feed);
	}
}
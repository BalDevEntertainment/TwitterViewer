package com.baldev.twitterviewer.modules;

import android.app.Application;

import com.baldev.twitterviewer.model.DataManager;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP.Presenter;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP.View;
import com.baldev.twitterviewer.presenters.TwitterFeedPresenter;
import com.baldev.twitterviewer.views.adapters.TwitterListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TwitterFeedModule {
	private View view;

	public TwitterFeedModule(View view) {
		this.view = view;
	}

	@Provides
	public View provideView() {
		return this.view;
	}

	@Singleton
	@Provides
	public DataModel provideModel(Application context) {
		return new DataManager(context);
	}

	@Provides
	public Presenter providePresenter(View view, DataModel dataModel) {
		return new TwitterFeedPresenter(view, dataModel);
	}

	@Provides
	public TwitterListAdapter provideTwitterListAdapter() {
		return new TwitterListAdapter();
	}

}
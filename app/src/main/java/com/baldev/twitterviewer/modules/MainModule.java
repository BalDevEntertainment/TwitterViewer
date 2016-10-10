package com.baldev.twitterviewer.modules;

import com.baldev.twitterviewer.model.DataManager;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.MainMVP.Presenter;
import com.baldev.twitterviewer.mvp.MainMVP.View;
import com.baldev.twitterviewer.presenters.MainPresenter;
import com.baldev.twitterviewer.views.adapters.TwitterListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
	private View view;

	public MainModule(View view) {
		this.view = view;
	}

	@Provides
	public View provideView() {
		return this.view;
	}

	@Singleton
	@Provides
	public DataModel provideModel() {
		return new DataManager();
	}

	@Provides
	public Presenter providePresenter(View view, DataModel dataModel) {
		return new MainPresenter(view, dataModel);
	}

	@Provides
	public TwitterListAdapter provideTwitterListAdapter() {
		return new TwitterListAdapter();
	}

}
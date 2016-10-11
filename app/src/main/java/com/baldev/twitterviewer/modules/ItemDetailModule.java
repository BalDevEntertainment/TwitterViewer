package com.baldev.twitterviewer.modules;

import android.app.Application;
import android.content.Context;

import com.baldev.twitterviewer.model.DataManager;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.ItemDetailMVP;
import com.baldev.twitterviewer.mvp.ItemDetailMVP.View;
import com.baldev.twitterviewer.presenters.ItemDetailPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemDetailModule {
	private View view;

	public ItemDetailModule(View view) {
		this.view = view;
	}

	@Provides
	public View provideView() {
		return this.view;
	}

	@Provides
	@Singleton
	public DataModel provideModel(Application context) {
		return new DataManager(context);
	}

	@Provides
	public ItemDetailMVP.Presenter providePresenter(ItemDetailMVP.View view, DataModel model) {
		return new ItemDetailPresenter(view, model);
	}

}
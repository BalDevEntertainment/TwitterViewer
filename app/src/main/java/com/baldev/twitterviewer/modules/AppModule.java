package com.baldev.twitterviewer.modules;

import android.app.Application;

import com.baldev.twitterviewer.TwitterViewerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

	Application application;

	public AppModule(Application application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Application providesApplication() {
		return this.application;
	}
}
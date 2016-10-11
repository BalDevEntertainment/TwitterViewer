package com.baldev.twitterviewer.components;

import com.baldev.twitterviewer.modules.AppModule;
import com.baldev.twitterviewer.modules.MainModule;
import com.baldev.twitterviewer.views.TwitterFeedFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
		modules={AppModule.class, MainModule.class}
)
public interface MainComponent {
	void inject(TwitterFeedFragment fragment);
}


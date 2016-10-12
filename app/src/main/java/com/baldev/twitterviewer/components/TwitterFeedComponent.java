package com.baldev.twitterviewer.components;

import com.baldev.twitterviewer.modules.AppModule;
import com.baldev.twitterviewer.modules.TwitterFeedModule;
import com.baldev.twitterviewer.views.TwitterFeedFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
		modules={AppModule.class, TwitterFeedModule.class}
)
@SuppressWarnings("package")
public interface TwitterFeedComponent {
	void inject(TwitterFeedFragment fragment);
}


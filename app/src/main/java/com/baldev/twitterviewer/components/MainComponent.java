package com.baldev.twitterviewer.components;

import com.baldev.twitterviewer.modules.MainModule;
import com.baldev.twitterviewer.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
		modules={MainModule.class}
)
public interface MainComponent {
	void inject(MainActivity activity);
}

package com.baldev.twitterviewer;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class TwitterViewerApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
	}
}

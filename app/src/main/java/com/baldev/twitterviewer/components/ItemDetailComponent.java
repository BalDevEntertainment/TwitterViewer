package com.baldev.twitterviewer.components;

import com.baldev.twitterviewer.modules.ItemDetailModule;
import com.baldev.twitterviewer.views.ItemDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
		modules={ItemDetailModule.class}
)
public interface ItemDetailComponent {
	void inject(ItemDetailActivity activity);
}


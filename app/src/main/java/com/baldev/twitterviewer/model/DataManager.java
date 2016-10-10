package com.baldev.twitterviewer.model;

import com.baldev.twitterviewer.BuildConfig;
import com.baldev.twitterviewer.model.DTOs.TwitterAuthentication;
import com.baldev.twitterviewer.model.DTOs.TwitterAuthentication.GrantType;
import com.baldev.twitterviewer.model.DTOs.TwitterToken;
import com.baldev.twitterviewer.model.helpers.TwitterAPIHelper;
import com.baldev.twitterviewer.model.helpers.TwitterService;
import com.baldev.twitterviewer.mvp.DataModel;

import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager implements DataModel {

	private static final String API_KEY = BuildConfig.TWITTER_API_KEY; //Change for provided API KEY, used to avoid publishing API key on repository.
	private static final String GET_SOMETHING = "";

	//TODO inject dependency
	private static TwitterService service = TwitterAPIHelper.getInstance().create(TwitterService.class);

	@Override
	public Observable<Object> getSomething(int page) {
		return service.getSomething(GET_SOMETHING, API_KEY, page);
	}

	@Override
	public Observable<TwitterToken> authenticate() {
		TwitterAuthentication tokenAuthentication = new TwitterAuthentication();
		tokenAuthentication.setGrantType(GrantType.CLIENT_CREDENTIALS);
		return service.authenticate(tokenAuthentication.getRequestBody());
	}
}

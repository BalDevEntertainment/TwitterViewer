package com.baldev.twitterviewer.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.baldev.twitterviewer.BuildConfig;
import com.baldev.twitterviewer.model.DTOs.TwitterAuthentication;
import com.baldev.twitterviewer.model.DTOs.TwitterAuthentication.GrantType;
import com.baldev.twitterviewer.model.DTOs.TwitterToken;
import com.baldev.twitterviewer.model.helpers.PreferencesManager;
import com.baldev.twitterviewer.model.helpers.SharedPreferencesManager;
import com.baldev.twitterviewer.model.helpers.TwitterAPIHelper;
import com.baldev.twitterviewer.model.helpers.TwitterService;
import com.baldev.twitterviewer.mvp.DataModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Single;

import static android.icu.text.UnicodeSet.from;

@Singleton
public class DataManager implements DataModel {

	private static final String API_KEY = BuildConfig.TWITTER_API_KEY; //Change for provided API KEY, used to avoid publishing API key on repository.
	private static final String GET_SOMETHING = "";

	//TODO inject dependencies
	private static TwitterService twitterService = TwitterAPIHelper.getInstance().create(TwitterService.class);
	private static PreferencesManager preferencesManager = SharedPreferencesManager.getInstance();

	private Context context;

	@Inject
	public DataManager(Context context) {
		this.context = context;
	}

	@Override
	public Single<TwitterToken> getAccessToken() {
		String accessToken = preferencesManager.getAccessToken(this.context);
		TwitterToken value = TwitterToken.bearerToken(accessToken);
		return Single.just(value);
	}

	@Override
	public Observable<Object> getSomething(String accessToken) {
		return twitterService.getSomething(accessToken, GET_SOMETHING, API_KEY);
	}

	@Override
	public Single<TwitterToken> authenticate() {
		TwitterAuthentication tokenAuthentication = new TwitterAuthentication();
		tokenAuthentication.setGrantType(GrantType.CLIENT_CREDENTIALS);
		return twitterService.authenticate(tokenAuthentication.getRequestBody());
	}

	@Override
	public void saveAccessToken(String accessToken) {
		preferencesManager.saveAccessToken(this.context, accessToken);
	}
}

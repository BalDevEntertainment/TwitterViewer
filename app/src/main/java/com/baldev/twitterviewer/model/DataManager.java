package com.baldev.twitterviewer.model;

import android.content.Context;

import com.baldev.twitterviewer.BuildConfig;
import com.baldev.twitterviewer.model.DTOs.SearchResponse;
import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.model.DTOs.TwitterAuthentication;
import com.baldev.twitterviewer.model.DTOs.TwitterAuthentication.GrantType;
import com.baldev.twitterviewer.model.DTOs.TwitterToken;
import com.baldev.twitterviewer.model.helpers.PreferencesManager;
import com.baldev.twitterviewer.model.helpers.SharedPreferencesManager;
import com.baldev.twitterviewer.model.helpers.TwitterAPIHelper;
import com.baldev.twitterviewer.model.helpers.TwitterService;
import com.baldev.twitterviewer.mvp.DataModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

@Singleton
public class DataManager implements DataModel {

	private static final String API_KEY = BuildConfig.TWITTER_API_KEY; //Change for provided API KEY, used to avoid publishing API key on repository.

	//TODO inject dependencies
	private static TwitterService twitterService = TwitterAPIHelper.getInstance().create(TwitterService.class);
	private static PreferencesManager preferencesManager = SharedPreferencesManager.getInstance();

	private List<Tweet> retainedTweets;
	private String lastSearch = "";

	private Context context;

	@Inject
	public DataManager(Context context) {
		this.context = context;
	}

	@Override
	public Single<TwitterToken> askForAccessToken() {
		String accessToken = preferencesManager.getAccessToken(this.context);
		TwitterToken value = TwitterToken.bearerToken(accessToken);
		return Single.just(value);
	}

	@Override
	public Observable<SearchResponse> getTweetsBySearchTerm(String searchTerm) {
		if (isSearchEmpty(searchTerm)) {
			return Observable.just(SearchResponse.getEmptyResponse());
		} else {
			if (isCached(searchTerm)) {
				this.lastSearch = "";
				return Observable.just(new SearchResponse(this.retainedTweets));
			} else {
				String formattedAccessToken = getFormattedAccessToken(this.getAccessToken());
				return twitterService.getTweetsBySearchTerm(formattedAccessToken, searchTerm);
			}
		}
	}

	private boolean isCached(String searchTerm) {
		return searchTerm.equals(this.lastSearch);
	}

	private boolean isSearchEmpty(String searchTerm) {
		return searchTerm == null || searchTerm.equals("");
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

	@Override
	public void storeDataToRetain(List<Tweet> retainedTweets, String lastSearch) {
		this.retainedTweets = retainedTweets;
		this.lastSearch = lastSearch;
	}

	@Override
	public boolean needsUpdate(String searchTerm) {
		return !isSearchEmpty(searchTerm) && !isCached(searchTerm);
	}

	private String getFormattedAccessToken(TwitterToken accessToken) {
		String tokenType = accessToken.getTokenType().getValue();
		String accessTokenValue = accessToken.getAccessToken();
		return String.format("%s %s", tokenType, accessTokenValue);
	}

	private TwitterToken getAccessToken() {
		//Get token
		return this.askForAccessToken()
				.subscribeOn(Schedulers.computation()) //Check this
				//Get Access Token;
				.flatMap(accessToken ->
						accessToken.getAccessToken() == null ?
								//Not cached, authenticate.
								authenticate()
										//Save access token on the shared preferences.
										.doOnSuccess(twitterToken -> saveAccessToken(twitterToken.getAccessToken()))
								//Return cached access token
								: Single.just(accessToken))
				.toBlocking()
				.value();
	}
}

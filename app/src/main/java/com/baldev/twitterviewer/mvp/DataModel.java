package com.baldev.twitterviewer.mvp;

import com.baldev.twitterviewer.model.DTOs.TwitterToken;

import rx.Observable;
import rx.Single;

public interface DataModel {

	Single<TwitterToken> getAccessToken();

	Single<TwitterToken> authenticate();

	Observable<Object> getTweetsBySearchTerm(TwitterToken accessToken, String queryTerm);

	void saveAccessToken(String accessToken);
}

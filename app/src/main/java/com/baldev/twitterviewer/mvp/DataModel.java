package com.baldev.twitterviewer.mvp;

import com.baldev.twitterviewer.model.DTOs.SearchResponse;
import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.model.DTOs.TwitterToken;

import java.util.List;

import rx.Observable;
import rx.Single;

public interface DataModel {

	Single<TwitterToken> askForAccessToken();

	Single<TwitterToken> authenticate();

	Observable<SearchResponse> getTweetsBySearchTerm(String queryTerm);

	void saveAccessToken(String accessToken);

	void storeDataToRetain(List<Tweet> retainedTweets, String lastSearch);

	boolean needsUpdate(String searchTerm);
}

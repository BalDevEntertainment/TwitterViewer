package com.baldev.twitterviewer.mvp;

import android.content.Context;

import com.baldev.twitterviewer.model.DTOs.TwitterToken;

import rx.Observable;
import rx.Single;

public interface DataModel {

	Single<TwitterToken> getAccessToken();

	Single<TwitterToken> authenticate();

	Observable<Object> getSomething(String accessToken);

	void saveAccessToken(String accessToken);
}

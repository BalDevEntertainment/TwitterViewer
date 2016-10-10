package com.baldev.twitterviewer.mvp;

import com.baldev.twitterviewer.model.DTOs.TwitterToken;

import rx.Observable;

public interface DataModel {

	Observable<Object> getSomething(int page);

	Observable<TwitterToken> authenticate();

}

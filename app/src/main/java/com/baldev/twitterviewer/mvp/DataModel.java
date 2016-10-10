package com.baldev.twitterviewer.mvp;

import rx.Observable;

public interface DataModel {

	Observable<Object> getSomething(int page);

}

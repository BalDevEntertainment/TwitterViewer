package com.baldev.twitterviewer.presenters;

import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.MainMVP;
import com.baldev.twitterviewer.mvp.MainMVP.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter implements MainMVP.Presenter {

	private final View view;
	private final DataModel dataModel;
	private List<Subscription> subscriptions = new ArrayList<>();

	@Inject
	public MainPresenter(View view, DataModel dataModel) {
		this.view = view;
		this.dataModel = dataModel;
	}

	@Override
	public void getTweets() {
		//Get token
		final Subscription subscription = this.dataModel.getAccessToken()
				.subscribeOn(Schedulers.computation()) //Check this
				//If token isn't cached, authenticate and get a new one.
				.flatMap(accessToken -> accessToken.getAccessToken() == null ? dataModel.authenticate() : Single.just(accessToken))
				//Save access token on the shared preferences.
				.doOnSuccess(twitterToken -> dataModel.saveAccessToken(twitterToken.getAccessToken()))
				//Get twits
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(twitterToken -> dataModel.getSomething(twitterToken.getAccessToken()), Throwable::printStackTrace);
		subscriptions.add(subscription);
	}

	@Override
	public void unsubscribe() {
		for (Subscription subscription : subscriptions) {
			if (!subscription.isUnsubscribed()) {
				subscription.unsubscribe();
			}
		}
	}

	@Override
	public void onRefresh() {
	}
}

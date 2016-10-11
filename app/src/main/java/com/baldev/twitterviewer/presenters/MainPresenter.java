package com.baldev.twitterviewer.presenters;

import com.baldev.twitterviewer.model.DTOs.TwitterToken;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.MainMVP;
import com.baldev.twitterviewer.mvp.MainMVP.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
				//If token isn't cached, authenticate and get a new one.
				.subscribeOn(Schedulers.computation()) //Check this
				.flatMap(new Func1<TwitterToken, Single<TwitterToken>>() {
					@Override
					public Single<TwitterToken> call(TwitterToken accessToken) {
						return accessToken.getAccessToken() == null ? dataModel.authenticate() : Single.just(accessToken);
					}
				})
				//Save access token on the shared preferences.
				.doOnSuccess(new Action1<TwitterToken>() {
					@Override
					public void call(TwitterToken twitterToken) {
						dataModel.saveAccessToken(twitterToken.getAccessToken());
					}
				})
				//Get twits
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<TwitterToken>() {
					@Override
					public void call(TwitterToken twitterToken) {
						dataModel.getSomething(twitterToken.getAccessToken());
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						throwable.printStackTrace();
					}
				});
		subscriptions.add(subscription);
	}

	/*
	public void authenticate() {
		final Subscription subscription = this.dataModel.authenticate()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new SingleSubscriber<TwitterToken>() {
					@Override
					public void onSuccess(TwitterToken response) {
						Log.d("worked", String.format("access token: %s - token type: %s",
								response.getAccessToken(), response.getTokenType().getValue()));
						saveAccessToken(response.getAccessToken());
					}

					@Override
					public void onError(Throwable error) {
						error.printStackTrace();
					}

				});
		subscriptions.add(subscription);

	}*/

	@Override
	public void unsubscribe() {
		for (Subscription subscription : subscriptions) {
			subscription.unsubscribe();
		}
	}

	@Override
	public void onRefresh() {
	}

	private void saveAccessToken(String accessToken) {
		this.dataModel.saveAccessToken(accessToken);
	}

}

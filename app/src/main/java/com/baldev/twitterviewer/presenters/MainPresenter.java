package com.baldev.twitterviewer.presenters;

import android.icu.text.Replaceable;
import android.util.Log;

import com.baldev.twitterviewer.model.DTOs.TwitterToken;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.MainMVP;
import com.baldev.twitterviewer.mvp.MainMVP.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
	public void authenticate() {
		final Subscription subscription = this.dataModel.authenticate()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<TwitterToken>() {
					@Override
					public void call(TwitterToken response) {
						Log.d("worked", String.format("access token: %s - token type: %s",
								response.getAccessToken(), response.getTokenType().getValue()));
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						throwable.printStackTrace();
					}
				});
		subscriptions.add(subscription);

	}

	@Override
	public void unsubscribe() {
		for (Subscription subscription : subscriptions) {
			subscription.unsubscribe();
		}
	}

	@Override
	public void onRefresh() {
	}

}

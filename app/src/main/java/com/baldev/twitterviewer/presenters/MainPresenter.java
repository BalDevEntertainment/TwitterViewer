package com.baldev.twitterviewer.presenters;

import android.util.Log;

import com.baldev.twitterviewer.model.DTOs.SearchResponse;
import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.model.DTOs.TwitterToken;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observer;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainPresenter implements TwitterFeedMVP.Presenter {

	private static final int DELAY = 400;

	private final View view;
	private final DataModel dataModel;
	private List<Subscription> subscriptions = new ArrayList<>();
	//TODO inject this
	private PublishSubject<String> searchResultsSubject = PublishSubject.create();

	@Inject
	public MainPresenter(View view, DataModel dataModel) {
		this.view = view;
		this.dataModel = dataModel;
		this.setupSearch();
	}

	@Override
	public void getTweetsBySearchTerm(final String searchTerm) {
		this.searchResultsSubject.onNext(searchTerm);
	}

	@Override
	public void unsubscribe() {
		for (Subscription subscription : subscriptions) {
			if (!subscription.isUnsubscribed()) {
				subscription.unsubscribe();
			}
		}
	}

	private void setupSearch() {
		final Subscription subscription = searchResultsSubject
				//wait a little bit to avoid server overhead.
				.debounce(DELAY, TimeUnit.MILLISECONDS)
				.observeOn(Schedulers.io())
				//Get tweets by search term.
				.flatMap(searchTerm -> dataModel.getTweetsBySearchTerm(getAccessToken(), searchTerm))
				//Update UI
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<SearchResponse>() {

					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
					}

					@Override
					public void onNext(SearchResponse searchResponse) {
						List<Tweet> tweets = searchResponse.getStatuses();
						view.onLoadCompleted(tweets);
					}
				});
		subscriptions.add(subscription);
	}

	private TwitterToken getAccessToken() {
		//Get token
		return this.dataModel.getAccessToken()
				.subscribeOn(Schedulers.computation()) //Check this
				//Get Access Token;
				.flatMap(accessToken ->
						accessToken.getAccessToken() == null ?
								//Not cached, authenticate.
								dataModel.authenticate()
										//Save access token on the shared preferences.
										.doOnSuccess(twitterToken -> dataModel.saveAccessToken(twitterToken.getAccessToken()))
								//Return cached access token
								: Single.just(accessToken))
				.toBlocking()
				.value();
	}

	@Override
	public void onRefresh() {
	}
}

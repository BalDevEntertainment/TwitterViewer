package com.baldev.twitterviewer.presenters;

import com.baldev.twitterviewer.model.DTOs.SearchResponse;
import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainPresenter implements TwitterFeedMVP.Presenter {

	private static final int SEARCH_DELAY = 400;

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

	@Override
	public void storeDataToRetain(List<Tweet> tweets, String lastSearch) {
		this.dataModel.storeDataToRetain(tweets, lastSearch);
	}

	private void setupSearch() {
		final Subscription subscription = searchResultsSubject
				//wait a little bit to avoid server overhead.
				.debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
				.observeOn(AndroidSchedulers.mainThread())
				.map(searchTerm -> {
					if (this.dataModel.needsUpdate(searchTerm)) {
						view.startLoading();
					}
					return searchTerm;
				})
				.observeOn(Schedulers.io())
				//Get tweets by search term.
				.flatMap(dataModel::getTweetsBySearchTerm)
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

	@Override
	public void onRefresh() {
		//TODO reload information.
	}
}

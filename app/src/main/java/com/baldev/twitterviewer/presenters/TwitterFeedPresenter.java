package com.baldev.twitterviewer.presenters;

import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class TwitterFeedPresenter implements TwitterFeedMVP.Presenter {

	private static final int SEARCH_DELAY = 400;

	private final View view;
	private final DataModel dataModel;
	//TODO inject this
	private PublishSubject<String> searchResultsSubject = PublishSubject.create();
	private List<Subscription> subscriptions = new ArrayList<>();

	@Inject
	public TwitterFeedPresenter(View view, DataModel dataModel) {
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

	@Override
	public void onRefresh() {
		this.getTweetsBySearchTerm(this.view.getSearchQuery());
	}

	private void setupSearch() {
		final Subscription subscription = searchResultsSubject
				//wait a little bit to avoid server overhead.
				.debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
				//Show loading dialog
				.observeOn(AndroidSchedulers.mainThread())
				.map(searchTerm -> {
					this.showLoadIfNeeded(searchTerm);
					return searchTerm;
				})
				.observeOn(Schedulers.io())
				//Get tweets by search term.
				.flatMap(dataModel::getTweetsBySearchTerm)
				//Update UI
				.observeOn(AndroidSchedulers.mainThread())
				//Notify the view that new data has been retrieved.
				.subscribe(searchResponse -> {
					view.onNewData(searchResponse.getStatuses());
				});
		subscriptions.add(subscription);
	}

	private void showLoadIfNeeded(String searchTerm) {
		if (this.dataModel.needsUpdate(searchTerm)) {
			view.startLoading();
		}
	}
}

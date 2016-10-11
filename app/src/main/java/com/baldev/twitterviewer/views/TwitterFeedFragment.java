package com.baldev.twitterviewer.views;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baldev.twitterviewer.R;
import com.baldev.twitterviewer.components.DaggerMainComponent;
import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.modules.AppModule;
import com.baldev.twitterviewer.modules.MainModule;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP;
import com.baldev.twitterviewer.mvp.TwitterFeedMVP.Presenter;
import com.baldev.twitterviewer.views.adapters.TwitterListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwitterFeedFragment extends Fragment implements TwitterFeedMVP.View, OnQueryTextListener {

	@BindView(R.id.list_results) RecyclerView resultsList;
	@BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
	@BindView(R.id.search) SearchView searchView;

	@Inject
	Presenter presenter;

	@Inject
	TwitterListAdapter adapter;
	private List<Tweet> retainedTweets;
	private String lastSearch = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_twitter_feed, container, false);
		ButterKnife.bind(this, view);
		this.setupComponent();
		this.setupAdapter();
		this.setupSearchView();
		this.setupSwipeRefreshLayout();
		return view;
	}

	protected void setupComponent() {
		DaggerMainComponent.builder()
				.mainModule(new MainModule(this))
				.appModule(new AppModule(this.getActivity().getApplication()))
				.build()
				.inject(this);
	}


	@OnClick(R.id.search)
	public void expandSearchView() {
		searchView.setIconified(false);
	}

	@Override
	public void onDestroy() {
		this.presenter.unsubscribe();
		super.onDestroy();
	}

	private void setupAdapter() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
		this.resultsList.setLayoutManager(layoutManager);
		this.resultsList.setAdapter(this.adapter);
		this.resultsList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
			}
		});
	}

	private void setupSearchView() {
		SearchManager searchManager = (SearchManager) this.getActivity().getSystemService(Context.SEARCH_SERVICE);
		this.searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getActivity().getComponentName()));
		this.searchView.setOnQueryTextListener(this);
	}

	private void setupSwipeRefreshLayout() {
		this.swipeRefreshLayout.setOnRefreshListener(this.presenter);
		this.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this.getActivity(), R.color.colorPrimary));
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(final String query) {
		//TODO Improve this method
		if (query.equals("")) { // Maybe put this inside getTweetsBySearch to improve readability.
			this.adapter.setTweets(new ArrayList<>());
			this.adapter.notifyDataSetChanged();
			this.swipeRefreshLayout.setRefreshing(false);
		} else {
			if (this.lastSearch.equals(query)) {
				this.adapter.setTweets(this.retainedTweets);
				this.adapter.notifyDataSetChanged();
				this.swipeRefreshLayout.setRefreshing(false);
				this.lastSearch = "";
			} else {
				if (!this.swipeRefreshLayout.isRefreshing()) this.swipeRefreshLayout.setRefreshing(true);
				this.presenter.getTweetsBySearchTerm(query);
			}
		}
		return true;
	}

	@Override
	public void onLoadCompleted(List<Tweet> tweets) {
		this.adapter.setTweets(tweets);
		this.adapter.notifyDataSetChanged();
		this.swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onLoadFailed() {
		Log.d("Test", "On Load failed");
	}

	public void storeDataToRetain() {
		this.retainedTweets = this.adapter.getTweets();
		this.lastSearch = this.searchView.getQuery().toString();
	}

	public List<Tweet> getRetainedTweets() {
		return this.retainedTweets;
	}

	public String getLastSearch() {
		return this.lastSearch;
	}
}

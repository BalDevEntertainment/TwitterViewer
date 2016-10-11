package com.baldev.twitterviewer.views;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
		this.setupComponent();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_twitter_feed, container, false);
		ButterKnife.bind(this, view);
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
				//TODO Implement for pagination.
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
		this.presenter.getTweetsBySearchTerm(query);
		return true;
	}

	@Override
	public void onNewData(List<Tweet> tweets) {
		this.adapter.setTweets(tweets);
		this.adapter.notifyDataSetChanged();
		this.swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void startLoading() {
		if (!this.swipeRefreshLayout.isRefreshing()){
			this.swipeRefreshLayout.setRefreshing(true);
		}
	}

	public void storeDataToRetain() {
		this.presenter.storeDataToRetain(this.adapter.getTweets(), getSearchQuery());
	}

	@NonNull
	public String
	getSearchQuery() {
		return this.searchView.getQuery().toString();
	}
}

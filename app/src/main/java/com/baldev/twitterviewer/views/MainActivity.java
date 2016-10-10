package com.baldev.twitterviewer.views;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import com.baldev.twitterviewer.R;
import com.baldev.twitterviewer.components.DaggerMainComponent;
import com.baldev.twitterviewer.modules.MainModule;
import com.baldev.twitterviewer.mvp.MainMVP.Presenter;
import com.baldev.twitterviewer.mvp.MainMVP.View;
import com.baldev.twitterviewer.views.adapters.TwitterListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View, OnQueryTextListener {

	@BindView(R.id.list_results) RecyclerView photoList;
	@BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
	@BindView(R.id.search) SearchView searchView;

	@Inject
	Presenter presenter;

	@Inject
	TwitterListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		this.setupComponent();
		this.setupAdapter();
		this.setupSearchView();
		this.setupSwipeRefreshLayout();
	}

	protected void setupComponent() {
		DaggerMainComponent.builder()
				.mainModule(new MainModule(this))
				.build()
				.inject(this);
	}


	@OnClick(R.id.search)
	public void expandSearchView() {
		searchView.setIconified(false);
	}

	@Override
	protected void onDestroy() {
		this.presenter.unsubscribe();
		super.onDestroy();
	}

	private void setupAdapter() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		this.photoList.setLayoutManager(layoutManager);
		this.photoList.setAdapter(this.adapter);
		this.photoList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadMoreDataFromAPI(page);
			}
		});
	}

	private void loadMoreDataFromAPI(int page) {
	}


	private void setupSearchView() {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		this.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		this.searchView.setOnQueryTextListener(this);
	}

	private void setupSwipeRefreshLayout() {
		this.swipeRefreshLayout.setOnRefreshListener(this.presenter);
		this.swipeRefreshLayout.setRefreshing(true);
		this.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(final String query) {
		return true;
	}
}

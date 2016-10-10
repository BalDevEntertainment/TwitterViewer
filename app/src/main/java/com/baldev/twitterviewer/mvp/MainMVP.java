package com.baldev.twitterviewer.mvp;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public interface MainMVP {

	interface View {
	}

	interface Presenter extends OnRefreshListener {
		void unsubscribe();

		@Override
		void onRefresh();

		void authenticate();
	}
}

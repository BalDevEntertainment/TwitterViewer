package com.baldev.twitterviewer.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.baldev.twitterviewer.R;

public class TwitterFeedActivity extends AppCompatActivity {

	private TwitterFeedFragment twitterFeedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_twitter_feed);

		FragmentManager supportFragmentManager = getSupportFragmentManager();
		twitterFeedFragment = (TwitterFeedFragment) supportFragmentManager.findFragmentById(R.id.fragment_twitter_feed);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		twitterFeedFragment.storeDataToRetain();
	}
}

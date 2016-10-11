package com.baldev.twitterviewer.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baldev.twitterviewer.R;
import com.baldev.twitterviewer.model.DTOs.Tweet;
import com.baldev.twitterviewer.model.DTOs.Tweet.TwitterUser;

import java.util.ArrayList;
import java.util.List;

public class TwitterListAdapter extends RecyclerView.Adapter<TwitterViewHolder> {

	private List<Tweet> tweets = new ArrayList<>();

	//TODO temporal for testing, replace
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	@Override
	public TwitterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_results_item, parent, false);
		return new TwitterViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TwitterViewHolder holder, int position) {
		final Tweet tweet = tweets.get(position);
		TwitterUser user = tweet.getUser();
		holder.photoThumbnail.setImageURI(user.getProfilePictureUri());

		holder.userName.setText(user.getName());
		holder.userScreenName.setText(String.format("@%s", user.getScreenName()));
		holder.tweetText.setText(tweet.getText());
		if (!tweet.getMediaUris().isEmpty()) {
			holder.photoTweet.setVisibility(View.VISIBLE);
			holder.photoTweet.setImageURI(tweet.getMediaUris().get(0));
		} else {
			holder.photoTweet.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount() {
		return tweets.size();
	}
}

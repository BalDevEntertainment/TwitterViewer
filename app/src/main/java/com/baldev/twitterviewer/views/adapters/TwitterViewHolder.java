package com.baldev.twitterviewer.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baldev.twitterviewer.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

class TwitterViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.item_root_view) ViewGroup rootView;
	@BindView(R.id.photo_thumbnail) SimpleDraweeView photoThumbnail;
	@BindView(R.id.text_user_name) TextView userName;
	@BindView(R.id.text_user_screen_name) TextView userScreenName;
	@BindView(R.id.text_tweet) TextView tweetText;
	@BindView(R.id.photo_tweet) SimpleDraweeView photoTweet;

	TwitterViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	ViewGroup getRootView() {
		return this.rootView;
	}
}

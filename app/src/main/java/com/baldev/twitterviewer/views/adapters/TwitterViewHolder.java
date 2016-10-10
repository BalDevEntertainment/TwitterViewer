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
	@BindView(R.id.photo_title) TextView photoTitle;

	TwitterViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	ViewGroup getRootView() {
		return this.rootView;
	}
}

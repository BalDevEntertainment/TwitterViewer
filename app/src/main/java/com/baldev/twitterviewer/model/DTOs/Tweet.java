package com.baldev.twitterviewer.model.DTOs;


import android.net.Uri;

import com.baldev.twitterviewer.model.DTOs.Tweet.Entities.TweetMedia;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

	@SerializedName("text")
	private String text;

	@SerializedName("entities")
	private Entities entity;

	public List<Uri> getMediaUris() {
		List<Uri> mediaUris = new ArrayList<>();
		if (this.entity.hasMedia()) {
			for (TweetMedia tweetMedia : entity.getMedia()) {
				String url = tweetMedia.getUrl();
				if (tweetMedia.hasUrl()) {
					mediaUris.add(Uri.parse(tweetMedia.getUrl()));
				}
			}
		}
		return mediaUris;
	}

	class Entities {

		@SerializedName("media")
		private List<TweetMedia> media;

		List<TweetMedia> getMedia() {
			return media;
		}

		boolean hasMedia() {
			return this.media != null && !this.media.isEmpty();
		}

		class TweetMedia {

			@SerializedName("media_url")
			private String url;

			String getUrl() {
				return url;
			}

			boolean hasUrl() {
				return this.url != null && !this.url.equals("");
			}
		}
	}
}

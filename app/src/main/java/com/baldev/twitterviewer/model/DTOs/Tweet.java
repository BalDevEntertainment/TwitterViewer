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

	@SerializedName("user")
	private TwitterUser user;

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

	public TwitterUser getUser() {
		return user;
	}

	public String getText() {
		return text;
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

	public class TwitterUser {
		@SerializedName("name")
		private String name;

		@SerializedName("screen_name")
		private String screenName;

		@SerializedName("profile_image_url")
		private String pictureUrl;

		public Uri getProfilePictureUri() {
			return this.pictureUrl != null ? Uri.parse(this.pictureUrl) : null;
		}

		public String getName() {
			return name;
		}

		public String getScreenName() {
			return screenName;
		}
	}
}

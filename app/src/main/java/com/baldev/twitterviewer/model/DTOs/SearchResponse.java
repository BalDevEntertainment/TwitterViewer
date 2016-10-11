package com.baldev.twitterviewer.model.DTOs;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

	@SerializedName("statuses")
	private List<Tweet> statuses;

	public List<Tweet> getStatuses() {
		return statuses;
	}
}

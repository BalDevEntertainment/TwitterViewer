package com.baldev.twitterviewer.model.DTOs;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

	@SerializedName("statuses")
	private List<Tweet> statuses;

	public static SearchResponse getEmptyResponse() {
		return new SearchResponse(new ArrayList<>());
	}

	public SearchResponse(List<Tweet> statuses) {
		this.statuses = statuses;
	}

	//To avoid bad instantiation
	private SearchResponse(){
	}

	public List<Tweet> getStatuses() {
		return statuses;
	}
}

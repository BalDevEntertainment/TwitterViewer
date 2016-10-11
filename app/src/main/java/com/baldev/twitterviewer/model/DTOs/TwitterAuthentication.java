package com.baldev.twitterviewer.model.DTOs;

import com.google.gson.annotations.SerializedName;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TwitterAuthentication {

	private static final String KEY_GRANT_TYPE = "grant_type";

	@SerializedName(KEY_GRANT_TYPE)
	private String grantType;

	public void setGrantType(GrantType grantType) {
		this.grantType = grantType.getValue();
	}

	public RequestBody getRequestBody() {
		String text = String.format("%s=%s", KEY_GRANT_TYPE, this.grantType);
		return RequestBody.create(MediaType.parse("text/plain"), text);
	}

	public enum GrantType {
		CLIENT_CREDENTIALS("client_credentials");

		private final String value;

		GrantType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}

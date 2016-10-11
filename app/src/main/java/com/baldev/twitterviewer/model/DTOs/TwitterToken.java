package com.baldev.twitterviewer.model.DTOs;

import com.google.gson.annotations.SerializedName;


public class TwitterToken {

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("access_token")
	private String accessToken;

	private TwitterToken(String tokenType, String accessToken) {
		this.tokenType = tokenType;
		this.accessToken = accessToken;
	}

	public static TwitterToken bearerToken(String accessToken) {
		String tokenType = TokenType.BEARER.getValue();
		return new TwitterToken(tokenType, accessToken);
	}

	public TokenType getTokenType() {
		return TokenType.getFromValue(this.tokenType);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public enum TokenType {
		NONE(""),
		BEARER("bearer");

		private final String value;

		TokenType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		static TokenType getFromValue(String value) {
			switch (value) {
				case "bearer":
					return BEARER;
				default:
					//Throw exception?
					return NONE;
			}
		}
	}
}

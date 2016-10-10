package com.baldev.twitterviewer.model.helpers;


import com.baldev.twitterviewer.model.DTOs.TwitterToken;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterService {

	@Headers({
			"Authorization: Basic VnZsaFBJRE91dVA4RU9HNWJZdTU4NGlrejo5MlcyMVVsTjJRTkhmZzhEZDNuUzFPWkRkcVhzNzBydEM0dE93SkxObjBzc2RlaWxFbw==",
			"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"
	})
	@POST("oauth2/token")
	Observable<TwitterToken> authenticate(@Body RequestBody twitterAuthentication);

	@GET("1.1/search/tweets.json")
	Observable<Object> getSomething(@Query("method") String method, @Query("api_key") String apiKey, @Query("page") int page);

}

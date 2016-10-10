package com.baldev.twitterviewer.model.helpers;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class TwitterAPIHelper {

	private static final String BASE_URL = "https://api.twitter.com/";
	private static final Level LOG_LEVEL = Level.BODY;
	private static Retrofit retrofit;

	private TwitterAPIHelper() {
	}

	@NonNull
	public static Retrofit getInstance() {
		if (retrofit == null) {
			buildRetrofit();
		}
		return retrofit;
	}

	private static void buildRetrofit() {

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(LOG_LEVEL);

		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(logging);

		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient.build())
				.build();
	}

}
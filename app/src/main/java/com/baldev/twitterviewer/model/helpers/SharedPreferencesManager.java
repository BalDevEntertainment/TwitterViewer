package com.baldev.twitterviewer.model.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.BuildConfig;

public class SharedPreferencesManager implements PreferencesManager {
	private static final String SHARED_PREFERENCES_ACCESS_TOKEN = BuildConfig.PACKAGE_NAME + ".ACCESS_TOKEN";
	private static final String KEY_VALUE = "value";

	private static SharedPreferencesManager ourInstance = new SharedPreferencesManager();

	public static SharedPreferencesManager getInstance() {
		return ourInstance;
	}

	private SharedPreferencesManager() {
	}

	@Override
	public String getAccessToken(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_ACCESS_TOKEN, Context.MODE_PRIVATE);
		return sharedPreferences.getString(KEY_VALUE, null);
	}

	@Override
	public void saveAccessToken(Context context, String accessToken) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_ACCESS_TOKEN, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(KEY_VALUE, accessToken);
		editor.apply();
	}
}

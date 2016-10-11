package com.baldev.twitterviewer.model.helpers;

import android.content.Context;

public interface PreferencesManager {

	String getAccessToken(Context context);

	void saveAccessToken(Context context, String accessToken);
}

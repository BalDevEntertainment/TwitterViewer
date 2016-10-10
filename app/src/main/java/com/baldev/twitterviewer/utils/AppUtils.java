package com.baldev.twitterviewer.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AppUtils {

	public static String formatEpoch(String milliseconds) {
		try {
			long timeInMillis = Long.valueOf(milliseconds);
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss", Locale.getDefault());
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timeInMillis);
			TimeZone tz = TimeZone.getDefault();
			sdf.setTimeZone(tz);
			return sdf.format(calendar.getTime());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		}
	}
}

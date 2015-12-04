package com.org.finalmvp.utils;

import android.util.Log;

public class LogUtils {
	static final String LOGTAG="finalmvp";
	public static void d(String msg){
		Log.d(LOGTAG, msg);
	}
	public static void e(String msg){
		Log.e(LOGTAG, msg);
	}
}

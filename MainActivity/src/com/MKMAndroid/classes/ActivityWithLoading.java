package com.MKMAndroid.classes;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;

public interface ActivityWithLoading {
	 public abstract String getStringResourceByName(String aString);
	 public abstract void callBack(ArrayList args);
	 public ProgressDialog getDialog();
	 public boolean checkInternetConnection();
}

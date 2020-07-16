package com.shokoofeadeli.tictactoegame;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class G extends Application {
  public  Context context;
  public static SharedPreferences preferences;


  @Override
  public void onCreate() {
    super.onCreate();

    context = getApplicationContext();
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }
}

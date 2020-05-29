package com.project.messforumstudent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "uname";
    static final String PREF_ROLL_NO= "rollno";
    static final String PREF_API_KEY= "API_KEY";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static void setRoll(Context ctx, Long roll)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_ROLL_NO, String.valueOf(roll));
        editor.commit();
    }
    public static void setApiKey(Context ctx, String key)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_API_KEY, key);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getRoll(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_ROLL_NO, "");
    }

    public static String getApiKey(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_API_KEY, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
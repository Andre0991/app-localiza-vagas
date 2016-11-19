package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by andre.peric.tavares on 11/10/2016.
 */

public class SharedPreferencesUtils {
    private static final String SHPR_NAME = "SH_PREFS_PRKG_APP";
    private static final String USER_NAME_KEY = "SH_KEY_USERNAME";
    private static final String USER_PASSWORD_KEY = "SH_KEY_PASSWORD";
    private SharedPreferences sharedPreferences;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHPR_NAME, Context.MODE_PRIVATE);
    }

    public static boolean hasUserRegistered(Context context){
        return getPrefs(context).contains(USER_NAME_KEY);
    }

    public static String getUsername(Context context){
        return getPrefs(context).getString(USER_NAME_KEY, "");
    }

    public static String getPassword(Context context){
        return getPrefs(context).getString(USER_PASSWORD_KEY, "");
    }

    public static void setUsername(Context context, String username){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(USER_NAME_KEY, username);
        editor.commit();
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(USER_PASSWORD_KEY, password);
        editor.commit();
    }
}

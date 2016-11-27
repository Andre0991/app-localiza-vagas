package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities.RegisterActivity;

/**
 * Created by andre.peric.tavares on 11/10/2016.
 */

public class SharedPreferencesUtils {
    private static final String SHPR_NAME = "SH_PREFS_PRKG_APP";
    private static final String USER_NAME_KEY = "SH_KEY_USERNAME";
    private static final String USER_PASSWORD_KEY = "SH_KEY_PASSWORD";
    private static final String FIRST_NAME_KEY = "SH_KEY_FIRSTNAME";
    private static final String SURNAME_KEY = "SH_KEY_SURNAME";
    private static final String EMAIL_KEY = "SH_KEY_EMAIL";
    private static final String TOKEN_KEY = "SH_KEY_TOKEN";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHPR_NAME, Context.MODE_PRIVATE);
    }

    public static boolean hasUserRegistered(Context context){
        return getPrefs(context).contains(USER_NAME_KEY);
    }

    public static String getUsername(Context context){
        return getPrefs(context).getString(USER_NAME_KEY, "");
    }

    public static String getToken(Context context){
        return getPrefs(context).getString(TOKEN_KEY, "");
    }

    public static String getPassword(Context context){
        return getPrefs(context).getString(USER_PASSWORD_KEY, "");
    }

    public static String getFirstName(Context context){
        return getPrefs(context).getString(FIRST_NAME_KEY, "");
    }

    public static String getSurname(Context context){
        return getPrefs(context).getString(SURNAME_KEY, "");
    }

    public static String getEmail(Context context){
        return getPrefs(context).getString(EMAIL_KEY, "");
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

    public static void setFirstName(Context context, String firstName) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(FIRST_NAME_KEY, firstName);
        editor.commit();
    }


    public static void setSurname(Context context, String surname) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(SURNAME_KEY, surname);
        editor.commit();
    }

        public static void setEmail(Context context, String email) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(EMAIL_KEY, email);
        editor.commit();
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }
}

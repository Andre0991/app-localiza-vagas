package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by andre.peric.tavares on 11/10/2016.
 */

public class SharedPreferencesUtils {
    private static final String SHPR_NAME = "SH_PREFS_PRKG_APP";
    private static final String USER_NAME_KEY = "SH_PREFS_PRKG_APP";
    private SharedPreferences sharedPreferences;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHPR_NAME, Context.MODE_PRIVATE);
    }

    public static boolean hasUserRegistered(Context context){
        return getPrefs(context).contains(USER_NAME_KEY);
    }

    public static String getUsername(Context context){
        assert(hasUserRegistered(context));
        return getPrefs(context).getString(USER_NAME_KEY, "");
    }

    public static void setUsername(Context context, String input){
        assert(hasUserRegistered(context));
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(USER_NAME_KEY, input);
        editor.commit();
    }

}

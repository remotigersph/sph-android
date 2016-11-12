package pogi.tiger.com.sph.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import pogi.tiger.com.sph.model.User;

/**
 * Created by Pogi on 12/11/2016.
 */

public class SharedPreferenceUtils {

    private static final String PREFERENCE_USER = "user";

    public static void setUser(User user, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString("currentUser", gson.toJson(user).toString());
        editor.commit();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(sharedpreferences.getString("currentUser",""), User.class);
    }
}

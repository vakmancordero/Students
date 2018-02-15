package com.kaizensoftware.students.activity.security;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.kaizensoftware.students.model.User;

/**
 * Created by Arturo Cordero
 */
public class UserSession {

    public static final String PREFER_NAME = "UserSession";
    public static final String PREFER_KEY = "currentUser";

    private Context context;

    private SharedPreferences preferences;
    private Editor editor;

    public UserSession(Context context) {

        this.context = context;

        this.preferences = this.context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);

        this.editor = preferences.edit();

    }

    public void createSession(User user) {

        String json = new Gson().toJson(user);

        editor.putString(PREFER_KEY, json).commit();

    }

    public User getUser() {

        String preference = preferences.getString(PREFER_KEY, null);

        if (preference != null)
            return new Gson().fromJson(preference, User.class);

        return null;
    }

    public void logoutUser() {

        editor.remove(PREFER_KEY).commit();

    }

    public boolean isUserLoggedIn() {
        return this.getUser() != null;
    }

}

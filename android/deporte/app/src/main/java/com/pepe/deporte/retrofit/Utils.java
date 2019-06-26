package com.pepe.deporte.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    public static void setData(Context ctx, String token, String user_id, String user_email, String user_name, String user_photo) {
        SharedPreferences prefs = ctx.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.putString("idUser", user_id);
        editor.putString("emailUser", user_email);
        editor.putString("nombreUser", user_name);
        editor.putString("fotoUser", user_photo);
        editor.commit();
    }


    public static String getToken(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return prefs.getString("token", null);
    }

    public static String getUserId(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return prefs.getString("idUser", null);
    }

    public static String getEmailUser(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return prefs.getString("emailUser", null);

    }

    public static String getNombreUser(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return prefs.getString("nombreUser",null);
    }

    public static void clearSharedPreferences(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}

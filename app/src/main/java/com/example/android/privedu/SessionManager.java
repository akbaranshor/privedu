package com.example.android.privedu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Abay on 3/29/2018.
 */

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("Account", 0);
        editor = sharedPreferences.edit();
    }

    public void loginSession(String email, boolean teacher) {
        editor.putBoolean("isLogin", true);
        editor.putString("email", email);

        editor.putBoolean("teacher", teacher);

        editor.commit();
        //if (teacher) context.startActivity(new Intent(context, TeacherAddSubject.class));
        //else
        context.startActivity(new Intent(context, ListSubjectActivity.class));
    }

    public void logout(){
        editor.clear().commit();
        context.startActivity(new Intent(context, SigninActivity.class));
    }

    public boolean checkLogin() {
        return sharedPreferences.getBoolean("isLogin", false);
    }
}

package com.example.android.privedu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SignupActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView textView = findViewById(R.id.linkToSignIn);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            }
        });

        /*
        SharedPreferences sharedPreferences = getSharedPreferences("Account", 0);

        if (sharedPreferences.getBoolean("isLogin", false)) {
            startActivity(new Intent(this, ListSubjectActivity.class));
        }

        */
    }

    public void signup(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        EditText nameTxt = findViewById(R.id.editNameText);
        EditText phoneTxt = findViewById(R.id.editPhoneText);
        EditText emailTxt = findViewById(R.id.editEmailText);
        EditText passwordTxt = findViewById(R.id.editPasswordText);
        RadioGroup radioGroup = findViewById(R.id.role);

        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = findViewById(selectedId);

        String name = nameTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        Boolean role = false;
        SessionManager sessionManager = new SessionManager(this);

        if (email.trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && email.length() > 0)
        {
            helper.insertUser(new User(name, email, MD5(password), phone, radioButton.getText().toString()));

            if (radioButton.getText().toString().equals("Teacher")) role = true;

            sessionManager.loginSession(email, role);
            Toast.makeText(this,"Berhasil login",Toast.LENGTH_SHORT).show();
            finish();
        }

        /*
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.trim().matches(emailPattern) && email.length() > 0)
        {
            Toast.makeText(this,"valid email address",Toast.LENGTH_SHORT).show();
        }

        User user = new User(name, email, MD5(password), phone);

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("email", user.getEmail());
        intent.putExtra("pass", password);
        intent.putExtra("phone", user.getPhoneNumber());
        intent.putExtra("name", user.getFullName());
        startActivity(intent);


        if (helper.insertUser(user) == -1) {
            Toast.makeText(this, "Error bo", Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());
        }

        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        }
        return null;
    }
}

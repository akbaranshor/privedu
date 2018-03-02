package com.example.android.privedu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    public void signup(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        EditText nameTxt = findViewById(R.id.editNameText);
        EditText phoneTxt = findViewById(R.id.editPhoneText);
        EditText emailTxt = findViewById(R.id.editEmailText);
        EditText passwordTxt = findViewById(R.id.editPasswordText);
        TextView textView = findViewById(R.id.textView);

        String name = nameTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] seed = password.getBytes("UTF-8");
        sr.setSeed(seed);
        kgen.init(128, sr);
        SecretKey key = kgen.generateKey();

        Base64.encode(seed, Base64.DEFAULT);

        User user = new User(name, email, password, phone);

        helper.insertUser(user);
        textView.setText(key.toString());
    }
}

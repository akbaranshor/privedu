package com.example.android.privedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EditText emailTxt = findViewById(R.id.editTextEmail);
        EditText passTxt = findViewById(R.id.editTextPassword);

        EditText phoneTxt = findViewById(R.id.editTextPhone);
        EditText nameTxt = findViewById(R.id.editTextName);

        Intent intent = getIntent();
        emailTxt.setText(intent.getStringExtra("email"));
        passTxt.setText(intent.getStringExtra("pass"));

        phoneTxt.setText(intent.getStringExtra("phone"));
        nameTxt.setText(intent.getStringExtra("name"));
    }
}

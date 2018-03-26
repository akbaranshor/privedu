package com.example.android.privedu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        TextView textView = findViewById(R.id.linkToSignUp);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });
    }

    public void signin(View view) {
        EditText emailTxt = findViewById(R.id.editEmail);
        EditText passTxt = findViewById(R.id.editPassword);

        String email = emailTxt.getText().toString();
        String pass = passTxt.getText().toString();

        if (email.equals("admin") && pass.equals("admin")){
            startActivity(new Intent(this, SubjectActivity.class));
        } else if (helper.selectUser(email, pass).equals("not found")) {
            Toast.makeText(this, "Login denied", Toast.LENGTH_SHORT).show();
            return;
        } else {
            User u = helper.getInfo(email);
            Intent intent = new Intent(this, ListSubjectActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("pass", pass);
            intent.putExtra("phone", u.getPhoneNumber());
            intent.putExtra("name", u.getFullName());
            startActivity(intent);
        }
    }
}

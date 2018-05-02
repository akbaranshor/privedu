package com.example.android.privedu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TeacherProfileActivity extends AppCompatActivity {

    TextView name, skill, phone, email;
    DatabaseHelper databaseHelper  = new DatabaseHelper(this);
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        name = findViewById(R.id.teacher_name);
        skill = findViewById(R.id.teacher_skill);
        phone = findViewById(R.id.teacher_phone);
        email = findViewById(R.id.teacher_email);

        user = databaseHelper.getInfo1(getIntent().getStringExtra("teacher_name"));

        name.setText(user.getFullName());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());

        String joined = TextUtils.join(", ", databaseHelper.getSkill(databaseHelper.getIdTeacher(getIntent().getStringExtra("teacher_name"))));
        skill.setText(joined);
    }

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+user.getPhoneNumber()));
        startActivity(intent);
    }
}

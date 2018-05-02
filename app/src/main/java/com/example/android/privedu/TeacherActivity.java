package com.example.android.privedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity {
    EditText name, phone, skill, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        name = findViewById(R.id.teacher_name);
        phone = findViewById(R.id.teacher_phone);
        skill = findViewById(R.id.teacher_skill);
        email = findViewById(R.id.teacher_email);


    }

    public void addTeacher(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertTeacher(new Teacher(name.getText().toString(), email.getText().toString(), phone.getText().toString(), skill.getText().toString()));
        Toast.makeText(this, "Insert Success", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, SubjectActivity.class));
    }
}

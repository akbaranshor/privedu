package com.example.android.privedu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SubjectActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    EditText name, lecture, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        name = findViewById(R.id.subject_name);
        lecture = findViewById(R.id.lecture_name);
        price = findViewById(R.id.price);


    }

    public void add(View view) {
        Subject subject = new Subject(Integer.parseInt(price.getText().toString()), name.getText().toString(), lecture.getText().toString());
        helper.insertSubject(subject);
    }
}

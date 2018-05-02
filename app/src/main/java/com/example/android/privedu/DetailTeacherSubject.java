package com.example.android.privedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetailTeacherSubject extends AppCompatActivity {
    EditText subjectEt, priceEt;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_teacher_subject);
        subjectEt = findViewById(R.id.subject_et);
        priceEt = findViewById(R.id.price_et);

        subjectEt.setText(getIntent().getStringExtra("name"));
        priceEt.setText(getIntent().getStringExtra("price"));
    }

    public void updateSubject(View view) {

        databaseHelper.updateSubject(databaseHelper.getIdSubject(getIntent().getStringExtra("name")),
                subjectEt.getText().toString(),
                Integer.parseInt(priceEt.getText().toString()));
        Toast.makeText(this, "Update Succesfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ListSubjectActivity.class));
    }
}

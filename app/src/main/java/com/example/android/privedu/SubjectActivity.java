package com.example.android.privedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    EditText name, price;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        name = findViewById(R.id.subject_name);
        price = findViewById(R.id.price);
        spinner = findViewById(R.id.teacher_spinner);
        listTeacher();

    }

    public void add(View view) {
        Subject subject = new Subject(Integer.parseInt(price.getText().toString()), helper.getIdTeacher(spinner.getSelectedItem().toString()), name.getText().toString());
        helper.insertSubject(subject);
        Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
    }

    public void addTeacher(View view) {
        startActivity(new Intent(this, TeacherActivity.class));
    }

    public void listTeacher() {
        ArrayList<String> list = helper.showTeacher();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

}

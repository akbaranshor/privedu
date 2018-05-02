package com.example.android.privedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailSubjectActivity extends AppCompatActivity {
    TextView subject, teacher, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subject);
        subject = findViewById(R.id.subject_name);
        teacher = findViewById(R.id.teacher_name);
        price = findViewById(R.id.price_subject);

        subject.setText(getIntent().getStringExtra("name"));
        teacher.setText(getIntent().getStringExtra("teacher_name"));
        price.setText(getIntent().getStringExtra("price"));

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSubjectActivity.this, TeacherProfileActivity.class);
                intent.putExtra("teacher_name", getIntent().getStringExtra("teacher_name"));
                startActivity(intent);
            }
        });

    }


}

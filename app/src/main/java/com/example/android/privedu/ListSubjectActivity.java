package com.example.android.privedu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListSubjectActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ArrayList<Subject> mSubjectData;
    private SubjectAdapter mAdapter;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);

        mRecycleView = findViewById(R.id.recycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mSubjectData = new ArrayList<>();
        mAdapter = new SubjectAdapter(this, mSubjectData);
        mRecycleView.setAdapter(mAdapter);


        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT  price, name, lecture FROM subject";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                mSubjectData.add(new Subject(
                        c.getInt(0),        // price
                        c.getString(1),     // name
                        c.getString(2)      // lecture
                ));

            } while (c.moveToNext());
        }

    }
}

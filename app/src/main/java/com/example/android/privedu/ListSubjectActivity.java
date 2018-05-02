package com.example.android.privedu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ListSubjectActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ArrayList<Subject> mSubjectData;
    private SubjectAdapter mAdapter;
    FloatingActionButton fabBtn;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);

        position = 0;
        mRecycleView = findViewById(R.id.recycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        fabBtn = findViewById(R.id.fab);
        mSubjectData = new ArrayList<>();
        mAdapter = new SubjectAdapter(this, mSubjectData);
        mRecycleView.setAdapter(mAdapter);
        fabBtn.hide();
        String add = "";

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT  price, id_teacher, name FROM subject";
        SharedPreferences sharedPreferences = getSharedPreferences("Account", 0);

        User user = databaseHelper.getInfo(sharedPreferences.getString("email", null));
        Boolean b = false;
        if (user.getRole().equals("Teacher")) {
            add = " where id_teacher ="+databaseHelper.getIdTeacher(user.getFullName());
            b = true;
            fabBtn.show();
        }


        Cursor c = db.rawQuery(selectQuery+add, null);
        if (c.moveToFirst()) {
            do {
                mSubjectData.add(new Subject(
                        c.getInt(0),        // price
                        c.getInt(1),     // id_teacher
                        c.getString(2)      // name
                ));

            } while (c.moveToNext());
        }

        if (b) roleGuru();


    }

    public void roleGuru(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                position = viewHolder.getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(ListSubjectActivity.this);
                builder.setMessage("Ingin menghapus subject?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Subject subject = mSubjectData.get(position);
                        databaseHelper.deleteSubject1(subject.getName());
                        Toast.makeText(ListSubjectActivity.this, "Subject Deleted", Toast.LENGTH_SHORT).show();
                        //Remove swiped item from list and notify the RecyclerView
                        mSubjectData.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecycleView);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if(item.getItemId() == R.id.logout){

            FirebaseAuth.getInstance().signOut();
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.logout();
        } else {
            startActivity(new Intent(this, MapsActivity.class));
        }

        return true;
    }

    public void addSubject(View view) {
        startActivity(new Intent(this, AddSubject.class));
    }
}

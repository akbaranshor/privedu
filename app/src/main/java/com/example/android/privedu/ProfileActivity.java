package com.example.android.privedu;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class ProfileActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    User user;
    EditText emailTxt, passTxt, phoneTxt, nameTxt;
    TextView currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        emailTxt = findViewById(R.id.editTextEmail);
        passTxt = findViewById(R.id.editTextPassword);
        phoneTxt = findViewById(R.id.editTextPhone);
        nameTxt = findViewById(R.id.editTextName);
        currentUser = findViewById(R.id.current_user);

        SharedPreferences sharedPreferences = getSharedPreferences("Account", 0);

        user = databaseHelper.getInfoStudent(sharedPreferences.getString("email", null));

        nameTxt.setText(user.getFullName());
        phoneTxt.setText(user.getPhoneNumber());
        passTxt.setText(user.getPassword());
        emailTxt.setText(user.getEmail());
        //currentUser.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage("Would you like to delete this account?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user1 = databaseHelper.getInfo(user.getEmail());
                if (user1.getRole().equals("Teacher"))
                    databaseHelper.deleteSubject(databaseHelper.getIdTeacher(user1.getFullName()));


                databaseHelper.deleteAccount(databaseHelper.getIdTeacher(user1.getFullName()));
                SessionManager sessionManager = new SessionManager(ProfileActivity.this);
                sessionManager.logout();

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

    public void updateSetting(View view) {

        databaseHelper.updateInfo(databaseHelper.getIdTeacher(user.getFullName()), nameTxt.getText().toString(),
                emailTxt.getText().toString(), passTxt.getText().toString(), phoneTxt.getText().toString());
        Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
    }
}

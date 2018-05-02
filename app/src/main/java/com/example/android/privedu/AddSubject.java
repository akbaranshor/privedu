package com.example.android.privedu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AddSubject extends AppCompatActivity {
    EditText price, subject;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseAuth mAuth;
    String verifId = "";
    String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        price = findViewById(R.id.price_et);
        subject = findViewById(R.id.subject_et);

        mAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("PhoneAuthActivity", "onVerificationFailed", e);
                Toast.makeText(AddSubject.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Log.d("PhoneAuthActivity", "onCodeSent:" + s);
                verifId = s;
            }
        };

    }

    public void send(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }

    public void addSubject(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("Account", 0);

        User user = databaseHelper.getInfo(sharedPreferences.getString("email", null));
        send(user.getPhoneNumber());

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Enter Verification Code");
        alert.setTitle("Phone Code");


        alert.setView(edittext);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                Editable YouEditTextValue = edittext.getText();

                if(TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(AddSubject.this, "Code is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else verifyPhoneNumberWithCode(verifId, edittext.getText().toString());


            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.cancel();
            }
        });

        alert.show();

        /*
        SharedPreferences sharedPreferences = getSharedPreferences("Account", 0);

        User user = databaseHelper.getInfoStudent(sharedPreferences.getString("email", null));

        Subject subject = new Subject(Integer.parseInt(price.getText().toString()),
        databaseHelper.getIdTeacher(user.getFullName()), this.subject.getText().toString());
        databaseHelper.insertSubject(subject);
        Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ListSubjectActivity.class));
        */
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            SharedPreferences sharedPreferences = getSharedPreferences("Account", 0);

                            User user = databaseHelper.getInfoStudent(sharedPreferences.getString("email", null));

                            Subject subject1 = new Subject(Integer.parseInt(price.getText().toString()),
                            databaseHelper.getIdTeacher(user.getFullName()), subject.getText().toString());
                            databaseHelper.insertSubject(subject1);
                            Toast.makeText(AddSubject.this, "Insert Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddSubject.this, ListSubjectActivity.class));
                            Toast.makeText(AddSubject.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(AddSubject.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);

    }
}

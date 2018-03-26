package com.example.android.privedu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Abay on 3/2/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "privedu.db";
    private static final String TABLE_USER = "user";
    private static final String TABLE_SUBJECT = "subject";

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table "+TABLE_USER+" (id integer primary key autoincrement," +
            "name varchar(255) not null, email varchar(255) not null unique, password varchar(255) not null, phone varchar(255) not null);";
    String TABLE_CREATE_SUBJECT = "CREATE TABLE "+TABLE_SUBJECT+" ( id integer primary key autoincrement," +
            "name varchar(255) not null, lecture varchar(255) not null, price integer not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void showCourse (ArrayList<Subject> subjects) {
        db = this.getReadableDatabase();

        String selectQuery = "SELECT price, name, lecture FROM subject";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                subjects.add(new Subject(
                        c.getInt(0),        // price
                        c.getString(1),     // name
                        c.getString(2)      // lecture
                ));

            } while (c.moveToNext());
        }
    }

    public int insertUser(User user) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getFullName());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PHONE, user.getPhoneNumber());
        if (db.insert(TABLE_USER, null, contentValues) == -1) {
            Log.d("sad", "sudah di masukkan");
            return -1;
        }
        else return 1;
    }

    public int insertSubject(Subject subject) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", subject.getName());
        contentValues.put("price", subject.getPrice());
        contentValues.put("lecture", subject.getLecture());

        if (db.insert(TABLE_SUBJECT, null, contentValues) == -1) {
            Log.d("sad", "sudah di masukkan");
            return -1;
        }
        else return 1;
    }

    public String selectUser(String email, String pass) {
        db = this.getReadableDatabase();
        String query = "select email, password from "+TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(email)) {
                    b = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return b;
    }

    public User getInfo(String email) {
        db = this.getReadableDatabase();
        String query = "select * from "+TABLE_USER+" where email ='"+email+"'";
        Cursor cursor = db.rawQuery(query, null);
        String name = "", email1 = "", pass ="", phone ="";
        while (cursor.moveToNext()) {
            email1 = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
        }
        User u = new User(name, email1, pass, phone);
        return u;
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        sqLiteDatabase.execSQL(TABLE_CREATE_SUBJECT);
        this.db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS "+TABLE_USER+","+TABLE_SUBJECT;
        sqLiteDatabase.execSQL(query);
        this.onCreate(sqLiteDatabase);
    }
}

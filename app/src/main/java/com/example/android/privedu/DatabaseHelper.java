package com.example.android.privedu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Abay on 3/2/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "privedu.db";
    private static final String TABLE_USER = "user";
    private static final String TABLE_SUBJECT = "subject";
    private static final String TABLE_TEACHER = "teacher";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table "+TABLE_USER+" (id integer primary key autoincrement," +
            "name varchar(255) not null, email varchar(255) not null unique, password varchar(255) not null, phone varchar(255) not null, role varchar(255) not null);";
    String TABLE_CREATE_SUBJECT = "CREATE TABLE "+TABLE_SUBJECT+" ( id integer primary key autoincrement," +
            "name varchar(255) not null, price integer not null, id_teacher integer, foreign key(id_teacher) references "+TABLE_TEACHER+"(id));";
    String TABLE_CREATE_TEACHER = "CREATE TABLE "+TABLE_TEACHER+" ( id integer primary key autoincrement," +
            "name varchar(255) not null, email varchar(255) not null, phone varchar(255) not null, skill varchar(255) not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*
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
    */

    public String getUserName(int id){
        db = this.getReadableDatabase();
        String res = "";
        String selectQuery = "SELECT name FROM user where id = "+id+"";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                res = c.getString(0);
            } while (c.moveToNext());
        }

        return res;
    }

    public ArrayList<String> showTeacher () {
        ArrayList<String> strings = new ArrayList<>();
        db = this.getReadableDatabase();

        String selectQuery = "SELECT distinct name from teacher";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                strings.add(c.getString(0));

            } while (c.moveToNext());
        }
        return strings;
    }

    public int insertUser(User user) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getFullName());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PHONE, user.getPhoneNumber());
        contentValues.put("role", user.getRole());
        if (db.insert(TABLE_USER, null, contentValues) == -1) {
            Log.d("sad", "sudah di masukkan");
            return -1;
        }
        else return 1;
    }

    public boolean deleteAccount(int id_ac)
    {
        return db.delete("user", "id" + "=" + id_ac, null) > 0;
    }

    public boolean deleteSubject(int id_ac)
    {
        return db.delete("subject", "id_teacher" + "=" + id_ac, null) > 0;
    }

    public boolean deleteSubject1(String name)
    {
        return db.delete("subject", "name" + "='" + name+"'", null) > 0;
    }

    public void updateInfo(int id, String name, String email, String password, String phone) {
        ContentValues cv = new ContentValues();
        cv.put("name", name); //These Fields should be your String values of actual column names
        cv.put("email", email);
        cv.put("password", password);
        cv.put("phone", phone);
        db.update("user", cv, "id" + "=" + id,null);
    }

    public void updateSubject(int id, String name, int price) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name); //These Fields should be your String values of actual column names
        cv.put("price", Integer.valueOf(price));
        db.update("subject", cv, "id" + "=" + id,null);
    }

    public int insertTeacher(Teacher teacher) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", teacher.getFullName());
        contentValues.put("phone", teacher.getPhoneNumber());
        contentValues.put("email", teacher.getEmail());
        contentValues.put("skill", teacher.getSkill());
        if (db.insert(TABLE_TEACHER, null, contentValues) == -1) {
            return -1;
        }
        else return 1;
    }

    public int getIdTeacher(String name){
        db = this.getReadableDatabase();
        int res = 0;
        String selectQuery = "SELECT id FROM user where name = '"+name+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                res = c.getInt(0);
            } while (c.moveToNext());
        }
        return res;
    }

    public int getIdSubject(String name){
        db = this.getReadableDatabase();
        int res = 0;
        String selectQuery = "SELECT id FROM subject where name = '"+name+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                res = c.getInt(0);
            } while (c.moveToNext());
        }
        return res;
    }


    public int insertSubject(Subject subject) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", subject.getName());
        contentValues.put("price", subject.getPrice());
        contentValues.put("id_teacher", subject.getId_teacher());

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
        String name = "", email1 = "", pass ="", phone ="", role="";
        while (cursor.moveToNext()) {
            email1 = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            role = cursor.getString(cursor.getColumnIndex("role"));
        }
        User u = new User(name, email1, pass, phone, role);
        return u;
    }

    public String getPhoneNumb(String email) {

        return "";
    }
    public User getInfo1(String name) {
        db = this.getReadableDatabase();
        String query = "select * from "+TABLE_USER+" where name ='"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        String name1 = "", email = "", pass ="", phone ="", role="";
        while (cursor.moveToNext()) {
            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            name1 = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            role = cursor.getString(cursor.getColumnIndex("role"));
        }
        User u = new User(name1, email, pass, phone, role);
        return u;
    }

    public ArrayList<String> getSkill(int id_teacher) {
        ArrayList<String> strings = new ArrayList<>();
        db = this.getReadableDatabase();

        String selectQuery = "SELECT distinct name from subject where id_teacher ="+id_teacher;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                strings.add(c.getString(0));

            } while (c.moveToNext());
        }
        return strings;
    }

    public Teacher getInfoTeacher(String name) {
        db = this.getReadableDatabase();
        String query = "select * from "+TABLE_TEACHER+" where name ='"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        String name1 = "", email = "", skill ="", phone ="";
        while (cursor.moveToNext()) {
            email = cursor.getString(cursor.getColumnIndex("email"));
            name1 = cursor.getString(cursor.getColumnIndex("name"));
            skill = cursor.getString(cursor.getColumnIndex("skill"));
            phone = cursor.getString(cursor.getColumnIndex("phone"));
        }

        return new Teacher(name1, email, phone, skill);
    }

    public User getInfoStudent(String email) {
        db = this.getReadableDatabase();
        String query = "select * from "+TABLE_USER+" where email ='"+email+"'";
        Cursor cursor = db.rawQuery(query, null);
        String name = "", email1 = "", password ="", phone ="";
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
            email1 = cursor.getString(cursor.getColumnIndex("email"));
            password = cursor.getString(cursor.getColumnIndex("password"));
            phone = cursor.getString(cursor.getColumnIndex("phone"));
        }

        return new User(name, email1, password, phone, "");
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
        sqLiteDatabase.execSQL(TABLE_CREATE_TEACHER);
        this.db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS "+TABLE_USER+","+TABLE_SUBJECT+","+TABLE_TEACHER;
        sqLiteDatabase.execSQL(query);
        this.onCreate(sqLiteDatabase);
    }
}

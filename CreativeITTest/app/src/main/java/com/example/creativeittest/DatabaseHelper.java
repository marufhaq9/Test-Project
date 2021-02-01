package com.example.creativeittest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.webkit.CookieManager;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    //
    public static String DATABASE_NAME = "yotta_database";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWORD = "password";

    //db path
    private static String DB_PATH = "/data/data/MyDataBase/databases/";
    private SQLiteDatabase myDataBase;

    public SQLiteDatabase openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase;
    }
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT, " + KEY_EMAIL + " TEXT, "
            + KEY_PHONE + " TEXT, " + KEY_PASSWORD + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_USERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USERS + "'");
        onCreate(db);
    }
    public int addUser(String name, String email, String phone, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        int l;
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        values.put(KEY_PASSWORD, pass);
        // insert row in users table
        try {
            db.insert(TABLE_USERS, null, values);
            l=1;
        }catch (Exception e){
            Log.d("TAG", String.valueOf(e));
            l=0;
        }
        return l;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_USERNAME)));
                userModel.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                userModel.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
                userModel.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                // adding to Users list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void getSingleUser(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "+ KEY_USERNAME + "='"+user + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_USERNAME)));
                userModel.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                userModel.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
                userModel.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            } while (c.moveToNext());
        }
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        //db = openDataBase();

        String selection = "username=? and password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    public int updateUser(int id, String name, String lname, String varsity, String dept) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_PASSWORD, lname);
        values.put(KEY_EMAIL, varsity);
        values.put(KEY_PHONE, dept);
        // update row in users table base on users.is value
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}

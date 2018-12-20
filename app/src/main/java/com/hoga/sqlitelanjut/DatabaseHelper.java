package com.hoga.sqlitelanjut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String TABLE_POSITION = "position";

    private static final String TABLE_USER_DEPARTMENT = "department";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_FIRSTNAME = "name";
    private static final String KEY_HOBBY = "hobby";
    private static final String KEY_CITY = "city";

    private static final String KEY_ID_DEPARTMENT = "id_department";
    private static final String KEY_DEPARTMENT = "department";

    /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID_USER
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME +" TEXT,"+ KEY_HOBBY +" TEXT,"+ KEY_CITY + " TEXT );";

    private static final String CREATE_TABLE_POSITION = "CREATE TABLE "+TABLE_POSITION+" ("
            +KEY_ID_USER+" INTEGER,"
            +KEY_ID_DEPARTMENT+" INTEGER,"
            +" FOREIGN KEY ("+KEY_ID_USER+") REFERENCES "+TABLE_USER+"("+KEY_ID_USER+"),"
            +" FOREIGN KEY ("+KEY_ID_DEPARTMENT+") REFERENCES "+TABLE_USER_DEPARTMENT+"("+KEY_ID_DEPARTMENT+"))";

    private static final String CREATE_TABLE_USER_DEPARTMENT = " CREATE TABLE "
            + TABLE_USER_DEPARTMENT + "(" + KEY_ID_DEPARTMENT + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DEPARTMENT + " TEXT );";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_POSITION);
        db.execSQL(CREATE_TABLE_USER_DEPARTMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_POSITION + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER_DEPARTMENT + "'");
        onCreate(db);
    }

    public void addUser(String name, String hobby, String city ,String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        values.put(KEY_HOBBY, hobby);
        values.put(KEY_CITY, city);
        // db.insert(TABLE_USER, null, values);
        long id_users = db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        String selectQuery = "SELECT  * FROM " + TABLE_USER_DEPARTMENT+" WHERE "+KEY_DEPARTMENT+"='"+department+"'";
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c = db2.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        String id_department = "";
        if (c.moveToFirst()) {
            do {
                id_department = c.getString(c.getColumnIndex(KEY_ID_DEPARTMENT));
            } while (c.moveToNext());
        }

        //adding user city in users_city table
        ContentValues valuesCity = new ContentValues();
        valuesCity.put(KEY_ID_USER, id_users);
        valuesCity.put(KEY_ID_DEPARTMENT, Integer.valueOf(id_department));
        db.insert(TABLE_POSITION, null, valuesCity);
    }

    public void addDepartment(String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_DEPARTMENT, department);
        // db.insert(TABLE_USER, null, values);
        long id_users = db.insertWithOnConflict(TABLE_USER_DEPARTMENT, null, values, SQLiteDatabase.CONFLICT_IGNORE);

    }

    public ArrayList<String> getAllDepartment() {
        ArrayList<String> userModelArrayList = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER_DEPARTMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                userModelArrayList.add(c.getString(c.getColumnIndex(KEY_DEPARTMENT)));
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " LEFT JOIN "+TABLE_POSITION+" ON "+TABLE_USER+"."+KEY_ID_USER+"="+TABLE_POSITION+"."+KEY_ID_USER+" LEFT JOIN "+TABLE_USER_DEPARTMENT+" ON "+TABLE_POSITION+"."+KEY_ID_DEPARTMENT+"="+TABLE_USER_DEPARTMENT+"."+KEY_ID_DEPARTMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId_users(c.getInt(c.getColumnIndex(KEY_ID_USER)));
                userModel.setNama(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));
                userModel.setHobby(c.getString(c.getColumnIndex(KEY_HOBBY)));
                userModel.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
                userModel.setDepartment(c.getString(c.getColumnIndex(KEY_DEPARTMENT)));

                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }
    public void updateUser(int id, String name, String hobby, String city) {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        values.put(KEY_HOBBY, hobby);
        values.put(KEY_CITY, city);
        db.update(TABLE_USER, values, KEY_ID_USER + " = ?", new String[]{String.valueOf(id)});

    }

    public void deleteUSer(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting from users table
        db.delete(TABLE_USER, KEY_ID_USER + " = ?",new String[]{String.valueOf(id)});
    }
}

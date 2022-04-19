package com.example.binkhathlan_midt2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="m2.db";
    public static final String TABLE_NAME="Person";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "SURNAME";
    public static final String COL4 = "NID";

    //Constructor
    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY," +
                " NAME TEXT NOT NULL," +
                " SURNAME TEXT NOT NULL," +
                " NID INTEGER NOT NULL);";
        db.execSQL(createTable);
    }

    //Every time the dB is updated (or upgraded)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String id, String name,String surname, String nid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, name);
        contentValues.put(COL3, surname);
        contentValues.put(COL4, nid);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    public Cursor getSpecificProduct(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where ID=?", new String[]{id});
        data.moveToFirst();
        if(check(data)) {
            data.moveToFirst();
            String dataID = data.getString(0);
            String dataName = data.getString(1);
            String dataSurname = data.getString(2);
            String dataNID = data.getString(3);
            return data;
        }
        return null;
    }
    public boolean deleteSpecificProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = db.delete(TABLE_NAME, COL1 + " = ?", new String[]{id});
        return d == 1;
    }
    public boolean deleteField(String input) {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = db.delete(TABLE_NAME, COL1 + " = ? ||"+COL2 + " = ? ||"+COL3 + " = ? ||"+COL4 + " = ? ||", new String[]{input});
        return d == 1;
    }
    public boolean check (Cursor cursor){
        if(cursor.getCount()!=0)
            return true;
        return false;
    }
    public Cursor ViewTable () {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return x;
    }

}
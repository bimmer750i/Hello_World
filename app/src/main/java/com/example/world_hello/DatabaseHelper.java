package com.example.world_hello;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notification list updated";
    public static final String CONTACTS_TABLE_NAME = "Push_details_with_time";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ CONTACTS_TABLE_NAME +"(id integer primary key, Time text, Title text, Text text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_TABLE_NAME);
        onCreate(db);
    }
    public boolean insert(String s, String s1, String s2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Time", s);
        contentValues.put("Title", s1);
        contentValues.put("Text", s2);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }
}

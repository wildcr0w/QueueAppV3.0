package com.yashodainfotech.queuingapp.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Q.db";
    public static final String CART_TABLE_NAME = "finishtime";

    public static final String CART_COLUMN_id = "id1";
    public static final String CART_COLUMN_idtable = "idtable";
    public static final String CART_COLUMN_time = "time";


    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table finishtime " +
                        "(id integer primary key,id1 text,idtable text,time text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS finishtime");
    }

    public boolean insertIdtime(String Id,String time,String idtable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id1", Id);
        contentValues.put("time", time);
        contentValues.put("idtable", idtable);
        db.insert("finishtime", null, contentValues);
        return true;
    }

    public Cursor getid(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from finishtime where id1='"+id+"'",null);
        return cursor;
    }
    public Cursor getidtable(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from finishtime where idtable='"+id+"'",null);
        return cursor;
    }

    public Integer deleteId (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("finishtime",
                "idtable = ? ",
                new String[] { Integer.toString(id) });
    }
}

package com.example.addressbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Main.db";
    public static final String TABLE_NAME = "userInfo";
    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME +
                    "(_id integer primary key autoincrement," +
                    "name varchar(10)," +
                    "number varchar(20))";


    public MyOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据表
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.i("MyOpenHelper---", "create table success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据表
    }
}

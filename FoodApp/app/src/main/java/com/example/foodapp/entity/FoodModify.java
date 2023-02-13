package com.example.foodapp.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodapp.db.DBHelper;
import com.example.foodapp.models.Food;

public class FoodModify {
    public static final String QUERY_CREATE_TABLE = "CREATE TABLE food(\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\ttitle varchar(50),\n" +
            "\tthumbnail varchar(100),\n" +
            "\tcontent text,\n" +
            "\tprice integer\n" +
            ")";

    public static Cursor findAll(){
        String sql = "select * from food";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        return cursor;
    }

    public static void insert(Food food){
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("thumbnail", food.getThumbnail());
        values.put("title", food.getTitle());
        values.put("content", food.getContent());
        values.put("price", food.getPrice());

        sqLiteDatabase.insert("food", null, values);
    }

    public static void update(Food food){
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("thumbnail", food.getThumbnail());
        values.put("title", food.getTitle());
        values.put("content", food.getContent());
        values.put("price", food.getPrice());

        sqLiteDatabase.update("food", values,"_id = " + food.getId(), null);
    }

    public static void delete(int id){
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        sqLiteDatabase.delete("food", "_id = "+ id, null);
    }
}

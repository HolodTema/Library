package com.terabyte.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.TreeSet;

public class DatabaseClient {


    public static TreeSet<Book> selectAllBooks(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        TreeSet<Book> result = new TreeSet<>();
        Cursor cursor = db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            result.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4)));
        }
        db.close();
        cursor.close();
        return result;
    }

    public static Book selectBookById(Context context, int bookId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " + DatabaseHelper.COLUMN_ID + "=?", new String[] {String.valueOf(bookId)});
        cursor.moveToFirst();
        Book result = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
        db.close();
        cursor.close();
        return result;
    }

    public static void deleteBookById(Context context, int bookId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }

    public static void updateBookById(Context context, ContentValues contentValues, int bookId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update(DatabaseHelper.TABLE, contentValues, DatabaseHelper.COLUMN_ID + "=" + bookId, null);
        db.close();
    }

    public static void insertBook(Context context, ContentValues contentValues) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.insert(DatabaseHelper.TABLE, null, contentValues);
        db.close();
    }
}

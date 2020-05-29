package com.example.ham.learn_e_2000_mk_liv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class sql extends SQLiteOpenHelper {
    public static final String Dbname = "data.db";

    public sql(Context context) {
        super(context, Dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mydata ( words TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mydata");//هذا يضمن انو  هذه القاعده فيها  هذا الجدول
        onCreate(sqLiteDatabase);
    }

    public boolean insert_words(String the_word) {
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("words", the_word);
        long result = dp.insert("mydata", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList get_wordss() {
        ArrayList<String> array = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from mydata", null);
        res.moveToFirst();//اذهب لاول حقل
        while (res.isAfterLast() == false) {
            String words = res.getString(res.getColumnIndex("words"));
            array.add(words);
            res.moveToNext();
        }
        return array;
    }
}



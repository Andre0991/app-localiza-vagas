package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andreperictavares on 4/11/2016.
 */

public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME    = "ParkingAppDB";
    public static final String TABLE_NAME = "Car";
    public static final int    DB_VERSION = 1;


    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory,
                 int version) {
        super(context, name, factory, version);

    }

    @Override
    // TODO: ver direito cada tipo de dado
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE " + TABLE_NAME + " (" +
                " id       integer," +
                " model    varchar(50), " +
                " colour   varchar(50), " +
                " plate    varchar(10)) ";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }
}

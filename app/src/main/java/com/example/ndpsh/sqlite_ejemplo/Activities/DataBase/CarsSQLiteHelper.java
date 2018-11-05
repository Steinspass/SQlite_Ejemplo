package com.example.ndpsh.sqlite_ejemplo.Activities.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  Created by Naim on 5/11/18
 */

public class CarsSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tablas
    String sqlCreate = "CREATE TABLE Cars(VIN INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, color TEXT)";

    public CarsSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Cars");

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }
}

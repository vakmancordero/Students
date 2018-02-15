package com.kaizensoftware.students.dao.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kaizensoftware.students.db.util.DBContract;

/**
 * Created by Arturo Cordero
 */
public abstract class BaseDAO extends SQLiteOpenHelper {

    private String table;
    private String creationQuery;

    public BaseDAO(Context context, SQLiteDatabase.CursorFactory factory, int version, String table, String creationQuery) {
        super(context, DBContract.database_name, factory, version);

        this.table = table;
        this.creationQuery = creationQuery;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        this.createTables(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);

            onCreate(sqLiteDatabase);

        }

    }

    private void createTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.creationQuery);
    }

}

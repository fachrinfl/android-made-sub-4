package com.fachrinfl.movie.features.favourite.db.tv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbtv";
    private static final int DATABASE_VERSION =1;

    private static final String SQL_CRATE_TABLE_TV = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TV,
            DatabaseContract.TvColums._ID,
            DatabaseContract.TvColums.TITLE,
            DatabaseContract.TvColums.OVERVIERW,
            DatabaseContract.TvColums.BACKDROP_PATH,
            DatabaseContract.TvColums.POSTER_PATH,
            DatabaseContract.TvColums.RELEASE_DATE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CRATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}

package com.fachrinfl.movie.features.favourite.db.tv;

import android.database.Cursor;
import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_TV = "tv";

    static final class TvColums implements BaseColumns {
        static String TITLE = "title";
        static String OVERVIERW = "overview";
        static String BACKDROP_PATH = "backdrop_path";
        static String POSTER_PATH = "poster_path";
        static String RELEASE_DATE = "release_date";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}

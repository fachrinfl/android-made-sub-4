package com.fachrinfl.movie.features.favourite.db.tv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fachrinfl.movie.features.tv.model.Tv;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract.TABLE_TV;
import static com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract.TvColums.BACKDROP_PATH;
import static com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract.TvColums.OVERVIERW;
import static com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract.TvColums.POSTER_PATH;
import static com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract.TvColums.RELEASE_DATE;
import static com.fachrinfl.movie.features.favourite.db.tv.DatabaseContract.TvColums.TITLE;

public class TvHelper {

    private static String DATABASE_TABLE = TABLE_TV;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public TvHelper(Context context) {
        this.context = context;
    }

    public TvHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public ArrayList<Tv> getAllTvs(){
        ArrayList<Tv> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        Tv tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new Tv();
                tv.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tv.setOriginalName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tv.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIERW)));
                tv.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                tv.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                tv.setFirstAirDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                arrayList.add(tv);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return sqLiteDatabase.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(Tv tv) {
        ContentValues args = new ContentValues();
        args.put(_ID, tv.getId());
        args.put(TITLE, tv.getOriginalName());
        args.put(OVERVIERW, tv.getOverview());
        args.put(BACKDROP_PATH, tv.getBackdropPath());
        args.put(POSTER_PATH, tv.getPosterPath());
        args.put(RELEASE_DATE, tv.getFirstAirDate());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}

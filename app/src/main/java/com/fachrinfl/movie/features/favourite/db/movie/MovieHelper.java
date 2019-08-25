package com.fachrinfl.movie.features.favourite.db.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fachrinfl.movie.features.movie.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.fachrinfl.movie.features.favourite.db.movie.DatabaseContract.MovieColums.BACKDROP_PATH;
import static com.fachrinfl.movie.features.favourite.db.movie.DatabaseContract.MovieColums.OVERVIERW;
import static com.fachrinfl.movie.features.favourite.db.movie.DatabaseContract.MovieColums.POSTER_PATH;
import static com.fachrinfl.movie.features.favourite.db.movie.DatabaseContract.MovieColums.RELEASE_DATE;
import static com.fachrinfl.movie.features.favourite.db.movie.DatabaseContract.MovieColums.TITLE;
import static com.fachrinfl.movie.features.favourite.db.movie.DatabaseContract.TABLE_MOVIE;
import static com.fachrinfl.movie.features.movie.view.activity.MovieActivity.OVERVIEW;

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public ArrayList<Movie> getAllMovies(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIERW)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                arrayList.add(movie);
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

    public long insertProvider(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(BACKDROP_PATH, movie.getBackdropPath());
        args.put(POSTER_PATH, movie.getPosterPath());
        args.put(RELEASE_DATE, movie.getReleaseDate());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }

}

package sg.edu.rp.c346.id22021421.movies;
import  android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;



public class DBHelper extends SQLiteOpenHelper {
    // Start version with 1
    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 1;
    // Filename of the database
    private static final String DATABASE_NAME = "movie.db";
    private static final String TABLE_MOVIE = "movie";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG = "song";
    private static final String COLUMN_SINGERS = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_RATING = "rating";



    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_MOVIE +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " INTEGER,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_RATING + " TEXT,"
                + COLUMN_SONG + " TEXT)";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        // Create table(s) again
        onCreate(db);
    }
    public long insertSongs(String song, Integer date, String name, String ratings){

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the song as value
        values.put(COLUMN_SONG,song);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_SINGERS, name);
        values.put(COLUMN_RATING, ratings);
        // Insert the row into the TABLE_TASK
        long result = db.insert(TABLE_MOVIE, null, values);
        // Close the database connection
        db.close();
        return result;
    }
    @SuppressLint("Range")
    public ArrayList<String> getSongContent() {
        ArrayList<String> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_SINGERS, COLUMN_DATE, COLUMN_SONG, COLUMN_RATING};
        Cursor cursor = db.query(TABLE_MOVIE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                songs.add(cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Movie> get_Songs() {
        ArrayList<Movie> songs = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_SONG, COLUMN_DATE, COLUMN_RATING,COLUMN_SINGERS};
        Cursor cursor = db.query(TABLE_MOVIE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String song = cursor.getString(1);
                int date = cursor.getInt(2);
                String rating = cursor.getString(3);
                String name = cursor.getString(4);

                Movie obj = new Movie(id,name, song, date,rating);
                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
    public ArrayList<Movie> get_Songs(String keyword) {
        ArrayList<Movie> songs = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_SONG, COLUMN_DATE, COLUMN_RATING,COLUMN_SINGERS};
        String condition = COLUMN_RATING + " LIKE ? ";
        String[] args = {"%" + keyword +"%"};
        Cursor cursor = db.query(true,TABLE_MOVIE, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String song = cursor.getString(1);
                int date = cursor.getInt(2);
                String rating = cursor.getString(3);
                String name = cursor.getString(4);

                Movie obj = new Movie(id,name, song, date,rating);
                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }


    public ArrayList<String> getYears() {
        ArrayList<String> years = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RATING};

        Cursor cursor = db.query(true, TABLE_MOVIE, columns, null, null, null, null, COLUMN_DATE + " ASC", null);
        if (cursor.moveToFirst()) {
            do {
                String rating = cursor.getString(0);
                years.add(String.valueOf(rating));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return years;
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + " = ?";
        String[] args = { String.valueOf(id) };
        int result = db.delete(TABLE_MOVIE, condition, args);

        if (result < 1) {
            Log.d("DBHelper", "Delete failed");
        } else {
            Log.d("DBHelper", "Delete successful");
        }

        db.close();
        return result;
    }
    public int updateSong(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SONG, data.getGenre());
        values.put(COLUMN_SINGERS, data.getname());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_RATING, data.getRatings());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_MOVIE, values, condition, args);

        db.close();
        return result;


    }




}

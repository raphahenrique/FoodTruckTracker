package br.ufscar.auxiliares;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tracker.db";

    public DBHandler(Context c, String name, SQLiteDatabase.CursorFactory fact, int version) {
        super(c, DATABASE_NAME, fact, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query;
        // Food Trucks
        query = "CREATE TABLE foodtrucks (" +
                    "id INTEGER PRIMARY KEY ASC," +
                    "nome TEXT," +
                    "priceRange INTEGER," +
                    "coverPicture BLOB," +
                    "byOwner INTEGER" +
                ");";
        sqLiteDatabase.execSQL(query);

        // Food Truck Tags
        query = "CREATE TABLE tags (" +
                    "name TEXT," +
                    "truckId INTEGER," +
                    "FOREIGN KEY (truckId) REFERENCES foodtrucks (id)" +
                ");";
        sqLiteDatabase.execSQL(query);

        // Menu Entries
        query = "CREATE TABLE menuEntry ("+
                    "id INTEGER PRIMARY KEY ASC," +
                    "name TEXT," +
                    "price REAL," +
                    "description TEXT," +
                    "picture BLOB" +
                    "truckId INTEGER" +
                    "FOREIGN KEY (truckId) REFERENCES foodtrucks (id)" +
                ");";
        sqLiteDatabase.execSQL(query);

        // Menu Entries' Ingredients
        query = "CREATE TABLE ingredients (" +
                    "name TEXT," +
                    "entryId INT," +
                    "FOREIGN KEY (entryId) REFERENCES menuEntry (id)" +
                ");";
        sqLiteDatabase.execSQL(query);

        // Food Truck Locations
        query = "CREATE TABLE locations (" +
                    "startDate TEXT," +
                    "endDate TEXT," +
                    "latitude TEXT," +
                    "longitude TEXT," +
                    "opensAt TEXT," +
                    "closesBy TEXT," +
                    "truckId INTEGER," +
                    "FOREIGN KEY (truckId) REFERENCES foodtrucks (id)" +
                ");";
        sqLiteDatabase.execSQL(query);

        // Food Truck Reviews
        query = "CREATE TABLE reviews (" +
                    "rating INTEGER," +
                    "comment TEXT," +
                    "date TEXT," +
                    "picture BLOB" +
                    "truckId INTEGER," +
                    "FOREIGN KEY (truckId) REFERENCES foodtrucks (id)" +
                ");";
        sqLiteDatabase.execSQL(query);

        // TODO: User information
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

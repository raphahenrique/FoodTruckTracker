package br.ufscar.auxiliares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

import br.ufscar.foodtruck.FoodTruckLocation;
import br.ufscar.foodtruck.FoodTruckTag;
import br.ufscar.foodtruck.MenuEntry;
import br.ufscar.foodtruck.Truck;

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
        query = "CREATE TABLE trucks (" +
                    "id INTEGER PRIMARY KEY ASC," +
                    "name TEXT," +
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
                    "closesAt TEXT," +
                    "score INTEGER," +
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

        query = "CREATE TABLE users (" +
                    "id TEXT," +
                    "name TEXT," +
                    "email TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);

        // TODO: User information
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveTruck(Truck t) {
        if (t.getId() == -1) {
            // In case truck doesn't exist yet
            ContentValues values = new ContentValues();

            // Trucks table
            values.put("name", t.getName());
            values.put("priceRange", t.getPriceRange());
            values.put("coverPicture", Convert.bitmapToBlob(t.getCoverPicture()));
            values.put("byOwner", t.isByOwner());

            SQLiteDatabase db = getWritableDatabase();
            db.insert("trucks", null, values);

            int truckID = db.rawQuery("SELECT last_insert_rowid()", null).getInt(0);

            // Tags table
            for (FoodTruckTag tag : t.getTags()) {
                values = new ContentValues();
                values.put("name", tag.getName());
                values.put("truckId", truckID);

                db.insert("tags", null, values);
            }

            // Locations table
            for (FoodTruckLocation l : t.getLocations()) {
                values = new ContentValues();
                values.put("startDate", l.getStartDate().toString());
                values.put("endDate", l.getEndDate().toString());
                values.put("latitude", l.getLocation().latitude);
                values.put("longitude", l.getLocation().longitude);
                values.put("opensAt", l.getOpensAt().toString());
                values.put("closesAT", l.getClosesAt().toString());
                values.put("score", l.getScore());
                values.put("truckId", truckID);

                db.insert("locations", null, values);
            }

            // Menu entries table
            for (MenuEntry e : t.getMenu()) {
//                "name TEXT," +
//                        "entryId INT," +
                values = new ContentValues();
//                values.put("name", e.get);
            }

            // Reviews table

            db.close();
        } else {
            // Update truck

        }
    }

    public List<Truck> getAllTrucks() {
        List<Truck> trucks = new LinkedList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM trucks", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    //"CREATE TABLE trucks (" +
//                    "id INTEGER PRIMARY KEY ASC," +
//                            "name TEXT," +
//                            "priceRange INTEGER," +
//                            "coverPicture BLOB," +
//                            "byOwner INTEGER" +
//                            ");";
                    // Truck(String name, LatLng location, int priceRange, Collection<FoodTruckTag> tags)
//                    trucks.add(new Truck(c.getString(0),  c.getString()));
                } while (c.moveToNext());
            }
        }

        return trucks;
    }

//    // delete
//    SQLiteDatabase db = getWritableDatabase();
//    db.execSQL("DELETE FROM table WHERE ...");

}

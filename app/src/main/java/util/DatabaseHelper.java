package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.FuelModel;

/**
 * Created by mert.karsi on 20.08.2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BenzinMazotFiyatlariDB";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createFuelPricesTable = "CREATE TABLE fuel_prices( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fuel TEXT, "+
                "city TEXT," +
                "price TEXT," +
                "insert_date TEXT )";

        db.execSQL(createFuelPricesTable);
        Log.d("Database", "database onCreate oldu");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fuel_prices");
        this.onCreate(db);
        Log.d("Database", "database onUpgrade oldu");
    }

    public boolean updateFuelPrices(FuelModel fuelModel){

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("addFuel", fuelModel.toString());
        ContentValues values = new ContentValues();
        values.put("fuel", fuelModel.getFuelName());
        values.put("city", "Ankara");
        values.put("price", fuelModel.getFuelPrice());
        values.put("insert_date", "2014-08-21");
        db.insert("fuel_prices", null, values);
        return false;
    }

    public List<FuelModel> getAllRows(){
        List<FuelModel> fuelList = new ArrayList<FuelModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("fuel_prices", null, null, null, null, null, null);

        FuelModel fuelModel;
        while(cursor.moveToNext()){
            fuelModel = new FuelModel();
            fuelModel.setFuelName(cursor.getString(1) != null ? cursor.getString(1) : "");
            fuelModel.setCity(cursor.getString(2) != null ? cursor.getString(2) : "");
            fuelModel.setFuelPrice(cursor.getString(3) != null ? cursor.getString(3) : "");
            fuelList.add(fuelModel);
        }

        return fuelList;
    }

    public void deleteAllRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM fuel_prices");
    }
}
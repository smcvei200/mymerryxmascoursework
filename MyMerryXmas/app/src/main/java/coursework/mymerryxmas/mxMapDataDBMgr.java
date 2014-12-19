package coursework.mymerryxmas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rla on 10/10/2014.
 */
public class mxMapDataDBMgr extends SQLiteOpenHelper {

    private static final int DB_VER = 1;
    private static final String DB_PATH = "/data/data/coursework.mymerryxmas/databases/";
    private static final String DB_NAME = "mapElves.s3db";
    private static final String TBL_MAPELVESTABLE = "mapElvesTable";


    public static final String COL_FIRSTNAME = "FirstName";
    public static final String COL_OCCUPATION = "Occupation";
    public static final String COL_YEARSEMPLOYED = "YearsEmployed";


    private final Context appContext;

    public mxMapDataDBMgr(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MAPELVESTABLE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_MAPELVESTABLE + "("
                +  COL_FIRSTNAME + " TEXT,"
                + COL_OCCUPATION + " TEXT"
                + COL_YEARSEMPLOYED + " INTEGER" +")";
        db.execSQL(CREATE_MAPELVESTABLE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_MAPELVESTABLE);
            onCreate(db);
        }
    }

    // ================================================================================
    // Creates a empty database on the system and rewrites it with your own database.
    // ================================================================================
    public void dbCreate() throws IOException {

        boolean dbExist = dbCheck();

        if(!dbExist){
            //By calling this method an empty database will be created into the default system path
            //of your application so we can overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDBFromAssets();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    // ============================================================================================
    // Check if the database already exist to avoid re-copying the file each time you open the application.
    // @return true if it exists, false if it doesn't
    // ============================================================================================
    private boolean dbCheck(){

        SQLiteDatabase db = null;

        try{
            String dbPath = DB_PATH + DB_NAME;
            Log.e("SQLHelper","dbPath");
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            Log.e("SQLHelper","db");
            db.setLocale(Locale.getDefault());
            Log.e("SQLHelper","setLocale");
            db.setVersion(1);
            Log.e("SQLHelper","setVersion");

        }catch(SQLiteException e){

            Log.e("SQLHelper","Database not Found!");

        }

        if(db != null){

            db.close();

        }

        return db != null ? true : false;
    }

    // ============================================================================================
    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transfering bytestream.
    // ============================================================================================
    private void copyDBFromAssets() throws IOException{

        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;

        try {
            Log.e("In the try", DB_NAME);
            dbInput = appContext.getAssets().open(DB_NAME);
            Log.e("DBInput", dbInput.toString());
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
                Log.e("Out the try", DB_NAME);
            }

            //Close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        } catch (IOException e)
        {
            throw new Error("Problems copying DB!");
        }
    }


    public void addaMapElvesEntry(mxMapData aMapElves) {

        ContentValues values = new ContentValues();
        values.put(COL_FIRSTNAME, aMapElves.getFirstname());
        values.put(COL_OCCUPATION, aMapElves.getOccupation());
        values.put(COL_YEARSEMPLOYED, aMapElves.getYearsEmployed());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TBL_MAPELVESTABLE, null, values);
        db.close();
    }

    public mxMapData getMapElvesEntry(String aMapElvesEntry) {
        String query = "Select * FROM " + TBL_MAPELVESTABLE + " WHERE " + COL_FIRSTNAME + " =  \"" + aMapElvesEntry + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        mxMapData MapDataEntry = new mxMapData();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            MapDataEntry.setYearsEmployed(Integer.parseInt(cursor.getString(0)));

            MapDataEntry.setFirstname(cursor.getString(1));

            MapDataEntry.setOccupation(cursor.getString(2));

            cursor.close();
        } else {
            MapDataEntry = null;
        }
        db.close();
        return MapDataEntry;
    }

    public boolean removeaMapElvesEntry(String aMapElvesEntry) {

        boolean result = false;

        String query = "Select * FROM " + TBL_MAPELVESTABLE + " WHERE " + COL_FIRSTNAME + " =  \"" + aMapElvesEntry + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        db.close();
        return result;
    }

    //retrieve all data from database and return
    public List<mxMapData> allMapData()
    {
        String query = "Select * FROM " + TBL_MAPELVESTABLE;
        List<mxMapData> mxMapDataList = new ArrayList<mxMapData>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()==false) {
                mxMapData MapDataEntry = new mxMapData();

                MapDataEntry.setFirstname(cursor.getString(0));
                MapDataEntry.setOccupation(cursor.getString(1));
                MapDataEntry.setYearsEmployed(Integer.parseInt(cursor.getString(2)));
                mxMapDataList.add(MapDataEntry);
                cursor.moveToNext();
            }
        } else {
            mxMapDataList.add(null);
        }
        db.close();
        return mxMapDataList;
    }
}

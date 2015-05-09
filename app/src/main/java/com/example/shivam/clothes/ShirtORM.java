package com.example.shivam.clothes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shivam on 09/05/15 at 9:51 PM.
 */
public class ShirtORM {

    private static final String TAG = "ShirtORM";
    public static final String TABLE_NAME = "shirt";
    private static final String COLUMN_URI = "uri";
    private DatabaseWrapper dw;
    SQLiteDatabase myDataBase;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_URI + " TEXT)";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public int addImage(Context c,String uri) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        long shirtID = 0;
        if (isDatabaseOpened()) {
            ContentValues values = new ContentValues();
            values.put(ShirtORM.COLUMN_URI, uri);
            shirtID = myDataBase.insert(ShirtORM.TABLE_NAME, "null", values);
            Log.e(TAG, "Inserted new Shirt with ID: " + shirtID);
            myDataBase.close();
        }
        return (int) shirtID;
    }

    public ArrayList<String> getUriFromDB(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        ArrayList<String> uris = new ArrayList<String>();
        Cursor cur = myDataBase.rawQuery("SELECT * from shirt",null);
        Log.e("COUNT",String.valueOf(cur.getCount()));
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
        {
            uris.add(cur.getString(0));
        }
        System.out.println(uris);
        return uris;
    }

    public String getRandomUri(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        String randomUri = new String();
        Cursor cur = myDataBase.rawQuery("SELECT * from shirt ORDER BY RANDOM() LIMIT 1",null);
        Log.e("RANDOM",String.valueOf(cur.getCount()));
        randomUri = cur.getString(0);
        return randomUri;
    }

    public boolean isDatabaseOpened() {
        if (myDataBase == null) {
            return false;
        } else {
            return myDataBase.isOpen();
        }
    }




}
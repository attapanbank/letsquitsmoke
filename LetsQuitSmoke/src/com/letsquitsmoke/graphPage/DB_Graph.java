package com.letsquitsmoke.graphPage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DB_Graph extends SQLiteOpenHelper {
    private static final String DB_NAME = "BTS";
    private static final int DB_VERSION = 1;
    
    public static final String TABLE_NAME = "Product";

    public static final String COL_NAME = "name";
    
    public DB_Graph(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME 
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " 
                + COL_NAME + " TEXT);");
        for(int i = 0 ; i < 20 ; i++) {
            db.execSQL("INSERT INTO "+ TABLE_NAME 
                    + " (" + COL_NAME + ") VALUES ('Row" 
                    + String.valueOf(i) + "');"); 
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion
            , int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
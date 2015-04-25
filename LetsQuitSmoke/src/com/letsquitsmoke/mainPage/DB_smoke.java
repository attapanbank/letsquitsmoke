package com.letsquitsmoke.mainPage;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB_smoke extends SQLiteOpenHelper {

	private static final String DB_NAME = "LETS_QUIT_SMOKE";
	private static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "SMOKE_STAT";

	public DB_smoke(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "
				+ TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, time TEXT);");
	}

	
	// Use for clear Data
	/*
	 * SQLiteDatabase mDb;
	 * DB_smoke mHelper;
	 * mHelper = new DB_smoke(this);
	 * mDb = db_smoke.getWritableDatabase();
	 * db_smoke.onUpgrade(mDb, 1, 1);
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void InsertData() {
		SQLiteDatabase db;
		db = this.getReadableDatabase(); // Read Data
		// db.execSQL("INSERT INTO STAT (smokeTime) VALUES('')");

		ContentValues Val = new ContentValues();
		Val.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		Val.put("time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		long rows = db.insert(TABLE_NAME, null, Val);
		db.close();

		Log.d("TABLE", "Insert Data Successfully.");
	}
}

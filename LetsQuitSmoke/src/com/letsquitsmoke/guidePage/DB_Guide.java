package com.letsquitsmoke.guidePage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Guide extends SQLiteOpenHelper {

	private static final String DB_NAME = "ListData2";
	private static final int DB_VERSION = 1;

	public static final String TABLE_NAME = "ListItem";

	public static final String SymptomName = "s_name";
	public static final String SymptomFix = "s_fix";

	public DB_Guide(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		  db.execSQL("CREATE TABLE "+ TABLE_NAME 
	                +" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " 
	                + SymptomName + " TEXT, " + SymptomFix + " TEXT);");

		 db.execSQL("INSERT INTO "+ TABLE_NAME +" (" + SymptomName 
	                + ", " + SymptomFix + ") VALUES ('หงุดหงิด อารมณ์ไม่ดี', 'ให้ดื่มน้ำ ดื่มกลื่นช้าๆ หรืออาบน้ำให้เย็นชื่นใจ,ออกกำลังกายตามที่ถนัดหรือเล่นกีฬาที่ชอบ, บอกตัวเองว่าเป็นอาการปกติ ของคนเลิกบุหรี่ ไม่นานก็จะหายไป');"); 
		 
		 db.execSQL("INSERT INTO "+ TABLE_NAME +" (" + SymptomName 
	                + ", " + SymptomFix + ") VALUES ('ง่วงนอน อ่อนเพลีย  เวียนศรีษะ', 'นอนพักผ่อน,ดมยาดม,ล้างหน้า , อาบน้ำ ,ยืนตัวตรงสูดลมหายใจเข้าปอดลึกๆ แล้วผ่อนคลายหายใจออกช้าๆ ทำแบบนี้สัก 5 ครั้ง ในแต่ละช่วงและในแต่ละวันก็ทำได้หลายช่วง');");
		 
		 db.execSQL("INSERT INTO "+ TABLE_NAME +" (" + SymptomName 
	                + ", " + SymptomFix + ") VALUES ('เหงา เศร้า เบื่อ', 'ใช้ผ้าชุบน้ำเย็นเช็ดหน้า เช็ดตัวดื่มน้ำบ่อยๆ และดื่มกลืนช้าๆ ถ้าเป็นมากปรึกษาแพทย์ และเภสัชกร');");  
		 
		 db.execSQL("INSERT INTO "+ TABLE_NAME +" (" + SymptomName 
	                + ", " + SymptomFix + ") VALUES ('เป็นไข้', 'ใช้ผ้าชุบน้ำเย็นเช็ดหน้า เช็ดตัว, ดื่มน้ำบ่อยๆ และดื่มกลืนช้าๆ ');");  
		 
		 db.execSQL("INSERT INTO "+ TABLE_NAME +" (" + SymptomName 
	                + ", " + SymptomFix + ") VALUES ('หิวบ่อย', 'เป็นอาการปกติของคนเลิกบุหรี่ , รับประทานอาหารให้เป็นเวลา และพอรู้สึกอิ่มเล็กน้อยก็เลิก,หมั่นทานผักและผลไม้รสเปรี้ยว ,ออกกำลังกาย ตามที่ตัวเองชอบ ');");  
		 
		 
	}

	 public void onUpgrade(SQLiteDatabase db, int oldVersion
	            , int newVersion) {
	        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
	        onCreate(db);
	    }
}

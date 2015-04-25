package com.letsquitsmoke.guidePage;

import java.util.ArrayList;

import com.letsquitsmoke.R;
import com.letsquitsmoke.R.drawable;
import com.letsquitsmoke.R.id;
import com.letsquitsmoke.R.layout;
import com.letsquitsmoke.mainPage.MainPageActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class tabListView2 extends Activity {

	SQLiteDatabase mDb;
	DB_Guide mHelper;
	Cursor mCursor;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guidepage_activity_list2);
// Set font
    	final Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/sukhumvitlight_medium.ttf");
	   
	   
        
        mHelper = new DB_Guide(this);
		mDb = mHelper.getWritableDatabase();

		mCursor = mDb.rawQuery("SELECT * FROM " + DB_Guide.TABLE_NAME,
				null);

		ArrayList<String> dirArray = new ArrayList<String>();

		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			dirArray.add(mCursor.getString(mCursor
					.getColumnIndex(DB_Guide.SymptomName)));
			mCursor.moveToNext();
		}
        String[] list = { "หงุดหงิด อารมณ์ไม่ดี ", "ง่วงนอน อ่อนเพลีย  เวียนศรีษะ", "อาการเหงา เศร้า เบื่อ"
                    , "เป็นไข้ ครั้นเนื้อ ครั้นตัว ", "หิวบ่อย" };
        
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), list);
        
        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	
            	mCursor.moveToPosition(arg2);
            	
            	final Dialog dialog = new Dialog(tabListView2.this);
				dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.guidepage_activity_dialog_data);
				
				TextView textTHName = (TextView) dialog
						.findViewById(R.id.textS_Name);
				textTHName.setTypeface(myTypeface);
				textTHName.setText(": "
						+ mCursor.getString(mCursor
								.getColumnIndex(DB_Guide.SymptomName)));

				TextView textENName = (TextView) dialog
						.findViewById(R.id.textS_Fix);
				textENName.setTypeface(myTypeface);
				textENName.setText(""
						+ mCursor.getString(mCursor
								.getColumnIndex(DB_Guide.SymptomFix)));
				
				 ImageView buttonOK = 
	                        (ImageView)dialog.findViewById(R.id.buttonOK);
	                buttonOK.setOnClickListener(new OnClickListener() {
	                    public void onClick(View v) {
	                        dialog.cancel();
	                    }
	                });
	                
	                dialog.show();

            }
        });
    }
	// Back
	public void onBackPressed() {
		Intent intent = new Intent(tabListView2.this, MainPageActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
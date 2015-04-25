package com.letsquitsmoke.guidePage;


import com.letsquitsmoke.R;
import com.letsquitsmoke.R.layout;
import com.letsquitsmoke.mainPage.MainPageActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class tabListView1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.guidepage_activity_list1);
		 
		//set Font
			Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/sukhumvitlight_medium.ttf");
		    TextView myTextView = (TextView)findViewById(R.id.textView1);
		    myTextView.setTypeface(myTypeface);
		    
			Typeface myTypeface2 = Typeface.createFromAsset(getAssets(), "fonts/sukhumvitlight_medium.ttf");
		    TextView myTextView2 = (TextView)findViewById(R.id.textView2);
		    myTextView2.setTypeface(myTypeface2);
	        
	        
	        
	}
	public void onBackPressed() {
		Intent intent = new Intent(tabListView1.this, MainPageActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
}

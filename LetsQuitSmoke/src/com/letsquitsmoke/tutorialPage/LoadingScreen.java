package com.letsquitsmoke.tutorialPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.letsquitsmoke.R;
import com.letsquitsmoke.mainPage.MainPageActivity;

public class LoadingScreen extends Activity {

	final String keepinone = "keepinone";
	final String keep = "keep";
	String a;
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	private Handler myHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_screen);

		sp = getSharedPreferences(keepinone, Context.MODE_PRIVATE);
		editor = sp.edit();
		a = sp.getString(keep, "");
		
		Handler myHandler = new Handler();
		myHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
if(a.equals("")){
					
					Intent goMain = new Intent(getApplicationContext(),
							TutorialPageActivity.class);
					startActivity(goMain);
					
					finish();
				} else {
					
					Intent goMain = new Intent(getApplicationContext(),
							MainPageActivity.class);
					startActivity(goMain);
					finish();
				}
			}
		}, 3000);

	}
}

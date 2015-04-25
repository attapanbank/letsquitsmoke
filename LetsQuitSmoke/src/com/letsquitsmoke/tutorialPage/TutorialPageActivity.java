package com.letsquitsmoke.tutorialPage;


import com.letsquitsmoke.R;
import com.letsquitsmoke.guidePage.GuidePageActivity;
import com.letsquitsmoke.mainPage.MainPageActivity;
import com.letsquitsmoke.settingPage.SettingPageActivity;

import android.R.drawable;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TutorialPageActivity extends Activity {
	RelativeLayout layoutMenu, layoutActionBar, layoutHeader;
	ScrollView scrollView;
	
	final String keepinone = "keepinone";
	final String keep = "keep";
	String a;
	SharedPreferences sp;
	SharedPreferences.Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorialpage_activity_tutorialpage);
		
		//set Font
				Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/sukhumvitlight_medium.ttf");
			    TextView myTextView1= (TextView)findViewById(R.id.textcall);
			    TextView myTextView2 = (TextView)findViewById(R.id.textsmoke);
			    TextView myTextView3 = (TextView)findViewById(R.id.textgraph);
			    TextView myTextView4 = (TextView)findViewById(R.id.textfacebook);
			    TextView myTextView5 = (TextView)findViewById(R.id.texttutoral);
			    TextView myTextView6 = (TextView)findViewById(R.id.text_manual);
			    TextView myTextView7 = (TextView)findViewById(R.id.text_setting);
			    TextView myTextView = (TextView) findViewById(R.id.textView1);
				myTextView.setTypeface(myTypeface);
			    myTextView1.setTypeface(myTypeface);
			    myTextView2.setTypeface(myTypeface);
			    myTextView3.setTypeface(myTypeface);
			    myTextView4.setTypeface(myTypeface);
			    myTextView5.setTypeface(myTypeface);
			    myTextView6.setTypeface(myTypeface);
			    myTextView7.setTypeface(myTypeface);

		sp = getSharedPreferences(keepinone, Context.MODE_PRIVATE);
		editor = sp.edit();
		
		// Button Accept
		Button Btn = (Button) findViewById(R.id.acceptBtn);
		Btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				a = sp.getString(keep, "");
				if(a.equals("")){
					a = "notnull";
					editor.putString(keep, a);
					editor.commit();
					Intent intent = new Intent(getApplicationContext(), SettingPageActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
					startActivity(intent);
					finish();
				}
			}

		});

		Button Btn2 = (Button) findViewById(R.id.acceptBtn2);
		Btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				a = sp.getString(keep, "");
				if(a.equals("")){
					a = "notnull";
					editor.putString(keep, a);
					editor.commit();
					Intent intent = new Intent(getApplicationContext(), SettingPageActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
					startActivity(intent);
					finish();
				}
			}

		});

	
		

		// PhoneCall Button

		ImageView phonecallBtn = (ImageView) findViewById(R.id.callBtn);
		// add PhoneStateListener
		// PhoneCallListener phoneListener = new PhoneCallListener();
		// TelephonyManager telephonyManager = (TelephonyManager) this
		// .getSystemService(Context.TELEPHONY_SERVICE);
		// telephonyManager.listen(phoneListener,
		// PhoneStateListener.LISTEN_CALL_STATE);

		phonecallBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:1600"));
				startActivity(callIntent);
			}
		});

		// getActionBar().hide();
		// Set menu laout
		layoutMenu = (RelativeLayout) findViewById(R.id.layoutMenu);
		layoutActionBar = (RelativeLayout) findViewById(R.id.layoutActionBar);

		scrollView = (ScrollView) findViewById(R.id.scrollView);
		scrollView.setOnTouchListener(new OnTouchListener() {
			final int DISTANCE = 3;

			float startY = 0;
			float dist = 0;
			boolean isMenuHide = false;

			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_DOWN) {
					startY = event.getY();
				} else if (action == MotionEvent.ACTION_MOVE) {
					dist = event.getY() - startY;

					if ((pxToDp((int) dist) <= -DISTANCE) && !isMenuHide) {
						isMenuHide = true;
						hideMenuBar();
					} else if ((pxToDp((int) dist) > DISTANCE) && isMenuHide) {
						isMenuHide = false;
						showMenuBar();
					}

					if ((isMenuHide && (pxToDp((int) dist) <= -DISTANCE))
							|| (!isMenuHide && (pxToDp((int) dist) > 0))) {
						startY = event.getY();
					}
				} else if (action == MotionEvent.ACTION_UP) {
					startY = 0;
				}

				return false;
			}
		});
	}

	public int pxToDp(int px) {
		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		int dp = Math.round(px
				/ (dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

	public void showMenuBar() {
		AnimatorSet animSet = new AnimatorSet();

		ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu,
				View.TRANSLATION_Y, 0);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(layoutActionBar,
				View.TRANSLATION_Y, 0);

		animSet.playTogether(anim1, anim2);
		animSet.setDuration(300);
		animSet.start();
	}

	public void hideMenuBar() {
		AnimatorSet animSet = new AnimatorSet();

		ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu,
				View.TRANSLATION_Y, layoutMenu.getHeight());
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(layoutActionBar,
				View.TRANSLATION_Y, -layoutActionBar.getHeight());

		animSet.playTogether(anim1, anim2);
		animSet.setDuration(300);
		animSet.start();
	}

	// Phone Call
	/*
	 * private class PhoneCallListener extends PhoneStateListener { private
	 * boolean isPhoneCalling = false; String LOG_TAG = "LOGGING 123";
	 * 
	 * @Override public void onCallStateChanged(int state, String
	 * incomingNumber) { if (TelephonyManager.CALL_STATE_RINGING == state) { //
	 * phone ringing Log.i(LOG_TAG, "RINGING, number: " + incomingNumber); }
	 * 
	 * if (TelephonyManager.CALL_STATE_OFFHOOK == state) { // active
	 * Log.i(LOG_TAG, "OFFHOOK"); isPhoneCalling = true; }
	 * 
	 * if (TelephonyManager.CALL_STATE_IDLE == state) { // run when class
	 * initial and phone call ended, need detect flag // from CALL_STATE_OFFHOOK
	 * Log.i(LOG_TAG, "IDLE");
	 * 
	 * if (isPhoneCalling) { Log.i(LOG_TAG, "restart app");
	 * 
	 * // restart app Intent i = getBaseContext().getPackageManager()
	 * .getLaunchIntentForPackage( getBaseContext().getPackageName());
	 * i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(i);
	 * 
	 * isPhoneCalling = false; }
	 * 
	 * } } }
	 */
	public void onBackPressed() {
		Intent intent = new Intent(TutorialPageActivity.this, MainPageActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
}

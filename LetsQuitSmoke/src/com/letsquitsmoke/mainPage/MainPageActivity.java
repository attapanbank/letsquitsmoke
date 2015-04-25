package com.letsquitsmoke.mainPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.algorithm.seventh.tobacut.SortDate;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.letsquitsmoke.R;
import com.letsquitsmoke.R.id;
import com.letsquitsmoke.R.layout;
import com.letsquitsmoke.graphPage.GraphPageActivity;
import com.letsquitsmoke.guidePage.GuidePageActivity;
import com.letsquitsmoke.settingPage.SettingPageActivity;
import com.letsquitsmoke.tutorialPage.TutorialPageActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends Activity {
	// FaceBook
	final Context context = this;
	private static String App_Id = "835078053210256";
	private Facebook facebook = new Facebook(App_Id);
	
	// flag for Internet connection status
	Boolean isInternetPresent = false;
		
	// Connection detector class
	ConnectionDetector cd;
	
	//noti
	AlarmManager alarmManager;
	
	// DB
	final DB_smoke db_smoke = new DB_smoke(this);

	// for pop up
	AlertDialog alert;
	AlertDialog alertAD;
	SQLiteDatabase mDb;
	DB_smoke mHelper;
	private PendingIntent pendingIntent;
	// sharedpreference from setting
	final String PREFERENCE_NAME = "profile";
	final String Checkstoporsmoke = "checkstoporsmoke";
	SharedPreferences sp_checkstoporsmoke;
	SharedPreferences.Editor editor_checkstoporsmoke;
	// Check sharedpreference
	final String keepinone = "keepinone";
	final String keep = "keep";
	String a;
	SharedPreferences sp;
	SharedPreferences.Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);

		sp = getSharedPreferences(keepinone, Context.MODE_PRIVATE);
		a = sp.getString(keep, "");

		// set font
		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView myTextView = (TextView) findViewById(R.id.textHeadBar);
		myTextView.setTypeface(myTypeface, myTypeface.BOLD);

		Typeface myTypeface2 = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView TextView2 = (TextView) findViewById(R.id.textSmoke);
		TextView2.setTypeface(myTypeface2);

		Typeface myTypeface3 = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView TextView3 = (TextView) findViewById(R.id.textViewGraph);
		TextView3.setTypeface(myTypeface3);

		Typeface myTypeface4 = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView TextView4 = (TextView) findViewById(R.id.textViewGuide);
		TextView4.setTypeface(myTypeface4);

		Typeface myTypeface5 = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView TextView5 = (TextView) findViewById(R.id.textViewSetting);
		TextView5.setTypeface(myTypeface5);

		Typeface myTypeface6 = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView TextView6 = (TextView) findViewById(R.id.textViewTu);
		TextView6.setTypeface(myTypeface6);

		Typeface myTypeface7 = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView TextView7 = (TextView) findViewById(R.id.textViewShare);
		TextView7.setTypeface(myTypeface7);

		// set pop up

		mHelper = new DB_smoke(this);
		SharedPreferences sp = getSharedPreferences("profile",
				Context.MODE_PRIVATE);
		final String decrease = sp.getString("Decrease", "0");

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("");
		builder.setMessage("คุณสูบเกินเป้าหมายแล้ว ต้องการสูบ ?");

		builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Do nothing but close the dialog
				db_smoke.InsertData();
				Toast.makeText(MainPageActivity.this, "บันทึกข้อมูลสำเร็จ",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}

		});

		builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				dialog.dismiss();
			}
		});
		alert = builder.create();

		// Alert AD
		AlertDialog.Builder builderAD = new AlertDialog.Builder(this);
		builderAD.setTitle("");
		builderAD
				.setMessage("หากมีปัญหากับการเลิกบุหรี่สามารถขอคำปรึกษาจากทางศูนย์เลิกบุหรี่ได้นะ");
		builderAD.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing but close the dialog
			}
		});
		alertAD = builderAD.create();

		// SqLite DB
		// CAll Class
		db_smoke.getWritableDatabase();

		// Smoke Button
		final TextView Smokebtn = (TextView) findViewById(R.id.textSmoke);
		// Perform action on click
		Smokebtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// get Data from DB to draw the list

				mDb = mHelper.getReadableDatabase();
				Cursor mCursorGraph;
				mCursorGraph = mDb
						.rawQuery(
								"SELECT count(*) count, date FROM SMOKE_STAT GROUP BY date ORDER BY date desc",
								null);

				String[] arr_dataGraph = new String[7];
				String[] arr_dataGraphdate = new String[7];
				mCursorGraph.moveToFirst();
				int countGraph = 0;
				while (!mCursorGraph.isAfterLast()) {
					arr_dataGraph[countGraph] = mCursorGraph
							.getString(mCursorGraph.getColumnIndex("count"));
					arr_dataGraphdate[countGraph] = mCursorGraph
							.getString(mCursorGraph.getColumnIndex("date"));
					mCursorGraph.moveToNext();
					countGraph++;
				}
				countGraph = 0;
				mHelper.close();
				mDb.close();

				String lastDate;
				if (arr_dataGraph[0] == null) {
					lastDate = "0000-00-00";
				} else {
					lastDate = arr_dataGraphdate[0];
				}

				int lastVal;
				if (arr_dataGraph[0] == null) {
					lastVal = 0;
				} else {
					lastVal = Integer.parseInt(arr_dataGraph[0]);
				}

				int valpoint = Integer.parseInt(decrease); // from setting page

				// check date
				if (lastDate
						.equalsIgnoreCase(new SimpleDateFormat("yyyy-MM-dd")
								.format(new Date()))) {
					// check val
					if (lastVal >= 5) {
						alertAD.show();
					}
					if (lastVal >= valpoint) {
						alert.show();
					} else {
						db_smoke.InsertData();
						Toast.makeText(MainPageActivity.this,
								"บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					db_smoke.InsertData();
					Toast.makeText(MainPageActivity.this, "บันทึกข้อมูลสำเร็จ",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

		// Setting Page Button
		RelativeLayout settingBtn = (RelativeLayout) findViewById(R.id.settingBtn);
		settingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						SettingPageActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		// Tutarial Page Button
		RelativeLayout Btn = (RelativeLayout) findViewById(R.id.Btn);
		Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						TutorialPageActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		// Graph Page Button
		RelativeLayout graphBtn = (RelativeLayout) findViewById(R.id.graphBtn);
		graphBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						GraphPageActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		// Guide Page Button
		RelativeLayout guideBtn = (RelativeLayout) findViewById(R.id.guideBtn);
		guideBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						GuidePageActivity.class);

				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		// Share Button
		RelativeLayout ShareBtn = (RelativeLayout) findViewById(R.id.facebookShareBtn);
		cd = new ConnectionDetector(getApplicationContext());
		ShareBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    // Internet Connection is Present
                    // make HTTP requests
                	postSharing();
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(MainPageActivity.this, "No Internet Connection",
                            "โปรดตรวจสอบอินเตอร์เน็ตของท่านด้วย.", false);
                }
				

			}
		});

		// PhoneCall Button
		ImageView phonecallBtn = (ImageView) findViewById(R.id.imaCall);
		phonecallBtn.setVisibility(View.INVISIBLE);

		// add PhoneStateListener
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,
				PhoneStateListener.LISTEN_CALL_STATE);

		phonecallBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				confirmcall();
			}
		});

		// Check Stop or Decrease Smoke from Setting
		sp_checkstoporsmoke = getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		editor_checkstoporsmoke = sp_checkstoporsmoke.edit();
		String a = sp_checkstoporsmoke.getString(Checkstoporsmoke, "");
		if (a.equals("0")) {
			Smokebtn.setVisibility(View.VISIBLE);
			phonecallBtn.setVisibility(View.INVISIBLE);
		} else {
			phonecallBtn.setVisibility(View.VISIBLE);
			Smokebtn.setVisibility(View.INVISIBLE);
		}

//	notification();
		
		
	}

	// End onCreate

	// confirm call to 1600
	public void confirmcall() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// Yes button clicked
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:1600"));
					startActivity(callIntent);

					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					Toast.makeText(MainPageActivity.this, "ยกเลิก",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainPageActivity.this);
		builder.setMessage(
				"คุณต้องการโทรเพื่อปรึกษาปัญหา กับศูนย์เลิกบุหรี่ ใช่หรือไม่ ?")
				.setPositiveButton("ใช่", dialogClickListener)
				.setNegativeButton("ไม่ใช่", dialogClickListener).show();

	}

	// Post FB
	public void postSharing() {
		Bundle parameters = new Bundle();
		parameters.putString("link", "http://www.thaihealth.or.th/");
		facebook.dialog(this, "feed", parameters, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
				Toast.makeText(getApplicationContext(), "Post Complete",
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onCancel() {
				Toast.makeText(getApplicationContext(), "Cancle Post",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFacebookError(FacebookError e) {

			}

			@Override
			public void onError(DialogError e) {

			}
		});
	}

	// function

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Ask the user if they want to quit
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.quit)
					.setMessage(R.string.really_quit)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// Stop the activity
									MainPageActivity.this.finish();
								}

							}).setNegativeButton(R.string.no, null).show();

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	// Phone Call
	private class PhoneCallListener extends PhoneStateListener {
		private boolean isPhoneCalling = false;
		String LOG_TAG = "LOGGING 123";

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			if (TelephonyManager.CALL_STATE_RINGING == state) {
				// phone ringing
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(LOG_TAG, "OFFHOOK");
				isPhoneCalling = true;
			}

			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// run when class initial and phone call ended, need detect flag
				// from CALL_STATE_OFFHOOK
				Log.i(LOG_TAG, "IDLE");

				if (isPhoneCalling) {
					Log.i(LOG_TAG, "restart app");

					// restart app
					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					isPhoneCalling = false;
				}

			}

		}
	}

	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
         
        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
	
	
}

package com.letsquitsmoke.settingPage;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.letsquitsmoke.R;
import com.letsquitsmoke.mainPage.DB_smoke;
import com.letsquitsmoke.mainPage.MainPageActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingPageActivity extends Activity implements OnClickListener {

	// Attribute for SharePreference
	final String PREFERENCE_NAME = "profile";
	final String NAME = "Name";
	final String TEL = "Tel";
	final String DATE = "Date";
	final String NORMAL = "Normal";
	final String DECREASE = "Decrease";
	final String Checkstoporsmoke = "checkstoporsmoke"; // check in main page
	// End

	String checkcallorsmoke;

	SharedPreferences sp;
	SharedPreferences.Editor editor;

	final DB_smoke db_smoke = new DB_smoke(this);
	SQLiteDatabase mDb;
	DB_smoke mHelper;

	EditText inputname, inputtel, inputdate, inputnormal, inputdecrease;
	RadioButton stopbutton, decreasebutton;
	Button save_button, opennotification_button, closenotification_button;
	TextView decreasetext1, decreasetext2, myprofile,
			opennotificationtext, closenotificationtext;
	RelativeLayout stopform, decreaseform;

	Calendar myCalendar = Calendar.getInstance();

	boolean checkconfirm; // confirm to delete all smoke record
	boolean radiostop; // check stop or decrease smoke in save button

	static PendingIntent pendingIntent;
	static AlarmManager alarmManager;

	// Start onCreate
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settingpage_activity_settingpage);

		// view matching
		inputname = (EditText) findViewById(R.id.inputname);
		inputtel = (EditText) findViewById(R.id.inputtel);
		inputdate = (EditText) findViewById(R.id.inputdate);
		stopbutton = (RadioButton) findViewById(R.id.stopbutton);
		decreasebutton = (RadioButton) findViewById(R.id.decreasebutton);
		inputnormal = (EditText) findViewById(R.id.inputnormal);
		inputdecrease = (EditText) findViewById(R.id.inputdecrease);
		save_button = (Button) findViewById(R.id.save_button);
		stopform = (RelativeLayout) findViewById(R.id.stopform);
		decreaseform = (RelativeLayout) findViewById(R.id.decreaseform);
		decreasetext1 = (TextView) findViewById(R.id.decreasetext1);
		decreasetext2 = (TextView) findViewById(R.id.decreasetext2);
		myprofile = (TextView) findViewById(R.id.myprofile);
		opennotification_button = (Button) findViewById(R.id.opennotification1);
		opennotificationtext = (TextView) findViewById(R.id.opennotification2);
		closenotification_button = (Button) findViewById(R.id.closenotification1);
		closenotificationtext = (TextView) findViewById(R.id.closenotification2);

		// set Font
		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		decreasetext1.setTypeface(myTypeface);
		decreasetext2.setTypeface(myTypeface);
		myprofile.setTypeface(myTypeface);

		sp = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		// Check stop or smoke in MainPage
		String checkstopsmoke = sp.getString(Checkstoporsmoke, "");
		if (checkstopsmoke.equals("0")) {
			decreasebutton.setChecked(true);
			stopbutton.setChecked(false);
			decreaseform.setVisibility(View.VISIBLE);
			stopform.setVisibility(View.INVISIBLE);
			radiostop = false;
		} else {
			stopbutton.setChecked(true);
			decreasebutton.setChecked(false);
			stopform.setVisibility(View.VISIBLE);
			decreaseform.setVisibility(View.INVISIBLE);
			radiostop = true;
		}

		// End Check stop or smoke in MainPage

		// Set Preference
		inputname.setText(sp.getString(NAME, ""));
		inputname.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				editor.putString(NAME, s.toString());
				//editor.commit();
			}
		});

		inputtel.setText(sp.getString(TEL, ""));
		inputtel.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				editor.putString(TEL, s.toString());
				//editor.commit();
			}
		});

		inputdate.setText(sp.getString(DATE, ""));
		inputdate.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				editor.putString(DATE, s.toString());
				//editor.commit();
			}
		});

		inputnormal.setText(sp.getString(NORMAL, "2"));
		inputnormal.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				editor.putString(NORMAL, s.toString());
				//editor.commit();
			}
		});

		inputdecrease.setText(sp.getString(DECREASE, "1"));
		inputdecrease.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				editor.putString(DECREASE, s.toString());
				//editor.commit();
			}
		});
		// End Set Preference

		// Check Radio Button
		stopbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopbutton.setChecked(true);
				decreasebutton.setChecked(false);

				radiostop = true;

				checkcallorsmoke = "1";
				editor.putString(Checkstoporsmoke, checkcallorsmoke);
				editor.commit();

				stopform.setVisibility(View.VISIBLE);
				decreaseform.setVisibility(View.INVISIBLE);
			}
		});

		decreasebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				decreasebutton.setChecked(true);
				stopbutton.setChecked(false);

				radiostop = false;

				checkcallorsmoke = "0";
				editor.putString(Checkstoporsmoke, checkcallorsmoke);
				editor.commit();

				decreaseform.setVisibility(View.VISIBLE);
				stopform.setVisibility(View.INVISIBLE);
			}
		});
		// End check Radio Button

		// Set DatePickerDialog
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}

			private void updateLabel() {

				String myFormat = "dd/MM/yy"; // In which you need put here
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

				inputdate.setText(sdf.format(myCalendar.getTime()));
			}

		};

		inputdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(SettingPageActivity.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		// End DatePickerDialog

		// Save button
		save_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int normal = Integer.parseInt(inputnormal.getText().toString());
				int decrease = Integer.parseInt(inputdecrease.getText()
						.toString());
				if (decrease == 0 && radiostop == false) {
					checkdecreasezero();
				} else if (normal <= decrease && radiostop == false) {
					checknormal();
				} else {
					Toast.makeText(SettingPageActivity.this, "บันทึกสำเร็จ",
							Toast.LENGTH_SHORT).show();
					editor.commit();
				}

			}
		});
		// End Save button

		// Clear Data Button
		Button cleardatabtn = (Button) findViewById(R.id.cleardataBtn);

		// Call DB_smoke to clear data record
		mHelper = new DB_smoke(this);
		mDb = db_smoke.getWritableDatabase();

		cleardatabtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							// Yes button clicked
							checkconfirm = true;

							if (checkconfirm) {
								// drop table
								db_smoke.onUpgrade(mDb, 1, 1);

								Toast.makeText(SettingPageActivity.this,
										"ลบข้อมูลทั้งหมด เสร็จสิ้น",
										Toast.LENGTH_SHORT).show();
							}

							break;

						case DialogInterface.BUTTON_NEGATIVE:
							// No button clicked
							checkconfirm = false;

							if (checkconfirm == false) {
								Toast.makeText(SettingPageActivity.this,
										"ยกเลิก", Toast.LENGTH_SHORT).show();
							}

							break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingPageActivity.this);
				builder.setMessage(
						"คุณต้องการลบข้อมูลการสูบบุหรี่ทั้งหมด ใช่หรือไม่ ?")
						.setPositiveButton("ใช่", dialogClickListener)
						.setNegativeButton("ไม่ใช่", dialogClickListener)
						.show();

			}
		});
		// End call DB_smoke

		Intent intentsOpen = new Intent(this, AlarmReceiver.class);
		intentsOpen.setAction("com.letsquitsmoke.alarm.ACTION");
		pendingIntent = PendingIntent.getBroadcast(this, 111, intentsOpen, 0);
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		opennotification_button.setOnClickListener(this);
		opennotificationtext.setOnClickListener(this);
		closenotification_button.setOnClickListener(this);
		closenotificationtext.setOnClickListener(this);

	}

	// End onCreate

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == opennotification_button || v == opennotificationtext) {
			fireAlarm();
			Toast.makeText(getApplicationContext(),
					"เปิดการแจ้งเตือน",
					Toast.LENGTH_SHORT).show();
		}
		if (v == closenotification_button || v == closenotificationtext) {
			stopAlarm();
			Toast.makeText(getApplicationContext(),
					"ปิดการแจ้งเตือน",
					Toast.LENGTH_SHORT).show();
		}
	}

	// BackPressed Button
	@Override
	public void onBackPressed() {

		int normal = Integer.parseInt(inputnormal.getText().toString());
		int decrease = Integer.parseInt(inputdecrease.getText().toString());

		if (radiostop == true) {

			Intent intent = new Intent(SettingPageActivity.this,
					MainPageActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);

		} else if (radiostop == false) {
			if (normal > decrease && decrease != 0) {
				Intent intent = new Intent(SettingPageActivity.this,
						MainPageActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			} else if (decrease == 0) {
				checkdecreasezero();
			} else {
				checknormal();
			}
		}

	}

	// Check normal <= decrease
	public void checknormal() {

		int decrease = Integer.parseInt(inputdecrease.getText().toString());

		String changenormal = Integer.toString(decrease + 1);
		inputnormal.setText(changenormal);
		Toast.makeText(getApplicationContext(),
				"จำนวนสูบปกติต้องมากกว่าจำนวนที่ต้องการลด", Toast.LENGTH_SHORT)
				.show();

	}

	// Check decrease == 0
	public void checkdecreasezero() {
		AlertDialog alertDialog = new AlertDialog.Builder(
				SettingPageActivity.this).create();
		alertDialog.setTitle("คำเตือน");
		alertDialog.setMessage("จำนวนที่ต้องการลดบุหรี่\nต้องมากกว่า 0");
		alertDialog.setButton("ตกลง", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(),
						"จำนวนที่ต้องการลดบุหรี่ต้องมากกว่า 0",
						Toast.LENGTH_SHORT).show();
			}
		});
		alertDialog.show();
		inputdecrease.setText("1");
	}

	public void fireAlarm() {
		/**
		 * call broadcost reciver
		 */
		final String DATE = "Date";
		SharedPreferences sp1 = getSharedPreferences("profile",
		Context.MODE_PRIVATE);
		SimpleDateFormat formatter;
		Date dateFormat;
		int getDate = 0, getMonth = 0;
		String stopDate = sp1.getString(DATE, "");
		formatter = new SimpleDateFormat("dd/MM/yy");
		try {
		dateFormat = (Date) formatter.parse(stopDate);
		getDate = dateFormat.getDate();
		getMonth = dateFormat.getMonth();
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int hourofday = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.set(Calendar.MONTH, getMonth);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.DAY_OF_MONTH, getDate);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		//calendar.setTimeInMillis(System.currentTimeMillis());
		if (calendar.get(Calendar.DAY_OF_MONTH) == day
				&& calendar.get(Calendar.MONTH) == month
				&& calendar.get(Calendar.HOUR_OF_DAY) == hourofday){
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), 60*60*1000, pendingIntent);
		}

	}

	public void stopAlarm() {
		alarmManager.cancel(pendingIntent);

	}

}

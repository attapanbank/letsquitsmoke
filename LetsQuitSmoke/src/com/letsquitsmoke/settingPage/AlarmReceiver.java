package com.letsquitsmoke.settingPage;

import com.letsquitsmoke.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	private final String SOMEACTION = "com.letsquitsmoke.alarm.ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {
		generateNotification(context,"วันครบกำหนดเลิกสูบหรี่");
		String action = intent.getAction();
		if (SOMEACTION.equals(action)) {
			//do what you want here
			generateNotification(context,"วันครบกำหนดเลิกสูบหรี่");
		}
	}
	
	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {
		  System.out.println(message+"++++++++++2");
		  int icon = R.drawable.ic_launcher;
		  long when = System.currentTimeMillis();
		  NotificationManager notificationManager = (NotificationManager) context
		    .getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification notification = new Notification(icon, message, when);
		  String title = context.getString(R.string.app_name);
		  String subTitle = "วันครบกำหนดเลิกสูบบุหรี่";
		  Intent notificationIntent = new Intent(context, SettingPageActivity.class);
		  notificationIntent.putExtra("content", message);
		  PendingIntent intent = PendingIntent.getActivity(context, 0,notificationIntent, 0);
		  notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 
		  notification.setLatestEventInfo(context, title, subTitle, intent);
		  //To play the default sound with your notification:
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  notificationManager.notify(0, notification);
		 
	 
	
    }

}
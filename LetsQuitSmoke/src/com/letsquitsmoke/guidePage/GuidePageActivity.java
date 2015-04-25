package com.letsquitsmoke.guidePage;

import com.letsquitsmoke.R;
import com.letsquitsmoke.R.color;
import com.letsquitsmoke.R.layout;
import com.letsquitsmoke.mainPage.MainPageActivity;
import com.letsquitsmoke.settingPage.SettingPageActivity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class GuidePageActivity extends Activity implements OnTabChangeListener
{
	LocalActivityManager mLocalActivityManager;
	TabHost tabHost;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guidepage_activity_guidepage);
		
		//set Font
				Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/sukhumvitlight_medium.ttf");
			    TextView myTextView = (TextView)findViewById(R.id.textActionBar);
			    myTextView.setTypeface(myTypeface);
		
		Resources resources = getResources();

		mLocalActivityManager = new LocalActivityManager(this, false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(mLocalActivityManager);

		TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1")
				.setIndicator("คำแนะนำ")
				.setContent(new Intent(this, tabListView1.class));

		TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
				.setIndicator("อาการเมื่อเลิกบุหรี่")
				.setContent(new Intent(this, tabListView2.class));

		tabHost.addTab(tabSpec);
		tabHost.addTab(tabSpec2);

		tabHost.setCurrentTab(0); // Default Selected Tab

		tabHost.setOnTabChangedListener(this);

		tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 60;
		tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
		tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 60;
		tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
		
		tabHost.getTabWidget().getChildAt(0)
				.setBackgroundColor(Color.rgb(00, 219, 239));
	}
	
	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.tabbg);
						// unselected
		}
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundResource(R.color.primary); // selected

	}
	
	
}

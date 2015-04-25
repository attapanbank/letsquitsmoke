package com.letsquitsmoke.guidePage;

import com.letsquitsmoke.R;
import com.letsquitsmoke.R.id;
import com.letsquitsmoke.R.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	Context mContext;
	String[] strName;
	
	Typeface tf; 

	public CustomAdapter(Context context, String[] strName) {
		this.mContext = context;
		this.strName = strName;
		//set Font
		
	   
		
	}

	public int getCount() {
		return strName.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View row = mInflater.inflate(R.layout.guidepage_activity_listview_row,
				parent, false);

		TextView textView = (TextView) row.findViewById(R.id.textView1);
		textView.setText(strName[position]);
		
		 Typeface tf = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/sukhumvitlight_medium.ttf");
		    textView.setTypeface(tf);    

		
	

		

		return row;
	}


}
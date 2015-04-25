package com.letsquitsmoke.graphPage;

import com.letsquitsmoke.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterGraph extends BaseAdapter {
	Context mContext;
	String[] strName;

	public CustomAdapterGraph(Context context, String[] strName) {
		this.mContext = context;
		this.strName = strName;
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

	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View row = mInflater.inflate(R.layout.graphpage_activity_listview, parent, false);

		TextView textView = (TextView) row.findViewById(R.id.listData);
		textView.setText(strName[position]);

		return row;
	}
}
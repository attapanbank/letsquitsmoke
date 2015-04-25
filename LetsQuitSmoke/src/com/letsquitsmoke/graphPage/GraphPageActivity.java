package com.letsquitsmoke.graphPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.algorithm.seventh.tobacut.SortDate;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.letsquitsmoke.R;
import com.letsquitsmoke.mainPage.DB_smoke;
import com.letsquitsmoke.mainPage.MainPageActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GraphPageActivity extends Activity {

	final String PREFERENCE_NAME = "profile";
	final String DATE = "Date";
	final String NORMAL = "Normal";
	final String DECREASE = "Decrease";

	SQLiteDatabase mDb;
	DB_smoke mHelper;

	ArrayList<String> arr_list;
	int first_row, last_row, total_row = 40;
	ListView lv;

	int day = 7; // Day that show

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graphpage_activity_graphpage);

		// set date
		SharedPreferences sp = getSharedPreferences("profile",
				Context.MODE_PRIVATE);
		String decrease = sp.getString(DECREASE, "0");

		// set Font
		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"fonts/sukhumvitlight_medium.ttf");
		TextView myTextView = (TextView) findViewById(R.id.lableActionBar);
		myTextView.setTypeface(myTypeface);

		/*********************************************************************************/
		// Create Graph
		GraphView graphView = new BarGraphView(this // context
				, "" // heading
		);

		// get Data from DB to draw the list
		mHelper = new DB_smoke(this);
		mDb = mHelper.getReadableDatabase();
		Cursor mCursorGraph;
		mCursorGraph = mDb
				.rawQuery(
						"SELECT count(*) count FROM SMOKE_STAT GROUP BY date ORDER BY date",
						null);

		int sizeDataGraph = mCursorGraph.getCount();
		String[] arr_dataGraph = new String[7];
		mCursorGraph.moveToFirst();
		int countGraph = 0;
		while (!mCursorGraph.isAfterLast()) {
			arr_dataGraph[countGraph] = mCursorGraph.getString(mCursorGraph
					.getColumnIndex("count"));
			mCursorGraph.moveToNext();
			countGraph++;
		}

		for (int i = 6; i >= sizeDataGraph; i--) {
			arr_dataGraph[i] = "0";
		}
		countGraph = 0;
		mHelper.close();
		mDb.close();
		//

		// prepare compare data
		mHelper = new DB_smoke(this);
		mDb = mHelper.getReadableDatabase();
		Cursor mCursorCom;
		mCursorCom = mDb
				.rawQuery(
						"SELECT date FROM SMOKE_STAT GROUP BY date ORDER BY date",
						null);

		int sizeDataCom = mCursorCom.getCount();
		String[] arr_dataCom = new String[7];
		mCursorCom.moveToFirst();
		int countCom = 0;
		while (!mCursorCom.isAfterLast()) {
			arr_dataCom[countCom] = mCursorCom.getString(mCursorCom
					.getColumnIndex("date"));
			mCursorCom.moveToNext();
			countCom++;
		}

		for (int i = 6; i >= sizeDataCom; i--) {
			arr_dataCom[i] = "00";
		}
		countCom = 0;
		mHelper.close();
		mDb.close();
		//

		// fix bug with engine
		SortDate s = new SortDate();
		String[] graph_data = s.SortWith(arr_dataCom, arr_dataGraph);

		/*
		 * mCursor = mDb.rawQuery("SELECT " + DB_Graph.COL_NAME + " FROM " +
		 * DB_Graph.TABLE_NAME, null);
		 * 
		 * SELECT product_id, count(*) AS total FROM order_line GROUP BY
		 * product_id ORDER BY total desc
		 */

		// Prepare data
		double maxVal = 0; // find max value
		GraphDataImplement[] graphData = new GraphDataImplement[day];

		for (int i = 0; i < graphData.length; i++) { // loop for set graph

			graphData[i] = new GraphDataImplement(i + 1,
					Integer.parseInt(graph_data[i]));

			if (graphData[i].getY() > maxVal) {
				maxVal = graphData[i].getY();
			}
		}
		graphView.setManualYMinBound(0);
		if (maxVal <= 20) { // find max val on the graph
			graphView.setManualYMaxBound(20);
		} else if ((maxVal >= 21) && (maxVal) <= 30) {
			graphView.setManualYMaxBound(30);
		} else if ((maxVal >= 31) && (maxVal) <= 40) {
			graphView.setManualYMaxBound(40);
		} else if ((maxVal >= 41) && (maxVal) <= 60) {
			graphView.setManualYMaxBound(60);
		} else if ((maxVal >= 61) && (maxVal) <= 80) {
			graphView.setManualYMaxBound(80);
		} else {
			graphView.setManualYMaxBound(100);
		}

		final double maxValFinal = maxVal; // chang to final int
		final int valpoint = Integer.parseInt(decrease); // from setting page

		// Set graph style
		GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
		seriesStyle.setValueDependentColor(new ValueDependentColor() {
			@Override
			public int get(GraphViewDataInterface data) {
				if (data.getY() <= valpoint) {
					return Color.rgb((int) ((data.getY() / valpoint) * 250),
							(int) 250, (int) (0));
				} else {
					return Color.rgb(
							(int) 250,
							(int) (250 - (((data.getY() - valpoint) / maxValFinal) * 250)),
							(int) (0));
				}
			}
		});
		graphView.getGraphViewStyle().setNumVerticalLabels((int) 11);
		graphView.getGraphViewStyle().setNumHorizontalLabels((int) 1);
		graphView.setHorizontalLabels(new String[] { "จำนวนที่สูบในแต่ละวัน" });
		graphView.getGraphViewStyle().setTextSize(
				(float) (getResources()
						.getDimension(R.dimen.activity_horizontal_margin)));
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsWidth(
				(int) getResources().getDimension(
						R.dimen.activity_horizontal_margin) * 2);

		GraphViewSeries exampleSeries = new GraphViewSeries(
				"Graph show unit per day", seriesStyle, graphData);

		graphView.addSeries(exampleSeries); // add data

		// add graph to the layout
		LinearLayout layout = (LinearLayout) findViewById(R.id.graphView);
		layout.addView(graphView);

		/*********************************************************************************/

		// get Data from DB to draw the list
		mHelper = new DB_smoke(this);
		mDb = mHelper.getReadableDatabase();
		Cursor mCursorList;
		mCursorList = mDb.rawQuery(
				"SELECT time FROM " + DB_smoke.TABLE_NAME + "  WHERE date = '"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
						+ "' ORDER BY time desc", null);

		int sizeDataList = mCursorList.getCount();
		String[] arr_dataList = new String[sizeDataList];
		mCursorList.moveToFirst();
		int countList = 0;
		while (!mCursorList.isAfterLast()) {
			arr_dataList[countList] = mCursorList.getString(mCursorList
					.getColumnIndex("time"));
			mCursorList.moveToNext();
			countList++;
		}
		countList = 0;
		mHelper.close();
		mDb.close();
		//

		CustomAdapterGraph adapter = new CustomAdapterGraph(
				getApplicationContext(), arr_dataList);

		ListView listView1 = (ListView) findViewById(R.id.listView);
		listView1.setAdapter(adapter);

		// set hide show
		final RelativeLayout layoutGraph = (RelativeLayout) findViewById(R.id.layoutGraph);
		final RelativeLayout layoutList = (RelativeLayout) findViewById(R.id.layoutList);
		final ListView listviewList = (ListView) findViewById(R.id.listView);

		layoutList.setVisibility(View.GONE);
		layoutGraph.setVisibility(View.VISIBLE);

		layoutGraph.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutGraph.setVisibility(View.GONE);
				layoutList.setVisibility(View.VISIBLE);
			}
		});

		layoutList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutGraph.setVisibility(View.VISIBLE);
				layoutList.setVisibility(View.GONE);
			}
		});

		listviewList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				layoutGraph.setVisibility(View.VISIBLE);
				layoutList.setVisibility(View.GONE);
			}
		});

	}

}

package fnn.smirl.trader;

import android.view.*;
import fnn.smirl.trader.adapters.*;
import fnn.smirl.trader.fragments.*;
import fnn.smirl.trader.utils.*;
import java.io.*;
import java.util.*;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fnn.smirl.simple.Serializer;

public class MainActivity extends AppCompatActivity {
 public static final String TAG="Trader";
 SharedPreferences settings;
 SharedPreferences.Editor settingsEditor;
 public static Context ctx;
 public static AppCompatActivity ACTIVITY;
 public static Resources res;
 private TabLayout main_tablayout;
 TabsPagerAdapter tabAdapter;
 ViewPager pager;
 ActionBar actionbar;
 Handler timeHandler;
 Runnable timeRunner;
 
 static Serializer serializer = new Serializer();

 public static Business business;
 ZCalendar zCal = new ZCalendar(0);
 static String filename;
 public static ArrayList<Commodity> commodities;
 public static CommodityListAdapter commodityAdapter;
 public static StockListAdapter stockAdapter;
 String[] tabNames = new String[]{"Market","Stock","Summary"};
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	ctx = getApplicationContext();
	res = getResources();
	ACTIVITY = this;
	settings = getSharedPreferences("settings", 0);
	settingsEditor = settings.edit();

	init();

 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
	// TODO: Implement this method

	getMenuInflater().inflate(R.menu.main_menu, menu);

	return true;
 }

 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
	// TODO: Implement this method
	switch (item.getItemId()) {
	 case R.id.mm_reset:
		showInfo("Resetting game...");
		business.reset();
		showInfo("Reset successful!");
		updateDatabase();
		settingsEditor.putLong("date", 0);
		zCal = new ZCalendar(business.time);
		appendUserName();
		break;
	 case R.id.mm_sub_agric:
		commodityAdapter.clear();
		commodities = business.products.get(0);
		commodityAdapter = new CommodityListAdapter(this, commodities);
		commodityAdapter.notifyDataSetChanged();

		refreshMarket();
		break;
	 case R.id.mm_sub_metal:
		commodityAdapter.clear();
		commodities = business.products.get(1);
		commodityAdapter = new CommodityListAdapter(this, commodities);
		commodityAdapter.notifyDataSetChanged();
		refreshMarket();
		break;
	 case R.id.mm_sub_energy:
		commodityAdapter.clear();
		commodities = business.products.get(2);
		commodityAdapter = new CommodityListAdapter(this, commodities);
		commodityAdapter.notifyDataSetChanged();
	refreshMarket();
		break;
	 case R.id.mm_profile:
		openProfileDialog();
		break;
	 case R.id.mm_about:

		break;
	}
	return true;
 }

 @Override
 protected void onResume() {
	// TODO: Implement this method
	super.onResume();
	startTime();
Market.updateValues();
Stock.updateValues();
Summary.updateValues();
	appendUserName();
 }



 @Override
 protected void onPause() {
	// TODO: Implement this method
	super.onPause();
//	settingsEditor.putLong("date", zCal.get(ZCalendar.Field.MILLISECOND));
//	settingsEditor.commit();
	updateDatabase();
	stopTime();
 }

 @Override
 public void onBackPressed() {
	// TODO: Implement this method
	super.onBackPressed();
//	settingsEditor.putLong("date", zCal.get(ZCalendar.Field.MILLISECOND));
//	settingsEditor.commit();
	updateDatabase();
	stopTime();
 }

 private void init() {
	business = new Business();
	filename = getFilesDir() + "/trading.json";
	initToolbar();
	initTabLayout();
	initViewPager();
	commodities = new ArrayList<Commodity>();
	runTime();
	startTime();
	fillDatabase();
//	 Log.v(TAG, business.products.toString());
//	 
//	 System.out.println("null here");
//	 business.products = new Products();
	
	commodities = business.products.get(0);
	commodityAdapter = new CommodityListAdapter(this, commodities);
	stockAdapter = new StockListAdapter(this, business.trader.commodities);
	appendUserName();
	zCal = new ZCalendar(business.time);
	Market.updateValues();
 }

 private void startTime() {
	if (timeHandler == null) {
	 timeHandler = new Handler();
	}
	timeHandler.postDelayed(timeRunner, 1000);
 }

 private void stopTime() {
	if (timeHandler != null) {
	 timeHandler.removeCallbacks(timeRunner);
	 timeHandler = null;
	}
 }

 private void runTime() {
	timeRunner = new Runnable(){
	 long fd =0;
	 @Override
	 public void run() {
		// TODO: Implement this method
		String dString = zCal.toString();
		actionbar.setSubtitle(dString);
		timeHandler.postDelayed(timeRunner, 1);
		zCal.add(ZCalendar.Field.MINUTE, 1);
		if ((zCal.get(ZCalendar.Field.HOUR) != fd) && (zCal.get(ZCalendar.Field.HOUR) == 0 || zCal.get(ZCalendar.Field.HOUR) == 12)) {
		 refreshMarket();
		 showInfo("Midday update...");
		 fd = zCal.get(ZCalendar.Field.HOUR);
		 business.time = zCal.get(ZCalendar.Field.MILLISECOND);
		// settingsEditor.putLong("date", 
		 
		// settingsEditor.commit();
		}
	 }
	};
 }

 private void initToolbar() {
	Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	actionbar = getSupportActionBar();
	actionbar.setHomeAsUpIndicator(R.drawable.ic_trader);
	actionbar.setDisplayHomeAsUpEnabled(true);
 }

 private void initTabLayout() {
	main_tablayout = (TabLayout)findViewById(R.id.tabLayout);
	main_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
	for (String tabName:tabNames) {
	 TabLayout.Tab tab = main_tablayout.newTab().setText(tabName);
	 main_tablayout.addTab(tab);
	}
	main_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

		@Override
		public void onTabSelected(TabLayout.Tab p1) {
		 // TODO: Implement this method

		 pager.setCurrentItem(p1.getPosition());
		 if (p1.getPosition() == 0) {
			Market.updateValues();
		 } else if (p1.getPosition() == 1) {
			Stock.updateValues();

		 } else if (p1.getPosition() == 2) {
		Summary.updateValues();
		 }
		}

		@Override
		public void onTabUnselected(TabLayout.Tab p1) {
		 // TODO: Implement this method
		}

		@Override
		public void onTabReselected(TabLayout.Tab p1) {
		 // TODO: Implement this method
		}

	 });
 }

 private void initViewPager() {
	pager = (ViewPager)findViewById(R.id.my_pager);
	tabAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabNames);
	pager.setAdapter(tabAdapter);

	pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

		@Override
		public void onPageScrolled(int p1, float p2, int p3) {
		 // TODO: Implement this method
		}

		@Override
		public void onPageSelected(int p1) {
		 // TODO: Implement this method
		 main_tablayout.getTabAt(p1).select();
		}

		@Override
		public void onPageScrollStateChanged(int p1) {
		 // TODO: Implement this method
		}

	 });

 }

 private static void showInfo(String msg) {
	Snackbar
	 .make(ACTIVITY.findViewById(R.id.coordinatorLayout), msg, Snackbar.LENGTH_SHORT)
	 .setAction("Action", new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method

		}

	 })
	 .show();
 }

 
 
 private void refreshMarket() {
	business.products.refreshData();
	commodityAdapter.notifyDataSetChanged();
 }


 static void store() {
	serializer.serialize(filename, business, Business.class);
 }

 private void retrieveRecord() {
	File f = new File(filename);
	if (!f.exists()) {
	 try {
		f.createNewFile();
		initTraderDB();
	 }
	 catch (IOException e) {
		Log.d(TAG, "failed to create file: " + f.getPath());
	 }

	}
	business = serializer.deserialize(filename, Business.class);
 }

 private void fillDatabase() {
	retrieveRecord();
 }

 private void initTraderDB() {
	business = new Business();
	store();
 }

 public static void updateDatabase() {

	showInfo("Updating database...");
	store();
	stockAdapter.notifyDataSetChanged();
 }
 /**
	appending username to title bar
	*/
 public void appendUserName() {
	actionbar.setTitle(res.getString(R.string.app_name) + "(" + business.trader.name + ")");
 }

 //dialog
 public void openProfileDialog() {
	final Dialog dialog = new Dialog(ACTIVITY);

	dialog.setContentView(R.layout.profile_dialog);
	dialog.setTitle("Profile");
	final EditText et1=(EditText)dialog.findViewById(R.id.pf_user);
	et1.setText(business.trader.name);
	final Button b1 = (Button)dialog.findViewById(R.id.pf_btn_update);
	final Button b2= (Button)dialog.findViewById(R.id.pf_btn_cancel);

	et1.addTextChangedListener(new TextWatcher(){
		String s="";
		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
		 // TODO: Implement this method
		 s = et1.getText().toString();
		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
		 // TODO: Implement this method 
		 if (et1.getText().toString().length() > 8) {
			et1.setText(s);
		 }
		}

		@Override
		public void afterTextChanged(Editable p1) {
		 // TODO: Implement this method

		}
	 });

	//buttons
	b1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 business.trader.name = et1.getText().toString();
		 updateDatabase();
		 appendUserName();
		 dialog.dismiss();
		}
	 });

	b2.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 dialog.dismiss();
		}
	 });

	dialog.show();
 }
}

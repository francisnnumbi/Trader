package fnn.smirl.trader.fragments;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import fnn.smirl.trader.R;
import android.support.v7.widget.ListViewCompat;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import fnn.smirl.trader.utils.Commodity;
import java.util.ArrayList;
import fnn.smirl.trader.adapters.CommodityListAdapter;
import fnn.smirl.trader.utils.CommodityListHeading;
import java.util.Random;
import fnn.smirl.trader.MainActivity;
import android.widget.TextView;
import android.util.Log;

public class Market extends Fragment
{
ListView commodity_list;

View view;
static TextView market_c, market_b;
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO: Implement this method
	view = inflater.inflate(R.layout.market, container, false);
	return view;
 }

 @Override
 public void onViewCreated(View view, Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onViewCreated(view, savedInstanceState);
	commodity_list = (ListView)view.findViewById(R.id.market_list);
	commodity_list.setAdapter(MainActivity.commodityAdapter);
	market_c = (TextView)view.findViewById(R.id.market_c);
	market_b = (TextView)view.findViewById(R.id.market_b);
	
 }
 
 public static void updateValues() {
	try{
	market_c.setText("Capital: " + MainActivity.business.trader.liability.capital);
	market_b.setText("Bank: " + MainActivity.business.trader.liability.bank);
}catch(Exception e){
 Log.d(MainActivity.TAG, "Market: failed.-> " +e.toString());
}
 }
 
}

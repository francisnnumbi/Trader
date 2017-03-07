package fnn.smirl.trader.fragments;

import android.view.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import fnn.smirl.trader.R;
import android.widget.ListView;
import fnn.smirl.trader.MainActivity;
import android.widget.TextView;
import android.util.Log;

public class Stock extends Fragment {
 ListView stock_list;
 static TextView stock_p, stock_s, stock_tot_stock_val;
 View view;
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO: Implement this method
	view = inflater.inflate(R.layout.stock, container, false);
	return view;
 }

 @Override
 public void onViewCreated(View view, Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onViewCreated(view, savedInstanceState);
	stock_list = (ListView)view.findViewById(R.id.stock_list);
	stock_p = (TextView)view.findViewById(R.id.stock_p);
	stock_s = (TextView)view.findViewById(R.id.stock_s);
	stock_tot_stock_val=(TextView)view.findViewById(R.id.stock_tot_stock_val);
	stock_list.setAdapter(MainActivity.stockAdapter);
 }

 public static void updateValues() {
	try{
	stock_p.setText("Purchases: " + MainActivity.business.trader.purchases);
	stock_s.setText("Sales: " + MainActivity.business.trader.sales);
	stock_tot_stock_val.setText("Stock value: "+ MainActivity.business.trader.getStockValue());
}catch(Exception e){
 Log.d(MainActivity.TAG, "Stock: failed.-> " +e.toString());
}
 }
}

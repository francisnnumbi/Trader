package fnn.smirl.trader.fragments;
import android.view.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import fnn.smirl.trader.R;
import android.widget.TextView;
import fnn.smirl.trader.MainActivity;
import android.util.Log;

public class Summary extends Fragment 
{
 static TextView capital_tv, result_tv, bank_tv, bank_loan_tv, total_capital_tv, total2_capital_tv,
 stock_tv, s_purchases, s_sales, s_balance;

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO: Implement this method
	return inflater.inflate(R.layout.summary, container, false);
 }

 @Override
 public void onViewCreated(View view, Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onViewCreated(view, savedInstanceState);
	capital_tv = (TextView)view.findViewById(R.id.capital_tv);
	result_tv = (TextView)view.findViewById(R.id.result_tv);
	bank_tv = (TextView)view.findViewById(R.id.bank_tv);
	stock_tv = (TextView)view.findViewById(R.id.stock_tv);
	bank_loan_tv = (TextView)view.findViewById(R.id.bank_loan_tv);
	total_capital_tv = (TextView)view.findViewById(R.id.total_capital_tv);
	total2_capital_tv = (TextView)view.findViewById(R.id.total2_capital_tv);
	s_purchases = (TextView)view.findViewById(R.id.s_purchases);
	s_sales = (TextView)view.findViewById(R.id.s_sales);
	s_balance = (TextView)view.findViewById(R.id.s_balance);
	
 }

 public static void updateValues() {
	try {
	 capital_tv.setText("" + MainActivity.business.trader.liability.capital);
	 bank_tv.setText("" + MainActivity.business.trader.liability.bank);
	 bank_loan_tv.setText("" + MainActivity.business.trader.liability.bankLoan);
	 stock_tv.setText("" + MainActivity.business.trader.getStockValue());
	 
	 s_purchases.setText(""+MainActivity.business.trader.purchases);
	 s_sales.setText(""+MainActivity.business.trader.sales);
	 double res_balance = MainActivity.business.trader.sales-MainActivity.business.trader.purchases;
	 s_balance.setText(""+res_balance);
	 
	 double tot_assets = MainActivity.business.trader.liability.bank + MainActivity.business.trader.getStockValue();
	 total2_capital_tv.setText("" + tot_assets);
	 double tot_liabs = MainActivity.business.trader.liability.capital +
		MainActivity.business.trader.liability.bankLoan;
	 MainActivity.business.trader.liability.result = tot_assets - tot_liabs;
	 result_tv.setText("" + MainActivity.business.trader.liability.result);
	 total_capital_tv.setText("" + tot_assets);
	}
	catch (Exception e) {
	 Log.d(MainActivity.TAG, "Liabilities: failed.-> " + e.toString());
	}
 }



}

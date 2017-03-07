package fnn.smirl.trader.adapters;
import android.view.*;
import android.widget.*;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import fnn.smirl.trader.MainActivity;
import fnn.smirl.trader.R;
import fnn.smirl.trader.utils.enums.Transaction;
import java.util.ArrayList;
import fnn.smirl.trader.utils.Commodity;
import fnn.smirl.trader.fragments.Market;
import android.database.DataSetObserver;
import android.database.DataSetObservable;

public class CommodityListAdapter extends ArrayAdapter<Commodity> 
{
 Context ctx;

 public CommodityListAdapter(Context ctx, ArrayList<Commodity> commodities) {
	super(ctx, 0, commodities);
	this.ctx = ctx;
	setNotifyOnChange(true);
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
	// TODO: Implement this method
	final Commodity com = getItem(position);
	if (convertView == null) {
	 convertView = LayoutInflater.from(ctx)
		.inflate(R.layout.market_list_header, parent, false);
	}

	TextView tv1 = (TextView)convertView.findViewById(R.id.ml_no);
	tv1.setText("" + (position + 1));
	TextView tv2= (TextView)convertView.findViewById(R.id.ml_product);
	tv2.setText(com.description);
	TextView tv3= (TextView)convertView.findViewById(R.id.ml_qty);
	tv3.setText("" + com.quantities.peekFirst());
	TextView tv4= (TextView)convertView.findViewById(R.id.ml_price);
	tv4.setText("" + com.prices.peekFirst());
	TextView tv5= (TextView)convertView.findViewById(R.id.ml_evol);
	float x = com.getLatestQuantityVariation();
	tv5.setText("" + x);
	ImageView iv = (ImageView)convertView.findViewById(R.id.ml_sign);

	if (x < 0) {
	 iv.setImageResource(R.drawable.ic_trending_down);
	} else if (x > 0) {
	 iv.setImageResource(R.drawable.ic_trending_up);
	} else {
	 iv.setImageResource(R.drawable.ic_trending_neutral);
	}

	convertView.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 openBuySellDialog(com);

		}


	 });

	return convertView;
 }


 public void openBuySellDialog(final Commodity com) {
	final Dialog dialog = new Dialog(ctx);

	final double funds = MainActivity.business.trader.liability.bank;
	final Commodity cd = MainActivity.business.trader.getCommodityOfDescription(com.description);
	dialog.setContentView(R.layout.buy_sell_dialog);
	dialog.setTitle("Buy and Sell");
	TextView tv1 = (TextView)dialog.findViewById(R.id.bsd_product);
	tv1.setText(com.description);
	final EditText et1=(EditText)dialog.findViewById(R.id.bsd_qty);
	et1.setHint(com.quantities.peekFirst() + "");
	TextView tv2 = (TextView)dialog.findViewById(R.id.bsd_price);
	tv2.setText(com.prices.peekFirst() + "");
	final TextView tv3= (TextView)dialog.findViewById(R.id.bsd_total);
	final TextView tv4 = (TextView)dialog.findViewById(R.id.bsd_av_stock);

	final TextView tv5 = (TextView)dialog.findViewById(R.id.bsd_av_funds);
	tv5.setText("" + funds);
	final TextView tv6= (TextView)dialog.findViewById(R.id.bsd_notif);
	if (cd != null) {
	 tv4.setText("" + cd.quantities.peekFirst());
	} else {
	 tv4.setText("0");
	 tv6.setText("Commodity not in stock!");
	}

	final Button b1 = (Button)dialog.findViewById(R.id.bsd_buy);
	final Button b2= (Button)dialog.findViewById(R.id.bsd_sell);
	final Button b3 = (Button)dialog.findViewById(R.id.bsd_cancel);
	et1.addTextChangedListener(new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
		 // TODO: Implement this method
		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
		 // TODO: Implement this method
		}

		@Override
		public void afterTextChanged(Editable p1) {
		 // TODO: Implement this method
		 String df = et1.getText().toString();
		 df = df.length() < 1 ?"0": df;
		 long dou = Long.parseLong(df);
		 double total_val = dou * com.prices.peekFirst();
		 tv3.setText("" + total_val);

		 if (total_val <= funds) {
			if (dou <= com.quantities.peekFirst()) {
			 b1.setEnabled(true);
			} else {
			 b1.setEnabled(false);
			 tv6.setText("Quantity > " + com.quantities.peekFirst());
			}
		 } else {
			b1.setEnabled(false);
			tv6.setText("Insufficient funds");
		 }


		 if (cd != null) {
			if (dou <= cd.quantities.peekFirst()) {
			 b2.setEnabled(true);
			} else {
			 tv6.setText("Insufficient stock available");
			 b2.setEnabled(false);
			}
		 } else {
			tv6.setText("Stock not available");
			b2.setEnabled(false);
		 }
		}


	 });


	//buttons
	b1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method

		 Commodity c = new Commodity(com.description, Long.parseLong(et1.getText().toString()), com.prices.peekFirst());
		 Transaction t = MainActivity.business.trader.buy(c, c.quantities.peekFirst());
		 if (t == Transaction.DONE) {
			MainActivity.updateDatabase();
			Market.updateValues();
			dialog.dismiss();
		 } else if (t == Transaction.FUNDS_NOT_SUFFICIENT) {

		 }
		}


	 });

	b2.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 Commodity c = new Commodity(com.description, Integer.parseInt(et1.getText().toString()), com.prices.peekFirst());
		 Transaction t = MainActivity.business.trader.sell(c, c.quantities.peekFirst());
		 if (t == Transaction.DONE) {
			MainActivity.updateDatabase();
			Market.updateValues();
			dialog.dismiss();
		 } else if (t == Transaction.STOCK_NOT_SUFFICIENT) {

		 } else if (t == Transaction.NO_STOCK_AVAILABLE) {

		 }
		}


	 });

	b3.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 dialog.dismiss();
		}


	 });

	dialog.show();
 }

}

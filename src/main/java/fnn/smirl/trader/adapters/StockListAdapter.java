package fnn.smirl.trader.adapters;
import android.view.*;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fnn.smirl.trader.R;
import fnn.smirl.trader.utils.Commodity;
import java.util.ArrayList;

public class StockListAdapter extends ArrayAdapter<Commodity> {
 Context ctx;

 public StockListAdapter(Context ctx, ArrayList<Commodity> commodities) {
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
		.inflate(R.layout.stock_list_model, parent, false);
	}

	TextView tv1 = (TextView)convertView.findViewById(R.id.sl_no);
	tv1.setText("" + (position + 1));
	TextView tv2= (TextView)convertView.findViewById(R.id.sl_product);
	tv2.setText(com.description);
	TextView tv3= (TextView)convertView.findViewById(R.id.sl_qty);
	tv3.setText("" + com.quantities.peekFirst());
	TextView tv4= (TextView)convertView.findViewById(R.id.sl_price);
	tv4.setText("" + com.prices.peekFirst());
	
	convertView.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 //openBuySellDialog(com.description, com.quantity, com.currentPrice);

		}
	 });

	return convertView;
 }

// public void openBuySellDialog(final String product, final int qty, final double price) {
//	final Dialog dialog = new Dialog(ctx);
//	dialog.setContentView(R.layout.buy_sell_dialog);
//	dialog.setTitle("Buy and Sell");
//	TextView tv1 = (TextView)dialog.findViewById(R.id.bsd_product);
//	tv1.setText(product);
//	final EditText et1=(EditText)dialog.findViewById(R.id.bsd_qty);
//
//	TextView tv2 = (TextView)dialog.findViewById(R.id.bsd_price);
//	tv2.setText(price + "");
//	final TextView tv3= (TextView)dialog.findViewById(R.id.bsd_total);
//	final Button b1 = (Button)dialog.findViewById(R.id.bsd_buy);
//	final Button b2= (Button)dialog.findViewById(R.id.bsd_sell);
//	final Button b3 = (Button)dialog.findViewById(R.id.bsd_cancel);
//	et1.addTextChangedListener(new TextWatcher(){
//
//		@Override
//		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
//		 // TODO: Implement this method
//		}
//
//		@Override
//		public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
//		 // TODO: Implement this method
//		}
//
//		@Override
//		public void afterTextChanged(Editable p1) {
//		 // TODO: Implement this method
//		 String df = et1.getText().toString();
//		 df = df.length() < 1 ?"0": df;
//		 double dou = Double.parseDouble(df);
//		 if (dou <= qty) {
//			tv3.setText("" + (dou * price));
//			b1.setEnabled(true);
//			b2.setEnabled(true);
//		 } else {
//			tv3.setText("Quantity > " + qty);
//			b1.setEnabled(false);
//			b2.setEnabled(false);
//		 }
//		}
//
//
//	 });
//
//
//	//buttons
//	b1.setOnClickListener(new OnClickListener(){
//
//		@Override
//		public void onClick(View p1) {
//		 // TODO: Implement this method
//		 Commodity c = new Commodity(product, Integer.parseInt(et1.getText().toString()), price);
//		 Transaction t = MainActivity.trader.buy(c, c.quantity);
//		 if(t == Transaction.DONE){
//			MainActivity.updateDatabase();
//			dialog.dismiss();
//		 }else if(t == Transaction.FUNDS_NOT_SUFFICIENT){
//
//		 }
//		}
//
//
//	 });
//
//	b2.setOnClickListener(new OnClickListener(){
//
//		@Override
//		public void onClick(View p1) {
//		 // TODO: Implement this method
//		 Commodity c = new Commodity(product, Integer.parseInt(et1.getText().toString()), price);
//		 Transaction t = MainActivity.trader.sell(c, c.quantity);
//		 if(t == Transaction.DONE){
//			MainActivity.updateDatabase();
//			dialog.dismiss();
//		 }else if(t == Transaction.STOCK_NOT_SUFFICIENT){
//
//		 }else if(t ==Transaction.NO_STOCK_AVAILABLE){
//
//		 }
//		}
//
//
//	 });
//
//	b3.setOnClickListener(new OnClickListener(){
//
//		@Override
//		public void onClick(View p1) {
//		 // TODO: Implement this method
//		 dialog.dismiss();
//		}
//
//
//	 });
//
//	dialog.show();
// }

}

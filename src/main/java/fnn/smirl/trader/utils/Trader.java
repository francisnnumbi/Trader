package fnn.smirl.trader.utils;
import java.util.ArrayList;
import fnn.smirl.trader.utils.enums.Transaction;
import fnn.smirl.trader.fragments.Summary;
import fnn.smirl.trader.fragments.Stock;
import java.util.Collections;

public class Trader {
 public String name="Trader";
 public double sales=0;
 public double purchases=0;
 public Liability liability;
 public ArrayList<Commodity> commodities;
 
 public Trader(){
	this("Trader");
 }

 public Trader(String name) {
	this.name = name;
	liability = new Liability(100);
	commodities = new ArrayList<Commodity>();
	
 }

 public Commodity getCommodityOfDescription(String description) {
	Commodity temp = new Commodity(description);
	if (commodities.contains(temp)) {
	 return commodities.get(commodities.indexOf(temp));
	}
	return null;
 }

 public Transaction buy(Commodity c, long quantity) {

	if (commodities.contains(c)) {
	 Commodity co =commodities.get(commodities.indexOf(c));
	long er = quantity + co.quantities.pollFirst();
	 co.updateQuantity(er);
	 co.updatePrice(c.prices.peekFirst());
	 c.updateQuantity(c.quantities.peekFirst()-quantity);

	} else {
	 Commodity co = new Commodity(c.description, quantity, c.prices.peekFirst());
	 commodities.add(co);
	 c.updateQuantity(c.quantities.peekFirst()-quantity);
	}
	Collections.sort(commodities);
	double cost = c.prices.peekFirst() * quantity;
	purchases += cost;
	liability.bank -= cost;
	
	Summary.updateValues();
	Stock.updateValues();
	return Transaction.DONE;
 }

 public Transaction sell(Commodity c, long quantity) {
	if (commodities.contains(c)) {
	 Commodity co = commodities.get(commodities.indexOf(c));
	 if (co.quantities.peekFirst() < quantity) {return Transaction.STOCK_NOT_SUFFICIENT;
	 } else {
		double income = quantity * c.prices.peekFirst();
		sales += income;
		liability.bank += income;
		long se = co.quantities.peekFirst()- quantity;
		co.updateQuantity( se);
		c.updateQuantity(c.quantities.peekFirst()+quantity);
		
		Summary.updateValues();
		Stock.updateValues();
		if (co.quantities.peekFirst() < 1)commodities.remove(co);
		Collections.sort(commodities);
		return Transaction.DONE;
	 }
	} else {
	 return Transaction.NO_STOCK_AVAILABLE;
	}
 }

 public boolean reset() {
	name = "Trader";
	sales = 0;
	purchases = 0;
	liability.reinit();
	commodities.clear();

	return true;
 }

 public double getStockValue() {
	double d=0;
	for (int i =0;i < commodities.size();i++) {
	 Commodity c = commodities.get(i);
	 d += c.prices.peekFirst() * c.quantities.peekFirst();
	}
	return d;
 }
}

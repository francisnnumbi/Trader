package fnn.smirl.trader.utils;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class Commodity implements Comparable<Commodity> {
 public String description="";

 public LinkedList<Double> prices = new LinkedList<>();
 public LinkedList<Long> quantities = new LinkedList<>();

 public Commodity(String description, long quantity, double currentPrice) {
	this.description = description;
	this.quantities.offerFirst(new Long(10));
	this.prices.offerFirst(new Double(2));
	this.quantities.offerFirst(new Long(10));
	this.prices.offerFirst(new Double(2));
	this.quantities.push(new Long(quantity));
	this.prices.push(new Double(currentPrice));
 }

 public Commodity(String description) {
	this(description, 10, 2);
 }


 @Override
 public int compareTo(Commodity p1) {
	// TODO: Implement this method
	return description.compareToIgnoreCase(p1.description);
 }

 public void updatePrice(double price) {
	prices.push(new Double(price));
 }

 public void updateQuantity(long quantity) {
	quantities.push(new Long(quantity));
 }

 public float getLatestQuantityVariation() {
	if (quantities.isEmpty()) {
	 return 0;
	} else if (quantities.size() == 1) {
	 return 0;
	} else {
	 return ((quantities.get(1) - quantities.get(0)) +1) / ((1 + quantities.get(0))*10);
	}
 }

 @Override
 public boolean equals(Object o) {
	// TODO: Implement this method
	return (((Commodity)o).description.compareToIgnoreCase(description)) == 0;
 }


}

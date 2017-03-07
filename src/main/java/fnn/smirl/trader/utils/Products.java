package fnn.smirl.trader.utils;
import java.util.ArrayList;
import fnn.smirl.trader.MainActivity;
import fnn.smirl.trader.R;
import java.util.Random;
import java.util.Iterator;

public class Products {
 private Random random = new Random();
 public ArrayList<Commodity> agricultural = new ArrayList<>();
 public ArrayList<Commodity> metal = new ArrayList<>();
 public ArrayList<Commodity> energy = new ArrayList<>();

 public Products() {
	initAgric();
	initMetal();
	initEnergy();
 }

 public ArrayList<Commodity> get(int idx) {
	switch (idx) {
	 case 0:
		return agricultural;
	 case 1:
		return metal;
	 case 2:
		return energy;
		default:
		return agricultural;
	}
 }

 private void initAgric() {
	String[] p = MainActivity.res.getStringArray(R.array.agricultural);
	for (String s:p) {
	 agricultural.add(new Commodity(s));
	}
 }

 private void initMetal() {
	String[] p = MainActivity.res.getStringArray(R.array.metal);
	for (String s:p) {
	 metal.add(new Commodity(s));
	}
 }

 private void initEnergy() {
	String[] p = MainActivity.res.getStringArray(R.array.energy);
	for (String s:p) {
	 energy.add(new Commodity(s));
	}
 }

 //---
 public void refreshData() {
	Iterator<Commodity> iter1 = agricultural.iterator();
	while (iter1.hasNext()) {
	 refreshEach(iter1.next());
	}

	Iterator<Commodity> iter2 = metal.iterator();
	while (iter2.hasNext()) {
	 refreshEach(iter2.next());
	}

	Iterator<Commodity> iter3 = energy.iterator();
	while (iter3.hasNext()) {
	 refreshEach(iter3.next());
	}

 }

 private void refreshEach(Commodity com) {
	long qty = (long)(com.quantities.peekFirst() * (1 + com.getLatestQuantityVariation()));
	com.updateQuantity(qty);
	if (com.quantities.size() > 7) {com.quantities.pollLast();}

	double rand = (0.1 + random.nextDouble());
	if (!random.nextBoolean())rand *= -1;
	double pri = (com.prices.peekFirst() * (rand - com.getLatestQuantityVariation())) / 10;
	com.updatePrice(com.prices.peekFirst() + pri);

 }

}

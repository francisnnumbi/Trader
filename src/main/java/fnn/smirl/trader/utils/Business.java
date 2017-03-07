package fnn.smirl.trader.utils;

public class Business {
 public long time = 0;
 public Trader trader = new Trader();
 public Products products = new Products();
 
 
 public Business(){}
 
 public void reset(){
	time = 0;
	trader.reset();
 }
}

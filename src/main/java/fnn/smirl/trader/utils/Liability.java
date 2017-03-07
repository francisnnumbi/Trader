package fnn.smirl.trader.utils;

public class Liability {
 public double capital=0;
 public  double result=0;
 public double bankLoan=0;
 public double bank=0;

 public Liability() {
	this(100);
 }
 public Liability(double capital) {
	this.capital = capital;
	bank = capital;
	result = 0;
	bankLoan = 0;

 }

 public void reinit() {
	capital = 100;
	result = 0;
	bankLoan = 0;
	bank = capital;
 }
}

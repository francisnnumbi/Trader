package fnn.smirl.trader.utils;

public class CommodityListHeading extends Commodity
{
 String order = "No.";
	String description="Description";
	String quantity="Quantity";
	String price="Price";
	String evol ="Evolution";
	String symbol ="Symbol";
	public CommodityListHeading(){
	 this("Description");
	}
 public CommodityListHeading(String description){
	super(description);
 }
}

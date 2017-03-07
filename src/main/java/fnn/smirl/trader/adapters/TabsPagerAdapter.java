package fnn.smirl.trader.adapters;
import android.support.v4.app.*;
import fnn.smirl.trader.fragments.*;

public class TabsPagerAdapter extends FragmentPagerAdapter {
private String[] tabTitles;
 public TabsPagerAdapter(FragmentManager fm, String[] tabTitles) {
	super(fm);
	this.tabTitles = tabTitles;
 }
 @Override
 public Fragment getItem(int p1) {
	// TODO: Implement this method
	switch (p1) {
	 case 0:
		return new Market();
	 case 1:
		return new Stock();
	 case 2:
		return new Summary();
	}
	return null;
 }

 @Override
 public int getCount() {
	// TODO: Implement this method
	return tabTitles.length;
 }

}

package fnn.smirl.trader.utils;
public class ZCalendar {

 public enum Field {
	MILLISECOND,
	SECOND,
	MINUTE,
	HOUR,
	DAY,
	MONTH,
	YEAR;
 }
 private int _mil = 1000, _s = 60, _min=60, _h=24,
 _d=31, _mon=13;
 long millis =0;
 private long year=0, month=0,day=0,hour=0,
 minute=0, second=0;

 public ZCalendar() {
	this(System.currentTimeMillis());
 }
 public ZCalendar(long millis) {
	this.millis = millis;
	parse();
 }
 public long get(ZCalendar.Field field) {
	switch (field) {
	 case SECOND:
		return second;
	 case MINUTE:
		return minute;
	 case HOUR:
		return hour;
	 case DAY:
		return day;
	 case MONTH:
		return month;
	 case YEAR:
		return year;
	 default:
		return millis;
	}
 }

 public void add(ZCalendar.Field field, int val) {
	switch (field) {
	 case SECOND:
		millis += (val * _mil);
		break;
	 case MINUTE:
		millis += (val * _s * _mil);
		break;
	 case HOUR:
		millis += (val * _min * _s * _mil);
		break;
	 case DAY:
		millis += (val * _h * _min * _s * _mil);
		break;
	 case MONTH:
		millis += (val * _d * _h * _min * _s * _mil);
		break;
	 case YEAR:
		millis += (val * _mon * _d * _h * _min * _s * _mil);
		break;
	 default:
		millis += val;
	}
	parse();
 }


 private void parse() {
	clear();
	long m = millis;
	m =  m / 1000;
	if (m > 59) {
	 second = m % 60;
	 m /= 60;
	 if (m > 59) {
		minute = m % 60;
		m /= 60;
		if (m > 23) {
		 hour = m % 24;
		 m /= 24;
		 if (m > 30) {
			day = m % 31;
			m /= 31;
			if (m > 12) {
			 month = m % 13;
			 m /= 13;
			 year = m;//year
			} else {//month
			 month = m;
			}
		 } else {//day
			day = m;
		 }
		} else {//hour
		 hour = m;
		}
	 } else {//minute
		minute = m;
	 }
	} else {//second
	 second = m;
	}

 }

 private void clear() {
	second = 0;
	minute = 0;
	hour = 0;
	day = 0;
	month = 0;
	year = 0;
 }

 @Override
 public String toString() {
	// TODO: Implement this method
	return String.format("%04d-%02d-%02d %02d:%02d", year
											 , month, day, hour, minute);
 }



}

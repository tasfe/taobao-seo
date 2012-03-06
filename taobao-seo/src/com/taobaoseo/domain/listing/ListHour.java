package com.taobaoseo.domain.listing;

public class ListHour {

	private int dayOfWeek;
	private int hour;
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHour() {
		return hour;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof ListHour)
		{
			ListHour target = (ListHour)o;
			return dayOfWeek == target.dayOfWeek && hour == target.hour;
		}
		return false;
	}
	
	public int hashCode()
	{
		return dayOfWeek * 37 + hour;
	}
	
	public String toString()
	{
		return "dayOfWeek: " + dayOfWeek + "; hour: " + hour;
	}
}

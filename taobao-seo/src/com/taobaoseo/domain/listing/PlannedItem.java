package com.taobaoseo.domain.listing;

import java.util.Calendar;
import java.util.Date;

import com.taobao.api.domain.Item;

public class PlannedItem{

	private Item item;
	private Date plannedListTime;

	public void setPlannedListTime(Date plannedListTime) {
		this.plannedListTime = plannedListTime;
	}

	public Date getPlannedListTime() {
		return plannedListTime;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}
	
	public int getDayOfWeek()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(item.getListTime());
		return cld.get(Calendar.DAY_OF_WEEK);
	}
	
	public int getHour()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(item.getListTime());
		return cld.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getMinute()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(item.getListTime());
		return cld.get(Calendar.MINUTE);
	}
	
	public int getPlannedDayOfWeek()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(plannedListTime);
		return cld.get(Calendar.DAY_OF_WEEK);
	}
	
	public int getPlannedHour()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(plannedListTime);
		return cld.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getPlannedMinute()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(plannedListTime);
		return cld.get(Calendar.MINUTE);
	}
}

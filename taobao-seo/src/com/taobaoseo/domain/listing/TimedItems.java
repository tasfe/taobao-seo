package com.taobaoseo.domain.listing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.taobao.api.domain.Item;

public class TimedItems {

	private Date time;
	private List<Item> items;
	
	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}
	
	public synchronized void addItem(Item item)
	{
		if (items == null)
		{
			items = new ArrayList<Item>();
		}
		items.add(item);
	}
	
	public int itemsCount()
	{
		if (items != null)
		{
			return items.size();
		}
		return 0;
	}
	
	public List<Item> getHourItems(int hour)
	{
		List<Item> result = new ArrayList<Item>();
		for (Item item : items)
		{
			Date listTime = item.getListTime();
			long hours = DateUtils.getFragmentInHours(listTime, Calendar.DAY_OF_YEAR);
			if (hours == hour)
			{
				result.add(item);
			}
		}
		return result;
	}
	
	public List<List<Item>> getHourItemsList()
	{
		List<List<Item>> list = new ArrayList<List<Item>>();
		for (int i = 0; i < 23; i++)
		{
			List<Item> items = getHourItems(i);
		}
		return list;
	}
}

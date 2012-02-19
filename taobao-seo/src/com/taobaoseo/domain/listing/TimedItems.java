package com.taobaoseo.domain.listing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public int getItemsCount()
	{
		if (items != null)
		{
			return items.size();
		}
		return 0;
	}
	
	public String toString()
	{
		return getTime() + ": " + getItemsCount();
	}
}

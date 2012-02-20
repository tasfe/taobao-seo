package com.taobaoseo.domain.listing;

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
}

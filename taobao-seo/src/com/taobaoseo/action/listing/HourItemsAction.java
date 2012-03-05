package com.taobaoseo.action.listing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.domain.Item;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.PlannedItem;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.listing.ListingEngine;
import com.taobaoseo.service.listing.ListingService;
import com.taobaoseo.utils.PagingOption;
import com.taobaoseo.utils.PagingResult;

@Results({
	  @Result(location="../listing-items.jsp")
})
public class HourItemsAction extends ActionBase{

	private int period = 7;
	private Date date;
	private int hour;
	private PagingOption option;
	private PagingResult<PlannedItem> pagingItems;
	
	public String execute() throws Exception {
		_log.info("date: " + date);
		_log.info("hour: " + hour);
		if (option == null)
		{
			option = new PagingOption();
		}
		String session = getSessionId();
		String nick = getUser();
		Map<Date, TimedItems> hourItemsMap = ListingService.INSTANCE.getHourItems(period, session);
		Date key = DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
		key = DateUtils.setHours(key, hour);
		TimedItems hourItems = hourItemsMap.get(key);
		List<Item> resultItems = hourItems.getItems();
		long total = resultItems.size();
		_log.info("result items: " + resultItems.size());
		_log.info("total: " + total);
		List<PlannedItem> items = new ArrayList<PlannedItem>();
		for (Item item : resultItems)
		{
			PlannedItem i = new PlannedItem();
			i.setItem(item);
			i.setPlannedListTime(ListingEngine.INSTANCE.getFireTime(item.getNumIid(), nick));
			items.add(i);
		}
		pagingItems = new PagingResult<PlannedItem>();
		pagingItems.setItems(items);
		pagingItems.setTotal(total);
		pagingItems.setOption(option);
		return SUCCESS;
	}
	
	public PagingResult<PlannedItem> getPagingItems()
	{
		return pagingItems;
	}
	
	public void setOption(PagingOption option) {
		this.option = option;
	}

	public PagingOption getOption() {
		return option;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHour() {
		return hour;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPeriod() {
		return period;
	}
}

package com.taobaoseo.action.listing;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.domain.Item;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.listing.ListingService;
import com.taobaoseo.utils.PagingOption;
import com.taobaoseo.utils.PagingResult;

@Results({
	  @Result(location="../items.jsp")
})
public class HourItemsAction extends ActionBase{

	private Date date;
	private int hour;
	private PagingOption option;
	private PagingResult<Item> pagingItems;
	
	public String execute() throws Exception {
		if (option == null)
		{
			option = new PagingOption();
		}
		String session = getSessionId();
		Map<Date, TimedItems> hourItemsMap = ListingService.INSTANCE.getHourItems(session);
		Date key = DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
		key = DateUtils.setHours(key, hour);
		TimedItems hourItems = hourItemsMap.get(key);
		List<Item> resultItems = hourItems.getItems();
		long total = resultItems.size();
		_log.info("result items: " + resultItems.size());
		_log.info("total: " + total);
		pagingItems = new PagingResult<Item>();
		pagingItems.setItems(resultItems);
		pagingItems.setTotal(total);
		pagingItems.setOption(option);
		return SUCCESS;
	}
	
	public PagingResult<Item> getPagingItems()
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
}

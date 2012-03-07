package com.taobaoseo.action.listing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.domain.Item;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.ListHour;
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

	private boolean expected;
	private int period = 7;
	private ListHour listHour;
	private PagingOption option;
	private PagingResult<PlannedItem> pagingItems;
	
	public String execute() throws Exception {
		_log.info("listHour: " + listHour);
		if (option == null)
		{
			option = new PagingOption();
		}
		String session = getSessionId();
		String nick = getUser();
		Map<ListHour, TimedItems> hourItemsMap = 
			expected ? ListingService.INSTANCE.getExpectedItems(nick, session) : ListingService.INSTANCE.getHourItems(period, session);
		TimedItems hourItems = hourItemsMap.get(listHour);
		if (hourItems != null)
		{
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
		}
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

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPeriod() {
		return period;
	}

	public void setListHour(ListHour listHour) {
		this.listHour = listHour;
	}

	public ListHour getListHour() {
		return listHour;
	}

	public void setExpected(boolean expected) {
		this.expected = expected;
	}

	public boolean isExpected() {
		return expected;
	}
	
	public String getDayOfWeek()
	{
		switch (listHour.getDayOfWeek())
		{
			case 1: return "星期日";
			case 2: return "星期一";
			case 3: return "星期二";
			case 4: return "星期三";
			case 5: return "星期四";
			case 6: return "星期五";
			case 7: return "星期六";
			default: return "未知";
		}
	}
}

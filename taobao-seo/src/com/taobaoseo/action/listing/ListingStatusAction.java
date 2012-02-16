package com.taobaoseo.action.listing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.TaobaoService;

@Results({
	  @Result(location="../json.jsp")
})
public class ListingStatusAction extends ActionBase{

	private Map<Date, TimedItems> timedItems = new HashMap<Date, TimedItems>();
	
	public String execute()
	{
		String session = getSessionId();
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				Date listTime = item.getListTime();
				Date day = DateUtils.truncate(listTime, Calendar.DATE);
				TimedItems tItems = timedItems.get(day);
				if (tItems == null)
				{
					tItems = new TimedItems();
					tItems.setTime(day);
					timedItems.put(day, tItems);
				}
				tItems.addItem(item);
			}
		} catch (ApiException e) {
			error(e);
		}
		return SUCCESS;
	}

	public void setTimedItems(Map<Date, TimedItems> timedItems) {
		this.timedItems = timedItems;
	}

	public Map<Date, TimedItems> getTimedItems() {
		return timedItems;
	}

	public List<Date> getDates()
	{
		List<Date> dates = new ArrayList<Date>(timedItems.keySet());
		Collections.sort(dates);
		return dates;
	}
	
	public List<TimedItems> getItems()
	{
		List<TimedItems> items = new ArrayList<TimedItems>(timedItems.values());
		Collections.sort(items, new Comparator<TimedItems>() {

			@Override
			public int compare(TimedItems o1, TimedItems o2) {
				return o1.getTime().compareTo(o2.getTime());
			}
		});
		return items;
	}
}

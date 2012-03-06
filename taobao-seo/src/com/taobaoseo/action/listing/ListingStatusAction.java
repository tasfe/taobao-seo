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
import com.taobaoseo.domain.listing.ListHour;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.TaobaoService;
import com.taobaoseo.service.listing.ListingService;

@Results({
	  @Result(location="../json.jsp")
})
public class ListingStatusAction extends ActionBase{

	private Map<ListHour, TimedItems> timedItems = new HashMap<ListHour, TimedItems>();
	
	public String execute()
	{
		String session = getSessionId();
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				Date listTime = item.getListTime();
				ListHour listHour = ListingService.INSTANCE.getListHour(listTime);
				TimedItems tItems = timedItems.get(listHour);
				if (tItems == null)
				{
					tItems = new TimedItems();
					tItems.setListHour(listHour);
					timedItems.put(listHour, tItems);
				}
				tItems.addItem(item);
			}
		} catch (ApiException e) {
			error(e);
		}
		return SUCCESS;
	}
}

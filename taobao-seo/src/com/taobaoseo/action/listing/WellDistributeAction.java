package com.taobaoseo.action.listing;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobao.api.domain.Item;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.ListHour;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.listing.ListingEngine;
import com.taobaoseo.service.listing.ListingService;

@Results({
	  @Result(type="redirect", 
			  location="/listing/hour-items", 
			  params={"listHour.dayOfWeek", "${listHour.dayOfWeek}", 
			  	"listHour.hour", "${listHour.hour}",
			  	"expected", "${expected}"})
})
public class WellDistributeAction extends ActionBase{

	private ListHour listHour;
	private boolean expected;
	
	public String execute()
	{
		_log.info("hour: " + listHour);
		String nick = getUser();
		String session = getSessionId();
		Map<ListHour, TimedItems> hourItemsMap = 
			expected ? ListingService.INSTANCE.getExpectedItems(nick, session) : 
				ListingService.INSTANCE.getHourItems(7, session);
		TimedItems hourItems = hourItemsMap.get(listHour);
		if (hourItems != null)
		{
			List<Item> resultItems = hourItems.getItems();
			int interval = 60 / resultItems.size();
			_log.info("interval: " + interval);
			for (int i = 0, n = resultItems.size(); i < n; i++)
			{
				Item item = resultItems.get(i);
				long numIid = item.getNumIid();
				String topSession = getSessionId();
				Calendar cld = Calendar.getInstance();
				cld.setTime(item.getListTime());
				cld.set(Calendar.DAY_OF_WEEK, listHour.getDayOfWeek());
				cld.set(Calendar.HOUR_OF_DAY, listHour.getHour());
				cld.set(Calendar.MINUTE, i * interval);
				Date newListTime = cld.getTime();
				if (newListTime.before(new Date()))
				{
					cld.add(Calendar.DATE, 7);
				}
				newListTime = cld.getTime();
				try {
					if (ListingEngine.INSTANCE.jobExists(numIid, nick))
					{
						ListingEngine.INSTANCE.remove(numIid, nick);
					}
					ListingEngine.INSTANCE.list(numIid, newListTime, nick, topSession);
				} catch (SchedulerException e) {
					error(e);
				}
			}
		}
		return SUCCESS;
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
}

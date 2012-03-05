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
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.listing.ListingEngine;
import com.taobaoseo.service.listing.ListingService;

@Results({
	  @Result(type="redirect", location="/listing/hour-items", params={"date", "${hour}", "hour", "${hourTime}"})
})
public class WellDistributeAction extends ActionBase{

	private Date hour;
	
	public String execute()
	{
		_log.info("hour: " + hour);
		String session = getSessionId();
		Map<Date, TimedItems> hourItemsMap = ListingService.INSTANCE.getHourItems(7, session);
		TimedItems hourItems = hourItemsMap.get(hour);
		List<Item> resultItems = hourItems.getItems();
		int interval = 60 / resultItems.size();
		_log.info("interval: " + interval);
		for (int i = 0, n = resultItems.size(); i < n; i++)
		{
			Item item = resultItems.get(i);
			long numIid = item.getNumIid();
			String nick = getUser();
			String topSession = getSessionId();
			Calendar cld = Calendar.getInstance();
			cld.setTime(hour);
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
		return SUCCESS;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public Date getHour() {
		return hour;
	}
	
	public int getHourTime()
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(hour);
		return cld.get(Calendar.HOUR_OF_DAY);
	}
}

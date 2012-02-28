package com.taobaoseo.action.listing;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.listing.ListingService;

@Results({
	  @Result(location="../period-view.jsp")
})
public class PeriodViewAction extends ActionBase{

	private int period = 7;
	private List<Date> dates;
	private Map<Date, TimedItems> hourItems;
	private TimedItems[][] itemsMatrix;
	
	public String execute()
	{
		dates = ListingService.INSTANCE.getLastDays(period);
		if (period == 7)
		{
			Collections.sort(dates, new Comparator<Date>()
			{
				@Override
				public int compare(Date date1, Date date2) {
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(date1);
					int weekday1 = cal1.get(Calendar.DAY_OF_WEEK);
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(date2);
					int weekday2 = cal2.get(Calendar.DAY_OF_WEEK);
					if (weekday1 == 1)
					{
						return 1;
					}
					if (weekday2 == 1)
					{
						return -1;
					}
					return weekday1 - weekday2;
				}
				
			});
		}
		String session = getSessionId();
		hourItems = ListingService.INSTANCE.getHourItems(period, session);
		System.out.println(hourItems);
		itemsMatrix = new TimedItems[24][period];
		for (int i = 0; i < 24; i++)
		{
			for (int j = 0; j < period; j++)
			{
				Date day = DateUtils.truncate(dates.get(j), Calendar.HOUR_OF_DAY);
				Date date = DateUtils.setHours(day, i);
				itemsMatrix[i][j] = hourItems.get(date);
			}
		}
		print();
		return SUCCESS;
	}
	
	private void print()
	{
		for (int i = 0; i < 24; i++)
		{
			System.out.println();
			for (int j = 0; j < period; j++)
			{
				System.out.print(itemsMatrix[i][j] + "\t");
			}
		}
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPeriod() {
		return period;
	}

	public TimedItems[][] getItemsMatrix() {
		return itemsMatrix;
	}
}

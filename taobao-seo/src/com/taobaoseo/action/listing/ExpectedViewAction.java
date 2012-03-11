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
import com.taobaoseo.domain.listing.ListHour;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.listing.ListingService;

@Results({
	  @Result(location="../expected-view.jsp")
})
public class ExpectedViewAction extends ActionBase{

	private int period = 7;
	private List<Date> dates;
	private TimedItems[][] itemsMatrix;
	private boolean[] itemRow;
	private int today;
	
	public String execute()
	{
		String nick = getUser();
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
		Date now = new Date();
		for (int i = 0; i < 7; i++)
		{
			if (DateUtils.isSameDay(dates.get(i), now))
			{
				today = i;
				break;
			}
		}
		String session = getSessionId();
		Map<ListHour, TimedItems> hourItems = ListingService.INSTANCE.getExpectedItems(nick, session);
		System.out.println(hourItems);
		itemsMatrix = new TimedItems[24][period];
		itemRow = new boolean[24];
		for (int i = 0; i < 24; i++)
		{
			boolean hasItem = false;
			for (int j = 0; j < period; j++)
			{
				ListHour listHour = new ListHour();
				int dayOfWeek = j + 2;
				if (dayOfWeek > 7)
				{
					dayOfWeek = dayOfWeek % 7;
				}
				listHour.setDayOfWeek(dayOfWeek);
				listHour.setHour(i);
				itemsMatrix[i][j] = hourItems.get(listHour);
				if (itemsMatrix[i][j] != null)
				{
					hasItem = true;
				}
			}
			itemRow[i] = hasItem;
		}
		print();
		return SUCCESS;
	}
	
	private void print()
	{
		for (int i = 0; i < 24; i++)
		{
			System.out.println();
			System.out.println(itemRow[i] + "\t");
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

	public void setItemRow(boolean[] itemRow) {
		this.itemRow = itemRow;
	}

	public boolean[] getItemRow() {
		return itemRow;
	}

	public void setToday(int today) {
		this.today = today;
	}

	public int getToday() {
		return today;
	}
}

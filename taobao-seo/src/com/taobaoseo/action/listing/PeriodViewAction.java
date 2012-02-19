package com.taobaoseo.action.listing;

import java.util.ArrayList;
import java.util.Calendar;
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
	  @Result(location="../period-view.jsp")
})
public class PeriodViewAction extends ActionBase{

	private int period = 7;
	private List<Date> dates;
	private Map<Date, TimedItems> hourItems = new HashMap<Date, TimedItems>();;
	private TimedItems[][] itemsMatrix;
	
	public String execute()
	{
		dates = new ArrayList<Date>();
		Date now = new Date();
		for (int i = period -1; i >= 0; i--)
		{
			Date date = DateUtils.addDays(now, -i);
			dates.add(date);
		}
		String session = getSessionId();
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				Date listTime = item.getListTime();
				Date hourTime = DateUtils.truncate(listTime, Calendar.HOUR_OF_DAY);
				TimedItems tItems = hourItems.get(hourTime);
				if (tItems == null)
				{
					tItems = new TimedItems();
					tItems.setTime(hourTime);
					hourItems.put(hourTime, tItems);
				}
				tItems.addItem(item);
			}
		} catch (ApiException e) {
			error(e);
		}
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

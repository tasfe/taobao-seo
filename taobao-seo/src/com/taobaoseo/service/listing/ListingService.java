package com.taobaoseo.service.listing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.SchedulerException;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemUpdateResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobaoseo.domain.listing.ListHour;
import com.taobaoseo.domain.listing.TimedItems;
import com.taobaoseo.service.TaobaoService;
import com.taobaoseo.taobao.TaobaoProxy;

public class ListingService {

	static Logger _logger = Logger.getLogger(ListingService.class.getName());
	
	public static final long MIN_INTERVAL = DateUtils.MILLIS_PER_MINUTE * 10;
	public static final long MAX_INTERVAL = DateUtils.MILLIS_PER_MINUTE * 50;
	
	public static final ListingService INSTANCE = new ListingService();
	
	public void checkListing(String nick, String topSession) throws ApiException
	{
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid,title,list_time,delist_time");
		req.setOrderBy("list_time:desc");
		req.setPageNo(1L);
//		req.setPageSize(200L);
		TaobaoClient client = TaobaoProxy.createClient();
		ItemsOnsaleGetResponse rsp = client.execute(req, topSession);
		if (rsp.isSuccess())
		{
			long total = rsp.getTotalResults();
			List<Item> items = rsp.getItems();
			Item last = null;
			for (Item item : items)
			{
				if (last != null)
				{
					Date time1 = last.getDelistTime();
					_logger.info("time1: " + time1);
					Date time2 = item.getDelistTime();
					_logger.info("time2: " + time2);
					long interval = time2.getTime() - time1.getTime();
					if (interval < MIN_INTERVAL || interval > MAX_INTERVAL)
					{
						Date listTime = new Date(last.getListTime().getTime() + 7 * DateUtils.MILLIS_PER_DAY/total);
						try {
							ListingEngine.INSTANCE.list(item.getNumIid(), listTime, nick, topSession);
						} catch (SchedulerException e) {
							_logger.log(Level.SEVERE, "", e);
						}
						break;
					}
				}
				last = item;
			}
		}
		else
		{
			_logger.log(Level.SEVERE, TaobaoProxy.getError(rsp));
		}
	}
	
	public List<Date> getLastDays(int n)
	{
		List<Date> dates = new ArrayList<Date>();
		Date now = new Date();
		for (int i = n -1; i >= 0; i--)
		{
			Date date = DateUtils.addDays(now, -i);
			dates.add(date);
		}
		return dates;
	}
	
	public Map<ListHour, TimedItems> getHourItems(long period, String session)
	{
		Map<ListHour, TimedItems> hourItems = new HashMap<ListHour, TimedItems>();
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				long p = item.getValidThru();
				if (p == period)
				{
					Date listTime = item.getListTime();
					ListHour listHour = getListHour(listTime);
					TimedItems tItems = hourItems.get(listHour);
					if (tItems == null)
					{
						tItems = new TimedItems();
						tItems.setListHour(listHour);
						hourItems.put(listHour, tItems);
					}
					tItems.addItem(item);
				}
			}
		} catch (ApiException e) {
			_logger.log(Level.SEVERE, "");
		}
		return hourItems;
	}
	
	public Map<ListHour, TimedItems> getExpectedItems(String nick, String session)
	{
		Map<ListHour, TimedItems> hourItems = new HashMap<ListHour, TimedItems>();
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				Date planTime = null;
				try {
					planTime = ListingEngine.INSTANCE.getFireTime(item.getNumIid(), nick);
				} catch (SchedulerException e) {
					_logger.log(Level.SEVERE, "", e);
				}
				Date listTime = planTime;
				if (listTime == null)
				{
					listTime = item.getListTime();
				}
				ListHour listHour = getListHour(listTime);
				TimedItems tItems = hourItems.get(listHour);
				if (tItems == null)
				{
					tItems = new TimedItems();
					tItems.setListHour(listHour);
					hourItems.put(listHour, tItems);
				}
				tItems.addItem(item);
			}
		} catch (ApiException e) {
			_logger.log(Level.SEVERE, "");
		}
		return hourItems;
	}
	
	public void toPeriod7(String session)
	{
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				long p = item.getValidThru();
				if (p == 14)
				{
					try
					{
						ItemUpdateResponse rsp = service.updatePeriod(item.getNumIid(), 7, session);
						if (!rsp.isSuccess())
						{
							_logger.info(rsp.getErrorCode() + " - " + rsp.getMsg() + " - " + rsp.getSubCode() + " - " + rsp.getSubMsg());
						}
					}
					catch (ApiException e)
					{
						_logger.log(Level.SEVERE, "");
					}
				}
			}
		} catch (ApiException e) {
			_logger.log(Level.SEVERE, "");
		}
	}
	
	public Date calculateNewListTime(Date oldListTime, int dayOfWeek, int hour, int minute)
	{
		Calendar cld1 = Calendar.getInstance();
		cld1.setTime(oldListTime);
		cld1.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		cld1.set(Calendar.HOUR_OF_DAY, hour);
		cld1.set(Calendar.MINUTE, minute);
		Date time = cld1.getTime();
		if (time.before(new Date()))
		{
			cld1.add(Calendar.DATE, 7);
		}
		return cld1.getTime();
	}
	
	public int getDayOfWeek(Date date)
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_WEEK);
	}
	
	public int getHour(Date date)
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}
	
	public ListHour getListHour(Date date)
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		int dayOfWeek = cld.get(Calendar.DAY_OF_WEEK);
		int hour = cld.get(Calendar.HOUR_OF_DAY);
		ListHour listHour = new ListHour();
		listHour.setDayOfWeek(dayOfWeek);
		listHour.setHour(hour);
		return listHour;
	}
}

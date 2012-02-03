package com.taobaoseo.service.listing;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.SchedulerException;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsOnsaleGetResponse;
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
							ListingEngine.INSTANCE.list(item.getNumIid(), item.getNum(), listTime, nick, topSession);
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
}
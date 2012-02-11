package com.taobaoseo.domain.listing;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemUpdateDelistingRequest;
import com.taobao.api.request.ItemUpdateListingRequest;
import com.taobao.api.response.ItemUpdateDelistingResponse;
import com.taobao.api.response.ItemUpdateListingResponse;
import com.taobaoseo.taobao.TaobaoProxy;

public class ListingJob implements Job {

	static Logger _logger = Logger.getLogger(ListingJob.class.getName());
	
	public static final long MIN_INTERVAL = DateUtils.MILLIS_PER_MINUTE * 10;
	public static final long MAX_INTERVAL = DateUtils.MILLIS_PER_MINUTE * 50;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String topSession = dataMap.getString("topSession");
		Long numIid = dataMap.getLong("numIid");
		Long num = dataMap.getLong("num");
		_logger.info("topSession: " + topSession);
		if (delisting(numIid, topSession))
		{
			listing(numIid, num, topSession);
		}
	}
	
	private boolean delisting(long numIid, String topSession)
	{
		try {
			ItemUpdateDelistingRequest req = new ItemUpdateDelistingRequest();
			req.setNumIid(numIid);
			TaobaoClient client = TaobaoProxy.createClient();
			ItemUpdateDelistingResponse rsp = client.execute(req, topSession);
			if (rsp.isSuccess())
			{
				_logger.info("delisting successfully.");
				return true;
			}
			else
			{
				_logger.log(Level.SEVERE, TaobaoProxy.getError(rsp));
			}
		} catch (ApiException e) {
			_logger.log(Level.SEVERE, "", e);
		}
		return false;
	}
	
	private boolean listing(long numIid, long num, String topSession)
	{
		try {
			ItemUpdateListingRequest req = new ItemUpdateListingRequest();
			req.setNumIid(numIid);
			req.setNum(num);
			TaobaoClient client = TaobaoProxy.createClient();
			ItemUpdateListingResponse rsp = client.execute(req, topSession);
			if (rsp.isSuccess())
			{
				_logger.info("listing successfully.");
				return true;
			}
			else
			{
				_logger.log(Level.SEVERE, TaobaoProxy.getError(rsp));
			}
		} catch (ApiException e) {
			_logger.log(Level.SEVERE, "", e);
		}
		return false;
	}
}

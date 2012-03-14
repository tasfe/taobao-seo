package com.taobaoseo.action.listing;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobao.api.ApiException;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingEngine;
import com.taobaoseo.service.listing.ListingService;

@Results({
	@Result(name="success", type="httpheader"),
	@Result(name="error", type="httpheader")
})
public class StartAction extends ActionBase{

	public String execute()
	{
		_log.info("starting...");
		long userId = getUserId();
		String topSession = getSessionId();
		try {
			ListingService.INSTANCE.checkListing(userId, topSession);
		} catch (ApiException e) {
			error(e);
		}
		_log.info("started...");
		return SUCCESS;
	}
}

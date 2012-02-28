package com.taobaoseo.action.listing;

import java.util.Date;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobao.api.response.ItemGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingEngine;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	@Result(name="success", type="httpheader", params={"status", "200"}),
	@Result(name="error", type="httpheader", params={"status", "500", "errorMessage", "Internal Error"})
})
public class ScheduleListingAction extends ActionBase{

	private String numIids;
	private Date listTime;
	
	public String execute()
	{
		_log.info("numIids: " + numIids + ", listTime: " + listTime);
		long numIid = Long.parseLong(numIids);
		String nick = getUser();
		String topSession = getSessionId();
		try {
			if (ListingEngine.INSTANCE.jobExists(numIid, nick))
			{
				ListingEngine.INSTANCE.remove(numIid, nick);
			}
			ListingEngine.INSTANCE.list(numIid, listTime, nick, topSession);
		} catch (SchedulerException e) {
			error(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public void setNumIids(String numIids) {
		this.numIids = numIids;
	}

	public String getNumIids() {
		return numIids;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}

	public Date getListTime() {
		return listTime;
	}
}

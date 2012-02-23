package com.taobaoseo.action.listing;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingEngine;

@Results({
	@Result(name="success", type="httpheader", params={"status", "200"}),
	@Result(name="error", type="httpheader", params={"status", "500", "errorMessage", "Internal Error"})
})
public class CancelJobAction extends ActionBase {

	private long numIid;
	
	public String execute()
	{
		String nick = getUser();
		try {
			ListingEngine.INSTANCE.remove(numIid, nick);
		} catch (SchedulerException e) {
			error(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public void setNumIid(long numIid) {
		this.numIid = numIid;
	}

	public long getNumIid() {
		return numIid;
	}
}

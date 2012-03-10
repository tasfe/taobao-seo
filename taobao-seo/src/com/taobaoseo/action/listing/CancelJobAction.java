package com.taobaoseo.action.listing;

import org.apache.commons.lang.StringUtils;
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

	private String numIids;
	
	public String execute()
	{
		String nick = getUser();
		try {
			String[] numIidArray = StringUtils.split(numIids, ',');
			ListingEngine.INSTANCE.removeJobs(numIidArray, nick);
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

}

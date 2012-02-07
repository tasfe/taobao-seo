package com.taobaoseo.action.listing;

import java.util.Date;

import org.quartz.SchedulerException;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingEngine;

public class ScheduleListingAction extends ActionBase{

	private String numIids;
	private long num;
	private Date listTime;
	
	public String execute()
	{
		long numIid = Long.parseLong(numIids);
		String nick = getUser();
		String topSession = getSessionId();
		try {
			ListingEngine.INSTANCE.list(numIid, num, listTime, nick, topSession);
		} catch (SchedulerException e) {
			error(e);
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

	public void setNum(long num) {
		this.num = num;
	}

	public long getNum() {
		return num;
	}
}

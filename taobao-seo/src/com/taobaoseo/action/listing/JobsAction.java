package com.taobaoseo.action.listing;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.domain.listing.PlannedItem;
import com.taobaoseo.service.listing.ListingEngine;

@Results({
	  @Result(location="../jobs.jsp")
})
public class JobsAction extends ActionBase {

	private List<PlannedItem> plannedItems;
	
	public String execute()
	{
		try {
			plannedItems = ListingEngine.INSTANCE.getPlannedItems();
		} catch (SchedulerException e) {
			error(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public void setPlannedItems(List<PlannedItem> plannedItems) {
		this.plannedItems = plannedItems;
	}

	public List<PlannedItem> getPlannedItems() {
		return plannedItems;
	}
}

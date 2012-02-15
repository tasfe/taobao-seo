package com.taobaoseo.action.listing;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingEngine;

@Results({
	  @Result(location="../jobs.jsp")
})
public class JobsAction extends ActionBase {

	private List<JobDetail> jobs;
	
	public String execute()
	{
		try {
			setJobs(ListingEngine.INSTANCE.getJobs());
			_log.info("jobs: " + jobs);
		} catch (SchedulerException e) {
			error(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public void setJobs(List<JobDetail> jobs) {
		this.jobs = jobs;
	}

	public List<JobDetail> getJobs() {
		return jobs;
	}
}

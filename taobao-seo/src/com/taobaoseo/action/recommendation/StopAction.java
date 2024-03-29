package com.taobaoseo.action.recommendation;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.recommendation.RecommendEngine;

@Results({
	@Result(name="success", type="httpheader"),
	@Result(name="error", type="httpheader")
})
public class StopAction extends ActionBase{

	public String execute()
	{
		_log.info("stopping...");
		long userId = getUserId();
		RecommendEngine engine = new RecommendEngine(userId);
		try {
			engine.pause();
			_log.info("stopped...");
			return SUCCESS;
		} catch (SchedulerException e) {
			error(e);
			return ERROR;
		}
	}
}

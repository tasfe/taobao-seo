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
public class StartAction extends ActionBase{

	public String execute()
	{
		_log.info("starting...");
		long userId = getUserId();
		String topSession = getSessionId();
		RecommendEngine engine = new RecommendEngine(userId);
		try {
			if (engine.isStarted())
			{
				engine.resume();
			}
			else
			{
				engine.start(topSession);
			}
		} catch (SchedulerException e) {
			error(e);
		}
		_log.info("started...");
		return SUCCESS;
	}
}

package com.taobaoseo.action.recommendation;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobaoseo.servlet.MainServlet;

@Results({
	@Result(name="success", type="httpheader"),
	@Result(name="error", type="httpheader")
})
public class StopAction extends ActionBase{

	public String execute()
	{
		_log.info("stopping...");
//		String nick = getUser();
//		try {
//			MainServlet.getEngine().pause(nick);
//			return SUCCESS;
//		} catch (SchedulerException e) {
//			error(e);
//		}
//		return ERROR;
		_log.info("stopped...");
		return SUCCESS;
	}
}

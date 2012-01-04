package com.taobaoseo.action.listing;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobaoseo.action.ActionBase;

@Results({
	@Result(name="success", type="httpheader"),
	@Result(name="error", type="httpheader")
})
public class StartAction extends ActionBase{

	public String execute()
	{
		_log.info("starting...");
//		String nick = getUser();
//		String topSession = getSessionId();
//		try {
//			if (MainServlet.getEngine().isStarted(nick))
//			{
//				MainServlet.getEngine().resume(nick);
//			}
//			else
//			{
//				MainServlet.getEngine().recommend(nick, topSession);
//			}
//		} catch (SchedulerException e) {
//			error(e);
//		}
		_log.info("started...");
		return SUCCESS;
	}
}

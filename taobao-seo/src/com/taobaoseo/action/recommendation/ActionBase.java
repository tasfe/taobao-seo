package com.taobaoseo.action.recommendation;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.taobao.api.TaobaoResponse;
import com.taobaoseo.Constants;

public class ActionBase extends ActionSupport implements Constants{
	
	static Logger _log = Logger.getLogger(ActionBase.class.getName());
	
	protected static void error(TaobaoResponse rsp)
	{
		_log.info(rsp.getErrorCode() + " - " + rsp.getMsg() + " - " + rsp.getSubCode() + " - " + rsp.getSubMsg());
	}
	
	protected static void error(Throwable e)
	{
		_log.log(Level.SEVERE, "", e);
	}
	
	protected String getSessionId()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		return (String)session.get(TOP_SESSION);
	}
	
	protected long getUserId()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String)session.get(USER_ID);
		if (userId != null)
		{
			return Long.parseLong(userId);
		}
		return -1;
	}
	
	protected static String getUser()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		return (String)session.get(USER);
	}
	
	protected String getVersion()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		return (String)session.get(VERSION);
	}
}

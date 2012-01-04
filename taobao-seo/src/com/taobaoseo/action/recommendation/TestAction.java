package com.taobaoseo.action.recommendation;

import org.apache.struts2.convention.annotation.Action;
import org.quartz.SchedulerException;

import com.taobaoseo.recommendation.domain.RecommendScope;
import com.taobaoseo.servlet.MainServlet;

public class TestAction extends ActionBase{
	
	private String msg;
	
	public String execute(){
		_log.info("TTTTTTTTTTTT");
//		RecommendScope scope = new RecommendScope();
//		scope.setKeyword("keyword");
//		scope.setItems("items");
//		try {
//			MainServlet.getEngine().recommend("nick", scope, "session");
//		} catch (SchedulerException e) {
//			error(e);
//		}
		msg = "TTTTTTTTTTTT";
		return SUCCESS;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}

package com.taobaoseo.action.recommendation;

import com.opensymphony.xwork2.Action;
import com.recommendation.action.ActionBase;

public class IndexAction extends ActionBase {

	public String execute()
	{
		System.out.println("index...");
		return Action.SUCCESS;
	}
}

package com.taobaoseo.servlet;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.taobaoseo.recommendation.service.RecommendEngine;

public class MainServlet extends HttpServlet{

	static Logger _log = Logger.getLogger(MainServlet.class.getName());
	private static RecommendEngine engine;
	
	public void init()
	{
		engine = new RecommendEngine();
	}
	
	public void destroy()
	{
		engine.shutdown();
	}
	
	public static RecommendEngine getEngine()
	{
		return engine;
	}
}

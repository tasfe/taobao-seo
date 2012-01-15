package com.taobaoseo.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.taobaoseo.service.recommendation.RecommendEngine;

public class MainServlet extends HttpServlet{

	static Logger _log = Logger.getLogger(MainServlet.class.getName());
	
	public void init()
	{
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			_log.log(Level.SEVERE, "", e);
		}
	}
	
	public void destroy()
	{
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.shutdown();
		} catch (SchedulerException e) {
			_log.log(Level.SEVERE, "", e);
		}
	}
}

package com.taobaoseo.service.listing;

import java.util.Date;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import com.taobaoseo.domain.listing.ListingJob;
import com.taobaoseo.domain.listing.ListingJobListener;
import com.taobaoseo.service.recommendation.RecommendEngine;

public class ListingEngine {

	static Logger _logger = Logger.getLogger(RecommendEngine.class.getName());

	public static final int INTERVAL = 5;
	
	public static final ListingEngine INSTANCE = new ListingEngine();
	
	private ListingEngine()
	{
		
	}
	
	public boolean jobExists(long numIid, String nick) throws SchedulerException
	{
		String jobName = String.valueOf(numIid);
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler.checkExists(JobKey.jobKey(jobName, nick));
	}
	
	public void list(long numIid, long num, Date listTime, String nick, String topSession) throws SchedulerException
	{
		String jobName = String.valueOf(numIid);
        JobDetail job = JobBuilder.newJob(ListingJob.class)
            .withIdentity(jobName, nick)
            .usingJobData("topSession", topSession)
            .usingJobData("numIid", numIid)
            .usingJobData("num", num)
            .build();
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(jobName, nick)
            .startAt(listTime)         
            .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.getListenerManager().addJobListener(
        		new ListingJobListener(), KeyMatcher.keyEquals(JobKey.jobKey(jobName, nick)));
	}
	
	public void pause(long numIid, String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.pauseJob(new JobKey(String.valueOf(numIid), nick));
	}
	
	public void resume(long numIid, String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.resumeJob(new JobKey(String.valueOf(numIid), nick));
	}
	
	public boolean isStarted(long numIid, String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler.getJobDetail(new JobKey(String.valueOf(numIid), nick)) != null;
	}
	
	public void remove(long numIid, String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.deleteJob(new JobKey(String.valueOf(numIid), nick));
	}
}

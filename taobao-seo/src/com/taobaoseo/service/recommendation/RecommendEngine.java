package com.taobaoseo.service.recommendation;

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

import com.taobaoseo.db.Dao;
import com.taobaoseo.domain.recommendation.RecommendJob;
import com.taobaoseo.domain.recommendation.RecommendScope;

public class RecommendEngine {
	
	static Logger _logger = Logger.getLogger(RecommendEngine.class.getName());

	public static final int INTERVAL = 5;
	public static final String JOB_NAME = "recommend";
	
	private long _userId;
	
	public RecommendEngine(long userId)
	{
		_userId = userId;
	}
	
	private String getGroup() {
		return String.valueOf(_userId);
	}
	
	public void start(String topSession) throws SchedulerException
	{
        JobDetail job = JobBuilder.newJob(RecommendJob.class)
            .withIdentity("recommend", getGroup())
            .usingJobData("topSession", topSession)
            .build();
            
        	RecommendScope scope = Dao.INSTANCE.getRecommendScope();
            job.getJobDataMap().put("scope", scope);
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("recommend", getGroup())
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(INTERVAL)
                    .repeatForever())            
            .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
	}
	
	public void pause() throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.pauseJob(new JobKey("recommend", getGroup()));
	}
	
	public void resume() throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.resumeJob(new JobKey("recommend", getGroup()));
	}
	
	public boolean isStarted() throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler.checkExists(new JobKey("recommend", getGroup()));
	}
	
	public void shutDown() throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.deleteJob(new JobKey("recommend", getGroup()));
	}
}

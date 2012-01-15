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
	
	public static final RecommendEngine INSTANCE = new RecommendEngine();
	
	private RecommendEngine()
	{
		
	}
	
	public void recommend(String nick, String topSession) throws SchedulerException
	{
        JobDetail job = JobBuilder.newJob(RecommendJob.class)
            .withIdentity("recommend", nick)
            .usingJobData("topSession", topSession)
            .build();
            
        	RecommendScope scope = Dao.INSTANCE.getRecommendScope();
            job.getJobDataMap().put("scope", scope);
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("recommend", nick)
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(INTERVAL)
                    .repeatForever())            
            .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
	}
	
	public void pause(String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.pauseJob(new JobKey("recommend", nick));
	}
	
	public void resume(String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.resumeJob(new JobKey("recommend", nick));
	}
	
	public boolean isStarted(String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler.getJobDetail(new JobKey("recommend", nick)) != null;
	}
	
	public void remove(String nick) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.deleteJob(new JobKey("recommend", nick));
	}
}

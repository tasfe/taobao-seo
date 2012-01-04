package com.taobaoseo.recommendation.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.taobaoseo.db.Dao;
import com.taobaoseo.recommendation.domain.RecommendJob;
import com.taobaoseo.recommendation.domain.RecommendScope;

public class RecommendEngine {
	
	static Logger _logger = Logger.getLogger(RecommendEngine.class.getName());

	public static final int INTERVAL = 5;
	private Scheduler scheduler;
	
	public RecommendEngine()
	{
//		try {
//			scheduler = StdSchedulerFactory.getDefaultScheduler();
//			scheduler.start();
//		} catch (SchedulerException e) {
//			_logger.log(Level.SEVERE, "", e);
//		}
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
        
        scheduler.scheduleJob(job, trigger);
	}
	
	public void pause(String nick) throws SchedulerException
	{
		scheduler.pauseJob(new JobKey("recommend", nick));
	}
	
	public void resume(String nick) throws SchedulerException
	{
		scheduler.resumeJob(new JobKey("recommend", nick));
	}
	
	public boolean isStarted(String nick) throws SchedulerException
	{
		return scheduler.getJobDetail(new JobKey("recommend", nick)) != null;
	}
	
	public void remove(String nick) throws SchedulerException
	{
		scheduler.deleteJob(new JobKey("recommend", nick));
	}
	
	public void shutdown()
	{
		try {
            scheduler.shutdown();
        } catch (SchedulerException se) {
        	_logger.log(Level.SEVERE, "", se);
        }
	}
}

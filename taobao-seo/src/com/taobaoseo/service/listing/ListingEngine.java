package com.taobaoseo.service.listing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemGetResponse;
import com.taobaoseo.domain.listing.ListingJob;
import com.taobaoseo.domain.listing.ListingJobListener;
import com.taobaoseo.domain.listing.PlannedItem;
import com.taobaoseo.service.recommendation.RecommendEngine;
import com.taobaoseo.taobao.TaobaoProxy;

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
//        ((AbstractTrigger)trigger).setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
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
		_logger.info("cancelling job: " + numIid + ":" + nick);
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.deleteJob(new JobKey(String.valueOf(numIid), nick));
	}
	
	public List<PlannedItem> getPlannedItems() throws SchedulerException
	{
		List<PlannedItem> plannedItems = new ArrayList<PlannedItem>();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		for(String group: scheduler.getJobGroupNames()) {
		    _logger.info(group);
		    for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
		        _logger.info("    " + jobKey.getName());
		        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
		        Date fireTime = trigger.getStartTime();
		        PlannedItem plannedItem = new PlannedItem();
		        ItemGetResponse rsp;
				try {
					rsp = TaobaoProxy.getItem(Long.parseLong(jobKey.getName()), "num_iid,title,price,pic_url,list_time");
					Item item = rsp.getItem();
					plannedItem.setItem(item);
			        plannedItem.setPlannedListTime(fireTime);
			        plannedItems.add(plannedItem);
				} catch (NumberFormatException e) {
					_logger.log(Level.SEVERE, "", e);
				} catch (ApiException e) {
					_logger.log(Level.SEVERE, "", e);
				}
		    }
		}
		return plannedItems;
	}
}

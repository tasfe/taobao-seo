package com.taobaoseo.service.listing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
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
import com.taobaoseo.taobao.TaobaoProxy;

public class ListingEngine {

	static Logger _logger = Logger.getLogger(ListingEngine.class.getName());

	public static final int INTERVAL = 5;
	
	private long _userId;
	
	public ListingEngine(long userId)
	{
		_userId = userId;
	}
	
	private String getGroup() {
		return String.valueOf(_userId);
	}
	
	public boolean jobExists(long numIid) throws SchedulerException
	{
		String jobName = String.valueOf(numIid);
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler.checkExists(JobKey.jobKey(jobName, getGroup()));
	}
	
	public void list(long numIid, Date listTime, String topSession) throws SchedulerException
	{
		_logger.info("addding job...numIid: " + numIid + " listTime: " + listTime);
		String jobName = String.valueOf(numIid);
        JobDetail job = JobBuilder.newJob(ListingJob.class)
            .withIdentity(jobName, getGroup())
            .usingJobData("userId", _userId)
            .usingJobData("topSession", topSession)
            .usingJobData("numIid", numIid)
            .build();
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(jobName, getGroup())
            .startAt(listTime)         
            .build();
//        ((AbstractTrigger)trigger).setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.getListenerManager().addJobListener(
        		new ListingJobListener(), KeyMatcher.keyEquals(JobKey.jobKey(jobName, getGroup())));
	}
	
	public void pause(long numIid) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.pauseJob(new JobKey(String.valueOf(numIid), getGroup()));
	}
	
	public void resume(long numIid) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.resumeJob(new JobKey(String.valueOf(numIid), getGroup()));
	}
	
	public boolean isStarted(long numIid) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler.getJobDetail(new JobKey(String.valueOf(numIid), getGroup())) != null;
	}
	
	public void remove(long numIid) throws SchedulerException
	{
		_logger.info("cancelling job: " + numIid + ":" + getGroup());
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.deleteJob(new JobKey(String.valueOf(numIid), getGroup()));
	}
	
	public void removeJobs(String[] numIids) throws SchedulerException
	{
		_logger.info("cancelling jobs: " + Arrays.toString(numIids) + ":" + getGroup());
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		List<JobKey> jobKeys = new ArrayList<JobKey>();
		for (String numIid : numIids)
		{
			jobKeys.add(JobKey.jobKey(numIid, getGroup()));
		}
		scheduler.deleteJobs(jobKeys);
	}
	
	public List<PlannedItem> getPlannedItems() throws SchedulerException
	{
		List<PlannedItem> plannedItems = new ArrayList<PlannedItem>();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(getGroup()))) {
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
		Collections.sort(plannedItems, new Comparator<PlannedItem>(){

			@Override
			public int compare(PlannedItem item1, PlannedItem item2) {
				return item1.getPlannedListTime().compareTo(item2.getPlannedListTime());
			}
			
		});
		return plannedItems;
	}
	
	public Date getFireTime(long numIid) throws SchedulerException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(String.valueOf(numIid), getGroup()));
		if (trigger != null)
		{
			return trigger.getStartTime();
		}
		return null;
	}
}

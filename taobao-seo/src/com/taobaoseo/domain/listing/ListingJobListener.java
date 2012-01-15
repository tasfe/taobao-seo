package com.taobaoseo.domain.listing;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

import com.taobaoseo.service.listing.ListingService;

public class ListingJobListener extends JobListenerSupport{

	@Override
	public String getName() {
		return "listingJobListener";
	}

	@Override
    public void jobWasExecuted(JobExecutionContext context,
            JobExecutionException jobException) {
//		ListingService.INSTANCE.checkListing(nick, topSession);
	}
}

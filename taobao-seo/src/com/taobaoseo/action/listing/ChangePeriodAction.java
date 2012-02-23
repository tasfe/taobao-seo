package com.taobaoseo.action.listing;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingService;

@Results({
	@Result(name="success", type="httpheader"),
	@Result(name="error", type="httpheader")
})
public class ChangePeriodAction extends ActionBase{

	public String execute()
	{
		String session = getSessionId();
		ListingService.INSTANCE.toPeriod7(session);
		return SUCCESS;
	}
}

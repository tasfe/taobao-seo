package com.taobaoseo.action.listing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.listing.ListingEngine;
import com.taobaoseo.service.listing.ListingService;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	@Result(name="success", type="httpheader", params={"status", "200"}),
	@Result(name="error", type="httpheader", params={"status", "500", "errorMessage", "Internal Error"})
})
public class ScheduleListingAction extends ActionBase{

	private String numIids;
	private int dayOfWeek;
	private String time;
	private boolean wellDistribute;
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	public String execute()
	{
		_log.info("numIids: " + numIids + ", dayOfWeek: " + dayOfWeek + ", time: " + time);
		try {
			if (dayOfWeek > 7)
			{
				dayOfWeek = dayOfWeek % 7;
			}
			_log.info("dayOfWeek: " + dayOfWeek);
			if (time.length() == 4)
			{
				time = "0" + time;
			}
			_log.info("time: " + time);
			Date t = timeFormat.parse(time);
			Calendar cld = Calendar.getInstance();
			cld.setTime(t);
			int hour = cld.get(Calendar.HOUR_OF_DAY);
			int minute = cld.get(Calendar.MINUTE);
			String[] numIidArray = StringUtils.split(numIids, ',');
			int interval = 0;
			if (wellDistribute)
			{
				interval = 60 / numIidArray.length;
			}
			for(int i = 0; i < numIidArray.length; i++)
			{
				String strNumIid = numIidArray[i];
				try {
					long numIid = Long.parseLong(strNumIid);
					scheduleListing(numIid, dayOfWeek, hour, minute + i * interval);
				} catch (ApiException e) {
					error(e);
				}catch (Exception e) {
					error(e);
				}	
			}
		} catch (ParseException e2) {
			error(e2);
		}
		return SUCCESS;
	}
	
	private boolean scheduleListing(long numIid, int dayOfWeek, int hour, int minute) throws ApiException
	{
		ItemGetResponse rsp = TaobaoProxy.getItem(numIid, "list_time");
		if (rsp.isSuccess())
		{
			Item item = rsp.getItem();
			Date listTime = item.getListTime();
			Date newListTime = ListingService.INSTANCE.calculateNewListTime(listTime, dayOfWeek, hour, minute);
			_log.info("newListTime: " + newListTime);
			
			String nick = getUser();
			String topSession = getSessionId();
			try {
				if (ListingEngine.INSTANCE.jobExists(numIid, nick))
				{
					ListingEngine.INSTANCE.remove(numIid, nick);
				}
				ListingEngine.INSTANCE.list(numIid, newListTime, nick, topSession);
				return true;
			} catch (SchedulerException e) {
				error(e);
			}
		}
		else
		{
			_log.info(TaobaoProxy.getError(rsp));
		}
		return false;
	}

	public void setNumIids(String numIids) {
		this.numIids = numIids;
	}

	public String getNumIids() {
		return numIids;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setWellDistribute(boolean wellDistribute) {
		this.wellDistribute = wellDistribute;
	}

	public boolean isWellDistribute() {
		return wellDistribute;
	}
}

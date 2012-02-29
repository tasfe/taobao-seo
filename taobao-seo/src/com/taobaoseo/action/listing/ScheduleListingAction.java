package com.taobaoseo.action.listing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

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
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	public String execute()
	{
		_log.info("numIids: " + numIids + ", dayOfWeek: " + dayOfWeek + ", time: " + time);
		long numIid = Long.parseLong(numIids);
		ItemGetResponse rsp;
		try {
			Date t = timeFormat.parse(time);
			Calendar cld = Calendar.getInstance();
			cld.setTime(t);
			int hour = cld.get(Calendar.HOUR_OF_DAY);
			int minute = cld.get(Calendar.MINUTE);
			rsp = TaobaoProxy.getItem(numIid, "list_time");
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
				} catch (SchedulerException e) {
					error(e);
					return ERROR;
				}
			}
			else
			{
				_log.info(TaobaoProxy.getError(rsp));
			}
		} catch (ApiException e1) {
			_log.log(Level.SEVERE, "", e1);
		} catch (ParseException e) {
			_log.log(Level.SEVERE, "", e);
		}
		return SUCCESS;
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
}

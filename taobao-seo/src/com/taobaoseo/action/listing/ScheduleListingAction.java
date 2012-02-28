package com.taobaoseo.action.listing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	@Result(name="success", type="httpheader", params={"status", "200"}),
	@Result(name="error", type="httpheader", params={"status", "500", "errorMessage", "Internal Error"})
})
public class ScheduleListingAction extends ActionBase{

	private String numIids;
	private int dayOfWeek;
	private String time;
	private Date listTime;
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	public String execute()
	{
		_log.info("numIids: " + numIids + ", listTime: " + listTime);
		long numIid = Long.parseLong(numIids);
		ItemGetResponse rsp;
		try {
			rsp = TaobaoProxy.getItem(numIid, "listTime");
			if (rsp.isSuccess())
			{
				Item item = rsp.getItem();
				Date listTime = item.getListTime();
				_log.info("listTime: " + listTime);
				Calendar cld = Calendar.getInstance();
				cld.setTime(listTime);
				Date newListTime = cld.getTime();
				Date t = timeFormat.parse(time);
				Calendar cld2 = Calendar.getInstance();
				cld2.setTime(t);
				cld.set(Calendar.HOUR_OF_DAY, cld2.get(Calendar.HOUR_OF_DAY));
				cld.set(Calendar.MINUTE, cld2.get(Calendar.MINUTE));
				_log.info("newListTime: " + newListTime);
			}
		} catch (ApiException e1) {
			_log.log(Level.SEVERE, "", e1);
		} catch (ParseException e) {
			_log.log(Level.SEVERE, "", e);
		}
		
//		String nick = getUser();
//		String topSession = getSessionId();
//		try {
//			if (ListingEngine.INSTANCE.jobExists(numIid, nick))
//			{
//				ListingEngine.INSTANCE.remove(numIid, nick);
//			}
//			ListingEngine.INSTANCE.list(numIid, listTime, nick, topSession);
//		} catch (SchedulerException e) {
//			error(e);
//			return ERROR;
//		}
		return SUCCESS;
	}

	public void setNumIids(String numIids) {
		this.numIids = numIids;
	}

	public String getNumIids() {
		return numIids;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}

	public Date getListTime() {
		return listTime;
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

package com.taobaoseo.action.listing;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.TaobaoService;

@Results({
	  @Result(location="../json.jsp")
})
public class ListingStatusAction extends ActionBase{

	private Map<Date, Integer> listingCount = new HashMap<Date, Integer>();
	
	public String execute()
	{
		String session = getSessionId();
		try {
			TaobaoService service = new TaobaoService();
			List<Item> items = service.getAllOnsaleItems(session);
			for (Item item : items)
			{
				Date listTime = item.getListTime();
				Date day = DateUtils.truncate(listTime, Calendar.DATE);
				Integer c = listingCount.get(day);
				if (c == null)
				{
					c = 0;
				}
				listingCount.put(day, ++c);
			}
		} catch (ApiException e) {
			error(e);
		}
		return SUCCESS;
	}

	public Map<Date, Integer> getListingCount() {
		return listingCount;
	}

	public void setListingCount(Map<Date, Integer> listingCount) {
		this.listingCount = listingCount;
	}
}

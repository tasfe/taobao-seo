package com.taobaoseo.action.listing;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ListCellRenderer;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.service.TaobaoService;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	  @Result(location="../json.jsp")
})
public class ItemsAction extends ActionBase{

	private String json;
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

	public void setJson(String json) {
		this.json = json;
	}

	public String getJson() {
		return json;
	}
}

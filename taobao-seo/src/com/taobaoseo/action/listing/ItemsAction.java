package com.taobaoseo.action.listing;

import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;

import com.taobao.api.ApiException;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	  @Result(location="../json.jsp")
})
public class ItemsAction extends ActionBase{

	private String json;
	
	public String execute()
	{
		String session = getSessionId();
		try {
			ItemsOnsaleGetResponse rsp = TaobaoProxy.getOnSales(session, 0, 200, null, null);
			if (rsp.isSuccess())
			{
				json = rsp.getBody();
			}
			else
			{
				error(rsp);
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

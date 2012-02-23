package com.taobaoseo.action.listing;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	  @Result(location="../adjust-form.jsp")
})
public class AdjustingAction extends ActionBase{

	private long numIid;
	private Item item;
	
	public String execute()
	{
		try {
			ItemGetResponse rsp = TaobaoProxy.getItem(numIid, "num_iid,title,pic_url,list_time,num");
			if (rsp.isSuccess())
			{
				item = rsp.getItem();
			}
		} catch (ApiException e) {
			error(e);
		}
		return SUCCESS;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setNumIid(long numIid) {
		this.numIid = numIid;
	}

	public long getNumIid() {
		return numIid;
	}
}

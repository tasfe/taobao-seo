package com.taobaoseo.action.listing;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.ApiException;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.response.SellercatsListGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.taobao.TaobaoProxy;

@Results({
	  @Result(location="../items-selector.jsp")
})
public class ItemsSelectorAction extends ActionBase{

	private List<SellerCat> categories;
	
	public String execute()
	{
		String nick = getUser();
		SellercatsListGetResponse catRsp;
		try {
			catRsp = TaobaoProxy.getSellerCategories(nick);
			setCategories(catRsp.getSellerCats());
		} catch (ApiException e) {
			error(e);
		}
		return SUCCESS;
	}

	public void setCategories(List<SellerCat> categories) {
		this.categories = categories;
	}

	public List<SellerCat> getCategories() {
		return categories;
	}
}

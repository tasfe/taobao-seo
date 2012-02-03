package com.taobaoseo.action.listing;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobaoseo.action.ActionBase;
import com.taobaoseo.taobao.TaobaoProxy;
import com.taobaoseo.utils.PagingOption;
import com.taobaoseo.utils.PagingResult;

@Results({
	  @Result(location="../items.jsp")
})
public class ItemsAction extends ActionBase{

//	private ItemsFilter filter;
	private PagingOption option;
	private PagingResult<Item> pagingItems;
	
	public String execute() throws Exception {
		if (option == null)
		{
			option = new PagingOption();
		}
//		if (filter == null)
//		{
//			filter = new ItemsFilter();
//		}
		String topSession = getSessionId();
		List<Item> resultItems = null;
		long total = 0;
		if (false)//filter.getSaleStatus() == ItemsFilter.STATUS_INVENTORY)
		{
			ItemsInventoryGetResponse rsp = TaobaoProxy.getInventory(topSession, option.getCurrentPage() + 1, option.getLimit(), null, null, null);//filter.getBanner(), filter.getSellerCids(), filter.getKeyWord());
			if (rsp.isSuccess())
			{
				resultItems = rsp.getItems();
				if (resultItems != null)
				{
					total = rsp.getTotalResults();
				}
			}
			else
			{
				error(rsp);
			}
		}
		else
		{
			ItemsOnsaleGetResponse rsp = TaobaoProxy.getOnSales(topSession, option.getCurrentPage() + 1, option.getLimit(), null, null);//filter.getSellerCids(), filter.getKeyWord());
			if (rsp.isSuccess())
			{
				resultItems = rsp.getItems();
				if (resultItems != null)
				{
					total = rsp.getTotalResults();
				}
			}
			else
			{
				error(rsp);
			}
		}
		_log.info("result items: " + resultItems.size());
		_log.info("total: " + total);
		pagingItems = new PagingResult<Item>();
		pagingItems.setItems(resultItems);
		pagingItems.setTotal(total);
		pagingItems.setOption(option);
		return SUCCESS;
	}
	
	public PagingResult<Item> getPagingItems()
	{
		return pagingItems;
	}

//	public void setFilter(ItemsFilter filter) {
//		this.filter= filter;
//	}
//
//	public ItemsFilter getFilter() {
//		return filter;
//	}
	
	public void setOption(PagingOption option) {
		this.option = option;
	}

	public PagingOption getOption() {
		return option;
	}
}

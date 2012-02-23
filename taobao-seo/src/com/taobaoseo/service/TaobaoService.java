package com.taobaoseo.service;

import java.util.ArrayList;
import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemUpdateResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobaoseo.taobao.TaobaoProxy;
import com.taobaoseo.utils.PagingResult;

public class TaobaoService {
	public static final int PAGE_SIZE = 200;
	
	public List<Item> getAllOnsaleItems(String session) throws ApiException
	{
		List<Item> items = new ArrayList<Item>();
		ItemsOnsaleGetResponse rsp = getPage(1, session);
		if (rsp.isSuccess())
		{
			items.addAll(rsp.getItems());
			long total = rsp.getTotalResults();
			int pageCount = PagingResult.getPageCount(total, PAGE_SIZE);
			for (int i = 2; i <= pageCount; i++)
			{
				rsp = getPage(i, session);
				if (rsp.isSuccess())
				{
					items.addAll(rsp.getItems());
				}
			}
		}
		return items;
	}
	
	public ItemsOnsaleGetResponse getPage(int pageNo, String session) throws ApiException
	{
		return getPage((long)PAGE_SIZE, pageNo, session);
	}
	
	public ItemsOnsaleGetResponse getPage(long pageSize, int pageNo, String session) throws ApiException
	{
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid,title,price,pic_url,list_time,delist_time,valid_thru");
		req.setOrderBy("list_time:desc");
		req.setPageNo(0L);
		req.setPageSize(pageSize);
		return TaobaoProxy.createClient().execute(req, session);
	}
	
	public ItemUpdateResponse updatePeriod(long numIid, long period, String session) throws ApiException
	{
		ItemUpdateRequest req = new ItemUpdateRequest();
		req.setNumIid(numIid);
		req.setValidThru(period);
		 
		ItemUpdateResponse rsp = TaobaoProxy.createClient().execute(req, session);
		return rsp;
	}
}

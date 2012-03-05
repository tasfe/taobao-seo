package com.taobaoseo.service.recommendation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;


import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemsListGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobaoseo.domain.recommendation.RecommendScope;
import com.taobaoseo.taobao.TaobaoProxy;

public class RecommendItemsProvider {
	
	static Logger _logger = Logger.getLogger(RecommendItemsProvider.class.getName());
	
	public static final RecommendItemsProvider INSTANCE = new RecommendItemsProvider();

	public List<Item> getItems(RecommendScope scope, int num, String session)
	{
		switch(scope.getType())
		{
			case RecommendScope.TYPE_KEYWORD:
				return getItemsByKeyword(scope.getKeyword(), session, num);
			case RecommendScope.TYPE_SPECIFIED:
				return getSpecifiedItems(scope.getItems(), session, num);
			default:
				return getItemsByKeyword(null, session, num);
		}
	}
	
	private List<Item> getItemsByKeyword(String keyword, String topSession, int num)
	{
		try {
			ItemsOnsaleGetResponse itemsRsp = TaobaoProxy.getOldestOnSales(topSession, num, keyword);
			if (itemsRsp.isSuccess())
			{
				return itemsRsp.getItems();
			}
			else
			{
				_logger.log(Level.SEVERE, TaobaoProxy.getError(itemsRsp));
			}
		} catch (ApiException e) {
			_logger.log(Level.SEVERE, "", e);
		}
		return null;
	}
	
	private List<Item> getSpecifiedItems(String numIids, String session, int num)
	{
		List<Item> items = new ArrayList<Item>();
		String[] numIidArray = numIids.split(", ");
		int pages = numIidArray.length / 20 + 1;
		for (int i = 0; i < pages; i++)
		{
			int from = i * 20;
			int to = Math.min((i + 1) * 20, numIidArray.length);
			String[] page = Arrays.copyOfRange(numIidArray, from, to);
			String partialNumIids = StringUtils.join(page, ",");
			try {
				ItemsListGetResponse rsp = TaobaoProxy.getItems(partialNumIids, "num_iid,title,pic_url,price,delist_time", session);
				if (rsp.isSuccess())
				{
					List<Item> list = rsp.getItems();
					items.addAll(list);
				}
				else
				{
					_logger.warning(TaobaoProxy.getError(rsp));
				}
			} catch (ApiException e) {
				_logger.log(Level.SEVERE, "", e);
			}
		}
		Collections.sort(items, new Comparator<Item>(){
			public int compare(Item o1, Item o2) {
				return o1.getDelistTime().compareTo(o2.getDelistTime());
			}
		});
		items = items.subList(0, num - 1);
		return items;
	}
}

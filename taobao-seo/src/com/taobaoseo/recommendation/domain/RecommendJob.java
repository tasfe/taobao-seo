package com.taobaoseo.recommendation.domain;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemRecommendAddResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import com.taobaoseo.recommendation.taobao.TaobaoProxy;

public class RecommendJob implements Job{

	static Logger _logger = Logger.getLogger(RecommendJob.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String topSession = dataMap.getString("topSession");
		RecommendScope scope = (RecommendScope)dataMap.get("scope");
		_logger.info("topSession: " + topSession);
		_logger.info("scope: " + scope);
//		try {
//			ShopRemainshowcaseGetResponse remainRsp = TaobaoProxy.getRemainShowCaseCount(topSession);
//			if (remainRsp.isSuccess())
//			{
//				long remainCount = remainRsp.getShop().getRemainCount();
//				if (remainCount > 0)
//				{
//					_logger.info("remain showcase count: " + remainCount);
//					List<Item> items = RecommendItemsProvider.INSTANCE.getItems(scope, (int)remainCount, topSession);
//					for (Item item : items)
//					{
//						ItemRecommendAddResponse recommendRsp = TaobaoProxy.addRecommendItem(item.getNumIid(), topSession);
//						if (recommendRsp.isSuccess())
//						{
//							Item recommendedItem = recommendRsp.getItem();
//						}
//						else
//						{
//							_logger.log(Level.SEVERE, TaobaoProxy.getError(recommendRsp));
//						}
//					}
//				}
//			}
//			else
//			{
//				_logger.log(Level.SEVERE, TaobaoProxy.getError(remainRsp));
//			}
//		} catch (ApiException e) {
//			_logger.log(Level.SEVERE, "", e);
//		}
	}
	
	
}

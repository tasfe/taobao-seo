package com.taobaoseo.taobao;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.ArticleUserSubscribe;
import com.taobao.api.domain.User;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemRecommendAddRequest;
import com.taobao.api.request.ItemRecommendDeleteRequest;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsListGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.request.ShopRemainshowcaseGetRequest;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.request.VasSubscribeGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemRecommendAddResponse;
import com.taobao.api.response.ItemRecommendDeleteResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsListGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.SellercatsListGetResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import com.taobao.api.response.UserGetResponse;
import com.taobao.api.response.VasSubscribeGetResponse;
import com.taobaoseo.Constants;
import com.taobaoseo.taobao.TaobaoProxy;

public class TaobaoProxy implements Constants
{
	static Logger _log = Logger.getLogger(TaobaoProxy.class.getName());
	
	
	private static String getApiUrl()
	{
		return "http://gw.api.taobao.com/router/rest";
	}
	
	private static String getAppKey()
	{
		return APP_KEY;
	}
	
	private static String getAppSecret()
	{
		return SECRET;
	}
	
	public static TaobaoClient createClient()
	{
		return new DefaultTaobaoClient(getApiUrl(), getAppKey(), getAppSecret());
	}
	
	public static User getUser(String session) throws ApiException
	{
		UserGetRequest req = new UserGetRequest();
		req.setFields("user_id,uid,nick");
		//req.setNick(nick);
		UserGetResponse rsp = createClient().execute(req, session);
		return rsp.getUser();
	}
	
	public static ItemGetResponse getItem(String session, long numIid) throws ApiException
	{
		ItemGetRequest req = new ItemGetRequest();
		req.setFields("num_iid,title,pic_url,price");
		req.setNumIid(numIid);
		return createClient().execute(req, session);
	}
	
	public static ItemGetResponse getItem(long numIid, String fields) throws ApiException
	{
		ItemGetRequest req = new ItemGetRequest();
		req.setFields(fields);
		req.setNumIid(numIid);
		return createClient().execute(req);
	}
	
	public static ItemsOnsaleGetResponse getOnSales(String session, long pageNumber, long pageSize, String sellerCids, String keyWord) throws ApiException
	{
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid,title,pic_url,price,list_time,delist_time");
		if (sellerCids != null)
		{
			req.setSellerCids(sellerCids);
		}
		if (keyWord != null)
		{
			req.setQ(keyWord);
		}
		req.setOrderBy("list_time:desc");
		req.setPageNo(pageNumber);
		req.setPageSize(pageSize);
		return createClient().execute(req, session);
	}
	
	public static ItemsInventoryGetResponse getInventory(String sessionKey, long pageNumber, long pageSize, String banner, String sellerCids, String keyWord) throws ApiException
	{
		ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
		req.setFields("num_iid,title,pic_url,price,num, list_time, delist_time, nick");
		if (sellerCids != null)
		{
			req.setSellerCids(sellerCids);
		}
		if (keyWord != null)
		{
			req.setQ(keyWord);
		}
		if (banner != null)
		{
			req.setBanner(banner);
		}
		req.setOrderBy("list_time:desc");
		req.setPageNo(pageNumber);//default 1
		req.setPageSize(pageSize);// default 200
		 
		ItemsInventoryGetResponse rsp = createClient().execute(req, sessionKey);
		return rsp;
	}
	
	public static ItemsListGetResponse getItems(String numIids, String fields, String session) throws ApiException
	{
		ItemsListGetRequest req = new ItemsListGetRequest();
		req.setFields(fields);
		req.setNumIids(numIids);
		return createClient().execute(req, session);
	}
	
	public static SellercatsListGetResponse getSellerCategories(String nick) throws ApiException
	{
		SellercatsListGetRequest req = new SellercatsListGetRequest();
		req.setNick(nick);

		SellercatsListGetResponse rsp = createClient().execute(req);
		return rsp;
	}
	
	public static String getUserInfo(String nick) throws ApiException
	{
		TaobaoClient client = new DefaultTaobaoClient(getApiUrl(), getAppKey(), getAppSecret());
		UserGetRequest req = new UserGetRequest();
		req.setNick(nick);
		req.setFields("alipay_account");
		 
		UserGetResponse rsp = client.execute(req);
		return rsp.getBody();
	}
	
	public static List<ArticleUserSubscribe> getSubscription(String nick, String articleCode) throws ApiException
	{
		TaobaoClient client = new DefaultTaobaoClient(getApiUrl(), getAppKey(), getAppSecret());
		VasSubscribeGetRequest req = new VasSubscribeGetRequest();
		req.setNick(nick);
		req.setArticleCode(articleCode);
		 
		VasSubscribeGetResponse rsp = client.execute(req);
		return rsp.getArticleUserSubscribes();
	}
	
	public static boolean verifyVersion(String appKey, String leaseId, String timestamp, String versionNo, String sign, String appSecret)
    {
		StringBuilder result = new StringBuilder();
		result.append("appkey").append(appKey)
		.append("leaseId").append(leaseId);
		if (timestamp != null)
		{
			result.append("timestamp").append(timestamp);
		}
		result.append("versionNo").append(versionNo);
		result.insert(0, appSecret);
		result.append(appSecret);
		_log.info("sign: " + sign);
		String s = DigestUtils.md5Hex(result.toString());
		_log.info("s: " + s);
		return sign != null && sign.equals(s.toUpperCase());
    }
	
	public static boolean verifySubscription(String nick, String itemCode)
    {
		try {
			List<ArticleUserSubscribe> subscriptions = getSubscription(nick, ARTICLE_CODE);
			if (subscriptions != null)
			{
				for (ArticleUserSubscribe sub : subscriptions)
				{
					if (itemCode.equals(sub.getItemCode()) && new Date().before(sub.getDeadline()))
					{
						return true;
					}
				}
			}
		} catch (ApiException e) {
			_log.log(Level.SEVERE, "", e);
		}
		return false;
    }
	
	public static ShopRemainshowcaseGetResponse getRemainShowCaseCount(String sessionKey) throws ApiException
	{
		ShopRemainshowcaseGetRequest req = new ShopRemainshowcaseGetRequest();
		ShopRemainshowcaseGetResponse rsp = createClient().execute(req, sessionKey);
		return rsp;
	}
	
	public static ItemRecommendAddResponse addRecommendItem(long numIid, String sessionKey) throws ApiException
	{
		ItemRecommendAddRequest req = new ItemRecommendAddRequest();
		req.setNumIid(numIid);
		ItemRecommendAddResponse rsp = createClient().execute(req, sessionKey);
		return rsp;
	}
	
	public static ItemRecommendDeleteResponse deleteRecommendItem(long numIid, String sessionKey) throws ApiException
	{
		ItemRecommendDeleteRequest req = new ItemRecommendDeleteRequest();
		req.setNumIid(numIid);
		ItemRecommendDeleteResponse rsp = createClient().execute(req, sessionKey);
		return rsp;
	}
	
	public static ItemsOnsaleGetResponse getOldestOnSales(String session, long num, String keyWord) throws ApiException
	{
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid,title,pic_url,price,delist_time");
		if (keyWord != null)
		{
			req.setQ(keyWord);
		}
		req.setHasShowcase(false);
		req.setOrderBy("delist_time:asc");
		req.setPageNo(0L);
		req.setPageSize(num);
		return createClient().execute(req, session);
	}
	
	public static ItemsOnsaleGetResponse getHasShowcaseItems(String session, long num) throws ApiException
	{
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid,title,pic_url,price,delist_time");
		req.setHasShowcase(true);
		req.setOrderBy("delist_time:desc");
		req.setPageNo(0L);
		req.setPageSize(num);
		return createClient().execute(req, session);
	}
	
	public static String getError(TaobaoResponse response)
	{
		return response.getErrorCode() + " - " + response.getMsg() 
			+ " - " + response.getSubCode() + " - " + response.getSubMsg();
	}
}

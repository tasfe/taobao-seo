package com.taobaoseo;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.recommendation.Constants;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobaoseo.db.Dao;
import com.taobaoseo.taobao.TaobaoProxy;

public class SessionFilter implements Filter, Constants{

	static Logger _log = Logger.getLogger(SessionFilter.class.getName());

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	private boolean verify(ServletRequest req) throws IOException, ServletException
	{
		//req.setCharacterEncoding("GBK");
		String queryString = ((HttpServletRequest)req).getQueryString();
		_log.info(queryString);
		HttpSession session = ((HttpServletRequest)req).getSession(false);
		if (session != null && session.getAttribute(USER) != null)
		{
			return true;
		}
		
		String topParams = req.getParameter("top_parameters");
		String topSession = req.getParameter("top_session");
		String topSign = req.getParameter("top_sign");
		String appKey = req.getParameter("top_appkey");
		String version = req.getParameter("versionNo");
		String leaseId = req.getParameter("leaseId");
		String timestamp = req.getParameter("timestamp");
		String sign = req.getParameter("sign");
		String itemCode = req.getParameter("itemCode");
		
		if (topParams == null || topSession == null || topSign==null || appKey == null)
		{
			return false;
		}
		
		boolean verifiedTopParameters = TaobaoUtils.verifyTopResponse(topParams, topSession, topSign, appKey, TaobaoProxy.SECRET);
		_log.info("top parameters verified: " + verifiedTopParameters);
		if (verifiedTopParameters)
		{
			String browser = ((HttpServletRequest)req).getHeader("user-agent");
			_log.info("browser: " + browser);
			Map<String, String> topMap = TaobaoUtils.decodeTopParams(URLEncoder.encode(topParams, "GBK"));
			_log.warning("parsed top params: " + topMap);
			String userId = topMap.get("visitor_id");
			String nick = topMap.get("visitor_nick");
			_log.info("uid: " + userId);
			_log.info("session: " + topSession);
			_log.info("itemCode: " + itemCode);
			boolean v = false;
			if (itemCode != null)
			{
				v = TaobaoProxy.verifySubscription(nick, itemCode);
				_log.info("subscription verified: " + v);
				if (version == null)
				{
					if (ITEM_CODE_3.equals(itemCode))
					{
						version = "3";
					}
					else if (ITEM_CODE_2.equals(itemCode))
					{
						version = "2";
					}
					else
					{
						version = "1";
					}
				}
			}
			else
			{
				v = TaobaoProxy.verifyVersion(appKey, leaseId, timestamp, version, sign, SECRET);
				_log.info("version verified: " + v);
			}
			v = true;
			if (v)
			{
				session = ((HttpServletRequest)req).getSession(true);
				session.setAttribute(TOP_SESSION, topSession);
				session.setAttribute(USER, nick);
				session.setAttribute(USER_ID, userId);
				session.setAttribute(VERSION, version);
				session.setAttribute("query", queryString);
				session.setAttribute("browser", browser);
				try {
					Dao.INSTANCE.updateUser(Long.parseLong(userId), nick, topSession);
				} catch (Exception e) {
					_log.log(Level.SEVERE, "", e);
				}
				return true;
			}
		}
		return false;
	}
	
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		try 
		{
			if (verify(req))
		    {
		    	chain.doFilter(req, rsp);
		    }
			else
			{
				rsp.setContentType("text/html;charset=UTF-8");
				rsp.getWriter().println("未授权。");
			}
		} 
		catch (IOException e) {
			_log.log(Level.SEVERE, "", e);
			throw e;
		}
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}

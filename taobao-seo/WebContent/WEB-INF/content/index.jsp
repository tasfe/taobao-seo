<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>排名优化专家</title>
	<link rel="stylesheet" href="css/redmond/jquery-ui-1.8.16.custom.css" type="text/css" />
	<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css" media="screen, projection"/> 
    <link rel="stylesheet" href="css/blueprint/print.css" type="text/css" media="print"/>
    <!--[if lt IE 8]><link rel="stylesheet" href="css/blueprint/ie.css" type="text/css" media="screen, projection"><![endif]-->
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
</head>

<body>
<div class="container">
	<div id="header" class="span-24 last append-bottom">
		<div class="left">
		<img src="images/logo.png"></img>
		</div>
		<div class="right" style="margin-top: 5px;">
			您好，<span id="nick"><s:property value="#session.USER"/></span><span class="quiet separator">|</span>
			<span><a target='_blank' href='http://amos.im.alisoft.com/msg.aw?v=2&uid=%E8%B5%A4%E7%8F%A0%E5%AD%90&site=cntaobao&s=1&charset=utf-8'><img border='0' src='http://amos.im.alisoft.com/online.aw?v=2&uid=%E8%B5%A4%E7%8F%A0%E5%AD%90&site=cntaobao&s=1&charset=utf-8' alt='联系作者' /></a></span>
		</div>
	</div>
	<div id="tabs" class="span-24 last">
		<ul>
			<s:url action="listing/items" var="listingLink"></s:url>
			<li><a href="${listingLink}">上架</a></li>
			<s:url action="recommendation" var="recommendLink"></s:url>
			<li><a href="${recommendLink}">推荐</a></li>
			<li><a href="faq.html">常见问题</a></li>
			<s:url action="service_info" var="serviceInfoLink"></s:url>
			<li><a href='${serviceInfoLink}'>服务信息</a></li>
			<s:if test='#session.admin'>
				<s:url action="op-by-categories" var="opByCategoriesLink"></s:url>
				<li><a href="${opByCategoriesLink}">快速操作</a></li>
				<s:url action="admin" var="adminLink"></s:url>
				<li><a href="${adminLink}">Admin</a></li>
			</s:if>
		</ul>
	</div>
	<div id="footer" class="span-24 last prepend-top quiet">
		<a href="http://seller.taobao.com/fuwu/shopshow/shop_index.htm?page_id=95889&isv_id=62112477&page_rank=2&tab_type=1" target="_blank" class="quiet">道和科技</a>
	</div>
</div>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script> 
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>
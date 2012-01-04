<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>排名优化</title>
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
	<div class='content span-24 last' >
		<div class='panel'>
			<div class='panel-title'>运行状态</div>
			<div class='panel-content'>
			<div id='msg-stopped'>自动推荐未启动或者已停止。</div>
			<div id='msg-running' class='hide'>自动推荐正在运行中。<img src='images/ani-gear.gif'/></div>
			<button id='start'>启动</button>
			</div>
		</div>
		<div class='panel prepend-top'>
			<div class='panel-title'>选项</div>
			<div class='panel-content'>
				<form>
				<table>
					<tr>
						<td>
						<label for='mode'>推荐模式</label>
						</td>
						<td>
						<div id='mode'>
							<input type='radio' name='mode' value='1' checked='checked'>发现橱窗有空缺，则将即将下架的宝贝加入橱窗推荐</input><br/>
							<input type='radio' name='mode' value='2'>发现即将下架的宝贝（剩余时间小于30分钟），不管橱窗是否空缺，强制加入橱窗推荐（橱窗无空缺时，撤销离下架时间最久的宝贝推荐）</input>
						</div>
						</td>
					</tr>
					<tr>
						<td><label for='scope'>待推荐宝贝范围</label></td>
						<td>
							<div id='scope'>
							<input type='radio' name='scope' value='1' checked='checked'>全部宝贝</input><br/>
							<input type='radio' name='scope' value='2'>指定关键字</input><br/>
							<input type='radio' name='scope' value='3'>手动选择</input>
							</div>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
	<div id="footer" class="span-24 last prepend-top quiet">
		<a href="http://seller.taobao.com/fuwu/shopshow/shop_index.htm?page_id=95889&isv_id=62112477&page_rank=2&tab_type=1" target="_blank" class="quiet">道和科技</a>
	</div>
</div>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script> 
<script type="text/javascript" src="js/my.js"></script>
</body>
</html>
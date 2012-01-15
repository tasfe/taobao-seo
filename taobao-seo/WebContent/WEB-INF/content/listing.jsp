<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id='listing'>
	<div class='msg-stopped'>自动推荐未启动或者已停止。</div>
			<div class='msg-running hide'>
				自动推荐正在运行中。<img src='images/ani-gear.gif' />
			</div>
			<button class='start'>启动</button>
</div>
<div>
	<div id="chart" style="width:600px;height:300px"></div>
</div>
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="js/excanvas.min.js"></script><![endif]-->
<script type="text/javascript" src="js/jquery.flot.min.js"></script>
<script type="text/javascript" src="js/listing.js"></script>
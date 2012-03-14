<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>

<table class="listing-logs" pages='<s:property value="pagingItems.totalPages" />' 
	pageIndex='<s:property value="pagingItems.currentPage" />'>
	<thead>
		<tr>
			<th>执行时间</th>
			<th>主图</th>
			<th>基本信息</th>
			<th>上架时间变化</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="items">
			<tr class="drow" num_iid='<s:property value="item.numIid"/>'>
				<td>
					<div><s:date name="plannedListTime" format="M月d日 HH:mm E"/></div>
				</td>
				<td class='item-main-pic'>
					<a href='http://item.taobao.com/item.htm?id=<s:property value="item.numIid"/>' target="_blank">
						<img class="pic" src='<s:property value="item.picUrl"/>_80x80.jpg'/>
					</a>
				</td>
				<td class="item-details">
					<div><s:property value="item.title"/></div>
				</td>
				<td>
					<div title='原上架时间'><s:date name="item.listTime" format="E HH:mm"/></div>
					<div class='adjust-time' title='调整后上架时间' style='color:blue;'><s:date name="plannedListTime" format="E HH:mm"/></div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>

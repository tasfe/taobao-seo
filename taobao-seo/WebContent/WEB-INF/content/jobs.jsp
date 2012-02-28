<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<table id="items-table" pages='<s:property value="pagingItems.totalPages" />' pageIndex='<s:property value="pagingItems.currentPage" />'>
	<thead>
		<tr>
			<th><input type="checkbox" class="selector"></input>
			</th>
			<th>主图</th>
			<th>基本信息</th>
			<th>上架时间</th>
			<th>执行时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="plannedItems">
			<tr class="drow" num_iid='<s:property value="item.numIid"/>'>
				<td style='width:20px;'><input type="checkbox" class="selector"></input>
				</td>
				<td class='item-main-pic'>
					<a href='http://item.taobao.com/item.htm?id=<s:property value="item.numIid"/>' target="_blank">
						<img class="pic" src='<s:property value="item.picUrl"/>_80x80.jpg'/>
					</a>
				</td>
				<td class="item-details">
					<div><s:property value="item.title"/></div>
					<div>价格：<s:property value="item.price"/>元</div>
				</td>
				<td class="list-time">
					<div><s:date name="item.listTime" format="E HH:mm"/></div>
					<div><s:date name="plannedListTime" format="E HH:mm"/></div>
				</td>
				<td class="list-time">
					<div><s:date name="plannedListTime" format="M月d日 HH:mm"/></div>
				</td>
				<td class="op">
					<div><a class="cancel-job-link" href="#">取消</a></div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<script type="text/javascript" src="js/jobs.js"></script>
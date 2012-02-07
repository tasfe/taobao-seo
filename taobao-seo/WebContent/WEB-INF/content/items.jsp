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
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="pagingItems.items">
			<tr class="drow" num_iid='<s:property value="numIid"/>'>
				<td style='width:20px;'><input type="checkbox" class="selector"></input>
				</td>
				<td class='item-main-pic'>
					<a href='http://item.taobao.com/item.htm?id=<s:property value="numIid"/>' target="_blank">
						<img class="pic" src='<s:property value="picUrl"/>_80x80.jpg' src_310='<s:property value="picUrl"/>_310x310.jpg' />
					</a>
				</td>
				<td class="item-details">
					<div><s:property value="title"/></div>
					<div>价格：<s:property value="price"/>元</div>
				</td>
				<td class="list-time">
					<div><a href='#'><s:date name="listTime" format="yyyy-MM-dd HH:mm E"/></a></div>
					<div><a href='#'><s:date name="delistTime" format="yyyy-MM-dd HH:mm E"/></a></div>
				</td>
				<td class="op">
					<div><a class="adjust-link" href="#">调整</a></div>
					<div><a class="cancel-adjust-link" href="#">取消调整</a></div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div id="adjust-dialog" title="调整上架时间">
</div>
<script type="text/javascript" src="js/items.js"></script>
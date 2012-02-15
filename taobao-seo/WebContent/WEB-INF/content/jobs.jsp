<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<table pages='<s:property value="pagingItems.totalPages" />' pageIndex='<s:property value="pagingItems.currentPage" />'>
	<thead>
		<tr>
			<th><input type="checkbox" class="selector"></input>
			</th>
			<th>name</th>
			<th>group</th>
			<th>fire time</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="jobs">
			<tr class="drow">
				<td style='width:20px;'><input type="checkbox" class="selector"></input>
				</td>
				<td>
					<s:property value="key.name"/>
				</td>
				<td>
					<s:property value="key.group"/>
				</td>
				<td>
					<s:date name="fireTime" format="yyyy-MM-dd HH:mm E"/>
				</td>
				<td>
					<div><a class="adjust-link" href="#">调整</a></div>
					<div><a class="cancel-adjust-link" href="#">取消调整</a></div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
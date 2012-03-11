<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<div>
	<div class='left strong'><s:if test='%{expected}'>预计</s:if><s:property value="dayOfWeek"/> <s:property value="listHour.hour"/>:00 - <s:property value="listHour.hour"/>:59 上架的宝贝：</div>
	<div class='right'>
		<button class='well-distribute'>均匀化当前时段</button>
		<button class='batch-change'>批量修改</button>
		<button class='batch-cancel'>批量取消</button>
	</div>
	<div class='clear'></div>
</div>
<table class="listing-items" pages='<s:property value="pagingItems.totalPages" />' 
	pageIndex='<s:property value="pagingItems.currentPage" />'
	day-of-week='<s:property value="listHour.dayOfWeek"/>'
	hour='<s:property value="listHour.hour"/>'
	expected='<s:property value="expected"/>'>
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
				<td class="list-time" day-of-week='<s:property value="dayOfWeek"/>'>
					<div title='原上架时间'><s:date name="item.listTime" format="E HH:mm"/></div>
					<div class='adjust-time' title='调整后上架时间' style='color:blue;'><s:date name="plannedListTime" format="E HH:mm"/></div>
					<div class='editor hide'>
						<select>
							<option value='2'>星期一</option>
							<option value='3'>星期二</option>
							<option value='4'>星期三</option>
							<option value='5'>星期四</option>
							<option value='6'>星期五</option>
							<option value='7'>星期六</option>
							<option value='1'>星期日</option>
						</select>
						<input name='time' value='<s:date name="item.listTime" format="HH:mm"/>' style='width:45px;'></input>
						<button class='ok'>确定</button><button class='cancel'>取消</button>
					</div>
				</td>
				<td class="op">
					<s:if test='%{plannedListTime == null}'>
						<div><a class="adjust-link" href="#">调整</a></div>
					</s:if>
					<s:else>
						<div><a class="change-job" href="#">修改</a></div>
						<div><a class="cancel-job" href="#">取消</a></div>
					</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div id="adjust-dialog" title="批量调整上架时间">
	<s:include value="adjust-form.jsp"></s:include>
</div>
<script type="text/javascript" src="js/listing-items.js"></script>
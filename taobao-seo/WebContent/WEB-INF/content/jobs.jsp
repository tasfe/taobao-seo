<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<div>
	<div class='left strong'>所有等待执行的任务：</div>
	<div class='right'>
		<button class='batch-change'>批量修改</button>
		<button class='batch-cancel'>批量取消</button>
	</div>
	<div class='clear'></div>
</div>
<table id="jobs-table" pages='<s:property value="pagingItems.totalPages" />' pageIndex='<s:property value="pagingItems.currentPage" />'>
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
				<td class="list-time" day-of-week='<s:property value="plannedDayOfWeek"/>'>
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
						<input value='<s:date name="plannedListTime" format="HH:mm"/>' style='width:45px;'></input>
						<button class='ok'>确定</button><button class='cancel'>取消</button>
					</div>
				</td>
				<td class="plan-time">
					<div><s:date name="plannedListTime" format="M月d日 HH:mm"/></div>
				</td>
				<td class="op">
					<div><a class="change-job-link" href="#">修改</a></div>
					<div><a class="cancel-job-link" href="#">取消</a></div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<script type="text/javascript" src="js/jobs.js"></script>
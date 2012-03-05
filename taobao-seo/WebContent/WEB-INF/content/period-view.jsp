<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class='period-view'>
	<div>
		<form id='period-form'>
		<span class="strong">
			有效周期为
			<select name="period">
				<option value='7' <s:if test='%{period == 7}'>selected="selected"</s:if>>7</option>
				<option value='14' <s:if test='%{period == 14}'>selected="selected"</s:if>>14</option>
			</select>
			天的宝贝上架时间分布图：
		</span>
		<s:if test='%{period == 14}'>
			<a id='all-to-period7' class='right'>全部改为7天有效周期</a>
		</s:if>
		<s:else>
			<a id='well-distribute-all' class='right'>均匀化所有时段</a>
		</s:else>
		</form>
	</div>
	<table class='grid'>
		<thead>
			<tr>
				<th>时间</th>
				<c:forEach var="date" items="${dates}">
					<th title='<fmt:formatDate value="${date}" pattern="M月dd日"/>'>
					<fmt:formatDate value='${date}' pattern="E"/></th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<s:iterator value='itemsMatrix' status='st'>
				<tr <s:if test='itemRow[#st.index]'>class='has-item'</s:if>>
					<td><s:property value="#st.index" />:00</td>
					<s:iterator value='itemsMatrix[#st.index]' status='weekSt'>
						<td title='<s:date name="time" format="M月dd日 HH:mm E"/>'
							datetime='<s:date name="time"/>' 
							time='<s:property value="#st.index" />:00'
							day-of-week='<s:property value="#weekSt.index + 2" />' 
							style='position:relative;'>
							<s:if test='%{period == 7}'>
								<div class='tools hide' style='position:absolute;top:0;right:0;'>
									<span title='均匀化这个时段' class='distribute-tool' style='cursor:pointer;'>=</span>
									<span title='添加商品到这个时段' class='add-tool' style='cursor:pointer;'>+</span>
								</div>
							</s:if>
							<a href='listing/hour-items?period=<s:property value='period'/>&date=<s:date name="time"/>&hour=<s:date name="time" format="H"/>'><s:property value='itemsCount'/></a>
						</td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<div id='dialog'></div>
<script type="text/javascript" src="js/period-view.js"></script>
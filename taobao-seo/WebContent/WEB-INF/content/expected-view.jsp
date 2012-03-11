<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<div class='strong'>预期所有调整任务成功执行后的分布图：</div>
<div class='period-view'>
	<table class='grid' expected='true'>
		<thead>
			<tr>
				<th>时间</th>
				<fmt:setLocale value="zh_CN"/>
				<c:forEach var="date" items="${dates}" varStatus='sst'>
					<th <c:if test='${sst.index == today}'>style='color:#06C;'</c:if>>
					<fmt:formatDate value='${date}' pattern="E"/></th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<s:iterator value='itemsMatrix' status='st'>
				<tr <s:if test='itemRow[#st.index]'>class='has-item'</s:if>>
					<td <s:if test='itemRow[#st.index]'>style='font-weight:bold;'</s:if>><s:property value="#st.index" />:00</td>
					<s:iterator value='itemsMatrix[#st.index]' status='weekSt'>
						<td day-of-week='<s:property value="listHour.dayOfWeek" />'
							hour='<s:property value="listHour.hour" />'>
							<div style='width:100%;height:20px;position:relative;'>
							<s:if test='%{period == 7}'>
								<div class='tools hide' style='position:absolute;top:0;right:0;'>
									<span title='均匀化这个时段' class='distribute-tool' style='cursor:pointer;'>=</span>
									<span title='添加商品到这个时段' class='add-tool' style='cursor:pointer;'>+</span>
								</div>
							</s:if>
							<a href='listing/hour-items?expected=true&period=<s:property value='period'/>&listHour.dayOfWeek=<s:property value="listHour.dayOfWeek"/>&listHour.hour=<s:property value="listHour.hour"/>'><s:property value='itemsCount'/></a>
							</div>
						</td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<div id='dialog'></div>
<script type="text/javascript" src="js/period-view.js"></script>
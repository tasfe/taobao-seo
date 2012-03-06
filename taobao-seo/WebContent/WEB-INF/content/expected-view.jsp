<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class='period-view'>
	<table class='grid'>
		<thead>
			<tr>
				<th>时间</th>
				<c:forEach var="date" items="${dates}" varStatus='sst'>
					<th <c:if test='${sst.index == today}'>style='color:#06C;'</c:if>>
					<fmt:formatDate value='${date}' pattern="E"/></th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<s:iterator value='itemsMatrix' status='st'>
				<tr <s:if test='itemRow[#st.index]'>class='has-item'</s:if>>
					<td><s:property value="#st.index" />:00</td>
					<s:iterator value='itemsMatrix[#st.index]' status='weekSt'>
						<td day-of-week='<s:property value="listHour.dayOfWeek" />'
							hour='<s:property value="listHour.hour" />' 
							style='position:relative;'>
							<s:if test='%{period == 7}'>
								<div class='tools hide' style='position:absolute;top:0;right:0;'>
									<span title='均匀化这个时段' class='distribute-tool' style='cursor:pointer;'>=</span>
									<span title='添加商品到这个时段' class='add-tool' style='cursor:pointer;'>+</span>
								</div>
							</s:if>
							<a href='listing/hour-items?expected=true&period=<s:property value='period'/>&listHour.dayOfWeek=<s:property value="listHour.dayOfWeek"/>&listHour.hour=<s:property value="listHour.hour"/>'><s:property value='itemsCount'/></a>
						</td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<div id='dialog'></div>
<script type="text/javascript" src="js/period-view.js"></script>
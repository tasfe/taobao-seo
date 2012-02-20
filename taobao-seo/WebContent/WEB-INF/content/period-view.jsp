<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
	<table class='grid'>
		<thead>
			<tr>
				<th>时间</th>
				<c:forEach var="date" items="${dates}">
					<th><fmt:formatDate value='${date}' pattern="M月dd日"/><br/>
					<fmt:formatDate value='${date}' pattern="E"/></th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<s:iterator value='itemsMatrix' status='st'>
				<tr>
					<td><s:property value="#st.index" />:00</td>
					<s:iterator value='itemsMatrix[#st.index]'>
						<td title='<s:date name="time" format="M月dd日 HH:mm E"/>'>
							<a href='listing/hour-items?date=<s:date name="time"/>&hour=<s:date name="time" format="H"/>'><s:property value='itemsCount'/></a>
						</td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<script type="text/javascript" src="js/period-view.js"></script>
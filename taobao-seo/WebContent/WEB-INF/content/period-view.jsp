<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
	<table class='grid'>
		<thead>
			<tr>
				<th>时间</th>
				<s:iterator value='dates'>
					<th><s:property/><s:date name="" format="MM-dd E"/></th>
				</s:iterator>
			</tr>
		</thead>
		<tbody>
			<s:iterator value='itemsMatrix' status='st'>
				<tr>
					<td><s:property value="#st.index" />:00</td>
					<s:iterator value='itemsMatrix[#st.index]'>
						<td title='<s:date name="time" format="MM-dd HH:mm E"/>'><a href=''><s:property value='itemsCount'/></a></td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
</div>
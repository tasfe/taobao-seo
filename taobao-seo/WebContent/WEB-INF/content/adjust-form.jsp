<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id='adjust-form'>
	<div>
		<select>
			<option value='2'>星期一</option>
			<option value='3'>星期二</option>
			<option value='4'>星期三</option>
			<option value='5'>星期四</option>
			<option value='6'>星期五</option>
			<option value='7'>星期六</option>
			<option value='1'>星期日</option>
		</select> 
		<input name='time' value='20:00'
			style='width: 45px;'></input>
	</div>
</form>
<script type='text/javascript'>
$('#adjust-form input[name="time"]').timepicker({});
</script>
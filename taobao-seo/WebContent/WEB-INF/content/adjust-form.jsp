<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form>
	<div>
		<label>宝贝:</label><br/>
		<div><s:property value="item.title"/></div>
	</div>
	<div>
		<label>数量:</label><br/>
		<div><input class='text' type="text" name="num" value='<s:property value="item.num"/>'/></div>
	</div>
	<div>
		<label>当前上架时间:</label><br/>
		<div><s:date name="item.listTime" format="yyyy-MM-dd HH:mm E"/></div>
	</div>
	<div>
		<label>调整为:</label><br/>
		<input class="text" type="text" name="list_time" value='<s:date name="item.listTime" format="yyyy-MM-dd HH:mm E"/>'/>
	</div>
</form>
<script type='text/javascript'>
$("#adjust-dialog input[name='list_time']").datetimepicker({
	stepMinute: 5
});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form>
	<div>
		<img class="left"  style='margin-right:10px;' src='<s:property value="item.picUrl"/>_80x80.jpg'/>
		<div class="left">
			<div>标题: <s:property value="item.title"/></div>
			<div>数量: <s:property value="item.num"/></div>
			<div>上架时间: <s:date name="item.listTime" format="yyyy-MM-dd HH:mm E"/></div>
		</div>
		<div class='clear' style='height:10px;'></div>
	</div>
	<div>
		<label>调整数量:</label><br/>
		<div><input class='text' type="text" name="num" value='<s:property value="item.num"/>'/></div>
	</div>
	<div>
		<label>调整时间:</label><br/>
		<input class="text" type="text" name="list_time" value='<s:date name="item.listTime" format="yyyy-MM-dd HH:mm E"/>'/>
	</div>
</form>
<script type='text/javascript'>
$("#adjust-dialog input[name='list_time']").datetimepicker({
	stepMinute: 5
});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<table class='items-selector'>
	<tr>
		<td>
			<form id="search-form">
			<select name="filter.sellerCids">
				<option value=''>所有分类</option>
				<s:iterator value="categories">
					<option value='<s:property value="cid"/>' parent='<s:property value="parentCid"/>'>
						<s:if test="%{parentCid != 0}">└</s:if>
						<s:property value="name" />
					</option>
				</s:iterator>
			</select>
			<input id="keyword" type="text" name="filter.keyWord" value="关键字" style="margin:0.5em 0;"></input>
			<button id='search' style="margin:0.5em 0;">查找</button>
			</form>
		</td>
	</tr>
	<tr>
		<td style='vertical-align:top;'>
			<div style='width:400px;backgournd:#C3D9FF;font-weight:bold;'>待选宝贝</div>
			<div id="items" style='width:400px;height:400px;overflow:auto;'>
				<s:action name="items" executeResult='true'></s:action>
			</div>
			<div id="pager" class="pager span-8 separator"></div>
		</td>
		<td>
			<div><button id='select-btn'>&gt;</button></div>
			<div><button id='unselect-btn'>&lt;</button></div>
		</td>
		<td style='vertical-align:top;'>
			<div style='width:400px;backgournd:#C3D9FF;font-weight:bold;'>已选宝贝</div>
			<div style='width:400px;height:400px;overflow:auto;'>
				<table id='selected-items'>
					<tbody>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript" src='js/items-selector.js'></script>
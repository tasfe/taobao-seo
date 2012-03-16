<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div>
	<div id='recommendation-status' class='panel'>
		<div class='panel-title'>运行状态</div>
		<div class='panel-content'>
			<div class='msg-stopped'>自动推荐未启动或者已停止。</div>
			<div class='msg-running hide'>
				自动推荐正在运行中。<img src='images/ani-gear.gif' />
			</div>
			<button class='start'>启动</button>
		</div>
	</div>
	<div id='recommendation-options' class='panel prepend-top'>
		<div class='panel-title'>选项</div>
		<div class='panel-content'>
			<form>
				<table>
					<tr>
						<td><label for='mode'>推荐模式</label></td>
						<td>
							<div id='mode'>
								<input type='radio' name='mode' value='1' checked='checked'>发现橱窗有空缺，则将即将下架的宝贝加入橱窗推荐</input><br />
								<input type='radio' name='mode' value='2'>发现即将下架的宝贝（剩余时间小于30分钟），不管橱窗是否空缺，强制加入橱窗推荐（橱窗无空缺时，撤销离下架时间最久的宝贝推荐）</input>
							</div></td>
					</tr>
					<tr>
						<td><label for='scope'>待推荐宝贝范围</label>
						</td>
						<td>
							<div class='scope'>
								<div><input type='radio' name='scope' value='1' checked='checked'>全部宝贝</input></div>
								<div><input type='radio' name='scope' value='2'>含有关键字: </input><input name="keyword" type='text'></input></div> 
								<div><input type='radio' name='scope' value='3'>手动选择</input></div>
								<div class='item-selector hide'><s:include value="items-selector.jsp"/></div>
							</div></td>
					</tr>
				</table>
			</form>
			<button>保存</button>
			<div class='clear'></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/recommendation.js"></script>
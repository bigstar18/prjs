<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="subApp.comp_id.appid" value="${subApp.comp_id.appid}"/>
	<input type="hidden" name="subApp.appname" value="${subApp.appname}"/>
</form>

<form method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>所属应用：</label>
					<s:select name="subApp.comp_id.appid" list="apps" listKey="appid" listValue="appname" value="subApp.comp_id.appid" headerKey="" headerValue="全部" cssClass="textInput"/>
				</li>
				<li>
					<label>子应用名称：</label>
					<input type="text" name="subApp.appname" value="${subApp.appname}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="navTab" rel="addSubApp" href="CreateInfoSubappinfo?navTabId=${ navTabId }" title="添加子应用"><span>添加</span></a></li>
			<li><a class="edit" target="navTab" rel="modifySubApp" href="ModifyInfoSubappinfo?subApp.comp_id.subappid={subappid}&navTabId=${ navTabId }" title="修改子应用"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="subappId" href="RemoveSubappinfo" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="subappId" class="checkboxCtrl"></th>
				<th>子应用ID</th>
				<th>子应用名称</th>
				<th>所属应用</th>
				<th>子应用描述</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="subApps">
				<tr target="subappid" rel="${comp_id.subappid}">
					<td>
						<input type="checkbox" value="${comp_id.subappid}" name="subappId"/>
					</td>
					<td>${comp_id.subappid}</td>
					<td>${appname}</td>
					<td>${app.appname}</td>
					<td>${subappdesc}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	
</div>

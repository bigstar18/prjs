<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="app.appid" value="${app.appid}"/>
	<input type="hidden" name="app.appname" value="${app.appname}"/>
</form>

<form method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>应用号：</label>
					<input type="text" name="app.appid" value="${app.appid}" prompt="请输入应用号"/>
				</li>
				<li>
					<label>应用名：</label>
					<input type="text" name="app.appname" value="${app.appname}" prompt="请输入应用名"/>
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
			<li><a class="add" target="navTab" rel="addApp" href="linkPage?path=usermanager/CreateApplication.jsp&navTabId=${ navTabId }" title="添加应用" params="table"><span>添加</span></a></li>
			<li><a class="edit" target="navTab" rel="modifyApp" href="ModifyInfoApplication?app.appid={appid}&navTabId=${ navTabId }" title="修改应用"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="apps.appid" href="RemoveApplication" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="apps.appid" class="checkboxCtrl"></th>
				<th>应用号</th>
				<th>应用名称</th>
				<th>应用地址</th>
				<th>版本号</th>
				<th>启动时间</th>
				<th>停止时间</th>
				<th>投产日期</th>
				<th>最后升级日期</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="apps">
				<tr target="appid" rel="${appid}">
					<td>
						<input type="checkbox" name="apps.appid" value="${appid}"/>
					</td>
					<td>${appid}</td>
					<td>${appname}</td>
					<td>${appadress}</td>
					<td>${version}</td>
					<td>${starttime}</td>
					<td>${stoptime}</td>
					<td>${depdate}</td>
					<td>${finalupdate}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	
</div>



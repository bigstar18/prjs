<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="condition.roleId" value="${condition.roleId}"/>
	<input type="hidden" name="condition.userId" value="${condition.userId}"/>
	
	<input type="hidden" name="rollPage"/>
</form>

<form id="query_listfunc" method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>级别</label>
					<s:select 
						name="condition.roleId" 
						list="roleList" 
						listKey="roleId"
						listValue="roleName"
						headerKey=""
						headerValue="全部级别"
						value="condition.roleId"
						cssClass="textInput"></s:select>
				</li>
				<li>
					<label>用户</label>
					<input type="text" name="condition.userId" value="${condition.userId}"/>
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
			<li><a class="add" target="dialog" rel="addUser" href="CreateInfoUser?navTabId=${ navTabId }" title="添加用户" height="200"><span>添加</span></a></li>
			<li><a class="edit" target="dialog" rel="modifyUser" href="ModifyInfoUser?userid={userid}&navTabId=${ navTabId }" title="修改用户" height="310" params="table"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="uList.userId" href="RemoveUser" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="135" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="uList.userId" class="checkboxCtrl"></th>
				<th>ID</th>
					<th>姓名</th>
					<th>级别</th>
					<th>最近一次登录IP</th>
					<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="uList">
				<tr target="userid" rel="${userId}">
					<td>
						<input type="checkbox" value="${userId}" name="uList.userId"/>
					</td>
					<td>${userId}</td>
					<td>${tellerName}</td>
					<td>${role.roleName}</td>
					<td>${remark}</td>
					<td>
						<s:if test="tellerState==0">正常</s:if>
						<s:if test="tellerState==1">冻结</s:if>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
</div>



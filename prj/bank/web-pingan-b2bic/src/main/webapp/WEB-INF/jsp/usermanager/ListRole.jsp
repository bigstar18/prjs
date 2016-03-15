<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="navTab" rel="addRole" href="CreateInfoRole?navTabId=${ navTabId }" title="添加级别"><span>添加</span></a></li>
			<li><a class="edit" target="navTab" rel="modifyRole" href="ModifyInfoRole?role.roleId={roleId}&navTabId=${ navTabId }" title="修改级别"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="roleList.roleId" href="RemoveRole" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="75" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="roleList.roleId" class="checkboxCtrl"></th>
				<th>名称</th>
				<th>上级级别</th>
				<th>是否可以有下属级别</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="roleList">
				<tr target="roleId" rel="${roleId}">
					<td>
						<input type="checkbox" value="${roleId}" name="roleList.roleId"/>
					</td>
					<td>${roleName}</td>
					<td><s:property value="superiorRoleName(superiorRoleId)"/></td>
					<td>${finalFlag == '0' ? '可以' : '不可以'}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	
</div>
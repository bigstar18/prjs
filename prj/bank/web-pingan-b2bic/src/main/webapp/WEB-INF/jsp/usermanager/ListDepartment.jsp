<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="dept.id" value="${dept.id}"/>
	<input type="hidden" name="dept.name" value="${dept.name}"/>
</form>

<form method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>机构号：</label>
					<input type="text" name="dept.id" value="${dept.id}"/>
				</li>
				<li>
					<label>机构名称：</label>
					<input type="text" name="dept.name" value="${dept.name}"/>
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
			<li><a class="add" target="navTab" rel="addApp" href="CreateInfoDepartment?navTabId=${ navTabId }" title="添加机构"><span>添加</span></a></li>
			<li><a class="edit" target="navTab" rel="modifyApp" href="ModifyInfoDepartment?dept.id={deptid}&navTabId=${ navTabId }" title="修改机构"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="deptList.id" href="RemoveDepartment" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="deptList.id" class="checkboxCtrl"></th>
				<th>机构号</th>
				<th>机构类型</th>
				<th>机构名称</th>
				<th>上级机构</th>
				<th>新系统机构号</th>
				<th>是否能有下级机构</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="deptList">
				<tr target="deptid" rel="${id}">
					<td>
						<input type="checkbox" name="deptList.id" value="${id}"/>
					</td>
					<td>${id}</td>
					<td>${depnotype}</td>
					<td>${name}</td>
					<td><s:property value="superiorDept == null || superiorDept.name == null || superiorDept.name.length() == 0 ? superiorDeptName(superiorDepartmentId) : superiorDept.name"/></td>
					<td>${newSystemNo}</td>
					<td>${finalFlag == '0' ? '可以' : '不可以'}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	
</div>



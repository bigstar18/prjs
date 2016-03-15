<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="condition.itemename" value="${condition.itemename}"/>
	<input type="hidden" name="condition.itemcname" value="${condition.itemcname}"/>
</form>

<form method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>类别代码：</label>
					<input type="text" value="${condition.item}" readonly="readonly"/>
				</li>
				<li>
					<label>代码：</label>
					<input type="text" name="condition.code" value="${condition.code}"/>
				</li>
				<li>
					<label>中文名称：</label>
					<input type="text" name="condition.codename" value="${condition.codename}"/>
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
			<li><a class="add" target="dialog" rel="addSubdict" href="CreateSubdict?navTabId=${ navTabId }&item.comp_id.item=${ condition.item }" title="添加数据字典从表" width="500" height="200" mask="true"><span>添加</span></a></li>
			<li><a class="edit" target="dialog" rel="modifySubdict" href="ModifySubdict?item.item={item}&navTabId=${ navTabId }" title="修改数据字典从表" width="500" height="200" mask="true" params="table"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="removeList" href="RemoveSubdict" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="items.item" class="checkboxCtrl"></th>
				<th>类别代码</th>
				<th>代码</th>
				<th>中文名称</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="items">
				<tr target="item" rel="${item}">
					<td>
						<input type="checkbox" name="removeList" value='{ item : "${ comp_id.item }"_ code : "${ comp_id.code }" }'/>
					</td>
					<td>${comp_id.item}</td>
					<td>${comp_id.code}</td>
					<td>${codename}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	
</div>



<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="condition.item" value="${condition.item}"/>
	<input type="hidden" name="condition.itemename" value="${condition.itemename}"/>
	<input type="hidden" name="condition.itemcname" value="${condition.itemcname}"/>
</form>

<form method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>类别代码：</label>
					<input type="text" name="condition.item" value="${condition.item}"/>
				</li>
				<li>
					<label>英文名称：</label>
					<input type="text" name="condition.itemename" value="${condition.itemename}"/>
				</li>
				<li>
					<label>中文名称：</label>
					<input type="text" name="condition.itemcname" value="${condition.itemcname}"/>
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
			<li><a class="add" target="dialog" rel="addMaindict" href="linkPage?path=afa/CreateMaindict.jsp&navTabId=${ navTabId }" title="添加数据字典" width="500" height="190" mask="true"><span>添加</span></a></li>
			<li><a class="edit" target="dialog" rel="modifyMaindict" href="ModifyMaindict?item.item={item}&navTabId=${ navTabId }" title="修改数据字典" width="500" height="190" mask="true"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="items.item" href="RemoveMaindict" class="delete"><span>删除</span></a></li>
			<li><a class="icon" target="navTab" rel="listSubdict" href="ListSubdict?condition.item={item}&navTabId=listSubdict" title="子表详情"><span>子表详情</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="items.item" class="checkboxCtrl"></th>
				<th>类别代码</th>
				<th>英文名称</th>
				<th>中文名称</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="items">
				<tr target="item" rel="${item}">
					<td>
						<input type="checkbox" name="items.item" value="${item}"/>
					</td>
					<td>${item}</td>
					<td>${itemename}</td>
					<td>${itemcname}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	
</div>



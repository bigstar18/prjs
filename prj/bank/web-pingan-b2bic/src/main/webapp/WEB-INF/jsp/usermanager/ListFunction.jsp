<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="func.appid" value="${func.appid}"/>
	<input type="hidden" name="func.subappid" value="${func.subappid}"/>
	<input type="hidden" name="func.funcName" value="${func.funcName}"/>
	<input type="hidden" name="func.funcAddress" value="${func.funcAddress}"/>
</form>

<form id="query_listfunc" method="post" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent" layoutH>
				<li>
					<label>所属应用：</label>
					<s:select 
						id="appid_select" 
						name="func.appid" 
						list="appidList" 
						listKey="id" 
						listValue="value" 
						value="func.appid" 
						cssClass="commontypeSelect textInput"
						refSelect="subappid_select"
						refCt="'subappidList'"
						refForm="query_listfunc"
						refUrl="QuerySelectList_ListFunction"/>
				</li>
				<li>
					<label>所属子应用：</label>
					<s:select id="subappid_select" name="func.subappid" list="subappidList" listKey="id" listValue="value" value="func.subappid" cssClass="textInput"/>
				</li>
				<li></li>
				<li>
					<label>功能名称：</label>
					<input type="text" name="func.funcName" value="${func.funcName}"/>
				</li>
				<li>
					<label>功能地址：</label>
					<input type="text" name="func.funcAddress" value="${func.funcAddress}"/>
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
			<li><a class="add" target="navTab" rel="addFunc" href="CreateInfoFunction?navTabId=${ navTabId }" title="添加功能"><span>添加</span></a></li>
			<li><a class="edit" target="navTab" rel="modifyFunc" href="ModifyInfoFunction?func.funcId={funcId}&navTabId=${ navTabId }" title="修改功能"><span>修改</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="funcList.funcId" href="RemoveFunction" class="delete"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="163" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="funcList.funcId" class="checkboxCtrl"></th>
				<th>功能ID</th>
				<th>功能名称</th>
				<th>功能地址</th>
				<th>是否显示</th>
				<th>功能描述</th>
				<th>所属应用</th>
				<th>所属子应用</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="funcList">
				<tr target="funcId" rel="${funcId}">
					<td>
						<input type="checkbox" name="funcList.funcId" value="${funcId}"/>
					</td>
					<td>${funcId}</td>
					<td>${funcName}</td>
					<td>${funcAddress}</td>
					<td>${runflgStr}</td>
					<td>${funcDesc}</td>
					<td>${app.appname}</td>
					<td>${subApp.appname}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<!--  <div class="noRandPager"> -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
	<!--  </div> -->
</div>



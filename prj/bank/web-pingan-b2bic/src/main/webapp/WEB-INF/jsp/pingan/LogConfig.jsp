<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.perPageRecords}" />
	
	<input type="hidden" name="rollPage"/>
</form>

<div class="pageContent">
	<%--  
	<div class="panelBar">
		<ul class="toolBar">
			<s:if test="hasPermission('B2Bi_LogConfig_downloadLog')">
				<li><a class="icon" target="_blank" href="B2Bi_LogConfig_downloadLog" title="下载日志文件"><span>下载日志文件</span></a></li>
			</s:if>
		</ul>
	</div>
	--%>
	
	<form method="post" action="B2Bi_LogConfig_save?navTabId=${ navTabId }" class="pageForm required-validate changeSubmitForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH>
			<dl>
				<dt>日志级别</dt>
				<dd>
					<s:select list="#{ 'debug' : 'debug', 'info' : 'info', 'warn' : 'warn', 'error' : 'error', 'fatal' : 'fatal' }"
						name="logLvLVo.sysLevel"
						value="logLvLVo.sysLevel"
						cssStyle="margin-right : 60px;"></s:select>
						
					<s:if test="hasPermission('B2Bi_LogConfig_downloadLog')">	
						<div class="button"><div class="buttonContent"><button disabled="disabled" type="submit">保存配置</button></div></div>
					</s:if>
				</dd>
			</dl>
		</div>
	</form>
	
	<h2 class="contentTitle">日志列表</h2>
	<form method="post" onsubmit="return navTabSearch(this)" style="margin-bottom: 10px;">
		<div style="width: 500px;float: left;">
			<label style="margin-left: 5px;">开始时间：</label>
			<input type="text" name="condition.starttime" value="${ condition.starttime }" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" style="margin-right: 20px;"/>
			
			<label>结束时间：</label>
			<input type="text" name="condition.endtime" value="${ condition.endtime }" class="date" dateFmt="yyyy-MM-dd HH:mm:ss"/>
		</div>
		<div class="button" style="margin-bottom: 3px;"><div class="buttonContent"><button type="submit">查询</button></div></div>
	</form>
	<table class="table" layoutH=187" width="98%">
		<thead>
			<tr>
				<th>文件名</th>
				<th>最后修改日期</th>
				<th>文件大小</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${ name }</td>
					<td>${ time }</td>
					<td><s:property value="formatFileSize(size)"/></td>
					<td><a href="<s:url encode="true" value="B2Bi_LogConfig_downloadLog?logFileName=%{name}"></s:url>" target="_blank" style="color: blue;">下载查看</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<%@ include file="/WEB-INF/jsp/common/panelBar.jsp" %>
</div>

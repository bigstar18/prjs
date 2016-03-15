<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form id="query_addUser" method="post" action="CreateUser?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>用户姓名</dt>
				<dd>
					<input type="text" id="tellerName_input" name="u.tellerName" value="${u.tellerName}" class="required" maxlength="12" alphanumeric="alphanumeric"/>
				</dd>
			</dl>
			<dl>
				<dt>级别列表</dt>
				<dd>
					<s:select id="select_role" name="u.roleId" list="roleList" listKey="roleId" listValue="roleName" value="u.roleId" cssClass="textInput"></s:select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

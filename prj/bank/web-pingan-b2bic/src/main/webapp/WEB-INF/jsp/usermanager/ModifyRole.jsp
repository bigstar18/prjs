<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<style>
.double_select{width: 300px;height: 280px;}
.double_select_left{margin-right: 45px;}
.double_select_right{margin-left: 45px;}
</style>

<script type="text/javascript" src="eteller/js/usermanager/userMgr_comm.js"></script>
<script type="text/javascript">
$(function() {removeSeletedItemInDoubleSelect($('#role_func1_modify'), $('#role_func2_modify'));});
</script>

<div class="pageContent">
	
	<form id="query_addUser" method="post" action="ModifyRole?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="role.roleId" value="${role.roleId}"/>
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>级别名称</dt>
				<dd>
					<input type="text" name="role.roleName" value="${role.roleName}" class="required"/>
				</dd>
			</dl>
			<dl>
				<dt>上级级别</dt>
				<dd>
					<s:select 
						name="role.superiorRoleId" 
						list="roleList" 
						listKey="roleId" 
						listValue="roleName"
						value="role.superiorRoleId"
						emptyOption="true"
						cssClass="textInput"
						theme="simple"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>下属级别</dt>
				<dd>
					<input type="radio" name="role.finalFlag" value="0" ${empty role ? "checked" : role.finalFlag == "0" ? "checked" : ""}>可以有下属级别&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="role.finalFlag" value="1" ${role.finalFlag == "1" ? "checked" : ""} style="margin-left: 30px;">不可以有下属级别
				</dd>
			</dl>
			<dl>
				<dt>所属应用</dt>
				<dd>
					<s:select 
						id="select_appid_modify"
						name="appid" 
						list="apps" 
						listKey="id" 
						listValue="value"
						value="appid"
						cssClass="beansSelect textInput"
						refSelect="role_func1_modify"
						refBeans="'toSelectFuncList'"
						refKey="'funcId'"
						refValue="'funcName'"
						refUrl="SelectListInfoRole"
						refCallback="removeSeletedItemInDoubleSelect($('#role_func1_modify'), $('#role_func2_modify'));"
						theme="simple"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>功能信息</dt>
				<dd>
					<s:optiontransferselect 
						leftTitle="可选"
						rightTitle="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已选"
						id="role_func1_modify"
						doubleId="role_func2_modify"
						cssClass="double_select double_select_left"
						doubleCssClass="double_select double_select_right"
						cssStyle="width: 300px;height: 280px;margin-right: 45px;"
						doubleCssStyle="width: 300px;height: 280px;margin-left: 45px;"
						list="toSelectFuncList"
						listKey="funcId"
						listValue="funcName"
						name="toSelectFuncList.funcId"
						multiple="true"
						doubleList="selectedFuncList"
						doubleListKey="funcId" 
						doubleListValue="funcName"
						doubleName="selectedFuncList.funcId"
						doubleMultiple="true" 
						allowSelectAll="false"
						allowUpDownOnLeft="false"
						allowUpDownOnRight="false"></s:optiontransferselect>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" onclick="$('#role_func2_modify option').attr('selected', true);$(this).parents('form:first').submit();">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

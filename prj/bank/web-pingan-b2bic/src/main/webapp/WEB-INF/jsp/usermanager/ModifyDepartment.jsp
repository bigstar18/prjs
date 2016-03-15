<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<link href="eteller/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="eteller/js/usermanager/userMgr_comm.js"></script>
<script type="text/javascript">
$(function() {removeSeletedItemInDoubleSelect($('#dept_role1_modify'), $('#dept_role2_modify'));});
</script>

<div class="pageContent">
	
	<form id="query_adddept" method="post" action="ModifyDepartment?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>机构号</dt>
				<dd>
					<input type="text" name="dept.id" value="${dept.id}" readonly="readonly" class="required"/>
				</dd>
			</dl>
			<dl>
				<dt>机构名称</dt>
				<dd>
					<input type="text" name="dept.name" value="${dept.name}" class="required"/>
				</dd>
			</dl>
			<dl>
				<dt>机构类型</dt>
				<dd>
					<s:select 
						name="dept.depnotype" 
						list="deptTypeList" 
						listKey="id" 
						listValue="value"
						value="dept.depnotype"
						theme="simple"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>上级机构</dt>
				<dd>
					<s:select 
						id="select_superiorDept_modify"
						name="dept.superiorDepartmentId" 
						list="highLevelDeptList" 
						listKey="id" 
						listValue="name"
						value="dept.superiorDepartmentId"
						emptyOption="true"
						cssClass="beansSelect"
						refSelect="dept_role1_modify"
						refBeans="'toSelectRoleList'"
						refKey="'roleId'"
						refValue="'roleName'"
						refUrl="SelectListInfoDepartment"
						refCallback="removeSeletedItemInDoubleSelect($('#dept_role1_modify'), $('#dept_role2_modify'));"
						theme="simple"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>下属机构上级机构</dt>
				<dd>
					<input type="radio" name="dept.finalFlag" value="0" ${empty dept.finalFlag ? "checked" : dept.finalFlag == "0" ? "checked" : ""}>可以有下属机构&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="dept.finalFlag" value="1" ${dept.finalFlag == "1" ? "checked" : ""} style="margin-left: 30px;">不可以有下属机构
				</dd>
			</dl>
			<dl>
				<dt>机构级别</dt>
				<dd>
					<s:optiontransferselect 
						leftTitle="可选"
						rightTitle="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已选"
						id="dept_role1_modify"
						doubleId="dept_role2_modify"
						cssClass="double_select double_select_left"
						doubleCssClass="double_select double_select_right"
						cssStyle="width: 300px;height: 280px;margin-right: 45px;"
						doubleCssStyle="width: 300px;height: 280px;margin-left: 45px;"
						list="toSelectRoleList"
						listKey="roleId"
						listValue="roleName"
						name="toSelectRoleList.roleId"
						multiple="true"
						doubleList="selectedRoleList"
						doubleListKey="roleId" 
						doubleListValue="roleName"
						doubleName="selectedRoleList.roleId"
						doubleMultiple="true" 
						allowSelectAll="false"
						allowUpDownOnLeft="false"
						allowUpDownOnRight="false"
						theme="simple"></s:optiontransferselect>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" onclick="$('#dept_role2_modify option').attr('selected', true);$(this).parents('form:first').submit();">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form id="query_modifyUser" method="post" action="ModifyUser?navTabId=${ navTabId }" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>用户ID</dt>
				<dd>
					<input id="modifyUser_userid" type="text" name="u.userId" value="${u.userId}" readonly="readonly"/>
				</dd>
			</dl>
			<dl>
				<dt>用户姓名</dt>
				<dd>
					<input type="text" name="u.tellerName" value="${u.tellerName}" class="required" maxlength="12" alphanumeric="alphanumeric"/>
				</dd>
			</dl>
			<dl>
				<dt>级别列表</dt>
				<dd>
					<s:select name="u.roleId" list="roleList" listKey="roleId" listValue="roleName" value="u.roleId"></s:select>
				</dd>
			</dl>
			<dl>
				<dt>用户状态</dt>
				<dd>
					<input type="radio" name="u.tellerState" ${ u.tellerState == '0' ? 'checked' : u.tellerState == '1' ? '' : 'checked' } value="0"/>正常
					<input type="radio" name="u.tellerState" ${ u.tellerState == '1' ? 'checked' : '' } value="1" style="margin-left: 25px;"/>冻结
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button id="modify_user_resetPwd" type="button">重置密码</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		
		<input type="hidden" name="u.tellerPasswd" value="${u.tellerPasswd}"/>
		<input type="hidden" name="u.error" value="${u.error}"/>
		<input type="hidden" name="u.remark" value="${u.remark}"/>
		<input type="hidden" name="u.departmentId" value="${u.departmentId}"/>
		<input type="hidden" name="u.pwdModDate" value="${u.pwdModDate}"/>
		<input type="hidden" name="u.enterTime" value="${u.enterTime}"/>
		<input type="hidden" name="u.exitTime" value="${u.exitTime}"/>
		<input type="hidden" name="u.ipAddress" value="${u.ipAddress}"/>
		<input type="hidden" name="u.pwdInit" value="${u.pwdInit}"/>
		<input type="hidden" name="u.loginStatus" value="${u.loginStatus}"/>
		<input type="hidden" name="u.encryptMethod" value="${u.encryptMethod}"/>
		<input type="hidden" name="u.resetLogin" value="${u.resetLogin}"/>
	</form>
	
</div>

<script type="text/javascript">
$(function() {
	$("#modify_user_resetPwd").click(function() {
		alertMsg.confirm("确定重置吗？", {
			okCall : function() {
				$.post("ResetUserPwd", {"user.userId" : $("#modifyUser_userid").val()}, function(data) {
					data = $.parseJSON(data);
					if(data.statusCode == "200") {
						alertMsg.correct(data.message);
					} else {
						alertMsg.error(data.message);
					}
				});
			}
		});
	});
});
</script>
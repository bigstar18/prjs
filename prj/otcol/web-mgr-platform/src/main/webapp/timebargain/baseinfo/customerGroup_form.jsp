<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if(customerGroupForm.crud.value == "create")
    {
      customerGroupForm.groupID.focus();
    }
    else if(customerGroupForm.crud.value == "update")
    {
      setDisabled(customerGroupForm.parentID);
      setReadOnly(customerGroupForm.groupID);
      customerGroupForm.groupName.focus();
    }
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if(validateCustomerGroupForm(customerGroupForm))
  	{
    if(customerGroupForm.crud.value == "update")
    {
      setEnabled(customerGroupForm.parentID);
    }
    customerGroupForm.submit();
    customerGroupForm.save.disabled = true;
  }
	}
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/customerGroup.do"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="350" width="500" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/customerGroup.do?funcflg=save"
						method="POST" styleClass="form">
						<fieldset>
							<legend class="common">
								<b>设置交易商组信息
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
											上级组名：
									</td>
									<td>
							            <html:select property="parentID" style="width:150">
							                <html:options collection="customerGroupSelect" property="value" labelProperty="label"/>
							            </html:select>
							            <span class="req">*</span>
							        </td>     
								</tr>
								<tr>
									<td align="right">
											组ID：
									</td>
									<td>
										<html:text property="groupID" maxlength="10"
											style="ime-mode:disabled" onkeypress="return numberPass()"
											styleClass="text" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											组名称：
									</td>
									<td>
										<html:text property="groupName" maxlength="64"
											styleClass="text" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											描述：
									</td>
									<td>
										<html:textarea property="description" rows="3" cols="40" styleClass="text" />
									</td>
								</tr>
								<tr>
									<td colspan="2" height="10"></td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="10"></td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
					</html:form>
				</td>
			</tr>
		</table>

		<html:javascript formName="customerGroupForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

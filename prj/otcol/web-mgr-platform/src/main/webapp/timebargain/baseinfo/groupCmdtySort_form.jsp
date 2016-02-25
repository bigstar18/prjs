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
    if(cmdtySortForm.crud.value == "create")
    {
      cmdtySortForm.groupID.focus();
    }
    else if(cmdtySortForm.crud.value == "update")
    {
      setDisabled(cmdtySortForm.sortID);
      setDisabled(cmdtySortForm.groupID);
      cmdtySortForm.maxHoldQty.focus();
    }
}
//save
function save_onclick()
{
  if(trim(cmdtySortForm.groupID.value) == "")
  {
    alert("<fmt:message key="customerGroupForm.groupID"/><fmt:message key="prompt.required"/>");
    cmdtySortForm.groupID.focus();
    return false;
  } 
  if(validateCmdtySortForm(cmdtySortForm))
  {  
    setEnabled(cmdtySortForm.sortID);
    setEnabled(cmdtySortForm.groupID);
    cmdtySortForm.submit();
    setDisabled(cmdtySortForm.sortID);
    setDisabled(cmdtySortForm.groupID);
    cmdtySortForm.save.disabled = true;
  }
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/cmdtySort.do?funcflg=searchGroup"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/cmdtySort.do?funcflg=saveGroup"
						method="POST" styleClass="form">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="cmdtySortForm.titleG" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											<fmt:message key="customerForm.GroupID" />
									</td>
									<td>
										<html:select property="groupID"  style="width:153">
                                          <html:options collection="customerGroupSelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="cmdtySortForm.sortID" />
									</td>
									<td>
										<html:select property="sortID"  style="width:153">
                                          <html:options collection="cmdtySortSelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
								</tr>								
								<tr>
									<td align="right">
											<fmt:message key="cmdtySortForm.maxHoldQty" />
									</td>
									<td>
										<html:text property="maxHoldQty" maxlength="10"
											onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											<fmt:message key="button.save" />
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											<fmt:message key="button.return" />
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
					</html:form>
				</td>
			</tr>
		</table>
		<html:javascript formName="cmdtySortForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

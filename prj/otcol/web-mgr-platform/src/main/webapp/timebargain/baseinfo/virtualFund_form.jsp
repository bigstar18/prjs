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
    customerForm.customerID.value = "<c:out value="${param['customerID']}"/>";
    customerForm.virtualFunds.value = "<c:out value="${param['virtualFunds']}"/>";
    customerForm.addVirtualFunds.focus();
}
//save
function save_onclick()
{
   if(trim(customerForm.addVirtualFunds.value) == "")
   {
       alert("<fmt:message key="customerFunds.addVirtualFunds"/><fmt:message key="prompt.required"/>");
       customerForm.addVirtualFunds.focus();
 	   return false;
   }
   customerForm.submit();	
   customerForm.save.disabled = true;
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/virtualFund.do?funcflg=searchVirtualFund"/>";
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/virtualFund.do?funcflg=saveVirtualFund"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="customerFunds.addVirtualFunds" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											<fmt:message key="customerForm.CustomerID" />
									</td>
									<td>
										<input type="text" name="customerID" readOnly class="ReadOnlyString"/>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="customerFunds.VirtualFunds" />
									</td>
									<td>
										<input type="text" name="virtualFunds" readOnly class="ReadOnlyNumber"/>
									</td>
								</tr>									
								<tr>
									<td align="right">
											<fmt:message key="customerFunds.addVirtualFunds" />
									</td>
									<td>
										<input type="text" name="addVirtualFunds" maxlength="15" onKeyPress="return numberPass()" style="ime-mode:disabled" class="Number"/>
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
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<c:import url="/timebargain/common/dblCustomerID.jsp"/>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if(cmdtySortForm.crud.value == "create")
    {
      cmdtySortForm.customerID.focus();
    }
    else if(cmdtySortForm.crud.value == "update")
    {
      setDisabled(cmdtySortForm.sortID);
      setReadOnly(cmdtySortForm.customerID);
      cmdtySortForm.maxHoldQty.focus();
    }
}
//save
function save_onclick()
{
  if(trim(cmdtySortForm.customerID.value) == "")
  {
    alert("<fmt:message key="customerForm.CustomerID"/><fmt:message key="prompt.required"/>");
    cmdtySortForm.customerID.focus();
    return false;
  } 
  if(validateCmdtySortForm(cmdtySortForm))
  {
    setEnabled(cmdtySortForm.sortID);
    cmdtySortForm.submit();
    setDisabled(cmdtySortForm.sortID);
    cmdtySortForm.save.disabled = true;
  }
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/cmdtySort.do?funcflg=searchCustomer"/>";
}

function customerID_dblclick()
{
  dblCustomerID();
}

function setCustomer(customerID,customerName)
{
  cmdtySortForm.customerID.value = customerID;
  //cmdtySortForm.customerName.value = customerName;
  customerID_onchange();
}

function customerID_onchange()
{
  if(trim(cmdtySortForm.customerID.value) == "")
  {
    statQueryForm.customerName.value = "";
    return false;
  }
  customerManager.getCustomerName(trim(cmdtySortForm.customerID.value),function(data)
  {
    if(data != null)
    {
      //cmdtySortForm.customerName.value = data;
    }
    else
    {
      alert("<fmt:message key="statQuery.customerNotExist"/>");
      cmdtySortForm.customerID.value = "";
      //cmdtySortForm.customerName.value = "";
    }
  });  
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/cmdtySort.do?funcflg=saveCustomer"
						method="POST" styleClass="form">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="cmdtySortForm.titleC" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											<fmt:message key="customerForm.CustomerID" />
									</td>
									<td>
										<html:text property="customerID" maxlength="16" title="Ë«»÷²éÑ¯"
											styleClass="text" onchange="customerID_onchange()" ondblclick="if(!this.readOnly){customerID_dblclick();}"/>
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

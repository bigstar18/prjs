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
    if(tradePropsForm.crud.value == "create")
    {
      tradePropsForm.groupID.focus();
    }
    else if(tradePropsForm.crud.value == "update")
    {
      setDisabled(tradePropsForm.groupID);
      tradePropsForm.maxHoldQty.focus();
    }
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if(trim(tradePropsForm.groupID.value) == "")
  {
      alert("<fmt:message key="customerForm.GroupID"/><fmt:message key="prompt.required"/>");
      tradePropsForm.groupID.focus();
      return false;
  }
  if(trim(tradePropsForm.maxHoldQty.value) == "")
  {
      alert("<fmt:message key="tradePropsForm.maxHoldQty"/><fmt:message key="prompt.required"/>");
      tradePropsForm.maxHoldQty.focus();
      return false;
  }
  if(trim(tradePropsForm.minClearDeposit.value) == "")
  {
      alert("<fmt:message key="tradePropsForm.minClearDeposit"/><fmt:message key="prompt.required"/>");
      tradePropsForm.minClearDeposit.focus();
      return false;
  }
  if(trim(tradePropsForm.maxOverdraft.value) == "")
  {
      alert("<fmt:message key="tradePropsForm.maxOverdraft"/><fmt:message key="prompt.required"/>");
      tradePropsForm.maxOverdraft.focus();
      return false;
  }

    setEnabled(tradePropsForm.groupID);
     tradePropsForm.moduleName.value="n";
    tradePropsForm.submit();
    setDisabled(tradePropsForm.groupID);
    tradePropsForm.save.disabled = true;
	}
  
  
}
function save_onclick1()
{
	if (confirm("您确定要提交吗？")) {
		if(trim(tradePropsForm.groupID.value) == "")
  {
      alert("<fmt:message key="customerForm.GroupID"/><fmt:message key="prompt.required"/>");
      tradePropsForm.groupID.focus();
      return false;
  }
  if(trim(tradePropsForm.maxHoldQty.value) == "")
  {
      alert("<fmt:message key="tradePropsForm.maxHoldQty"/><fmt:message key="prompt.required"/>");
      tradePropsForm.maxHoldQty.focus();
      return false;
  }
  if(trim(tradePropsForm.minClearDeposit.value) == "")
  {
      alert("<fmt:message key="tradePropsForm.minClearDeposit"/><fmt:message key="prompt.required"/>");
      tradePropsForm.minClearDeposit.focus();
      return false;
  }
  if(trim(tradePropsForm.maxOverdraft.value) == "")
  {
      alert("<fmt:message key="tradePropsForm.maxOverdraft"/><fmt:message key="prompt.required"/>");
      tradePropsForm.maxOverdraft.focus();
      return false;
  }

    setEnabled(tradePropsForm.groupID);
    tradePropsForm.moduleName.value="s";
    tradePropsForm.submit();
    setDisabled(tradePropsForm.groupID);
    tradePropsForm.save.disabled = true;
	}
  
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tradeProps.do?funcflg=searchGroupProps"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeProps.do?funcflg=saveGroupProps"
						method="POST" styleClass="form">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="tradePropsForm.title7" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											组ID
									</td>
									<td>
										<html:select property="groupID"  style="width:153">
                                          <html:options collection="customerGroupSelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>								
								<tr>
									<td align="right">
											最大持仓量
									</td>
									<td>
										<html:text property="maxHoldQty" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/> <span class="req">*</span>
										
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											最低结算保证金/>
									</td>
									<td>
										<html:text property="minClearDeposit" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/> <span class="req">*</span>
										
									</td>
									<td></td>
								</tr>								
								<tr>
									<td align="right">
											最大透支额度
									</td>
									<td>
										<html:text property="maxOverdraft" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/> <span class="req">*</span>
										
									</td>
									<td></td>
								</tr>																																												
								<tr>
									<td colspan="3" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick1();">
											提交生效
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
						<html:hidden property="moduleName" />
					</html:form>
				</td>
			</tr>
		</table>


		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

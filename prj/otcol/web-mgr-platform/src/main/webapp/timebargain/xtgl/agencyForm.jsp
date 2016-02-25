<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<title></title>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script type="text/javascript">
//页面装载时调用 
function window_onload()
{
    highlightFormElements();
    setDisabled(agencyForm.marketID);
    setDisabled(agencyForm.customerID); 
    setDisabled(agencyForm.marketStatus); 
    agencyForm.marketStatus.value = "";   
    agencyForm.type.focus();
}
//提交 
function ok_onclick()
{
  if(agencyForm.type.value == "")
  {
    alert("<fmt:message key="agencyForm.type"/><fmt:message key="prompt.required"/>");
    return false;
  }
  if(agencyForm.type.value == "09")
  {
    if(agencyForm.marketStatus.value == "")
    {
      alert("<fmt:message key="agencyForm.marketStatus"/><fmt:message key="prompt.required"/>");
      agencyForm.marketStatus.focus();
      return false;
    }
  } 
  if(!confirm("<fmt:message key="prompt.isOperate"/>"))
  {
    return false;
  }
  agencyForm.submit();
  agencyForm.ok.disabled = true;
}
//操作类别变化 
function type_onchange()
{
  if(agencyForm.type.value == "05" || agencyForm.type.value == "06" || agencyForm.type.value == "07"  || agencyForm.type.value == "08")
  {
    setDisabled(agencyForm.marketID);
    setDisabled(agencyForm.customerID);
    setDisabled(agencyForm.marketStatus); 
  }  
  else if(agencyForm.type.value == "02" || agencyForm.type.value == "03")
  {
    setEnabled(agencyForm.marketID);
    setDisabled(agencyForm.customerID);
    setDisabled(agencyForm.marketStatus); 
  }  
  else if(agencyForm.type.value == "04")
  {
    setDisabled(agencyForm.marketID);
    setDisabled(agencyForm.customerID);
    setDisabled(agencyForm.marketStatus); 
  }   
  else if(agencyForm.type.value == "09")
  {
    setEnabled(agencyForm.marketID);
    setDisabled(agencyForm.customerID);
    setEnabled(agencyForm.marketStatus);
  } 
  else if(agencyForm.type.value == "10")
  {
    setDisabled(agencyForm.marketID);
    setEnabled(agencyForm.customerID);
    setDisabled(agencyForm.marketStatus); 
  }  
  else
  {
    setDisabled(agencyForm.marketID);
    setDisabled(agencyForm.customerID);
    setDisabled(agencyForm.marketStatus); 
  }  
}
</script>
</head>
<body  leftmargin="0" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
<table border="0" height="300" align="center">
<tr><td>
<html:form method="post" action="/timebargain/xtgl/agency.do?funcflg=operate" target="HiddFrame">
<fieldset class="pickList">
	<legend class="common">
	<b><fmt:message key="agencyForm.title"/></b>
	</legend>
      <table border="0" align="center" cellpadding="5" cellspacing="5" class="common">

        <tr>
          <td width="53" height="29"><fmt:message key="agencyForm.type"/></td>
          <td>
             <html:select property="type" onchange="type_onchange()" style="width:180">
				    <html:option value=""></html:option>
					<html:option value="02"><fmt:message key="agencyForm.type.option.requestCmdty"/></html:option>
					<html:option value="03"><fmt:message key="agencyForm.type.option.reconnectMarket"/></html:option>
					<html:option value="04"><fmt:message key="agencyForm.type.option.refreshMemory"/></html:option> 
					<html:option value="05"><fmt:message key="agencyForm.type.option.stopTrade"/></html:option>
					<html:option value="06"><fmt:message key="agencyForm.type.option.contineTrade"/></html:option>
					<html:option value="07"><fmt:message key="agencyForm.type.option.closeAgency"/></html:option>
					<html:option value="08"><fmt:message key="agencyForm.type.option.startAgency"/></html:option>
					<html:option value="09"><fmt:message key="agencyForm.type.option.chgMarketStatus"/></html:option>
					<html:option value="10"><fmt:message key="agencyForm.type.option.refreshSortHoldMap"/></html:option>
			 </html:select>
          </td>
        </tr>
        <tr>
          <td width="53" height="28"><fmt:message key="agencyForm.marketID"/></td>
          <td>
            <html:select property="marketID"  style="width:180">
                <html:options collection="marketSelect" property="value" labelProperty="label"/>
            </html:select>
          </td>
        </tr>
		<tr>
		  <td width="53" height="28"><fmt:message key="agencyForm.customerID"/></td>
		  <td>
			<html:select property="customerID"  style="width:180">
                <html:options collection="customerSelect" property="value" labelProperty="label"/>
            </html:select>
		  </td>
		</tr>
		<tr>
		  <td width="53" height="28"><fmt:message key="agencyForm.marketStatus"/></td>
		  <td>
			<html:select property="marketStatus"  style="width:180">
                <html:option value=""></html:option>
				<html:option value="2"><fmt:message key="agencyForm.marketStatus2"/></html:option>
				<html:option value="3"><fmt:message key="agencyForm.marketStatus3"/></html:option>
				<html:option value="4"><fmt:message key="agencyForm.marketStatus4"/></html:option>
				<html:option value="5"><fmt:message key="agencyForm.marketStatus5"/></html:option>
            </html:select>
		  </td>
		</tr>		
		<tr><td colspan="2" align="center">
		  <html:button  property="ok" styleClass="button" onclick="javascript:ok_onclick();"><fmt:message key="button.ok"/></html:button>
		  <html:reset  property="reset" styleClass="button"><fmt:message key="button.reset"/></html:reset>
        </td></tr>
      </table>
</fieldset>    
</html:form>
</td></tr>
</table>

<%@ include file="/timebargain/common/messages.jsp" %> 
</body>
</html>


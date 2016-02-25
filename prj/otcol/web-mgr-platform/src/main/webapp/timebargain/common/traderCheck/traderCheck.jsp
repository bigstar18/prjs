<%@ include file="/timebargain/common/taglibs.jsp"%>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title>
<fmt:message key="traderCheck.title"/>
</title>
<link href="<c:url value="/timebargain/styles/common.css"/>" type="text/css" rel="stylesheet">	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
function window_onload()
{
  highlightFormElements();
  ordersForm.traderID.focus();
}
function closewindow()
{
    window.dialogArguments.traderLoginCallBack(trim(ordersForm.traderID.value));
    window.close();
}
function ok_onclick()
{
  if(trim(ordersForm.traderID.value) == "")
  {
    alert("<fmt:message key="traderForm.TraderID"/><fmt:message key="prompt.required"/>");
    ordersForm.traderID.focus();
    return false;
  }
  if(trim(ordersForm.password.value)=="")
  {
    alert("<fmt:message key="traderForm.Password"/><fmt:message key="prompt.required"/>");
    ordersForm.password.focus();
    return false;
  }  
  ordersForm.submit();
  ordersForm.ok.disabled = true;
}

</script>
</head>
<body topMargin="0" leftMargin="0" onload="JavaScript:window_onload();" onkeypress="javascript:keyEnter(event.keyCode);">
<html:form action="/timebargain/tradecontrol/forceClose.do?funcflg=login" target="HiddFrame" method="post">
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">

								<tr>
									<td align="right">
											<fmt:message key="traderForm.TraderID" />
									</td>
									<td>
										<input type="text" name="traderID" maxlength="10"
											class="text" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="traderForm.Password" />
									</td>
									<td>
										<input type="password" name="password" maxlength="64" class="text"/>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="ok" styleClass="button"
											onclick="javascript:return ok_onclick();">
											<fmt:message key="button.ok" />
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:window.close();">
											<fmt:message key="button.cancel" />
										</html:button>
									</td>
								</tr>								
							</table>
</html:form>
</body>
</html>

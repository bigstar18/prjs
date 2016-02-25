<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
<head>
<title>
head
</title>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script type="text/javascript">
//window_onload
function window_onload()
{
  highlightFormElements();
  customerForm.customerID.focus();
}
//reset_onclick()
function reset_onclick()
{
  customerForm.customerID.value = "";
  customerForm.customerName.value = "";
  customerForm.query.disabled=false;
  parent.ListFrame.location.href="customerQuery_list.jsp";
}
</script>
<head>

<body topMargin="0" leftMargin="0" onload="window_onload()" onkeypress="javascript:keyEnter(event.keyCode);">
<html:form action="/timebargain/common/customerQuery/customerQuery.do?funcflg=customerQuery" target="ListFrame">
  <table cellSpacing="0" cellPadding="0" width="100%" height="100%" border="0" align="center">
    <tr>
      <td align="center">
        <table border="0" class="common">
          <tr>
            <td align="center">
              交易商ID：<html:text property="customerID" size="20" maxlength="16"/>
              交易商名称：<html:text property="customerName" maxlength="32"/>
            </td>
          </tr>     
          <tr><td height="10"></td></tr>             
          <tr>
            <td align="center">              
              <input type="button" name="query" value="<fmt:message key="button.query"/>"  class="button" style="behavior: url(<c:url value="/timebargain/scripts/ButtonStyleFlat.htc"/>)" onclick="javascript:customerForm.submit();customerForm.query.disabled=true;">&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" name="reset" value="<fmt:message key="button.reset"/>"  class="button" style="behavior: url(<c:url value="/timebargain/scripts/ButtonStyleFlat.htc"/>)" onclick="javascript:reset_onclick();">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</html:form>
</body>
</html>

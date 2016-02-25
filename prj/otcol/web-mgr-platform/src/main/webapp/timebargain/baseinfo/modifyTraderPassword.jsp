modifyTraderPassword.jsp
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <head>
	<%@ include file="/timebargain/common/meta.jsp" %>
	<link href="<c:url value="/timebargain/styles/common.css"/>" type="text/css" rel="stylesheet">	
	<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
	<title><fmt:message key="customerForm.pwd.title"/></title>
<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
 //    pwdForm.oldpwd.focus();
}
//修改口令
function save_onclick()
{
	traderForm.submit();
    traderForm.save.disabled = false;
}
</script>	
<head>
  </head>
  
  <body onload="window_onload();" onkeypress="javascript:keyEnter(event.keyCode);">
    <html:form action="/timebargain/baseinfo/trader.do?funcflg=modifyTraderPassword" method="post" styleClass="form">
      <table class="common" align="center">
		<tr>
			<td colspan="2" height="10"></td>
					
	     </tr>
	     
		<tr>
			<td>新口令：</td>
			<td>
				<input type="password" name="password" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
		<tr>
			<td>确认新口令：</td>
			<td>
				<input type="password" name="confirmPassword" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
	     
	     <%
	     	String traderID = (String)request.getParameter("traderID");
	     %>
	     <input type="hidden" name="traderID" value="<%=traderID%>"/>
	     
		<tr>
			<td colspan="2" align="center">
			  <html:button  property="save" styleClass="button" onclick="javascript:return save_onclick();">修改</html:button>
			  <html:reset  property="cancel" styleClass="button" onclick="javascript:window.close();">退出</html:reset>
			</td>		
	     </tr>	
		<tr>
			<td colspan="2" align="center">
			    <div id="chgpwdMessage" class="req">
                
                </div>
			</td>		
	     </tr>	          	     	     
	</table>
    </html:form>
    <html:javascript formName="traderForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
  </body>
</html:html>

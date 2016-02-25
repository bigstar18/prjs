<%@ include file="/timebargain/common/taglibs.jsp" %>
<%@ page pageEncoding="GBK" %>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
String consignerID = (String)request.getAttribute("consignerID");
%>
<html>
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
  var type = "consigner";
  var v_oldpwd = trim(pwdForm.oldpwd.value);
  var v_newpwd = trim(pwdForm.newpwd.value);
//  if(v_oldpwd == "")
//  {
//    alert("<fmt:message key="customerForm.oldpwd"/><fmt:message key="prompt.required"/>");
//    pwdForm.oldpwd.focus();
//    return false;
//  }
   if(v_newpwd == "")
  {
    alert("<fmt:message key="customerForm.newpwd"/><fmt:message key="prompt.required"/>");
     pwdForm.newpwd.focus();
    return false;
  }  
  if (v_oldpwd == "") {
  	alert("原口令不能为空！");
  	pwdForm.oldpwd.focus();
  	return false;
  }
 
  if(type=="market")
  {
  if(v_newpwd.length<5)
  {
    alert("<fmt:message key='customerForm.newpwd'/> <fmt:message key='customerForm.password.length1'/>");
    pwdForm.newpwd.focus();
    return false;
  }
  }
  else
  {
   if(v_newpwd.length<6)
  {
    alert("<fmt:message key='customerForm.newpwd'/> <fmt:message key='customerForm.password.length'/>");
    pwdForm.newpwd.focus();
    return false;
  }
  } 
  if(trim(pwdForm.confirmpwd.value) == "")
  {
    alert("<fmt:message key="customerForm.confirmpwd"/><fmt:message key="prompt.required"/>");
    pwdForm.confirmpwd.focus();
    return false;
  } 
  if(type=="market")
  {
  if(trim(pwdForm.confirmpwd.value).length <5)
  {
    alert("<fmt:message key='customerForm.confirmpwd'/> <fmt:message key='customerForm.password.length1'/>");
    pwdForm.confirmpwd.focus();
    return false;
  }
  }
  else
  {
   if(trim(pwdForm.confirmpwd.value).length <6)
  {
    alert("<fmt:message key='customerForm.confirmpwd'/> <fmt:message key='customerForm.password.length'/>");
    pwdForm.confirmpwd.focus();
    return false;
  }
  }   
  if(trim(pwdForm.newpwd.value) != trim(pwdForm.confirmpwd.value))
  {
    alert("<fmt:message key="customerForm.newpwdnotlike"/>");
    pwdForm.confirmpwd.focus();
    return false;
  }
  var type1="";
  //alert(pwdForm.type1.value);
  if(!pwdForm.type1)
  {
     type1=3
  }
  else
  {
     type1=pwdForm.type1.value;
  }
  
  var prikey = "<%=request.getParameter("prikey")%>";
  var consignerID = document.forms(0).consignerID.value;
  new Ajax.Updater('chgpwdMessage', '<c:url value="/timebargain/baseinfo/market.do?funcflg=modifyPwd&oldpwd="/>' + v_oldpwd + "&newpwd=" + v_newpwd + "&prikey=" + consignerID + "&type=" + type+"&type1="+type1, {asynchronous:true});
}
</script>	
</head>

<body onload="window_onload();" onkeypress="javascript:keyEnter(event.keyCode);">
<form method="POST" class="form" name="pwdForm">	
	<input type="hidden" name="consignerID" value="<%=consignerID%>"/>
	<table class="common" align="center">
		<tr>
			<td colspan="2" height="10"></td>
					
	     </tr>
	     
		<tr>
			<td><fmt:message key="customerForm.oldpwd"/>：</td>
			<td>
				<input type="password" name="oldpwd" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
	    
		<tr>
			<td><fmt:message key="customerForm.newpwd"/>：</td>
			<td>
				<input type="password" name="newpwd" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
		<tr>
			<td><fmt:message key="customerForm.confirmpwd"/>：</td>
			<td>
				<input type="password" name="confirmpwd" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
	     
	      <%
                 if("market".equals(request.getParameter("type")))
                 {
                 %>
                   <tr>
                   <td colspan="2" align="center">
                   <select name="type1" >
                   <option value="1"><fmt:message key="repairMarketPassword"/></option>
                   <option value="2"><fmt:message key="repairlocalPassword"/></option>
                   </select>
                   </td>
                   </tr>
                 <%
                 }
                  %>
	     
		<tr>
			<td colspan="2" align="center">
			  <html:button  property="save" styleClass="button" onclick="javascript:return save_onclick();"><fmt:message key="button.save"/></html:button>
			  <html:reset  property="cancel" styleClass="button" onclick="javascript:window.close();">重  置</html:reset>
			</td>		
	     </tr>	
		<tr>
			<td colspan="2" align="center">
			    <div id="chgpwdMessage" class="req">
                
                </div>
			</td>		
	     </tr>	          	     	     
	</table>
</form>
</body>
</html>
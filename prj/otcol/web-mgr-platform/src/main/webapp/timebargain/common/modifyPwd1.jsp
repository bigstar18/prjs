<%@ include file="/timebargain/common/taglibs.jsp" %>
<%@ page pageEncoding="GBK" %>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
	<%@ include file="/timebargain/common/meta.jsp" %>
	<link href="<c:url value="/timebargain/styles/common.css"/>" type="text/css" rel="stylesheet">	
	<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
	<title>�޸�����</title>
<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
 //    pwdForm.oldpwd.focus();
}
//�޸Ŀ���
function save_onclick()
{
  var type = "<%=request.getParameter("type")%>";
  var v_oldpwd = "";
  var v_newpwd = trim(pwdForm.newpwd.value);
//  if(v_oldpwd == "")
//  {
//    alert("<fmt:message key="customerForm.oldpwd"/><fmt:message key="prompt.required"/>");
//    pwdForm.oldpwd.focus();
//    return false;
//  }
   if(v_newpwd == "")
  {
    alert("�¿����Ϊ�գ�");
     pwdForm.newpwd.focus();
    return false;
  }  
 
  if(type=="market")
  {
  if(v_newpwd.length<5)
  {
    alert("�¿����Ӧ����6λ��");
    pwdForm.newpwd.focus();
    return false;
  }
  }
  else
  {
   if(v_newpwd.length<6)
  {
    alert("�¿����Ӧ����6λ��");
    pwdForm.newpwd.focus();
    return false;
  }
  } 
  if(trim(pwdForm.confirmpwd.value) == "")
  {
    alert("ȷ���¿����Ϊ�գ�");
    pwdForm.confirmpwd.focus();
    return false;
  } 
  if(type=="market")
  {
  if(trim(pwdForm.confirmpwd.value).length <5)
  {
    alert("ȷ���¿����Ӧ����6λ��");
    pwdForm.confirmpwd.focus();
    return false;
  }
  }
  else
  {
   if(trim(pwdForm.confirmpwd.value).length <6)
  {
    alert("ȷ���¿����Ӧ����6λ��");
    pwdForm.confirmpwd.focus();
    return false;
  }
  }   
  if(trim(pwdForm.newpwd.value) != trim(pwdForm.confirmpwd.value))
  {
    alert("�¿�����ȷ�Ͽ��һ�£�");
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
  parent.HiddFrame2.location.href = "<c:url value="/timebargain/baseinfo/market.do?funcflg=modifyPwd&oldpwd="/>" + "" + "&newpwd=" + v_newpwd + "&prikey=" + prikey + "&type=" + type+"&type1="+type1;
  //new Ajax.Updater('chgpwdMessage', '<c:url value="/timebargain/baseinfo/market.do?funcflg=modifyPwd&oldpwd="/>' + "" + "&newpwd=" + v_newpwd + "&prikey=" + prikey + "&type=" + type+"&type1="+type1, {asynchronous:true});
}
</script>	
</head>

<body onload="window_onload();" onkeypress="javascript:keyEnter(event.keyCode);">
<form method="POST" class="form" name="pwdForm">	
	<table class="common" align="center">
		<tr>
			<td colspan="2" height="10"></td>
					
	     </tr>
	     <!--  
		<tr>
			<td><fmt:message key="customerForm.oldpwd"/>��</td>
			<td>
				<input type="password" name="oldpwd" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
	     -->
		<tr>
			<td>�� �� �</td>
			<td>
				<input type="password" name="newpwd" maxlength="20" class="text"/>
				<span class="req">*</span>
			</td>		
	     </tr>
		<tr>
			<td>ȷ�Ͽ��</td>
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
                   <option value="1"></option>
                   <option value="2"></option>
                   </select>
                   </td>
                   </tr>
                 <%
                 }
                  %>
	     
		<tr>
			<td colspan="2" align="center">
			  <html:button  property="save" styleClass="button" onclick="javascript:return save_onclick();">�ύ</html:button>
			  <html:reset  property="cancel" styleClass="button" onclick="javascript:window.close();">ȡ��</html:reset>
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
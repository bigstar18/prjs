<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<html>
  <title>�޸��û�����</title>
<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
<script language="javascript" src="<%=serverPath %>/public/jslib/passwordStrength.js"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=commonUserControllerPath%>commonUserModPassword&sign=old">
		<fieldset width="100%">
		<legend>�޸��û�����</legend>
		<BR>
		<span>		
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
        	<td align="right"> �û����� ��</td>
            <td align="left">
            	<input name="userId" type="text" class="text" style="width: 180px;" value="${modUser.userId }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> �û����� ��</td>
            <td align="left">
            	<input name="name" type="text" class="text" style="width: 180px;" value="${modUser.name }" readonly="readonly">
            </td>
        </tr>
   
        <tr height="35">
            <td align="right"> ԭ���� ��</td>
            <td align="left">
            	<input name="oldpass" type="password" class="text" style="width: 180px;">
            </td>
        </tr>
   
        <tr height="35">
            <td align="right"> ������ ��</td>
            <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" onKeyUp="passwordStrength(this.value)" onblur="passwordYin(this.value)" onkeypress="notSpace()">
                  <div id="passwordPrompt">
                    <div style="width:70px; float:left;">����ǿ�ȣ�</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                  </div>
                  <div id="msg"></div>
            </td>
        </tr>
        <tr height="35">       
	          <td align="right"> ȷ������ ��</td>
	          <td align="left">
	          	<input name="password1" type="password" class="text" style="width: 180px;" onkeypress="notSpace()">
	          </td>
        </tr>
  	</table>
		<BR>
        </span>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
			  <input type="button" name="btn" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close();" class="btn" value="�ر�">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{ 
	if(Trim(frm.oldpass.value) == "")
	{
		alert("ԭ���벻��Ϊ�գ�");
     	frm.oldpass.focus();
		return false;
	} 
	if(Trim(frm.password.value) == "")
	{
		alert("�����벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(Trim(frm.password.value),8)){
		alert("���볤�Ȳ�������8λ���ɰ�����ĸ�����֡��������,���������룡");
		frm.password.focus();
		return false;
	}
	else if(Trim(frm.password1.value) == "")
	{
		alert("ȷ�����벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("��������ȷ�����벻һ�£�");
		frm.password1.focus();
		return false;
	}
	else 
	{ 
	  if(userConfirm()){
		frm.submit();
		//return true;
	   }else{
	    return false;
	   }
	}
}
//-->
</SCRIPT>
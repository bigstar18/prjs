<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>
<base target="_self"> 

	<script language="javascript">
	//alert(<%=request.getParameter("brokerid")%>);
		<c:if test='${not empty resultMsg}'>
				window.close();
		</c:if>
	</script>
<html>
  <head>
	<title>��������</title>
	<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
	<script language="javascript" src="<%=request.getContextPath()%>/common/public/jslib/passwordStrength.js"></script>
</head>
<body>
<form name=frm action="<%=brokerControllerPath%>setBrokerPwd" method='post'>
			<fieldset width="100%">
		<legend>����<%=BROKER%>����</legend>
		<BR>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right" width="40%"><%=BROKERID%> ��</td>
                <td align="left" >
                	${param.brokerid} <input name="brokerid" type="hidden" value="${param.brokerid}">&nbsp;
                </td>
				</tr>
				<tr height="35">
				<td align="right"> ������ ��</td>
                <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" onKeyUp="passwordStrength(this.value)" onblur="passwordYin(this.value)" onkeypress="notSpace()" maxlength="14">&nbsp;
                  <div id="passwordPrompt">
                    <div style="width:40px; float:left;">ǿ�ȣ�</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                    <div style="clear: both;">���볤��8-14λ����ĸ���ִ�Сд</div>
                  </div>
                  <div id="msg"></div>
                </td>
				</tr>
				<tr height="35">
            	<td align="right"> ������ȷ�� ��</td>
                <td align="left">
                	<input name="password1" type="password" class="text" style="width: 180px;" onkeypress="notSpace()" maxlength="14" >&nbsp;
                </td>
				</tr>
        	</table>
			<BR>
        </span>
		</fieldset>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				  <tr height="35">
					<td width="40%"><div align="center">
					  <input type="hidden" name="opt">
					  <input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
					  <input name="cls" type="button" onclick="window.close()" class="btn" value="����">&nbsp;&nbsp;
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
	if(Trim(frm.password.value) == "")
	{
		alert("�����벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(Trim(frm.password.value),8))
	{
		alert("�����볤�Ȳ�������8λ���ɰ�����ĸ�����֡��������,���������룡");
		frm.password.focus();
		return false;
	}
	else if(Trim(frm.password1.value) == "")
	{
		alert("������ȷ�ϲ���Ϊ�գ�");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("������������벻һ�£�");
		frm.password1.focus();
		return false;
	}
	else 
	{
	    if(userConfirm()){
		  frm.submit();
		}else{
		  return false;
		}
	}
}
//-->
</SCRIPT>
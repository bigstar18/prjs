<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<style type="text/css">
<!--
body {
	background-color: #6b7ea9;
	text-align: center;
	vertical-align: middle;
	margin-top: 100px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
}
.login_td {
	background-color: #CFD9F1;
	height: 298px;
	width: 490px;
	background-image: url(pic/bg.jpg);
	background-repeat: no-repeat;
	border: 1px dashed #FFFFFF;
	background-attachment: fixed;
	background-position: center bottom;
	margin: 0px;
	text-align: center;
	vertical-align: top;
}
.login_bt {
	font-size: 12px;
	font-weight: normal;
	color: #FFFFFF;
	text-decoration: none;
	background-color: #6B7EA9;
	padding-top: 0px;
	padding-right: 5px;
	padding-bottom: 0px;
	padding-left: 5px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #414F70;
	border-left-color: #414F70;
	text-align: center;
}
.login_k {
	background-color: #FFFFFF;
	border: 1px dashed #6B7EA9;
	height: 18px;
	width: 140px;
}
.login_btn {
	font-size: 12px;
	font-weight: normal;
	color: #FFFFFF;
	text-decoration: none;
	background-color: #6B7EA9;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #6B7EA9;
	border-right-color: #3D4A69;
	border-bottom-color: #3D4A69;
	border-left-color: #6B7EA9;
	padding-top: 2px;
	padding-right: 2px;
	padding-bottom: 0px;
	padding-left: 2px;
}
-->
</style>

<%
if(request.getParameter("Submit") != null)
{		
		//out.println(delNull(request.getRemoteAddr()));
		//��֤��У��	
		String randNumSys = (String)session.getAttribute("RANDOMICITYNUM");
		String randNumInput = request.getParameter("randNumInput");
		if(!(randNumInput != null && !randNumInput.trim().equals("") && randNumInput.trim().equals(randNumSys)))
		{
			alert("��֤�����",out);
		}
		else //����У��
	    {
			String username = delNull(request.getParameter("username"));
			String password = delNull(request.getParameter("password"));
			
			LogonManager manager = LogonManager.getInstance();
			TraderInfo traderInfo= manager.logon(username, password, "", request.getRemoteAddr());
				/*
				sessionID��-1������Ա���벻���ڣ�-2�������ȷ��-3����ֹ��½��
				-4��Key����֤����-5�������쳣 -6���װ�鱻��ֹ
				*/	
				if(traderInfo.auSessionId>0)
				{
					session.setAttribute("LOGINID",new Long(traderInfo.auSessionId));		
					session.setAttribute("username",username);
					session.setAttribute("FIRMID",traderInfo.firmId);
					session.setMaxInactiveInterval(SESSIONINTERVAL);		
				    sendRedirect("main.jsp",out);
				  return;
				}	
				else if(traderInfo.auSessionId == -1)
				{
					alert("����Ա���벻���ڣ�",out);	
				}
				else if(traderInfo.auSessionId == -2)
				{
					alert("�����ȷ��",out);	
				}	
				else if(traderInfo.auSessionId == -3)
				{
					alert("��ֹ��½��",out);	
				}	
				else if(traderInfo.auSessionId == -4)
				{
					alert("Key����֤����",out);	
				}
				else if(traderInfo.auSessionId == -5)
				{
					alert("��¼ʧ�ܣ�",out);	
				}
				else
				{
					alert("���װ�鱻��ֹ��",out);
				}
		}			
}
%>
	<!--<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
      STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
      CODEBASE="GnntKey.cab#Version=1,0,0,5">
	</OBJECT> -->


<body bgcolor="#CEDEDE">
	<form name="frm" method="post" action="">
<table width="492"  border="0"  cellpadding="0" cellspacing="0">
    <tr>
      <td><div align="center"><!--img src="pic/m_name.jpg" width="307" height="56" /--></div></td>
    </tr>
      <tr>
        <td width="506" class="login_td">
        <table width="100%" height="88" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td height="88">         
          <div align="center"><b>�� ӭ �� ¼ �� �� �� ϵ ͳ</b><!--img src="pic/bt.jpg" width="268" height="31" /--></div>
          </td>
          </tr>
          </table>
		  <table width="55%" border="0" cellpadding="0" cellspacing="10">
            <tr>
              <td width="33%" class="login_bt">�û���</td>
              <td width="67%"><input name="username" type="text" class="login_k"></td>
            </tr>
            <tr>
              <td class="login_bt">��&nbsp;&nbsp;��</td>
              <td><input name="password" type="password" class="login_k"></td>
            </tr>
			<tr>
              <td class="login_bt">��֤��</td>
              <td><input name=randNumInput type="text" class="login_k">&nbsp;<img src="image.jsp" align="absmiddle"></td>
            </tr>
            <tr>              
              <td height="52">&nbsp;</td>
              <td><label>
			      <input type="submit" name="Submit" onclick="return frmChk()" class="login_btn" value="�ύ">&nbsp;&nbsp;
				  <input type="reset" name="Submit2"  class="login_btn" value="����">
			  </label>
			  </td>
            </tr>
        </table>
		</td>
        </tr>
    </table>
<input type=hidden name="kcode">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	if(frm.username.value == "")
	{
		alert("����д�û�����");
		frm.username.focus();
		return false;
	}
	else if(frm.randNumInput.value == "")
	{
		alert("����д��֤�룡");
		frm.randNumInput.focus();
		return false;
	}
	return true;
}
frm.username.focus();
//-->
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/broke/organization/updatePassword.action" targetType="hidden">
		<div class="div_scromin">
			<table border="0" height="300" width="90%" align="center" >
				<tr height="100"></tr>
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;�޸Ļ�������</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
        	<td align="right"> �������� ��</td>
            <td align="left">
            	<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
            	
            	<input name="obj.organizationNO" type="text" class="input_text_mid" value="${obj.organizationNO }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> �������� ��</td>
            <td align="left">
            	<input name="obj.name" type="text" class="input_text_mid" value="${obj.name }" readonly="readonly">
            </td>
        </tr>
        <!-- 
        <tr height="35">
            <td align="right"> ԭ���� ��</td>
            <td align="left">
            	<input name="oldpass" type="password" class="text" style="width: 180px;">
            </td>
        </tr>
         -->
        <tr height="35">
            <td align="right"> �µ绰���� ��</td>
            <td align="left">
            	<input id="password" name="obj.password" type="password" class="input_text_mid">
            </td>
        </tr>
        <tr height="35">       
	          <td align="right"> �µ绰����ȷ�� ��</td>
	          <td align="left">
	          	<input name="password1" type="password" class="input_text_mid">
	          </td>
        </tr>
  	</table>
  	</td>
  	</tr>
  	</table>
  	</div>
  	<div class="tab_pad">
      <table border="0" cellspacing="0" cellpadding="0" width="90%" >
		  <tr height="35">
			<td width="30%"><div align="center">
				<button  class="btn_sec" onClick="frmChk()" id="update">����</button>
           </td>
			<td width="30%"><div align="center">
				<button  class="btn_sec" onClick="window.close()">�ر�</button></td>
		  </tr>
	 </table>
	 </div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{ 
	//if(Trim(frm.oldpass.value) == "")
	//{
	//	alert("ԭ���벻��Ϊ�գ�");
	//	frm.oldpass.focus();
	//	return false;
	//}
	//else 
	if(Trim(frm.password.value) == "")
	{
		alert("�绰���벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	}
	else if(Trim(frm.password1.value) == "")
	{
		alert("ȷ�ϵ绰���벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("�绰������ȷ�ϵ绰���벻һ�£�");
		frm.password.focus();
		return false;
	}else if(!chkLen(Trim(frm.password1.value),6)){
		alert("�绰����ȷ�ϲ���С��6λ,����ʹ����8λ����,�����Կո���Ϊ����,���������룡");
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
<%@ include file="/public/footInc.jsp"%>
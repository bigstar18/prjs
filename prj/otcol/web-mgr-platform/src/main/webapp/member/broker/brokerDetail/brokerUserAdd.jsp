<%@ page contentType="text/html;charset=GBK" %>
   <%@ include file="../../public/headInc.jsp"%>
<base target="_self"> 
<html>
  <head>
	<title>���<%=BROKER%>��Ͻ�û�</title>
</head>

	<script language="javascript">
		<c:if test='${not empty resultMsg}'>
			window.dialogArguments.document.frm_query.submit();
				window.close();
				
				
		</c:if>
	</script>
<body>
<form name=frm id=frm action="<%=brokerControllerPath%>brokerFirmAdd" method='post'>
		<fieldset width="100%">
		<legend>���<%=BROKER%>��Ͻ�û�</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
			  <tr height="35">
            	<td align="right"> <%=BROKERID%> ��</td>
                <td align="left">
                	${param.brokerid}<input type=hidden name=brokerid value="${param.brokerid}"><b/>
                </td>
        </tr>
		<tr height="35">
            	<td align="right"> <%=FIRMID%> ��</td>
                <td align="left">
                	<input name="firmid" type="text" class="text" style="width: 100px;" onkeypress="onlyNumberAndCharInput();" maxlength="16"><b><font color=red>*</font></b>
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
				  <input type="hidden" name="opt">
				  <input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
				  <input name="back" type="button" onclick="window.close();" class="btn" value="����">&nbsp;&nbsp;
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
	if(Trim(frm.firmid.value) == "")
	{
		alert("�����̴��벻��Ϊ�գ�");
		frm.firmid.focus();
		return false;
	}
	else 
	{
	  if(userConfirm()){
		//frm.opt.value="add";
		frm.submit();
	
		//window.showModalDialog("brokerUserList.jsp","", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;");
		//return true;
	  }else{
	    return false;
	  }
	}
}
//���ƴ��������û���ѡ��
function checkTradeUser(){
      if(document.frm.tradeUserCheck.checked){
        document.frm.tradeUser.value="true";	
      }else{
        //document.frm.tradeUser.value="0";	
      }
    }
//-->
</SCRIPT>
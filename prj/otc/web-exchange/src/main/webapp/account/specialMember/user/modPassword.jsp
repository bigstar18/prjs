<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/public/session.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<script language="javascript" src="<%=basePath %>/common/public/jslib/tools.js"></script>
<head>
	<title>�޸��ر��Ա����Ա����</title>
</head>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/account/specialMemberUser/updatePassword.action" targetType="hidden">
		<div class="div_scromin">
			<table border="0" width="80%" align="center" >
				<tr>
					<td>
					<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;�޸��ر��Ա����Ա����</div>
						
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
		<tr height="35">
        	<td align="right" width="35%"> �û����� ��</td>
            <td align="left">
             <input name="obj.userId" type="hidden" value="${modUser.userId }">
            	<input name="userId" type="text" class="input_text_mid" value="${modUser.userId }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> �û����� ��</td>
            <td align="left">
            	<input name="name" type="text" class="input_text_pwd" value="${modUser.name }" readonly="readonly">
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
            <td align="right"> ������ ��</td>
            <td align="left">
            	<input id="password" name="obj.password" type="password" class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
            </td>
        </tr>
        <tr height="35">       
	          <td align="right"> ��ȷ������ ��</td>
	          <td align="left">
	          	<input name="specialforAudit.password1" id="password1" type="password" class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
	          </td>
        </tr>
  	</table>
</td>
</tr>
</table></div>
<div class="tab_pad">
     <table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
          <tr height="35">
          	<td align="center">
          		<button  class="btn_sec" onClick="frmChk()" id="updatePassword">����</button>
            </td>
            <td align="center">
          		<button  class="btn_sec" onClick="window.close()">�ر�</button>
           </td>
          </tr>
     </table>
     </div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function frmChk(){
	var password1=document.getElementById("password").value;
	var password2=document.getElementById("password1").value;
	checkPW(password1,password2);
}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/public/session.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<head>
   <title>�޸��û�����</title>
</head>
<body  class="st_body">
<form name="frm" id="frm" method ="post" action="<%=basePath%>/account/memberUser/updatePassword.action" targetType="hidden">
		<div class="div_scromin">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;�޸��û�����</div>	
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
		<tr height="35">
        	<td align="right" class="td_size" width="38%">�û����룺</td>
            <td align="left" class="td_size">
            <input name="obj.userId" type="hidden" value="${modUser.userId }">
            	<input name="userId" type="text" class="input_text_pwd" value="${modUser.userId }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right" class="td_size">�û����ƣ�</td>
            <td align="left" class="td_size">
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
            <td align="right" class="td_size">�����룺</td>
            <td align="left" class="td_size">
            	<input id="password" name="obj.password" type="password" class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
            </td>
        </tr>
        <tr height="35">       
	          <td align="right" class="td_size">��ȷ�����룺</td>
	          <td align="left" class="td_size">
	          	<input name="specialforAudit.password1" type="password" id="password1" class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
	          </td>
        </tr>
  	</table>
  	</td>
  	</tr>
  	</table>
  	</div>
  	<div class="tab_pad">
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td align="center">
          		<button  class="btn_sec" onclick="updatePasswordUser()" id="updatePassword">����</button>
            </td>
            <td align="center">
          		<button class="btn_sec" onclick="window.close()">�ر�</button>
            </td>
          </tr>
     </table>
     </div>
</form>
</body>
<script type="text/javascript">
function updatePasswordUser()
{ 
	var password1=document.getElementById("password").value;
	var password2=document.getElementById("password1").value;
	checkPW(password1,password2);
	}
</script>
<%@ include file="/public/footInc.jsp"%>







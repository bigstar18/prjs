<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"
			defer="defer"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/broke/manager/updatePassword.action" targetType="hidden">
		<div class="div_scromin">
			<table border="0" width="90%" align="center" >
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;�޸Ŀͻ���������</div>	
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
		<tr height="35">
        	<td align="right" class="td_size" width="35%">�ͻ�������� ��</td>
            <td align="left">
            	<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
            	<input type="hidden" name="obj.parentOrganizationNO" value="${obj.parentOrganizationNO}">
            	<input type="hidden" name="obj.email" value="${obj.email }">
            	<input type="hidden" name="obj.telephone" value="${obj.telephone }">
            	<input type="hidden" name="obj.mobile" value="${obj.mobile }">
            	<input type="hidden" name="obj.address" value="${obj.address}">
            	<input name="obj.managerNo" type="text" class="input_text_pwd" value="${obj.managerNo }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right" class="td_size">�ͻ��������� ��</td>
            <td align="left">
            	<input name="obj.name" type="text" class="input_text_pwd" value="${obj.name }" readonly="readonly">
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
            <td align="right" class="td_size">������ ��</td>
            <td align="left">
            	<input id="password" name="obj.password" type="password" class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
            </td>
        </tr>
        <tr height="35">       
	          <td align="right" class="td_size">��ȷ������ ��</td>
	          <td align="left">
	          	<input name="password1" id="password1" type="password" class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
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
	var password1=frm.password.value;
	var password2=frm.password1.value;
	checkPW(password1,password2);
}
//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
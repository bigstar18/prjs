<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<head>
  <title>�����޸�</title>
</head>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/user/updatePassword.action" targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
					<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;�����޸�</div>			
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
		<tr height="35">
        	<td align="right" class="td_size"> �Ӽ���� ��</td>
            <td align="left" class="td_size">
            <input name="obj.brokerageNo" type="hidden" value="${modUser.brokerageNo }" >
            	<input name="obj.brokerageNo" type="text" class="text" style="width: 180px;" value="${modUser.brokerageNo }" disabled="disabled">
            </td>
        </tr>
        <tr height="35">
            <td align="right"> ԭ���� ��</td>
            <td align="left">
            	<input name="oldPassword" type="password" class="text" style="width: 180px;">&nbsp;<span style="color: red">*</span>
            </td>
        </tr>
        <tr height="35">
            <td align="right" class="td_size"> ������ ��</td>
            <td align="left" class="td_size">
            	<input id="password1" name="password1" type="password" class="text" style="width: 180px;">&nbsp;<span style="color: red">*</span>
            </td>
        </tr>
        <tr height="35">       
	          <td align="right" class="td_size"> ��ȷ������ ��</td>
	          <td align="left" class="td_size">
	          	<input id="password2" name="password2" type="password" class="text" style="width: 180px;">&nbsp;<span style="color: red">*</span>
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
				<button  class="btn_sec" onClick="frmChk()" id="update">����</button>
           </td>
			<td align="center">
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
	var password1=document.getElementById("password1").value;
	var password2=document.getElementById("password2").value;
	checkPW(password1,password2);
}
function checkPW(password1, password2) {
	 var re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;//��֤����ֻ������ĸ������
	if (Trim(password1) == "") {
		alert("���벻��Ϊ�գ�");
		document.getElementById("password1").focus();
		return false;
	} else if (Trim(password1) == "") {
		alert("ȷ�����벻��Ϊ�գ�");
		document.getElementById("password2").focus();
		return false;
	} else if (password1 != password2) {
		alert("������ȷ�����벻һ�£�");
		frm.password1.focus();
		return false;
	} else if (!chkLen(Trim(password1), 6)) {
		alert("���벻��С��6λ��");
		return false;
	} else if (Trim(password1).length>16) {
		alert("���벻�ܴ���16λ��");
		return false;
	}else if (!isStr(password1,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		alert("������������ַ������������룡");
		return false;
	}
	
	else {
		if (userConfirm()) {
			frm.submit();
			//return true;
		} else {
			return false;
		}
	}
}

/**
 * �ж��Ƿ���������ַ�
 * s ����֤���ַ���
 * ch true���ֲ��������ַ�����false ���������ַ���
 * vec �����ַ����飬�����������еĲ��������ַ�
 * true �����������ַ�
 * false ���������ַ�
 */
function isStr(s,ch,vec){
	if(s==""){
		return true;
	}
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec != null){
		for(var i=0;i<vec.length;i++){
			strs += "|\\"+vec[i];
		}
	}
	var matchs='\^[0-9A-Za-z'+china+strs+']{1,}\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
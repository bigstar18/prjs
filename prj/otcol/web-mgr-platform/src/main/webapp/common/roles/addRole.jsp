<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
  <head>
    <base target="_self">
    <title>���ϵͳ��ɫ</title>
    <script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
  </head>
<c:if test="${not empty addSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
  <body>
    <form name="frm" id="frm" action="<%=commonRoleControllerPath %>commonRoleAddForward" method="post" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>���ϵͳ��ɫ</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="80%" align="center">
			  <tr height="35">
            	<td align="right"> ��ɫ���� ��</td>
                <td align="left">
                	<input name="id" type="text" class="text" style="width: 180px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeypress="onlyNumberInput()" maxlength="10"><b><font color=red>&nbsp;*</font></b>
                </td>
        </tr>
			  <tr height="35">
            	<td align="right"> ��ɫ���� ��</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 180px;" onkeypress="onlyNumberAndCharInput()" maxlength="16"><b><font color=red>&nbsp;*</font></b>
                </td>
			  </tr>
              <tr height="35">
                <td align="right"> ��ɫ���� ��</td>
                <td align="left">
                	<textarea name="description" class="normal"></textarea>
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
				  <input name="back" type="button" onclick="javaScript:window.close();" class="btn" value="�ر�">&nbsp;&nbsp;
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
	if(Trim(frm.id.value) == "")
	{
		alert("��ɫ���벻��Ϊ�գ�");
		frm.id.focus();
		return false;
	}
	else if(isNaN(Trim(frm.id.value)))
	{
		alert("��ɫ������Ϊ���֣�");
		frm.id.focus();
		return false;
	}else if (trim(frm.name.value) == ""){
		alert("��ɫ���Ʋ���Ϊ�գ�");
		frm.name.focus();
		return false;
	}
	else 
	{
		var roleidlen = getLength(frm.id.value);
		var rolenamelen = getLength(frm.name.value);
		var descriptionlen = getLength(frm.description.value);
		var mark = true;
		var sign = false;
		
//		if(roleidlen>32 || rolenamelen>32 || descriptionlen>64){
//			mark = false;
//			alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫid�ͽ�ɫ�����Ȳ�����32����ɫ�������Ȳ�����64��");
//		}
		if(roleidlen > 10){
			mark = false;
			alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫid���Ȳ�����10��");
			frm.id.focus();
			return;
		}
		if(rolenamelen > 32 ){
			mark = false;
			alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫ���Ƴ��Ȳ�����16��");
			frm.name.focus();
			return;
		}
		if(descriptionlen > 64){
			mark = false;
			alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫ�������Ȳ�����64��");
			frm.description.focus();
			return;
		}
		if(mark){		
			  if(userConfirm()){
				frm.btn.disabled=true;
				sign = true;
			  }else{
			    return false;
			  }
		}
	//��ֹ�ظ��ύ
	if(sign){
			frm.submit();
		}
	}
}

function getLength(v){
	
	var vlen = 0;
	var str = v.split("");
	for( var a=0 ; a<str.length ; a++)
	{
		if (str[a].charCodeAt(0)<299){ 
			vlen++;
		}else{
			vlen+=2;
		}
	}
	return vlen;
}
//-->
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<body>
<form name="frm" id="frm" method="post" action="<%=commonRoleControllerPath %>commonRoleModForward">
		<fieldset width="100%">
		<legend>�޸�ϵͳ��ɫ</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
			  <tr height="35">
            	<td align="right"> ��ɫ���� ��</td>
                <td align="left">
                	${role.id }<input type="hidden" name="id" value="${role.id }">
                </td>
        </tr>        
	  <tr height="35">
		<td align="right"> ��ɫ���� ��</td>
		<td align="left">
			<input name="name" type="text" class="input_text_mid" value="${role.name }">
		</td>
	  </tr>
	  <tr height="35">
		<td align="right"> ��ɫ���� ��</td>
		<td align="left">
			<textarea name="description" class="normal">${role.description }</textarea>
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
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
		var rolenamelen = getLength(frm.name.value);
		var descriptionlen = getLength(frm.description.value);
		var mark = true;
		var sign = false;
		if(rolenamelen>32 || descriptionlen>64){
			mark = false;
			alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫ�����Ȳ�����32����ɫ�������Ȳ�����64��");
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

//�����ַ�������
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
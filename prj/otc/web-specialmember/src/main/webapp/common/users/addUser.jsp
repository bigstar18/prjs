<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<c:if test="${not empty addSuccess }">
	<script>
		window.returnValue="1";
		window.close();
	</script>
</c:if>
<base target="_self">
<body>
<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
<form name="frm" id="frm" method="post">
		<fieldset width="100%">
		<legend>���ϵͳ�û�</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="70%" align="center">
			  <tr height="35">
            	<td align="right"> �û����� ��</td>
                <td align="left">
                	<input name="userId" onkeyup="value=value.replace(/[\W]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" type="text" class="text" style="width: 180px;"><b><font color=red>&nbsp;*</font></b>
                </td>
        </tr>
        <tr height="35">
            	<td align="right"> ���� ��</td>
                <td align="left">
                	<input name="name" type="text" class="input_text_mid">
                </td>
			  </tr>
        <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
                	<input name="password" type="password" class="input_text_mid"><b><font color=red>&nbsp;*</font></b>
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> ȷ������ ��</td>
                <td align="left">
                	<input name="password1" type="password" class="input_text_mid"><b><font color=red>&nbsp;*</font></b>
                </td>
              </tr>
         <tr height="35">
            	<td align="right"> �Ƿ�����key ��</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)">����
                	<input type="radio" name="enableKey" value="N" checked="checked" onClick="onSelect(this.value)">������
                </td>
         </tr>
			<tr id="showTr" style="display: none">
            	<td align="right"> key ��</td>
                <td align="left">
                    <input ytpe="text" name="keyCode" style="width: 150px;">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">��key</button>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right"> ѡ���� ��</td>
                <td align="left">
                	<select name="skinstyle">
					<option value="">��ѡ��</option>
					<c:forEach items="${skinMap }" var="snMap">
						<option value="${snMap.key }">${snMap.value }</option>
					</c:forEach>
				</select>               	
                </td>
			  </tr>
              <tr height="35">
                <td align="right"> �û����� ��</td>
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
				  <input type="button" name="btn" onclick="frmChk()" class="btn" value="����">&nbsp;&nbsp;
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
	if(Trim(frm.userId.value) == "" || Trim(frm.userId.value).toUpperCase() == "SYSTEM")
	{
		alert("�û�id����Ϊ�ջ��ѱ�ʹ�ã�");
		frm.userId.value="";
		frm.userId.focus();
		return false;
	}
	if(Trim(frm.password.value) == "")
	{
		alert("���벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	}
	if(Trim(frm.password1.value) == "")
	{
		alert("ȷ�����벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	}
	if(!chkLen(Trim(frm.password.value),6))
	{
		alert("���벻��С��6λ,����ʹ��8λ����,�����Կո���Ϊ����,���������룡");
		frm.password.focus();
		return false;
	}
	if(!chkLen(Trim(frm.password1.value),6))
	{
		alert("����ȷ�ϲ���С��6λ,����ʹ����8λ����,�����Կո���Ϊ����,���������룡");
		frm.password1.focus();
		return false;
	}
	if(frm.password1.value != frm.password.value)
	{
		alert("������������벻һ�£�");
		frm.password.focus();
		return false;
	}
	if(frm.enableKey[0].checked && frm.keyCode.value == '')
	{
		alert("������keyֵ��");
		frm.keyCode.focus();
		return false;
	}
	if(!frm.enableKey[0].checked)
	{
		frm.keyCode.value = "0123456789ABCDE";
	}
	

	var useridlen = getLength(frm.userId.value);
	var passwordlen = getLength(frm.password.value);
	var usernamelen = getLength(frm.name.value);
	var descriptionlen = getLength(frm.description.value);
	var mark = true;
	var sign = false;
	if(useridlen>32 || passwordlen>128 || usernamelen>32 || descriptionlen>64){
		mark = false;
		alert("���ύ�����ݳ��ȹ���\n�뱣֤�û�id���û��������Ȳ�����32�����볤�Ȳ�����128����ע���Ȳ�����64��");
	}
	
	if(mark){		
	  if(userConfirm()){
	  	frm.btn.disabled=true;
	  	sign = true;
		//return true;
	  }else{
	    return false;
	  }
	}
	//��ֹ�ظ��ύ
	if(sign){
		frm.action="<%=commonUserControllerPath %>commonUserAddForward";
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
    
    //����keyѡ�� showTr
   function onSelect(value)
		{
		   if(value=='Y')
		   {
		   		document.getElementById("showTr").style.display='';
		   }
		   else
		   {
		     document.getElementById("showTr").style.display='none';
		   }
		}
		
function addKey()
{
	 var str1 = "";
	 var errorCode = 0;
	 var ifInstalled = true;
	 var traderId=frm.userid.value;
	try
	{
		str1 = ePass.VerifyUser(<%=marketId%>,traderId);	
	}
	catch(err)
	{
		ifInstalled = false;														
	}	
	
	if(isNaN(str1))
	{
	}
	else
	{
		ifInstalled = true;
		errorCode = parseInt(str1);
		if(errorCode==-10)
		{
			alert("��Key�����������");	
		}else if (errorCode==-1)
		{
			alert("�����USB�����֤�̣�");	
		}else if (errorCode==-2)
		{
			alert("�Ƿ�USB�����֤�̣�");	
		}else if (errorCode==-3)
		{
			alert("USB�����֤�̲���ȷ");	
		}else if (errorCode==-4)
		{
			alert("USB�����֤���Ѿ��𻵣�����ϵ�����ߣ�");	
		}
	}
	
	if(!ifInstalled)
	{
		alert("�밲װ���׿ؼ��������󶨣�");	
		return false;	
	}
	else
	{	   
		if(errorCode==0)
		{
			frm.keyCode.value = str1;	
		}else
		{
			return false;	
		}			
	}
}
//-->
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="../public/common.jsp"%>
<html>
  <head>
    <title>�޸�ϵͳ�û�</title>
    <script language="JavaScript" src="<c:url value="/common/timebargain/scripts/global.js"/>"></script>
  </head>
  <body>
 <!-- <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT> -->
<form name="frm" id="frm" method="post" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>�޸�ϵͳ�û�</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="70%" align="center">
		  <tr height="35">
           	<td align="right"> �û����� ��</td>
               <td align="left">
               	${user.userId }
               	<input type="hidden" name="userId" value="${user.userId }">
               	<input type="hidden" name="password" value="${user.password }">
               	<input type="hidden" name="skin" value="${user.skin }">
               </td>
         </tr>
         <tr height="35">
			<td align="right"> �û����� ��</td>
			<td align="left">
				<input name="name" type="text" class="text" value="${user.name }" style="width: 180px;" onkeypress="onlyNumberAndCharInput()" maxlength="16">
			</td>
		  </tr>
		  <c:set var="codeValue" value="0123456789ABCDE"/>
		  <%
		  if(!"0123456789ABCDE".equals(((User)request.getAttribute("user")).getKeyCode())){
		  %>
	           <tr height="35">
	            	<td align="right"> �Ƿ�����key ��</td>
	                <td align="left">
	                	<input type="radio" name="enableKey" value="Y" checked="checked" onClick="onSelect(this.value)">����
	                	<input type="radio" name="enableKey" id="noKey" value="N"  onClick="onSelect(this.value)">������
                           <div id="message"></div>
	                </td>
	                
	           </tr>
	  		   <tr id="showTr" style="">
            	<td align="right"> key ��</td>
                <td align="left">
                    <input type="text" name="keyCode" value="${user.keyCode }" style="width: 150px;" onblur="checkKey()">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">��key</button>
                    
                </td>
              </tr>
              <%}else{ %>
         	<tr height="35">
            	<td align="right"> �Ƿ�����key ��</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)">����
                	<input type="radio" name="enableKey" id="noKey" value="N" checked="checked" onClick="onSelect(this.value)">������
                	     <div id="message"></div>
                </td>
              
            </tr>
			<tr id="showTr" style="display: none">
            	<td align="right"> key ��<br></td>
                <td align="left"><input type="text" name="keyCode" style="width: 150px;" onblur="checkKey()">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">��key</button>
                <br></td>
              </tr>
			<%} %>
		  <tr height="35">
			<td align="right"> �û����� ��</td>
			<td align="left">
				<textarea name="description" class="normal">${user.description }</textarea>
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

function checkKey(){
	  if(frm.enableKey[0].checked && frm.keyCode.value == '0123456789ABCDE')
		{
			alert("keyֵ�ظ������������룡");
			if("${user.keyCode }" != "0123456789ABCDE"){
				frm.keyCode.value = "${user.keyCode }";
			}else{
				frm.keyCode.value = "";
			}
			
			frm.keyCode.focus();
			return;
		}
}

function frmChk()
{

	var usernamelen = getLength(frm.name.value);
	var descriptionlen = getLength(frm.description.value);
	var mark = true;
	var sign = false;
	if(frm.enableKey[0].checked && frm.keyCode.value == '')
	{
		alert("������keyֵ��");
		frm.keyCode.focus();
		return false;
	}
	
	if(usernamelen>32){
		mark = false;
		alert("���ύ�����ݳ��ȹ���\n�뱣֤�û��������Ȳ�����32��");
		frm.name.focus();
		return false;
	}
	if(descriptionlen>512){
		mark = false;
		alert("���ύ�����ݳ��ȹ���\n�뱣֤�������Ȳ�����512��");
		frm.description.focus();
		return false;
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
		var message = document.getElementById("message").innerHTML;
	    if(message != ""){
	    	frm.keyCode.value = "0123456789ABCDE";
	    }	
			frm.action = "<%=commonUserControllerPath %>commonUserModForward";
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
		   		
		   		document.getElementById("message").style.display = "none";
		   		document.getElementById("message").innerHTML = "";
		   }
		   else
		   { 
		     document.getElementById("showTr").style.display='none';
		     
		     document.getElementById("message").style.display = "block";
		     document.getElementById("message").innerHTML = "<font color='red'>��key,���ᱻ����</font>";
		   }

		   
		}
		
function addKey()
{
  var str1 = "";
  var errorCode = 0;
  var ifInstalled = true;
  var traderId=frm.userId.value;
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
      alert("USB�����֤�������������");	
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
    alert("�밲װ��ȫ�ؼ���");	
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
<script>
	var varOnLoad = document.body.onload;
	document.body.onload = new Function("proxyLoad()");
	
	function proxyLoad()
	{
		if (varOnLoad)
			varOnLoad();
		window.setTimeout("initForm();", 50);
	}
</script>
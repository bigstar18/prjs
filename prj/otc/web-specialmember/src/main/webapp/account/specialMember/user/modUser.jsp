<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="/common/public/common.jsp"%>

<body style="overflow-y:hidden">
 <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
     <form name="frm" id="frm" method="post" targetType="hidden">
		<div class="div_scromin">
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;����ر��Ա����Ա</div>
			<table border="0" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
		  <tr height="35">
           	<td align="right"> �û����� ��</td>
               <td align="left">
               	${obj.userId }
               	<input type="hidden" id="userid" name="obj.userId" value="${obj.userId}">
               	<input type="hidden" name="obj.password" value="${obj.password}">
               	<input type="hidden" name="obj.skin" value="${obj.skin}">
               </td>
         </tr>
         <tr height="35">
			<td align="right"> �û����� ��</td>
			<td align="left">
				<input name="obj.name" id="name" type="text" class="input_text" value="${obj.name}">
			</td>
		  </tr>
		  <input type="hidden" name="enableKey" id="enableKey" value="N">
		  <!-- 
		  <c:set var="codeValue" value="0123456789ABCDE"/>
		  
		 <%// if(!"0123456789ABCDE".equals(((User)request.getAttribute("obj")).getKeyCode())){
		  %>
	         <tr height="35">
	            	<td align="right"> �Ƿ�����key ��</td>
	                <td align="left">
	                	<input type="radio" name="enableKey" value="Y" checked="checked" onClick="onSelect(this.value)">����
	                	<input type="radio" name="enableKey" value="N" onClick="onSelect(this.value)">������
	                </td>
	  		   <tr id="showTr" style="">
            	<td align="right"> key ��</td>
                <td align="left">
                    <input type="text" name="keyCode" value="${user.keyCode }" style="width: 150px;">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">��key</button>
                </td>
              </tr>
              <%//}else{ %>
         	<tr height="35">
            	<td align="right"> �Ƿ�����key ��</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)">����
                	<input type="radio" name="enableKey" value="N" checked="checked" onClick="onSelect(this.value)">������
                </td>
         </tr>
			<tr id="showTr" style="display: none">
            	<td align="right"> key ��<br></td>
                <td align="left"><input type="text" name="keyCode" value="0123456789ABCDE" style="width: 150px;">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">��key</button>
                <br></td>
              </tr>
			<%//} %>
			 -->
		  <tr height="35">
			<td align="right"> �û���ע ��</td>
			<td align="left">
				<textarea name="obj.description" id="description" class="normal">${obj.description }</textarea>
			</td>
		   </tr>
       </table>
       </td>
       </tr>
       </table>
       </div>
	 <table border="0" cellspacing="0" cellpadding="0" width="100%" class="tab_pad">
		  <tr>
		<td align="center">
			<button  class="btn_sec" onClick="frmChk()" id="update">�޸�</button>
		</td>
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">�ر�</button>
		</td>
	</tr>
	 </table>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function frmChk()
{
	var usernamelen = getLength(document.getElementById("name").value);
	var descriptionlen = getLength(document.getElementById("description").value);
	var mark = true;
	var sign = false;
	//if(frm.enableKey[0].checked && frm.keyCode.value == '')
	//{
	//	alert("������keyֵ��");
	//	frm.keyCode.focus();
	//	return false;
	//}
	if(usernamelen>32 || descriptionlen>64){
		mark = false;
		alert("���ύ�����ݳ��ȹ���\n�뱣֤�û��������Ȳ�����32����ע���Ȳ�����64��");
	}
	
	if(mark){			
		if(userConfirm()){
			sign = true;
		  }else{
			return false;
		  }
	}
	//��ֹ�ظ��ύ
	if(sign){
			frm.action = "<%=basePath%>/account/specialMemberUser/update.action";
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
  var traderId=document.getElementById("userid").value;
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

</SCRIPT>
<%@ include file="/public/footInc.jsp"%>

<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body style="overflow-y:hidden">
 <head>
		<title>�ر��Ա����Ա��Ϣ</title>
	</head>
 <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
     <form name="frm" id="frm" method="post" targetType="hidden">
		<div class="div_scromin">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
					<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp; �ر��Ա����Ա��Ϣ</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
		  <tr height="35">
           	<td align="right" class="td_size" width="40%"> �û����� ��</td>
               <td align="left" class="td_size">
               	${obj.userId }
               	<input type="hidden" id="userid" name="obj.userId" value="${obj.userId}">
               	<input type="hidden" id="userName" name="obj.name" value="${obj.name}">
               	<input type="hidden" name="obj.password" value="${obj.password}">
               	<input type="hidden" name="obj.skin" value="${obj.skin}">
               </td>
         </tr>
         <tr height="35">
			<td align="right" class="td_size"> �û����� ��</td>
			<td align="left" class="td_size">
					${obj.name }
			</td>
		  </tr>
		  <tr height="35">
			<td align="right" class="td_size"> �û���ע ��</td>
			<td align="left" class="td_size">
				${obj.description }<%--<textarea name="obj.description" id="description" class="normal"></textarea>
			--%></td>
		   </tr>
       </table>
       </td>
       </tr>
       </table>
       </div>
       <div class="tab_pad">
	 <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr>
		<!--<td align="center">
			<button  class="btn_sec" onClick="frmChk()" id="update">����</button>
		</td> -->
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">�ر�</button>
		</td>
	</tr>
	 </table>
	 </div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function frmChk()
{
	var mark = true;
	var sign = false;	
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

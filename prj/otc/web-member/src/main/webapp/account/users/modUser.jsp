<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body class="st_body">
<head>
   <title> �޸�ϵͳ�û�</title>
</head>
     <form name="frm" id="frm" method="post" targetType="hidden">
		<div style="overflow:auto;height:250px;">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
					<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;�޸�ϵͳ�û�</div>
		<table border="0" cellspacing="0" cellpadding="0" width="95%" align="center" class="st_bor">
		  <tr height="35">
           	<td align="right" class="td_size" width="20%"> �û����� ��</td>
               <td align="left" colspan="2" width="40%" class="td_size">
               	${obj.userId }
               	<input class="from" type="hidden" id="userid" name="obj.userId" value="${obj.userId}">
               	<input class="from" type="hidden" name="obj.password" value="${obj.password}">
               	<input class="from" type="hidden" name="obj.skin" value="${obj.skin}">
               </td>
         </tr>
         <tr height="35">
			<td align="right" class="td_size" width="20%"> �û����� ��</td>
			<td align="left" width="40%" class="td_size">
				<input class="input_text" name="obj.name" id="name" type="text" 
				onblur="checkName('name');"	onfocus="myfocus('name')"
				class="text" value="${obj.name}"><strong class="check_input">&nbsp;*</strong>
			</td>
			<td style="padding-right: 10px;"><div id="name_vTip">&nbsp;</div></td>
		  </tr>
		  <tr height="35">
			<td align="right" class="td_size"> �Ƿ���� ��</td>
			<td align="left" class="td_size">
				<select name="obj.isForbid" id="isForbid">
					<option value="">��ѡ��</option>
					<option value="Y">����</option>
					<option value="N">������</option>
				</select>
				<script type="text/javascript">
					document.getElementById("isForbid").value = "${obj.isForbid }";
				</script>
			</td>
		   </tr>
		  <input type="hidden" id="keyCode" name="obj.keyCode" value="0123456789ABCDE" style="width: 150px;">
		  <input type="hidden" name="enableKey" value="N" id="enableKey">
		  <c:set var="codeValue" value="0123456789ABCDE"/>
		  <tr height="35">
			<td align="right" class="td_size"> �û����� ��</td>
			<td align="left">
				<textarea name="obj.description" id="description" cols="24" rows="4"
				onblur="myblur('description');"	onfocus="myfocus('description')"
				 class="normal">${obj.description }</textarea>
			</td>
			<td style="padding-right: 10px;"><div id="description_vTip">&nbsp;</div></td>
		   </tr>
		   <tr><td height="5" colspan="3"></td></tr>
       </table>
       </td>
       </tr>
       </table>
       </div>
       <div class="tab_pad">
	 <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr height="35">
			<td align="center" id="tdId">
				<button  class="btn_sec" onClick="updateUser()" id="update">�޸�</button>
           </td>
			<td align="center">
				<button  class="btn_sec" onClick="window.close()">�ر�</button></td>
		  </tr>
	 </table>
	 </div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
var reg =/^\w*DEFAULT_+\w*$/; 
var id='${obj.type}';
var userId='${obj.userId}';
if(id.match(reg)!=null||userId==currenUserId){
	var tableTd=document.getElementById("tdId");
	tableTd.style.display="none";
	var regexTests = document.getElementsByTagName("input");
	var len = regexTests.length;
	for (i = 0; i < len; i++) {
		regexTests[i].readOnly = 'readOnly';
	}
	var regexselect = document.getElementsByTagName("select");
	var len2 = regexselect.length;
	for (i = 0; i < len2; i++) {
		regexselect[i].disabled='disabled';
	}
	var regexText = document.getElementsByTagName("textarea");
	var len3 = regexText.length;
	for (i = 0; i < len3; i++) {
		regexText[i].readOnly='readOnly';
	}
}
	function myblur(userID) {
		var flag = true;
		if ("description" == userID) {
			flag = description(userID);
		} else {
			if (!checkName("name")) {
				flag = false;
			}
			if (!description("description")) {
				flag = false;
			}
		}
		return flag;
	}
	function checkName(userID){
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "����Ϊ��";
		} else {
			if (user.value.length > 32) {
			innerHTML = "����Ӧ������32λ";
			flag = false;
			}else if(!isStr(user.value,true,null)){
			innerHTML = "���в��Ϸ��ַ�";
		}else {
			innerHTML = "";
			flag = true;
				}
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		return flag;
	}
	
	function description(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (user.value.length > 64) {
			innerHTML = "����Ӧ������64λ";
		} else {
			flag = true;
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		return flag;
	}
	
	function myfocus(userID) {
		/*var vTip = document.getElementById(userID + '_vTip');
		var innerHTML = "";
		if ('minRiskFund' == userID) {
			innerHTML = "����Ϊ��";
		}
		vTip.innerHTML = innerHTML;
		vTip.className = "onFocus";*/
	}

function updateUser()
{
	var flag = myblur("all");
	if(!flag){
		return false;
	}
	var usernamelen = getLength(document.getElementById("name").value);
	var descriptionlen = getLength(document.getElementById("description").value);
	var mark = true;
	var sign = false;
	if(usernamelen>32 || descriptionlen>64){
		mark = false;
		alert("���ύ�����ݳ��ȹ���\n�뱣֤�û��������Ȳ�����32����ע���Ȳ�����64��");
	}
	if(frm.name.value == "") {
		alert("��������Ϊ�գ�");
		frm.name.focus();
		return false;
	}
	if(frm.isForbid.value == "") {
		alert("�Ƿ���ò���Ϊ�գ�");
		frm.isForbid.focus();
		return false;
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
			frm.action = "<%=basePath%>/user/update.action";
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

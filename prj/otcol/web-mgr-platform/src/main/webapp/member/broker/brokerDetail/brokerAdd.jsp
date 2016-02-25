<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>

<html>
  <head>
	<title>���ϵͳ�û�</title>
	<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
	<script language="javascript" src="<%=request.getContextPath()%>/common/public/jslib/passwordStrength.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js"></script>
  </head>
  <body>
    <form name=frm id=frm method="post" action="<%=brokerControllerPath %>brokerAdd">
		<fieldset width="100%">
		<legend>���<%=BROKER%></legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
		  
		  <tr height="35">
            	<td align="right"> <%=BROKERID%> ��</td>
                <td align="left">
                	<input name="brokerid" type="text" class="text" style="width: 180px;" onkeypress="onlyNumberAndCharInput()" maxlength="4"><b><font color=red>&nbsp;*</font><b/>
                </td>
          </tr>
          <tr height="35">
                <td align="right"> <%=BROKERFRIM%> ��</td>
                <td align="left">
                	<input id="dealerId" name="firmId" type="text" class="text" style="width: 180px;"  onblur="dealTest();" onkeypress="onlyNumberAndCharInput()" maxlength="16" ><b><font color=red>&nbsp;* <span id="hint"></span></font><b/>
                </td>
        </tr>
         
        <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" onblur="passwordYin(this.value)" onKeyUp="passwordStrength(this.value)" onkeypress="notSpace()"  maxlength="64"><b><font color=red>&nbsp;*</font></b>
                  <div id="passwordPrompt">
                    <div style="width:70px; float:left;">����ǿ�ȣ�</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                  </div>
                  <div id="msg"></div>
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> ȷ������ ��</td>
                <td align="left">
                	<input name="password1" type="password" class="text" style="width: 180px;" onkeypress="notSpace()" maxlength="16"><b><font color=red>&nbsp;*</font><b/>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right"> ���� ��</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 180px;"  maxlength="32"><b><font color=red>&nbsp;*</font><b/>
                </td>
			  </tr>
			  <tr height="35">
                <td align="right"> �г�������Ա��</td>
                <td align="left">
                	<input id="dealerId" name="marketManager" type="text" class="text" style="width: 180px;" maxlength="20" >
                </td>
       		 </tr>
			  <tr height="35">
            	<td align="right"> �绰 ��</td>
                <td align="left">
                	<input name="telephone" type="text" class="text" style="width: 180px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> �ֻ� ��</td>
                <td align="left">
                	<input name="mobile" type="text" class="text" style="width: 180px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> �����ʼ� ��</td>
                <td align="left">
                	<input name="email" type="text" class="text" style="width: 180px;" maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> ����ʡ�� ��</td>
                <td align="left">
                	<input name="locationProvince" type="text" class="text" style="width: 180px;" maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> ��ַ ��</td>
                <td align="left">
                	<input name="address" type="text" class="text" style="width: 180px;" maxlength="32">
                </td>
			  </tr>
              <tr height="35">
                <td align="right"> ��ע ��</td>
                <td align="left">
                	<textarea name="note" class="normal"></textarea>
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
				  <input type="hidden" name="opt">
				  <input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
  			    <input name="back" type="button" onclick="gotoUrl('<%=brokerControllerPath %>brokerList');" class="btn" value="����">
				</div></td>
			  </tr>
		 </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function frmChk()
{
	if(Trim(frm.brokerid.value) == "")
	{
		alert("<%=BROKERID%>����Ϊ�գ�");
		frm.brokerid.focus();
		return false;
	}
	else if(getLength(Trim(frm.brokerid.value))>4)
	{
		alert("�������˺ų��Ȳ��ܴ���4λ��");
		frm.brokerid.focus();
		return false;
	}
	else if(Trim(frm.firmId.value) == "")
	{
		alert("<%=FIRMID%>����Ϊ�գ�");
		frm.firmId.focus();
		return false;
	}
	else if(Trim(frm.password.value) == "")
	{
		alert("���벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(Trim(frm.password.value),8))
	{
		alert("���볤�Ȳ�������8λ���ɰ�����ĸ�����֡��������,���������룡");
		frm.password.focus();
		return false;
	}
	else if(Trim(frm.password1.value) == "")
	{
		alert("ȷ�����벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("������������벻һ�£�");
		frm.password1.focus();
		return false;
	}
	else if(Trim(frm.name.value) == "")
	{
		alert("���Ʋ���Ϊ��! ");
		frm.name.focus();
		return false;
	}
	else if(getLength(Trim(frm.name.value))>30)
	{
		alert("���Ƴ��Ȳ��ܳ���30��");
		frm.name.focus();
		return false;
	}
	if((frm.email.value!="")&&!checkEmail(frm.email))
	{
	  return;
	}
	/*else if(parseFloat(frm.rewardRate.value)<0)
	{
		alert("������Ӷ���������С��0! ");
		frm.rewardRate.focus();
		return false;
	}
	else if(parseFloat(frm.rewardRate.value)>100)
	{
		alert("������Ӷ��������ܴ���100%��");
		frm.rewardRate.focus();
		return false;
	}*/
	
	else 
	{
	  if(userConfirm()){
		frm.submit();
	  }else{
	    return false;
	  }
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
//���ƴ��������û���ѡ��
function checkTradeUser(){
      if(document.frm.tradeUserCheck.checked){
        document.frm.tradeUser.value="true";	
      }else{
        //document.frm.tradeUser.value="0";	
      }
    }
    
function dealTest(){
	var dealerId = $("#dealerId").val();
	$.ajax({
			type:"post",
			url:"<%=brokerControllerPath%>getFirmInfo",
			data:"firmId=" + dealerId,
			success : function(data){
						if(data==1){
						  $("#dealerId").val("");
						  $("#hint").html("�������˺ű����ж�Ӧ�Ľ������˺�");

						}else{
							$("#hint").html("");
						}
			}
		});
}

//-->
</SCRIPT>
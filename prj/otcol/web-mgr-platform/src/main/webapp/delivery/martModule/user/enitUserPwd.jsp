<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
    <base target="_self">  
    
    <title>�޸ĳ�������Ա����</title> 
    
	<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
	<script language="javascript" src="<%=request.getContextPath()%>/common/public/jslib/passwordStrength.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/tools.js"></script>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
		window.returnValue= true;
		window.close();
	</script>
</c:if>
	
  </head>
  
  <body>
    <form id="frm" name="frm" method="POST" action="<%=basePath%>servlet/userController.${POSTFIX}?funcflg=editUserPwd">
	<fieldset width="100%">
	<legend>�޸ĳ�������Ա����</legend>
	    <input type="hidden" id="userId" name="userId" value="${userId }" />
	    <input type="hidden" id="warehouseId" name="warehouseId" value="${warehouseId }" />
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="200">
			<tr>
			  <td align="right">����Ա��ţ�</td>
                <td align="left">${userId }</td>
			</tr>
			<tr>
				<td align="right">���룺</td>
                <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" maxlength="14"  onKeyUp="passwordStrength(this.value)" onblur="passwordYin(this.value)" onkeypress="notSpace()">&nbsp;<font color="red">*</font>
                  <div id="passwordPrompt" >
                    <div style="width:40px; float:left;">ǿ�ȣ�</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                    <div style="clear: both;">���볤��8-14λ����ĸ���ִ�Сд</div>
                  </div>
                  <div id="msg"></div>
                </td>
            </tr>
            <tr>
				<td align="right">�ظ����룺</td>
                <td align="left">
                	<input name="password1" type="password" class="text" style="width: 180px;" maxlength="14" onkeypress="notSpace()" >&nbsp;<font color="red">*</font>
                </td>
              </tr>
              <tr>
                <td colspan="2"><div align="center">
	              <input type="button" value="ȷ��" class="smlbtn" type="button" onclick="frmChk()">&nbsp;&nbsp;
	              <button class="smlbtn" type="button" onclick="window.close()">����</button>
      			</td>
              </tr>
          </table>
	  </fieldset>
    </form>
  </body>
  
</html>
<script type="text/javascript">
  function frmChk(){
    if(frm.name.value == "")
	{
		alert("���Ʋ���Ϊ�գ�");
		frm.name.focus();
		return false;
	}
	else if(frm.password.value == "")
	{
		alert("���벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(frm.password.value,8))
	{
		alert("���볤�Ȳ�������8λ���ɰ�����ĸ�����֡��������,���������룡");
		frm.password.focus();
		return false;
	}
	else if(frm.password1.value == "")
	{
		alert("�ظ����벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("������������벻һ�£�");
		frm.password1.focus();
		return false;
	}
	else{
	    frm.submit();
	}
  }
  </script>
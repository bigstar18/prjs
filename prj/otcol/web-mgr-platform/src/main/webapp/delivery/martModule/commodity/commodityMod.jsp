<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" action="<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityMod" >
		<fieldset width="100%">
		<legend>Ʒ����Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			
			  <tr height="30">
                <td align="right" width="40%">Ʒ�����ƣ�</td>
                <td align="left" width="60%">
                	<select id="id" name="id">
                			<option value="${result.id }">
                				${result.name}
							</option>
                	</select>
                </td>
			  </tr>
			  <!-- 
			   <tr height="30">
                <td align="right" width="40%">��С������:</td>
                <td align="left" width="60%">
                	<input class="text" name="minJS" value="" style="width: 150px;" reqfv="required;��С������">&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">���:</td>
                <td align="left" width="60%">
                	<input class="text" name="yshort" value="" style="width: 150px;" reqfv="required;���">&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">�糤:</td>
                <td align="left" width="60%">
                	<input class="text" name="ylong" value="" style="width: 150px;" reqfv="required;�糤">&nbsp;<font color="red">*</font>
                </td>
			  </tr> 
			   -->
			  <tr height="30">
				<td align="right">������λ��</td>
                <td align="left">
                	<input name="countType" type="text" value="${result.countType }" class="text" style="width: 100px;" reqfv="required;������λ" maxlength="8">&nbsp;<font color="red">*</font>&nbsp;��eg.�֣�
                </td>
			  </tr>
			  </table>
			 <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr height="30">
                <td colspan="2"><div align="center">
				  <button class="smlbtn" type="button" onclick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn()">����</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<script>
	function doSubmit()
	{
		var str = /^[1-9]*[1-9][0-9]*$/;
		var id = frm.id.value;
		if(frm.id.value==""){
			alert('��ѡ��Ʒ��');
			return false��
		}
		if(frm.countType.value==""){
			alert('����д������λ');
			frm.countType.focus;
			return false;
		} 
		frm.submit();
	}
	function freturn(){
		frm.action = "<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityReturn";
		frm.submit();
	}
</script> 

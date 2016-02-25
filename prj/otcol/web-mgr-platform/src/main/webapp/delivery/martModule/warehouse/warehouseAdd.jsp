<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
<form id="frm" name="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseAdd">
	<fieldset width="100%">
	<legend>�ֿ���Ϣ</legend>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		  <tr height="35">
	              <td align="right" width="40%">��ţ�</td>
	              <td align="left" width="60%">
	              	<input class="text" name="id" value="" style="width: 150px;" maxlength="4" onkeypress="onlyNumberAndCharInput()" onkeyup="this.value=this.value.replace(/\W/g,'')" 
	              	 reqfv="required;���">&nbsp;<font color="red">* (��4λ������ĸ���������)</font>
	              </td>
	            </tr>
	            <tr height="35">
	          	<td align="right">��ƣ�</td>
	              <td align="left">
	              	<input name="name" onkeypress="checkChar()" type="text" value="" class="text" style="width: 150px;" reqfv="required;����" maxlength="10">&nbsp;<font color="red">*</font>
	              </td>
	            </tr>
		   <tr height="35">
	          	<td align="right">ȫ�ƣ�</td>
	              <td align="left">
	              	<input name="fullName" type="text" onkeypress="checkChar()" value="" class="text" style="width: 150px;" reqfv="required;����" maxlength="20">&nbsp;<font color="red">*</font>
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">��ַ��</td>
	              <td align="left">
	              	<input name="address" type="text" value="" class="text" style="width: 150px;">
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">�����ˣ�</td> 
	              <td align="left">
	              	<input id="dealerId" name="linkman" type="text" onkeypress="checkChar()" value="" class="text" style="width: 150px;" reqfv="required;������" maxlength="20">&nbsp;<font color="red">*<span id="hint"></span></font>
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">�绰��</td>
	              <td align="left">
	              	<input name="tel" type="text" value="" class="text" style="width: 150px;" maxlength="32" >
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">���棺</td>
	              <td align="left">
	              	<input name="fax" type="text" value="" class="text" style="width: 150px;" maxlength="32">
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">�����ʼ���</td>
	              <td align="left">
	              	<input name="email" type="text" value="" class="text" style="width: 150px;" maxlength="32">
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">�ʱࣺ</td>
	              <td align="left">
	              	<input name="postcode" type="text" value="" class="text" style="width: 150px;" maxlength="6" onkeyup="this.value=this.value.replace(/\D/g,'')" >
	              </td>
	            </tr>
	            <!-- 
		  <tr height="35">
	          	<td align="right">�ܿ��ݣ�</td>
	              <td align="left">
	              	<input name="max_Capacity" type="text" value="" class="text" style="width: 150px;" reqfv="REQ_RANGE;1;0;0;;������">&nbsp;<font color="red">*</font>
	              </td>
	            </tr>
		    <tr height="35">
	          	<td align="right">���ÿ��ݣ�</td>
	              <td align="left">
	              	<input name="used_Capacity" type="text" value="" class="text" style="width: 150px;" reqfv="REQ_RANGE;1;0;0;;���ÿ���">&nbsp;<font color="red">*</font>
	              </td>
	            </tr>
		  <tr height="35">
	          	<td align="right">��Ᵽ֤��</td>
	              <td align="left">
	              	<input name="bail" type="text" value="" class="text" style="width: 150px;" reqfv="req_num;1;0;��Ᵽ֤��">&nbsp;<font color="red">*</font>
	              </td>
	            </tr>
	             -->
	            <tr height="35">
	              <td colspan="2">
	              <div align="center">
	                <button class="smlbtn" type="button" onClick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			  <button class="smlbtn" type="button" onClick="freturn()">����</button>
	              </div>
	              </td>
	      		</tr>
	        </table>
	</fieldset>
</form>
</body>
</html>
<script>
	function doSubmit()
	{
		if(!checkValue("frm"))
			return;
		if(frm.id.value.length!=4){
			alert("�ֿ��ű�����4λ");
			document.frm.id.select();
			document.frm.id.focus();
			return;
		}
		
		if( !/^([a-zA-Z0-9])/.test(frm.id.value)){
			alert("�ֿ�������ĸ���������");
       		document.frm.id.select();
			document.frm.id.focus();
			return;
    	}

		//var maxCap = parseFloat(frm.max_Capacity.value);
		//var useCap = parseFloat(frm.used_Capacity.value);
		//if(maxCap<useCap){
		//	alert("���ÿ��ݲ��ܴ����ܿ��ݣ�");
		//	frm.used_Capacity.select();
		//	frm.used_Capacity.focus();
		//	return;
		//}
		if(confirm("ȷ��ִ�д˲�����")){   
		    frm.submit();	
		}
	}
	function freturn(){
	   
		frm.action = "<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseReturn";
		frm.submit();
	}
     function checkChar(){
			if (event.keyCode == 32)
			{
					event.returnValue=false;
			}
 	 }

 	 
</script> 

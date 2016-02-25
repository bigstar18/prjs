<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
			<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    
}
//save
function save_onclick()
{
		var applyForm = document.forms[0];
		if (applyForm.billid.value == "") {
			alert("�ֵ��Ų���Ϊ�գ�");
			applyForm.billid.focus();
			return false;
		}
		if (applyForm.breedid.value == "") {
			alert("Ʒ�ִ��벻��Ϊ�գ�");
			applyForm.breedid.focus();
			return false;
		}
		if (applyForm.quantity.value == "") {
			alert("�ֵ���������Ϊ�գ�");
			applyForm.quantity.focus();
			return false;
		}
		if (applyForm.quantity.value < 0) {
			alert("�ֵ���������Ϊ������");
			applyForm.quantity.focus();
			return false;
		}
		if (applyForm.firmid.value == "") {
			alert("���������̴��벻��Ϊ�գ�");
			applyForm.firmid.focus();
			return false;
		}
		if (applyForm.quantity.value <= 0) {
			alert("�ֵ�����������������");
			applyForm.quantity.focus();
			return false;
		}
		if(applyForm.remark1.value.length > 128){
			alert("��ע�������!");
			applyForm.remark1.focus();
			return false;
		}
		var billid = applyForm.billid.value;
		var breedid = applyForm.breedid.value;
		var quantity = applyForm.quantity.value;
		var firmid = applyForm.firmid.value;
		if (confirm("��ȷ��Ҫ�ֶ��òֵ��� \n�ֵ��ţ�"+billid+"\nƷ�ִ��룺"+breedid+"\n�ֵ�������"+quantity+"\n���������̴��룺"+firmid+"")) {
			applyForm.submit();
			applyForm.save.disabled = true;
		}
	
  
}


</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
		<table border="0" height="300" width="640" align="center">
			<tr>
				<td>
					<form name =applyForm action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=addGageWarehouse"
						method="POST" class="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  �ֵ��ֶ�
								</b>
							</legend>
							<span id="msg" ></span>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
											�ֵ��ţ�
									</td>
									<td >
										<input type = "text" name = "billid" size="15"  maxlength="16" style="ime-mode:disabled" />
										<span class="req">*</span>
										<input type = "button" value = "��ѯ" onclick= "oneAjax();">
									</td>
									
									<td align="right">
											Ʒ�ִ��룺
									</td>
									<td>
										<input type = "text"  class="ReadOnlyString" name = "breedid" id= "breedid" size="15" readonly/>&nbsp;<span id="breedname">${breedname }</span>
									</td>
								</tr>
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>								
										
								<tr>
									<td align="right">
											�ֵ�������
									</td>
									<td>
										<input type = "text" class="ReadOnlyString"  name = "quantity" id= "quantity" size="15" readonly/>
									</td>
									
									<td align="right">
											���������̴��룺
									</td>
									<td>
										<input type = "text" class="ReadOnlyString"  name = "firmid" id= "firmid"  size="15" readonly/>
									</td>
								</tr>
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	
								
								<tr>
									<td align="right" >
											��ע��
									</td>
									<td colspan="3">
										<textarea name="remark1" rows="3" cols="55"  style="width:450" class="text"></textarea>
									</td>
								</tr>																																					
								<tr >
									<td colspan="4" align="center">
										<input type="button"   onclick="javascript:return save_onclick();" value="�ύ"/>
										<input type="hidden" name="issettle" value="Y" />
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
				
			</tr>
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
<script type="text/javascript"> 
var request = new ActiveXObject("Microsoft.XMLHTTP");
function oneAjax(){
	document.getElementById("msg").innerHTML="";
	var applyForm = document.forms[0];
	var v = applyForm.billid.value;
	request.onreadystatechange = stateChanged;
	request.open("post","getWarehouseData.jsp?billid="+v,false);
	request.send();
	request.abort();
}
function stateChanged() 
{
  if (request.readyState==4){ 
	  var result = request.responseText;
	  var bs = result.split("[]");
	  var tt = bs[1];
	  if(tt=='have'){
	  	var outWarehouse = bs[2];
	  	var str = outWarehouse.split("#");
	  	document.getElementById("breedid").value=str[1];
	  	document.getElementById("quantity").value=str[2];
	  	document.getElementById("firmid").value=str[3];
  		document.getElementById("breedname").innerHTML=str[4];
	  }
	  if(tt=='nohave'){
	  	var outWarehouses = bs[2];
	  	alert("��Ч�Ĳֵ��ţ�");
	  	document.getElementById("billid").value="";
	  	document.getElementById("breedid").value="";
	  	document.getElementById("quantity").value="";
	  	document.getElementById("firmid").value="";
		document.getElementById("breedname").innerHTML="";
	  	//document.getElementById("msg").innerHTML=outWarehouses;
	  }
  }
}
</script>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.*" %>
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
	<%
	String sql = " select * from t_a_breed ";	
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);    
	  System.out.println(list.size());
	
	%>
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
										<input type = "text" name = "billid" size="15" onkeypress="onlyNumberAndCharInput()" maxlength="16"/>
										<span class="req">*</span>
										<!-- <input type = "button" value = "��ѯ" onclick= "oneAjax();"> -->
									</td>
									
									<td align="right">
											Ʒ�ִ��룺
									</td>
									<td>
										<input type = "text"  class="ReadOnlyString" name = "breedid" id= "breedid" style="disabled"  size=10/>
										<select  onchange="selectBreed(this.value)">
										<option value="-1"></option>
									    <%for(int a = 0 ; a < list.size() ; a ++){
    										Map breedMap = (Map)list.get(a); %>
									<option value="<%=breedMap.get("breedid") %>"><%=breedMap.get("breedname")%></option>
									<%} %>
									</select>
									</td>
								</tr>
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>								
										
								<tr>
									<td align="right">
											�ֵ�������
									</td>
									<td>
										<input type = "text" name = "quantity" id= "quantity" size="15" onkeypress="onlyNumberInput()" maxlength="16"/>
									</td>
									
									<td align="right">
											���������̴��룺
									</td>
									<td>
										<input type = "text" name = "firmid" id= "firmid"  size="15" onchange="validateFirm()" onkeypress="onlyNumberAndCharInput()"  maxlength="16"/>
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
										<input type="hidden" name="issettle" value="N" />
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
function selectBreed(breedid){
	document.getElementById("breedid").value=breedid;
}
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
	  }
	  if(tt=='nohave'){
	  	var outWarehouses = bs[2];
	  	alert("�޴˲ֵ���");
	  	//document.getElementById("msg").innerHTML=outWarehouses;
	  }
  }
}
function validateFirm(){

    document.getElementById("msg").innerHTML="";
	var applyForm = document.forms[0];
	var v = applyForm.firmid.value;
	request.onreadystatechange = callback;
	request.open("post","getFirmData.jsp?firmid="+v,false);
	request.send();
	request.abort();
}
function callback(){
   
   if (request.readyState==4){ 
	  var result = request.responseText;
	  var bs = result.split("[]");
	  var tt = bs[1];
	  if(tt=='nohave'){
	     
	      alert("��Ч�Ľ�����");
	      applyForm.firmid.value = "";
	  }
   }

}
</script>
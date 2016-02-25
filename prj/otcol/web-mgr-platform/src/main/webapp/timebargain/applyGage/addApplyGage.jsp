<%@ include file="/timebargain/common/taglibs.jsp"%>


<%@ page pageEncoding="GBK"%>
<%	
basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/timebargain/applyGage/";

%>
<c:if test="${not empty returnMsg }">
	<script type="text/javascript" defer="defer"> 
		alert("<c:out value='${returnMsg }'/>");
    	parent.parent.location.href = "<c:url value="/timebargain/applyGage/applyGage.jsp"/>";
	</script>
</c:if>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
			<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
			<script language="javascript"src="<c:url value="/timebargain/public/jstools/jquery.js"/>"></script>
		<title>
		</title>
<script type="text/javascript" defer="defer"> 
function window_onload()
{
    highlightFormElements();
    
}
//save
function save_onclick()
{
	if (applyGageForm.commodityID.value == "") {
		alert("商品代码不能为空！");
		applyGageForm.commodityID.focus();
		return false;
	}
	if (!isCommodity) {
		alert("请正确填写商品代码！");
		applyGageForm.commodityID.focus();
		return false;
	}
	if (applyGageForm.quantity.value == "") {
		alert("申请数量不能为空！");
		applyGageForm.quantity.focus();
		return false;
	}
	if (applyGageForm.quantity.value < 0) {
		alert("申请数量不能为负数！");
		applyGageForm.quantity.focus();
		return false;
	}
	if (applyGageForm.customerID.value == "") {
		alert("二级代码不能为空！");
		applyGageForm.customerID.focus();
		return false;
	}
	if (!isCustomer) {
		alert("请正确填写二级代码！");
		applyGageForm.customerID.focus();
		return false;
	}
	if (applyGageForm.type && applyGageForm.type.value == "") {
		alert("请选择撤销类型！");
		applyGageForm.customerID_S.focus();
		return false;
	}
	if (applyGageForm.remark1.value.length >= 256) {
		alert("备注不大于256个字符");
		applyGageForm.remark1.focus();
		return false;
	}
	
	if (confirm("您确定要提交吗？")) {
		applyGageForm.submit();
		applyGageForm.save.disabled = true;
	}
  
}

function suffixNamePress(){
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	}else {
		event.returnValue=false;
	}
}
</script>
<script type="text/javascript">
var isCommodity = false;
	var isCustomer = false;
$(function(){
    $("#commodityID").bind("blur",function(){
    	var commodityId = $("#commodityID").val();
    	$.ajax({   
      				  type : "POST",     //HTTP 请求方法,默认: "GET"   
      				  url : "<%=basePath%>valiAjaxByGage.jsp",   //发送请求的地址   
      				  data : "commodityId=" + commodityId, //发送到服务器的数据   
      				  dataType : "xml",         //预期服务器返回的数据类型   
      				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
      				  success : function (data) {   
      				  var commodityResult = $(data).find("commodityResult").text();
      				 	if(commodityResult == 0){
							$("#hint").html("没有对应商品!") ;
							isCommodity = false;  
						}else{
							$("#hint").html("") ;
							isCommodity = true;
						}
		 			}
    		})
	})
    $("#customerID").bind("blur",function(){
    	var customerId = $("#customerID").val();
    	$.ajax({   
      				  type : "POST",     //HTTP 请求方法,默认: "GET"   
      				  url : "<%=basePath%>valiAjaxByGage.jsp",   //发送请求的地址   
      				  data : "customerId="+customerId, //发送到服务器的数据   
      				  dataType : "xml",         //预期服务器返回的数据类型   
      				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
      				  success : function (data) {   
					  var customerResult = $(data).find("customerResult").text();	
						if(customerResult == 0){
							$("#custhint").html("没有对应二级代码!")   
							isCustomer = false;
						}else{
							$("#custhint").html("") ;
							isCustomer = true;
						}
		 			}
    		})
	})
})
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="640" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/applyGage/applyGage.do?funcflg=applyGageSave" method="POST" styleClass="form" >
						<input type="hidden" name="applyType" value="${applyType}">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								<c:choose>
									<c:when test="${applyType == 1 }">
										抵顶
									</c:when>
									<c:when test="${applyType == 2 }">
										撤销抵顶
									</c:when>
								</c:choose>
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
								<tr>
									<td align="right">商品代码：</td>
									<td>
										<input type="text" name="commodityID" id="commodityID" maxlength="10"  style="ime-mode:disabled" />
										<font color="red">*<span id="hint"></span></font>
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>								
								<tr>
									<td align="right">申请数量：</td>
									<td>
										<input type="text" name="quantity" maxlength="10"  style="ime-mode:disabled" onkeypress="return suffixNamePress()" onpaste="return false"/>
										<span class="req">*</span>
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	
								<tr>	
									<td align="right">二级代码：</td>
									<td>
										<input type="text" name="customerID" id="customerID" maxlength="15"  style="ime-mode:disabled" />
										<font color="red">*<span id="custhint"></span></font>
									</td>
								</tr>
								<c:if test="${applyType == 2 }">
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	
									<tr>	
										<td align="right">撤销类型：</td>
										<td>
										<!-- add by yangpei 将单选框名字更改、因为以前叫applyType和上面的变量重名、值错误 -->
											<input type="radio" name="repealType" value="2" style="border:0px;"/>正常撤销
											<input type="radio" name="repealType" value="3" style="border:0px;"/>强制撤销
											<span class="req">*</span>
										</td>
									</tr>
								</c:if>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	
								<tr>
									<td align="right" >备注：</td>
									<td colspan="3">
										<textarea name="remark1" rows="3" cols="55"  style="width:450" styleClass="text" ></textarea>
									</td>
								</tr>																																					
								<tr >
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

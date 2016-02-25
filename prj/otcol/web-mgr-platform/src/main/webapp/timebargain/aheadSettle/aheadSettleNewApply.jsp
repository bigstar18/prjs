<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<c:if test="${not empty returnMsg }">
     <c:if test="${returnMsg=='保存成功！' }">
        <script type="text/javascript" defer="defer"> 
		  alert("<c:out value='${returnMsg }'/>");
		  parent.parent.mainFrame.location.href = "<c:url value="/timebargain/aheadSettle/aheadSettleApply.jsp"/>";
	    </script>
    </c:if>
     <c:if test="${returnMsg!='保存成功！' }">
        <script type="text/javascript" defer="defer"> 
		  alert("<c:out value='${returnMsg }'/>");
		  parent.parent.mainFrame.ListFrame.location.reload(); 
	    </script>
     </c:if> 
</c:if>
<%	
 basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/timebargain/applyGage/";

%>

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
		
<style type="text/css">
 
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}  
</style>
<script type="text/javascript" defer="defer"> 
function window_onload()
{
    highlightFormElements();
    
}
//save
function save_onclick()
{
	if (aheadSettleForm.customerID_B.value == "") {
		alert("买方二级代码不能为空！");
		aheadSettleForm.customerID_B.focus();
		return false;
	}
	if (aheadSettleForm.customerID_S.value == "") {
		alert("卖方二级代码不能为空！");
		aheadSettleForm.customerID_S.focus();
		return false;
	}
	if (!isCustomer_B) {
		alert("请正确填写客户代码！");
		aheadSettleForm.customerID_B.focus();
		return false;
	}
	if (!isCustomer_S) {
		alert("请正确填写二级代码！");
		aheadSettleForm.customerID_S.focus();
		return false;
	}
	
	if (aheadSettleForm.commodityID.value == "") {
		alert("商品代码不能为空！");
		aheadSettleForm.commodityID.focus();
		return false;
	}
	if (!isCommodity) {
		alert("请正确填写商品代码！");
		aheadSettleForm.commodityID.focus();
		return false;
	}
	
	if (aheadSettleForm.price.value == "") {
		alert("提前交收价格不能为空！");
		aheadSettleForm.price.focus();
		return false;
	}else if (aheadSettleForm.price.value.substr(0,1) == "0") {
		if(aheadSettleForm.priceType.value=="1"){
		alert("提前交收价格不能为0,或以0开头！");
		aheadSettleForm.price.focus();
		return false;
		}
	}
	if (aheadSettleForm.price.value < 0) {
		alert("提前交收价格不能为负数！");
		aheadSettleForm.price.focus();
		return false;
	}
	
	if (aheadSettleForm.quantity.value == "") {
		alert("申请数量不能为空！");
		aheadSettleForm.quantity.focus();
		return false;
	}else if(aheadSettleForm.quantity.value == "0"){
	    alert("申请数量不能为0！");
		aheadSettleForm.quantity.focus();
		return false;
	
	}
	if (aheadSettleForm.quantity.value < 0) {
		alert("申请数量不能为负数！");
		aheadSettleForm.quantity.focus();
		return false;
	}
	
	if (aheadSettleForm.gageQty.value == "") {
		alert("卖方抵顶数量不能为空！");
		aheadSettleForm.gageQty.focus();
		return false;
	}
	if (aheadSettleForm.gageQty.value < 0) {
		alert("卖方抵顶数量不能为负数！");
		aheadSettleForm.gageQty.focus();
		return false;
	}
	if(aheadSettleForm.quantity.value-aheadSettleForm.gageQty.value<0){
		alert("卖方抵顶数量不能大于提前交收数量");
		aheadSettleForm.gageQty.focus();
		return false;
	}
	if (confirm("您确定要提交吗？")) {
		aheadSettleForm.submit();
		aheadSettleForm.save.disabled = true;
	}
  
}

function suffixNamePress(){
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	}else {
		event.returnValue=false;
	}
}

function suffixNameAndDotPress()
{
	
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47) 
  {
    event.returnValue=false;
  }
  else
  {
    event.returnValue=true;
  }
}

function priceType_change(type){ 
	var pricetype = type;
	var tdName = document.getElementById("td1s");
	if (pricetype == 1) { 
		document.getElementById("td1s").innerHTML = '交收价格：';
		document.getElementById("td1s").className = "xian";
		document.getElementById("td2s").className = "xian";
		aheadSettleForm.price.value = "";
	}else {
		document.getElementById("td1s").innerHTML = '';
		document.getElementById("td1s").className = "yin";
		document.getElementById("td2s").className = "yin";
		aheadSettleForm.price.value = "0";
		
	}
}

</script>
<script type="text/javascript">
var isCommodity = false;
	var isCustomer_B = false;
	var isCustomer_S = false;
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
      				  var aheadSettlePriceType = $(data).find("aheadSettlePriceType").text();
                       priceType_change(aheadSettlePriceType); 
                       aheadSettleForm.priceType.value=aheadSettlePriceType;
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
    $("#customerID_B").bind("blur",function(){
    	var customerId_B = $("#customerID_B").val();
    	$.ajax({   
      				  type : "POST",     //HTTP 请求方法,默认: "GET"   
      				  url : "<%=basePath%>valiAjaxByGage.jsp",   //发送请求的地址   
      				  data : "customerId="+customerId_B, //发送到服务器的数据   
      				  dataType : "xml",         //预期服务器返回的数据类型   
      				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
      				  success : function (data) {   
					  var customerResult = $(data).find("customerResult").text();	
						if(customerResult == 0){
							$("#custhint_B").html("没有对应二级代码!")   
							isCustomer_B = false;
						}else{
							$("#custhint_B").html("") ;
							isCustomer_B = true;
						}
		 			}
    		})
	})
	
	$("#customerID_S").bind("blur",function(){
    	var customerId_S = $("#customerID_S").val();
    	$.ajax({   
      				  type : "POST",     //HTTP 请求方法,默认: "GET"   
      				  url : "<%=basePath%>valiAjaxByGage.jsp",   //发送请求的地址   
      				  data : "customerId="+customerId_S, //发送到服务器的数据   
      				  dataType : "xml",         //预期服务器返回的数据类型   
      				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
      				  success : function (data) {   
					  var customerResult = $(data).find("customerResult").text();	
						if(customerResult == 0){
							$("#custhint_S").html("没有对应交易商!")   
							isCustomer_S = false;
						}else{
							$("#custhint_S").html("") ;
							isCustomer_S = true;
						}
		 			}
    		})
	})
	
})
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="800" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/aheadSettle/aheadSettleApply.do?funcflg=applyAheadSettle"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>提前交收</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
								<tr>
									<td align="right">买方二级代码：</td>
									<td>
										<input type="text" name="customerID_B" id="customerID_B" maxlength="15"  style="ime-mode:disabled" />
										<font color="red">*<span id="custhint_B"></span></font>
									</td>
									<td align="right">卖方二级代码：</td>
									<td>
										<input type="text" name="customerID_S" id="customerID_S" maxlength="15"  style="ime-mode:disabled" />
										<font color="red">*<span id="custhint_S"></span></font>
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
									<td align="right">商品代码：</td>
									<td>
										<input type="text" name="commodityID" id="commodityID" maxlength="10"  style="ime-mode:disabled" />
										<font color="red">*<span id="hint"></span></font>
									</td>
									<input type=hidden name="priceType" value="0">
									 <td align="right"   id="td1s" class="yin">
											提前交收价格：
									</td>
									<td  id="td2s"   class="yin">
										<html:text property="price" maxlength="15" style="ime-mode:disabled"  onkeypress="suffixNameAndDotPress()"  value="0" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
									<td align="right">提前交收数量：</td>
									<td>
										<html:text property="quantity" maxlength="15" style="ime-mode:disabled"  onkeypress="return suffixNamePress()"/>
										<span class="req">*</span>
									</td>
									<td align="right">其中卖方抵顶数量：</td>
									<td>
										<html:text property="gageQty" maxlength="15" style="ime-mode:disabled"  value="0" onkeypress="return suffixNamePress()"/>
										<span class="req">*</span>
									</td>
								</tr>	
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
									<td align="right" >备注：</td>
									<td colspan="3">
										<html:textarea property="remark1" rows="3" cols="55"
											style="width:450" styleClass="text"
										 onkeydown="if (this.value.length>=120){event.returnValue=false}" />
									</td>
								</tr>																																					
								<tr>
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

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    setReadOnly(applyForm.billID);
    setReadOnly(applyForm.commodityID);
    
    setReadOnly(applyForm.quantity);
    if (applyForm.billType.value == "3") {
    	setReadWrite(applyForm.customerID_S);
    }else {
    	setReadOnly(applyForm.customerID_S);
    }
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (applyForm.commodityID.value == "") {
			alert("商品代码不能为空！");
			applyForm.commodityID.focus();
			return false;
		}
		
		if (applyForm.billID.value == "") {
			alert("仓单号不能为空！");
			applyForm.billID.focus();
			return false;
		}
		if (applyForm.quantity.value == "") {
			alert("仓单数量不能为空！");
			applyForm.quantity.focus();
			return false;
		}
		
		if (applyForm.price.value == "") {
			alert("提前交收价格不能为空！");
		}
		if (applyForm.customerID_B.value == "") {
			alert("买方二级代码不能为空！");
		}
		
		if (applyForm.customerID_S.value == "") {
			alert("卖方二级代码不能为空！");
		}
		applyForm.submit();
		applyForm.save.disabled = true;
	}
  
}

function suffixNamePress(){
	if ((event.keyCode>=46 && event.keyCode<=57) && applyForm.quantity.value > 0) {
		event.returnValue=true;
	}else {
		event.returnValue=false;
	}
}

function cancle_onclick(){
	document.location.href = "<c:url value="/timebargain/menu/liveInfo.do"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="640" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/apply/apply.do?funcflg=applySaveLiveInfo"
						method="POST" styleClass="form">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  抵顶转提前交收
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
											仓单号：
									</td>
									<td>
										<html:text property="billID" maxlength="15"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									
									<td align="right">
											商品代码：
									</td>
									<td>
										<html:text property="commodityID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
								</tr>	
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									
								<tr>
									<td align="right">
											提前交收价格：
									</td>
									<td>
										<html:text property="price" maxlength="15" style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td align="right">
											仓单数量：
									</td>
									<td>
										<html:text property="quantity" maxlength="15"  style="ime-mode:disabled" onkeypress="return suffixNamePress()"/>
										<span class="req">*</span>
									</td>
									
									
									
								</tr>	
									
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>							
							
								<tr>
									<td align="right">
											买方二级代码：
									</td>
									<td>
										<html:text property="customerID_B" maxlength="15" style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td align="right">
											卖方二级代码：
									</td>
									<td>
										<html:text property="customerID_S" maxlength="15" style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											备注：
									</td>
									<td colspan="3">
										<html:textarea property="remark1" rows="3" cols="55"  style="width:450" styleClass="text" />
										
									</td>
								</tr>
																																												
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancle" styleClass="button"
											onclick="javascript:return cancle_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
						<html:hidden property="applyID"/>
						<html:hidden property="billType"/>
					</html:form>
					
					
					
					
				</td>
				
			</tr>
		</table>
		
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
		<script type="text/javascript">
			<%
				if (request.getAttribute("applyNewRelForm1") != null) {
			%>
				parent.TopFrame2.applyForm.query.disabled = false;
				parent.TopFrame2.wait.style.visibility = "hidden";
			<%		
				}
			%>
		</script>
	</body>
</html>

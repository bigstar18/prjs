<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<title>
		审核协议交收
		</title>
		<script type="text/javascript"> 
function window_onload()
{
}

function cancel_onclick()
{
   window.close();
}

function relase()
{
	    pledgeForm.query.disabled = false;
	  	pledgeForm.query1.disabled = false;
}

function ok_onclick(){
	if (confirm("您确定要提交吗？")) {
		pledgeForm.status.value = "2";//2为通过
		pledgeForm.submit();
	  	pledgeForm.query.disabled = true;  
	  	pledgeForm.query1.disabled = true;
	}
	
	//window.close();
}

function fail_onclick(){
	if (confirm("您确定要提交吗？")) {
		pledgeForm.status.value = "3";//3为不通过
		pledgeForm.submit();
	  	pledgeForm.query.disabled = true;
	  	pledgeForm.query1.disabled = true;
	}
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" onload="window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="200" width="380" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form id="pledgeForm" action="${basePath}/timebargain/agreementSettle/agreementCheck.action"
						method="POST" class="form">
						<input type="hidden" id="status" name="status" value="${entity.status}"/>
						<input type="hidden" id="applyID" name="applyID" value="${entity.applyID}"/>
						<input type="hidden" id="commodityID" name="commodityID" value="${entity.commodityID}"/>
						<input type="hidden" id="customerID_B" name="customerID_B" value="${entity.customerID_B}"/>
						<input type="hidden" id="customerID_S" name="customerID_S" value="${entity.customerID_S}"/>
						<input type="hidden" id="price" name="price" value="${entity.price}"/>
						<input type="hidden" id="quantity" name="quantity" value="${entity.quantity}"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>协议交收信息</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="5" border="5" class="common">
								<tr>
									<td width="80" align="right">商品代码：</td>
									<td width="80">
										${entity.commodityID}
									</td>
									<td width="80" align="right">审核状态：</td>
									<td width="80">
										<c:forEach items="${Agreement_statusMap}" var="result">
											<c:if test="${entity.status==result.key }">${result.value}</c:if>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td width="80" align="right">买二级代码：</td>
									<td>
										${entity.customerID_B}
									</td>
									<td width="80" align="right">卖二级代码：</td>
									<td>
										${entity.customerID_S}
									</td>
								</tr>
								<tr>
									<td width="80" align="right">转让价格：</td>
									<td width="80">
										${entity.price}
									</td>
									<td width="80" align="right">转让数量：</td>
									<td width="80">
										${entity.quantity}
									</td>
								</tr>
								
								<tr>
						            <td align="center" colspan="4">
						                <c:if test="${entity.status == 1}">
										<input type="button" name="query" value="通过" style="width:60" class="anniu_btn"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="不通过" style="width:60" class="anniu_btn"
											onclick="javascript:return fail_onclick();"/>
										</c:if>	
										<input type="button" name="close" value="关闭" class="anniu_btn"
											onclick="javascript:return cancel_onclick();"/>	
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
						<!--  input type="hidden" name="firmID" value=""/>
						<input type="hidden" name="id" value=""/>
						<input type="hidden" name="status" value=""/>
						<input type="hidden" name="billFund" value=""/>
						-->
					</form>		
				</td>
			</tr>
		</table>

	</body>
</html>

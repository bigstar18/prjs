<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<title>
		审核非交易过户
		</title>
		<script type="text/javascript"> 
function window_onload()
{
/*
    if (pledgeForm.status.value == "1") {
    	setReadWrite(pledgeForm.note);
    }else {
    	setReadOnly(pledgeForm.note);
    }*/
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
		pledgeForm.status.value = "1";//1为通过
		pledgeForm.submit();
	  	pledgeForm.query.disabled = true;  
	  	pledgeForm.query1.disabled = true;
	}
	
	//window.close();
}

function fail_onclick(){
	if (confirm("您确定要提交吗？")) {
		pledgeForm.status.value = "2";//2为不通过
		pledgeForm.submit();
	  	pledgeForm.query.disabled = true;
	  	pledgeForm.query1.disabled = true;
	}
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" onload="window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="200" width="420" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form id="pledgeForm" action="${basePath}/timebargain/transfer/transferCheck.action"
						method="POST" class="form">
						<input type="hidden" id="transferID" name="transferID" value="${entity.transferID}"/>
						<input type="hidden" id="customerID_s" name="customerID_s" value="${entity.customerID_s}"/>
						<input type="hidden" id="customerID_b" name="customerID_b" value="${entity.customerID_b}"/>
						<input type="hidden" id="commodityID" name="commodityID" value="${entity.commodityID}"/>
						<input type="hidden" id="bs_flag" name="bs_flag" value="${entity.bs_flag}"/>
						<input type="hidden" id="quantity" name="quantity" value="${entity.quantity}"/>
						<input type="hidden" id="status" name="status" value="${entity.status}"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>非交易过户信息</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="5" border="5" class="common">
								<tr>
									<td width="100" align="right">过户人二级代码：</td>
									<td>
										${entity.customerID_s}
									</td>
									<td width="100" align="right">接受人二级代码：</td>
									<td>
										${entity.customerID_b}
									</td>
								</tr>
								
								<tr>
									<td width="80" align="right">商品代码：</td>
									<td width="80">
										${entity.commodityID}
									</td>
									<td width="80" align="right">持仓方向：</td>
									<td width="80">
										<c:forEach items="${BS_flagMap}" var="result">
											<c:if test="${entity.bs_flag==result.key }">${result.value }</c:if>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td width="80" align="right">过户数量：</td>
									<td width="80">
										${entity.quantity}
									</td>
									<td width="80" align="right">审核状态：</td>
									<td width="80">
										<c:forEach items="${Transfer_statusMap}" var="result">
											<c:if test="${entity.status==result.key }">${result.value }</c:if>
										</c:forEach>
									</td>
								</tr>
								
								<tr>
						            <td align="center" colspan="4">
						                <c:if test="${entity.status == 0}">
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

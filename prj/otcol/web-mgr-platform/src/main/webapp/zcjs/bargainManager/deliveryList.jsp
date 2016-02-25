<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body onload="initBody('${returnRefresh}')">
		<form name="frm" method="post" action="">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="100%">
				<legend>
					合同基本信息
				</legend>
				<span> <input type="hidden" name="tradeId" id="tradeId"
						value="${hisTrade.tradeNo }">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35">
							<td align="right" class="tdstyle">
								合同号：
							</td>
							<td align="left">
								${hisTrade.tradeNo }
							</td>
							<td align="right" class="tdstyle">
								买方编号：
							</td>
							<td align="left">
								${hisTrade.firmId_B }
							</td>
							<td align="right" class="tdstyle">
								卖方编号：
							</td>
							<td align="left">
								${hisTrade.firmId_S }
							</td>
							<td align="right" class="tdstyle">
								品种编号：
							</td>
							<td align="left">
								${hisTrade.breedId }
							</td>
						</tr>
						<tr height="35">
							<td align="right" class="tdstyle">
								单价：
							</td>
							<td align="left">
								${hisTrade.price }
							</td>
							<td align="right" class="tdstyle">
								成交数量：
							</td>
							<td align="left">
							<fmt:formatNumber value="${hisTrade.quantity }" type="currency" pattern="${sessionScope.quantityDecimalsString }"/>
							</td>
							<td align="right" class="tdstyle">
								成交时间：
							</td>
							<td align="left">
								<fmt:formatDate value="${hisTrade.tradeDate }"
									pattern="M/d/yyyy" />
							</td>
							<td align="right" class="tdstyle">
								结算时间：
							</td>
							<td align="left">
								<fmt:formatDate value="${hisTrade.clearDate }"
									pattern="M/d/yyyy" />
							</td>
						</tr>
					</table> </span>
			</fieldset>
			<br />
			<%@ include file="deliveryTable.jsp"%>
		</form>
	</body>
</html>
<script type="text/javascript">
	<!--有仓库-->
	function checkNumber(weight,frozenWeight,regStockID){
		var initWeight=document.getElementById(regStockID).value;
		if(initWeight>(weight-frozenWeight)){
			alert("配对数量大于仓单剩余数量，请重新输入！");
			document.getElementById(regStockID).value="";
			document.getElementById(regStockID).focus();
		}
	}
	function matchSettle(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=selectFit";
		frm.submit();
	}
	
	function match(frm,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
		alert ( "没有可以操作的数据！" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "请选择需要操作的数据！" );
		return false;
		}
		if(confirm("您确实要操作选中数据吗？"))
		{
		frm.submit();
		}
		else
		{
		return false;
		}
	}
	function back(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=getHisTradelist";
		frm.submit();
	}
	function auditing(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=auditing";
		frm.submit();
	}
	function autoDelivery(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=autoDelivery";
		frm.submit();
	}
	function deleteMatch(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=deleteMatch";
		match(frm,tableList,'delCheck');
	}
	function send(tradeNo){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=send&tradeNo="+tradeNo;
		frm.submit();
	}
	<!--没有仓库-->
	function matchOneSettle(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=matchForward";
		frm.submit();
	}
</script>
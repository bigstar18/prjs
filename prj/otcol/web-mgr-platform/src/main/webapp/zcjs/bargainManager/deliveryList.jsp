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
					��ͬ������Ϣ
				</legend>
				<span> <input type="hidden" name="tradeId" id="tradeId"
						value="${hisTrade.tradeNo }">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35">
							<td align="right" class="tdstyle">
								��ͬ�ţ�
							</td>
							<td align="left">
								${hisTrade.tradeNo }
							</td>
							<td align="right" class="tdstyle">
								�򷽱�ţ�
							</td>
							<td align="left">
								${hisTrade.firmId_B }
							</td>
							<td align="right" class="tdstyle">
								������ţ�
							</td>
							<td align="left">
								${hisTrade.firmId_S }
							</td>
							<td align="right" class="tdstyle">
								Ʒ�ֱ�ţ�
							</td>
							<td align="left">
								${hisTrade.breedId }
							</td>
						</tr>
						<tr height="35">
							<td align="right" class="tdstyle">
								���ۣ�
							</td>
							<td align="left">
								${hisTrade.price }
							</td>
							<td align="right" class="tdstyle">
								�ɽ�������
							</td>
							<td align="left">
							<fmt:formatNumber value="${hisTrade.quantity }" type="currency" pattern="${sessionScope.quantityDecimalsString }"/>
							</td>
							<td align="right" class="tdstyle">
								�ɽ�ʱ�䣺
							</td>
							<td align="left">
								<fmt:formatDate value="${hisTrade.tradeDate }"
									pattern="M/d/yyyy" />
							</td>
							<td align="right" class="tdstyle">
								����ʱ�䣺
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
	<!--�вֿ�-->
	function checkNumber(weight,frozenWeight,regStockID){
		var initWeight=document.getElementById(regStockID).value;
		if(initWeight>(weight-frozenWeight)){
			alert("����������ڲֵ�ʣ�����������������룡");
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
		alert ( "û�п��Բ��������ݣ�" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
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
	<!--û�вֿ�-->
	function matchOneSettle(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=matchForward";
		frm.submit();
	}
</script>
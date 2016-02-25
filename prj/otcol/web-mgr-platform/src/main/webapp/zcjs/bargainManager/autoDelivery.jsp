<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainManagerController.zcjs?funcflg=autoDeliveryFaitch" method="post">
			<fieldset width="100%">
				<legend>
					自主交收设置
				</legend>
					<input type="hidden" name="tradeNo" id="tradeNo" value="${hisTrade.tradeNo }">
					<table border="0" cellspacing="0" cellpadding="0" width="70%" align="center">
						<tr>
							<td align="left" class="tdstyle">
								合同号：
							</td>
							<td align="left">
								${hisTrade.tradeNo }
							</td>
						
							<td align="left" class="tdstyle">
								买方交易商 ：
							</td>
							<td align="left">
								${hisTrade.firmId_B}
							</td>
						</tr>
						<tr>
							<td align="left" class="tdstyle">
								卖方交易商 ：
							</td>
							<td align="left">
								${hisTrade.firmId_S}
							</td>
							
							<td align="left" class="tdstyle">
								自主交收数量：
							</td>
							<td>
								<input type="text" name="quantity" id="quantity" value="${hisTrade.quantity }" readonly="readonly"/>
							</td>
							
						</tr>
						<tr>
							<td align="center" colspan="2">
								<input type="button" onclick="submitTable()"
									class="btn" value="确认">
							</td>
							
							<td align="center" colspan="2">
								<input type="button"
									class="btn" value="返回" onclick="freturn('<c:out value="${hisTrade.tradeNo }"/>')">
							</td>
						</tr>
					</table>
			</fieldset>
		</form>
	</body>
</html>
<script type="text/javascript">
	function submitTable(){
		frm.submit();
	}
	function freturn(tradeid){
		frm.action = "<%=basePath%>bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId="+tradeid;
		frm.submit();
	}
</script>
	
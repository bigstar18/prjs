<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainManagerController.zcjs?funcflg=auditingFaitch" method="post">
			<fieldset width="100%">
				<legend>
					违约设置
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
								违约状态：
							</td>
							<td align="left">
								<select name="type">
									<option value="2">
										买方违约
									</option>
									<option value="3">
										卖方违约
									</option>
									<option value="4">
										双方违约
									</option>
								</select>
							</td>
							</tr>
							<tr>
							<td align="left" class="tdstyle">
								违约数量：
							</td>
							<td>
								<input type="text" name="quantity" id="quantity" value="0"/>
							</td>
							
						</tr>
						<tr>
							<td align="center" colspan="2">
								<input type="button" onclick="submitTable()"
									class="btn" value="确认">
							</td>
							<td align="center" colspan="2">
								<input type="reset"
									class="btn" value="重置">
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
		var type=document.getElementById("type").value;
		var quantity=document.getElementById("quantity").value;
		if(type==null||type==""){
			document.getElementById("type").focus();
			alert("请确认违约状态");
			return false;
		}
		if(quantity==null||quantity==""||quantity==0||isNaN(quantity)){
			document.getElementById("quantity").value="";
			document.getElementById("quantity").focus();
			alert("违约数量必须为数字并且大于0");
			return false;
		}
		frm.submit();
	}
	function freturn(tradeid){
		frm.action = "<%=basePath%>bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId="+tradeid;
		frm.submit();
	}
</script>
	
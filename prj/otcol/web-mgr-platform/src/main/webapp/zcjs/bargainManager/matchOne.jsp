<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainManagerController.zcjs?funcflg=dealMatch" method="post">
			<fieldset width="100%">
				<legend>
					配对设置
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
									总交易数量 :
							</td>
							<td align="left">
								${hisTrade.quantity }
							</td>
							</tr>
							<tr>
							<td align="left" class="tdstyle">
								实际交易数量：
							</td>
							<td>
								<input type="text" name="trueQuantity" id="trueQuantity" value="0"/>
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
		var trueQuantity=document.getElementById("trueQuantity").value;
		if(trueQuantity==null||trueQuantity==""){
			document.getElementById("trueQuantity").value="";
			document.getElementById("trueQuantity").focus();
			alert("实际交易数量不能为空");
			return false;
		}else if(isNaN(trueQuantity)){
			document.getElementById("trueQuantity").value="";
			document.getElementById("trueQuantity").focus();
			alert("实际交易数量必须为数字");
			return false;
		}else if(trueQuantity==0){
			document.getElementById("trueQuantity").value="";
			document.getElementById("trueQuantity").focus();
			alert("实际交易数量必须大于零");
			return false；
		}
		frm.submit();
	}
	function freturn(tradeid){
		frm.action = "<%=basePath%>bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId="+tradeid;
		frm.submit();
	}
</script>
	
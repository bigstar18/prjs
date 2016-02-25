<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body>
		<form name="frm" method="post" action="">
			<fieldset width="100%">
				<legend>
					合同基本信息
				</legend>
				<span>
					<input type="hidden" name="orderField" value="${orderFiled}">
					<input type="hidden" name="orderDesc" value="${orderType}">
					<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
					<input type="hidden" id="pageNo" name="pageNo">
					<input type="hidden" name="quantity" id="quantity" value="${quantity }">
					<input type="hidden" name="tradeId" id="tradeId" value=${tradeId }>
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
								品种名称：
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
								${hisTrade.quantity }
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
			<%@ include file="matchSettleTable.jsp"%>
			<table border="0" cellspacing="0" cellpadding="0" width="80%">
				<tr height="35">
					<td align="center">
						<div>
							<button class="lgrbtn" type="button" onclick="matchSettle();">
								配对处理
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="lgrbtn" type="button" onclick="back()">
								返回
							</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	function checkNumber(weight,frozenWeight,regStockID){
	var initWeight=document.getElementById(regStockID).value;
		if(isNaN(initWeight)){
			alert("配置数量应为数字");
			document.getElementById(regStockID).value="";
		}else {
		   if(parseFloat(initWeight)>accSub(weight,frozenWeight)){
			alert("配对数量大于仓单剩余数量，请重新输入！");
			document.getElementById(regStockID).value="";
			}
		}
	}
	function matchSettle(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=match";
		match(frm,tableList,'delCheck');
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
			var collCheck = tableList.children[1].all.namedItem(checkName);
			for(var i=0;i < collCheck.length;i++ )
			{
				if( collCheck[i].checked == true )
				{
					//alert(""+collCheck[i].value+"");
					var ipv = document.getElementById(collCheck[i].value);
					if(ipv.value == null || ipv.value == ""){
						alert("配对数量不能为空！");
						ipv.focus();
						return false;
					}			
				}
			}
			frm.submit();
		}
		else
		{
		return false;
		}
	}
	function back(tradeid){
		frm.action = "<%=basePath%>bargainManagerController.zcjs?funcflg=getDeliveryList";
		frm.submit();
	}
	//add by yangpei 解决js中浮点数相减无法取精确值的问题
	function accSub(arg1,arg2){
	　　 var r1,r2,m,n;
	　　 try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	　　 try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	　　 m=Math.pow(10,Math.max(r1,r2));
	　　 //last modify by deeka
	　　 //动态控制精度长度
	　　 n=(r1>=r2)?r1:r2;
	　　 return ((arg1*m-arg2*m)/m).toFixed(n);
	}


</script>
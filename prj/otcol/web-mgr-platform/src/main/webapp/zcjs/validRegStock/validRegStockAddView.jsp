<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<form name="frm"
			action="<%=basePath%>regStockController.zcjs?funcflg=AddRegStock"
			method="post">
			<fieldset width="95%">
				<legend>
					有效仓单添加
				</legend>
				<table border="1" cellspacing="0" cellpadding="0" width="40%"
					height="80%" align="center" bordercolor="#ddffee">
					<input type="hidden" name="regStockId" value="${whRegStock.regStockId }">
					<tr>
						<td align="right">注册仓单号：</td>
						<td>${whRegStock.regStockId }</td>
						<td align="right">商品名称：</td>
						<td>${whRegStock.name }</td>
					</tr>
					<tr>
						<td align="right">交易商ID：</td>
						<td align="left">${whRegStock.firmId }</td>
						<td align="right">仓库号：</td>
						<td>${whRegStock.warehouseId }</td>
					</tr>
					<tr>
						<td align="right">
							商品属性：
						</td>
						<td>
							<table>
							<c:forEach items="${propertyList}" var="result">
							<tr>
								<td>
									${result.name }：${result.value }
								</td>
							</tr>
							</c:forEach>
							</table>
						</td>
						<td align="center">
							质量指标：
						</td>
						<td align="left">
							<table>
							<c:forEach items="${qualityList}" var="result">
							<tr>
								<td>
									${result.name }：${result.value }
								</td>
							</tr>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right">
							初始数量：
						</td>
						<td>${whRegStock.initWeight }</td>
						<td align="right">
							仓单数量：
						</td>
						<td>${whRegStock.weight }</td>
					</tr>
					<tr>
						<td align="right">
							交收冻结数量：
						</td>
						<td>${whRegStock.frozenWeight }</td>
						<td align="right">
							仓单类型：
						</td>
						<td>标准仓单</td>
					</tr>
				</table>
				<table border="0" cellspacing="0" cellpadding="0" width="40%" align="center">
					<tr>
						<td align="center">
							<button type="button" class="smlbtn" onclick="doQuery();">
								确定&nbsp;&nbsp;
							</button>
						</td>	
						<td align="center">
							<button type="button" class="smlbtn" onclick="resetForm();">
								返回
							</button>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">

	
	function resetForm(){
	
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=getvalidRegStockList";
		frm.submit();
	}

</SCRIPT>
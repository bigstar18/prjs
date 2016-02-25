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
					��Ч�ֵ����
				</legend>
				<table border="1" cellspacing="0" cellpadding="0" width="40%"
					height="80%" align="center" bordercolor="#ddffee">
					<input type="hidden" name="regStockId" value="${whRegStock.regStockId }">
					<tr>
						<td align="right">ע��ֵ��ţ�</td>
						<td>${whRegStock.regStockId }</td>
						<td align="right">��Ʒ���ƣ�</td>
						<td>${whRegStock.name }</td>
					</tr>
					<tr>
						<td align="right">������ID��</td>
						<td align="left">${whRegStock.firmId }</td>
						<td align="right">�ֿ�ţ�</td>
						<td>${whRegStock.warehouseId }</td>
					</tr>
					<tr>
						<td align="right">
							��Ʒ���ԣ�
						</td>
						<td>
							<table>
							<c:forEach items="${propertyList}" var="result">
							<tr>
								<td>
									${result.name }��${result.value }
								</td>
							</tr>
							</c:forEach>
							</table>
						</td>
						<td align="center">
							����ָ�꣺
						</td>
						<td align="left">
							<table>
							<c:forEach items="${qualityList}" var="result">
							<tr>
								<td>
									${result.name }��${result.value }
								</td>
							</tr>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right">
							��ʼ������
						</td>
						<td>${whRegStock.initWeight }</td>
						<td align="right">
							�ֵ�������
						</td>
						<td>${whRegStock.weight }</td>
					</tr>
					<tr>
						<td align="right">
							���ն���������
						</td>
						<td>${whRegStock.frozenWeight }</td>
						<td align="right">
							�ֵ����ͣ�
						</td>
						<td>��׼�ֵ�</td>
					</tr>
				</table>
				<table border="0" cellspacing="0" cellpadding="0" width="40%" align="center">
					<tr>
						<td align="center">
							<button type="button" class="smlbtn" onclick="doQuery();">
								ȷ��&nbsp;&nbsp;
							</button>
						</td>	
						<td align="center">
							<button type="button" class="smlbtn" onclick="resetForm();">
								����
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
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
					�鿴��Ч�ֵ�����
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="80%" align="center">
					<input type="hidden" name="regStockId" value="${regStockId }">
						<tr>
							<td align="right" width="50%">�ֵ��ţ�&nbsp;&nbsp;</td>
							<td align="left">&nbsp;&nbsp;${validRegstock.regstockId }</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" width="50%">Ʒ������&nbsp;&nbsp;</td>
							<td align="left">&nbsp;&nbsp;${validRegstock.name }</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" width="50%">������&nbsp;&nbsp;</td>
							<td align="left">&nbsp;&nbsp;${validRegstock.quantity }</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" width="50%">״̬��&nbsp;&nbsp;</td>
							<td align="left">&nbsp;
								<c:if test="${validRegstock.status==1 }">δ��</c:if>
								<c:if test="${validRegstock.status==2 }">���ֵ�</c:if>
								<c:if test="${validRegstock.status==3 }">���ն���</c:if>
								<c:if test="${validRegstock.status==5 }">�������</c:if>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" width="50%">���ͣ�&nbsp;&nbsp;</td>
							<td align="left">&nbsp;
								<c:if test="${validRegstock.type==1 }">���ֵ�</c:if>
								<c:if test="${validRegstock.type==2 }">���ն���ֵ�</c:if>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					<c:forEach items="${qualityList}" var="result">
			        	<tr>
			        		<td align="right" width="50%">
			        			${result.name }��&nbsp;&nbsp;
			        		</td>
			        		<td align="left">
			        			&nbsp;&nbsp;${result.value }
			        		</td>
			        	</tr>
			        	<tr><td>&nbsp;</td></tr>
			        </c:forEach>
			        <c:forEach items="${propertyList}" var="result">
			        	<tr>
			        		<td align="right" width="50%">
			        			${result.name }��&nbsp;&nbsp;
			        		</td>
			        		<td align="left">
			        			&nbsp;&nbsp;${result.value }
			        		</td>
			        	</tr>
			        	<tr><td>&nbsp;</td></tr>
			        </c:forEach>
					<tr>
						
							
						<td colspan="2" align="center">
							<button type="button" class="smlbtn" onclick="resetForm();">
								����
							</button>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
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
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<form name="frm"
			method="post">
			<input type="hidden" name="orderField" value="${orderFiled}">
			<input type="hidden" name="orderDesc" value="${orderType}">
			<input type="hidden" name="pageSize"
				value="<c:out value="${pageInfo.pageSize}"/>">
			<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="95%">
				<legend>
					��Ч�ֵ����ѯ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							��Ч�ֵ���&nbsp;
						</td>
						<td align="left">
							<input id="regstockId" name="_regstockId[like]"
								value="<c:out value='${oldParams["regstockId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="left" colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doQuery();">
								��ѯ
							</button>
							&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doreset()">
								����
							</button>
						</td>
					</tr>
				</table>
			</fieldset>
			<%@ include file="validRegStockTable.jsp"%>
			<table border="0" cellspacing="0" cellpadding="0" width="80%">
				<tr height="35">
					<td>
						<div align="center">
							<button class="lgrbtn" type="button" onclick="add()">
								��Ӳֵ�
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="lgrbtn" type="button" onclick="dealNormal()">
								ɾ���ֵ�
							</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function add(){
       frm.action="<%=basePath%>regStockController.zcjs?funcflg=addSaleForward";
		frm.submit();
	}	
	function dealNormal(){
       frm.action="<%=basePath%>regStockController.zcjs?funcflg=deleteSaleRegStock";
		deleteRec(frm,tableList,'delCheck');
	}
	function deleteRec(frm,tableList,checkName)
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
	function doreset(){
		document.getElementById("regstockId").value="";
		frm.submit();
	}
	function fmod(id){
		frm.action = "<%=basePath%>regStockController.zcjs?funcflg=ForwardRegStockNoStockView&regStockId="+id;
		frm.submit();
	}
	function doQuery(){
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=getvalidRegStockList";
		frm.submit();
	}
</SCRIPT>
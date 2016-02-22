<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/common.jsp"%>

<script type="text/javascript"
	src="<%=serverPath%>/public/jslib/xtree.js">
</script>
<html xmlns:MEBS>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title></title>
		<script language="JavaScript">
function clickMenu() {
	var t1 = document.getElementById("bt1");
	t1.className = "top_b2";
	window.parent.frames('leftFrame').location = '<%=basePath%>/report/queryReport/customerReportQuery.action?LOGINID=${LOGINID}&username=${username}';
}
function clickMenu1() {
	var t1 = document.getElementById("bt1");
	t1.className = "top_b2";
	window.parent.frames('leftFrame').location = '<%=basePath%>/report/queryReport/memberReportQuery.action?LOGINID=${LOGINID}&username=${username}';
}
function clickMenu2() {
	var t1 = document.getElementById("bt1");
	t1.className = "top_b2";
	window.parent.frames('leftFrame').location = '<%=basePath%>/report/bankInternalTrans/bankInternalTransReportQuary.action?LOGINID=${LOGINID}&username=${username}';
}
function clickMenu3() {
	var t1 = document.getElementById("bt1");
	t1.className = "top_b2";
	window.parent.frames('leftFrame').location = '<%=basePath%>/report/settlementBankFund/settlementBankFundReportQuery.action?LOGINID=${LOGINID}&username=${username}';
}
</script>
	</head>

	<body>
		<form id="frm_query" action="voucherBaseList.htm" method="post">
			<table width="50%" border="0" cellpadding="4" cellspacing="4">
				<tr>
					<td align="left" style="padding-left: 10px;">
						<c:if test="${type=='C' }">
						<button id="bt1" onclick="javascript:clickMenu();"
							style="width: 110px; font-weight: bolder;" class="top_b2">
							交易客户报表
						</button>&nbsp;
						</c:if>
						<c:if test="${type=='M' }">
						<button id="bt1" onclick="javascript:clickMenu1();"
							style="width: 110px; font-weight: bolder;" class="top_b2">
							会员报表
						</button>&nbsp;
						</c:if>
						
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

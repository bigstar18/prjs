<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
			<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<SCRIPT language="javascript">
		function window_load(){
			//alert(document.getElementById("clearMargin").innerText);
			if (trim(document.getElementById("clearMargin").innerText) == "") {
				document.getElementById("clearMargin").innerText = "0.00";
			}else {
				document.getElementById("clearMargin").innerText = formatFloat(trim(document.getElementById("clearMargin").innerText),2);
			}
			if (trim(document.getElementById("initFunds").innerText) == "") {
				document.getElementById("initFunds").innerText = "0.00";
			}else {
				document.getElementById("initFunds").innerText = formatFloat(trim(document.getElementById("initFunds").innerText),2);
			}
			
			if (trim(document.getElementById("clearFL").innerText) == "") {
				document.getElementById("clearFL").innerText = "0.00";
			}else {
				document.getElementById("clearFL").innerText = formatFloat(trim(document.getElementById("clearFL").innerText),2);
			}
			if (trim(document.getElementById("clearAssure").innerText) == "") {
				document.getElementById("clearAssure").innerText = "0.00";
			}else {
				document.getElementById("clearAssure").innerText = formatFloat(trim(document.getElementById("clearAssure").innerText),2);
			}
			if (trim(document.getElementById("outFunds").innerText) == "") {
				document.getElementById("outFunds").innerText = "0.00";
			}else {
				document.getElementById("outFunds").innerText = formatFloat(trim(document.getElementById("outFunds").innerText),2);
			}
			if (trim(document.getElementById("inFunds").innerText) == "") {
				document.getElementById("inFunds").innerText = "0.00";
			}else {
				document.getElementById("inFunds").innerText = formatFloat(trim(document.getElementById("inFunds").innerText),2);
			}
			if (trim(document.getElementById("runtimeMargin").innerText) == "") {
				document.getElementById("runtimeMargin").innerText = "0.00";
			}else {
				document.getElementById("runtimeMargin").innerText = formatFloat(trim(document.getElementById("runtimeMargin").innerText),2);
			}
			if (trim(document.getElementById("runtimeFL").innerText) == "") {
				document.getElementById("runtimeFL").innerText = "0.00";
			}else {
				document.getElementById("runtimeFL").innerText = formatFloat(trim(document.getElementById("runtimeFL").innerText),2);
			}
			if (trim(document.getElementById("runtimeAssure").innerText) == "") {
				document.getElementById("runtimeAssure").innerText = "0.00";
			}else {
				document.getElementById("runtimeAssure").innerText = formatFloat(trim(document.getElementById("runtimeAssure").innerText),2);
			}
			if (trim(document.getElementById("tradeFee").innerText) == "") {
				document.getElementById("tradeFee").innerText = "0.00";
			}else {
				document.getElementById("tradeFee").innerText = formatFloat(trim(document.getElementById("tradeFee").innerText),2);
			}
			if (trim(document.getElementById("close_PL").innerText) == "") {
				document.getElementById("close_PL").innerText = "0.00";
			}else {
				document.getElementById("close_PL").innerText = formatFloat(trim(document.getElementById("close_PL").innerText),2);
			}
			if (trim(document.getElementById("settleMargin").innerText) == "") {
				document.getElementById("settleMargin").innerText = "0.00";
			}else {
				document.getElementById("settleMargin").innerText = formatFloat(trim(document.getElementById("settleMargin").innerText),2);
			}
			if (trim(document.getElementById("settleFee").innerText) == "") {
				document.getElementById("settleFee").innerText = "0.00";
			}else {
				document.getElementById("settleFee").innerText = formatFloat(trim(document.getElementById("settleFee").innerText),2);
			}
			if (trim(document.getElementById("settle_PL").innerText) == "") {
				document.getElementById("settle_PL").innerText = "0.00";
			}else {
				document.getElementById("settle_PL").innerText = formatFloat(trim(document.getElementById("settle_PL").innerText),2);
			}
			if (trim(document.getElementById("balance").innerText) == "") {
				document.getElementById("balance").innerText = "0.00";
			}else {
				document.getElementById("balance").innerText = formatFloat(trim(document.getElementById("balance").innerText),2);
			}
		}
			
		</SCRIPT>
	</head>

<body leftmargin="0" topmargin="0" onload="window_load()">
	<table border="1" style="BORDER-RIGHT: 1px; BORDER-TOP: 1px; MARGIN: 1px; BORDER-LEFT: 1px; WIDTH: 400px; COLOR: blue; BORDER-BOTTOM: 1px; BORDER-COLLAPSE: collapse; TEXT-ALIGN: center" borderColor="#000000" align="center" cellSpacing="0" cellPadding="0" class="common">
			<caption><b><font size="2px" ><span style="font-size:14px;">资金明细</span></font></b></caption>
<%
  if(request.getAttribute("customerFund") != null)
  {
%>		<c:forEach items="${customerFund}" var="fund">
			<tr >
				<td class="right" align="right" style="width:180">
					日期&nbsp;
				</td>
				<td align="right">
				<font color="red"><fmt:formatDate value="${fund.ClearDate}" pattern="yyyy-MM-dd" /></font>
				
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					交易商ID&nbsp;
				</td>
				<td align="right">
					<font color="red"><c:out value="${fund.FirmID}"/></font>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					期初余额&nbsp;
				</td>
				<td align="right" id="initFunds">
					<c:out value="${fund.InitFunds}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					+上日保证金&nbsp;
				</td>
				<td align="right" id="clearMargin">
					<c:out value="${fund.ClearMargin}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					+上日浮亏&nbsp;
				</td>
				<td align="right" id="clearFL">
					<c:out value="${fund.ClearFL}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-上日担保金&nbsp;
				</td>
				<td align="right" id="clearAssure">
					<c:out value="${fund.ClearAssure}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-出金&nbsp;
				</td>
				<td align="right" id="outFunds">
					<c:out value="${fund.OutFunds}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					+入金&nbsp;
				</td>
				<td align="right" id="inFunds">
					<c:out value="${fund.InFunds}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-当日保证金&nbsp;
				</td>
				<td align="right" id="runtimeMargin">
					<c:out value="${fund.RuntimeMargin}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-当日浮亏&nbsp;
				</td>
				<td align="right" id="runtimeFL">
					<c:out value="${fund.RuntimeFL}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					+当日担保金&nbsp;
				</td>
				<td align="right" id="runtimeAssure">
					<c:out value="${fund.RuntimeAssure}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-交易手续费&nbsp;
				</td>
				<td align="right" id="tradeFee">
					<c:out value="${fund.TradeFee}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					+平仓盈亏&nbsp;
				</td>
				<td align="right" id="close_PL">
					<c:out value="${fund.Close_PL}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-交收货款&nbsp;
				</td>
				<td align="right" id="settleMargin">
					<c:out value="${fund.SettleMargin}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					-交收手续费&nbsp;
				</td>
				<td align="right" id="settleFee">
					<c:out value="${fund.SettleFee}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					+交收盈亏&nbsp;
				</td>
				<td align="right" id="settle_PL">
					<c:out value="${fund.Settle_PL}"/>
				</td>
			</tr>
			<tr>
				<td class="right" align="right" style="width:180">
					期末余额&nbsp;
				</td>
				<td align="right" id="balance">
					<c:out value="${fund.Balance}"/>
				</td>
			</tr>
		</c:forEach>
<%
  }
%>			
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

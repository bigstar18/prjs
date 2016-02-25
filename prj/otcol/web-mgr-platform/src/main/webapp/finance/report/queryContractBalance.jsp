<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>合同结算列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function resetForm(){
			frm_query.beginNo.value = "";
			frm_query.endNo.value = "";
			frm_query.beginDate.value = "";
			frm_query.endDate.value = "";
		}
		function init(){
			if(frm_query.pageSize.value == null || frm_query.pageSize.value == ''){
				frm_query.pageSize.value = '15';
			
			}
		}
		function doQuery(){
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
		function detail(contractNo){
			var url = "<%=basePath%>/report/queryContract.jsp?contractNo="+contractNo;
			openWin(url,"_blank",600,400);
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportContractBalance.spr" method="post">
	  	<input type="hidden" name="orderField" value="balance">
		<input type="hidden" name="orderDesc" value="false">
		<input type="hidden" name="orderField" value="contractNo">
		<input type="hidden" name="orderDesc" value="false">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>合同余额查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="95%" height="35">
				<tr height="35">
					<td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="beginDate" eltName="_voucherDate[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["voucherDate[>=][date]"]}'/>"/>
					</td>
					<td align="right">结束日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="endDate" eltName="_voucherDate[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["voucherDate[<=][date]"]}'/>"/>
					</td>
					<td align="right"><%=CONTRACTNO%>：&nbsp;</td>
					<td align="left">
						<input id="beginNo" name="_contractNo[>=]" value="<c:out value='${oldParams["contractNo[>=]"]}'/>" type=text class="text" style="width: 100px">
						-<input id="endNo" name="_contractNo[<=]" value="<c:out value='${oldParams["contractNo[<=]"]}'/>" type=text class="text" style="width: 100px">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">成交<%=CONTRACTNO%></td>
				<td class="panel_tHead_MB_Curr">借方金额合计</td>
				<td class="panel_tHead_MB_Curr">贷方金额合计</td>
				<td class="panel_tHead_MB_Curr">余额</td>
				<td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contractNo}"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.dSum}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.cSum}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.balance}" pattern="#,##0.00"/></td>
	  			<td class="underLine" align="center"><span style="color:blue;cursor:hand" onclick="detail(<c:out value="${result.contractNo}"/>);">查看详细</span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="5">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="5"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
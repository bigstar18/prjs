<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function resetForm(){
			frm_query.occurDate.value = "<%=nowDate%>";
		}
		function init(){
			if(frm_query.occurDate.value == null || frm_query.occurDate.value == '')
				frm_query.occurDate.value = '<%=nowDate%>';
		}
		function doQuery(){
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
	</script>
  </head>

  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportMarketBalance.spr" method="post">
		<fieldset width="95%">
			<legend>分市场余额查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="60%" height="35">
				<tr height="35">
					<td align="right">结算日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="occurDate" eltName="occurDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${occurDate}'/>"/>
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
				<td class="panel_tHead_MB">分市场ID</td>
				<td class="panel_tHead_MB">分市场名称</td>
				<td class="panel_tHead_MB_Curr2">保证金余额</td>
				<td class="panel_tHead_MB_Curr2">手续费余额</td>
				<td class="panel_tHead_MB_Curr2">总计余额</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:set var="bailsum" value="0"/>
	  		<c:set var="feeSum" value="0"/>
	  		<c:set var="balSum" value="0"/>
	  		<c:forEach items="${resultList}" var="result">
	  		<c:set var="bailsum" value="${bailsum + result.bailB}"/>
	  		<c:set var="feeSum" value="${feeSum + result.feeB}"/>
	  		<c:set var="balSum" value="${balSum + result.balance}"/>
	  		<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.marketId}"/></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.bailB}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.feeB}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.balance}" pattern="#,##0.00"/>&nbsp;</td>
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
				<td class="pager" colspan="2" align="right">合计：</td>
				<td class="pager" align="right"><fmt:formatNumber value="${bailsum}" pattern="#,##0.00"/></td>
				<td class="pager" align="right"><fmt:formatNumber value="${feeSum}" pattern="#,##0.00"/></td>
				<td class="pager" align="right"><fmt:formatNumber value="${balSum}" pattern="#,##0.00"/></td>
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
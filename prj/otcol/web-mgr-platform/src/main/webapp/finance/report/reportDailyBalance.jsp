<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>凭证列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function init(){
			if(frm_query.b_date.value == null || frm_query.b_date.value == ''){
				frm_query.b_date.value = '<%=nowDate%>';
				//doQuery();
			}
			
		} 
		function doQuery(){
			if(frm_query.b_date.value == ""){
				alert("请选择日期");
				return;
			}
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=reportDailyBalance" method="post">
		<fieldset width="95%">
			<legend>结算日报表</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="50%" height="35">
				<tr height="35">
					<td align="right">结算日期：&nbsp;</td>
					<td align="left">
						<!--input id="occurDate" name="_occurDate[=][date]" value="<c:out value='${oldParams["occurDate[=][date]"]}'/>" type=text class="text" style="width: 100px" reqfv="required;日期"-->
						<MEBS:calendar eltID="b_date" eltName="_b_date[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[=][date]"]}'/>"/>
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<!-- add by yangpei 2011-11-22 增加重置功能 -->
		            	<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
		            	<script>
		            		function resetForm(){
		            			frm_query.b_date.value="";
		            		}
		            	</script>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="accountCode"><%=BITCODE%></td>
				<td class="panel_tHead_MB" abbr="Name"><%=BITCODENAME%></td>
				<td class="panel_tHead_MB_Curr" abbr="lastDayBalance">期初余额</td>
				<td class="panel_tHead_MB_Curr" abbr="debitAmount">借方</td>
				<td class="panel_tHead_MB_Curr" abbr="creditAmount">贷方</td>
				<td class="panel_tHead_MB_Curr" abbr="todayBalance">期末余额&nbsp;&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:set var="lastDayBalanceSum" value="0"/>
			<c:set var="todayDebitAmountSum" value="0"/>
			<c:set var="todayCreditAmountSum" value="0"/>
			<c:set var="todayBalanceSum" value="0"/>
	  		<c:forEach items="${debitList}" var="result">
	  			<c:set var="lastDayBalanceSum" value="${lastDayBalanceSum + result.lastDayBalance}"/>
				<c:set var="todayDebitAmountSum" value="${todayDebitAmountSum + result.debitAmount}"/>
				<c:set var="todayCreditAmountSum" value="${todayCreditAmountSum + result.creditAmount}"/>
				<c:set var="todayBalanceSum" value="${todayBalanceSum + result.todayBalance}"/>
		  		<tr height="22" onclick="selectTr();">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine"><c:out value="${result.accountCode}"/></td>
		  			<td class="underLine"><c:out value="${result.Name}"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.lastDayBalance}" pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.debitAmount}" pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.creditAmount}" pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.todayBalance}" pattern="#,##0.00"/></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
	  		</c:forEach>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine"><b>借方合计：</b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${lastDayBalanceSum}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${todayDebitAmountSum}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${todayCreditAmountSum}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${todayBalanceSum}" pattern="#,##0.00"/></b></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		<c:set var="lastDayBalanceSum" value="0"/>
			<c:set var="todayDebitAmountSum" value="0"/>
			<c:set var="todayCreditAmountSum" value="0"/>
			<c:set var="todayBalanceSum" value="0"/>
	  		<c:forEach items="${creditList}" var="result">
	  			<c:set var="lastDayBalanceSum" value="${lastDayBalanceSum + result.lastDayBalance}"/>
				<c:set var="todayDebitAmountSum" value="${todayDebitAmountSum + result.debitAmount}"/>
				<c:set var="todayCreditAmountSum" value="${todayCreditAmountSum + result.creditAmount}"/>
				<c:set var="todayBalanceSum" value="${todayBalanceSum + result.todayBalance}"/>
		  		<tr height="22" onclick="selectTr();">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine"><c:out value="${result.accountCode}"/></td>
		  			<td class="underLine"><c:out value="${result.name}"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.lastDayBalance}" pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.debitAmount}" pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.creditAmount}" pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value="${result.todayBalance}" pattern="#,##0.00"/></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
	  		</c:forEach>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine"><b>贷方合计：</b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${lastDayBalanceSum}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${todayDebitAmountSum}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${todayCreditAmountSum}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr"><b><fmt:formatNumber value="${todayBalanceSum}" pattern="#,##0.00"/></b></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="6">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="6"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
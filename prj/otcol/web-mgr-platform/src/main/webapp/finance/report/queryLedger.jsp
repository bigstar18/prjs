<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>分类帐列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function resetForm(){
			frm_query.accountCode.value = "";
			frm_query.beginDate.value = "<%=nowDate%>";
			frm_query.endDate.value = "<%=nowDate%>";
		}
		function init(){
			if(frm_query.beginDate.value == null || frm_query.beginDate.value == '')
				frm_query.beginDate.value = '<%=nowDate%>';
			if(frm_query.endDate.value == null || frm_query.endDate.value == '')
				frm_query.endDate.value = '<%=nowDate%>';
		}
		function doQuery(){
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
	</script>
  </head>

  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryLedger" method="post">
		<fieldset width="95%">
			<legend>分类帐查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=BITCODE%>：&nbsp;</td>
					<td align="left">
						<input id="accountCode" name="accountCode" value="<c:out value='${accountCode}'/>" type=text class="text" style="width: 100px" reqfv="required;科目代码" onkeypress="onlyNumberInput()" maxlength="16">
					</td>
					<td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<!--input id="beginDate" name="beginDate" value="<c:out value='${beginDate}'/>" type=text class="text" style="width: 100px" reqfv="required;开始日期"-->
						<MEBS:calendar eltID="beginDate" eltName="beginDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${beginDate}'/>"/>
					</td>
					<td align="right">结束日期：&nbsp;</td>
					<td align="left">
						<!--input id="endDate" name="endDate" value="<c:out value='${endDate}'/>" type=text class="text" style="width: 100px" reqfv="required;结束日期"-->
						<MEBS:calendar eltID="endDate" eltName="endDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${endDate}'/>"/>
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
				<td class="panel_tHead_MB">凭证日期</td>
				<td class="panel_tHead_MB">凭证号</td>
				<td class="panel_tHead_MB">凭证摘要</td>
				<td class="panel_tHead_MB">对方科目</td>
				<td class="panel_tHead_MB">对方科目名称</td>
				<td class="panel_tHead_MB"><%=CONTRACTNO%></td>
				<td class="panel_tHead_MB_Curr2">借方</td>
				<td class="panel_tHead_MB_Curr2">贷方</td>
				<td class="panel_tHead_MB_Curr2">期末余额&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">上期转入</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLineCurr2">
	  			<c:choose>
	  				<c:when test='${dCFlag=="D"&&balance>=0||dCFlag=="C"&&balance<=0}'>
	  					<fmt:formatNumber value="${balance}" pattern="#,##0.00"/>
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
	  			</td>
	  			<td class="underLineCurr2">
	  			<c:choose>
	  				<c:when test='${dCFlag=="D"&&balance<=0||dCFlag=="C"&&balance>=0}'>
	  					<fmt:formatNumber value="${balance}" pattern="#,##0.00"/>
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
				</td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${balance}" pattern="#,##0.00"/>&nbsp;<!-- <c:out value="${dCFlagName}"/> --></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		<c:set var="bal" value="${balance}"/>
	  		<c:set var="debit" value="0"/>
	  		<c:set var="credit" value="0"/>
	  		<c:forEach items="${resultList}" var="result">
	  			<c:set var="debit" value="${debit + result.debitAmount}"/>
	  			<c:set var="credit" value="${credit + result.creditAmount}"/>
	  			<c:if test='${dCFlag=="D"}'>
	  				<c:set var="bal" value="${bal + result.debitAmount - result.creditAmount}"></c:set>
	  			</c:if>
	  			<c:if test='${dCFlag=="C"}'>
	  				<c:set var="bal" value="${bal - result.debitAmount + result.creditAmount}"></c:set>
	  			</c:if>
	  		<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value='${result.voucherDate}'/></td>
	  			<td class="underLine"><c:out value="${result.voucherNo}"/></td>
	  			<td class="underLine"><c:out value="${result.summary}"/></td>
	  			<td class="underLine"><c:out value="${result.accountCode}"/></td>
	  			<td class="underLine"><c:out value="${result.accountName}"/></td>
	  			<td class="underLine"><c:out value="${result.contractNo}"/>&nbsp;</td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.debitAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.creditAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${bal}" pattern="#,##0.00"/>&nbsp;<!-- <c:out value="${dCFlagName}"/> --></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="9">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6" align="right">合计：</td>
				<td class="pager" align="right"><fmt:formatNumber value="${debit}" pattern="#,##0.00"/></td>
				<td class="pager" align="right"><fmt:formatNumber value="${credit}" pattern="#,##0.00"/></td>
				<td class="pager" align="right"><fmt:formatNumber value="${bal}" pattern="#,##0.00"/></td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="9"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
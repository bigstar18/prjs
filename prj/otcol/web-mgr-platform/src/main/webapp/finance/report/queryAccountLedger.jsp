<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>科目账列表</title>
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
			if(frm_query.beginDate.value == '')
			{
			   alert("必须填写开始日期");
			   return;
			}
			if(frm_query.endDate.value == '')
			{
			   alert("必须填写结束日期");
			   return;
			}
			if (frm_query.beginDate.value>frm_query.endDate.value) {
				alert("开始日期不能大于结束日期！");
				return;
			}
			frm_query.submit();
		}
	</script>
  </head>

  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryAccountLedger" method="post">
		<fieldset width="95%">
			<legend>分类总帐查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=BITCODE%>：&nbsp;</td>
					<td align="left">
						<input id="accountCode" name="accountCode" value="<c:out value='${accountCode}'/>" type=text class="text" style="width: 100px" reqfv="required;科目代码" 
onkeypress="onlyNumberAndCharInput()" maxlength="20">
					</td>
					<td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<!--input id="beginDate" name="beginDate" value="<c:out value='${beginDate}'/>" type=text class="text" style="width: 100px" reqfv="required;开始日期"-->
						<MEBS:calendar eltID="beginDate" eltName="beginDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${beginDate}'/>" reqfv="required;开始日期"/>
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
  				<td class="panel_tHead_MB"><%=SUMMARYNO%></td>
				<td class="panel_tHead_MB"><%=DUMMARYNAME%></td>
				<td class="panel_tHead_MB" align="right">借方金额</td>
				<td class="panel_tHead_MB" align="right">贷方金额</td>
				<td class="panel_tHead_MB" align="right">余额</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" >&nbsp;</td>
	  			<td class="underLine" ><b>期初金额</b></td>
	  			<td class="underLineCurr2"><b></b>&nbsp;</td>
	  			<td class="underLineCurr2"><b></b>&nbsp;</td>
				<td class="underLineCurr2"><b>
				<c:choose>
	  				<c:when test='${balanceMap!=null}'>
	  					<fmt:formatNumber value="${balanceMap.lastdaybalance}" pattern="#,##0.00"/>
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
				</b></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		<c:set var="bal" value="${balanceMap.lastdaybalance}"/>
	  		<c:set var="debit" value="0"/>
	  		<c:set var="credit" value="0"/>
	  		<c:forEach items="${resultList}" var="result">
	  		<c:set var="debit" value="${debit + result.dAmount}"/>
	  			<c:set var="credit" value="${credit + result.cAmount}"/>
	  			<c:if test='${balanceMap.dCFlag=="D"}'>
	  				<c:set var="bal" value="${bal + result.dAmount - result.cAmount}"></c:set>
	  			</c:if>
	  			<c:if test='${balanceMap.dCFlag=="C"}'>
	  				<c:set var="bal" value="${bal - result.dAmount + result.cAmount}"></c:set>
	  			</c:if>
	  		<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.summaryNo}"/></td>
	  			<td class="underLine"><c:out value="${result.summary}"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.dAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.cAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${bal}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  		<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" >&nbsp;</td>
	  			<td class="underLine"><b>合计</b></td>
	  			<td class="underLineCurr2"><b><fmt:formatNumber value="${debit}" pattern="#,##0.00"/></b></td>
	  			<td class="underLineCurr2"><b><fmt:formatNumber value="${credit}" pattern="#,##0.00"/></b>&nbsp;</td>
				<td class="underLineCurr2">&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		<tr height="22">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" >&nbsp;</td>
	  			<td class="underLine"><b>期末金额</b></td>
	  			<td class="underLineCurr2"><b></b>&nbsp;</td>
	  			<td class="underLineCurr2"><b></b>&nbsp;</td>
				<td class="underLineCurr2"><b>
				<c:choose>
	  				<c:when test='${balanceMap!=null}'>
	  					<fmt:formatNumber value="${balanceMap.todaybalance}" pattern="#,##0.00"/>
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
				</b></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td  class="pager" align="center">&nbsp;</td>
				<td  class="pager" align="center">科目代码：<c:out value="${balanceMap.code}"/></td>
				<td  class="pager" align="center">科目名称：<c:out value="${balanceMap.name}"/></td>
				<td  class="pager" align="center">&nbsp;</td>
				<td  class="pager" align="center">&nbsp;</td>
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
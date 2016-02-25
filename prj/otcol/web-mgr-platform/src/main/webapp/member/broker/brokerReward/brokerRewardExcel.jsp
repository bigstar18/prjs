<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gnnt.MEBS.member.broker.util.SysData"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="gnnt.MEBS.base.query.QueryConditions"%>
<%@page import="gnnt.MEBS.base.query.QueryHelper"%>
<%@page import="gnnt.MEBS.base.query.PageInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
	String title = request.getParameter("title");
	String moduleId = (String)request.getAttribute("moduleId");
	String reportName = "待付佣金";
	SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMdd HHmm");
	String datetime = tempDate.format(new java.util.Date());
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < reportName.length(); i++) {
		char c = reportName.charAt(i);
		if (c >= 0 && c <= 255) {
			sb.append(c);
		}
		byte[] b;
		try {
			b = Character.toString(c).getBytes("utf-8");
		} catch (Exception e) {
			b = new byte[0];
		}
		for (int j = 0; j < b.length; j++) {
			int k = b[j];
			if (k < 0) {
				k += 256;
			}
			sb.append("%" + Integer.toHexString(k).toUpperCase());
		}
	}
	reportName = sb.toString();
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=GBK");
	response.setHeader("Content-disposition",
			"attachment; filename=" +reportName + datetime + ".xls");
%>
<html xmlns:MEBS>
  <head>
	<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
    <title></title> 
  </head>
  
  <body>
  	<form id="frm_query" action="<%=brokerRewardControllerPath%>brokerRewardList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="orderField" value="occurDate">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" value="${pageInfo.pageNo}">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
		        <td class="panel_tHead_MB" align="center" width="126"><%=BROKERID%></td>
  				<td class="panel_tHead_MB" align="center" width="126"><%=MODULEID%></td>
				<td class="panel_tHead_MB" align="center" width="126">发生日</td>
				<td class="panel_tHead_MB" align="center" width="126">已付服务费</td>
				<td class="panel_tHead_MB" align="center" width="126">付款日</td>
				<td class="panel_tHead_MB" align="center" width="126">待付服务费</td>
				<td class="panel_tHead_MB" align="center">&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:set var="sumPaidAmount" value="0"/>
			<c:set var="sumAmount" value="0"/>
			<c:forEach items="${resultList}" var="result">
			<c:set var="sumPaidAmount" value="${sumPaidAmount+result.PaidAmount}"/>
			<c:set var="sumAmount" value="${sumAmount+result.amount}"/>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" align="center">&nbsp;<c:out value="${result.brokerId}"/></td>
	  			<td class="underLine" align="center">
	  				<c:if test="${result.moduleId=='2'}">订单</c:if>
	  				<c:if test="${result.moduleId=='3'}">挂牌</c:if>
	  				<c:if test="${result.moduleId=='4'}">竞价</c:if>
	  			</td>
	  			<td class="underLine" align="center"><fmt:formatDate value="${result.occurDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLineCurr" style = "text-align: center;">&nbsp;<fmt:formatNumber value="${result.PaidAmount}" pattern="#,##0.00"/>&nbsp;</td>
				<td class="underLineCurr" style = "text-align: center;"><fmt:formatDate value="${result.payDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLineCurr" style = "text-align: center;">&nbsp;<fmt:formatNumber value="${result.amount}" pattern="#,##0.00"/>&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" align="center"><b>合计：</b></td>
	  			<td class="underLine" align="center">&nbsp;</td>
	  			<td class="underLine" align="center">&nbsp;</td>
				<td class="underLineCurr" style = "text-align: center;">&nbsp;<fmt:formatNumber value="${sumPaidAmount}" pattern="#,##0.00"/>&nbsp;</td>
				<td class="underLineCurr">&nbsp;</td>
				<td class="underLineCurr" style = "text-align: center;">&nbsp;<fmt:formatNumber value="${sumAmount}" pattern="#,##0.00"/>&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  	</tBody>
	  	<tFoot>
		</tFoot>
		</table>
	</form>
  </body>
</html>


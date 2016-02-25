<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript"> 
//dy_onclick
function dy_onclick1(id)
{
  //alert(id);
  ec.action = "<c:url value="/timebargain/report/report.do?funcflg=printAccount1"/>&clearDate="+id;
  ec.target = "_blank";
  ec.exportTo.value = "pdf";
  ec.submit();   
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listHoldDetail"/>";
}

</script>
</head>
<body leftmargin="2" topmargin="0">

<div id="content">
	<ec:table items="accountList" var="account" 
			action="${pageContext.request.contextPath}/report/report.do?method=listAccount"	
			xlsFileName="accountList.xls" 
			csvFileName="accountList.csv"
			showPrint="true"
			rowsDisplayed="20"
			listWidth="100%"
	>
	
	
		<ec:row>	
			<ec:column property="ClearDate" title="report.account.time" style="text-align:center;">
			<a href="javascript:dy_onclick1('<c:out value="${account.ClearDate}"/>')" title="²鿴"><c:out value="${account.ClearDate}"/></a> 
            </ec:column>
			<ec:column property="brjy" title="report.account.brjy" style="text-align:right;"/>
			<ec:column property="InFunds" title="report.account.InFunds" cell="number" format="quantity" style="text-align:right;"/>
			<ec:column property="OutFunds" title="report.account.OutFunds" cell="currency" style="text-align:right;"/>
			<ec:column property="TradeFee" title="report.account.TradeFee" cell="number" format="quantity" style="text-align:right;"/>
			<ec:column property="Close_PL" title="report.account.Close_PL" cell="currency" style="text-align:right;"/>
			<ec:column property="Balance" title="report.account.Balance" cell="currency" style="text-align:right;"/>			
									
		</ec:row>
	</ec:table>
</div>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("accountList") != null)
  {
%>
    parent.TopFrame.reportForm.query.disabled = false;
    parent.TopFrame.wait.style.visibility = "hidden";
<%
  }
%>
// -->
</script>
</body>

</html>

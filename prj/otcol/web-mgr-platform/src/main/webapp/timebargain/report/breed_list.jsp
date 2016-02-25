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

</head>
<body leftmargin="2" topmargin="0">

<table width="100%"><tr><td>
	<ec:table items="breedList" var="breed" 
			action="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listHoldDetail"	 
			xlsFileName="breedList.xls" 
			csvFileName="breedList.csv"
			showPrint="true"
			rowsDisplayed="20"
			minHeight="300"
			listWidth="100%"
	>
	
	
		<ec:row>	
			<ec:column property="Commodityid" title="商品代码" width="20%" style="text-align:center;"/>
			
			<ec:column property="BuyQuantity" title="买批数" width="20%" cell="number" format="quantity" style="text-align:right;"/>
			<ec:column property="BuyEvenPrice" title="买平均价" width="20%" cell="currency" style="text-align:right;"/>
			<ec:column property="SellQuantity" title="卖批数" width="20%" cell="number" format="quantity" style="text-align:right;"/>
			<ec:column property="SellEvenPrice" title="卖平均价" width="20%" cell="currency" style="text-align:right;"/>			
									
		</ec:row>
	</ec:table>
</td></tr></table>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("breedList") != null)
  {
%>
    parent.TopFrame.reportForm.query.disabled = false;
    parent.TopFrame.reportForm.dy.disabled = false;
    parent.TopFrame.wait.style.visibility = "hidden";
<%
  }
%>
// -->
</script>
</body>

</html>

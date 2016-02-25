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
<table width="100%">
	<tr>
	<td>

<div id="content">
	<ec:table items="cmdtyHoldList" var="cmdtyHold" 
			action="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listCmdtyHold"	
			xlsFileName="cmdtyHoldList.xls" 
			csvFileName="cmdtyHoldList.csv"
			showPrint="true"
			rowsDisplayed="20"
			minHeight="300"
	>
	
	
		<ec:row>	
			<ec:column property="commodityId" title="��Ʒ����" style="text-align:center;"/>
			
			<ec:column property="BuyQuantity" title="������" cell="number" format="quantity" style="text-align:right;"/>
			<ec:column property="BuyEvenPrice" title="��ƽ����" cell="currency" style="text-align:right;"/>
			<ec:column property="SellQuantity" title="������" cell="number" format="quantity" style="text-align:right;"/>
			<ec:column property="SellEvenPrice" title="��ƽ����" cell="currency" style="text-align:right;"/>			
		</ec:row>
	</ec:table>
</div>
		</td>
	</tr>
</table>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("cmdtyHoldList") != null)
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

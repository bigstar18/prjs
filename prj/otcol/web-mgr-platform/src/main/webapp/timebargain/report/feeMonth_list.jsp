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
<body >
<table width="100%">
	<tr>
	<td>

	<ec:table items="feeMonthList" var="feeMonth" 
			action="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listFeeMonth2"	
			xlsFileName="feeMonthList.xls" 
			csvFileName="feeMonthList.csv"
			showPrint="true"
			rowsDisplayed="20"
			listWidth="100%"
			minHeight="300"
	>
	
	
		<ec:row>	
            <ec:column property="FirmID" title="������ID" style="text-align:center;" width="33%"/>
            <ec:column property="FirmName" title="����������" style="text-align:center;" width="33%"/>
			<ec:column property="TradeFee" title="������" cell="currency" calc="total" calcTitle= "�ϼ�"  style="text-align:right;" width="33%"/>					
		</ec:row>
	</ec:table>
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	</td>
	</tr>
</table>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("feeMonthList") != null)
  {
%>
    
    parent.TopFrame.reportForm.query.disabled = false;
    //alert(parent.TopFrame.reportForm);
    parent.TopFrame.reportForm.dy.disabled = false;
    parent.TopFrame.wait.style.visibility = "hidden";
<%
  }
%>
// -->
</script>
</body>

</html>

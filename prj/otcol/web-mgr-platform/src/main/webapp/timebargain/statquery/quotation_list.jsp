<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>

</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="quotationList" var="quotation" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listQuotation"	
			xlsFileName="quotationList.xls" 
			csvFileName="quotationList.csv"
			showPrint="true" 
			doPreload="false" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"	
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"	  
	>
		<ec:row>	
			
			<ec:column property="Commodityid" title="��Ʒ����" width="90" style="text-align:center;"/>
			<ec:column property="price" title="ƽ����" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="yesterBalancePrice" title="���ս����" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="openPrice" title="���м�" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="highPrice" title="��߼�" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="lowPrice" title="��ͼ�" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="curPrice" title="���¼�" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="reserveCount" title="������" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="totalAmount" title="�ܳɽ���" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="spreadNow" title="�ǵ�" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="createTime" title="����ʱ��" cell="date" format="date" width="90" style="text-align:center;"/>
		</ec:row>
	</ec:table>
</td></tr>
</table>	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("quotationList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.statQueryForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
</body>
</html>

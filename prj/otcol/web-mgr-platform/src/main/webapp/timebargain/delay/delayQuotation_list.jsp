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
	<ec:table items="listDelayQuotation" var="quotation" 
			action="${pageContext.request.contextPath}/timebargain/delay/delay.do?funcflg=listDelayQuotation"	
			xlsFileName="delayQuotationList.xls" 
			csvFileName="delayQuotationList.csv"
			showPrint="true" 			
			rowsDisplayed="20"
			listWidth="100%"	
			minHeight="300"  
	>
		<ec:row>	
			
			<ec:column property="Commodityid" title="��Ʒ����" width="90" style="text-align:center;"/>
			<ec:column property="BuySettleQty" title="���뽻���걨��" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="SellSettleQty" title="���������걨��" cell="currency" width="90" style="text-align:right;"/>
			<ec:column property="BuyNeutralQty" title="�����������걨��" cell="number" format="quantity" calc="total" calcTitle= "�ϼ�" width="15%" style="text-align:right;"/>

			<ec:column property="SellNeutralQty" title="�����������걨��" cell="number" format="quantity" calc="total" calcTitle= "�ϼ�" width="15%" style="text-align:right;"/>
			<ec:column property="DelayCleanHoldQty" title="���ھ�������" cell="number" format="quantity" calc="total" calcTitle= "�ϼ�" width="15%" style="text-align:right;"/>
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
  if(request.getAttribute("listDelayQuotation") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.delayForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
</body>
</html>

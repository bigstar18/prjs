<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
�ʽ��������
</title>
<script type="text/javascript"> 
function dy_onclick1(FirmID,date)
{
  //alert(date);
  ec.action = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=editCustomerFund"/>&CustomerID="+FirmID+"&date="+date;
  ec.target = "_blank";
  ec.submit();   
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listCustomerFunds"/>";
  //p("<c:url value="/statquery/statQuery.do?method=editCustomerFund"/>&CustomerID="+CustomerID+"&date="+date,600,700,'no');
}

function customerFunds_table(firmID){
	pTop("<c:url value="/timebargain/statquery/statQuery.do?funcflg=editCustomerFundsTable&firmID="/>" + firmID + "&id=" + Date(),900,700);
}
</script>
</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="customerFundsList" var="customerFunds" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listCustomerFunds"	
			xlsFileName="CustomerFundsList.xls" 
			csvFileName="CustomerFundsList.csv"
			showPrint="true"
			doPreload="false" 	
			rowsDisplayed="20"
			listWidth="100%"
			minHeight="300"	
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"	  
	>
		<ec:row>	
			<ec:column property="FirmID" title="�����̴���" width="80" style="text-align:center;">
				<a href="#" onclick="customerFunds_table('<c:out value="${customerFunds.FirmID}"/>')"><c:out value="${customerFunds.FirmID}"/></a>
            </ec:column>
			<ec:column property="FirmName" title="����������" width="80" style="text-align:center;"/>
			<ec:column property="firmCategoryId" title="���������"   width="160" style="text-align:center;">
				<c:forEach items="${firmCategoryList }" var = "firmCategory">
					<c:if test="${firmCategory.id == customerFunds.firmCategoryId}">
						${firmCategory.name }
					</c:if>
				</c:forEach>
  			</ec:column>
			
			<ec:column property="nowLeaveBalance" title="��ǰ�����ʽ�" cell="currency" calcTitle= "�ϼ�" calc="total" width="120" style="text-align:right;"/>
			
			<ec:column property="RuntimeFL" title="��ǰ����" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="RuntimeMargin" title="��ǰ��֤��" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="RuntimeAssure" title="��ǰ������" cell="currency" calc="total" width="120" style="text-align:right;"/>
					
			
			<ec:column property="LastBalance" title="�����ʽ����" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="ClearFL" title="���ո���" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="ClearMargin" title="���ձ�֤��" cell="currency" calc="total" width="120" style="text-align:right;"/>
			<ec:column property="ClearAssure" title="���յ�����" cell="currency" calc="total" width="120" style="text-align:right;"/>
			
			<ec:column property="TradeFee" title="����������" cell="currency" calc="total" width="120" style="text-align:right;"/>			
			<ec:column property="MaxOverdraft" title="��Ѻ�ʽ�" cell="currency" calc="total" width="120" style="text-align:right;"/>	
			<!--��̨�����������ף����ݲ�ѯ���ʽ����ҳ�棺�����ʽ���������(�����ֶ���ʾ)  -->
			<!--ec:column property="VirtualFunds" title="�����ʽ�" cell="currency" calc="total" width="120" style="text-align:right;"/> -->	
			<ec:column property="close_PL" title="����ת��ӯ��" cell="currency" calc="total" width="120" style="text-align:right;"/>
			
			<%-- <ec:column property="settle_PL" title="���ս���ӯ��" cell="currency" calc="total" width="120" style="text-align:right;"/>--%>
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
  if(request.getAttribute("customerFundsList") != null)
  {
%>	if (parent.TopFrame) {
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

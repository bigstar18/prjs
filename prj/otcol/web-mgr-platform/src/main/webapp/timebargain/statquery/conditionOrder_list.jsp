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
	<ec:table items="orderList" var="order" 
			action="${pageContext.request.contextPath}/timebargain/statquery/conditionOrder.do?funcflg=listConditionOrder"	
			xlsFileName="ConditionOrderList.xls" 
			csvFileName="ConditionOrderList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"			  
			retrieveRowsCallback="limit"    
	        sortRowsCallback="limit"    
	        filterRowsCallback="limit"		  
	>
		<ec:row>	
		    <ec:column property="A_OrderNo" title="��������" width="70" style="text-align:center;"/>
		    <ec:column property="FirmID" title="�����̴���" width="90" style="text-align:center;"/>
		    <ec:column property="firmName" title="����������" width="100" style="text-align:center;"/>
            <ec:column property="CustomerID" title="��������" width="100" style="text-align:center;"/> 
			<ec:column property="commodityID" title="ί����Ʒ����" width="95" style="text-align:center;"/>
			<ec:column property="TraderID" title="����Ա����" width="90" style="text-align:center;"/>
			
			<ec:column property="BS_Flag" title="����" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG" style="text-align:center;"/>
			<ec:column property="OrderType" title="����/ת��" width="80" editTemplate="ecs_t_status1" mappingItem="ORDERTYPE" style="text-align:center;"/>
			<ec:column property="sendStatu" title="ί��״̬" width="100" style="text-align:center;"/>
			<ec:column property="Quantity" title="ί������" cell="number" format="quantity" calc="total" calcTitle= "�ϼ�"  width="65" style="text-align:right;"/>
			<ec:column property="Price" title="ί�м۸�" cell="number"  calc="total" calcTitle= "�ϼ�" width="120" style="text-align:right;"/>
			<ec:column property="ConditionCommodityID" title="������Ʒ����" width="95" style="text-align:center;"/>
			<ec:column property="ctype" title="��������" width="95" style="text-align:center;"/>
			<ec:column property="ConditionOperation" title="����������" editTemplate="ecs_t_status2" mappingItem="CONDITIONOPERATION" width="95" style="text-align:center;"/>
			<ec:column property="ConditionPrice" title="�����۸�" cell="number"  calc="total" calcTitle= "�ϼ�" width="95" style="text-align:right;"/>
			
			<ec:column property="UpdateTime" title="�趨ʱ��" cell="date" format="datetime" width="150" style="text-align:center;"/>		
			<ec:column property="ValidDate" title="��������" cell="date" format="date" width="150" style="text-align:center;"/>	
			<ec:column property="SuccessTime" title="ί��ʱ��" cell="date" format="datetime" width="150" style="text-align:center;"/>

		</ec:row>
	</ec:table>
</td></tr>
</table>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDERTYPE" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CONDITIONOPERATION" />
		</select>
	</textarea>	
	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("orderList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.conditionOrderForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
  </body>
</html>

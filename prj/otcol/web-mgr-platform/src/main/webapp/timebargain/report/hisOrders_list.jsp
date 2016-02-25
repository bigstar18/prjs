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

<div id="content">
<table width="100%">
  <tr><td>
	<ec:table items="hisOrdersList" var="hisOrders" 
			action="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listHisOrders"	
			xlsFileName="hisOrdersList.xls" 
			csvFileName="hisOrdersList.csv"
			showPrint="true"
			rowsDisplayed="20"
			minHeight="300"
			listWidth="100%"
	>
		
		<ec:row>	
            <ec:column property="A_OrderNo" title="ί�е���" width="90" style="text-align:center;"/>
            
            <ec:column property="FirmID" title="������ID" width="90" style="text-align:center;"/>
            <ec:column property="firmName" title="����������" width="90" style="text-align:center;"/>
           
			<ec:column property="commodityid" title="��Ʒ����" width="90" style="text-align:center;"/>
			<ec:column property="Price" title="�۸�"  width="90" style="text-align:right;"/>
			<ec:column property="BS_Flag" title="��/��" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" width="90" style="text-align:center;"/>
			<ec:column property="OrderType" title="ί������" editTemplate="ecs_t_status1" mappingItem="ORDERTYPE" width="90" style="text-align:center;"/>
			<ec:column property="Quantity" title="����" cell="number" format="quantity" width="90" style="text-align:right;"/>
			<ec:column property="OrderTime" title="����ʱ��" cell="date" format="date" width="90" style="text-align:center;"/>
			<ec:column property="WithdrawTime" title="����ʱ��" cell="date" format="date" width="90" style="text-align:center;"/>			
								
		</ec:row>
	</ec:table>
	</td></tr>
</table>	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDERTYPE" />
		</select>
	</textarea>	
</div>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("hisOrdersList") != null)
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

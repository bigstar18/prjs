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
	<ec:table items="listDelayOrders" var="order" 
			action="${pageContext.request.contextPath}/timebargain/delay/delay.do?funcflg=listDelayOrders"	
			xlsFileName="delayOrderList.xls" 
			csvFileName="delayOrderList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"			  
	>
		<ec:row>	
		    <ec:column property="FirmID" title="�����̴���" width="90" style="text-align:center;"/>
            <ec:column property="CustomerID" title="��������" width="100" style="text-align:center;"/>
            <ec:column property="A_OrderNo" title="ί�е���" width="70" style="text-align:center;"/>
       
			<ec:column property="commodityid" title="��Ʒ����" width="95" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="����" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
			<ec:column property="Status" title="״̬" width="100" editTemplate="ecs_t_status2" mappingItem="ORDER_STATUS" style="text-align:center;"/>
			<ec:column property="DelayOrderType" title="ί������" width="100"  mappingItem="DELAYORDERTYPE_STATUS" style="text-align:center;"/>
			<ec:column property="Quantity" title="ί������" cell="number" format="quantity" calc="total" calcTitle= "�ϼ�"  width="65" style="text-align:right;"/>
			<ec:column property="Price" title="ί�м۸�" cell="currency"  width="120" style="text-align:right;"/>
			<ec:column property="TradeQty" title="�ѳɽ�����" cell="number" format="quantity" calc="total" width="90" style="text-align:right;"/>
			<ec:column property="OrderTime" title="ί��ʱ��" cell="date" format="datetime" width="150" style="text-align:center;"/>
			<ec:column property="WithdrawTime" title="����ʱ��" cell="date" format="datetime" width="150" style="text-align:center;"/>			
			<ec:column property="TraderID" title="����Ա����" width="90" style="text-align:center;"/>
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
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDER_STATUS" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status3" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CLOSEMODE" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status4" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TIMEFLAG" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status5" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CLOSEFlAG2" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("listDelayOrders") != null)
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

<script type="text/javascript">
<!--
// -->
</script>
</html>

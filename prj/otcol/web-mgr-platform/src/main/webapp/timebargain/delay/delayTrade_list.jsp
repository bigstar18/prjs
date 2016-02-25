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
	<ec:table items="listDelayTrade" var="trade" 
			  action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listTrade"	
			xlsFileName="delayTradeList.xls" 
			csvFileName="delayTradeList.csv"
			showPrint="true" 		
			listWidth="100%"		
			rowsDisplayed="20"
			minHeight="300"		  
	>
		<ec:row>	
		    <ec:column property="FirmID" title="�����̴���" width="90" style="text-align:center;"/>
            <ec:column property="CustomerID" title="��������" width="100" style="text-align:center;"/>
            <ec:column property="A_TradeNo" title="�ɽ���"  width="75" style="text-align:center;"/>
			<ec:column property="A_OrderNo" title="ί�е���"  width="80" style="text-align:center;"/>
			<ec:column property="TradeTime" title="�ɽ�ʱ��" cell="date" format="datetime" width="150" style="text-align:center;"/>
			<ec:column property="commodityid" title="��Ʒ����" width="90" style="text-align:center;"/>
			<ec:column property="BS_Flag" title="����" width="60" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>									
			<ec:column property="DelayOrderType" title="����ί������" width="90" editTemplate="ecs_t_DelayOrderType" mappingItem="DELAYORDERTYPE" style="text-align:center;"/>
			<ec:column property="Quantity" title="�ɽ�����" cell="number" format="quantity" calc="total" calcTitle= "�ϼ�" width="65" style="text-align:right;"/>								
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
	<textarea id="ecs_t_DelayOrderType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="DelayOrderType" >
			<ec:options items="DELAYORDERTYPE" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADETYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("listDelayTrade") != null)
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

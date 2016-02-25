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
	<ec:table items="dailyMoneyList" var="dailyMoney" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listDailyMoney"	
			xlsFileName="DailyMoneyList.xls" 
			csvFileName="DailyMoneyList.csv"
			showPrint="true" 	
			rowsDisplayed="20"
			listWidth="100%"	
			minHeight="300"		  
	>
		<ec:row>	
            <ec:column property="FundFlowID" title="��ˮ��"  width="90" style="text-align:center;"/>
			<ec:column property="CreateTime" title="��������" cell="date" format="date" width="90" style="text-align:center;"/>
			<ec:column property="FirmID" title="������ID" width="90" style="text-align:center;"/>
			<ec:column property="OprCode" title="ҵ�����" width="90" editTemplate="ecs_t_status" mappingItem="OPERATION" style="text-align:right;"/>	
			<ec:column property="ContractNo" title="ϵͳ�ɽ���"  width="90" style="text-align:center;"/>
			<ec:column property="Amount" title="������" cell="currency" calc="total" calcTitle= "�ϼ�" width="130" style="text-align:right;"/>
			<ec:column property="Balance" title="�ʽ����" cell="currency" calc="total" width="130" style="text-align:right;"/>
			<ec:column property="VoucherNo" title="ƾ֤��"  width="90" style="text-align:center;"/>
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
			<ec:options items="OPERATION" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
  var cll;
  for (var i=0;i<ec_table.rows.length-1;i++) 
  {
   /* cll = ec_table.rows(i).cells(3);
    var operation = cll.innerHTML;
    if (operation == "101") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation101"/>";
    }
    else if (operation == "102") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation102"/>";
    }    
    else if (operation == "103") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation103"/>";
    }
    else if (operation == "104") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation104"/>";
    } 
    else if (operation == "105") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation105"/>";
    }    
    else if (operation == "106") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation106"/>";
    }
    else if (operation == "107") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation107"/>";
    }
    else if (operation == "108") 
    {
       cll.innerHTML = "<fmt:message key="dailyMoney.Operation108"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }*/    
  }

<%
  if(request.getAttribute("dailyMoneyList") != null)
  {
%>
    parent.TopFrame.statQueryForm.query.disabled = false;
    parent.TopFrame.wait.style.visibility = "hidden";
<%
  }
%>
// -->
</script>
</body>
</html>

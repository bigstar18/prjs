<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	function next(){
		//var date = ec_table.rows(id).cells(0).innerHTML;
		//var code = ec_table.rows(id).cells(1).innerHTML;
		//if (date == "" || code == "") {
			//alert("�����м�¼ʱǿ����");
			//return false;
		//}
		//top.MainFrame.location.href = "<c:url value="/deduct/deduct.do?method=deductGo&date="/>" + date + "&code=" + code;
		
		top.mainFrame.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=deductGo&date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>'+"&d="+Date();
	}
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="nextDeductDetailQueryMenu" var="deduct" 
			action="${pageContext.request.contextPath}/timebargain/deduct/deduct.do?funcflg=nextDeductDetailQueryMenu"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
  		>
  		<ec:row>
  			<ec:column property="customerID" title="���׶�������" width="90" style="text-align:center;"/> 
  			
  			<ec:column property="buyQty" title="�򶩻�" format="quantity" calcTitle= "�ϼ�" calc="total" width="60"  style="text-align:right;"/> 
  			<ec:column property="sellQty" title="������" format="quantity" calc="total" width="60" style="text-align:right;"/> 
  			<ec:column property="buyKeepQty" title="����" format="quantity"  calc="total" width="60" style="text-align:right;"/>
  			
  			<ec:column property="sellKeepQty" title="������" format="quantity"  calc="total" width="60" style="text-align:right;"/>
  			<ec:column property="relPureHoldQty" title="��������" format="quantity" width="90" style="text-align:right;"/>
  			<ec:column property="pL" title="ӯ���ϼ�" width="90" style="text-align:right;"/> 
  			<ec:column property="pL_ratio" title="ӯ����" width="90" style="text-align:right;"/> 
  			<ec:column property="counteractQty" title="�Գ���" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  			<ec:column property="orderQty" title="ί����" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  			<ec:column property="deductableQty" title="��ǿ����" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  			<ec:column property="estimateQty" title="����ǿ����" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  			<ec:column property="deductQty" title="Ӧǿ����" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  			<ec:column property="deductedQty" title="��ǿ����" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  			<ec:column property="counteractedQty" title="�ѶԳ���" format="quantity"  calc="total" width="90" style="text-align:right;"/>
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
  
</table>

	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="Qty" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="bS_Flag" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>		
	<textarea id="ecs_t_WL" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="wL" >
			<ec:options items="WL" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--



// -->
</script>

  </body>
</html>

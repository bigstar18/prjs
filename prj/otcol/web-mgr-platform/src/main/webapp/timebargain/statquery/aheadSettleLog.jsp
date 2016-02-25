<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
  </head>
			
  <body>
   <table width="650">
  <tr><td>
  		<ec:table items="queryAheadSettleList" var="log" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=queryAheadSettle"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
  		>
  		<ec:row>
  			<ec:column property="commodityID" title="��Ʒ����" width="100" style="text-align:right;"/> 
  			<ec:column property="price" title="�۸�" width="100" style="text-align:right;"/> 
  			<ec:column property="customerID_B" title="���׿ͻ�ID" width="100" style="text-align:right;"/>
  			<ec:column property="quantity_B" title="��������" width="100" style="text-align:right;"/>
  			<ec:column property="customerID_S" title="�����׿ͻ�ID" width="100" style="text-align:right;"/>
  			<ec:column property="gageQty_S" title="���ֶ�����" width="100" style="text-align:right;"/>
  			<ec:column property="userID" title="�����û�" width="100" style="text-align:right;"/>
  			<ec:column property="createTime" title="����ʱ��" cell="date" format="date" width="100" style="text-align:right;"/>
			
  		
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
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--

// -->
</script>

  </body>
</html>

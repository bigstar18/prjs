<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="settleList" var="settle" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listSettle"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="settleProcessDate" title="��������" cell="date" width="100" style="text-align:center;"/> 
  			<ec:column property="firmID" title="�����̴���" width="80" style="text-align:center;"/> 
  			<ec:column property="commodityID" title="��Ʒ����" width="80" style="text-align:center;"/> 
  			<ec:column property="bS_Flag" title="������־" width="80" style="text-align:center;" editTemplate="ecs_t_bS_Flag" mappingItem="BS_FLAG2"/>	
  			
  			<ec:column property="settleQty" title="��������" width="80" format="quantity" calcTitle= "�ϼ�" calc="total" style="text-align:right;"/> 
  			<ec:column property="a_HoldNo" title="��������"  width="70" format="quantity" style="text-align:right;"/>
  			<ec:column property="Price" title="������" width="70" cell="currency" style="text-align:right;"/>
  			<ec:column property="SettlePrice" title="���ռ�" width="70" cell="currency" style="text-align:right;"/>
  			<ec:column property="settleMargin" title="���ձ�֤��" calc="total" width="100" cell="currency" style="text-align:right;"/>
  			<ec:column property="payout" title="���ջ���" calc="total" width="100" cell="currency" style="text-align:right;"/>
  			<ec:column property="settleFee" title="����������" calc="total" width="80" cell="currency" style="text-align:right;"/>
  			<ec:column property="settle_PL" title="����ӯ��" width="70" cell="currency" calcTitle= "�ϼ�" calc="total" style="text-align:right;"/> 
  			<ec:column property="SettleAddedTax" title="��ֵ˰�۲�" cell="currency" calc="total" width="80" style="text-align:right;"/>
  			<ec:column property="_1" title="��������"   width="80" style="text-align:right;">
  					<c:choose>
  						<c:when test="${settle.SettleType==0}">
  							�Զ�����
  						</c:when>
  						<c:when test="${settle.SettleType==1}">
  							�ֶ�����
  						</c:when>
  						<c:when test="${settle.SettleType==2}">
  							��ǰ����
  						</c:when>
  						<c:otherwise>
  							���ڽ���
  						</c:otherwise>
  					</c:choose>
  			</ec:column>
  			
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
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
	<textarea id="ecs_t_bS_Flag" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="bS_Flag" >
			<ec:options items="BS_FLAG2" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("settleList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.statQueryForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>


  for (var i=0;i<ec_table.rows.length;i++)                
  {
    //alert(i);
    //ec_table.rows(i).cells(7).style.display = "none";	
  }
// -->
</script>

  </body>
</html>

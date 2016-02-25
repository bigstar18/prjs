<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	function check(id){
		var applyID = ec_table.rows(id).cells(0).innerHTML;
		pTop("<c:url value="/timebargain/baseinfo/waitCheck.jsp?applyID="/>" + applyID, 400, 300);
	}
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="applyWaitCheList" var="che" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/virtualFund.do?funcflg=applyWaitCheList"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="virtualFund.xls" 
			csvFileName="virtualFund.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="applyID" title="���뵥��" width="130" style="text-align:center;"/>	
  			<ec:column property="firmID" title="������ID" width="100" style="text-align:center;"/> 
  			<ec:column property="virtualFunds" title="�����ʽ�" width="100" style="text-align:right;"/> 
  			
  			<ec:column property="status" title="��ǰ״̬"  width="100"   editTemplate="ecs_t_status" mappingItem="PRESENTSTATUS"   style="text-align:center;"/>
  			<ec:column property="createTime" title="����ʱ��" cell="date" format="date" width="100"  style="text-align:center;"/> 
  			<ec:column property="_1" title="���" width="50" style="text-align:center;">
  				<a href="#" onclick="check(${GLOBALROWCOUNT}-1)"><img title="���" src="<c:url value="/timebargain/images/she.gif"/>"/></a>
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
			<ec:options items="PRESENTSTATUS" />
		</select>
	</textarea>		
	<textarea id="ecs_t_APPLYTYPE" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="applyType" >
			<ec:options items="APPLYTYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("applyWaitCheList") != null)
  {
%>
    parent.TopFrame1.customerForm.query.disabled = false;
    parent.TopFrame1.wait.style.visibility = "hidden";
<%
  }
%>



// -->
</script>

  </body>
</html>

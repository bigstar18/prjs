<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	function check(id){
		//alert(id);
		var applyID = id;
		
		//window.open("<c:url value="/apply/applyWaitListCheck.jsp?applyID="/>" + applyID, 500, 500);
		pTop("<c:url value="/timebargain/apply/applyWaitListCheck_lastFrame.jsp?applyID="/>" + applyID, 500, 400);
	}
	
	function search(){
		parent.TopFrame1.query_onclick();
	}
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="applyWaitList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/apply/apply.do?funcflg=applyWaitListCheck"	
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
  			<ec:column property="billID" title="�ֵ���" width="80" style="text-align:center;"/>
  			<ec:column property="firmID_S" title="�������̴���" width="90" style="text-align:center;"/> 
  			<ec:column property="commodityID" title="��Ʒ����" width="100" style="text-align:center;"/> 
  				
  			
  			<ec:column property="quantity" title="�ֵ�����" width="80" format="quantity" calcTitle= "�ϼ�" calc="total" style="text-align:right;"/> 
  			<ec:column property="applyType" title="��������" width="120" editTemplate="ecs_t_APPLYTYPE" mappingItem="APPLYTYPE" style="text-align:center;"/> 
  			<ec:column property="status" title="��ǰ״̬"  width="70" editTemplate="ecs_t_status" mappingItem="PRESENTSTATUS" style="text-align:center;"/>
  			<ec:column property="createTime" title="����ʱ��" width="100" cell="date" format="date"  style="text-align:center;"/> 
  			<ec:column property="_1" title="���" width="50" style="text-align:center;">
  				<a href="#" onclick="check('<c:out value="${app.applyID}"/>')"><img title="���" src="<c:url value="/timebargain/images/she.gif"/>"/></a>
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
  if(request.getAttribute("applyWaitList") != null)
  {
%>
	if (parent.TopFrame1) {
		parent.TopFrame1.applyForm.query.disabled = false;
    	parent.TopFrame1.wait.style.visibility = "hidden";
	}
<%
  }
%>

  //for (var i=0;i<ec_table.rows.length;i++)                
  //{
    //alert(i);
  //  ec_table.rows(i).cells(8).style.display = "none";	
  //}

// -->
</script>

  </body>
</html>

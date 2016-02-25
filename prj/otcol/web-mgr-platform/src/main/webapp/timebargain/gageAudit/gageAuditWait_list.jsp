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
		var result = pTop("<c:url value="/timebargain/gageAudit/gageAuditWait_Message.jsp?applyID="/>" + applyID, 500, 400);
		if(result==0){
			search();
		}
		
	}
	
	function search(){
		parent.TopFrame1.query_onclick();
	}
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="auditGageList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/gageAudit/gageAudit.do?funcflg=listAuditGage"	
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
  			<ec:column property="APPLYID" title="���뵥��" width="60" style="text-align:center;"/>	
  			<ec:column property="COMMODITYID" title="��Ʒ����" width="70" style="text-align:center;"/> 
  			<ec:column property="FIRMID" title="�����̴���" width="70" style="text-align:center;"/> 
  			<ec:column property="CUSTOMERID" title="���׶�������" width="80" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="��������" width="60" style="text-align:center;"/> 
  			<ec:column property="APPLYTYPE" title="��������" width="70" style="text-align:center;">
  				<c:choose>
					<c:when test="${app.APPLYTYPE==1 }">�ֶ�����</c:when>
					<c:when test="${app.APPLYTYPE==2 }">��������</c:when>
					<c:when test="${app.APPLYTYPE==3 }">ǿ�Ƴ���</c:when>
				</c:choose>
  			</ec:column> 
  			<ec:column property="CREATETIME" title="��������" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="CREATOR" title="������" width="60" style="text-align:center;"/> 
  			<ec:column property="_1" title="���" width="50" style="text-align:center;">
  				<a href="#" onclick="check('<c:out value="${app.APPLYID}"/>')"><img title="���" src="<c:url value="/timebargain/images/she.gif"/>"/></a>
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
	<script type="text/javascript">
<!--
<%
  if(request.getAttribute("auditGageList") != null)
  {
%>
	if (parent.TopFrame1) {
		parent.TopFrame1.applyGageForm.query.disabled = false;
    	parent.TopFrame1.wait.style.visibility = "hidden";
	}
    
<%
  }
%>
// -->
</script>
<%@ include file="/timebargain/common/messages.jsp" %>
  </body>
</html>

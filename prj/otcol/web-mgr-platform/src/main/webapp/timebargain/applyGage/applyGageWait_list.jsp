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
  		<ec:table items="applyGageList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/applyGage/applyGage.do?funcflg=listApplyGage"	
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
  			<ec:column property="APPLYID" title="���뵥��" width="70" style="text-align:center;"/>	
  			<ec:column property="COMMODITYID" title="��Ʒ����" width="70" style="text-align:center;"/> 
  			<ec:column property="FIRMID" title="�����̴���" width="80" style="text-align:center;"/> 
  			<ec:column property="CUSTOMERID" title="���׶�������" width="90" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="��������" width="70" style="text-align:center;"/> 
  			<ec:column property="APPLYTYPE" title="��������" width="70" style="text-align:center;">
  				<c:choose>
					<c:when test="${app.APPLYTYPE==1 }">�ֶ�����</c:when>
					<c:when test="${app.APPLYTYPE==2 }">��������</c:when>
					<c:when test="${app.APPLYTYPE==3 }">ǿ�Ƴ���</c:when>
				</c:choose>
  			</ec:column> 
  			<ec:column property="CREATETIME" title="��������" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="CREATOR" title="������" width="70" style="text-align:center;"/> 
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
  if(request.getAttribute("applyGageList") != null)
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
  </body>
</html>
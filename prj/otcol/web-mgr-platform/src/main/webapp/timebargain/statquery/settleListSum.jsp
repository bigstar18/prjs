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
  		<ec:table items="sumList" var="settleSum" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listSum"	
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
  			<ec:column property="replaceSettle" title="��������" width="60" style="text-align:center;"/>	
  			<ec:column property="settleQtys" title="��Լ����" width="60" style="text-align:center;"/> 
  			<ec:column property="fellbackQtys" title="ΥԼ����"  width="60" style="text-align:center;"/>
			
  		
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
<%
  if(request.getAttribute("list") != null)
  {
%>
    parent.List2Frame.statQueryForm.query.disabled = false;
    parent.List2Frame.wait.style.visibility = "hidden";
<%
  }
%>
// -->
</script>

  </body>
</html>

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
  <LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">

	
</SCRIPT>
  </head>
			
  <body>
  		<%
  			String flag = (String)request.getAttribute("flag");
  		%>
   <table width="100%">
  <tr><td>
  		<ec:table items="togetherList" var="settle" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listTogether"	
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
  			<ec:column property="firmID" title="�����̴���" width="15%" style="text-align:center;"/> 
  			<ec:column property="commodityID" title="��Ʒ����" width="15%" style="text-align:center;"/> 
  			
  			<ec:column property="relHoldQty" title="��������" width="15%" format="quantity" calcTitle= "�ϼ�" calc="total" style="text-align:right;"/> 	
  			
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>
	<div class="req">
		˵����������ť���в�ѯ
	</div>
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



 
// -->
</script>

  </body>
</html>

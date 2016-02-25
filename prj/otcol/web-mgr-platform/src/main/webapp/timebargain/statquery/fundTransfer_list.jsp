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
	<ec:table items="fundTransferList" var="fundTransfer" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listFundTransfer"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="CustomerFundsList.xls" 
			csvFileName="CustomerFundsList.csv"
			showPrint="true" 			  
			rowsDisplayed="20"
			listWidth="100%"	
			minHeight="300"
	>
		<ec:row>	
		    <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${fundTransfer.id}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:30;"/>
			<ec:column property="FirmID" title="交易商ID" width="90" style="text-align:center;"/>
			
			<ec:column property="TransferFund" title="划拨资金" width="100" style="text-align:right;"/>
			<ec:column property="CreateTime" title="创建时间" cell="date" format="date" width="120" style="text-align:center;"/>		
			<ec:column property="status" title="状态" width="100" editTemplate="ecs_t_status" mappingItem="TRANSFER_STATUS" style="text-align:center;"/>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:batch_do('<fmt:message key="fundTransfer.status1"/>','<c:url value="/timebargain/statquery/statQuery.do?funcflg=fundTransferStatus"/>');">审核</a>
			
		</ec:extend>
	</ec:table>
	
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRANSFER_STATUS" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("fundTransferList") != null)
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

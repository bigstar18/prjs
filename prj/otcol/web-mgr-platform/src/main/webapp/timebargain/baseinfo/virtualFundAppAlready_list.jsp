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
  		<ec:table items="applyAlreadyList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/virtualFund.do?funcflg=applyAlreadyList"	
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
  			<ec:column property="applyID" title="申请单号" width="130" style="text-align:center;"/>	
  			<ec:column property="firmID" title="交易商ID" width="100" style="text-align:center;"/> 
  			<ec:column property="virtualFunds" title="虚拟资金" width="100" style="text-align:center;"/> 
  			
  			
  			<ec:column property="status" title="当前状态"  width="100"  editTemplate="ecs_t_status" mappingItem="PRESENTSTATUS" style="text-align:center;"/>
  			<ec:column property="createTime" title="创建时间" cell="date" format="date" width="100"  style="text-align:center;"/> 
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>

	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="Qty" />
	</textarea>
	<!-- 编辑状态所用模板 -->
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
  if(request.getAttribute("applyAlreadyList") != null)
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

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="applyAlreadyList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/apply/apply.do?funcflg=applyAlreadyListCheck"	
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
  			<ec:column property="billID" title="仓单号" width="80" style="text-align:center;"/>	
  			<ec:column property="firmID_S" title="卖交易商代码" width="80" style="text-align:center;"/> 
  			<ec:column property="commodityID" title="商品代码" width="80" style="text-align:center;"/> 
  			
  			
  			<ec:column property="quantity" title="仓单数量" width="80" format="quantity" calcTitle= "合计" calc="total" style="text-align:right;"/> 
  			<ec:column property="applyType" title="申请种类" width="120" editTemplate="ecs_t_APPLYTYPE" mappingItem="APPLYTYPE" style="text-align:center;"/> 
  			<ec:column property="status" title="当前状态"  width="80" editTemplate="ecs_t_status" mappingItem="PRESENTSTATUS"  style="text-align:center;"/>
  			<ec:column property="createTime" title="创建时间" width="100" cell="date" format="date"  style="text-align:center;"/> 
  			
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
	if (parent.TopFrame1) {
		parent.TopFrame1.applyForm.query.disabled = false;
    	parent.TopFrame1.wait.style.visibility = "hidden";
	}
<%
  }
%>



// -->
</script>

  </body>
</html>

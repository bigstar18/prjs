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
		var result = pTop("<c:url value="/timebargain/aheadSettle/aheadSettleMessage.jsp?applyID="/>" + applyID, 500, 400);
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
  		<ec:table items="aheadSettleList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/aheadSettle/aheadSettleAudit.do?funcflg=listAuditAheadSettle"	
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
  			<ec:column property="APPLYID" title="申请单号" width="70" style="text-align:center;"/>	
  			<ec:column property="COMMODITYID" title="商品代码" width="70" style="text-align:center;"/> 
  			<ec:column property="CUSTOMERID_S" title="卖方交易二级代码" width="120" style="text-align:center;"/> 
  			<ec:column property="CUSTOMERID_B" title="买方交易二级代码" width="120" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="申请数量" width="70" style="text-align:center;"/> 
  			<ec:column property="PRICE" title="交收价格" width="70" style="text-align:center;">
  			<c:choose>
					<c:when test="${app.PRICE==0 }">  按订立价交收</c:when> 
					<c:otherwise>${app.PRICE}</c:otherwise>
				</c:choose>
  			</ec:column>  
  			<ec:column property="GAGEQTY" title="卖方抵顶数量" width="100" style="text-align:center;"/>	
  			<ec:column property="CREATETIME" title="创建日期" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="CREATOR" title="创建人" width="70" style="text-align:center;"/> 
  			<ec:column property="_1" title="审核" width="50" style="text-align:center;">
  				<a href="#" onclick="check('<c:out value="${app.APPLYID}"/>')"><img title="审核" src="<c:url value="/timebargain/images/she.gif"/>"/></a>
  			</ec:column>
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
	<script type="text/javascript">
<!--
<%
  if(request.getAttribute("aheadSettleList") != null)
  {
%>
	if (parent.TopFrame1) {
		parent.TopFrame1.aheadSettleForm.query.disabled = false;
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

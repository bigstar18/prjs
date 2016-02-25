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
  		<ec:table items="aheadSettleList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/aheadSettle/aheadSettleApply.do?funcflg=listApplyAheadSettle"	
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
			<ec:column property="STATUS" title="状态" width="100" style="text-align:center;">
				<c:choose>
					<c:when test="${app.STATUS==1 }">等待审核</c:when>
					<c:when test="${app.STATUS==2 }">审核通过</c:when>
					<c:when test="${app.STATUS==3 }">审核不通过</c:when>
				</c:choose>
			</ec:column>
  			<ec:column property="CREATETIME" title="创建日期" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="CREATOR" title="创建人" width="70" style="text-align:center;"/> 
  			<ec:column property="MODIFYTIME" title="修改时间" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="MODIFIER" title="最后修改人" width="80" style="text-align:center;"/> 
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
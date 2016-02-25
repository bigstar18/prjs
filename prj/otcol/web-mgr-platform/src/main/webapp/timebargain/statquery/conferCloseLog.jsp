<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="queryConferCloseList" var="log" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=queryConferClose"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			title=""
  		>
  		<ec:row>
  			<ec:column property="commodityID" title="商品代码" width="80" style="text-align:center;"/> 
  			<ec:column property="price" title="价格" width="100" style="text-align:right;"/> 
  			<ec:column property="customerID_B" title="买二级代码" width="100" style="text-align:center;"/>
  			
  			<ec:column property="customerID_S" title="卖二级代码" width="100" style="text-align:center;"/>
  			<ec:column property="quantity_S" title="转让数量" width="100" style="text-align:center;"/>
  			<ec:column property="userID" title="操作用户" width="100" style="text-align:center;"/>
  			<ec:column property="createTime" title="操作时间" cell="date" format="date" width="100" style="text-align:center;"/>
			
  		
  		</ec:row>
  			
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
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--

// -->
</script>

  </body>
</html>

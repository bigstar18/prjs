<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.StatQueryManager"%>
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%>

<%
	StatQueryManager mgr = (StatQueryManager)SysData.getBean("statQueryManager");
	boolean cnt = true;
%>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	function isConfirm(commodityID){
		if (confirm("您确定要提交吗？")) {
			parent.HiddFrame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=listHandCheck&commodityID="/>" + commodityID;
			//return true;
		}else {
			return false;
		}
	}
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="list" var="settleHand" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listHand"	
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
  			<ec:column property="name" title="商品名称" width="20%" style="text-align:center;"/>	
  			<ec:column property="commodityID" title="商品代码" width="20%" style="text-align:center;"/> 
  			<ec:column property="marketDate" title="上市日期" cell="date" format="date" width="20%" style="text-align:center;"/>
			<ec:column property="settleDate" title="交收日期" cell="date" format="date" width="20%" style="text-align:center;"/>			
  			<ec:column property="_1" title="交收" width="20%" tipTitle="手工交收" style="text-align:center;"> 
  				<a href="#" onclick="return isConfirm('<c:out value="${settleHand.commodityID}"/>')"><img src="<c:url value="/timebargain/images/053753220.gif"/>"></a>
  			</ec:column>
  		
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

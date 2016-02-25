<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--

// -->
</script>
</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="traderLogList" var="traderLog" 
			action="${pageContext.request.contextPath}/timebargain/onlinebrowse/onlinebrowse.do?funcflg=traderLog_list"	
			xlsFileName="traderLogList.xls" 
			csvFileName="traderLogList.csv"
			showPrint="true"
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			
	>
		<ec:row>	
		    <ec:column property="occurTime"  title="发生时间" cell="date" format="datetime" width="200" style="text-align:center;"/>	
			<ec:column property="traderID" title="交易员代码" width="100" style="text-align:center;"/>
			<ec:column property="oprType" title="操作类型" editTemplate="ecs_t_oprType" mappingItem="OPRTYPE" width="100" style="text-align:center;"/>
			<ec:column property="moduleID" title="交易模块代码" width="300" style="text-align:center;"/>
			<ec:column property="ip" title="登录IP" width="300" style="text-align:center;"/>		
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
	<textarea id="ecs_t_oprType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="oprType" >
			<ec:options items="OPRTYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("traderLogList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.agencyForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
</body>
<script type="text/javascript">
<!--

// -->
</script>
</html>

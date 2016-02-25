<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/tradecontrol/broadcast.do?crud=create&funcflg=editWait"/>";
}

// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="waitSendList" var="broadcast" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/broadcast.do?funcflg=waitSend"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="broadcastWait.xls" 
			csvFileName="broadcastWait.csv"
			showPrint="true" 
			listWidth="100%"		  
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
		<ec:row>
             <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${broadcast.id}" viewsAllowed="html" style="text-align:center;padding-left:9px;width:30;"/>				            		
			<ec:column property="title" title="标题" width="100" style="text-align:center;">
			<a href="<c:out value="${ctx}"/>/timebargain/tradecontrol/broadcast.do?crud=update&funcflg=editWait&id=<c:out value="${broadcast.id}"/>"><c:out value="${broadcast.title}"/></a> 
			</ec:column>
			<ec:column property="content" title="内容" width="120" style="text-align:center;"/>	
			<ec:column property="firmID" title="交易商代码" width="100" style="text-align:center;"/>
			<ec:column property="status" title="状态" editTemplate="ecs_t_status" mappingItem="BROADCAST_STATUS" width="90" style="text-align:center;"/>
			<ec:column property="author" title="作者" width="90" style="text-align:center;"/>	
			<ec:column property="createTime" title="创建时间" cell="date" format="date" width="100" style="text-align:center;"/>		
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('删除广播消息','<c:url value="/timebargain/tradecontrol/broadcast.do?funcflg=delete"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>	
	</ec:table>
	</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BROADCAST_STATUS" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

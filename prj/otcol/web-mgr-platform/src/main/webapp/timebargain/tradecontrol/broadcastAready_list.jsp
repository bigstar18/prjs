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
	<ec:table items="alreadySendList" var="broadcast" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/broadcast.do?funcflg=alreadySend"	
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
			<ec:column property="title" title="����" width="20%" style="text-align:center;">
			<a href="<c:out value="${ctx}"/>/timebargain/tradecontrol/broadcast.do?crud=update&funcflg=editAready&id=<c:out value="${broadcast.id}"/>"><c:out value="${broadcast.title}"/></a> 
			</ec:column>
			<ec:column property="content" title="����" width="20%" style="text-align:center;"/>	
			<ec:column property="firmID" title="�����̴���" width="20%" style="text-align:center;"/>
			<ec:column property="author" title="����" width="20%" style="text-align:center;"/>	
			<ec:column property="createTime" title="����ʱ��" cell="date" format="date" width="20%" style="text-align:center;"/>		
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:batch_do('ɾ���㲥��Ϣ','<c:url value="/timebargain/tradecontrol/broadcast.do?funcflg=deleteAready"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>	
	</ec:table>
	</td></tr>
</table>	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

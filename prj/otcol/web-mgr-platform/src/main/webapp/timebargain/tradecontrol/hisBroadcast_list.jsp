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
	function getHisBroadcast(clearDate,id){
		parent.location.href = "<c:url value="/timebargain/tradecontrol/broadcast.do?funcflg=getHisBroadcastByKye&clearDate="/>" + clearDate + "&id=" + id;
	}


// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="hisBroadcastList" var="hisBroadcast" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/broadcast.do?funcflg=searchHis"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="hisBroadcast.xls" 
			csvFileName="hisBroadcast.csv"
			showPrint="true" 
			listWidth="100%"		  
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
		<ec:row>
                		
			<ec:column property="title" title="����" width="20%" style="text-align:center;" >
				<a href="#" onclick="getHisBroadcast('<c:out value="${hisBroadcast.clearDate}"/>','<c:out value="${hisBroadcast.id}"/>')"><c:out value="${hisBroadcast.title}"/></a>
			</ec:column>
			<ec:column property="content" title="����" width="20%" style="text-align:center;"/>	
			<ec:column property="firmID" title="�����̴���" width="20%" style="text-align:center;"/>
			<ec:column property="author" title="����" width="20%" style="text-align:center;"/>	
			<ec:column property="createTime" title="����ʱ��" cell="date" format="date" width="20%" style="text-align:center;"/>		
		</ec:row>
		<ec:extend>
			
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
<script type="text/javascript" defer="defer">
<!--
	if (parent.TopFrame) {
		parent.TopFrame.broadcastForm.query.disabled = false;
		parent.TopFrame.wait.style.visibility = "hidden";
	}
//-->
</script>

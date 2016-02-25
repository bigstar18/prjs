<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<link href="<c:url value="/timebargain/widgets/extremecomponents/extremecomponents.css"/>" type="text/css" rel="stylesheet">
<title>
default
</title>
<script type="text/javascript">
<!--

// -->
</script>
</head>
<body>
<div id="content">
	<ec:table items="qryInfoList" var="qryInfo" 
			  action="${pageContext.request.contextPath}/timebargain/riskcontrol/tradeProps.do?funcflg=qryInfo"	
	>
		<ec:exportXls fileName="QryInfoList.xls" tooltip="ec.export.tooltip"/>
		<ec:row>	
		    <ec:column property="createTime" title="sysLogForm.createTime" cell="date" format="datetime" style="text-align:right;"/>	
			<ec:column property="userID" title="sysLogForm.userID" style="text-align:right;"/>
			<ec:column property="note" title="sysLogForm.note" style="text-align:right;"/>			
		</ec:row>
	</ec:table>
</div>	

<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

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
<body>
<table width="100%">
  <tr><td>
	<ec:table items="customerList" var="customer" 
			action="${pageContext.request.contextPath}/timebargain/onlinebrowse/onlinebrowse.do?funcflg=customer_list"	
			xlsFileName="customerList.xls" 
			csvFileName="customerList.csv"
			showPrint="true"
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
		<ec:row>
		    <ec:column property="consignerID" title="��Ϊί��Ա����" width="100" style="text-align:center;"/>
			<ec:column property="loginTime" title="��¼ʱ��" cell="date" format="datetime" style="text-align:center;" width="250"/>	
		</ec:row>
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

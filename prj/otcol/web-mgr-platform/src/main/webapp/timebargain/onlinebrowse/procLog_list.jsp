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
	<ec:table items="procLogList" var="procLog" 
			action="${pageContext.request.contextPath}/timebargain/onlinebrowse/onlinebrowse.do?funcflg=procLog_list"	
			xlsFileName="procLogList.xls" 
			csvFileName="procLogList.csv"
			showPrint="true"
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>	
            <ec:column property="err_date" title="ִ�д洢ʱ��" cell="date" format="datetime" width="250" style="text-align:center;"/>
            <ec:column property="name_proc" title="�洢��" width="150" style="text-align:center;"/>
			<ec:column property="err_code" title="�������" width="100" style="text-align:center;"/>
			<ec:column property="err_msg" title="��������" width="350" style="text-align:center;"/>
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
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("procLogList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.agencyForm.query.disabled = false;
	    parent.TopFrame.agencyForm.del.disabled = false;
	    parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
</body>
</html>

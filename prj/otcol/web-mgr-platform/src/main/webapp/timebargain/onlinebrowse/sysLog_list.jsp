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
	<ec:table items="sysLogList" var="sysLog" 
			action="${pageContext.request.contextPath}/timebargain/onlinebrowse/onlinebrowse.do?funcflg=sysLog_list"	
			xlsFileName="sysLogList.xls" 
			csvFileName="sysLogList.csv"
			showPrint="true"
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			
	>
		<ec:row>	
		    <ec:column property="createTime"  title="����ʱ��" cell="date" format="datetime" width="200" style="text-align:center;"/>	
			<ec:column property="userID" title="ϵͳ�û�ID" width="100" style="text-align:center;"/>
			<ec:column property="action" title="��־����" editTemplate="ecs_t_status" mappingItem="LOGTYPE" width="100" style="text-align:center;"/>
			<ec:column property="note" ellipsis="true" title="������¼"  width="300" style="text-align:center;">	
			</ec:column>
		</ec:row>
	</ec:table>
	</td></tr>
</table>
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="LOGTYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("sysLogList") != null)
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
  /*var cll;
  for (var i=3;i<ec_table.rows.length;i++) 
  {
    cll = ec_table.rows(i).cells(2);
    var action = cll.innerHTML;
    if (action == "01") 
    {
       cll.innerHTML = "<fmt:message key="sysLogForm.action.option01"/>";
    }
    else if (action == "02") 
    {
       cll.innerHTML = "<fmt:message key="sysLogForm.action.option02"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }   
  }*/
// -->
</script>
</html>

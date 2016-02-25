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
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/customerGroup.do?crud=create&funcflg=edit"/>";
}

function searchCustomer(groupID)
{ 
  document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?groupID="/>" + groupID;
}
// -->
</script>
</head>
<body>
<table width="600">
  <tr><td>
	<ec:table items="customerGroupList" var="customerGroup" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/customerGroup.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			height="450px" 
			xlsFileName="customerGroup.xls" 
			csvFileName="customerGroup.csv"
			showPrint="true" 
			listWidth="100%"
			title="���������б�"	
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${customerGroup.groupID}" viewsAllowed="html" style="text-align:center;padding-left:9px;width:30;"/>						
            <ec:column property="groupID" title="��ID" width="100" style="text-align:right;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/customerGroup.do?crud=update&funcflg=edit&groupID=<c:out value="${customerGroup.groupID}"/>"><c:out value="${customerGroup.groupID}"/></a> 
            </ec:column>
			<ec:column property="groupName" title="������" width="130" style="text-align:right;"/>
			<ec:column property="parentName" title="�ϼ�����" width="130" style="text-align:right;"/>			
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('<fmt:message key="customerGroupForm.delbutton"/>','<c:url value="/timebargain/baseinfo/customerGroup.do?funcflg=delete"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
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

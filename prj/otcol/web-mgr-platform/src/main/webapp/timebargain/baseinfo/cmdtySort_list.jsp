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
    document.location.href = "<c:url value="/timebargain/baseinfo/cmdtySort.do?crud=create&funcflg=edit"/>";
}

// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="cmdtySortList" var="cmdtySort" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/cmdtySort.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			toolbarContent="save add del|extend"
			pageSizeList="all"
			minHeight="240"
			title="品种分类"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${cmdtySort.sortID}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:30;"/>			
			<ec:column property="sortName" title="品种分类名称" width="100%" style="text-align:center;">	
				<a href="<c:out value="${ctx}"/>/timebargain/baseinfo/cmdtySort.do?crud=update&funcflg=edit&sortID=<c:out value="${cmdtySort.sortID}"/>"><c:out value="${cmdtySort.sortName}"/></a> 
			</ec:column>		
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('删除品种分类','<c:url value="/timebargain/baseinfo/cmdtySort.do?funcflg=delete"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>		
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

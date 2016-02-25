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
//del
function del_onclick()
{
    batch_do('<fmt:message key="suffixForm.delbutton"/>','<c:url value="/timebargain/baseinfo/suffix.do?funcflg=deleteSuffix"/>');
}
// -->
</script>
</head>
<body>
	<ec:table items="notUsedSuffixList" var="notUsedSuffix" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/suffix.do?funcflg=listNotUsedSuffix"	
			xlsFileName="notUsedSuffix.xls" 
			csvFileName="notUsedSuffix.csv"
			showPrint="true" 		
			listWidth="100%"		  
			rowsDisplayed="20"	
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${notUsedSuffix.marketCode},${notUsedSuffix.name}" width="30" viewsAllowed="html" style="text-align:center;"/>				            						
			<ec:column property="name" title="suffixForm.name" width="100"/>			
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:del_onclick();"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>		
	</ec:table>
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>

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
    document.location.href = "<c:url value="/timebargain/baseinfo/tradeProps.do?crud=create&funcflg=editGroupProps"/>";
}
// -->
</script>
</head>
<body>

    <table width="740">
    <tr><td>
    <ec:table items="groupTradePropsList" var="tradeProps" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeProps.do?funcflg=searchGroupProps"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="groupTradePropsList.xls" 
			csvFileName="groupTradePropsList.csv"
			showPrint="true" 
			listWidth="100%"
			rowsDisplayed="20"
	>
    
    <ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tradeProps.groupID}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:30;"/>								
            <ec:column property="groupName" title="组名称" width="100" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeProps.do?crud=update&funcflg=editGroupProps&groupID=<c:out value="${tradeProps.groupID}"/>" title="修改"><c:out value="${tradeProps.groupName}"/></a>
            </ec:column>
            <ec:column property="maxHoldQty" title="最大持仓量" style="text-align:center;"/>			
			<ec:column property="minClearDeposit" title="最低结算保证金" width="100" style="text-align:right;"/>							
			<ec:column property="maxOverdraft" title="最大透支额度" width="130" style="text-align:right;" />
			<ec:column property="modifyTime" title="修改时间" cell="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" style="text-align:center;"/>				
		</ec:row>
		
		<ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="batch_do('<fmt:message key="tradePropsForm.delbutton"/>','<c:url value="/timebargain/baseinfo/tradeProps.do?funcflg=deleteGroupProps"/>')"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>
	
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

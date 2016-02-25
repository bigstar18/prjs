<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>

<title>
default
</title>

<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/open.js"/>">
</script>
<script type="text/javascript">
function add_onclick()
{
    parent.window.location.href = "<c:url value="/timebargain/delay/delay.do?funcflg=privilegeAddForward"/>";
}
function alertMsg(){
	var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
	}
	
}
function updateInfo(id){
		pTop("<c:url value="/timebargain/delay/settleprivilege_A.jsp?id="/>" + id,600,400);
}
function searchKHPri(){
	document.location.reload();
}
</script>
</head>
<body leftmargin="2" topmargin="0" onload="alertMsg()">
<table width="100%">
  <tr><td>
	<ec:table items="listSettleprivilege" var="settleprivilege" 
			action="${pageContext.request.contextPath}/timebargain/delay/delay.do?funcflg=listSettleprivilege"	
			xlsFileName="firmprivilegeList.xls" 
			csvFileName="firmprivilegeList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"			  
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${settleprivilege.id}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:20%;"/>		
		    <ec:column property="typeId" title="交易商代码" width="20%" style="text-align:center;">
		    <a onclick="updateInfo('<c:out value="${settleprivilege.id}"/>');" style="cursor:hand" title="查看"><c:out value="${settleprivilege.typeid}"/></a> 
		    </ec:column>
            <ec:column property="kindId" title="商品代码" width="20%" style="text-align:center;"/>
            <ec:column property="privilegecode_b" title="买方权限" width="20%" style="text-align:center;" mappingItem="PRIVILEGECODE_B"/>
			<ec:column property="privilegecode_s" title="卖方权限" width="20%" style="text-align:center;" mappingItem="PRIVILEGECODE_S"/>
		</ec:row>
		<ec:extend>
			
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('删除交易商交收权限','<c:url value="/timebargain/delay/delay.do?funcflg=deletePrivilege"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
			
			
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
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
</script>
</body>


</html>

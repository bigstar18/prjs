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
	pTop("<c:url value="/timebargain/baseinfo/directFirmBreed_U.jsp"/>",500,240);
}
function mod_onclick(firmid){
	parent.location.href="<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=searchFirmMaxHoldQty&firmId="/>"+firmid;
}
function searchKHPri(){
	document.location.reload();
}
// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="directFirmBreedList" var="directFirmBreed" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedGet&type=e"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="directFirmBreed.xls" 
			csvFileName="directFirmBreed.csv"
			showPrint="true" 
			listWidth="100%"
			title=""			  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" width="20%" alias="itemlist" columnId="itemlist" value="${directFirmBreed.firmId},${directFirmBreed.breedId }" viewsAllowed="html" style="text-align:center;"/>				            	
            <ec:column property="firmId" title="交易商代码" width="20%" style="text-align:center;">                                                                                       
            </ec:column>
            
            <ec:column property="breedId" title="商品品种" width="20%" style="text-align:center;">
            	${directFirmBreed.breedname}
            </ec:column>
             <ec:column property="sortName" title="品种分类" width="20%" style="text-align:center;">
            	${directFirmBreed.sortName}
            </ec:column>	
            <ec:column property="_1" title="交易商特殊订货量" width="20%" style="text-align:center;">
             <a href="#" onclick="mod_onclick('<c:out value="${directFirmBreed.firmId }"/>')"><img title="进入详细信息" src="<c:url value="/timebargain/images/fly.gif"/>"/></a>
            </ec:column>				
		</ec:row>
		<ec:extend>
			<img src="<c:url value="/timebargain/images/girdadd.gif"/>" usemap="#Map1">
			<img src="<c:url value="/timebargain/images/girddel.gif"/>" usemap="#Map2">
		</ec:extend>		
	</ec:table>
	
		<map name="Map1">
			<area shape="rect" coords="1,1,51,19" href="javascript:add_onclick()">
		</map>
		<map name="Map2">
			<area shape="rect" coords="1,1,51,19" href="javascript:batch_do('删除交易商品种设置','<c:url value="/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedDelete&type=e"/>');">
		</map>
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

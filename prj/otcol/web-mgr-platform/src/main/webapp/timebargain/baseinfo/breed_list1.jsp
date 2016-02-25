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
    parent.location.href = "<c:url value="/timebargain/baseinfo/breed.do?crud=create&funcflg=edit"/>";
}
//修改
function update_onclick(breedID) {
    parent.location.href ="<c:url value='/timebargain/baseinfo/breed.do?crud=update&funcflg=edit&breedID="+breedID+"'/>";
}
//queryCommodity
function queryCommodity(breedID)
{	
	window.top.document.frames['workspace'].frames[1].location.href = "<c:url value="/timebargain/baseinfo/commodity.jsp?condition=and a.status <> 1&breedID="/>" + breedID;
}
//cmdtySort_onclick()
function cmdtySort_onclick()
{	
    //window.open("<c:url value="/baseinfo/cmdtySort.jsp"/>",620,600)
    pTop("<c:url value="/timebargain/baseinfo/cmdtySort.jsp"/>",450,400);
}
// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="breedList" var="breed" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/breed.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="breed.xls" 
			csvFileName="breed.csv"
			showPrint="true" 
			listWidth="100%"
			title=""			  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${breed.breedID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>				            	
            <ec:column property="breedName" title="品种名称" width="40%" style="text-align:center;">
              <a href="javascript:update_onclick('${breed.breedID}')" title="修改"><c:out value="${breed.breedName}"/></a>  
            </ec:column>
            
            <ec:column property="sortName" title="品种分类" width="40%" style="text-align:center;"/>			
			<ec:column property="_1" title="对应商品" viewsAllowed="html" resizeColWidth="false" tipTitle="进入商品信息" sortable="false" width="20%" style="text-align:center;">
              <a href="#" onclick="queryCommodity('<c:out value="${breed.breedID}"/>')"><img src="<c:url value="/timebargain/images/commodity.gif"/>"></a> 
            </ec:column>			
		</ec:row>
		<ec:extend>
			<img src="<c:url value="/timebargain/images/girdadd.gif"/>" usemap="#Map1">
			<img src="<c:url value="/timebargain/images/girddel.gif"/>" usemap="#Map2">
			<img src="<c:url value="/timebargain/images/breed.gif"/>" usemap="#Map3">
		</ec:extend>		
	</ec:table>
	
		<map name="Map1">
			<area shape="rect" coords="1,1,51,19" href="javascript:add_onclick()">
		</map>
		<map name="Map2">
			<area shape="rect" coords="1,1,51,19" href="javascript:batch_do('删除品种','<c:url value="/timebargain/baseinfo/breed.do?funcflg=delete"/>');">
		</map>
		<map name="Map3">
			<area shape="rect" coords="1,1,51,19" href="javascript:cmdtySort_onclick()">
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

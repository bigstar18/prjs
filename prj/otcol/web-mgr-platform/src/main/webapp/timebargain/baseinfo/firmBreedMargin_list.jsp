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
    document.location.href = "<c:url value="/timebargain/baseinfo/tradeBreedRule.do?crud=create&funcflg=editFirmBreedMargin"/>";
}
function deleteFor(){
	if(confirm("是否级联删除该品种下的特殊保证金设置？")){
		batch_do('删除特殊保证金','<c:url value="/timebargain/baseinfo/tradeBreedRule.do?funcflg=deleteFirmBreedMargin&logos=true"/>');
	}else{
		batch_do('删除特殊保证金','<c:url value="/timebargain/baseinfo/tradeBreedRule.do?funcflg=deleteFirmBreedMargin"/>');
	}
}
// -->
</script>
</head>
<body>
<div id="content">
    <table width="100%">
  <tr><td>
	<ec:table items="firmBreedMarginList" var="tradeBreedRule" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeBreedRule.do?funcflg=searchFirmBreedMargin"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="tradeRuleList.xls" 
			csvFileName="tradeRuleList.csv"
			showPrint="true" 
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
    <ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tradeBreedRule.firmID},${tradeBreedRule.breedID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>						
            <ec:column property="_1" title="修改" width="20%" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeBreedRule.do?crud=update&funcflg=editFirmBreedMargin&firmID=<c:out value="${tradeBreedRule.firmID}"/>&breedID=<c:out value="${tradeBreedRule.breedID}"/>"><img title="进入修改信息" src="<c:url value="/timebargain/images/fly.gif"/>"/></a> 
            </ec:column>
            <ec:column property="firmID" title="交易商代码" width="20%" style="text-align:center;">
              
            </ec:column>
            <ec:column property="breedID" title="品种代码" width="20%" style="text-align:center;"/>
            <ec:column property="marginAlgr" title="保证金算法" width="20%" style="text-align:center;" editTemplate="ecs_t_marginAlgr" mappingItem="MARGINALGR"/>
			<ec:column property="modifyTime" title="修改时间" cell="date" width="20%" style="text-align:center;"/>
		</ec:row>
   
	  <ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:deleteFor();"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
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
	<textarea id="ecs_t_marginAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="marginAlgr" >
			<ec:options items="MARGINALGR" />
		</select>
	</textarea>	
</div>
	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

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
function add_onclick(firmId)
{
    document.location.href = "<c:url value="/timebargain/baseinfo/tradeRule.do?crud=create&move=firm&funcflg=editFirmMaxHoldQty&firmID="/>"+firmId;
}
// -->
</script>
</head>
<%
	String firmId = "001";
	String actionPath = "/timebargain/baseinfo/tradeRule.do?funcflg=searchFirmMaxHoldQty&firmId="+firmId;
 %>
<body>
<div id="content">
    <table width="100%">
  <tr><td>
	<ec:table items="firmMaxHoldQtyList" var="tradeRule" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeRule.do?funcflg=searchFirmMaxHoldQty&firmId=${tradeRule.firmID}"	
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
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tradeRule.firmID},${tradeRule.commodityID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>						
            <ec:column property="_1" title="修改" width="15%" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeRule.do?crud=update&move=firm&funcflg=editFirmMaxHoldQty&firmID=<c:out value="${tradeRule.firmID}"/>&commodityID=<c:out value="${tradeRule.commodityID}"/>"><img title="进入修改信息" src="<c:url value="/timebargain/images/fly.gif"/>"/></a> 
            </ec:column>
            <ec:column property="firmID" title="交易商代码" width="15%" style="text-align:center;">
              
            </ec:column>
            <ec:column property="commodityID" title="商品代码" width="15%" style="text-align:center;"/>
            <ec:column property="maxHoldQty" title="最大订货量" width="15%" style="text-align:right;"/>
            <ec:column property="cleanMaxHoldQty" title="最大净订货量" width="15%" style="text-align:right;"/>
			<ec:column property="modifyTime" title="修改时间" cell="date" width="30%" style="text-align:center;"/>
		</ec:row>  			
	  <ec:extend>
	  		
			<a href="#" onclick="add_onclick('<c:out value="${RfirmID }"/>')"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('删除特殊订货量','<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=deleteFirmMaxHoldQty&move=firm"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
			<a href="<c:out value="/mgr/timebargain/baseinfo/directFirmBreed.jsp"/>">返回主持交易商品种设置</a>
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
	<textarea id="ecs_t_feeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="feeAlgr" >
			<ec:options items="FEEALGR" />
		</select>
	</textarea>	
</div>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

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
function addSuffix(MarketCode)
{
    pTop("<c:url value="/timebargain/baseinfo/addSuffix.jsp?marketCode="/>" + MarketCode,500,400);  
}
//usedSuffix
function usedSuffix(MarketCode,marketName,firmID)
{
    pTop("<c:url value="/timebargain/baseinfo/usedSuffix.jsp?marketCode="/>" + MarketCode + 
    	"&marketName=" + marketName + "&firmID=" + firmID,560,600);  
}
//notUsedSuffix
function notUsedSuffix(MarketCode)
{
    pTop("<c:url value="/timebargain/baseinfo/notUsedSuffix.jsp?marketCode="/>" + MarketCode,620,600);  
}
// -->
</script>
</head>
<body>
<table width="600">
  <tr><td>
	<ec:table items="marketSuffixList" var="marketSuffix" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/suffix.do?funcflg=listMarketSuffix"	
			xlsFileName="marketSuffix.xls" 
			csvFileName="marketSuffix.csv"
			showPrint="true" 	
			listWidth="100%"		  
			rowsDisplayed="20"
	>
		<ec:row>
			<ec:column property="marketName" title="marketForm.marketName" width="100"/>
			<ec:column property="allCnt" title="代码总数" width="100" style="text-align:right;"/>	
			<ec:column property="usedCnt" title="代码后缀" width="100" style="text-align:right;">
              <a href="#" onclick="usedSuffix('<c:out value="${marketSuffix.MarketCode}"/>','<c:out value="${marketSuffix.marketName}"/>','<c:out value="${marketSuffix.firmID}"/>')"><c:out value="${marketSuffix.usedCnt}"/></a>
            </ec:column>
			<ec:column property="notUsedCnt" title="未用代码" width="100" style="text-align:right;">
              <a href="#" onclick="notUsedSuffix('<c:out value="${marketSuffix.MarketCode}"/>')"><c:out value="${marketSuffix.notUsedCnt}"/></a>
            </ec:column>
           	<ec:column property="null" title="增加代码" width="100" style="text-align:right;">
              <a href="#" onclick="addSuffix('<c:out value="${marketSuffix.MarketCode}"/>')">增加</a>
            </ec:column>
		</ec:row>		
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

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*"%>
<html:html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>

<script type="text/javascript">
	function updateTFirm(firmID){
		parent.location.href = "<c:url value="/timebargain/baseinfo/tradePropsThree.do?funcflg=editTFirm&firmID="/>" + firmID;
	}
</script>

<title>
default
</title>
<%
response.setHeader("Charset","GBK");
 %>

</head>
<body >
			

<table width="100%">
  <tr><td>
	<ec:table items="tradePropsTFirmList" var="customer" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tradePropsThree.do?funcflg=searchTFirm"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="customer.xls" 
			csvFileName="customer.csv"
			showPrint="true" 
            listWidth="100%"		  
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
		<ec:row>
            
            <ec:column property="firmID" title="交易商代码" width="20%" style="text-align:center;">
            	<a href="#" onclick="updateTFirm('<c:out value="${customer.firmID}"/>')"><c:out value="${customer.firmID}"/></a>
            </ec:column>
            <ec:column property="maxHoldQty" title="最大订货总量"  width="20%" style="text-align:center;"/>			
			<ec:column property="minClearDeposit" title="最低结算准备金" width="20%" cell="currency" style="text-align:center;"/>
			<ec:column property="maxOverdraft" title="质押资金" width="20%" cell="currency" style="text-align:center;"/>
			<%-- 
			<ec:column property="virtualFunds" title="虚拟资金" width="20%" cell="currency" style="text-align:right;"/>
		     --%>
		</ec:row>
		<ec:extend>
			
			
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
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="maxHoldQty" >
			<ec:options items="MAXHOLDQTY" />
		</select>
	</textarea>	

<%@ include file="/timebargain/common/messages.jsp" %>
<SCRIPT language="javascript">


</SCRIPT>

</body>

</html:html>

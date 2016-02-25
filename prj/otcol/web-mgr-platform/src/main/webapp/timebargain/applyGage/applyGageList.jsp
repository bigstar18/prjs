<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html xmlns:MEBS>
<head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
    <LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
	<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar.htc"/>">
</head>
<body>
   <table width="100%">
   <tr>
   	<td>
   		<form name="queryForm" action="${pageContext.request.contextPath}/timebargain/menu/applyGage.do?funcflg=listApplyGage" method="post">
   		<table width="100%" height="100%" align="center">
			<tr align="center">
				<td align="right">申请单号：</td><td><input type="text" name="applyId" value="${applyId }"></td>
				<td align="right">商品代码：</td><td><input type="text" name="commodityID" value="${commodityID }"></td>
				<td align="right">交易商代码：</td><td><input type="text" name="firmID" value="${firmID }"></td>
				<td align="right">申请种类：</td>
				<td>
					<select name="applyType">
						<option value="">全部</option>
						<option value="0">正常</option>
						<option value="1">卖仓单</option>
						<option value="2">抵顶转让</option>
					</select>
					<script type="text/javascript">
						queryForm.applyType.value = "<c:out value='${applyType }'/>";
					</script>
				</td>
			</tr>
			<tr align="center">
				<td align="right">创建人：</td><td><input type="text" name="creator" value="${creator }"></td>
				<td align="right">创建时间：</td>
				<td>
					<MEBS:calendar eltID="createTime" eltName="createTime" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${createTime }" />
				</td>
				<td align="right">状态：</td>
				<td align="right">
					<select name="status">
						<option value="">全部</option>
						<option value="1">待审核</option>
						<option value="2">审核通过</option>
						<option value="3">审核失败</option>
					</select>
					<script type="text/javascript">
						queryForm.status.value = "<c:out value='${status }'/>";
					</script>
				</td>
				<td colspan="2" align="center">
					<input type="button" value="查询" onclick="doQuery()">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="添加申请" onclick="addApplyGage()">
				</td>
			</tr>
		</table>
		</form>
   	</td>
   </tr>
  <tr><td>
  		<ec:table items="applyGageList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/menu/applyGage.do?funcflg=listApplyGage"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="APPLYID" title="申请单号" width="130" style="text-align:center;">
  				<a href="#" onclick="showApplyMessage('<c:out value="${app.APPLYID }"/>')"> <c:out value="${app.APPLYID }"/> </a>
  			</ec:column>	
  			<ec:column property="COMMODITYID" title="商品代码" width="100" style="text-align:center;"/> 
  			<ec:column property="FIRMID" title="交易商代码" width="100" style="text-align:center;"/> 
  			<ec:column property="CUSTOMERID" title="交易二级代码" width="130" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="申请数量" width="100" style="text-align:center;"/> 
  			<ec:column property="APPLYTYPE" title="申请种类" width="100" mappingItem="NUM2STR_TYPE" style="text-align:center;"/> 
  			<ec:column property="STATUS" title="当前状态" width="130" mappingItem="NUM2STR_STATUS" style="text-align:center;"/>	
  			<ec:column property="CREATETIME" title="创建时间" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="CREATOR" title="创建人" width="100" style="text-align:center;"/> 
  			<ec:column property="REMARK1" title="创建人备注" width="130" style="text-align:center;"/>	
  			<ec:column property="MODIFYTIME" title="修改时间" width="100" cell="date" format="date" style="text-align:center;"/> 
  			<ec:column property="MODIFIER" title="最后修改人" width="100" style="text-align:center;"/> 
  			<ec:column property="REMARK2" title="修改人备注" width="100" style="text-align:center;"/> 
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>
<script type="text/javascript">
function showApplyMessage(v)
{
	window.location.href = "/timebargain/menu/applyGage.do?funcflg=getApplyGage&applyId="+v+"&d="+Date();
}

function addApplyGage()
{
	queryForm.action="/timebargain/menu/applyGage.do?funcflg=addApplyGage";
	queryForm.submit();
}

function doQuery()
{
	queryForm.submit();
}
</script>
</body>
</html>
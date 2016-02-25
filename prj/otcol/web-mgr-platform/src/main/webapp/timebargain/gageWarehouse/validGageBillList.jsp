<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html xmlns:MEBS>
<head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
    <LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
	<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar.htc"/>">
</head>
<body>
   <table width="100%" class="common">
   <tr>
   	<td>
   		<form name="queryForm" action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=validGageBillList" method="post">
   		<fieldset class="" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
   		<table width="100%" height="100%" align="center" class="">
			<tr>
				<td  width="60" style="white-space:nowrap;">商品品种:</td><td width="100" >
				<select name="BreedID">
				<option value="" >全部</option>
				<c:forEach var="breed" items="${breedList}">
					<option value = "${breed.BREEDID }">${breed.BREEDNAME }</option>					
				</c:forEach>
				</select>
				<script type="text/javascript">
					queryForm.BreedID.value = "<c:out value='${BreedID}'/>";
				</script>
				</td>
				<td align="left" width="90">交易商代码：</td><td align="left" width="150"><input type="text" name="FirmID" value="${FirmID }"
onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
				<td align="left" >
					<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return doQuery();">
											查询
										</html:button>	

					<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return resetData();">
											重置
										</html:button>			
				</td>
			</tr>
		</table>
		</fieldset>
		</form>
   	</td>
   </tr>
  <tr><td>
  		<ec:table items="validGageBillList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=validGageBillList"	
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
  			<ec:column property="FIRMID" title="交易商代码" width="25%" style="text-align:center;"/> 
  			<ec:column property="BREEDNAME" title="商品品种" width="25%" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="数量" width="25%" style="text-align:center;"/> 
  			<ec:column property="FROZENQTY" title="冻结数量" width="25%" style="text-align:center;"/> 
  		</ec:row>
  		</ec:table>
  </td></tr>
</table>
<script type="text/javascript">
function doQuery()
{
	queryForm.submit();
}

//重置数据
function resetData()
{
  document.forms[0].reset();
  document.getElementsByName("FirmID")[0].value="";

}
</script>
</body>
</html>
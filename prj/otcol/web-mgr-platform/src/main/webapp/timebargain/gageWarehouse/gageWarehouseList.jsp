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
   		<form name="queryForm" action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=gageWarehouseList" method="post">
   		<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
   		<table width="100%" height="100%" align="center" class="common">
			<tr align="center">
				<td align="right">仓单号：</td><td><input type="text" name="BillID" value="${BillID }"onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
				<td align="right">商品品种：</td><td>
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
				<td align="right">交易商代码：</td><td><input type="text"  name="FirmID" value="${FirmID }"onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
				<td align="right"></td>
				<td>
				</td>
				<td colspan="6" align="center">
					<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return doQuery();">
											查询
										</html:button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button"  style="width:60" class="button" value="重置" onclick="resetData()" />
				</td>
			</tr>
		</table>
		</fieldset>
		</form>
   	</td>
   </tr>
  <tr><td>
  		<ec:table items="gageWarehouseList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/menu/gageWarehouse.do?funcflg=gageWarehouseList"	
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
  			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${app.BILLID}&${app.FIRMID}&${app.BREEDID}&${app.QUANTITY}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:4%;"/>		
  			<ec:column property="BILLID" title="仓单号" width="20%" style="text-align:center;"/>
  			<ec:column property="FIRMID" title="交易商代码" width="20%" style="text-align:center;"/> 
  			<ec:column property="breedname" title="商品品种" width="20%" style="text-align:center;"/>	
  			<ec:column property="QUANTITY" title="数量" width="20%" style="text-align:center;"/> 
  			<ec:column property="CREATETIME" title="创建日期" width="16%" cell="date" format="date" style="text-align:center;"/>
  		</ec:row>
  		<ec:extend >
  			<a href="#" onclick="javascript:deleteFor();"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>
<script type="text/javascript">

function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/gageWarehouse/addGageWarehouse.jsp"/>";
}
function deleteFor(){
	batch_do('撤销仓单抵顶','<c:url value="/timebargain/menu/gageWarehouse.do?funcflg=revocation"/>');
}
function batch_do(entityName, action)
{
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一个进行' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = action + '&autoInc=false';
        form.submit();
    }
}
function atleaseOneCheck()
{
    var items = document.getElementsByName('itemlist');
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++)
        {
            if (items[i].checked == true)
            {
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
}
function revocation()
{
	queryForm.action="/timebargain/menu/gageWarehouse.do?funcflg=revocation";
	queryForm.submit();
}

function doQuery()
{
	queryForm.submit();
}

//重置数据
function resetData()
{
	document.getElementById("BillID").value="";
	document.getElementsByName("BreedID")[0].value="";
	document.getElementsByName("FirmID")[0].value="";
}
</script>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
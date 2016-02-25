<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.MEBS.delivery.services.*"%>
<%@ include file="../../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/regStockToEnterWareController.wha?funcflg=regStockToEnterWareList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<INPUT TYPE="hidden" NAME="id" value="">
		<fieldset width="95%">
			<legend>注册仓单转入库单查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">注册单号：</td>
					<td align="left">
						<input id="regStockId" name="_regStockId[like]" value="<c:out value='${oldParams["regStockId[like]"]}'/>" type=text class="text" style="width: 120px"
onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">${FIRMID}：</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 120px"
onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;仓库名称：
						<select id="warehouseId" name="_warehouseId[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${warehouseList}" var="warehouseList">
								<option value="${warehouseList.id}">${warehouseList.name}</option>
			                </c:forEach>
						</select>
						<script>
							frm.warehouseId.value = "<c:out value='${oldParams["warehouseId[=]"]}'/>";
					   </script>
					</td>
				</tr>
				<tr height="35">
					<td align="right">注册时间：</td>
					<td align="left">
						<MEBS:calendar eltID="createDate" eltName="_trunc(createDate)[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(createDate)[=][date]"]}'/>"/>
					</td>
					<td align="right">品种名称：</td>
					<td align="left">
					<select id="breedId" name="_breedId[=]" style="width:120" class="normal">
							<option value="">全部</option>
							<c:forEach items="${commodityList}" var="commodityList">
								<option value="${commodityList.id}">${commodityList.name}</option>
			                </c:forEach>
						</select>&nbsp;
						<script>
							frm.breedId.value = "<c:out value='${oldParams["breedId[=]"]}'/>";
					   </script>
					</td>
					<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	  <%@ include file="regStockToEnterWareTable.jsp"%>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery(){
		frm.submit();
	}
	
	function resetForm(){
		frm.regStockId.value = "";
		frm.firmId.value = "";
		frm.createDate.value = "";
		frm.breedId.options[0].selected = true;
		frm.warehouseId.options[0].selected = true;
		frm.submit();
	}
	
	//查看详情
	function fAuditing(id){
		frm.id.value = id;
		frm.action = "<%=basePath%>servlet/regStockToEnterWareController.${POSTFIX}?funcflg=regStockToEnterWareView";
		frm.submit();
	}
	
//-->
</SCRIPT>


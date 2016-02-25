<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}'); ">
  	<form name="frm" action="<%=basePath%>servlet/enterWareController.wha?funcflg=enterWareList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<INPUT TYPE="hidden" NAME="enterWareId" value="">
		<fieldset width="95%">
			<legend>入库单查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">入库单号：</td>
					<td align="left">
						<input id="id" name="_Id[like]" value="<c:out value='${oldParams["Id[like]"]}'/>" type=text class="text" style="width: 120px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">${FIRMID}：</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 120px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;入库单状态：
						<select id="ability" name="_ability[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${statusList}" var="result">
								<option value="${result.value}">${result.name}</option>
			                </c:forEach>
						</select>
						<script>
							frm.ability.value = "<c:out value='${oldParams["ability[=]"]}'/>";
						</script>
					</td>
					
				</tr>
				<tr height="35">
					<td align="right">仓库名称：</td>
					<td align="left">
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
					<td align="right">入库时间：</td>
					<td align="left">
						<MEBS:calendar eltID="enterDate" eltName="_trunc(enterDate)[=][date]" 
						               eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" 
						               eltValue="<c:out value='${oldParams["trunc(enterDate)[=][date]"]}'/>"/>
					</td>
					<script>
							//frm.enterDate.value = "<c:out value='${oldParams["enterDate[=]"]}'/>";
					</script>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;品种名称：
					<select id="commodityId" name="_commodityId[=]" style="width:120" class="form_k">
							<option value="">全部</option>
							<c:forEach items="${commodityList}" var="commodityList">
								<option value="${commodityList.id}">${commodityList.name}</option>
			                </c:forEach>
						</select>&nbsp;
						<script>
							frm.commodityId.value = "<c:out value='${oldParams["commodityId[=]"]}'/>";
					</script>
					</td>
				</tr>
				<tr height="35">
					<td align="right">现存重量：</td>
					<td align="left">
						<select id="existAmount" name="_existAmount[=]" class="normal" style="width: 120px">
							<OPTION value="-1" selected="selected">全部</OPTION>
							<OPTION value="0">等于零</OPTION>
							<OPTION value="1">大于零</OPTION>
						</select>
						<script>
							frm.existAmount.value = "<c:out value='${oldParams["existAmount[=]"]}'/>";
						</script>
					</td>
					<td colspan="2">&nbsp;</td>
					<td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
        <%@ include file="enterWareTable.jsp"%>
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
             <button class="lgrbtn" type="button" onclick="add();">添加入库单</button>&nbsp;&nbsp;
			 </div></td>
        </tr>
    </table>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function add()
	{
		frm.action="<%=basePath%>servlet/enterWareController.${POSTFIX}?funcflg=enterWareForward";
		frm.submit();
	}
	
	function doQuery(){
		//frm.pageIndex.value = 1;
		frm.submit();
	}
	
	function resetForm(){
		frm.id.value = "";
		frm.ability.value = "";
		frm.firmId.value = "";
		frm.enterDate.value = "";
		frm.commodityId.options[0].selected = true;
		frm.warehouseId.options[0].selected = true;
		frm.existAmount.options[2].selected = true;
		frm.submit();
	}
	
	//查看详情
	function fView(id){
		frm.enterWareId.value = id;
		frm.action = "<%=basePath%>servlet/enterWareController.${POSTFIX}?funcflg=enterWareView";
		frm.submit();
	}
	
//-->
</SCRIPT>


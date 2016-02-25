<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/enterInformController.${POSTFIX}?funcflg=enterInformList" method="post">
  	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="enterInformId" name="enterInformId">
		<fieldset width="95%">
			<legend>入库通知单查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">通知单号：</td>
					<td align="left">
						<input id="id" name="_id[like]" value="<c:out value='${oldParams["id[like]"]}'/>" type=text class="text" style="width: 120px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">${FIRMID}：</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" class="text" style="width: 120px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">&nbsp;&nbsp;通知单状态：
						<select id="ability" name="_informAbility[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							 <c:forEach items="${statusList}" var="status">
			                <option value="${status.value}">${status.name}</option>
			                </c:forEach>
							
						</select>
						<script>
						frm.ability.value = "<c:out value='${oldParams["informAbility[=]"]}'/>"
					   </script>
					</td>
				</tr>
				<tr height="35">
					<td align="right">品种名称：</td>
					<td class="cd_list1">
						<select id="cname" name="_commodityId[=]" style="width:120" class="form_k">
							<option value="">全部</option>
							<c:forEach items="${commodityList}" var="result">
			                <option value="<c:out value='${result.id}'/>">${result.name}</option>
			                </c:forEach>
						</select>&nbsp;
						<script type="text/javascript">
							frm.cname.value = "<c:out value='${oldParams["commodityId[=]"]}'/>";
						</script>
					</td>
					<td align="right">仓库名称：</td>
					<td align="left">
						<select id="warehouseName" name="_warehouseId[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${warehouseList}" var="result">
			                <option value="${result.id}">${result.name}</option>
			                </c:forEach>	
						</select>
						<script type="text/javascript">
							frm.warehouseName.value = "<c:out value='${oldParams["warehouseId[=]"]}'/>";
						</script>
					</td>
					<td colspan=2>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	 
	 <%@ include file="enterInformTable.jsp"%>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	function doQuery(){
		frm.submit();
	}
	function fView(vid){
		frm.enterInformId.value = vid;
		frm.action = "<%=basePath%>servlet/enterInformController.wha?funcflg=enterInformView";
		frm.submit();
	}
	function resetForm(){
		frm.id.value = "";
		frm.warehouseName.options[0].selected = true;
		frm.cname.options[0].selected = true;
		frm.ability.options[0].selected = true;
		frm.firmId.value="";
		frm.submit();
	}
</SCRIPT>
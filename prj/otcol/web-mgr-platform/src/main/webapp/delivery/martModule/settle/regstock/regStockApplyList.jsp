<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/regStockApplyController.${POSTFIX}?funcflg=regStockApplyList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>注册仓单申请查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">仓单申请号：</td>
					<td align="left">
						<input type="text" name="_ApplyID[like]" id="applyId" value="<c:out value='${oldParams["ApplyID[like]"]}'/>" class="text" style="width: 120px"
onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">${FIRMID }：</td>
					<td align="left">
						<input type="text" name="_FirmID[like]" id="firmId" value="<c:out value='${oldParams["FirmID[like]"]}'/>" class="text" style="width: 120px"
onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请时间：
						<MEBS:calendar eltID="applyTime" eltName="_trunc(ApplyTime)[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(ApplyTime)[=][date]"] }'/>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">仓单状态：</td>
					<td align="left">
						<select name="_Status[=]" class="normal" id="regStockStatus" style="width: 120px">
							<option value="">全部</option>
							<option value="1">未审核</option>
							<option value="2">已审核</option>
							<option value="-2">驳回</option>
						</select>
						<script type="text/javascript">
							document.getElementById("regStockStatus").value="<c:out value='${oldParams["Status[=]"]}'/>";
						</script>
					</td>
					<td align="right">仓库名称：</td>
					<td align="left">
						<select id="warehouseId" name="_WarehouseID[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${warehouseList }" var="warehouse">
								<OPTION value="${warehouse.id }">${warehouse.name }</OPTION>
							</c:forEach>
						</select>
						<script>
							frm.warehouseId.value = "<c:out value='${oldParams["WarehouseID[=]"]}'/>";
					   </script>						
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品种名称：
						<select id="breedId" name="_BreedID[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${breedList }" var="list">
								<OPTION value="${list.id }">${list.name }</OPTION>
							</c:forEach>
						</select>
						<script>
							frm.breedId.value = "<c:out value='${oldParams["BreedID[=]"]}'/>";
					   </script>						
					</td>
					</tr>
					<tr>
					<td colspan="4">&nbsp;</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	  <%@include file="regStockApplyTable.jsp" %>
	<INPUT TYPE="hidden" NAME="tag" value="">
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function initQuery()
	{
		if( frm.pageSize.value == "" || frm.pageSize.value == "null"){
			doQuery();
		}
	}
	function doQuery(){
		frm.submit();
	}
	function viewOprLog(logid){
		frm.action = "<%=basePath%>servlet/regStockApplyController.${POSTFIX}?funcflg=regStockApplyView&id="+logid;
		frm.submit();
	}
	function resetForm(){
		frm.applyId.value = "";
		frm.firmId.value = "";
		frm.applyTime.value = "";
		frm.regStockStatus.options[0].selected = true;
		frm.warehouseId.options[0].selected = true;
		frm.breedId.options[0].selected = true;
		frm.submit();
	}
//-->
</SCRIPT>


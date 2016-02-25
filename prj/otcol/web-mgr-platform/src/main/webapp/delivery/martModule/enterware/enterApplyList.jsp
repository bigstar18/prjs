<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/enterApplyController.${POSTFIX}?funcflg=enterApplyList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="enterApplyId" name="enterApplyId">
		<fieldset width="95%">
			<legend>入库申请单查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">申请单号：</td>
					<td align="left">
						<input id="queryId" name="_id[like]" value="<c:out value='${oldParams["id[like]"]}'/>" type=text class="text" style="width: 120px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
				 <td align="right">申请时间：</td>
					<td align="left">
						<MEBS:calendar eltID="createdate" eltName="_trunc(createdate)[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(createdate)[=][date]"]}'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;申请单状态：
						<select id="ability" name="_ability[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							 <c:forEach items="${statusList}" var="result">
			                <option value="${result.value}">${result.name}</option>
			                </c:forEach>
							
						</select>
						<script>
						frm.ability.value = "<c:out value='${oldParams["ability[=]"]}'/>"
					   </script>
					</td>
				</tr>
				<tr height="35">
					<td align="right">品种名称：</td>
					<td align="left">
						<select id="commodity" name="_commodityId[=]" style="width:120" class="form_k">
							<option value="">全部</option>
							<c:forEach items="${commodityList}" var="result">
			                <option value="${result.id}">${result.name}</option>
			                </c:forEach>
						</select>&nbsp;
						<script>
						frm.commodity.value = "<c:out value='${oldParams["commodityId[=]"]}'/>"
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
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>
							
					</td>
					
				</tr>
				
			</table>
		</fieldset>
	  <%@ include file="enterApplyTable.jsp"%>
	
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">添加入库申请</button>
			</div></td>
        </tr>
    </table>
	
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery(){
		frm.submit();
	}
	function fAuditing(vid){
		frm.enterApplyId.value=vid;
		frm.action = "<%=basePath%>servlet/enterApplyController.${POSTFIX}?funcflg=enterApplyView";
		frm.submit();
	}
	function resetForm(){
		frm.queryId.value = "";
		frm.createdate.value="";
		frm.ability.options[0].selected = true;
		frm.warehouseName.options[0].selected = true;
		frm.commodity.value="";
		frm.submit();
	}
	
	function add(){
		frm.action="<%=basePath%>servlet/enterApplyController.${POSTFIX}?funcflg=enterApplyForward";
		frm.submit();
	}

	
//-->
</SCRIPT>


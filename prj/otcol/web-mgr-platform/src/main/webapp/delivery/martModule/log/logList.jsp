<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>  
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/operateLogController.${POSTFIX}?funcflg=operateLogsList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<INPUT TYPE="hidden" NAME="tag" value="">
		<fieldset width="95%">
			<legend>交收日志查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">操作人类型：</td>
					<td align="left">
						<select name="_popedom[=]" id="type" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<option value="0">市场</option>
							<option value="1">仓库</option>
							<option value="9">交易商</option>							
						</select>
						<script>
							document.getElementById("type").value = "<c:out value='${oldParams["popedom[=]"]}'/>";
					   </script>
					</td>
					<td align="right">操作人编号：</td>
					<td align="left">
						<input type="text" name="_userid[like]" id="operator" value="<c:out value='${oldParams["userid[like]"]}'/>" class="text" style="width: 120px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">操作时间：</td>
					<td align="left">
						<MEBS:calendar eltID="operatime" eltName="_trunc(operatetime)[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(operatetime)[=][date]"]}'/>"/>
					</td>
					<td align="right">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	  	 <%@ include file="logTable.jsp"%>
	
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
		frm.action = "<%=basePath%>servlet/operateLogController.${POSTFIX}?funcflg=operateLogView&id="+logid;
		frm.submit();
	}
	function resetForm(){
		document.getElementById("operator").value = "";
		frm.operatime.value = "";
		document.getElementById("type").options[0].selected = true;
		frm.submit();
	}
//-->
</SCRIPT>


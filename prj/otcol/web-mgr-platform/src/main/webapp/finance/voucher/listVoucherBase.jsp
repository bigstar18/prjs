<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>凭证列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function init(){
			if(frm_query.pageSize.value == ""){ 
				//frm_query.pageSize.value = "15";
				frm_query.b_Date1.value = "<%=nowDate%>";
				frm_query.b_Date2.value = "<%=nowDate%>";
				//frm_query.submit();
			}
			changeOrder(sign);
		}
		function doQuery(){
			if(!checkValue("frm_query")) {
				return;
			}
			if (frm_query.b_Date1.value>frm_query.b_Date2.value) {
				alert("开始日期不能大于结束日期！");
				return;
			}
			frm_query.submit();
		}
		function resetForm(){
			frm_query.summary.value = "";
			frm_query.voucherNo.value = "";
			frm_query.summaryNo.value = "";
			frm_query.b_Date1.value = "<%=nowDate%>";
			frm_query.b_Date2.value = "<%=nowDate%>";
		}
		function editInfo(vVoucherNo){
			var returnValue = openDialog("<%=basePath%>/voucherController.spr?funcflg=voucherViewForward&sign=view&voucherNo=" + vVoucherNo+"&sign=2" ,"_blank",900,550);
				  if(returnValue)
				     frm_query.submit();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/voucherController.spr?funcflg=voucherBaseList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>凭证库查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="b_Date1" eltName="_b_Date[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_Date[>=][date]"]}'/>"/>
					</td>
					<td align="right">结束日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="b_Date2" eltName="_b_Date[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_Date[<=][date]"]}'/>"/>
					</td>
					<td align="right"><%=VOUCHERNO%>：&nbsp;</td>
					<td align="left">
						<input id="voucherNo" name="_voucherNo[like]" value="<c:out value='${oldParams["voucherNo[like]"]}'/>" reqfv="req_num;0;0;凭证号" type=text class="text" maxlength="10" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					</tr>
				<tr height="35">
					
					<td align="right">凭证摘要号：</td>
					<td align="left">
					<input id="summaryNo" name="_summaryNo[like]" value="<c:out value='${oldParams["summaryNo[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">凭证摘要：&nbsp;</td>
					<td align="left">
						<input id="summary" name="_summary[like]" value="<c:out value='${oldParams["summary[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
					
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" action="" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="voucherNo"><%=VOUCHERNO%></td>
				<td class="panel_tHead_MB" abbr="b_date">凭证日期</td>
				<td class="panel_tHead_MB" abbr="summaryNo">凭证摘要号</td>
				<td class="panel_tHead_MB" abbr="summary">凭证摘要</td>
				<!-- <td class="panel_tHead_MB">凭证类型</td> -->
				<td class="panel_tHead_MB" abbr="inputUser">录入员</td>
				<td class="panel_tHead_MB" abbr="inputTime">审核时间</td>
				<td class="panel_tHead_MB" abbr="auditor">审核员</td>
				<td class="panel_tHead_MB" abbr="auditTime">审核时间</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.voucherNo}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.voucherNo}"/></span></td>
	  			<td class="underLine"><fmt:formatDate value='${result.b_date}'/></td>
	  			<td class="underLine"><c:out value="${result.summaryNo}"/></td>
	  			<td class="underLine"><c:out value="${result.summary}"/></td>
	  			<td class="underLine"><c:out value="${result.inputUser}"/></td>
	  			<td class="underLine"><fmt:formatDate value="${result.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="underLine"><c:out value="${result.auditor}"/></td>
	  			<td class="underLine"><fmt:formatDate value="${result.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
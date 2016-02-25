<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %> 
    <title>科目列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
			  changeOrder(sign);  
		}
		function createNew(){
			var returnValue = openDialog("<%=basePath%>/voucherController.spr?funcflg=summaryCreatForward","_blank",500,285);
			if(returnValue)
			    frm_query.submit();
				//window.location.reload();
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.summaryNo.value = "";
			frm_query.summary.value = "";
			frm_query.summaryNol.value="";//重置
			frm_query.ledgerItem.value="";//重置
			//frm_query.voucherType.value = "";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/voucherController.spr?funcflg=summaryEditForward&summaryNo="+vCode ,"_blank",500,285);
			if(returnValue)
			    frm_query.submit();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/voucherController.spr?funcflg=summaryList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>摘要查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right"><%=SUMMARYNO%>：&nbsp;</td>
					<td align="left">
						<input id="summaryNo" name="_summaryNo[=]" value="<c:out value='${oldParams["summaryNo[=]"]}'/>" type=text class="text" style="width: 80px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">摘要名称：&nbsp;</td>
					<td align="left">
						<input id="summary" name="_summary[=]" value="<c:out value='${oldParams["summary[=]"]}'/>" type=text  class="text" style="width: 80px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">系统：&nbsp;</td>
					<td align="left">
						<select id="summaryNol" name="_summaryNo[like]" class="normal" style="width: 100px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${moduleList}" var="result">
			                <option value="${result.moduleid}%">${result.name}</option>
			                </c:forEach>
						</select>
					</td>
					<script>
						frm_query.summaryNol.value = "<c:out value='${oldParams["summaryNo[like]"]}'/>"
					</script>
					<td align="right">归入总帐：&nbsp;</td>
					<td align="left">
						<select id="ledgerItem" name="_ledgerItem[like]" class="normal" style="width: 100px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${fieldList}" var="result">
			                <option value="${result.code}">${result.name}</option>
			                </c:forEach>
						</select>
					</td>
					<script>
						frm_query.ledgerItem.value = "<c:out value='${oldParams["ledgerItem[like]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" action="<%=basePath%>/voucherController.spr?funcflg=summaryDelete" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="summaryNo"><%=SUMMARYNO%></td>
				<td class="panel_tHead_MB" abbr="summary"><%=DUMMARYNAME%></td>
				<td class="panel_tHead_MB" abbr="ledgerItem">归入总账</td>
				<td class="panel_tHead_MB" abbr="fundDCFlag">资金借贷方向</td>
				<td class="panel_tHead_MB" abbr="accountCodeOpp">对方科目代码</td>
				<td class="panel_tHead_MB" abbr="appendAccount">附加帐</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.summaryNo}"/>">
	  			</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.summaryNo}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.summaryNo}"/></span></td>
	  			<td class="underLine"><c:out value="${result.summary}"/></td>
	  			<td class="underLine"><c:out value="${result.ledgerItem}"/>&nbsp;</td>
	  			<td class="underLine">
	  			<c:if test="${result.fundDCFlag=='C'}">记贷方</c:if>
			    <c:if test="${result.fundDCFlag=='D'}">记借方</c:if>
				<c:if test="${result.fundDCFlag=='N'}">不涉及</c:if>
	  			</td>
	  			<td class="underLine"><c:out value="${result.accountCodeOpp}"/>&nbsp;</td>
	  			<td class="underLine">
	  			<c:if test="${result.appendAccount=='T'}">增值税</c:if>
			    <c:if test="${result.appendAccount=='W'}">担保金</c:if>
				<c:if test="${result.appendAccount=='N'}">无附加</c:if>
	  			</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="createNew()">添加</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="deleteRec(frm_delete,tableList,'delCheck')">删除</button>
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
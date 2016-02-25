<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <c:set var="sign" value='${sign}'/>
    <!-- sign:edit confirm audit view -->
	<c:if test='${sign == null}'>
		<c:set var='sign' value='view'/>
	</c:if>
    <title>凭证列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	 
	    function init(){
			changeOrder(sign); 
		}
		function createNew(){
			window.location = "<%=basePath%>/voucher/createVoucher.jsp";
		}
		function doQuery(){
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
		function resetForm(){
			frm_query.voucherNo.value = "";
			frm_query.summary.value = "";
			frm_query.summaryNo.value = "";
		    frm_query.status.value="";
		    frm_query.accountCode.value="";
		    frm_query.inputUser.value="";
		    frm_query.auditor.value="";
		}
		function editInfo(vVoucherNo){
		<c:choose>
			<c:when test='${sign == "edit"}'>
			window.location = "<%=basePath%>/voucherController.spr?funcflg=voucherEditForward&sign=<c:out value='${sign}'/>&voucherNo=" + vVoucherNo;
			</c:when>
			<c:otherwise>
			    <c:choose>
				<c:when test='${sign == "audit"}'>
				window.location = "<%=basePath%>/voucherController.spr?funcflg=voucherViewForward&sign=<c:out value='${sign}'/>&voucherNo=" + vVoucherNo+"&sign=1";
				</c:when>
	  			<c:otherwise>
				var returnValue = openDialog("<%=basePath%>/voucherController.spr?funcflg=voucherViewForward&sign=<c:out value='${sign}'/>&voucherNo=" + vVoucherNo+"&sign=1" ,"_blank",900,550);
				  if(returnValue)
				     frm_query.submit();
					//window.location.reload();
			    </c:otherwise>
	  			</c:choose>
			</c:otherwise>
		</c:choose>
		}
		function deleteVoucher(){
			frm_delete.action = "<%=basePath%>/voucherController.spr?funcflg=voucherDelete";
			deleteRec(frm_delete,tableList,'delCheck');
		}
		function submitAllAudit(){
			if(confirm("全部提交审核将把所有编辑状态的凭证提交审核，确认提交？"))
			{
				frm_delete.action = "<%=basePath%>/voucherController.spr?funcflg=submitAuditVoucher&all=true";
				frm_delete.submit();
			}
		}
		function submitAudit(){
		　　　if(isSelNothing(tableList,'delCheck') == -1)
	　　　　{
		　　　　　alert ( "没有可以操作的数据！" );
		　　　　　return;
	　　　　}
	　　　　　　if(isSelNothing(tableList,'delCheck'))
	　　　　{
		　　　　　alert ( "请选择需要操作的数据！" );
		　　　　　　return;
	　　　　}
			　if(confirm("提交审核将把选中的凭证提交审核，凭证提交审核后将不能修改，确认提交？"))
		　　{
				frm_delete.action = "<%=basePath%>/voucherController.spr?funcflg=submitAuditVoucher";
				frm_delete.submit();
		　　}
		}
		
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/voucherController.spr?funcflg=voucherList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="sign" name="sign" value="<c:out value='${sign}'/>">
		<c:if test='${sign=="edit" or sign=="audit" or sign=="confirm"}'>
		<input type="hidden" name="_status[like]" value="<c:out value='${oldParams["status[like]"]}'/>">
		</c:if>
		<c:if test='${sign=="edit"}'>
		<input type="hidden" name="_inputUser[like]" value="<c:out value='${logonUser[like]}'/>">
		</c:if>
		<fieldset width="100%">
			<legend>凭证查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right"><%=VOUCHERNO%>：&nbsp;</td>
					<td align="left">
						<input id="voucherNo" name="_voucherNo[like]" value="<c:out value='${oldParams["voucherNo[like]"]}'/>" type=text reqfv="req_num;0;0;凭证号" class="text" maxlength="10" style="width: 100px" onkeypress=" onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right"><%=SUMMARYNO%>：&nbsp;</td>
					<td align="left">
						<input id="summaryNo" name="_summaryNo[like]" value="<c:out value='${oldParams["summaryNo[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">摘要：&nbsp;</td>
					<td align="left">
						<input id="summary" name="_summary[like]" value="<c:out value='${oldParams["summary[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="right">凭证状态&nbsp;</td>
					<td align="left">
						<select id="status" name="_status[=]" class="normal" style="width: 80px">
							<OPTION value="">全部</OPTION>
							<option value="editing">编辑中</option>
              				<option value="auditing">待审核</option>
              				<option value="audited">已审核</option>
						</select>
					</td>
					<script>
						frm_query.status.value = "<c:out value='${oldParams["status[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
				
				<tr  height="35">
				<td align="right"><%=BITCODE%>&nbsp;</td>
					<td align="left">
						<input id="accountCode" name="accountCode" value="<c:out value='${accountCode}'/>" type=text  class="text" style="width: 100px">
					</td>
				<td align="right">录入员&nbsp;</td>
					<td align="left">
						<input id="inputUser" name="_inputUser[like]" value="<c:out value='${oldParams["inputUser[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
				<td align="right">审核员&nbsp;</td>
					<td align="left">
						<input id="auditor" name="_auditor[like]" value="<c:out value='${oldParams["auditor[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					<td colspan="4"></td>
				</tr>
				
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" action="" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><c:if test='${sign == "edit" or sign == "confirm"}'><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></c:if></td>
				<td class="panel_tHead_MB" abbr="voucherNo"><%=VOUCHERNO%></td>
				<td class="panel_tHead_MB" abbr="summaryNo"><%=SUMMARYNO%></td>
				<td class="panel_tHead_MB" abbr="summary">凭证摘要</td>
				<td class="panel_tHead_MB" abbr="status">凭证状态</td>
				<td class="panel_tHead_MB" abbr="inputUser">录入员</td>
				<td class="panel_tHead_MB" abbr="inputTime">录入时间</td>
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
	  				<c:if test='${sign == "edit" or sign == "confirm"}'><input name="delCheck" type="checkbox" value="<c:out value="${result.voucherNo}"/>"></c:if>
	  			</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.voucherNo}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.voucherNo}"/></span></td>
	  			<td class="underLine"><c:out value="${result.summaryNo}"/></td> 
	  			<td class="underLine"><c:out value="${result.summary}"/></td>
	  			<td class="underLine">
	  			<c:choose>
	  				<c:when test='${result.status=="audited"}'>已审核</c:when>
	  				<c:when test='${result.status=="auditing"}'>待审核</c:when>
	  				<c:when test='${result.status=="editing"}'>编辑中</c:when>
	  			</c:choose>
	  			</td>
	  			<td class="underLine"><c:out value="${result.inputUser}"/></td>
	  			<td class="underLine">&nbsp;<fmt:formatDate value="${result.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="underLine"><c:out value="${result.auditor}"/>&nbsp;</td>
	  			<td class="underLine">&nbsp;<fmt:formatDate value="${result.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="9">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="9">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="9"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="90%">
	    <tr height="35" >
            <td><div align="right">
            <c:choose>
            <c:when test='${sign == "edit"}'>
              <button class="mdlbtn" type="button" onclick="createNew();">添加</button>&nbsp;&nbsp;
  			  <button class="mdlbtn" type="button" onclick="deleteVoucher();">删除</button>
  			</c:when>
  			<c:when test='${sign == "confirm"}'>
  			  <button class="mdlbtn" type="button" onclick="submitAudit();">提交审核</button>&nbsp;&nbsp;
  			  <button class="lgrbtn" type="button" onclick="submitAllAudit();">全部提交审核</button>
  			</c:when>
  			</c:choose>
            </div></td>
        </tr>
    </table>
  </body>
</html>

<%@ include file="../public/footInc.jsp" %>
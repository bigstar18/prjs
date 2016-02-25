<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.unit.Voucher' %>
<%
	String voucherNo = request.getParameter("voucherNo");
%>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %> 
	<c:if test="${voucher==null}">
		<c:redirect url="../error.jsp?errorMsg=VoucherNotExist"/>
	</c:if>    
	<title>修改凭证</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/voucher/voucher.js"></script>
	<script>
	
		function initData(){
			trClone = document.all.tableList.children[1].children[0].cloneNode(true);
			//清空克隆的tr
			trClone.children[1].children[0].checked = false;//entryId_check
			trClone.children[1].children[1].value = '';//entryId
			trClone.children[2].children[0].value = '';
			trClone.children[3].children[0].value = '';
			trClone.children[4].children[0].value = '';
			trClone.children[5].children[0].value = '0';//debitAmount
			trClone.children[6].children[0].value = '0';//creditAmount
			//显示借贷金额合计
			computeSum();
		}
		function audit( vIsPass ){
			if( vIsPass == "true" )
				msg = "确认审核【通过】？审核通过后凭证将不能修改！";
			else
				msg = "确认审核【失败】？审核失败后凭证将变为编辑状态！";
			if(confirm( msg )){
				formAudit.isPass.value = vIsPass;
				formAudit.submit();
			}
		}
		function gotoAuditVoucherList(){
			window.location = '<%=basePath%>/voucherController.spr?funcflg=voucherList&sign=audit&_status[%3D]=auditing';
		}
		function gotoEditVoucherList(){
			window.location = '<%=basePath%>/voucherController.spr?funcflg=voucherList&sign=edit&_status[%3D]=editing';
		}
		function printToPDF(){
			openWin("printVoucher.jsp?voucherNo=<%=voucherNo%>","_blank",800,600);
		}
	</script> 
</head>
<body onload="initData();">
	<c:if test='${sign == "audit"}'>
		<form id="formAudit" name="formAudit" action="<%=basePath%>/voucherController.spr?funcflg=auditVoucher" method="POST" targetType="hidden" callback="gotoAuditVoucherList();">
			<input name="voucherNo" value="<%=voucherNo%>" type="hidden">
			<input name="isPass" value="" type="hidden">
		</form>
	</c:if>
     <form id="formNew" name="formNew" action="<%=basePath%>/voucherController.spr?funcflg=voucherMod" method="POST" targetType="hidden" callback="gotoEditVoucherList();">
        <input name="voucherNo" value="<%=voucherNo%>" type="hidden">
		<fieldset width="100%">
		<legend>凭证 <%=voucherNo%> 基本信息</legend>
		<span id="voucherSpan">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 凭证日期 ：</td>
                <td align="left">
                	<input name="voucherDate" type="text" value="<c:out value='${voucher.voucherDate}'/>" class="text" style="width: 100px;"  readonly> <!-- reqfv="required;凭证日期" -->
                </td>
                <td align="right"> <%=SUMMARYNO%> ：</td>
                <td align="left">
                	<input id="summaryNo" name="summaryNo" mapId="summaryNo" type="text" value="<c:out value='${voucher.summaryNo}'/>" class="text" style="width: 100px;" onchange="changeSummaryNo(this.value)" >
                </td>
                <td align="right"> 凭证摘要 ：</td>
                <td align="left">
                	<input id="summary" name="summary" mapId="summary" type="text" value="<c:out value='${voucher.summary}'/>" class="text" style="width: 150px;" reqfv="required;凭证摘要">
                	<button class="smlbtn" onclick="selectSummary(voucherSpan)">选择</button>
                </td>
              </tr>
              <tr height="35">
                <!-- <td align="right"> 凭证类型 ：</td>
                <td align="left">
                	<input id="voucherType" name="voucherType" mapId="voucherType"  type="text" class="text" style="width: 100px;" readonly>
                </td> -->
                <input type="hidden" id="voucherType" name="voucherType" mapId="voucherType" >
				<td align="right"> <%=CONTRACTNO%> ：</td>
                <td align="left">
                	<input id="contractNo" name="contractNo" type="text" value="<c:out value='${voucher.contractNo}'/>" class="text" style="width: 100px;">
                </td>
                <td align="right"> 附加说明 ：</td>
                <td align="left">
                	<input name="note" type="text" value="<c:out value='${voucher.note}'/>" class="text" style="width: 200px;">
                </td>
              </tr>
        	</table>
        </span>
		</fieldset>
		<br>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
	  		<tHead>
	  			<tr height="25">
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB">
					<c:if test='${sign == "edit"}'>
					<input type="checkbox" id="checkAll" onclick="selectAll(tableList,'entryId_check')">
					</c:if>
					</td>
					<td class="panel_tHead_MB">分录摘要</td>
					<td class="panel_tHead_MB"><%=BITCODE%></td>
					<td class="panel_tHead_MB"><%=BITCODENAME%></td>
					<td class="panel_tHead_MB">借方金额</td>
					<td class="panel_tHead_MB">贷方金额</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<c:forEach items="${voucher.voucherEntrys}" var="entry">
		  		<tr onclick="selectTr();">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">
		  			<input name="entryId_check" type="checkbox" <c:if test='${sign != "edit"}'>style="display:none"</c:if> >
		  			<input name="entryId" value="<c:out value='${entry.entryId}'/>" type="hidden">
		  			</td>
		  			<td class="underLine"><input name="entrySummary" value="<c:out value='${entry.entrySummary}'/>" type="text" class="text" style="width: 200px;"></td>
		  			<td class="underLine"><input name="accountCode" value="<c:out value='${entry.accountCode}'/>" type="text" class="text" style="width: 100px;" reqfv="required;科目代码" onchange="changeAccountCode(this.value);"></td>
		  			<td class="underLine"><input name="accountName" value="<c:out value='${entry.accountName}'/>" type="text" class="readonly" style="width: 200px;" readonly></td>
		  			<td class="underLine"><input name="debitAmount" value="<c:out value='${entry.debitAmount}'/>" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;借方金额" value="0" onchange="computeSum();"></td>
		  			<td class="underLine"><input name="creditAmount" value="<c:out value='${entry.creditAmount}'/>" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;贷方金额" value="0" onchange="computeSum();"></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
		  	</c:forEach>
		  	</tBody>
		  	<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="6">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="4" align="right" class="pager">合计：</td>
					<td align="right" class="pager"><span id="debitSum">0.00</span></td>
					<td align="right" class="pager"><span id="creditSum">0.00</span></td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="6"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>
		</table>
     </form>
     <table border="0" cellspacing="0" cellpadding="0" width="90%">
          <tr height="35">
          	<td width="40%"><div align="right">
          	<c:choose>
            <c:when test='${sign == "edit"}'>
              <button class="smlbtn" type="button" onclick="addEntry();">增加分录</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="delEntry();">删除分录</button>&nbsp;&nbsp;
              <button class="smlbtn" type="button" onclick="voucherSubmit();">提交</button>&nbsp;&nbsp;
              <button class="mdlbtn" type="button" onclick="gotoEditVoucherList();">返回到列表</button>
  			</c:when>
  			<c:when test='${sign == "audit"}'>
  			  <button class="smlbtn" type="button" onclick="audit('true');">审核通过</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="audit('false');">审核失败</button>&nbsp;&nbsp;
   			  <button class="mdlbtn" type="button" onclick="gotoAuditVoucherList();">返回到列表</button>
  			</c:when>  
  			</c:choose>
            </div></td>
          </tr>
     </table>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>
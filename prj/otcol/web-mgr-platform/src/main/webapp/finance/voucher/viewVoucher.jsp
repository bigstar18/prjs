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
	<title>凭证</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
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
			window.location = '<%=basePath%>/voucherController.spr?funcflg=voucherList&sign=audit';
		}
		function printToPDF(){
			openWin("printVoucher.jsp?voucherNo=<%=voucherNo%>","_blank",800,600);
		}
		function returnList(sign){
		   window.close();
		}
	</script> 
</head>
<body>
	<c:if test='${sign == "audit"}'>
		<form id="formAudit" name="formAudit" action="<%=basePath%>/voucherController.spr?funcflg=auditVoucher" method="POST" targetType="hidden" callback="gotoAuditVoucherList();">
			<input name="voucherNo" value="<%=voucherNo%>" type="hidden">
			<input name="isPass" value="" type="hidden">
		</form>
	</c:if>
		<fieldset width="100%">
		<legend>凭证基本信息</legend>
		<span id="voucherSpan">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
			    <td align="right"> <%=VOUCHERNO%> ：</td>
			    <td align="left"><c:out value='${voucher.voucherNo}'/></td>
            	<td align="right"> 凭证日期 ：</td>
                <td align="left"><c:out value='${voucher.voucherDate}'/></td>
                <td align="right"> <%=SUMMARYNO%> ：</td>
                <td align="left"><c:out value='${voucher.summaryNo}'/></td>
                <td align="right"> 凭证摘要 ：</td>
                <td align="left"><c:out value='${voucher.summary}'/></td>
              </tr>
              <tr height="35">
				<td align="right"> <%=CONTRACTNO%> ：</td>
                <td align="left"><c:out value='${voucher.contractNo}'/></td>
                <td align="right"> 附加说明 ：</td>
                <td align="left" colspan="3"><c:out value='${voucher.note}'/></td>
              </tr>
        	</table>
        </span>
		</fieldset>
		<br>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
	  		<tHead>
	  			<tr height="25">
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB">分录摘要</td>
					<td class="panel_tHead_MB"><%=BITCODE%></td>
					<td class="panel_tHead_MB"><%=BITCODENAME%></td>
					<td class="panel_tHead_MB_Curr">借方金额</td>
					<td class="panel_tHead_MB_Curr">贷方金额</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<c:set var="todayDebitAmountSum" value="0"/>
			<c:set var="todayCreditAmountSum" value="0"/>
			<c:forEach items="${voucher.voucherEntrys}" var="entry">
				<c:set var="debitAmountSum" value="${debitAmountSum + entry.debitAmount}"/>
				<c:set var="creditAmountSum" value="${creditAmountSum + entry.creditAmount}"/>
		  		<tr onclick="selectTr();" height="22">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">&nbsp;<c:out value='${entry.entrySummary}'/></td>
		  			<td class="underLine"><c:out value='${entry.accountCode}'/></td>
		  			<td class="underLine"><c:out value='${entry.accountName}'/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value='${entry.debitAmount}' pattern="#,##0.00"/></td>
		  			<td class="underLineCurr"><fmt:formatNumber value='${entry.creditAmount}' pattern="#,##0.00"/></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
		  	</c:forEach>
		  	</tBody>
		  	<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="5">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="3" align="right" class="pager">合计：</td>
					<td align="right" class="pager"><span id="debitSum"><fmt:formatNumber value='${debitAmountSum}' pattern="#,##0.00"/></span></td>
					<td align="right" class="pager"><span id="creditSum"><fmt:formatNumber value='${creditAmountSum}' pattern="#,##0.00"/></span></td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="5"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="90%">
          <tr height="35">
          	<td width="40%"><div align="right">
          	<!-- <button class="smlbtn" type="button" onclick="printToPDF();">PDF打印</button>&nbsp;&nbsp; -->
          	<c:choose>
  			<c:when test='${sign == "audit"}'>
  			  <button class="smlbtn" type="button" onclick="audit('true');">审核通过</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="audit('false');">审核失败</button>&nbsp;&nbsp;
   			  <button class="mdlbtn" type="button" onclick="gotoAuditVoucherList();">返回到列表</button>
  			</c:when>
  			<c:otherwise>
  				<button class="smlbtn" type="button" onclick="returnList('${param.sign}')">关闭</button>
  			</c:otherwise>
  			</c:choose>
            </div></td>
          </tr>
     </table>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>
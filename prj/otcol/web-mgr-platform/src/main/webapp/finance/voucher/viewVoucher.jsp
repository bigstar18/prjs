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
	<title>ƾ֤</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function audit( vIsPass ){
			if( vIsPass == "true" )
				msg = "ȷ����ˡ�ͨ���������ͨ����ƾ֤�������޸ģ�";
			else
				msg = "ȷ����ˡ�ʧ�ܡ������ʧ�ܺ�ƾ֤����Ϊ�༭״̬��";
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
		<legend>ƾ֤������Ϣ</legend>
		<span id="voucherSpan">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
			    <td align="right"> <%=VOUCHERNO%> ��</td>
			    <td align="left"><c:out value='${voucher.voucherNo}'/></td>
            	<td align="right"> ƾ֤���� ��</td>
                <td align="left"><c:out value='${voucher.voucherDate}'/></td>
                <td align="right"> <%=SUMMARYNO%> ��</td>
                <td align="left"><c:out value='${voucher.summaryNo}'/></td>
                <td align="right"> ƾ֤ժҪ ��</td>
                <td align="left"><c:out value='${voucher.summary}'/></td>
              </tr>
              <tr height="35">
				<td align="right"> <%=CONTRACTNO%> ��</td>
                <td align="left"><c:out value='${voucher.contractNo}'/></td>
                <td align="right"> ����˵�� ��</td>
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
					<td class="panel_tHead_MB">��¼ժҪ</td>
					<td class="panel_tHead_MB"><%=BITCODE%></td>
					<td class="panel_tHead_MB"><%=BITCODENAME%></td>
					<td class="panel_tHead_MB_Curr">�跽���</td>
					<td class="panel_tHead_MB_Curr">�������</td>
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
					<td colspan="3" align="right" class="pager">�ϼƣ�</td>
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
          	<!-- <button class="smlbtn" type="button" onclick="printToPDF();">PDF��ӡ</button>&nbsp;&nbsp; -->
          	<c:choose>
  			<c:when test='${sign == "audit"}'>
  			  <button class="smlbtn" type="button" onclick="audit('true');">���ͨ��</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="audit('false');">���ʧ��</button>&nbsp;&nbsp;
   			  <button class="mdlbtn" type="button" onclick="gotoAuditVoucherList();">���ص��б�</button>
  			</c:when>
  			<c:otherwise>
  				<button class="smlbtn" type="button" onclick="returnList('${param.sign}')">�ر�</button>
  			</c:otherwise>
  			</c:choose>
            </div></td>
          </tr>
     </table>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>
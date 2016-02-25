<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.ViewManager' %>
<%@ page import='gnnt.MEBS.finance.manager.AccountManager' %>
<%@ page import='gnnt.MEBS.finance.unit.Account' %>
<%@ page import='java.util.List' %>
<%
	String accountCode = request.getParameter("accountCode");
	String contractNo = request.getParameter("contractNo");
	List list = null;
	Account account = null;

	if(accountCode == null)
		accountCode = "202";
	if(contractNo != null){ 
		account = AccountManager.getAccountByCode(accountCode);
		list = ViewManager.queryContractAccount(accountCode,contractNo);
	} 
	pageContext.setAttribute( "accountCode", accountCode );
	pageContext.setAttribute( "account", account );
	pageContext.setAttribute( "contractNo", contractNo );
	pageContext.setAttribute( "resultList", list );
%>
<html> 
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>ƾ֤�б�</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function resetForm(){
			frm_query.accountCode.value = "202";
			frm_query.contractNo.value = "";
		}
		function init(){
			if(frm_query.accountCode.value == null || frm_query.accountCode.value == '')
				frm_query.accountCode.value = '202';
		}
		function doQuery(){
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" method="post">
		<fieldset width="95%">
			<legend>��ͬ�����˲�ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="500" height="35">
				<tr height="35">
					<td align="right"><%=BITCODE%>��&nbsp;</td>
					<td align="left">
						<input id="accountCode" name="accountCode" value="<c:out value='${accountCode}'/>" type=text class="text" style="width: 100px" reqfv="required;��Ŀ����">
					</td>
					<td align="right"><%=CONTRACTNO%>��&nbsp;</td>
					<td align="left">
						<input id="contractNo" name="contractNo" value="<c:out value='${contractNo}'/>" type=text class="text" style="width: 100px" reqfv="required;�ɽ���ͬ��">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">ƾ֤����</td>
				<td class="panel_tHead_MB">ƾ֤��</td>
				<td class="panel_tHead_MB">ƾ֤ժҪ</td>
				<td class="panel_tHead_MB">�Է���Ŀ</td>
				<td class="panel_tHead_MB">�Է���Ŀ����</td>
				<td class="panel_tHead_MB_Curr2">�跽</td>
				<td class="panel_tHead_MB_Curr2">����&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:set var="debit" value="0"/>
	  		<c:set var="credit" value="0"/>
	  		<c:forEach items="${resultList}" var="result">
		  		<c:set var="debit" value="${debit + result.debitAmount}"/>
	  			<c:set var="credit" value="${credit + result.creditAmount}"/>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value='${result.voucherDate}'/></td>
	  			<td class="underLine"><c:out value="${result.voucherNo}"/></td>
	  			<td class="underLine"><c:out value="${result.voucherSummary}"/></td>
	  			<td class="underLine"><c:out value="${result.accountCode}"/></td>
	  			<td class="underLine"><c:out value="${result.accountName}"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.debitAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr2"><fmt:formatNumber value="${result.creditAmount}" pattern="#,##0.00"/>&nbsp;</td>
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
				<td class="pager" colspan="5" align="right">�ϼƣ�</td>
				<td class="pager" align="right"><fmt:formatNumber value="${debit}" pattern="#,##0.00"/></td>
				<td class="pager" align="right"><fmt:formatNumber value="${credit}" pattern="#,##0.00"/>&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
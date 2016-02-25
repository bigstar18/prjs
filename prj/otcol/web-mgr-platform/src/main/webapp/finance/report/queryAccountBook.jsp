<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>�ʲ���ѯ</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function init(){ 
			
			changeOrder(sign);
		}
		function resetForm(){
			frm_query.creditCode.value = "";
			frm_query.debitCode.value = "";
			frm_query.contractNo.value = "";
			frm_query.voucherNo.value = "";
			frm_query.beginDate.value = "";
			frm_query.endDate.value = "";
			frm_query.summaryNo.value = "";
			frm_query.summary.value = "";
			frm_query.amount.value="";
		}
		function doQuery(){

			var startDate = document.getElementById("beginDate").value;
			var endDate = document.getElementById("endDate").value;
			
			if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
			  {
				if(startDate == ""){
					alert("��ʼ���ڲ���Ϊ�գ�");
					frm_query.beginDate.focus();
					return false;
					
				}
				if(endDate == ""){
					alert("�������ڲ���Ϊ�գ�");
					frm_query.endDate.focus();
					return false;
					
				}
				if(!isDateFomat(startDate))
			    {
			        alert("��ʼ���ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
			        frm_query.beginDate.value = "";
			        frm_query.beginDate.focus();
			        return false;
			    }
			    if(!isDateFomat(endDate))
			    {
			        alert("�������ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
			        frm_query.endDate.value = "";
			        frm_query.endDate.focus();
			        return false;
			    }
			  
			    if ( startDate > '<%=nowDate%>' ) { 
			        alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
			        frm_query.beginDate.focus();
			        return false; 
			    } 
			    if ( startDate > endDate ) { 
			        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
			        return false; 
			    } 
			  }
			
			frm_query.submit();
		}
		//��֤��Ǯ�ĸ�ʽ�Ƿ���ȷ���������ȷ����������롣��ʾ��������
		function reg(){
			var reg=/^[0-9]+\.?[0-9]*$/;//��֤����Ľ���ǲ���ֻ��һ����.��������ǣ��ύʧ��
			if(""!=frm_query.amount.value&&null!=frm_query.amount.value&&!reg.test(frm_query.amount.value)){
				alert("��������ȷ�Ľ��");
				frm_query.amount.value = "";
			}
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryAccountBook" method="post">
	  	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>�ʲ���ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">�跽��Ŀ��&nbsp;</td>
					<td align="left">
						<input id="creditCode" name="_debitCode[like]" value="<c:out value='${oldParams["debitCode[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">������Ŀ��&nbsp;</td>
					<td align="left">
						<input id="debitCode" name="_creditCode[like]" value="<c:out value='${oldParams["creditCode[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">��ͬ�ţ�&nbsp;</td>
					<td align="left">
						<input id="contractNo" name="_contractNo[like]" value="<c:out value='${oldParams["contractNo[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="right"><%=VOUCHERNO%>��&nbsp;</td>
					<td align="left">
						<input id="voucherNo" name="_voucherNo[like]" value="<c:out value='${oldParams["voucherNo[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ƾ֤ժҪ�ţ�&nbsp;</td>
					<td align="left"><input id="summaryNo" name="_summaryNo[like]" value="<c:out value='${oldParams["summaryNo[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">ƾ֤ժҪ��&nbsp;</td>
					<td align="left"><input id="summary" name="_summary[like]" value="<c:out value='${oldParams["summary[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="32">
					</td>
					<td align="right">��&nbsp;</td>
					<td align="left">
						<input id="amount" name="_amount[=]" value="<c:out value='${oldParams["amount[=]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberInput()" onblur="reg();" maxlength="16">
					</td> 
					<td align="right">��ʼ���ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="beginDate" eltName="_b_date[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[>=][date]"]}'/>"/>
					</td>
					<td align="right">�������ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="endDate" eltName="_b_date[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[<=][date]"]}'/>"/>
					</td>
					<td align="center">
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
				<td class="panel_tHead_MB" abbr="b_date">ƾ֤����</td>
				<td class="panel_tHead_MB" abbr="voucherNo"><%=VOUCHERNO%></td>
				<td class="panel_tHead_MB" abbr="summaryNo">ƾ֤ժҪ��</td>
				<td class="panel_tHead_MB" abbr="summary">ƾ֤ժҪ</td>
				<td class="panel_tHead_MB" abbr="debitCode">�跽��Ŀ</td>
				<td class="panel_tHead_MB" abbr="debitName">�跽��Ŀ����</td>
				<td class="panel_tHead_MB" abbr="creditCode">������Ŀ</td>
				<td class="panel_tHead_MB" abbr="creditName">������Ŀ����</td>
				<td class="panel_tHead_MB" abbr="amount">�������</td>
				<td class="panel_tHead_MB" abbr="contractNo">��ͬ��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value='${result.b_date}'/></td>
	  			<td class="underLine"><c:out value="${result.voucherNo}"/></td>
	  			<td class="underLine"><c:out value="${result.summaryNo}"/></td>
	  			<td class="underLine"><c:out value="${result.summary}"/></td>
	  			<td class="underLine"><c:out value="${result.debitCode}"/></td>
	  			<td class="underLine"><c:out value="${result.debitName}"/></td>
	  			<td class="underLine"><c:out value="${result.creditCode}"/></td>
	  			<td class="underLine"><c:out value="${result.creditName}"/></td>
	  			<td class="underLine"><fmt:formatNumber value="${result.amount}" pattern="#,##0.00"/></td>
	  			<td class="underLine"><c:out value="${result.contractNo}"/>&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10">
				<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>ƾ֤�б�</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function init(){
			changeOrder(sign); 
		}
		function doQuery(){
			if(!checkValue("frm_query"))
				return;
			frm_query.submit();
		}
		function resetForm(){
			frm_query.b_date.value = "";
			frm_query.accountLevel.value = "1";
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryDailyBalance" method="post">
	  	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="100%">
			<legend>�����ձ���ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<!-- <td align="right">��������&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="b_date" eltName="_b_date[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[=][date]"]}'/>"/>
					</td> -->
					<td align="right">��ʼ���ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="beginDate" eltName="_b_date[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[>=][date]"]}'/>"/>
					</td>
					<td align="right">�������ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="endDate" eltName="_b_date[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[<=][date]"]}'/>"/>
					</td>
					<td align="right">��Ŀ����&nbsp;</td>
					<td align="left">
						<select id="accountLevel" name="_accountLevel[=]" class="normal" style="width: 80px">
							<option value="1">1</option>
              				<option value="2">2</option>
              				<option value="3">3</option>
              				<OPTION value="">ȫ��</OPTION>
						</select>
					</td>
					<script>
						frm_query.accountLevel.value = "<c:out value='${oldParams["accountLevel[=]"]}'/>"
					</script>
					<td align="right"><%=BITCODE%>��&nbsp;</td>
					<td align="left">
						<input id="accountCode" name="_accountCode[like]" value="<c:out value='${oldParams["accountCode[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberInput()" maxlength="16">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<!-- add by yangpei 2011-11-22 �������ù��� -->
		            	<button type="button" class="smlbtn" onclick="resetForm();">����</button>
		            	<script>
		            		function resetForm(){
		            			frm_query.beginDate.value="";
		            			frm_query.endDate.value="";
		            			frm_query.accountLevel.value="";
		            			frm_query.accountCode.value="";
		            		}
		            	</script>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB" abbr="b_date">����</td>
				<td class="panel_tHead_MB" abbr="accountCode"><%=BITCODE%></td>
				<td class="panel_tHead_MB" abbr="name"><%=BITCODENAME%></td>
				<td class="panel_tHead_MB_Curr" abbr="lastDayBalance">�ڳ����</td>
				<td class="panel_tHead_MB_Curr" abbr="DebitAmount">�跽</td>
				<td class="panel_tHead_MB_Curr" abbr="CreditAmount">����</td>
				<td class="panel_tHead_MB_Curr" abbr="todayBalance">��ĩ���&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.b_date}" pattern="yyyy-MM-dd"/></td>
	  			<td class="underLine"><c:out value="${result.accountCode}"/></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.lastDayBalance}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.DebitAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.CreditAmount}" pattern="#,##0.00"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.todayBalance}" pattern="#,##0.00"/></td>
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
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
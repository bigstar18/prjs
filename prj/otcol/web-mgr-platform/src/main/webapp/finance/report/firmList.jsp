<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.List' %>
<%@ page import='gnnt.MEBS.finance.base.util.*' %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>交易商</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){	    
			changeOrder(sign);
		}
		function doQuery(){
			frm_query.submit();
		}
		function changeBlanceSel(sel){
			if(sel=="eq"){
				frm_query.balanceNe.value="";
				frm_query.balanceEq.value="0";
			} else if(sel=="ne"){
				frm_query.balanceNe.value="0";
				frm_query.balanceEq.value="";
			} else {
				frm_query.balanceNe.value="";
				frm_query.balanceEq.value="";
			}
		}
		
		function openFunds(firmId){
			var returnValue = openDialog("<%=basePath%>/reportController.spr?funcflg=firmFundsForward&firmId="+firmId,"_blank",450,500);
			if(returnValue)
				window.location.reload();
		}
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryFirmBalance" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>平台账户当前资金查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">平台账号：&nbsp;</td>
					<td align="left">
						<input name="_firmId[like]" id="fid" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<!-- add by yangpei 2011-11-22 增加重置功能 -->
		            	<button type="button" class="smlbtn" onclick="resetForm();">重置</button>
		            	<script>
		            		function resetForm(){
		            			frm_query.fid.value="";
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
				<td class="panel_tHead_MB" abbr="firmId">平台账号</td>
				<td class="panel_tHead_MB_Curr" abbr="f_balance">交易系统当前余额</td>
				<td class="panel_tHead_MB_Curr" abbr="l_balance">财务结算余额</td>
				<td class="panel_tHead_MB_Curr" abbr="y_balance">财务未结算金额</td>
				<td class="panel_tHead_MB_Curr" abbr="balanceSubtract">差额&nbsp;&nbsp;</td>
				<td class="panel_tHead_MB_Curr" abbr="frozenFunds">临时资金</td>
				<td class="panel_tHead_MB_Curr" abbr="user_balance">可用资金</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.firmId}"/></td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.f_balance}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.l_balance}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLineCurr"><span onclick="openFunds('<c:out value="${result.firmId}"/>')" style="cursor:hand;color:blue">
	  			<fmt:formatNumber value="${result.y_balance}" pattern="#,##0.00"/>&nbsp;<img src="<%=skinPath%>/ico/053753258.gif" width="15" height="15" align="bottom"/>
	  			</span>
	  			</td>
	  			<td class="underLineCurr">
	  				<SPAN <c:if test="${result.balanceSubtract!=0}">style="color:red"</c:if> >
	  					<fmt:formatNumber value="${result.balanceSubtract}" pattern="#,##0.00"/>
	  				</SPAN>&nbsp;
	  			</td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.frozenFunds}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.user_balance}" pattern="#,##0.00"/>&nbsp;</td>
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
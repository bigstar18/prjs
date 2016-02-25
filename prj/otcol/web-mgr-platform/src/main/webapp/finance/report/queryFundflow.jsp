<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>资金流水</title>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript"> 
	    function init(){
	        if(frm_query.beginDate)
	        {
			if(frm_query.beginDate.value == null || frm_query.beginDate.value == ''){
				frm_query.beginDate.value = '<%=nowDate%>';	
			}
			}
			if(frm_query.endDate)
	        {
			if(frm_query.endDate.value == null || frm_query.endDate.value == ''){
				frm_query.endDate.value = '<%=nowDate%>';	
			}
			}
			if(frm_query.type.value == null || frm_query.type.value == ''){
				frm_query.type.value = 'd';	
				
			}
			var type=frm_query.type.value;
			change(type);
			changeOrder(sign);
			//doQuery();
		}
		function doQuery(){
			if (frm_query.type.value=="h") {
				if (frm_query.beginDate.value>frm_query.endDate.value) {
					alert("开始日期不能大于结束日期！");
					return;
				}
			}
			frm_query.submit();
		}
		function change(value){
			if(value=='d')
			{
			  frm_query.beginDate.disabled=true;
			  frm_query.endDate.disabled=true;
			}
			else if(value=='h')
			{
			   frm_query.beginDate.disabled=false;
			   frm_query.endDate.disabled=false;
			}
		}
		function changeBlanceSel(sel){
			if(sel=="G"){
				frm_query.balanceGt.value="0";
				frm_query.balanceLt.value="";
				frm_query.balanceEq.value="";
			} else if(sel=="L"){
				frm_query.balanceGt.value="";
				frm_query.balanceLt.value="0";
				frm_query.balanceEq.value="";
			} else if(sel=="E"){
                frm_query.balanceGt.value="";
				frm_query.balanceLt.value="";
				frm_query.balanceEq.value="0";
			}
		}
		
		function fucCheckNUM(NUM)     
		{   
			if(NUM==""){  
				
			}else{
				if(!/^(0|[1-9]\d*)(\.\d{1,2})?$/.test(NUM)){
					alert("发生额请填写数字"); 
					document.getElementById("amount").value = "";
					document.getElementById("amount").focus();
					return;
				}
			}
		
		} // 验证只能为2位小数或整数的 js方法

		
		
	</script>
  </head>
  
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryFundflow" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>资金流水查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
				 <td align="right">查询类型：&nbsp;</td>
				 <td align="left">
				<select id="type" name="type" style="width: 100px" onchange="change(this.value)">
					<option value="d">当前</option>
					<option value="h">历史</option>
				</select>
				<script>
					frm_query.type.value="<c:out value='${type}'/>";
					
				</script>
					</td>
					<td align="right">平台账号：&nbsp;</td>
					<td align="left">
						<input name="_firmId[like]" id="fid" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">发生额：&nbsp;</td>
					<td align="left">
						<input id="amount" name="_amount[=]" value="<c:out value='${oldParams["amount[=]"]}'/>" type=text class="text"  onchange="fucCheckNUM(this.value);" style="width: 100px" onkeypress="onlyNumberInput()" maxlength="16">
					</td>
					
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
					</td>
					</tr>
				<tr>
				   <td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="beginDate" eltName="_b_date[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[>=][date]"]}'/>"/>
					</td>
					<td align="right">结束日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="endDate" eltName="_b_date[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[<=][date]"]}'/>"/>
					</td>
					<td align="right">资金余额：&nbsp;</td>
				    <td align="left">
				    <select id="balance" name="_balance" style="width: 100px" onchange="changeBlanceSel(this.value);">
				      <OPTION value="">全部</OPTION>
					  <option value="G">大于零</option>
					  <option value="L">小于零</option>
					  <option value="E">等于零</option>
				   </select>
				   <input id="balanceGt" name="_balance[>]" type="hidden">
				   <input id="balanceLt" name="_balance[<]" type="hidden">
				   <input id="balanceEq" name="_balance[=]" type="hidden">
				   <script>
							frm_query.balance.value="<c:out value='${oldParams["balance"]}'/>";
							changeBlanceSel("<c:out value='${oldParams["balance"]}'/>");
				  </script>
				   </td>
				   <td>
				   		<!-- add by yangpei 2011-11-22 增加重置功能 -->
		            	<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
		            	<script>
		            		function resetForm(){
		            			frm_query.type.value="d";
		            			frm_query.fid.value="";
		            			frm_query.beginDate.value="";
		            			frm_query.endDate.value="";
		            			frm_query.amount.value="";
		            			frm_query.balance.value="";
		            		}
		            	</script>
				   </td>
				</tr>
			</table>
		</fieldset>
	</form>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400" >
  		<tHead>
  			<tr height="25" id="tr" name="tr">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"  abbr="fundFlowID">流水编号</td>
				<td class="panel_tHead_MB"  abbr="b_date">结算日期</td>
				<td class="panel_tHead_MB"  abbr="firmId">平台账户</td>
				<td class="panel_tHead_MB"  abbr="oprCode">业务</td>
				<td class="panel_tHead_MB"  abbr="contractNo"><%=CONTRACTNO%></td>
				<td class="panel_tHead_MB"  abbr="commodityID"><%=COMMODITYID%></td>
				<td class="panel_tHead_MB_Curr"  abbr="amount">发生额</td>
				<td class="panel_tHead_MB_Curr"  abbr="balance">资金余额</td>
				<td class="panel_tHead_MB_Curr"  abbr="appendAmount">附加帐金额</td>
				<td class="panel_tHead_MB"  abbr="voucherNo"><%=VOUCHERNO%></td>
				<td class="panel_tHead_MB"  abbr="createTime">发生时间</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		    <c:set var="sumAmount" value="0" />
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.FundFlowID}"/>&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.b_date}" pattern="yyyy-MM-dd"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.firmId}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.oprCode}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contractNo}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.commodityID}"/>&nbsp;</td>
	  			<c:set  var="sumAmount" value="${sumAmount+result.amount}"/>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.amount}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.balance}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${result.appendAmount}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.voucherNo}"/>&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  		<c:if test="${sumAmount>0}">
	  		   <tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine" align="right">合计:</td>
	  			<td class="underLineCurr"><fmt:formatNumber value="${sumAmount}" pattern="#,##0.00"/>&nbsp;</td>
	  			<td class="underLineCurr">&nbsp;</td>
	  			<td class="underLineCurr">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:if>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
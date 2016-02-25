<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>

<html xmlns:MEBS>
  <head>
	<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
    <title></title> 
  </head>
  
  <body onload="init()">
  	<form id="frm_query" action="<%=brokerRewardControllerPath%>brokerRewardList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="orderField" value="occurDate">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" value = "${pageInfo.pageNo }">
		<fieldset width="95%">
			<legend>待付佣金查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right"><%=BROKERID%>：&nbsp;</td>
					<td align="left">
						<input id="brokerID" name="_t.brokerID[like]" value="<c:out value='${oldParams["t.brokerID[like]"]}'/>" type=text class="text" style="width: 90px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right"><%=MODULEID%>：&nbsp;</td>
					<td align="left">
						<select id="moduleId" name="_moduleId[=]">
							<option selected="true" value="">全部</option>
							<option value="2">订单</option>
							<option value="3">挂牌</option>
							<option value="4">竞价</option>
						</select>
					</td>
					<script>
							frm_query.moduleId.value = "<c:out value='${oldParams["moduleId[=]"]}'/>";
					</script>
					<td align="right">发生日：</td>
					<td align="left">
						<MEBS:calendar eltID="sOccurDate" eltName="_trunc(occurDate)[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(occurDate)[>=][date]"]}'/>"/>&nbsp;至&nbsp;
						<MEBS:calendar eltID="eOccurDate" eltName="_trunc(occurDate)[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(occurDate)[<=][date]"]}'/>"/>
					</td>

					
					<script>
							//frm_query.ability.value = "<c:out value='${oldParams["ability[=]"]}'/>";
					</script>
					<td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
				
				
			</table>
		</fieldset>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
		        <td class="panel_tHead_MB" align="center" width="126"><%=BROKERID%></td>
  				<td class="panel_tHead_MB" align="center" width="126"><%=MODULEID%></td>
				<td class="panel_tHead_MB" align="center" width="126">发生日</td>
				<td class="panel_tHead_MB" align="center" width="126">已付服务费</td>
				<td class="panel_tHead_MB" align="center" width="126">付款日</td>
				<td class="panel_tHead_MB" align="center" width="126">待付服务费</td>
				<td class="panel_tHead_MB" width="120">支付服务费</td>
				<td class="panel_tHead_MB" align="center">&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:set var="sumPaidAmount" value="0"/>
			<c:set var="sumAmount" value="0"/>
			<c:forEach items="${resultList}" var="result">
			<c:set var="sumPaidAmount" value="${sumPaidAmount+result.PaidAmount}"/>
			<c:set var="sumAmount" value="${sumAmount+result.amount}"/>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" align="center"><c:out value="${result.brokerId}"/></td>
	  			<td class="underLine" align="center">
	  				<c:if test="${result.moduleId=='2'}">订单</c:if>
	  				<c:if test="${result.moduleId=='3'}">挂牌</c:if>
	  				<c:if test="${result.moduleId=='4'}">竞价</c:if>
	  			</td>
	  			<td class="underLine" align="center"><fmt:formatDate value="${result.occurDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLineCurr"><fmt:formatNumber value="${result.PaidAmount}" pattern="#,##0.00"/></td>
				<td class="underLineCurr"><fmt:formatDate value="${result.payDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLineCurr"><fmt:formatNumber value="${result.amount}" pattern="#,##0.00"/></td>
				<td class="underLine">
	  				<span onclick="repairInfo('<c:out value="${result.brokerId}"/>','<c:out value="${result.occurDate}"/>','<c:out value="${result.moduleId}"/>')" style="cursor:hand;color:blue">
	  					<img src="<%=basePath%>/public/ico/edit.gif" width="15" height="15" />
	  				</span>
	  			</td>
				<td class="underLine" align="right">&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine" align="center"><b>合计：</b></td>
	  			<td class="underLine" align="center">&nbsp;</td>
	  			<td class="underLine" align="center">&nbsp;</td>
				<td class="underLineCurr"><fmt:formatNumber value="${sumPaidAmount}" pattern="#,##0.00"/></td>
				<td class="underLineCurr">&nbsp;</td>
				<td class="underLineCurr"><fmt:formatNumber value="${sumAmount}" pattern="#,##0.00"/></td>
				<td class="underLine" align="center">&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8">
					<%@ include file="../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		</table>
		<div align="right"><input type="button" onclick="exportExce();" class="btn" value="导出报表"></div>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	
	function init(){
		if(frm_query.pageSize.value == "" || frm_query.pageSize.value == "null"){
			//frm_query.sOccurDate.value = '<%=nowDate%>';
			//frm_query.eOccurDate.value = '<%=nowDate%>';
			doQuery();
		}
	}
	function doQuery(){
		var startDate = document.getElementById("sOccurDate").value;
		var endDate =  document.getElementById("eOccurDate").value;
		

	  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
	  {
		if(startDate == ""){
			alert("起始日期不能为空！");
			frm_query.sOccurDate.focus();
			return false;
			
		}
		if(endDate == ""){
			alert("结束日期不能为空！");
			frm_query.eOccurDate.focus();
			return false;
			
		}
		if(!isDateFomat(startDate))
	    {
	        alert("起始日期格式不正确！\n如：" + '<%=nowDate%>');
	        frm_query.sOccurDate.value = "";
	        frm_query.sOccurDate.focus();
	        return false;
	    }
	    if(!isDateFomat(endDate))
	    {
	        alert("结束日期格式不正确！\n如：" + '<%=nowDate%>');
	        frm_query.eOccurDate.value = "";
	        frm_query.eOccurDate.focus();
	        return false;
	    }
	  
	    if ( startDate > '<%=nowDate%>' ) { 
	        alert("起始日期不能大于当天日期!"); 
	        frm_query.sOccurDate.focus();
	        return false; 
	    } 
	    if ( startDate > endDate ) { 
	        alert("起始日期不能大于结束日期!"); 
	        return false; 
	    } 
	  }
		frm_query.submit();
	}
	
	function resetForm(){
		frm_query.brokerID.value = "";
		frm_query.moduleId.value = "";
		document.getElementById("sOccurDate").value = "";
		document.getElementById("eOccurDate").value = "";
	}
	function repairInfo(brokerId, occurDate,moduleId){
			var returnValue = openDialog("<%=brokerRewardControllerPath%>changebrokerRewardForward&brokerId="+brokerId + "&occurDate=" + occurDate +"&moduleId="+moduleId+"&radom="+Date(),"_blank",500,350);
			if(returnValue)
				frm_query.submit();
	}
	//查看详情
	function exportExce(){
		var chuLiMethod=window.confirm("是否处理全部数据？\n“取消”为处理当前页面数据。");
		if(chuLiMethod){
			frm_query.action="<%=brokerRewardControllerPath%>brokerRewardList&exportExcel=1&exportAll=1";
		}else{
			frm_query.action="<%=brokerRewardControllerPath%>brokerRewardList&exportExcel=1";	
		}
		frm_query.submit();
		frm_query.action="<%=brokerRewardControllerPath%>brokerRewardList";
	}
//-->
</SCRIPT>


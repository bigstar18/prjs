<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>

<html xmlns:MEBS>
  <head>
	<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
    <title></title>
  </head>
  
  <body onload="init()">
  	<form id="frm_query" action="<%=brokerRewardControllerPath%>brokerRewardPropsList"   method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>佣金设置查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
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
					<td align="right"><%=BREEDID%>：&nbsp;</td>
					<td align="left">
						<input id="breedName" name="_breedName[like]" value="<c:out value='${oldParams["breedName[like]"]}'/>" type=text class="text" style="width: 120px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right"><%=BROKERID%>：&nbsp;</td>
					<td align="left">
						<input id="brokerId" name="_brokerId[like]" value="<c:out value='${oldParams["brokerId[like]"]}'/>" type=text class="text" style="width: 120px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
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
  				
	  			<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
		        <td class="panel_tHead_MB"><%=BROKERID%></td>
				<td class="panel_tHead_MB"><%=MODULEID%></td>
				<td class="panel_tHead_MB"><%=BREEDID%></td>
				<td class="panel_tHead_MB">手续费佣金比例(%)</td>
				<td class="panel_tHead_MB">提成首付比例(%)</td>
				<td class="panel_tHead_MB">提成尾款比例(%)</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><input name="delCheck" type="checkbox" value="<c:out value="${result.breedId}"/>,<c:out value="${result.moduleId}"/>,<c:out value="${result.brokerId}"/>"></td>
	  			<td class="underLine"><c:out value="${result.brokerId}"/></td>
	  			<td class="underLine">
	  				<c:if test="${result.moduleId=='2'}">订单</c:if>
	  				<c:if test="${result.moduleId=='3'}">挂牌</c:if>
	  				<c:if test="${result.moduleId=='4'}">竞价</c:if>
	  			</td>
	  			<td class="underLine"><a href="javascript:updateProps('${result.breedId}','${result.brokerId}','${result.moduleId}');" class="normal"><c:out value="${result.breedName}"/></a></td>
	  			<td class="underLine"><fmt:formatNumber value="${result.rewardRate*100}" pattern="#,##0.00"/></td>
				<td class="underLine"><fmt:formatNumber value="${result.firstPayRate*100}" pattern="#,##0.00"/></td>
				<td class="underLine"><fmt:formatNumber value="${result.secondPayRate*100}" pattern="#,##0.00"/></td>
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
					<%@ include file="../../public/pagerInc.jsp" %>
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
		
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="disposeRecForbidOrResume(frm_query,tableList,'delCheck','删除佣金设置')">删除</button>&nbsp;&nbsp;
			</div></td>
        </tr>
    </table>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	
	function init(){
		if(frm_query.pageSize.value == "" || frm_query.pageSize.value == "null"){
			doQuery();
		}
	}
	function doQuery(){
		frm_query.submit();
	}
	
	function resetForm(){
		frm_query.brokerId.value = "";
		frm_query.breedName.value = "";
		frm_query.moduleId.value = "";
	}
	
	//添加
	function add(){
		frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsAddForward";
		frm_query.submit();
	}
	//删除
	function disposeRecForbidOrResume(frm_delete,tableList,checkName,dispose){
		if(isSelNothing(tableList,checkName) == -1){
			alert ( "没有可以"+dispose+"的数据！" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "请选择需要"+dispose+"的数据！" );
			return;
		}
		if(confirm("您确实要"+dispose+"选中数据吗？")){
			frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsDelete";
			frm_delete.submit();
		}
	}
	//修改
	function updateProps(breedId,brokerId,moduleId){
		var result=window.showModalDialog("<%=brokerRewardControllerPath%>brokerRewardPropsUpdateOther&brokerId=" + brokerId + "&breedId=" + breedId+"&moduleId=" + moduleId);
		if(result){
	    	frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsList";
			frm_query.submit();
	    }
	}
//-->
</SCRIPT>


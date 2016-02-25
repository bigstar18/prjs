<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>

<html xmlns:MEBS>
  <head>
	<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
    <title></title>
  </head>
  
  <body onload="init()">
  	<form id="frm_query" action="<%=brokerRewardControllerPath%>brokerRewardPropsBreedList"  targetType="hidden" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" name="tf" value="false">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				
	  			<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB">
				</td>
				<td class="panel_tHead_MB">交易品种</td>
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
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.breedId}"/>">
	  			</td>
	  			<td class="underLine">
	  			</td>
	  			<td class="underLine"><a href="javascript:updateProps('${result.breedId}');" class="normal"><c:out value="${result.breedName}"/></a></td>
	  			<td class="underLine"><fmt:formatNumber value="${result.rewardRate}" pattern="#,##0.00"/></td>
				<td class="underLine"><fmt:formatNumber value="${result.firstPayRate}" pattern="#,##0.00"/></td>
				<td class="underLine"><fmt:formatNumber value="${result.secondPayRate}" pattern="#,##0.00"/></td>
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
			  <button class="lgrbtn" type="button" onclick="add();">添加佣金设置</button>&nbsp;&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="disposeRecForbidOrResume(frm_query,tableList,'delCheck','删除佣金设置')">删除佣金设置</button>&nbsp;&nbsp;
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
		frm_query.brokerID.value = "";
		frm_query.commodityId.value = "";
	}
	
	//添加
	function add(){
		frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsBreedAdd";
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
		if(confirm("是否将该品种下属所有商品的特殊佣金设置全部删除？")){
			frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsBreedDelete";
			frm_query.tf.value="true";
			frm_query.submit();
		}//else{
		//	frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsBreedDelete";
		//	frm_query.tf.value="false";
		//	frm_query.submit();
		//}
	}
	//修改
	function updateProps(breedId){
		var result=window.showModalDialog("<%=brokerRewardControllerPath%>brokerRewardPropsBreedUpdate&breedId="+breedId+"", "dialogWidth=600px; dialogHeight=400px; status=yes;scroll=yes;help=no;");
		if(result){
	    	frm_query.action = "<%=brokerRewardControllerPath%>brokerRewardPropsBreedList";
			frm_query.submit();
	    }
	}
//-->
</SCRIPT>


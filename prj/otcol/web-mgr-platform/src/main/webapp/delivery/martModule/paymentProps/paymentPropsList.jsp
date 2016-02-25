<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>


<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" name="requestModuleID" value="">
		<input type="hidden" name="requestBreedID" value="">
		<input type="hidden" name="requestSettleDayNo" value="">
		<fieldset width="95%">
			<legend>货款收付设置查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易模块：</td>
					<td align="left">
						<select id="moduleID" name="_moduleID[=]" class="normal" style="width: 120px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${moduleNameMap}" var="moduleNameMap">
								<option value="${moduleNameMap.key}">${moduleNameMap.value}</option>
			                </c:forEach>
						</select>
						<script>
							document.getElementById("moduleID").value = "<c:out value='${oldParams["moduleID[=]"]}'/>";
						</script>
					</td>
					
					<td align="right">品种名称：</td>
					<td align="left">
					<select id="breedID" name="_breedID[=]" style="width:120" class="normal">
							<option value="">全部</option>
							<c:forEach items="${PayCommoditysList}" var="payCommoditysList">
								<option value="${payCommoditysList.id}">${payCommoditysList.name}</option>
			                </c:forEach>
						</select>&nbsp;
						<script>
							document.getElementById("breedID").value = "<c:out value='${oldParams["breedID[=]"]}'/>" ;
						</script>
					</td>
					
					<td align="right">第几交收日：</td>
					<td align="left">
						<input id="settleDayNo" name="_settleDayNo[=]" value="<c:out value='${oldParams["settleDayNo[=]"]}'/>" type=text class="text" style="width: 120px" onkeypress="notSpace()">
					</td>
					
					
					<td colspan="4">&nbsp;</td>
					<td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
					<script>
							//frm.enterDate.value = "<c:out value='${oldParams["enterDate[=]"]}'/>";
					</script>
				
				
				
			</table>
		</fieldset>
	  <%@include file="paymentPropsTable.jsp" %>
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
            
			 </div></td>
			 <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">添加收付设置</button>&nbsp;&nbsp;
             <button class="lgrbtn" type="button" onclick="disposeRecForbidOrResume(frm,tableList,'delCheck','删除')">删除</button>&nbsp;&nbsp;
			 </div></td>
        </tr>
    	</table>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--

	function initQuery(){
		if(frm.pageSize.value == "" || frm.pageSize.value == "null"){
			doQuery();
		}
	}
	function doQuery(){
		//var num = 1;
		//if (frm.settleDayNo.value==""){
		//	return true;
		//}else if(isNaN(frm.settleDayNo.value)){
		//	alert("请输入数字交收日");
		//	return false;
		//}
		frm.submit();
	}

	function resetForm(){
		frm.moduleID.value = "";
		frm.breedID.value = "";
		frm.settleDayNo.value = "";
		frm.submit();
	}
	
	//查看详情
	function fAuditing(moduleID, breedID, settleDayNo){
		frm.requestModuleID.value = moduleID;
		frm.requestBreedID.value = breedID;
		frm.requestSettleDayNo.value = settleDayNo;
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsView";
		frm.submit();
	}
	
	//添加
	function add(){
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsAddForward";
		frm.submit();
	}
	//修改
	function fupdate(moduleID, breedID, settleDayNo){
		frm.requestModuleID.value = moduleID;
		frm.requestBreedID.value = breedID;
		frm.requestSettleDayNo.value = settleDayNo;
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsUpdateForward";
		frm.submit();
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
			frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsDelete";
			frm_delete.submit();
		}
		
}
	
//-->
</SCRIPT>


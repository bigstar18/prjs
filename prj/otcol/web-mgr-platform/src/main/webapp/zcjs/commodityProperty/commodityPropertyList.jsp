<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<IMPORT namespace="MEBS"
			implementation="<%=publicPath%>jstools/calendar.htc">
		<title></title>
	</head>
	<body onload="initBody('${returnRefresh}')">
		<form name="frm"
			action="<%=basePath%>commodityPropertyController.zcjs?funcflg=list"
			method="post">
			<input type="hidden" name="orderField" value="${orderFiled}">
			<input type="hidden" name="orderDesc" value="${orderType}">
			<input type="hidden" name="pageSize"
				value="<c:out value="${pageInfo.pageSize}"/>">
			<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="95%">
				<legend>
					商品属性查询
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							商品属性名称&nbsp;
						</td>
						<td align="left">
							<input id="propertyName" name="_propertyName[like]"
								value="<c:out value='${oldParams["propertyName[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="left" colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doQuery();">
								查询
							</button>
							&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="resetForm();">
								重置
							</button>
						</td>
					</tr>
				</table>
			</fieldset>
			<%@ include file="commodityPropertyTable.jsp"%>
			 <table border="0" cellspacing="0" cellpadding="0" width="80%">
				<tr height="35">
					<td>
						<div align="center">
							<button class="lgrbtn" type="button" onclick="dealForbid();">
								状态禁止
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="lgrbtn" type="button" onclick="dealNormal();">
								状态正常
							</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function initBody(returnValue){
		var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
		}
	
		changeOrder(sign);
		
	}
	function mod(id,status){
	alert("ss"+id+"mm"+status);
	frm.action="<%=basePath%>/commodityPropertyController.zcjs?funcflg=mod&propertyId="+id+"&status="+status;
	frm.submit();
	}
	function dealForbid(){
       frm.action="<%=basePath%>commodityPropertyController.zcjs?funcflg=mod&type="+2;
		if(!modRec(frm,tableList,'delCheck')){
			frm.action = "<%=basePath%>commodityPropertyController.zcjs?funcflg=list";
		}
	}	
	function dealNormal(){
       frm.action="<%=basePath%>commodityPropertyController.zcjs?funcflg=mod&type="+1;
	   if(!modRec(frm,tableList,'delCheck')){
			frm.action = "<%=basePath%>commodityPropertyController.zcjs?funcflg=list";
		}
	}
	function modRec(frm,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
		alert ( "没有可以操作的数据！" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "请选择需要操作的数据！" );
		return false;
		}
		if(confirm("您确实要操作选中数据吗？"))
		{
		frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	function resetForm(){
	frm.propertyName.value = "";
	frm.submit();
}

</SCRIPT>
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
					��Ʒ���Բ�ѯ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							��Ʒ��������&nbsp;
						</td>
						<td align="left">
							<input id="propertyName" name="_propertyName[like]"
								value="<c:out value='${oldParams["propertyName[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="left" colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doQuery();">
								��ѯ
							</button>
							&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="resetForm();">
								����
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
								״̬��ֹ
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="lgrbtn" type="button" onclick="dealNormal();">
								״̬����
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
		alert ( "û�п��Բ��������ݣ�" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
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
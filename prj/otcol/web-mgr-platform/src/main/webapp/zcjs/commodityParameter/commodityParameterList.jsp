<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>commodityParameterController.zcjs?funcflg=list" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
				<legend>
					商品参数查询
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							商品参数名称&nbsp;
						</td>
						<td align="left">
							<input id="parameterName" name="_parameterName[like]"
								value="<c:out value='${oldParams["parameterName[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="right">
							品种名称&nbsp;
						</td>
						<td align="left">
							<input id="breedName" name="_breedName[like]"
								value="<c:out value='${oldParams["breedName[like]"]}'/>"
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
	  <%@ include file="commodityParameterTable.jsp"%>
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
	function resetForm(){
		frm.parameterName.value = "";
		frm.breedName.value="";
		frm.submit();
	}
//类型（type）1 正常，2禁止，3 删除 
	function dealForbid(){
       frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=mod&type="+2;
		modRec(frm,tableList,'delCheck');
	}	
	function dealNormal(){
       frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=mod&type="+1;
		modRec(frm,tableList,'delCheck');
	}	
	function modRec(frm,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
			alert ( "没有可以操作的数据！" );
			frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=list";
			return false;
		}
		if(isSelNothing(tableList,checkName))
		{
			alert ( "请选择需要操作的数据！" );
			frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=list";
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
	
	

</SCRIPT>
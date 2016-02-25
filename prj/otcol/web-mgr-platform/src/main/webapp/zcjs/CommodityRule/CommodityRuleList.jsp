<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm"  method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="95%">
				<legend>
					商品规则查询
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							商品规则序号&nbsp;
						</td>
						<td align="left">
							<input id="commodityRuleId" name="_commodityRuleId[like]"
								value="<c:out value='${oldParams["commodityRuleId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="right">
							品种编号&nbsp;
						</td>
						<td align="left">
							<input id="breedId" name="_breedId[like]"
								value="<c:out value='${oldParams["breedId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="right">
							交易商名称&nbsp;
						</td>
						<td align="left">
							<input id="commodityRuleFirmId" name="_commodityRuleFirmId[like]"
								value="<c:out value='${oldParams["commodityRuleFirmId[like]"]}'/>"
								type=text class="text" style="width: 100px " onkeypress="notSpace()">
						</td>
						<td align="left" colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doQueryNew();">
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
	  <%@ include file="CommodityRuleTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td align="center"><div >
			  <button class="lgrbtn" type="button" onclick="add();">添加</button>&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="deleted();">删除</button>
			</div></td>
        </tr>
    </table>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function fmod(commodityRuleId){
		frm.action = "<%=basePath%>commodityRuleController.zcjs?funcflg=updateForward&commodityRuleId="+commodityRuleId;
		frm.submit();
	}
	function doQueryNew(){
		var commodityRuleId=document.getElementById('commodityRuleId').value;
		var breedId=document.getElementById('breedId').value;
		if(!checkNumber(commodityRuleId)){
			document.getElementById('commodityRuleId').value="";
			document.getElementById('commodityRuleId').focus();
			alert('商品规则序号必须为数字');
		}else{
			if(!checkNumber(breedId)){
				document.getElementById('breedId').value="";
				document.getElementById('breedId').focus();
				alert('品种Id必须为数字');
			}else{
				doQuery();
			}
		}
	}
	function resetForm(){
		frm.commodityRuleId.value="";
		frm.breedId.value="";
		frm.commodityRuleFirmId.value="";
		frm.submit();
	}
	function checkNumber(input){
		if(input==""){
			return true;
		}else{
			return checkRate(input);
		}
	}
	function checkRate(input)    
	{    
  		var re = /^[0-9]{1,20}$/;   //判断字符串是否为数字
  		if (!re.test(input)){
  			return false;
  		}else{
  			return true;
  		}
  	}
	function add(){
		frm.action="<%=basePath%>commodityRuleController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function deleted(){
		
		deleteRec(frm,tableList,'delCheck');
	}
	
	function deleteRec(frm_delete,tableList,checkName)
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
		frm.action="<%=basePath%>commodityRuleController.zcjs?funcflg=delete";
		frm.submit();
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>
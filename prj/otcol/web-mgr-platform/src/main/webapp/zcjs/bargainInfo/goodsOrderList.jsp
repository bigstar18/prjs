<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainInfoController.zcjs?funcflg=getC_GoodsOrderList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>当前挂单情况查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
				<td align="right">交易商代码&nbsp;</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">挂单编号&nbsp;</td>
					<td align="left">
						<input id="goodsOrderID" name="_goodsOrderId[=]"
								value="<c:out value='${oldParams["goodsOrderId[=]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">数量&nbsp;</td>
					<td align="left">
						<input id="quantity" name="_quantity[=]"
								value="<c:out value='${oldParams["quantity[=]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">买卖方向&nbsp;</td>
					<td align="left">
					<c:set value='${oldParams["businessDirection[=]"]}' var="businessDirection"></c:set>
						<select name="_businessDirection[=]" id="businessDirection">
									<option value="">
										全部
									</option>
									<option value="买"
									<c:if test="${businessDirection=='买' }">selected</c:if>>
										买
									</option>
									<option value="卖"
									<c:if test="${businessDirection=='卖' }">selected</c:if>>
										卖
									</option>
						</select>
					</td>
					

				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery1();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="reset1()" >重置</button>	
				</td>
				</tr>
			</table>
	</fieldset>		
		
	  <%@ include file="goodsOrderTable.jsp"%>
	
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
function doQuery1(){
		var goodsOrderID=document.getElementById('goodsOrderID').value;
		var quantity=document.getElementById('quantity').value;
		if(!checkNumber(goodsOrderID)){
			document.getElementById('goodsOrderID').value="";
			document.getElementById('goodsOrderID').focus();
			alert('挂单ID必须为数字');
		}else{
			if(!checkNumber(quantity)){
				document.getElementById('quantity').value="";
				document.getElementById('quantity').focus();
				alert('数量必须为数字');
			}else{
				doQuery();
			}
		}
	}
	function reset1(){
		document.getElementById('goodsOrderID').value="";
		document.getElementById('quantity').value="";
		document.getElementById('businessDirection').value="";
		document.getElementById('firmId').value="";
		doQuery();
	}
	//判断input是否为数字
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
  	function fmod(id){
		frm.action = "<%=basePath%>bargainInfoController.zcjs?funcflg=SystemCancleSub&goodsOrderId="+id;
		frm.submit();
	}
	function fmods(goodsOrderId){
  	 openDialog("<%=basePath%>/bargainInfoController.zcjs?funcflg=getGoodsOrderParameter&goodsOrderId="+goodsOrderId,"_blank",470,450);
		
	}
		<c:remove var="resultMsg" scope="session"/>
</SCRIPT>
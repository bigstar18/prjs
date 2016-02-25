<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainInfoController.zcjs?funcflg=getD_TradeResultList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>当前成交情况查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="110%" height="35">
				<tr height="35">
					<td align="right">合同号&nbsp;</td>
					<td align="left">
						<input id="TradeNo" name="_TradeNo[like]"
								value="<c:out value='${oldParams["TradeNo[like]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>					
					<td align="right">卖方交易商代码&nbsp;</td>
					<td align="left">
						<input id="FirmId_S" name="_FirmId_S[like]" value="<c:out value='${oldParams["FirmId_S[like]"]}'/>" type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>	
					<td align="right">买方交易商代码&nbsp;</td>
					<td align="left">
						<input id="FirmId_B" name="_FirmId_B[like]" value="<c:out value='${oldParams["FirmId_B[like]"]}'/>" type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">数量&nbsp;</td>
					<td align="left">
						<input id="quantity" name="_quantity[=]"
								value="<c:out value='${oldParams["quantity[=]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery1();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="reset1();">重置</button>	
				</td>
				</tr>
			</table>
	</fieldset>		
		
	  <%@ include file="tradeResultTable.jsp"%>
	
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function doQuery1(){
		var TradeNo=document.getElementById('TradeNo').value;
		var quantity=document.getElementById('quantity').value;
			if(!checkNumber(TradeNo)){
			document.getElementById('TradeNo').value="";
			document.getElementById('TradeNo').focus();
			alert('合同号必须为数字');
			return false;
		}
		if(!checkNumber(quantity)){
			document.getElementById('quantity').value="";
			document.getElementById('quantity').focus();
			alert('数量必须为数字');
			return false;
			}
		doQuery();
	}
	function reset1(){
		document.getElementById('TradeNo').value="";
		document.getElementById('quantity').value="";
		document.getElementById('FirmId_S').value="";
		document.getElementById('FirmId_B').value="";
		frm.submit();
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
  		function fmod(TradeNo){
  	 openDialog("<%=basePath%>/bargainInfoController.zcjs?funcflg=getCommodityParameter&TradeNo="+TradeNo,"_blank",470,450);
		
	}
	
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainInfoController.zcjs?funcflg=getC_DPConList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>当前还价情况查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">议价编号&nbsp;</td>
					<td align="left">
						<input id="discussPriceId" name="_discussPriceId[like]"
								value="<c:out value='${oldParams["discussPriceId[like]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">对应挂单编号&nbsp;</td>
					<td align="left">
						<input id="goodsOrderId" name="_goodsOrderId[like]"
								value="<c:out value='${oldParams["goodsOrderId[like]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">跟单交易商代码&nbsp;</td>
					<td align="left">
						<input id="FollowFirmId" name="_FollowFirmId[like]" value="<c:out value='${oldParams["FollowFirmId[like]"]}'/>" type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>
					<td align="right">买卖方向&nbsp;</td>
					<td align="left">
						<c:set value='${oldParams["businessDirection[like]"]}' var="businessDirection"></c:set>
						<select name="_businessDirection[like]" id="businessDirection">
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
						<button type="button" class="smlbtn" onclick="reset1()">重置</button>	
				</td>
				</tr>
			</table>
	</fieldset>		
		
	  <%@ include file="discussPriceTable.jsp"%>
	
	</form>
	
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	function doQuery1(){
		var discussPriceId=document.getElementById('DiscussPriceId').value;
		var goodsOrderId=document.getElementById('goodsOrderId').value;
		if(!checkNumber(discussPriceId)){
			document.getElementById('DiscussPriceId').value="";
			document.getElementById('DiscussPriceId').focus();
			alert('议价ID必须为数字');
		}else{
			if(!checkNumber(goodsOrderId)){
				document.getElementById('goodsOrderId').value="";
				document.getElementById('goodsOrderId').focus();
				alert('对应挂单ID必须为数字');
			}else{
			doQuery();
			}
		}
	}
	function reset1(){
		document.getElementById('DiscussPriceId').value="";
		document.getElementById('goodsOrderId').value="";
		document.getElementById('businessDirection').value="";
		document.getElementById('FollowFirmId').value="";
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
  	function fmod(DiscussPriceId){
  	 openDialog("<%=basePath%>/bargainInfoController.zcjs?funcflg=getDisParameter&DiscussPriceId="+DiscussPriceId,"_blank",470,450);
		
	}
</SCRIPT>
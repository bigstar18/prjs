<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsUpdate">
		<fieldset width="50%" >
		<legend>货款收付设置信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
                <td align="right" width="40%">交易模块：</td>
                <td align="left" width="60%">
                	<c:forEach items="${moduleNameMap}" var="moduleNameMap">
                		<c:choose>
                			<c:when test="${moduleNameMap.key == pay.moduleID}">
                				${moduleNameMap.value}
                			</c:when>
                		</c:choose>
                	</c:forEach>
                	<input type="hidden" id="moduleID" name="moduleID" value="${pay.moduleID}"/>
                </td>
              </tr>
              <tr height="35">
            	<td align="right">品种：</td>
                <td align="left">
						${commodity.name}
                		<input type="hidden" id="breedID" name="breedID" value="${pay.breedID}"/>
                </td>
              </tr>
			   <tr height="35">
            	<td align="right">第几交收日：</td>
                <td align="left">
					${pay.settleDayNo}
					<input type="hidden" id="settleDayNo" name="settleDayNo" value="${pay.settleDayNo}" />
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">收买家货款比例：</td>
                <td align="left">
                	<input id="buyPayoutPct" name="buyPayoutPct" type="text" value="${pay.buyPayoutPct}" class="text" style="width: 120px;" maxlength="15">&nbsp;<font color="red">%&nbsp;*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">付卖家货款比例：</td> 
                <td align="left">
                	<input id="sellIncomePct" name="sellIncomePct" type="text" value="${pay.sellIncomePct}" class="text" style="width: 120px;" maxlength="15">&nbsp;<font color="red">%&nbsp;*</font>
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onClick="freturn()">返回</button>
                </div></td>
              </tr>
          </table>
          
		</fieldset>
		<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			    <tr height="35">
		            <td ><div align="right">
		            	<font color="red">说明：货款比例最小为0.01%</font>
					 </div></td>
		        </tr>
		   </table>
        </form>
</body>
</html>
<script>
	function doSubmit()
	{
		if (frm.moduleID.value == "") {
			alert("交易模块不能为空！");
			return false;
		}
		if (frm.breedID.value == "") {
			alert("商品代码不能为空！");
			return false;
		}
		if (frm.settleDayNo.value == "") {
			alert("第几交收日不能为空！");
			return false;
		}
		if (frm.buyPayoutPct.value == "") {
			alert("收买家货款比例不能为空！");
			return false;
		}
		if(!IsIntOrFloat(frm.buyPayoutPct.value)) {
			alert("收买家货款比例输入不正确!");
			frm.buyPayoutPct.value = "";
			frm.buyPayoutPct.select();
			return false;
		}
		if (frm.sellIncomePct.value == "") {
			alert("付卖家货款比例不能为空！");
			return false;
		}
		if(!IsIntOrFloat(frm.sellIncomePct.value)) {
			alert("付卖家货款比例输入不正确!");
			frm.sellIncomePct.value = "";
			frm.sellIncomePct.select();
			return false;
		}
		frm.submit();
	}
	function freturn(){
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsList";
		frm.submit();
	}
	function IsIntOrFloat(num){
  		var reNum=/(^\d)\d*(\.?)\d*$/;
  		return (reNum.test(num));
	}
</script> 

<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsAdd">
		<fieldset width="50%" >
		<legend>货款收付设置信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
                <td align="right" width="40%">交易模块：</td>
                <td align="left" width="60%">
                	<select id="moduleID" name="moduleID" class="normal" style="width: 120px">
						<OPTION value="">请选择</OPTION>
						<c:forEach items="${moduleNameMap}" var="moduleNameMap">
                			<OPTION value="${moduleNameMap.key}">${moduleNameMap.value}</OPTION>
                		</c:forEach>
					</select>
                </td>
              </tr>
              <tr height="35">
            	<td align="right">品种：</td>
                <td align="left">
                	<select id="breedID" name="breedID" class="normal" style="width: 120px">
						<OPTION value="">请选择</OPTION>
						<c:forEach items="${payCommoditysList}" var="payCommoditysList">
                			<OPTION value="${payCommoditysList.id}">${payCommoditysList.name}</OPTION>
                		</c:forEach>
					</select>
                </td>
              </tr>
			   <tr height="35">
            	<td align="right">第几交收日：</td>
                <td align="left">
                	<input id="settleDayNo" name="settleDayNo" type="text" value="" class="text" style="width: 120px;" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">收买家货款比例：</td>
                <td align="left">
                	<input id="buyPayoutPct" name="buyPayoutPct" type="text" value="" class="text" style="width: 120px;" maxlength="15" >&nbsp;<font color="red">%&nbsp;*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">付卖家货款比例：</td> 
                <td align="left">
                	<input id="sellIncomePct" name="sellIncomePct" type="text" value="" class="text" style="width: 120px;" maxlength="15">&nbsp;<font color="red">%&nbsp;*</font>
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
			alert("品种不能为空！");
			return false;
		}
		if (frm.settleDayNo.value == "") {
			alert("第几交收日不能为空！");
			return false;
		}
		if (isNaN(frm.settleDayNo.value)){
			alert("请输入数字交收日");
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
		if(confirm("确定执行此操作？")){
			frm.submit();
		}
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

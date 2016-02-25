<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<html>
  <head>
	<title></title>
	<script>
	function doSubmit()
	{
		if (confirm("您确定要提交吗？")){
			if (frm.breedId.value == "") {
				alert("<%=BREEDID%>不能为空！");
				return false;
			}
			if (frm.moduleId.value == "") {
				alert("<%=MODULEID%>不能为空！");
				return false;
			}
			if (frm.brokerId.value == "") {
				alert("<%=BROKERID%>不能为空！");
				return false;
			}
			if (frm.firstPayRate.value == "") {
				alert("提成首付比例不能为空！");
				return false;
			}
			if (frm.firstPayRate.value > 100) {
				alert("提成首付比例应小于等于百分之百！");
				return false;
			}
			if (frm.secondPayRate.value == "") {
				alert("提成尾款比例不能为空！");
				return false;
			}
			if (frm.rewardRate.value == "") {
				alert("手续费佣金比例不能为空！");
				return false;
			}
			if (frm.rewardRate.value > 100) {
				alert("手续费佣金比例应小于百分之百！");
				return false;
			}
			
			if (frm.payPeriodDate.value != "") {
				var payDate = parseInt(frm.payPeriodDate.value);
				if (frm.payPeriod[0].checked == true) {
					if (payDate < 1 || payDate > 28) {
						alert("付款周期日截止到28号！");
						return false;
					}
				}else {
					if (payDate < 1 || payDate > 7) {
						alert("付款周期日超出范围！");
						return false;
					}
				}
			}else {
				alert("付款周期日不能为空！");
				return false;
			}
			frm.submit();
		}
		
	}
	
	function change(){
		if (frm.firstPayRate.value != "") {
			frm.secondPayRate.value = 100 - frm.firstPayRate.value;
		}
	}
	
	function window_onload(){
		//isFormat(frm.rewardRate.value,2);
		//isFormat(frm.firstPayRate.value,2);
		//isFormat(frm.secondPayRate.value,2);
	}
	function suffixNamePress(){
	  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47){
	    event.returnValue=false;
	  }else{
	    event.returnValue=true;
	  }
	}
	
	function changeradio(){
		frm.payPeriodDate.value="";
		frm.payPeriodDate.select();
	}
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsUpdate">
		<input type="hidden" id="breedId" name="breedId" value="${brokerReward.breedId}"/>
		<input type="hidden" id="moduleId" name="moduleId" value="${brokerReward.moduleId}"/>
		<input type="hidden" id="brokerId" name="brokerId" value="${brokerReward.brokerId}"/>
		<table align="center" width="50%">
		<tr><td>
		<fieldset style="width:800px;" >
		<legend  >佣金设置信息</legend>
			
			
			<table align="center" border="0" cellspacing="0" cellpadding="0" width="50%">
			 
              <tr height="35">
                <td align="right" >手续费佣金比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerReward.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
              </tr>
               <tr height="35">
			  	<td align="right">是否自动付款：</td>
                <%
                	String type = (String)request.getAttribute("type");
                %>
                <td align="left">
                	<input name="autoPay" type="radio"  value="Y" <%if("Y".equals(type)){out.println("checked");} %> class="" style="border:0px;" >是
                	<input name="autoPay" type="radio"  value="N" <%if("N".equals(type)){out.println("checked");} %> class="" style="border:0px;" >否
                </td>
			  </tr>
			  
			  <tr height="35">
                <td align="right" >提成首付比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerReward.firstPayRate}" onkeypress="return suffixNamePress()" style="width: 150px;" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
			  	<td align="right">提成尾款比例：</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerReward.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;" onkeypress="return suffixNamePress()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			 
			  <tr height="35">
			  	<td align="right">付款周期：</td>
                <%
                	String payPeriod = (String)request.getAttribute("payPeriod");
                %>
                <td align="left">
                	<input name="payPeriod" type="radio" onclick="changeradio();" value="1" <%if("1".equals(payPeriod)){out.println("checked");} %> class="" style="border:0px;" >按月
                	<input name="payPeriod" type="radio" onclick="changeradio();" value="2" <%if("2".equals(payPeriod)){out.println("checked");} %> class="" style="border:0px;" >按星期
                </td>
			  </tr>
			  
			  <tr height="35">
                <td align="right" >付款周期日：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="payPeriodDate" value="${brokerReward.payPeriodDate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp; <font color="red">*</font>
                </td>
              </tr>
              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div></td>
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		<tr>
			<td>
				<font color="red"> *  付款周期为按月时，付款周期日表示的是日期(如15表示15号)；否则付款周期日表示的是星期(如1表示星期日)</font>
			</td>
		</tr>
		<tr>
			<td>
				<font color="red"> *  周期日对应关系：星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7</font>
			</td>
		</tr>
		<tr>
			<td>
				<font color="red"> *  是否自动付款对首付佣金和尾款佣金同时生效，当自动付款设置为否时，则首付和尾款均需要手动支付。</font>
			</td>
		</tr>
		</table>
        </form>
</body>
</html>


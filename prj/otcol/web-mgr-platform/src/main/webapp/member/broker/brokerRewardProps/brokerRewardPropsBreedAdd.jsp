<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<html>
  <head>
	<title></title>
	<script>
	function doSubmit()
	{
		var commodityId = frm.breedId.options[frm.breedId.selectedIndex].value;
			if (commodityId == "") { 
				alert("品种为空,请至少填一个！");
				return false;
			}
			if (frm.firstPayRate.value == "") {
				alert("提成首付比例不能为空！");
				return false;
			}
			if (frm.firstPayRate.value > 100) {
				alert("提成首付比例应小于百分之百！");
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
		if (confirm("是否将该品种下属所有商品添加至佣金特殊设置？")){
			frm.tf.value="true";
			frm.submit();
		}//else{
		//	frm.tf.value="false";
		//	frm.submit();
		//}
		
	}
	
	function cancle(){
		frm.action = "<%=brokerRewardControllerPath%>brokerRewardPropsBreedList";
		frm.submit();
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
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsBreedAddAction">
		
		<table align="center" width="50%">
		<tr><td>
		<input type="hidden" name="oprcode_T" value="${brokerReward.oprCode_T}"/>
		<input type="hidden" name="AutoPay" value="${brokerReward.autoPay}"/>
		<fieldset style="width:800px;" >
		<legend  >添加佣金设置信息</legend>
			
			
			<table align="center" border="0" cellspacing="2" cellpadding="2" width="50%">
			  <tr height="35">
                <td align="right" >品种：</td>
                <td align="left" >
                	                <!-- 	<input class="text" name="commodityId"   style="width: 150px;" > -->
                	<select name="breedId" style="width: 150px;">
                		<option value="">请选择</option>
						<%
							List list = (List)request.getAttribute("resultList");
							if (list != null && list.size() > 0) {
								for (int i = 0; i < list.size(); i++) {
									Map map = (Map)list.get(i);
									String BreedID = map.get("BreedID")+"";
									String BreedName = map.get("BreedName")+"";
									%>
										<option value="<%=BreedID%>"><%=BreedName%></option>
									<%
								}
							}
						%>
                	</select>
                	&nbsp;<font color="red">*</font>
                	<input type="hidden" name="tf" value="false">
                </td>
                
              </tr>
              <tr height="35">
                <td align="right" >手续费佣金比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerReward.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
                <td align="right" >提成首付比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerReward.firstPayRate}" style="width: 150px;" onkeypress="return suffixNamePress()" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr>
			  	<td align="right">提成尾款比例：</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerReward.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  
              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<button class="smlbtn" type="button" onClick="cancle();">返回</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div></td>
               
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		</table>
        </form>
</body>
</html>


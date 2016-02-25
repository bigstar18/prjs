<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp" %>
<base target="_self">
<html>
  <head>
	<title></title>
	<script>
	function doSubmit()
	{
		
			
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
		if (confirm("是否修改该品种下属所有商品的特殊设置？")){
			frm.tf.value="true";
			frm.submit();
		}else{
			frm.tf.value="false";
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
	
	function cancle(){
		window.close();
	}
	
	<c:if test='${not empty resultMsg}'>
		window.close();
	</c:if>
		
</script> 
</head>
<body onload="window_onload()">
        <form id="frm" name="frm" method="POST" callback="closeDialog(1);" targetType="hidden" action="<%=brokerRewardControllerPath%>brokerRewardPropsBreedUpdateAction">
		<input type="hidden" id="breedId" name="breedId" value="${brokerRewardProps.breedId}"/>
		<input type="hidden"  name="tf" value="false"/>
		<table align="center" width="500" height="80" border="0">
			<tr><td>&nbsp;</td></tr>
		</table>
		<table align="center" width="500">
		<tr><td>
		
		<fieldset style="width:490px;" >
		<legend  >佣金设置信息</legend>
			
			
			<table align="center" border="0" cellspacing="0" cellpadding="0" width="490">
			  <tr height="35">
                <td align="right" >品种：</td>
                <td align="left" >
                	${brokerRewardProps.BreedName}
                </td>
              </tr>
              <tr height="35">
                <td align="right" >手续费佣金比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="rewardRate" value="${brokerRewardProps.rewardRate}" onkeypress="return suffixNamePress()" style="width: 150px;" >&nbsp;%&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
                <td align="right" >提成首付比例：</td>
                <td align="left" >
                	<input class="text" maxlength="5" name="firstPayRate" value="${brokerRewardProps.firstPayRate}" onkeypress="return suffixNamePress()" style="width: 150px;" onblur="change()">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
                
              </tr>
			  <tr height="35">
			  	<td align="right">提成尾款比例：</td>
                <td align="left">
                	<input name="secondPayRate" type="text" value="${brokerRewardProps.secondPayRate}"  class="text" readonly="readonly" style="width: 150px;">&nbsp;%&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  
              <tr height="35">
                <td colspan="6"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
                  <button class="smlbtn" type="button" onClick="cancle();">关闭</button>
                </div></td>
              </tr>
          </table>
         
		</fieldset>
		</td></tr>
		</table>
        </form>
</body>
</html>
<%@ include file="../../public/footInc.jsp" %>

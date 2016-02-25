<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    virtualFundForm.typeOperate[0].checked = true;
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
	
		if (virtualFundForm.firmId.value == "") {
			alert("交易商代码不能为空！");
			return false;
		}
		if (virtualFundForm.money.value == "") {
			alert("虚拟资金不能为空！");
			return false;
		}
		if (virtualFundForm.money.value <= 0) {
			alert("虚拟资金应大于0！");
			return false;
		}
		
		if (virtualFundForm.typeOperate[1].checked) {
			virtualFundForm.money.value = "-" + virtualFundForm.money.value;
			//alert(virtualFundForm.virtualFunds.value);
		}
    	virtualFundForm.submit();
    	virtualFundForm.save.disabled = true;
	}
  
}


function cancle_onclick(){
	document.location.href = "<%=basePath%>/timebargain/virtualfund/app/virtualFundApp.jsp";
}

 function suffixNamePress()
{
	
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
    event.returnValue=false;
  }
  else
  {
    event.returnValue=true;
  }
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<form id="virtualFundForm" action="<%=basePath%>/timebargain/virtualFundSave.spr"
						method="POST" target="mainFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  设置虚拟资金
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											操作类型：
									</td>
									<td>
										<input type="radio" name="typeOperate" value="1" style="border:0px;"></radio>增加
										<input type="radio" name="typeOperate" value="2" style="border:0px;"></radio>扣减
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											交易商代码：
									</td>
									<td>
										<input type="text" name="firmId" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>	
								<tr>
									<td align="right">
											虚拟资金：
									</td>
									<td>
										<input type="text" name="money" maxlength="10" onkeypress="return suffixNamePress()" style="ime-mode:disabled" class="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
															
								
								
																																														
								<tr>
									<td colspan="3" align="center">
										
										<input type="button" name="save" value="提交" 
											onclick="javascript:return save_onclick();"/>
										<input type="button" name="cancle" value="返回" 
											onclick="javascript:return cancle_onclick();"/>
											
									</td>
								</tr>
							</table>
						</fieldset>
						
					</form>
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>

<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<html>
	<head>
		<title>手动入金</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/bankFunds/moneyInto/update.action"
			method="post">
			<%
				String clearDelaySecs = "";
			%>
			<div>
				<table border="0" width="50%" align="center">
					<tr>
						<td height="100">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;手动出入金
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="650px"
								height="100" align="center" class="st_bor">
					<tr height="40">
					<td align="right">
						交易账号:
					</td>
					<td>
						<input type="text" id="firmId" name="firmId" onblur="myblur('firmId')" class="input_textmid" />
					</td>
					<td align="left" height="40">
						<div id="firmId_vTip"></div>
					</td>
				    </tr>
				    <tr height="40">
					<td align="right">
						出入金:
					</td>
					<td>
					<select id="inOrout"	name="inOrout" style="width: 100px;">
					    <option value="">请选择</option>
					    <option value="0">入金</option>
					    <option value="1">出金</option>
					</select>		
				
					</td>
					<td align="left" height="40">
						&nbsp;
					</td>
				    </tr>
					<tr height="40">
					<td align="right">
						银行名称:
					</td>
					<td>
					<select id="bankId"	name="bankId" style="width: 100px;">
					</select>		
				
					</td>
					<td align="left" height="40">
						&nbsp;
					</td>
				    </tr>
				    <tr height="40">
					<td align="right">
						金额:
					</td>
					<td>
						<input type="text" id="money" name='money' onblur="moneyBlur()" />
					</td>
					<td align="left" height="40">
						<div id="money_vTip"></div>
					</td>
				    </tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table cellspacing="0" cellpadding="0" border="0" width="80%"
					align="center">
					<tr>
						<td align="center" height="20">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateMoney();" id="add">添加</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function myblur(userID){
	var flag = false;
	flag = firmId(userID);
    
	/*
    var firmId1 = document.getElementById("firmId").value;
    if(flag) {
    	checkAction.getBankId(firmId1,function(bankids){
    		if(bankids!="") {    			
    		var product = document.getElementById("bankId"); 
    			if(bankids.indexOf(',')>=0) {
    				var bankid = new Array;
    				bankid = bankids.split(",");
    				for(var i = 1;i<bankid.length;i++) {
    					 product.add(new Option(bankid[i],bankid[i]));
    				}
	    		}else {
					product.options.length=0;
	            	product.add(new Option(bankids,bankids));
	    		}
		}
    });
    
}
*/
}
function moneyBlur(){
	var vTip = document.getElementById('money_vTip');
	var innerHTML = "";
	var flag = false;
	var moneyValue = document.getElementById("money").value;
	if (isEmpty(moneyValue)) {
		innerHTML = "不能为空";
	} else if (!flote(moneyValue, 2)) {
		innerHTML = "最多2位小数的正数";
	} else if (moneyValue < 0) {
		innerHTML = "请输入≥0的数";
	} else {
		innerHTML = "";
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function firmId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "请添加交易商账号";
		flag =false;
	}else{
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
    	var product = document.getElementById("bankId"); 
	    product.options.length=0;
	var firmId = document.getElementById("firmId").value;
	if(firmId){
		checkAction.existId(firmId,function(isExist){
			if(!isExist){
				alert('交易账号不存在，请重新添加');
				flag = false;
				document.getElementById("firmId").value="";
				document.getElementById("firmId").focus();
			}else {
				checkAction.getBankId(firmId,function(bankids){
    		if(bankids!='null;null') {
    			if(bankids.indexOf(',')>=0) {
    				var bankid = new Array;
    				bankid = bankids.split(",");
    				for(var i = 0;i<bankid.length;i++) {
    					 var temp = bankid[i].split(";");
    					 product.add(new Option(temp[1],temp[0]));
    				}
	    		}else {  
	    			     var temp = bankids.split(";");
    				     product.add(new Option(temp[1],temp[0]));
	    		}
		}else {
			            alert('该交易商没有绑定银行');
			            product.add(new Option('',''));
		}
    });
			}
		});
	}
	return flag;
}
/*
function bankId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "请输入银行账号";
		flag =false;
	}else{
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	
		var bankId = document.getElementById("bankId").value;
	if(bankId){
		checkAction.getBankId(bankId,function(bankids){
			
		});
	}
	return flag;
}
*/
function updateMoney() {
	var firmId = frm.firmId.value;
	var bankId = frm.bankId.value;
	var money = frm.money.value;
	var inOrout = frm.inOrout.value;
	
	if(firmId == "") {
		alert('交易账号不可以为空');
		return false;
	}
	
	if(inOrout == "") {
		alert('请选择出入金方式');
		return false;
	}
	
	if(bankId == "") {
		alert('银行账号不可以为空');
		return false;
	}
	
	if(money == "") {
		alert('金额不可以为空');
		return false;
	}else if (isNaN(money)) {
		alert('金额必须为数字');
		frm.money.value = "";
		return false;
	}else if (money<0) {
		alert('金额必须正数');
		frm.money.value = "";
		return false;
	}
    
	if(moneyBlur()==true){
		var vaild = affirm("您确定要操作吗？");
		if (vaild == true) {
			frm.action="${basePath}/bankFunds/moneyIntoByManual/add.action"
			frm.submit();
		} else {
			return false;
		}
	}
}

</script>
<%@ include file="/public/footInc.jsp"%>
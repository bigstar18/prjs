<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<html>
	<head>
		<title>客户信息添加</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y:hidden">
		<form action="${basePath}/account/customer/add.action" name="frm"
			method="post" targetType="hidden">
			<div class="st_title">
				&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif"
					align="absmiddle" />&nbsp;客户信息添加
			</div>
			<div style="overflow:auto;height:490px;">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<%@include file="commonTable.jsp"%>
							<!-- 另一个开始-->
							<table width="90%" border="0" align="center" cellpadding="0"
								cellspacing="2">
								<tr>
									<td colspan="4">
										<div class="div_cxtj">
											<img src="<%=skinPath%>/cssimg/13.gif" />
											&nbsp;密码信息
										</div>
										<div class="div_tj">
											<table width="90%" border="0" class="table2_style">
												<tr>
													<td align="right" width="15%">
														交易密码:
													</td>
													<td width="45%">
														<label>
															<input type="password" name="obj.password" id="password"
																class="input_textmid" onblur="myblur('password')"
																onfocus="myfocus('password')">
															<strong class="check_input">&nbsp;*</strong>
														</label>
													</td>
													<td style="padding-right: 10px;" align="left">
														<div id="password_vTip">
															&nbsp;
														</div>
													</td>
												</tr>
												<tr>
													<td align="right" width="15%">
														交易密码确认:
													</td>
													<td>
														<label>
															<input type="password" name="password1" id="password1"
																class="input_textmid" onblur="myblur('password1')"
																onfocus="myfocus('password1')">
															<strong class="check_input">&nbsp;*</strong>
														</label>
													</td>
													<td style="padding-right: 10px;" align="left">
														<div id="password1_vTip">
															&nbsp;
														</div>
													</td>

												</tr>
												<tr height="35">
													<td align="right" width="15%">
														电话密码:
													</td>
													<td>
														<label>
															<input type="password" name="obj.phonePWD"
																id="phonePassword" onblur="myblur('phonePassword')"
																onfocus="myfocus('phonePassword')" class="input_textmid">
															<strong class="check_input">&nbsp;*</strong>
														</label>
													</td>
													<td style="padding-right: 10px;" align="left">
														<div id="phonePassword_vTip">
															&nbsp;
														</div>
													</td>
												</tr>
												<tr>
													<td align="right" width="10%">
														电话密码确认:
													</td>
													<td>
														<label>
															<input type="password" name="phonePWD1"
																id="phonePassword1" onblur="myblur('phonePassword1')"
																onfocus="myfocus('phonePassword1')"
																class="input_textmid">
															<strong class="check_input">&nbsp;*</strong>
														</label>
													</td>
													<td style="padding-right: 10px;" align="left">
														<div id="phonePassword1_vTip">
															&nbsp;
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<!-- 另一个结束-->
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="addCustomer()" id="add">
								添加
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
	var memberNo = '${memberNo}';
	document.getElementById("memberNo").value=memberNo;
	document.getElementById("memberNos").value=memberNo;
	var sessionOrgNo='${sessionScope.ORGANIZATIONID}';
	if(sessionOrgNo==""){
		changeOrganization('');
	}else{
		changeOrganization(sessionOrgNo);
	}
	if(${not empty obj.memberNo}) {
		document.getElementById("memberNo").value = "${obj.memberNo}";
	}
	function checkMemberNo(userID){
		var innerHTML = "";
		var marg = document.getElementById("shortId").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		}else if(((trim1(user.value)).length)!=12||user.value.length!=(trim1(user.value)).length){
			innerHTML = "交易账号15位数字且没有空格";
		} else if(isNaN(user.value)){
			innerHTML = "不合法的数字";
		}else {
			innerHTML = "";
			flag = true;
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		var id=document.getElementById("memberNo").value+document.getElementById("shortId").value;
		document.getElementById("customerId").value=id;
		if(document.getElementById("shortId").value != ""){
			checkAction.existId(id,function(isExist){
				if(isExist){
					alert('编号已存在，请重新添加');
					document.getElementById("shortId").value="";
				}
			}
		);}
		return flag;
	}
	
	function checkMemberNoLaw(){
		var vTip = document.getElementById("shortId_vTip");
		if(checkMemberNo("shortId")){
			vTip.className = "onError";
			vTip.innerHTML="正在检测数据合法性";
			checkCustomerNo();
		}
	}
	
	function onloadBrokerage(id){
		customerAdd.getBrokerageListByMember(id,sessionOrgNo,function(brokerageList){
			if(brokerageList==""){
			    return;
			}
			var brokerage=document.getElementById("brokerage");
			DWRUtil.removeAllOptions(brokerage);
			brokerage.style.width='300px';
			var item1 = document.createElement("OPTION");
			brokerage.options.add(item1);
			item1.value ="";
			item1.innerText = "请选择";
			for(var i = 0; i < brokerageList.length; i++){
			 	 	//DWRUtil.addOptions(brokerage,brokerageList[i].name);
			 	var item = document.createElement("OPTION");
			    brokerage.options.add(item);
			    item.value = brokerageList[i].brokerageNo;
			    item.innerText = brokerageList[i].name;
			    item.title=brokerageList[i].name;
			    if(brokerageList[i].brokerageNo=='${obj.brokerageNo}'){
			    	item.selected='selected';
			    }
				}
		});
	}
	function addCustomer(){
		var flag = myblur("all");
		if (!flag) {
			return false;
		}
		if(customerNoUsed!=3){
			return false;
		}else{
			var vaild = affirm("您确定要操作吗？");
			if(vaild==true){
		    	 frm.submit();
	   		}else{
	          return false;
	    	}
		}
	}
	function password(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (user != null) {
			var vTip = document.getElementById(userID + "_vTip");
			var flag = false;
			if (checkPassword(user.value) != "") {
				innerHTML = checkPassword(user.value);
			} else {
				flag = true;
			}
			vTip.innerHTML = innerHTML;
			if (flag) {
				vTip.className = "";
			} else {
				vTip.className = "onError";
			}
			return flag;
		} else {
			return true;
		}
	}

	function passwordcompare(userID, compareuserID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (user != null) {
			var comparevalue = document.getElementById(compareuserID).value;
			var vTip = document.getElementById(userID + "_vTip");
			var flag = false;
			if (checkPassword(user.value) != "") {
				innerHTML = checkPassword(user.value);
			} else {
				if (user.value != comparevalue) {
					innerHTML = "密码与确认密码不一致！";
				} else {
					flag = true;
				}
			}
			vTip.innerHTML = innerHTML;
			if (flag) {
				vTip.className = "";
			} else {
				vTip.className = "onError";
			}
			return flag;
		} else {
			return true;
		}
	}
	
	var customerNoUsed=1;
	var isPapersUsed=1;
	/**
	 * 检测账户是否被用过 1正在检测，2：已被用过 ，3：未被用过
	 */
	 function checkCustomerNo(){
		var id = document.getElementById("memberNo").value
				+ document.getElementById("shortId").value;
			document.getElementById("customerId").value = id;
			var vTip = document.getElementById("shortId_vTip");
			checkAction.existId(id, function(isExist) {
				if (isExist) {
					customerNoUsed=2;
					vTip.innerHTML="此账号已存在，请重新添加！";
					vTip.className = "onError";
				}else{
					customerNoUsed=3;
					vTip.innerHTML="此账号未被使用";
					vTip.className = "";
				}
			});
	 }
	
	 function checkPapersTypeLaw(){
			var vTip = document.getElementById("papersName_vTip");
			if(papersName("papersName")){
				vTip.innerHTML="正在检查数据合法性";
				vTip.className = "onError";
				checkPapersUsed();
			}
		}
	 
	 
	 
	</script>
<%@ include file="/public/footInc.jsp"%>
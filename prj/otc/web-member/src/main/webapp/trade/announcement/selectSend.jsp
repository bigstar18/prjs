<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='${basePath}/dwr/engine.js'>
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
</script>
<script type="text/javascript" src='${basePath}/dwr/interface/checkAction.js' />
</script>
<html>
	<head>
		<title>系统用户浏览</title>
		<script language="JavaScript" src="../../public/global.js"></script>
		<script language="JavaScript" src="../../public/open.js"></script>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<form name="frm"
				action="${basePath}/tradeManage/announcement/add.action"
				method="post">
				<textarea name="notice.title" style="display: none;">${notice.title}</textarea>
				<textarea name="notice.content" style="display: none;">${notice.content}</textarea>
				<input type="hidden" name="notice.author" value="${notice.author}" />
				<input type="hidden" name="notice.authorOrganization" value="${notice.authorOrganization}" />
				<fmt:formatDate pattern="yyyy-MM-dd" value="${notice.expiryTime}" var="dteObject" />
				<input type="hidden" name="notice.expiryTime" value="${dteObject}" />
				<input type="hidden" name="${GNNT_}specialMemberInfoIds"
					id="specialMemberInfoIds" value="${oldParams['specialMemberInfoIds']}"
					class="input_text">
				<input type="hidden" name="${GNNT_}memberInfoIds"
					id="memberInfoIds" value="${oldParams['memberInfoIds']}"
					class="input_text">
				<input type="hidden" name="${GNNT_}organizationIds"
					id="organizationIds" value="${oldParams['organizationIds']}"
					class="input_text">
				<input type="hidden" name="${GNNT_}traderInfoIds"
					id="traderInfoIds" value="${oldParams['traderInfoIds']}"
					class="input_text">
				<input type="hidden" name="${GNNT_}trader_ognIds"
					id="trader_ognIds" value="${oldParams['trader_ognIds']}"
					class="input_text">
				<table class="table1_style" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td>
							&nbsp;
							<div class="div_cxtjd">
								<img src="<%=skinPath%>/cssimg/13.gif" />
								选择发送对象
							</div>
							<div class="div_tj">
								<input type="hidden" name="specialType" value="specialMemeber" />
								<input type="hidden" name="memberType" value="member" />
								<input type="hidden" name="traderType" value="trader" />
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" align="center">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table border="0" cellspacing="0"
													cellpadding="0" width="840">
													<tr>
														<td colspan="2" height="10">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="center" width="100">
															<font color="#880000">居间:&nbsp;</font>
														</td>
														<td align="left">
															<input type="checkbox" name="isAllSpecial" value="N" onclick="isAllSpecials();"/>全部
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															<input type="hidden" value="" id="specialMember" name="specialMember" />
															<textarea rows="4" cols="70" id="specialsManagerShow" name="specialsManagerShow" readonly="readonly"></textarea>&nbsp;&nbsp;
															<button class="btn_sec" onclick="selectSpecialMember();" id="selSpecial" name="selSpecial">选择</button>
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															<%--<input type="checkbox" name="underlingBrokerage" value="Y" onclick="checkHasBrokerage();" />包括客户--%>
															<%--<input type="checkbox" name="relationOrganization" value="Y" />包括其下属居间--%>
														</td>
													</tr>
													<tr>
														<td colspan="2" height="10" style="border-bottom: 1px solid #bfbfbf;">
															&nbsp;
														</td>
														<td>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
													</tr>
													
													<tr>
														<td colspan="2" height="10">
															&nbsp;
														</td>
													</tr>
													
													<tr>
														<td align="center" width="100">
															<font color="#880000">机构:&nbsp;</font>
														</td>
														<td align="left">
															<input type="checkbox" name="isAllMember"  value="N" onclick="isAllMembers()"/>全部
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															<input type="hidden" id="memberInfo" name="memberInfo" value="" />
															<textarea rows="4" cols="70" id="managerShow" name="managerShow" readonly="readonly"></textarea>&nbsp;&nbsp;
															<button class="btn_sec" onclick="selectMember();" id="selMember" name="selMember">选择</button>
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															<input type="checkbox" name="underling" value="Y" onclick="checkHasMember();" />包括客户
															<%--<input type="checkbox" name="relationOrganization" value="Y" />包括其下属居间--%>
														</td>
													</tr>
													<tr>
														<td colspan="2" height="10" style="border-bottom: 1px solid #bfbfbf;">
															&nbsp;
														</td>
														<td>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
													</tr>
													<tr>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="center" width="100">
															<font color="#880000">客户:&nbsp;</font>
														</td>
														<td align="left">
															<input type="checkbox" name="isAllTrader"  value="N" onclick="isAllTraders()"/>全部
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															
														</td>
													</tr>
													<tr>
														<td align="left" width="100">
															&nbsp;
														</td>
														<td align="left">
															<input type="hidden" value="" id="trader" name="trader">
															<textarea rows="4" cols="70" id="traderManagerShow" name="traderManagerShow" onblur="checkUseDwr();"></textarea>&nbsp;&nbsp;
															<strong class="check_input">（客户编号之间请用逗号隔开）
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="2" height="10">
											&nbsp;
										</td>
									</tr>
									
								</table>
							</div>
							<table>
								<tr>
									<td colspan="2" height="10">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div class="div_list">
			<table border="0" cellspacing="0" cellpadding="0" width="50%"
				align="left">
				<tr valign="top">
					<td align="center" colspan="2">
						<button class="btn_sec" onclick="formSubmit();">
							发送
						</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="btn_sec" onclick="returnBack();">
							返回
						</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
<script type="text/javascript">
function returnBack() {
	frm.action = "${basePath}/tradeManage/announcement/show.action";
	frm.submit();
}
function selectSpecialMember(){
  	var url="${basePath}/tradeManage/announcement/specialList.action";
  	var result = dialog(url,window,610,580);
  	if(result == 1111){
  		frm.submit();
  	}
}

function selectMember(){
  	var url="${basePath}/tradeManage/announcement/memberList.action";
  	var result = dialog(url,window,610,580);
  	if(result == 1111){
  		frm.submit();
  	}
}

function selectCustomer(){
  	var url="${basePath}/tradeManage/announcement/memberListForCustomer.action";
  	var result = dialog(url,window,610,580);
  	if(result == 1111){
  		frm.submit();
  	}
}

function isAllSpecials() {
	if (frm.isAllSpecial.checked) {
		frm.specialsManagerShow.value = "";
		//setReadOnly(frm.specialsManagerShow);
		frm.specialsManagerShow.readOnly = true;
  		frm.specialsManagerShow.style.backgroundColor = "#C0C0C0";
		frm.selSpecial.disabled = "true";
		frm.memberInfoIds.value = "";
		//frm.underlingBrokerage.disabled = "true";
		//frm.underlingBrokerage.checked = "";
	} else {
		//setReadWrite(frm.specialsManagerShow);
		frm.specialsManagerShow.style.backgroundColor = "";
		frm.selSpecial.disabled = "";
		//frm.underlingBrokerage.disabled = "";
		//frm.underlingBrokerage.checked = "";
	}
}
function isAllMembers() {
	if (frm.isAllMember.checked) {
		frm.managerShow.value = "";
		//setReadOnly(frm.managerShow);
		frm.managerShow.readOnly = true;
  		frm.managerShow.style.backgroundColor = "#C0C0C0";
		frm.selMember.disabled = "true";
		//frm.underling.disabled = "true";
		//frm.underling.checked = "";
		frm.memberInfoIds.value = "";
		frm.organizationIds.value = "";
	} else {
		//frm.underling.disabled = "";
		frm.underling.checked = "";
		//setReadWrite(frm.managerShow);
		frm.managerShow.style.backgroundColor = "";
		frm.selMember.disabled = "";
	}
}
function isAllTraders() {
	if (frm.isAllTrader.checked) {
		frm.traderManagerShow.value = "";
		//setReadOnly(frm.traderManagerShow);
		frm.traderManagerShow.readOnly = true;
  		frm.traderManagerShow.style.backgroundColor = "#C0C0C0";
	} else {
		//setReadWrite(frm.traderManagerShow);
		frm.traderManagerShow.readOnly = "";
		frm.traderManagerShow.style.backgroundColor = "";
	}
}

function checkHasMember(){
	if(!frm.isAllMember.checked && frm.managerShow.value == ""){
		alert("请选择机构！");
		frm.underling.checked = "";
		return false;
	}
}

function checkHasBrokerage(){
	if(!frm.isAllMember.checked && frm.specialsManagerShow.value == ""){
		alert("请选择居间！");
		frm.underlingBrokerage.checked = "";
		return false;
	}
}
function formSubmit() {
	var tmsValue = frm.traderManagerShow.value.replace(/(^\s+)|(\s+$)/g, "");
//	if(tmsValue == ""){
	//	frm.traderManagerShow.value = "";
	//	alert("请选择发送对象！1");
	//	return false;
	//}
	if(!frm.isAllMember.checked && !frm.isAllSpecial.checked && !frm.isAllTrader.checked 
			&& frm.specialsManagerShow.value == "" && frm.managerShow.value == "" && tmsValue == ""){
		alert("请选择发送对象！");
		return false;
	}
	if (confirm("您确定要提交吗？")) {
		frm.submit();
	}
}
function checkUseDwr() {
	var tmsValue = frm.traderManagerShow.value.replace(/[\r\n]/g, " ");
	var tmsvalue2 = tmsValue.replace(/，/g,",").split(",");
	if(tmsvalue2 != ""){
		for(var i = 0; i < tmsvalue2.length; i++){
			if(tmsvalue2[i] == ""){
				frm.traderManagerShow.value = "";
				alert("逗号之间必须填写客户！");
				return false;
			}
		}
	}
	checkAction.existCustomerIds(tmsValue, '${notice.author}', function(wrongCustomerId){
		if(wrongCustomerId){
			var msg = "客户编号" + wrongCustomerId + "不存在或不属于该会员，请重新填写！";
			alert(msg);
			document.getElementById("traderManagerShow").value="";
			document.getElementById("traderManagerShow").focus();
			return false;
		}
	});
}
</script>

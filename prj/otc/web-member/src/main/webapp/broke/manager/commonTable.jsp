<%@ page contentType="text/html;charset=GBK"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'></script>
<script type='text/javascript' src='${basePath}/dwr/util.js'></script>
<script type="text/javascript" src='${basePath}/dwr/interface/checkAction.js'/></script>

<style>
<!--
select{
	width:100px;
	overflow:hidden;
}
-->
</style>
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- 基本信息 -->
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
				<span class="st_title2"><b>基&nbsp;本&nbsp;信&nbsp;息&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo" align="center">
					<table width="100%" border="0">
						<tr height="35">
							<td align="right" width="18%">
								客户代表代码:
							</td>
							<td colspan="3">
								<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
								<input type="text" onblur="checkManagerId('managerNo')" onfocus="myfocus('managerNo')"
								id="managerNo" name="obj.managerNo" value="${obj.managerNo }" class="input_text_mid" >
								<strong class="check_input">&nbsp;*</strong>
							</td>
							<td><div id="managerNo_vTip"></div></td>
						</tr>
						<tr height="35">
							<td align="right">
								客户代表名称:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									onblur="myblur('name')"	onfocus="myfocus('name')"
									class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
							</td>
							<td><div id="name_vTip"></div></td>
						</tr>
						<tr height="35">
							<td  align="right">
								所属机构代码:
							</td>
							<td colspan="3">
								<select name="obj.parentOrganizationNO" id="parentNo">
								<option value="">请选择</option>
								<c:forEach items="${memberInfoList}" var="list">
							  	<option value="${list.organizationNO}" <c:if test="${obj.parentOrganizationNO==list.organizationNO}">selected="selected"</c:if>>${list.organizationNO}</option>
							  </c:forEach>
							</select>
							</td>
					</tr>
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
</table>

<script type="text/javascript">
<!--
function myblur(userID) {
	var flag = true;

	if ("nameT" == userID) {
		flag = nameTT(userID);
	}else if("phone"==userID){
		flag = phone(userID);
	}else if("mobile"==userID){
		flag = mobile(userID);
	}else {
		if (!checkManagerId("managerNo"))
			flag = false;
		if (!nameT("name"))
			flag= false;
		if (!phone("phone"))
			flag = false;
		if (!mobile("mobile"))
			flag = false;
	}
	return flag;
}
function postcode(userID) {
	var innerHTML = "";
	var marg = document.getElementById("postcode").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		flag = true;
	}else if(isNaN(user.value)){
			innerHTML = "输入有误";
	}
	else if (!isStr(user.value, true, null)) {
		innerHTML = "不合法字符";
	} else if (user.value.length != 6) {
		innerHTML = "长度必须为6位";
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
}

function phone(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		flag = true;
	} else if (!checkphone(user.value)) {
		innerHTML = "输入有误，仅能输入数字和中划线";
	} else if (user.value.length > 12) {
		innerHTML = "长度不能超过12位";
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
}

function mobile(userID) {
	return phone(userID);
}

function fax(userID) {
	return phone(userID);
}


function nameT(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else if(user.value.length<2||user.value.length>18){
		innerHTML = "长度不能小于2位不能超过18位";
	}else{
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
function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	if ('minRiskFund' == userID) {
		innerHTML = "不能为空";
	}
	if ('warnTh' == userID) {
		innerHTML = "不能为空";
	}
	if ('frozenTh' == userID) {
		innerHTML = "不能为空";
	}
	if ('cu_F_WarnTh' == userID) {
		innerHTML = "不能为空";
	}
	if ('m_SelfTradeRate' == userID) {
		innerHTML = "不能为空";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
//-->
</script>

<!-- 另一个开始-->
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
								<span class="st_title2"><b>联&nbsp;系&nbsp;信&nbsp;息&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo1" align="center">
					<table width="90%" border="0">
						<tr height="35">
							<td align="right" width="18%">
							  电子邮箱:
							</td>
							<td colspan="3">
								<input type="text" name="obj.email" class="input_textdmax" value="${obj.email}">
							</td>
						</tr>
						<tr height="35">
							<td align="right"> 
								联系电话: 
							</td>
							<td>
								<input type="text" name="obj.telephone" class="input_textdmin" id="phone"
								onblur="myblur('phone')"	onfocus="myfocus('phone')"value="${obj.telephone}">
							</td>
							<td><div id="phone_vTip"></div></td>
						</tr>
						<tr>
							<td align="right" width="12%"> 
								手机: 
							</td>
							<td>
								<input type="text" name="obj.mobile"  class="input_textdmin" id="mobile"
								onblur="myblur('mobile')"	onfocus="myfocus('mobile')" value="${obj.mobile}">
							</td>
							<td><div id="mobile_vTip"></div></td>
						</tr>
						<tr height="35">
							<td align="right">
								通讯地址:
							</td>
							<td colspan="3">
								<input type="text" name="obj.address" class="input_textdmax" value="${obj.address}">
							</td>
						</tr>
						
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
	<!-- 另一个结束-->
</table>


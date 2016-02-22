<%@ page contentType="text/html;charset=GBK"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'></script>
<script type='text/javascript' src='${basePath}/dwr/util.js'></script>
<script type="text/javascript" src='${basePath}/dwr/interface/checkAction.js'/></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checksAction.js'/></script>

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
				<div class="div_cxtj">
				   <img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;基本信息
			    </div>
				<div id="baseinfo" align="center">
					<table width="100%" border="0" class="table2_style">
						<tr height="35">
							<td align="right" width="18%">居间代码: 
							</td>
							<td colspan="3">
								<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
								<span id="brokerageNoSpan">${obj.brokerageNo }</span>
								<input type="hidden" id="brokerageNo" name="obj.brokerageNo" value="${obj.brokerageNo }">
								<strong class="check_input">&nbsp;*</strong>
							</td>
								<td><div id="brokerageNo_vTip"></div></td>
						</tr>
						<tr height="35">
							<td align="right">居间名称: 
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="brokerageName" value="${obj.name}"
									onblur="myblur('brokerageName')"	onfocus="myfocus('brokerageName')"
									class="input_text_mid"><strong class="check_input">&nbsp;*</strong>
							</td>
							<td><div id="brokerageName_vTip"></div></td>
						</tr>
						<tr height="35">
							<td align="right">所属机构: 
							</td>
							<td colspan="3">
							<span name="parentOrganizationName" id="parentOrgName">${obj.organizationName}</span>
							<input type="hidden" name="organizationNo" id="parentNo" value="${obj.organizationNO}">
								<c:if test="${parentOrgNo!=''}">
									<c:if test="${obj.organizationNO==null||obj.organizationNO==''}">
										<c:forEach items="${organizationList}" var="list">
									  		<c:if test="${parentOrgNo==list.organizationNO}">
									  			<c:set var="setParentNo" value="${list.organizationNO}"/>
									  			<c:set var="setParentName" value="${list.name}"/>
									  			<script type="text/javascript">
										  			document.getElementById('parentNo').value='${setParentNo}';
										  			document.getElementById('parentOrgName').innerHTML='${setParentName}';
									  			</script>
									  		</c:if>
									 	 </c:forEach>
									</c:if>
								</c:if>
								<c:if test="${parentOrgNo==''}">
									<c:if test="${obj.organizationNO==null||obj.organizationNO==''}">
										<c:set var="setParentNo" value=""/>
							  			<c:set var="setParentName" value="会员直属居间"/>
							  			<script type="text/javascript">
								  			document.getElementById('parentNo').value='${setParentNo}';
								  			document.getElementById('parentOrgName').innerHTML='${setParentName}';
							  			</script>
							  		</c:if>
								</c:if>
							<a id="getOrgTree" href="#" onclick="getOrgTree()"><img align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
							</a>    
							</td>
							<td><div id="brokerageName_vTip"></div></td>
						</tr>
					</table>
				</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
<!--
function myblur(userID) {
	var flag = true;

	if ("brokerageName" == userID) {
		flag = brokerageName(userID);
	} else if ("phone" == userID) {
		flag = phone(userID);
	} else if ("mobile" == userID) {
		flag = mobile(userID);
	}else if ("email" == userID) {
		flag = emailT(userID);
	}
	else {
		if (!brokerageName("brokerageName"))
			flag= false;
		if (!phone("phone"))
			flag = false;
		if (!mobile("mobile"))
			flag = false;
		if (!emailT("email"))
			flag = false;
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
	} else if (!checkphone1(user.value)) {
		innerHTML = "输入有误，仅能输入数字、中划线";
	} else if (user.value.length >12) {
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
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		flag = true;
	} else if (!number(user.value)) {
		innerHTML = "输入有误，仅能输入数字";
	} else if (user.value.length !=11) {
		innerHTML = "长度应该是11位";
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

function emailT(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if(isEmpty(user.value + "")) {
		flag = true;
	}else if (!email(user.value)) {
		innerHTML = "输入有误";
	}else {
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

function brokerageName(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else if(user.value.length<2||user.value.length>60){
		innerHTML = "长度不能小于2位不能超过60位";
	}else if(!checkName(user.value)){
		innerHTML = "含有不合法字符";
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
	function checkName(name){
		 var re = /^\S{1,61}$/;
	   var flag = false;
		if (!re.exec(name)) {
                flag= false;
            }else{
                flag= true;
            }
		return flag;
	}
function getOrgTree(){
	var parentOrgNo=document.getElementById('parentNo').value;
	var url="${basePath}/broke/brokerage/getOrganizationTree.action?parentOrgNo="+parentOrgNo;
	var result=window.showModalDialog(url,window, "dialogWidth=500px; dialogHeight=520px; status=yes;scroll=yes;help=no;");
}
//-->
</script>

<!-- 另一个开始-->
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
				<div class="div_cxtj">
				   <img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;联系信息
			    </div>
				<div id="baseinfo1" align="center">
					<table width="90%" border="0" class="table2_style">
						<tr height="30">
							<td align="right"> 
								联系电话: 
							</td>
							<td>
								<input type="text" name="obj.telephone" class="input_textdmin" id="phone"
								onblur="myblur('phone')"	onfocus="myfocus('phone')"value="${obj.telephone}">
							</td>
							<td><div id="phone_vTip"></div></td>
						</tr>
						<tr height="30">
							<td align="right" width="12%"> 
								手机: 
							</td>
							<td>
								<input type="text" name="obj.mobile"  class="input_textdmin" id="mobile"
								onblur="myblur('mobile')"	onfocus="myfocus('mobile')" value="${obj.mobile}">
							</td>
							<td><div id="mobile_vTip"></div></td>
						</tr>
						<tr height="30">
							<td align="right" width="18%">
							  电子邮件:
							</td>
							<td>
								<input id ="email" type="text" name="obj.email" class="input_textdmax" value="${obj.email}" onblur="myblur('email')">
							</td>
							<td><div id="email_vTip"></div></td>
						</tr>
						<tr height="30">
							<td align="right">
								通讯地址:
							</td>
							<td colspan="2">
								<input type="text" name="obj.address" id="address" class="input_textdmax" value="${obj.address}">
							</td>
							<td><div id="address_vTip"></div></td>
						</tr>
						
					</table>
				</div>
		</td>
	</tr>
	<!-- 另一个结束-->
</table>


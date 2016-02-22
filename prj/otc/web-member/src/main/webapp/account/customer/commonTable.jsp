<%@ page contentType="text/html;charset=GBK"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'>
	
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/checkAction.js' />
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/customerAdd.js' />
</script>
<style>
<!--
select {
	width: 100px;
	overflow: hidden;
}
-->
</style>
<%
	String memberNo = "提示信息,未确定信息";
	String organization = "提示信息,未确定信息";
	String manager = "提示信息,未确定信息";
	String brokerage = "提示信息,未确定信息";
	String shortId = "提示信息,未确定信息";
	String customerName = "提示信息,未确定信息";
	String papersName = "提示信息,未确定信息";
	String papersType = "提示信息,未确定信息";
%>
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- 基本信息 -->
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				&nbsp;基本信息
			</div>
			<div class="div_tj">
				<table width="90%" border="0" class="table2_style">
					<input type="hidden" name="obj.customerNo" id="customerId"
						value="${obj.customerNo}">
					<input type="hidden" name="obj.createTime"
						value="${obj.createTime}">
					<input type="hidden" name="obj.memberNo" id="memberNo"
						value="${obj.memberNo}">
					<tr height="35">
						<td align="right" width="15%">
							所属机构:
						</td>
						<td align="left" width="100%" colspan="2">
							<span name="parentOrganizationName" id="parentOrgName">${obj.organizationName}</span>
								<input type="hidden" name="objSpecial.organizationNo" id="parentOrganizationNo" value="${obj.organizationNo}">
								<c:if test="${parentOrgNo!=''}">
									<c:if test="${obj.organizationNo==null||obj.organizationNo==''}">
								  			<script type="text/javascript">
									  			document.getElementById('parentOrganizationNo').value='${organization.organizationNO}';
									  			document.getElementById('parentOrgName').innerHTML='${organization.name}';
								  			</script>
									</c:if>
								</c:if>
								<c:if test="${parentOrgNo==''&&(obj.organizationNo==null||obj.organizationNo=='')}">
								  			<script type="text/javascript">
									  			document.getElementById('parentOrganizationNo').value='';
									  			document.getElementById('parentOrgName').innerHTML='会员直属客户';
								  			</script>
								</c:if>
							<a href="javascript:getOrgTree();"><img align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
							</a>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%">
							所属居间:
						</td>
						<td width="45%" align="left" colspan="2">
							<select id="brokerage" name="objSpecial.brokerageNo"  style="width: 200px;">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%">
							交易账号:
						</td>
						<td align="left">
							<input type="text" id="memberNos" readonly="readonly"
								style="width: 30px; background-color: #bebebe">
							<input type="text"
								style="background-color: #FFFFFF; height: 20px; width: 147px; border: 1px solid #7f9db9;"
								id="shortId" onblur="checkMemberNoLaw('shortId')"
								onfocus="myfocus('shortId')" maxlength="15">
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;">
							<div id="shortId_vTip"></div>
						</td>
					</tr>
					<tr height="35">
						<td align="right" width="15%">
							客户名称:
						</td>
						<td align="left" width="45%">
							<input type="text" name="obj.name" id="customerName"
								value="${obj.name}" class="input_text_mid"
								onblur="myblur('customerName')"
								onfocus="myfocus('customerName')" maxlength="60">
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;">
							<div id="customerName_vTip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							证件类型:
						</td>
						<td>
							<select name="obj.papersType" id="papersType" 
								class="select_wid" style="width: 110px;"
								onblur="myblur('papersType')" onfocus="myfocus('papersType')">
								<c:forEach items="${papersTypeUpdateMap}" var="maps">
									<option value="${maps.key}"
										<c:if test="${obj.papersType==maps.key}">selected="selected"</c:if>>
										${maps.value}
									</option>
								</c:forEach>
							</select>
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;">
							<div id="papersType_vTip"></div>
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							证件号码:
						</td>
						<td>
							<input type="text" name="obj.papersName" id="papersName"
								value="${obj.papersName}" class="input_text_mid"
								onblur="myblur('papersName')" onfocus="myfocus('papersName')">
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;">
							<div id="papersName_vTip"></div>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function myblur(userID) {
		var flag = true;
		if ("customerName" == userID) {
			flag = customerName(userID);
		} else if ("papersType" == userID) {
			flag = papersType(userID);
		} else if ("papersName" == userID) {
			flag = papersName(userID);
		}else if ("phone" == userID) {
			flag = phone(userID);
		} else if ("fax" == userID) {
			flag = fax(userID);
		} else if ("phonePassword" == userID) {
			flag = password(userID);
		} else if ("password" == userID) {
			flag = password(userID);
		} else if ("phonePassword1" == userID) {
			flag = passwordcompare(userID, "phonePassword");
		}else if ("email" == userID) {
			flag = emailT(userID);
		}else if ("postCode" == userID) {
			flag = postcode(userID);
		}
		else if ("password1" == userID) {
			flag = passwordcompare(userID, "password");
		} else {
			if (!customerName("customerName")){
				flag = false;
			}
			if (!postcode("postCode")) {
				flag = false;
			}
			if (!password("phonePassword")) {
				flag = false;
			}
			if (!password("password")) {
				flag = false;
			}
			if(!emailT("email")){
				flag=false;
			}
			if (!phone("phone"))
				flag = false;
			if (!fax("fax"))
				flag = false;
			if (!passwordcompare("phonePassword1", "phonePassword")) {
				flag = false;
			}
			if (!passwordcompare("password1", "password")) {
				flag = false;
			}
			if (!papersName("papersName")) {
				flag=false;
			}
		}
		return flag;
	}
	
	function postcode(userID) {
		var innerHTML = "";
		var marg = document.getElementById("postCode").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			flag = true;
		} else if (isNaN(user.value)) {
			innerHTML = "输入有误";
		} else if (!isStr(user.value, true, null)) {
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
			innerHTML = "输入有误，仅能输入数字、中划线和逗号";
		} else if (user.value.length > 64) {
			innerHTML = "长度不能超过64位";
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
	function fax(userID) {
		return phone(userID);
	}

	function memberNo(userID) {
		var innerHTML = "";
		var marg = document.getElementById("memberNo").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		} else if (isNaN(user.value)) {
			innerHTML = "不合法的数字";
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
	function customerName(userID) {
		var innerHTML = "";
		var marg = document.getElementById("customerName").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		} else if(user.value.length<1||user.value.length>32) {
			innerHTML = "应在1~32位之间";
		}else if(!checkName(user.value)) {
			innerHTML = "有不合法字符";
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
	
	function myfocus(userID) {
	}

	function papersType(userID) {
		return true;
	}
	function papersName(userID) {
		var innerHTML = "";
		var marg = document.getElementById("papersName").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		}else if(!isStr(user.value,null,new Array('-'))){
			innerHTML = "不能输入不合法字符";
		}else if(user.value.length > 18||user.value.length<6) {
			innerHTML = "长度不能小于6位且不能超过18位";
		} else{
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
	}
function emailT(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			flag = true;
		} else if (!email(user.value)) {
			innerHTML = "输入有误";
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
</script>


<!-- 另一个开始-->
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				&nbsp;联系信息
			</div>
			<div class="div_tj">
				<table width="90%" border="0" class="table2_style">
				
					<tr height="35">
						<td align="right" width="15%">
							联系电话:
						</td>
						<td width="45%">
							<input type="text" name="obj.phone" id="phone"
								onblur="myblur('phone')" onfocus="myfocus('phone')"
								class="input_text_mid" value="${obj.phone}">
								
						</td>
						
						<td style="padding-right: 15px;" align="left">
							<div id="phone_vTip">
								&nbsp;
							</div>
						</td>
						</tr>
					<tr>
						<td align="right" width="15%">
							传真机号:
						</td>
						<td>
							<label>
								<input type="text" name="obj.fax" id="fax"
								onblur="myblur('fax')" onfocus="myfocus('fax')"
									class="input_textmin" value="${obj.fax}">
							</label>
						</td>
						<td style="padding-right: 15px;" align="left">
							<div id="fax_vTip">
								&nbsp;
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%">
							邮政编码:
						</td>
						<td>
							<input type="text" name="obj.postCode" id="postCode" 
								onblur="myblur('postCode')" 
								class="input_textmin" 
								value="${obj.postCode}">
						</td>
						<td style="padding-right: 15px;">
							<div id="postCode_vTip">
								&nbsp;
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%">
							电子邮箱:
						</td>
						<td>
							<input type="text" name="obj.email" id="email"
								onblur="myblur('email')" onfocus="myfocus('email')"
								class="input_text_mid" value="${obj.email}">
						</td>
						<td style="padding-right: 15px;">
							<div id="email_vTip">
								&nbsp;
							</div>
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							通讯地址:
						</td>
						<td>
							<input type="text" name="obj.address" id="address"
								class="input_text_mid" value="${obj.address}">
						</td>
						<td style="padding-right: 15px;">
							<div id="address_vTip">
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
<script type="text/javascript">
	if (typeof window['DWRUtil'] == 'undefined') {
		window.DWRUtil = dwr.util;
	}
	function changeOrganization(id) {
		customerAdd.getBrokerageAndManagerList(id, sessionOrgNo,registerId,function(map) {
			if (!map) {
				return;
			}
			var brokerageList = map['brokerageList'];
			var brokerage = document.getElementById("brokerage");
			DWRUtil.removeAllOptions(brokerage);
			brokerage.style.width = '300px';
			var item = document.createElement("OPTION");
			brokerage.options.add(item);
			item.value = "";
			item.innerText = "请选择";
			for ( var i = 0; i < brokerageList.length; i++) {
				//DWRUtil.addOptions(brokerage,brokerageList[i].name);
				var item = document.createElement("OPTION");
				brokerage.options.add(item);
				item.value = brokerageList[i].brokerageNo;
				item.innerText = brokerageList[i].name;
				item.title=brokerageList[i].name;
				if (brokerageList[i].brokerageNo == '${obj.brokerageNo}') {
					item.selected = 'selected';
				}
			}
	 	 	
		});
	}
	
	function checkPapersUsed(){
		var papersType = document.getElementById("papersType").value;
		var id = document.getElementById("papersName").value;
		var vTip = document.getElementById("papersName_vTip");
		checkAction.existCustomerPapers(id, papersType, '', function(isExist) {
			if (isExist) {
				isPapersUsed=2;
				document.getElementById("papersName").focus();
				vTip.innerHTML="此证件号码已存在，请重新添加！";
				vTip.className = "onError";
			}else{
				isPapersUsed=3;
				vTip.innerHTML="此证件号码未被使用";
				vTip.className = "";
			}
		});
	}
	function getOrgTree(){
		var parentOrgNo=document.getElementById('parentOrganizationNo').value;
		var url="${basePath}/account/customer/getOrganizationTree.action?parentOrgNo="+parentOrgNo;
		var result=window.showModalDialog(url,window, "dialogWidth=500px; dialogHeight=520px; status=yes;scroll=yes;help=no;");
		if(result!=null){
			changeOrganization(result);
		}
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
</script>

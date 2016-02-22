<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type='text/javascript' src='${basePath}/dwr/engine.js'>
	
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
	
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/checkAction.js' />
</script>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- ������Ϣ -->
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				&nbsp;������Ϣ
			</div>
			<div id="baseinfo" align="center">
				<table border="0" width="100%" class="table2_style">
					<tr height="35">
						<td align="right" width="20%">
							�ر��Ա���:
						</td>
						<td width="35%">
							<input type="text" class="input_textmin" id="id" name="obj.id"
								value="${obj.id}" onblur="changeUserId('id')">
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;" width="40%">
							<div id="id_vTip"></div>
						</td>
						<!-- 
							<td align="right" class="signNoHidden">
								ǩԼ�˺�:
							</td>
							<td align="left" class="signNoHidden">
								${obj.signNo}
							</td>
							 -->
					</tr>
					<tr height="35">
						<td align="right" width="20%">
							�ر��Ա����:
						</td>
						<td width="45%">
							<input type="text" class="input_text_mid" id="memberName"
								onblur="myblur('memberName')" onfocus="myfocus('memberName')"
								name="obj.name" value="${obj.name}">
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;" width="35%">
							<div id="memberName_vTip"></div>
						</td>
					</tr>
					<tr height="35">
						<td align="right" width="15%">
							֤������:
						</td>
						<td>
							<select name="obj.papersType" class="select_wid" id="papersType"
								onblur="myblur('papersType')" onfocus="myfocus('papersType')">
								<c:forEach items="${accountPapersTypeMap}" var="maps">
									<option value="${maps.key}"
										<c:if test="${obj.papersType==maps.key}">selected="selected"</c:if>>
										${maps.value}
									</option>
								</c:forEach>
							</select>
							<strong class="check_input">&nbsp;&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;" width="50%">
							<div id="papersType_vTip"></div>
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							֤������:
						</td>
						<td>
							<input name="obj.papersName" id="papersName"
								onblur="myblur('papersName')" onfocus="myfocus('papersName')"
								class="input_text_mid" type="text" value="${obj.papersName}">
							<strong class="check_input">&nbsp;*</strong>
						</td>
						<td style="padding-right: 15px;" width="50%">
							<div id="papersName_vTip"></div>
						</td>
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
		if ("memberName" == userID) {
			flag = memberName(userID);
		} else if ("papersName" == userID) {
			flag = papersName(userID);
		} else if ("papersType" == userID) {
			flag = papersType(userID);
		} else if ("faxnumber" == userID) {
			flag = faxnumber(userID);
		} else if ("postcode" == userID) {
			flag = postcode(userID);
		} else if ("phonenumber" == userID) {
			flag = phonenumber(userID);
		} else if ("email" == userID) {
			flag = emailT(userID);
		} else if ("password" == userID) {
			flag = password(userID);
		} else if ("password1" == userID) {
			flag = passwordcompare(userID, "password");
		} else {
			if (!checkSpecialMemberId("id"))
				flag = false;
			if (!memberName("memberName"))
				flag = false;
			if (!papersName("papersName"))
				flag = false;
			if (!faxnumber("faxnumber"))
				flag = false;
			if (!postcode("postcode"))
				flag = false;
			if (!emailT("email")) {
				flag = false;
			}
			if (!phonenumber("phonenumber"))
				flag = false;
			if (!password("password")) {
				flag = false;
			}
			if (!passwordcompare("password", "password1")) {
				flag = false;
			}
		}
		return flag;
	}

	function id(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "����Ϊ��";
		} else if (!isStr(user.value, null, null)) {
			innerHTML = "�������벻�Ϸ��ַ�";
		} else {
			if (user.value.length != 3) {
				innerHTML = "ӦΪ3λ";
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
	}
	function memberName(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "����Ϊ��";
		} else {
			if (user.value.length<1||user.value.length>32) {
				innerHTML = "Ӧ��1~32λ֮��";
			} else if (!checkName(user.value)) {
				innerHTML = "���ڲ��Ϸ��ַ�";
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
			innerHTML = "����Ϊ��";
		} else if (!isStr(user.value, true, new Array('-'))) {
			innerHTML = "���Ϸ��ַ�";
		} else if (user.value.length > 30 || user.value.length < 6) {
			innerHTML = "���Ȳ���С��6λ�Ҳ��ܳ���30λ";
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

	function faxnumber(userID) {
		return phonenumber(userID);
	}

	function postcode(userID) {
		var innerHTML = "";
		var marg = document.getElementById("postcode").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			flag = true;
		} else if (isNaN(user.value)) {
			innerHTML = "��������";
		} else if (!isStr(user.value, true, null)) {
			innerHTML = "���Ϸ��ַ�";
		} else if (user.value.length != 6) {
			innerHTML = "���ȱ���Ϊ6λ";
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

	function phonenumber(userID) {
		var innerHTML = "";
		var marg = document.getElementById("phonenumber").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			flag = true;
		} else if (!checkphone(user.value)) {
			innerHTML = "�������󣬽����������֡��л��ߺͶ���";
		} else if (user.value.length > 64) {
			innerHTML = "���Ȳ��ܳ���64λ";
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
		if (isEmpty(user.value + "")) {
			flag = true;
		} else if (!email(user.value)) {
			innerHTML = "��������";
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
	function myfocus(userID) {
		/*var vTip = document.getElementById(userID + '_vTip');
		var innerHTML = "";
		if ('minRiskFund' == userID) {
			innerHTML = "����Ϊ��";
		}
		if ('warnTh' == userID) {
			innerHTML = "����Ϊ��";
		}
		if ('frozenTh' == userID) {
			innerHTML = "����Ϊ��";
		}
		if ('cu_F_WarnTh' == userID) {
			innerHTML = "����Ϊ��";
		}
		if ('m_SelfTradeRate' == userID) {
			innerHTML = "����Ϊ��";
		}
		vTip.innerHTML = innerHTML;
		vTip.className = "onFocus";*/
	}
//-->
</script>
<!-- ��һ����ʼ-->
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				&nbsp;��ϵ��Ϣ
			</div>
			<div id="baseinfo1" align="center">
				<table width="100%" border="0" class="table2_style">
					<tr height="35">
						<td align="right" width="20%">
							�������:
						</td>
						<td width="40%">
							<label>
								<input id="faxnumber" class="input_textmid" type="text"
									onblur="myblur('faxnumber')" name="obj.fax" value="${obj.fax}">
							</label>
						</td>
						<td style="padding-right: 15px;">
							<div id="faxnumber_vTip"></div>
							&nbsp;
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��������:
						</td>
						<td>
							<input id="postcode" class="input_textmid" type="text"
								onblur="myblur('postcode')" name="obj.postCode"
								value="${obj.postCode }">
						</td>
						<td style="padding-right: 15px;">
							<div id="postcode_vTip"></div>
							&nbsp;
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��ϵ�绰:
						</td>
						<td>
							<input id="phonenumber" class="input_textcdmax" type="text"
								onblur="myblur('phonenumber')" name="obj.phone"
								value="${obj.phone}">
						</td>
						<td style="padding-right: 15px;">
							<div id="phonenumber_vTip"></div>
							&nbsp;
						</td>
					</tr>
					<tr height="35">
						<td width="20%" align="right">
							��������:
						</td>
						<td>
							<label>
								<input id="email" class="input_textcdmax" type="text"
									name="obj.email" value="${obj.email}" onblur="myblur('email')">
							</label>
						</td>
						<td style="padding-right: 15px;">
							<div id="email_vTip"></div>
							&nbsp;
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							ͨѶ��ַ:
						</td>
						<td colspan="2">
							<input class="input_textcdmax" type="text" name="obj.address"
								value="${obj.address }">
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<!-- ��һ������-->
</table>


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
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- ������Ϣ -->
	<tr>
		<td colspan="4">
				<div class="div_cxtj">
				   <img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;������Ϣ
			    </div>
				<div id="baseinfo" align="center">
					<table width="100%" border="0" class="table2_style">
						<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
						<input type="hidden" id="organizationNo" name="obj.organizationNO" value="${obj.organizationNO }">
						<!-- 
						<tr height="35">
							<td align="right" width="20%">
								��������:
							</td>
							<td>
								<span id="organizationNoSpan">${obj.organizationNO }</span>
								<strong class="check_input">&nbsp;*</strong>
							</td>
							
						</tr>
						 -->
						<tr height="35">
							<td align="right" width="20%">
								��������:
							</td>
							<td width="45%">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									class="input_textmid" onblur="myblur('name')"><strong class="check_input">&nbsp;*</strong>
							</td>
							<td width="35%" style="padding-right: 10px;" align="left"><div id="name_vTip">&nbsp;</div></td>
						</tr>
						<tr height="35">
							<td  align="right">
								�ϼ���������:
							</td>
							<td>
								<span name="parentOrganizationName" id="parentOrgName">${obj.parentOrganizationName}</span>
								<input type="hidden" name="obj.parentOrganizationNO" id="parentNo" value="${obj.parentOrganizationNO}">
								<c:if test="${parentOrgNo!=''}">
									<c:if test="${obj.parentOrganizationNO==null||obj.parentOrganizationNO==''}">
										<c:forEach items="${memberInfoList}" var="list">
									  		<c:if test="${parentOrgNo==list[0]}">
									  			<c:set var="setParentNo" value="${list[0]}"/>
									  			<c:set var="setParentName" value="${list[1]}"/>
									  			<script type="text/javascript">
										  			document.getElementById('parentNo').value='${setParentNo}';
										  			document.getElementById('parentOrgName').innerHTML='${setParentName}';
									  			</script>
									  		</c:if>
									 	 </c:forEach>
									</c:if>
								</c:if>
								<c:if test="${parentOrgNo==''}">
									<c:if test="${obj.parentOrganizationNO==null||obj.parentOrganizationNO==''}">
										<c:set var="setParentNo" value=""/>
							  			<c:set var="setParentName" value="��Աֱ������"/>
							  			<script type="text/javascript">
								  			document.getElementById('parentNo').value='${setParentNo}';
								  			document.getElementById('parentOrgName').innerHTML='${setParentName}';
							  			</script>
							  		</c:if>
								</c:if>
							<a href="javascript:getOrgTree();"><img align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
							</a>
							</td>
					</tr>
	
					</table>
				</div>
		</td>
	</tr>
</table>


<!-- ��һ����ʼ-->
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
				<div class="div_cxtj">
				   <img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��ϵ��Ϣ
			    </div>
				<div id="baseinfo1" align="center">
					<table width="100%" border="0" class="table2_style">
						<tr height="30">
							<td align="right" width="20%"> 
								��ϵ�绰: 
							</td>
							<td width="35%">
								<input type="text" name="obj.telephone" id="phone" onblur="myblur('phone')" class="input_textmid" value="${obj.telephone}">
							</td>
							<td style="padding-right: 10px;" align="left"><div id="phone_vTip">&nbsp;</div></td>
						</tr>
						<tr height="30">
							<td align="right" width="20%"> 
								�ֻ�: 
							</td>
							<td width="35%">
								<input type="text" name="obj.mobile"  id="mobile" onblur="myblur('mobile')" class="input_textmid" value="${obj.mobile}" >
							</td>
							<td style="padding-right: 10px;" align="left"><div id="mobile_vTip">&nbsp;</div></td>
						</tr>
						<tr height="30">
							<td align="right" width="20%">
							  �����ʼ�:
							</td>
							<td width="35%">
								<input type="text" name="obj.email" id = "email" class="input_textdmax" value="${obj.email}" onblur="myblur('email')">
							</td>
							<td style="padding-right: 10px;" align="left"><div id="email_vTip">&nbsp;</div></td>
						</tr>
						<tr height="30">
							<td align="right">
								ͨѶ��ַ:
							</td>
							<td colspan="2">
								<input type="text" name="obj.address" class="input_textdmax" value="${obj.address}">
							</td>
						</tr>
						
					</table>
				</div>
		</td>
	</tr>
	<!-- ��һ������-->
</table>
<script type="text/javascript">
if (typeof window['DWRUtil'] == 'undefined') {
	window.DWRUtil = dwr.util;
}
function myblur(userID) {
	var flag = true;
	if ("phone" == userID) {
		flag = phone(userID);
	}   else if ("mobile" == userID) {
		flag = mobile(userID);
	} else if ("email" == userID) {
		flag = emailT(userID);
	}else  if('name'==userID){
		flag=checkName(userID);
	}
	else {
		if (!phone("phone")){
			flag = false;
		}
		if (!emailT("email")){
			flag = false;
			}
		if(!mobile("mobile")){
			flag=false;
		}
		if(!checkName("name")){
			flag=false;
		}
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
		innerHTML = "�������󣬽�����������";
	} else if (user.value.length !=11) {
		innerHTML = "����Ӧ����11λ";
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


function checkName(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML ="�������������";
	} else if(user.value.length<2||user.value.length>60){
		innerHTML = "���Ȳ���С��2λ���ܳ���60λ";
	}else if(!checkNameRex(user.value)){
		innerHTML = "���в��Ϸ��ַ�";
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

function phone(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		flag = true;
	} else if (!checkphone1(user.value)) {
		innerHTML = "�������󣬽����������֡��л���";
	} else if (user.value.length > 12) {
		innerHTML = "���Ȳ��ܳ���12λ";
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
			innerHTML = "��������";
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
function checkNameRex(name){
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
	var parentOrgNo=document.getElementById('parentNo').value;
	var url="${basePath}/broke/organization/getOrganizationTree.action?parentOrgNo="+parentOrgNo+"&organizationNO="+'${obj.organizationNO}';
	var result=window.showModalDialog(url,window, "dialogWidth=500px; dialogHeight=520px; status=yes;scroll=yes;help=no;");
}
</script>


<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String oneMaxOrderQty = "";
	String oneMinOrderQty = "";
	String maxCleanQty = "";
	String maxHoldQty = "";
%>
<div class="div_cxtjd">
	<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��Ա�ֲ�����
</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<tr height="40">
		<td align="right" width="23%">
			��Ʒ����:
		</td>
		<td width="30%">
			<input type="hidden" name="obj.commodityName" id="commodityName">
			<select id="commodityId" onblur="myblur('commodityId')"
				onfocus="myfocus('commodityId')" name="obj.commodityId"
				class="select_widmid" onchange="setCommodityName();">
				<option value="">
					��ѡ��
				</option>
				<c:forEach items="${commodityList }" var="commodity">
					<option value="${commodity.id}">
						${commodity.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40">
			<div id="commodityId_vTip"></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			��Ա���ƣ�
		</td>
		<td>
			<input type="hidden" name="obj.memberName" id="memberName">
			<select id="memberNo" onblur="myblur('memberNo')"
				onfocus="myfocus('memberNo')" name="obj.firmId"
				class="select_widmid" onchange="setCommodityName();">
				<option value="">
					��ѡ��
				</option>
				<c:forEach items="${memberInfoList }" var="member">
					<option value="${member.id}" title ='${member.name }'>
						${member.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40">
			<div id="memberNo_vTip"></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			��������µ���:
		</td>
		<td>
			<input type="text" id="oneMaxOrderQty" style="text-align:right"
				onblur="myblur('oneMaxOrderQty')"
				onfocus="myfocus('oneMaxOrderQty')" class="input_text"
				name="obj.oneMaxOrderQty" value="${obj.oneMaxOrderQty}"><span>���֣�</span>
		</td>
		<td align="left" height="40">
			<div id="oneMaxOrderQty_vTip"><%=oneMaxOrderQty%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			������С�µ���:
		</td>
		<td>
			<input type="text" id="oneMinOrderQty" style="text-align:right"
				onblur="myblur('oneMinOrderQty')"
				onfocus="myfocus('oneMinOrderQty')" class="input_text"
				name="obj.oneMinOrderQty" value="${obj.oneMinOrderQty}"><span>���֣�</span>
		</td>
		<td align="left" height="40">
			<div id="oneMinOrderQty_vTip"><%=oneMinOrderQty%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			��󾻳ֲ���:
		</td>
		<td>
			<input type="text" id="maxCleanQty" onblur="myblur('maxCleanQty')" style="text-align:right"
				onfocus="myfocus('maxCleanQty')" class="input_text"
				name="obj.maxCleanQty" value="${obj.maxCleanQty}"><span>���֣�</span>
		</td>
		<td align="left" height="40">
			<div id="maxCleanQty_vTip" ><%=maxCleanQty%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			���ֲ���:
		</td>
		<td>
			<input type="text" onblur="myblur('maxHoldQty')" style="text-align:right"
				onfocus="myfocus('maxHoldQty')" id="maxHoldQty" class="input_text"
				name="obj.maxHoldQty" value="${obj.maxHoldQty}"><span>���֣�</span>
		</td>
		<td align="left" height="40">
			<div id="maxHoldQty_vTip" ><%=maxHoldQty%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			���Ʒ�ʽ:
		</td>
		<td>
			<select id="operate" name="obj.operate" class="select_widmid">
				<option value="P" selected="selected">
					���Ի�
				</option>
			</select>
		</td>
	</tr>
</table>
<script type="text/javascript">
<!--
function myblur(userID){
	var flag = true;
	if("commodityId"==userID || "memberNo"==userID){
		flag = commodityName(userID);
	}else if(userID!=null && userID.indexOf("all")<0){
		flag = usersblur(userID);
	}else{
		if(!commodityName("commodityId")) flag = false;
		if(!commodityName("memberNo")) flag = false;
		if(!usersblur("oneMaxOrderQty")) flag = false;
		if(!usersblur("oneMinOrderQty")) flag = false;
		if(!usersblur("maxCleanQty")) flag = false;
		if(!usersblur("maxHoldQty")) flag = false;
	}
	return flag;
}
function commodityName(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var flag = false;
	if(isEmpty(user.value)){
		vTip.innerHTML = "��ѡ��";
		vTip.className = "onError";
		flag = false;
	}else{
		vTip.innerHTML = "";
		vTip.className = "";
		flag = true;
	}
	var firmId = document.getElementById("memberNo").value;
	var commodityId = document.getElementById("commodityId").value;
	if(firmId && commodityId){
		checkAction.existMemberHoldQuantily(commodityId, firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("commodityId").value="";
				document.getElementById("memberNo").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAdd(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("����������"+applyView.proposer+"��"+applyView.modTimeString+"�ύ");
						document.getElementById("commodityId").value="";
						document.getElementById("memberNo").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}
function usersblur(userID){
	var str="�������1�ҡ�20000������";
	var flag = false;
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
if (isEmpty(user.value)) {
		innerHTML = "����Ϊ��";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if (user.value<1 || user.value > 20000){
				innerHTML = str;
	}else {
			if(userID=="oneMaxOrderQty"){
				innerHTML = "<%=oneMaxOrderQty%>";
				flag = true;
				vTip.className = "";
			}
			if(userID=="oneMinOrderQty"){
				innerHTML = "<%=oneMinOrderQty%>";
				flag = true;
				vTip.className = "";
				var oneMax = document.getElementById("oneMaxOrderQty");
				if (parseFloat(user.value) > parseFloat(oneMax.value)) {
					innerHTML = "������С�µ���<��������µ���";
					flag = false;
				}
			}
			if(userID=="maxCleanQty"){
				innerHTML = "<%=maxCleanQty%>";
				flag = true;
				vTip.className = "";
			}
			if(userID=="maxHoldQty"){
				innerHTML = "<%=maxHoldQty%>";
				flag = true;
				vTip.className = "";
			}
		}
		if(!flag){
		vTip.className = "onError";
		}
	vTip.innerHTML = innerHTML;
	return flag;
}
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className = "onFocus";*/
}
function setCommodityName(){
	var obj = document.getElementById('commodityId');
	var m_FirmId = document.getElementById('memberNo');
	var value = obj.options[obj.selectedIndex].text;
	var firmValue = m_FirmId.options[m_FirmId.selectedIndex].text;
	document.getElementById('commodityName').value=value;
	document.getElementById('memberName').value=firmValue;
	
}

//-->
</script>

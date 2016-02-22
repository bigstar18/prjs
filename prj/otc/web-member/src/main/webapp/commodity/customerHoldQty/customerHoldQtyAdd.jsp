<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='${basePath}/dwr/engine.js'>
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/checkCustomerNo.js' />
</script>
<%
	String oneMaxOrderQty = "";
	String oneMinOrderQty = "";
	String maxCleanQty = "";
	String maxHoldQty = "";
%>
<html>
	<head>
		<title>�ͻ���Ʒ�ֲ��������</title>
	</head>
	<body class="st_body">
		<form name="frm"
			action="${basePath}/commodity/customerHoldQty/add.action"
			method="post" targetType="hidden">
			<input type="hidden" id="memberNo" name="obj.memberNo"
				value="${REGISTERID}">
			<input type="hidden" id="commodityName" name="obj.commodityName"
				value="">
			<div>
				<div class="st_title">
					<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
					&nbsp;&nbsp;�ͻ���Ʒ�ֲ��������
				</div>
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								align="center" class="st_bor">
								<tr height="35">
									<td align="right" width="23%">
										��Ʒ����:
									</td>
									<td width="37%">
										<select name="obj.commodityId" id="commodityId" 
										onblur="myblur('commodityId')"	onfocus="myfocus('commodityId')"		
										onchange="setCommodityName();"	class="select_widmid">
											<option value="">
												��ѡ��
											</option>
											<c:forEach items="${commodityList}" var="commodity">
												<option value="${ commodity.id}">
													${commodity.name}
												</option>
											</c:forEach>
										</select>
									</td>
									<td align="left" height="40"><div id="commodityId_vTip"></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										�����˺�:
									</td>
									<td>
										<input class="input_text" type="text" id="customerNo"
										onblur="myblur('customerNo')"	onfocus="myfocus('customerNo')"
											name="obj.customerNo" value="${obj.customerNo}"
											onblur="checkCustomer()">
										<strong class="check_input">&nbsp;*</strong>
									</td>
									<td align="left" height="40"><div id="customerNo_vTip"></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										��������µ���:
									</td>
									<td>
										<input class="input_text" type="text" id="oneMaxOrderQty"
										onblur="myblur('oneMaxOrderQty')" onfocus="myfocus('oneMaxOrderQty')"
											style="text-align: right" name="obj.oneMaxOrderQty"
											value="${obj.oneMaxOrderQty}">
										<strong class="check_input">&nbsp;*</strong><span>���֣�</span>
									</td>
									<td align="left" height="40"><div id="oneMaxOrderQty_vTip"><%=oneMaxOrderQty%></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										������С�µ���:
									</td>
									<td>
										<input class="input_text" type="text" id="oneMinOrderQty"
											style="text-align: right" name="obj.oneMinOrderQty"
										onblur="myblur('oneMinOrderQty')" onfocus="myfocus('oneMinOrderQty')"
											value="${obj.oneMinOrderQty}">
										<strong class="check_input">&nbsp;*</strong><span>���֣�</span>
									</td>
									<td align="left" height="40"><div id="oneMinOrderQty_vTip"><%=oneMinOrderQty%></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										��󾻳ֲ���:
									</td>
									<td>
										<input class="input_text" type="text" id="maxCleanQty"
										onblur="myblur('maxCleanQty')" onfocus="myfocus('maxCleanQty')"
											style="text-align: right" name="obj.maxCleanQty"
											value="${obj.maxCleanQty}">
										<strong class="check_input">&nbsp;*</strong><span>���֣�</span>
									</td>
									<td align="left" height="40"><div id="maxCleanQty_vTip" ><%=maxCleanQty%></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										���ֲ���:
									</td>
									<td>
										<input class="input_text" onblur="myblur('maxHoldQty')" 
										onfocus="myfocus('maxHoldQty')" type="text" id="maxHoldQty"
											style="text-align: right" name="obj.maxHoldQty"
											value="${obj.maxHoldQty}">
										<strong class="check_input">&nbsp;*</strong><span>���֣�</span>
									</td>
									<td align="left" height="40"><div id="maxHoldQty_vTip" ><%=maxHoldQty%></div></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" id="add" onclick="addCustomerHoldQty()">
								���
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
								�ر�
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
function addCustomerHoldQty() {
	if(!myblur("all")){return false;}
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}

function myblur(userID){
	var flag = true;
	if("commodityId"==userID || "customerNo"==userID){
		flag = commodityName(userID);
	}else if(userID!=null && userID.indexOf("all")<0){
		flag = usersblur(userID);
	}else{
		if(!commodityName("commodityId")) flag = false;
		if(!commodityName("customerNo")) flag = false;
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
		vTip.innerHTML = "����Ϊ��";
		vTip.className = "onError";
		flag = false;
	}else{
		vTip.innerHTML = "";
		vTip.className = "";
		flag = true;
	}
	
	var customerNo = document.getElementById("customerNo").value;
	var memberNo = document.getElementById("memberNo").value;
	if (customerNo == "") {
		return false;
	} else {
		checkCustomerNo.checkCustomerNo(memberNo, '${ORGANIZATIONID}',
				customerNo, function(isExist) {
					if (isExist == -2) {
						alert('�ͻ�״̬���Ƕ��������,���������');
						document.getElementById("customerNo").value = "";
					} else if (isExist == -1) {
						alert('û�д˿ͻ�,���������');
						document.getElementById("customerNo").value = "";
					} else if (isExist == -3) {
						alert('û�д˿ͻ�Ȩ��,���������');
						document.getElementById("customerNo").value = "";
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
	var value = obj.options[obj.selectedIndex].text;
	document.getElementById('commodityName').value=value;
}
</script>
<%@ include file="/public/footInc.jsp"%>
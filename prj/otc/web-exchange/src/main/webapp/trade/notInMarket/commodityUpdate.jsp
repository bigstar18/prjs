<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String name = "";
	String contractFactor="";
	String minHQMove="";
	String minPriceMove="";
	String stepMove="";
	String quoteRate = "";
	String lastPrice="";
%>
<html>
	<head>
		<title>���δ������Ʒ</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm" action="${basePath}/tradeManage/notInMarket/update.action" method="post" targetType="hidden">
			<div style="overflow:auto;height:510px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;��Ʒ��Ϣ�޸�
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor">
								<tr height="20">
									<td align="right" width="20%">
										��Ʒ����:
									</td>
									<td width="45%">
										<input type="text" id="id" class="input_text" name="obj.id"
											value="${obj.id}" style="background-color: #bebebe"
											readonly="readonly">
									</td>
									<td width="35%">&nbsp;</td>
								</tr>
								<tr>
									<td align="right">
										��Ʒ����:
									</td>
									<td>
										<input type="text" id="name" class="input_text" name="obj.name"
											onblur="myblur('name')"
											onfocus="myfocus('name')"  onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
											value="${obj.name}" >
									</td>
									<td align="left" height="40">
										<div id="name_vTip" class=""><%=name%></div>
									</td>
								</tr>
								<tr>
									<td align="right">
										��������:
									</td>
									<td>
										<input type="hidden" id="marketDate"
											name="obj.marketDate" value="${datefn:formatdate(obj.marketDate)}">
										<input type="text" class="input_text" id="date"
											name="marketDate" value="${datefn:formatdate(obj.marketDate)}" disabled="disabled">
									</td>
								</tr>
								<tr height="20">
									<td align="right">
										��Ʒ״̬:
									</td>
									<td>
										<input type="hidden" name="obj.status" value="0">
										<input type="text" class="input_text" disabled="disabled"
											value="�༭��">
									</td>
								</tr>
								<tr>
									<td align="right">
										����״̬:
									</td>
									<td>
										<input type="hidden" name="obj.tradeMode" value="P">
										<input type="text" class="input_text" disabled="disabled"
											value="ֹͣ����">
									</td>
								</tr>
								<tr height="20">
									<td align="right">
										��λ����:
									</td>
									<td>1
										<select id="contractUnit" name="obj.contractUnit" style="width: 60px;">
											<c:forEach items="${contractUnitMap}" var="maps">
												<option value="${maps.key}"
													<c:if test="${obj.contractUnit==maps.key}">selected="selected"</c:if>>
													${maps.value}
												</option>
											</c:forEach>
										</select>&nbsp;=
										<input type="text" class="input_text" id="quoteRate"
											style="text-align:right;"	onblur="myblur('quoteRate')"
											onfocus="myfocus('quoteRate')" name="obj.quoteRate"
											value="${obj.quoteRate}">&nbsp;��˾
									</td>
									<td align="left" height="40">
										<div id="quoteRate_vTip" ><%=quoteRate%></div>
									</td>
								</tr>
								<tr height="20">
									<td align="right">
										��Լ��λ:
									</td>
									<td>
										<input type="text" class="input_text" id="contractFactor"
											style="text-align:right" onblur="myblur('contractFactor')"
											onfocus="myfocus('contractFactor')" name="obj.contractFactor"
											value="${obj.contractFactor}">
									</td>
									<td align="left" height="40">
										<div id="contractFactor_vTip" ><%=contractFactor%></div>
									</td>
								</tr>
								<%--
								<tr>
									<td align="right">
										��С���鵥λ:
									</td>
									<td>
										<input type="text" class="input_text" id="minHQMove"
												style="text-align:right" onblur="myblur('minHQMove')" onfocus="myfocus('minHQMove')"
											name="obj.minHQMove" value="${obj.minHQMove}">
									</td>
									<td align="left" height="40">
										<div id="minHQMove_vTip" ><%=minHQMove%></div>
									</td>
								</tr>
								 --%>
								<tr height="20">
									<td align="right">
										��С�䶯��λ:
									</td>
									<td>
										<select name="obj.minPriceMove" id="minPriceMove"  class="input_text" >
											<option value="0.01" <c:if test="${obj.minPriceMove=='0.01'}">selected="selected"</c:if>>0.01</option>
											<option value="1.00" <c:if test="${obj.minPriceMove=='1.00'}">selected="selected"</c:if>>1.00</option>
										</select>
									</td>
									<td align="left" height="40">
										<div id="minPriceMove_vTip" ><%=minPriceMove%></div>
									</td>
								</tr>
								<%--
								<tr>
									<td align="right">
										����ֵ:
									</td>
									<td>
										<input type="text" class="input_text" id="stepMove"
											 style="text-align:right" onblur="myblur('stepMove')" onfocus="myfocus('stepMove')"
											name="obj.stepMove" value="${obj.stepMove}">
									</td>
									<td align="left" height="40">
										<div id="stepMove_vTip" ><%=stepMove%></div>
									</td>
								</tr>
								 --%>
								<tr>
									<td align="right">
										��׼��:
									</td>
									<td>
										<input type="text" class="input_text" id="lastPrice"
											style="text-align: right" onblur="myblur('lastPrice')"
											onfocus="myfocus('lastPrice')" name="obj.lastPrice"
											value="${obj.lastPrice}">
									</td>
									<td align="left" height="40">
										<div id="lastPrice_vTip"><%=lastPrice%></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button id="updateComm" class="btn_sec"
								onclick="updateCommodity()">
								�޸�
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
function updateCommodity() {
	var flag = true;
	flag = myblur("all");
	if (flag) {
		if(!isFormChanged(null,null)){
			alert("û���޸�����");
			return false;}
		var vaild = affirm("��ȷ��Ҫ������");
		if (vaild == true) {
			frm.submit();
		} else {
			return false;
		}
	}
}

function myblur(userID) {
	var flag = true;
	if ("contractFactor" == userID) {
		flag = contractFactor(userID);
	} else if ("minPriceMove" == userID) {
		flag = minPriceMove(userID);
	} else if("name" == userID){
		flag = nameA(userID);
	}else if("quoteRate"==userID){
		flag = quoteRate(userID);
	}else if("lastPrice"==userID){
		flag = lastPrice(userID);
	}else {
		if (!contractFactor("contractFactor"))
			flag = false;
		if (!minPriceMove("minPriceMove"))
			flag = false;
		if (!nameA("name"))
			flag = false;
		if(!quoteRate("quoteRate")) flag = false;
		if(!lastPrice("lastPrice")) flag = false;
	}
	return flag;
}
function nameA(userID){
	var flag = false;
	var str = "";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML = str + "����Ϊ��";
	} else if(user.value.length>16){
		innerHTML = str + "���ܳ���16���ַ�";
	} else {
		innerHTML = "<%=name%>";
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	var id = document.getElementById("id").value;
	var name = document.getElementById("name").value;
	if(name){
		checkAction.existCommodityNameSecond(id,name, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("name").value="";
				document.getElementById("name").focus();
			}
		});
	}
	return flag;
}
//���㵥λ
function quoteRate(userID){
	var flag = false;
	var str = "������>0�ҡ�100000������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="����Ϊ��";
	}else if (user.value <= 0 || user.value > 100000) {
		innerHTML = str;
	}else if(!flote(user.value,6)){
		innerHTML = "���6λС��������";
	}else{
		innerHTML = '<%=quoteRate%>';
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if(flag){
		vTip.className = "";
	}else{
		vTip.className = "onError";
	}
	return flag;
}
//��Լ��λ
function contractFactor(userID) {
	var flag = false;
	var str = "������>0�ҡ�100000������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML ="����Ϊ��";
	} else if (user.value <= 0 || user.value > 100000) {
		innerHTML = str;
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else {
		innerHTML = "<%=contractFactor%>";
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
//��С���鵥λ
function minHQMove(userID) {
	var flag = false;
	var str = "������>0�ҡ�100������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML ="����Ϊ��";
	} else if (!flote(user.value, 2)) {
		innerHTML = "���2λС��������";
	} else if (user.value <= 0 || user.value > 100) {
		innerHTML = str;
	} else {
		innerHTML = "<%=minHQMove%>";
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
//��С�䶯��λ
function minPriceMove(userID) {
	var flag = false;
	var str = "������>0�ҡ�100������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML =  "����Ϊ��";
	} else if (!flote(user.value, 2)) {
		innerHTML =  "���2λС��������";
	} else if (user.value <= 0 || user.value > 1000) {
		innerHTML = str;
	} else {
		innerHTML = "<%=minPriceMove%>";
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
//����ֵ
function stepMove(userID) {
	var flag = false;
	var str = "������>0�ҡ�100������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML ="����Ϊ��";
	} else if (!flote(user.value,2)) {
		innerHTML = "���2λС��������";
	} else if (user.value <= 0 || user.value > 100) {
		innerHTML = str;
	} else {
		innerHTML =  "<%=stepMove%>";
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
//ָ����
function lastPrice(userID) {
	var flag = false;
	var str = "������>0�ҡ�100000������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML = "����Ϊ��";
	} else if (!flote(user.value, 2)) {
		innerHTML = "���2λС��������";
	} else if (user.value <= 0 || user.value>100000) {
		innerHTML = str;
	} else {
		innerHTML = "<%=lastPrice%>";
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
	/*var vTip = document.getElementById(userID + "_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className = "onFocus";*/
}
</script>

<%@ include file="/public/footInc.jsp"%>


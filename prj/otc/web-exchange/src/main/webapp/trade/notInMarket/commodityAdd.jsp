<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String id = "";
	String myName = "";
	String contractFactor = "";
	String minHQMove = "";
	String minPriceMove = "";
	String stepMove = "";
	String quoteRate = "";
	String lastPrice="";
%>
<html>
	<head>
		<title>���δ������Ʒ</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm"
			action="${basePath}/tradeManage/notInMarket/add.action" method="post"
			targetType="hidden">
			<input type="hidden" name="obj.displayNum" value="0" />
			<input type="hidden" name="obj.delayFeeAlgr" value="1" />
			<div style="overflow:auto;height:500px;"> 
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;�����Ʒ
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor">
								<tr height="20" width="50%">
									<td width="20%" align="right">
										��Ʒ����:
									</td>
									<td width="45%" align="left">
										<input type="text" id="id" class="input_text" name="obj.id"
											onblur="myblur('id')" onfocus="myfocus('id')" onkeyup="value=value.replace(/[\W]/g,'')"
											
											value="${obj.id}">
									</td>
									<td width="35%">
										<div id="id_vTip"><%=id%></div>
									</td>
								</tr>
								<tr>
									<td align="right">
										��Ʒ����:
									</td>
									<td>
										<input type="text" id="myName" class="input_text"
											onblur="myblur('myName')" onfocus="myfocus('myName')" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
											name="obj.name" value="${obj.name}">
									</td>
									<td>
										<div id="myName_vTip"><%=myName%></div>
									</td>
								</tr>
								<tr>
									<td align="right">
										��������:
									</td>
									<td>
										<input type="text" style="width: 120px" id="date" readonly="readonly"
											class="wdate" maxlength="10" name="obj.marketDate"
											onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
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
										<input type="text" class="input_text" disabled="disabled"	value="ֹͣ����">
									</td>
								</tr>
								<tr height="20">
								<tr height="20">
									<td align="right">
										��λ����:
									</td>
									<td>
										1
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
											style="text-align: right" onblur="myblur('contractFactor')"
											onfocus="myfocus('contractFactor')" name="obj.contractFactor"
											value="${obj.contractFactor}">
									</td>
									<td align="left" height="40">
										<div id="contractFactor_vTip"><%=contractFactor%></div>
									</td>
								</tr>
								<%--
								<tr>
									<td align="right">
										��С���鵥λ:
									</td>
									<td>
										<input type="text" class="input_text" id="minHQMove"
											style="text-align: right" onblur="myblur('minHQMove')"
											onfocus="myfocus('minHQMove')" name="obj.minHQMove"
											value="${obj.minHQMove}">
									</td>
									<td align="left" height="40">
										<div id="minHQMove_vTip"><%=minHQMove%></div>
									</td>
								</tr>
								 --%>
								<tr height="20">
									<td align="right">
										��С�䶯��λ:
									</td>
									<td>
										<select name="obj.minPriceMove" id="minPriceMove"  class="input_text" >
											<option value="0.01">0.01</option>
											<option value="1.00">1.00</option>
										</select>
									</td>
									<td align="left" height="40">
										<div id="minPriceMove_vTip"><%=minPriceMove%></div>
									</td>
								</tr>
								<%--
								<tr>
									<td align="right">
										����ֵ:
									</td>
									<td>
										<input type="text" class="input_text" id="stepMove"
											style="text-align: right" onblur="myblur('stepMove')"
											onfocus="myfocus('stepMove')" name="obj.stepMove"
											value="${obj.stepMove}">
									</td>
									<td align="left" height="40">
										<div id="stepMove_vTip"><%=stepMove%></div>
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

function updateCommodity() {
	var flag = true;
	flag = myblur("all");
	var now = new Date();
	var s = new Date(Date.parse(frm.date.value.replace(/-/g, "/")));
	if (frm.date.value == "") {
		alert("�������ڲ���Ϊ��");
		return false;
	}
	if (s != "" && s < now) {
		alert("�������ڲ���С�ڵ��ڵ�ǰ����");
		return false;
	}
	if (flag) {
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
	} else if ("id" == userID) {
		flag = id(userID);
	} else if ("myName" == userID) {
		flag = myName(userID);
	}else if("quoteRate"==userID){
		flag = quoteRate(userID);
	}else if("lastPrice"==userID){
		flag = lastPrice(userID);
	}else {
		if (!contractFactor("contractFactor"))
			flag = false;		
		if (!minPriceMove("minPriceMove"))
			flag = false;		
		if (!id("id"))
			flag = false;
		if (!myName("myName"))
			flag = false;
		if(!quoteRate("quoteRate")) flag = false;
		if(!lastPrice("lastPrice")) flag = false;
	}
	return flag;
}
function myName(userID) {
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
		innerHTML = "<%=myName%>";
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	var name = document.getElementById("myName").value;
	if (name) {
		checkAction.existCommodityName(name, function(isExist) {
			if (isExist) {
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("myName").value = "";
				document.getElementById("myName").focus();
			}
		});
	}
	return flag;
}
function id(userID) {
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
		innerHTML = "<%=id%>";
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	var id = document.getElementById("id").value;
	if (id) {
		checkAction.existCommodityId(id, function(isExist) {
			if (isExist) {
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("id").value = "";
				document.getElementById("id").focus();
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
		innerHTML = "����Ϊ��";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if (user.value <= 0 || user.value > 100000) {
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
		innerHTML = "����Ϊ��";
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
		//innerHTML = "����Ϊ��";
	} else if (!flote(user.value, 2)) {
		//innerHTML = "���2λС��������";
	} else if (user.value < 0 || user.value > 1000) {
		//innerHTML = str;
	} else {
		//innerHTML = "<%=minPriceMove%>";
		flag = true;
	}
	//vTip.innerHTML = innerHTML;
	if (flag) {
		//vTip.className = "";
	} else {
		//vTip.className = "onError";
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
		innerHTML = "����Ϊ��";
	} else if (!flote(user.value, 2)) {
		innerHTML = "���2λС��������";
	} else if (user.value <= 0 || user.value > 100) {
		innerHTML = str;
	} else {
		innerHTML = "<%=stepMove%>";
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
	vTip.className = "";*/
}
</script>

<%@ include file="/public/footInc.jsp"%>


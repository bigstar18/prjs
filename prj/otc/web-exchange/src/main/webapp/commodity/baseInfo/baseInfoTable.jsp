<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String contractFactor="";
	String minHQMove="";
	String minPriceMove="";
	String stepMove="";
	String name="";
	String tradeMode="�ڶ�����Ч";
	String quoteRate = "";
%>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��Ʒ��Ϣ
			</div>
			<div class="div_tj">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr height="20">
						<td align="right" width="20%">
							��Ʒ����:
						</td>
						<td width="30%">
							<input type="text" id="id" class="input_text_pwdmin" name="obj.id"
								value="${obj.id}"
								readonly="readonly">
						</td>
						<td>
							<div>
								&nbsp;
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							��Ʒ����:
						</td>
						<td>
							<input type="text" id="name" class="input_text" name="obj.name"
								onblur="myblur('name')"
								onfocus="myfocus('name')" 
								value="${obj.name}" >
						</td>
						<td align="left" height="40">
							<div id="name_vTip" class=""><%=name%></div>
						</td>
					</tr>
					<tr height="30">
						<td align="right">
							��������:
						</td>
						<td>
							<input type="hidden" id="marketDate"
								name="obj.marketDate" value="${datefn:formatdate(obj.marketDate)}">
							<input type="text" class="input_text" id="date"
								name="obj.marketDate" value="${datefn:formatdate(obj.marketDate)}" disabled="disabled">
						</td>
					</tr>
					<tr height="40">
						<td align="right">
							��Ʒ״̬:
						</td>
						<td>
							<input type="hidden" name="obj.status" value="${obj.status}">
							<select id="status" name="obj.status2" class="select_widmid"
								disabled="disabled">
								<c:forEach items="${commodityStatusMap}" var="maps">
									<option value="${maps.key}"
										<c:if test="${obj.status==maps.key}">selected="selected"</c:if>>
										${maps.value}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>		
					<tr>
						<td align="right">
							����״̬:
						</td>
						<td>
							<select id="tradeMode" name="obj.tradeMode" class="select_widmid">
								<c:if test="${obj.status==1}">
									<c:forEach items="${commodityTradeModeMap}" var="maps">
										<option value="${maps.key}"
											<c:if test="${obj.tradeMode==maps.key}">selected="selected"</c:if>>
											${maps.value}
										</option>
									</c:forEach>
								</c:if>
								<c:if test="${obj.status!=1}">
									<option value="P">
										ֹͣ����
									</option>
								</c:if>
							</select>
						</td>
						<td align="left" height="40">
							<div id="tradeMode_vTip" class="onfocus" ><%=tradeMode%></div>
						</td>
					</tr>
					<tr height="20">
						<td align="right">
							��λ����:
						</td>
						<td>
							<input type="text" class="input_text" id="quoteRate"
							style="text-align:right;"	onblur="myblur('quoteRate')"
								onfocus="myfocus('quoteRate')" name="obj.quoteRate"
								value="${obj.quoteRate}">
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
							style="text-align:right;"	onblur="myblur('contractFactor')"
								onfocus="myfocus('contractFactor')" name="obj.contractFactor"
								value="${obj.contractFactor}"><select id="contractUnit" name="obj.contractUnit" style="width: 60px;">
								<c:forEach items="${contractUnitMap}" var="maps">
									<option value="${maps.key}"
										<c:if test="${obj.contractUnit==maps.key}">selected="selected"</c:if>>
										${maps.value}
									</option>
								</c:forEach>
							</select>
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
								style="text-align:right;" onblur="myblur('minHQMove')" onfocus="myfocus('minHQMove')"
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
							<input type="text" class="input_text" id="minPriceMove"
								style="text-align:right;" onblur="myblur('minPriceMove')"
								onfocus="myfocus('minPriceMove')" name="obj.minPriceMove"
								value="${obj.minPriceMove}">
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
							style="text-align:right;" 	onblur="myblur('stepMove')" onfocus="myfocus('stepMove')"
								name="obj.stepMove" value="${obj.stepMove}">
						</td>
						<td align="left" height="40">
							<div id="stepMove_vTip" ><%=stepMove%></div>
						</td>
					</tr>
					 --%>
					<c:if test="${obj.tradeMode=='P'&&obj.pauseType=='M'}">
						<tr>
						<td align="right">
							��ͣ����:
						</td>
						<td>
							<select id="pauseType" name="obj.pauseType" class="select_widmid" disabled="disabled">
								<c:forEach items="${pauseTypeMap}" var="maps">
									<option value="${maps.key}"
										<c:if test="${obj.pauseType==maps.key}">selected="selected"</c:if>>
										${maps.value}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					</c:if>
				</table>
			</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
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
	}else {
		if (!contractFactor("contractFactor"))
			flag = false;
		if (!minPriceMove("minPriceMove"))
			flag = false;
		if (!nameA("name"))
			flag = false;
		if(!quoteRate("quoteRate")) flag = false;
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
	}  else {
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
	var str = "�������0������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="����Ϊ��";
	}else if(user.value<0){
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
	var str = "������>0������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML ="����Ϊ��";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if (user.value < 0) {
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
	} else if (user.value < 0 || user.value > 100) {
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
	} else if (user.value < 0 || user.value > 1000) {
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
	} else if (!flote(user.value, 2)) {
		innerHTML = "���2λС��������";
	} else if (user.value < 0 || user.value > 100) {
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



function myfocus(userID) {
	/*var vTip = document.getElementById(userID + "_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className = "";*/
}

function spreadChange(fee) {
	if (fee == '1') {
		document.getElementById("spreadUpLmt_span").innerHTML = "%";
		document.getElementById("spreadDownLmt_span").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("spreadUpLmt_span").innerHTML = "";
		document.getElementById("spreadDownLmt_span").innerHTML = "";
	}
	if (fee == '4') {
		document.getElementById("spreadUpLmt_span").innerHTML = "";
		document.getElementById("spreadDownLmt_span").innerHTML = "";
	}
}
</script>

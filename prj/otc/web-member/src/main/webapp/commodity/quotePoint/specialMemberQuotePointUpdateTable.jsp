<%@ page contentType="text/html;charset=GBK"%>
<div class="div_cxtjmid">
	<img src="<%=skinPath%>/cssimg/13.gif" />
	�ر��Ա���۵��
</div>
<%
		String quotePointB="";
		String quotePoint_S="";
	
	%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<input type="hidden" name="obj.commodityId" value="${obj.commodityId}">
	<tr height="40">
		<td align="right" width="23%">
			��Ʒ����:
		</td>
		<td width="30%">
			<input type="text" id="name" class="input_text_pwdmin"
				name="obj.commodityName" value="${obj.commodityName}"
				readonly="readonly">
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			�ر��Ա����:
		</td>
		<td>
			<input type="hidden" class="input_text_pwdmin" id="m_firmId"
				name="obj.m_firmId" value="${obj.m_firmId}"
				readonly="readonly">
			<input type="text" class="input_text_pwdmin" id="firmName"
				name="obj.firmName" value="${obj.firmName}"
				readonly="readonly">
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			���۵���㷨:
		</td>
		<td>
			<select id="delayFeeAlgr" name="obj.quotePointAlgr_v"
				onchange="quotePointAlgrChange(this.value);" class="select_widmid">
				<c:forEach items="${delayFeeAlgrMap }" var="maps">
					<option value="${maps.key}"<c:if test="${maps.key==obj.quotePointAlgr_v}">selected=selected</c:if> >
						${maps.value }
					</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<div id="delayFeeAlgr_vTip" >
				
			</div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			�򱨼۵��:
		</td>
		<td>
			<input type="text" class="input_text" id="quotePointB" style="text-align:right"
				onblur="myblur('quotePointB')" onfocus="myfocus('quotePointB')"
				name="obj.quotePointB_v" value="${obj.quotePointB_v}">
			<span id="quotePointB_span"></span>
		</td>
		<td align="left" height="40">
			<div id="quotePointB_vTip">	<%=quotePointB %></div>
		</td>
	</tr>
	<!-- <tr height="40">
		<td align="right">
			�����۵��:
		</td>
		<td>
		 -->	<input type="hidden" class="input_text" id="quotePoint_S" style="text-align:right"
				name="obj.quotePointS_v" value="${obj.quotePointS_v}">
		<!-- 	<span id="quotePointS_span"></span>
		</td>
		<td align="left" height="40">
			<div id="quotePoint_S_vTip" class="onFocus">
				<%=quotePoint_S %>
			</div>
		</td>
	</tr> -->
	<tr height="40">
		<td align="right">
			���Ʒ�ʽ:
		</td>
		<td>
			<select id="operate" name="obj.operate" class="select_widmid">
				<option value="P">
					���Ի�
				</option>
				<option value="D">
					Ĭ��
				</option>
			</select>
			<script type="text/javascript">
				document.getElementById('operate').value = '${obj.operate}';
			</script>
		</td>
		</td>
	</tr>
</table>
<script type="text/javascript">

function myblur(userID){
	var flag = true;
	 if("quotePointB"==userID){
		flag = quotePointB(userID);
	}
	 //else if("quotePoint_S"==userID){
		//flag = quotePoint_S(userID);
	//}
	 else{
		if(!quotePointB("quotePointB")) flag = false;
		//if(!quotePoint_S("quotePoint_S")) flag = false;
	}
	return flag;
}
function quotePointB(userID){
	var user = document.getElementById(userID);
	var feeAlgr = document.getElementById("delayFeeAlgr").value;
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var str = "�������0�ҡ�200����";
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else if (feeAlgr == 1) {//�ٷֱ�
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value < 0 || user.value > 200) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value < 0 || user.value > 200) {
			innerHTML = str;
		} else {
			innerHTML = "";
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
function quotePoint_S(userID){
	var user = document.getElementById(userID);
	var delayFeeAlgr = document.getElementById("delayFeeAlgr").value;
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "����Ϊ��";
	}else if(delayFeeAlgr==1){//�ٷֱ�
		if(!flote(user.value,2)){
			innerHTML = "�����������λС��������";
		}else if(user.value<0 || user.value>100){
			innerHTML = "Ӧ��0��100֮��";
		}else{
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	}else{//����ֵ
		if(!flote(user.value,4)){
			innerHTML = "�����������λС��������";
		}else if(user.value<0 || user.value>100000){
			innerHTML = "Ӧ��0-100000֮��";
		}else{
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	}
	if(flag){
		vTip.className="onFocus";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	return flag;
}
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	if("delayFeeAlgr"==userID){
		innerHTML = "��ѡ��";
	}
	if("quotePointB"==userID){
		innerHTML = "����Ϊ��";
	}
	if("quotePoint_S"==userID){
		innerHTML = "����Ϊ��";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
function quotePointAlgrChange(quotePointAlgr) {
	if (quotePointAlgr == '1') {
		document.getElementById("quotePointB_span").innerHTML = "%";
	//	document.getElementById("quotePointS_span").innerHTML = "%";
	}
	if (quotePointAlgr == '2') {
		document.getElementById("quotePointB_span").innerHTML = "";
	//	document.getElementById("quotePointS_span").innerHTML = "";
	}
}</script>
<script type="text/javascript">
quotePointAlgrChange('${obj.quotePointAlgr_v}');
</script>
<%@ page contentType="text/html;charset=GBK"%>
<%
	String delayFee="";
%>

<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="40">
					<div class="div_cxtj"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;Ĭ�����ڷ�</div>
						<td align="right" width="31%">
							��Ʒ����:
						</td>
						<td width="30%">
						    <input type="hidden" name="obj.commodityId" value="${tcDelayFeeMap.commodityId}">
							<input class="input_text_pwdmin" type="text" id="commodityId"
								name="obj.commodityName" value="${tcDelayFeeMap.commodityName }"
								readonly="readonly">
						</td>
					</tr>
					<tr height="40">
						<td align="right">
							����:
						</td>
						<td >
							<input type="hidden" name="obj.firmId" value="${tcDelayFeeMap.firmId }">
							<c:set var="memberName">
								<c:out value="${tcDelayFeeMap.firmId}"></c:out>
							</c:set>
							<input class="input_text_pwdmin" type="text" id="memberName"
								name="obj.firmName" value="${firmDisMap_v[memberName]}"
								readonly="readonly">
						</td>
						
					</tr>
					<tr height="40">
							<td align="right">
								���ڷ��㷨:
							</td>
							<td>
								<select id="feeAlgr" name="obj.feeAlgr_v"
									onchange="feeAlgrChange(true);" class="select_widmid">
									<c:forEach items="${commodityFeeAlgrMap}" var="maps">
										<option value="${maps.key}"
											<c:if test="${maps.key==tcDelayFeeMap.feeAlgr_v}">selected=selected</c:if>>
											${maps.value}
										</option>
									</c:forEach>
								</select>
							</td>

						</tr>
					<c:forEach var="ta" begin="1" end="${total}" step="1">
						<c:set var="step" value="stepNO${ta}"></c:set>
						<c:set var="mkt_step" value="mkt_stepNO${ta}"></c:set>
						<tr height="40">
							<td align="right">
							<c:forEach var="fundder" items="${ladderList}">
								<c:if test="${fundder.stepNo==ta}">
									<c:set var="note" value="${fundder.note}"></c:set>
								</c:if>
							</c:forEach>
							${note}�����ڷ���:
							</td>
							<td>
								<input class="input_text" onblur="myblur('delayFee${ta}')"  onfocus="myfocus('delayFee${ta}')" style="text-align:right"
								 type="text" id="delayFee${ta}" id=""
									name="specialforAudit.delayFee_v" value="${tcDelayFeeMap[step]}">
									<span id="feeRate${ta}_span">%</span><font color="red">&nbsp;*</font>
							</td>
							<td align="left" height="40"><div id="delayFee${ta}_vTip" ><%=delayFee%></div></td>
						</tr>
						<tr height="40">
							<td align="right">
								<c:forEach var="fundder" items="${ladderList}">
									<c:if test="${fundder.stepNo==ta}">
										<c:set var="note" value="${fundder.note}"></c:set>
									</c:if>
								</c:forEach>
								${note}�Ľ�������ȡ���ڷ�:
							</td>
							<td>
								<input class="input_text" onblur="myblur('mkt_delayFeeRate${ta}')"
									onfocus="myfocus('mkt_delayFeeRate${ta}')" style="text-align: right"
									type="text" id="mkt_delayFeeRate${ta}" name="specialforAudit.mkt_delayFeeRate_v"
									value="${tcDelayFeeMap[mkt_step]}">
								<span id="mkt_feeRate${ta}_span">%</span><font color="red">&nbsp;*</font>
							</td>
							<td align="left" height="40" width="30%">
								<div id="mkt_delayFeeRate${ta}_vTip"><%=delayFee%></div>
							</td>
						</tr>
					</c:forEach>
				</table>
				
<script type="text/javascript">

function myblur(userID){
	var number = '${total}';
	var flag = true;
	if(userID != null && userID.indexOf('delayFee')>=0){
		flag = delayFee(userID);
	}else{
		for(var i=1;i<=number;i++){
			if(!delayFee("delayFee"+i)){
				flag = false;
			}
		}
	}
	return flag;
}
function delayFee(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var str = "�������0�ҡ�100������";
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "����Ϊ��";
	}else if(isNaN(user.value)){
			innerHTML =str;
	}else if(!flote(user.value,2)){
		innerHTML = "���2λС���ķǸ���";
	}else if(user.value<0 || user.value>100){
		innerHTML =str;
	}else{
		innerHTML = "<%=delayFee%>";
		flag = true;
	}
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	return flag;
}
function feeAlgrChange(flag) {
	var fee = frm.feeAlgr.value;
	var number='${total}';
	if (fee == '1') {
		for(var i=1;i<=number;i++){
			document.getElementById("feeRate"+i+"_span").innerHTML = "%";
			document.getElementById("mkt_feeRate"+i+"_span").innerHTML = "%";
		}
	}
	if (fee == '2') {
		for(var i=1;i<=number;i++){
			document.getElementById("feeRate"+i+"_span").innerHTML = "";
			document.getElementById("mkt_feeRate"+i+"_span").innerHTML = "";
		}
	}
	if(flag){
		for ( var i = 1; i <= number; i++) {
			if (!delayFee("delayFee" + i)) {
				flag = false;
			}
		}
	}
}
feeAlgrChange(false);
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className="onFocus";*/
}

</script>
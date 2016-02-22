<%@ page contentType="text/html;charset=GBK"%>
<%
	String delayFee="";
%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					 <div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;特别会员延期费</div>
					<tr height="40">
						<td align="right" width="35%">
							商品名称:
						</td>
						<td  width="30%">
						    <input type="hidden" name="obj.commodityId" value="${tcDelayFeeMap.commodityId}">
							<input class="input_text_pwdmin" type="text" id="commodityId"
								name="obj.commodityName" value="${tcDelayFeeMap.commodityName }"
								readonly="readonly">
						</td>
					</tr>
					<tr height="40">
						<td align="right">
							特别会员名称:
						</td>
						<td><input type="hidden" name="obj.firmId" value="${tcDelayFeeMap.firmId}">
							<input class="input_text_pwdmin" type="text" id="firmName"
								name="obj.firmName" value="${tcDelayFeeMap.firmName}"
								readonly="readonly">
						</td>
						
					</tr>
					
					<tr height="40">
						<td align="right">
							延期费费算法:
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
							${note}的延期费率:
							</td>
							<td>
								<input class="input_text" onblur="myblur('delayFee${ta}')"  onfocus="myfocus('delayFee${ta}')"
								style="text-align:right" type="text" id="delayFee${ta}"
									name="specialforAudit.delayFee_v" value="${tcDelayFeeMap[step]}">
								<span id="feeRate${ta}_span">%</span> <font color="red">*</font>
							</td>
								<td align="left" height="40"><div id="delayFee${ta}_vTip"><%=delayFee%></div></td>
						</tr>
						<tr height="40">
							<td align="right">
								<c:forEach var="fundder" items="${ladderList}">
									<c:if test="${fundder.stepNo==ta}">
										<c:set var="note" value="${fundder.note}"></c:set>
									</c:if>
								</c:forEach>
								${note}的交易所收取延期费:
							</td>
							<td>
								<input class="input_text" onblur="myblur('mkt_delayFeeRate${ta}')" onfocus="myfocus('mkt_delayFeeRate${ta}')"
								 style="text-align: right" type="text" id="mkt_delayFeeRate${ta}"
								  name="specialforAudit.mkt_delayFeeRate_v" value="${tcDelayFeeMap[mkt_step]}">
								<span id="mkt_feeRate${ta}_span">%</span> <font color="red">*</font>
							</td>
							<td align="left" height="40" width="30%">
								<div id="mkt_delayFeeRate${ta}_vTip"><%=delayFee%></div>
							</td>
						</tr>
					</c:forEach>
					<tr height="40">
						<td align="right">
							控制方式:
						</td>
						<td align="left" colspan="2">
							<select id="operate" name="obj.operate" class="select_widmid">
								<option value="P">
									个性化
								</option>
								<option value="D">
									默认
								</option>
							</select>
							<script type="text/javascript">
								document.getElementById('operate').value = '${tcDelayFeeMap.operate}';
							</script>
						</td>
	               </tr>
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

function delayFee(userID) {
	var userID2="";
	if(userID.indexOf('mkt_delayFeeRate') >= 0){
		userID2=userID;
		userID=userID.replace("mkt_delayFeeRate","delayFee");
	}else{
		userID2=userID.replace("delayFee","mkt_delayFeeRate");
	}
	var feeAlgr = document.getElementById("feeAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var user2 = document.getElementById(userID2);
	var vTip2 = document.getElementById(userID2 + "_vTip");
	var innerHTML = "";
	var str = "请输入≥0且≤100的正数";
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "不能为空";
	}else if (feeAlgr == 1) {//百分比
		if(isNaN(user.value)){
			innerHTML =str;
		}else if(!flote(user.value,2)){
			innerHTML = "最多2位小数的非负数";
		}else if(user.value<0 || user.value>100){
			innerHTML =str;
		}else{
			innerHTML = "<%=delayFee%>";
			flag = true;
		}
	} else {//绝对值
		if (!flote(user.value, 2)) {
			innerHTML = "最多2位小数的非负数";
		} else if (user.value < 0) {
			innerHTML = "请输入≥0的数";
		} else {
			innerHTML = "";
			flag = true;
		}
	}
	
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	
	var innerHTML2="";
	var flag2 = false;
	
	if(user.value*1>=user2.value*1){
		if(isEmpty(user2.value)){
			innerHTML2 = "不能为空";
		}else if (feeAlgr == 1) {//百分比
			if(isNaN(user2.value)){
				innerHTML =str;
			}else if(!flote(user2.value,2)){
				innerHTML2 = "最多2位小数的非负数";
			}else if(user2.value<0 || user2.value>100){
				innerHTML2 =str;
			}else{
				innerHTML2 = "<%=delayFee%>";
				flag2 = true;
			}
		} else {//绝对值
			if (!flote(user2.value, 2)) {
				innerHTML2 = "最多2位小数的非负数";
			} else if (user2.value < 0) {
				innerHTML2 = "请输入≥0的数";
			} else {
				innerHTML2 = "";
				flag2 = true;
			}
		}
	}else{
		flag2 = false;
		innerHTML2 = "交易所收取延期费应小于等于延期费";
	}
	
	if(flag2){
		vTip2.className="";
	}else{
		vTip2.className="onError";
	}
	vTip2.innerHTML=innerHTML2;
	
	if(flag){
		flag=flag2;
	}
	
	return flag;
}

function myfocus(userID){
/*	var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "不能为空";
	vTip.className="onFocus";*/
}

function feeAlgrChange(flag) {
	var fee = myForm.feeAlgr.value;
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
</script>

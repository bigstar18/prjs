<%@ page contentType="text/html;charset=GBK"%>
<%
	String delayFee="";
%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<div class="div_cxtj"><img src="<%=skinPath%>/cssimg/13.gif" />会员延期费</div>
			<input type="hidden" name="obj.commodityId"
					value="${tcDelayFeeMap.commodityId}">
					<tr height="40">
						<td align="right" width="30%">
							商品名称:
						</td>
						<td width="30%">
							<input class="input_text_pwdmin" type="text" id="commodityId"
								name="obj.commodityName" value="${tcDelayFeeMap.commodityName }"
								readonly="readonly">
						</td>
					</tr>
					<tr height="40">
						<td align="right">
							会员名称:
						</td>
						<td >
							<input type="hidden" name="obj.firmId"
								value="${tcDelayFeeMap.firmId}">
							<input class="input_text_pwdmin" type="text" id="firmName"
								name="obj.firmName" value="${tcDelayFeeMap.firmName}"
								readonly="readonly">
						</td>
					</tr>
					<c:forEach var="ta" begin="1" end="${total}" step="1">
						<c:set var="step" value="stepNO${ta}"></c:set>
						<tr height="40">
							<td align="right">
								<c:forEach var="fundder" items="${ladderList}">
								<c:if test="${fundder.stepNo==ta}">
									<c:set var="note" value="${fundder.note}"></c:set>
								</c:if>
							</c:forEach>
							${note}的延期费率:
							<td>
							<input class="input_text" onblur="myblur('delayFee${ta}')"  onfocus="myfocus('delayFee${ta}')" 
							style="text-align:right"type="text" id="delayFee${ta}"
									name="specialforAudit.delayFee_v" value="${tcDelayFeeMap[step]}">%<font color="red">*</font>
							</td>
							<td align="left" height="40" width="30%"><div id="delayFee${ta}_vTip" ><%=delayFee%></div></td>

						</tr>
					</c:forEach>
					<tr height="40">
						<td align="right">
							控制方式:
						</td>
						<td align="left" colspan="2">
							<select id="operate" name="obj.operate" class="select_widmid">
								<option value="P" >
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
function delayFee(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var str = "请输入≥0且≤100的正数";
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "不能为空";
	}else if(isNaN(user.value)){
			innerHTML =str;
	}else if(!flote(user.value,2)){
		innerHTML = "最多2位小数的非负数";
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
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "不能为空";
	vTip.className="onFocus";*/
}
</script>

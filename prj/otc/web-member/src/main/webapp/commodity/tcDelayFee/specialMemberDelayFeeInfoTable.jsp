<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String delayFee="";
%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;特别会员延期费</div>
					<tr height="40">
						<td align="right" width="30%">
							商品名称:
						</td>
						<td width="30%">
						<input type="hidden" name="obj.commodityName" id="commodityName">
						<select id="commodityId" name="obj.commodityId" onblur="myblur('commodityId')"  onfocus="myfocus('commodityId')" onchange="setName()">
											<option value="">请选择</option>
											<c:forEach items="${commodityList }" var="commodity">
												<option value="${commodity.id}">
													${commodity.name }
												</option>
											</c:forEach>
										</select>
						</td>
					<td align="left" height="40"><div id="commodityId_vTip"></div></td>
					</tr>
				
					<tr height="40">
						<td align="right">
							特别会员名称:
						</td>
						<td >
						<input type="hidden" name="obj.firmName" id="firmName">
						<select id="firmId" name="obj.firmId" onblur="myblur('firmId')"  onfocus="myfocus('firmId')" onchange="setName()">
											<option value="">请选择</option>
											<c:forEach items="${memberInfoList}" var="member">
												<option value="${member.s_memberNo}">
													${member.name }
												</option>
											</c:forEach>
										</select>
						</td>
						<td align="left" height="40"><div id="firmId_vTip" ></div></td>
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
							</td>
							<td>
							<input class="input_text" onblur="myblur('delayFee${ta}')"  onfocus="myfocus('delayFee${ta}')"
							style="text-align:right" type="text" id="delayFee${ta}"
									name="specialforAudit.delayFee_v" value="${tcDelayFeeMap[step]}">%<font color="red">*</font>
							</td>
							<td align="left" height="40"><div id="delayFee${ta}_vTip" ><%=delayFee%></div></td>
						</tr>
					</c:forEach>
					<tr>
						<td align="right">
							控制方式:
						</td>
						<td>
							<select id="operate" name="obj.operate" class="select_widmid">
								<option value="P" selected="selected">
									个性化
								</option>
							</select>
						</td>
	               </tr>
				</table>
<script type="text/javascript">
function myblur(userID){
	var number = '${total}';
	var flag = true;
	if("commodityId"==userID){
		flag = commodityId(userID);
	}else if("firmId"==userID){
		flag = memberNo(userID)
	}else if(userID != null && userID.indexOf('delayFee')>=0){
		flag = delayFee(userID);
	}else{
		if(!commodityId("commodityId")){
			flag =false;
		}
		if(!memberNo("firmId")){
			flag = false;
		}
		for(var i=1;i<=number;i++){
			if(!delayFee("delayFee"+i)){
				flag = false;
			}
		}
	}
	return flag;
}
function commodityId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "请选择商品";
		flag =false;
	}else{
	//	innerHTML = "选择完成";
		
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	var firmId = document.getElementById("firmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(firmId){
		checkAction.existSpecialMemberDelayFee(commodityId, firmId, function(isExist){
			if(isExist){
				alert('信息已存在，请重新添加');
				document.getElementById("commodityId").value="";
				document.getElementById("firmId").value="";
				document.getElementById("commodityId").focus();
			}
		});
	}
	return flag;
}
function memberNo(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "请选择特别会员";
		flag =false;
	}else{
		//innerHTML = "选择完成";
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	var firmId = document.getElementById("firmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(commodityId){
		checkAction.existSpecialMemberDelayFee(commodityId, firmId, function(isExist){
			if(isExist){
				alert('信息已存在，请重新添加');
				document.getElementById("commodityId").value="";
				document.getElementById("firmId").value="";
				document.getElementById("commodityId").focus();
			}
		});
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
	var innerHTML = "";
	if("commodityId"==userID){
		innerHTML = "请选择商品";
	}
	if("memberNo"==userID){
		innerHTML = "请选择会员";
	}
		innerHTML = "不能为空";
	
	vTip.innerHTML =innerHTML ;
	vTip.className="onFocus";*/
}
function setName() {
	var obj = document.getElementById('commodityId');
	var m_FirmId = document.getElementById('firmId');
	var value = obj.options[obj.selectedIndex].text;
	var firmValue = m_FirmId.options[m_FirmId.selectedIndex].text;
	document.getElementById('commodityName').value = value;
	document.getElementById('firmName').value = firmValue;

}
</script>

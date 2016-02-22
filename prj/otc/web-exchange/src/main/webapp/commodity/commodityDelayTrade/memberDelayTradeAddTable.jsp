<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' /></script>
<%
	String delayFee="";
%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;会员延迟成交</div>
					<tr height="40">
						<td align="right" width="30%">
							商品名称:
						</td>
						<td width="30%">
						<input type="hidden" name="obj.commodityName" id="commodityName">
						<select id="commodityId" name="obj.commodityId" onblur="myblur('commodityId')" 
							class="select_widmid" onchange="setName()">
											<option value="">所有商品</option>
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
							会员名称:
						</td>
						<td >
						<input type="hidden" name="obj.memberName" id="firmName">
						<select id="firmId" name="obj.f_FirmId" class="select_widmid"
						 onblur="myblur('firmId')"  onfocus="myfocus('firmId')" onchange="setName()">
											<option value="">请选择</option>
											<c:forEach items="${memberInfoList}" var="memberInfo">
												<option value="${memberInfo.id}" title ='${memberInfo.name}'>
													${memberInfo.name }
												</option>
											</c:forEach>
										</select>
						</td>
						<td align="left" height="40"><div id="firmId_vTip" ></div></td>
					</tr>
					
					<tr height="40">
						<td align="right">
							延迟成交类型:
						</td>
						<td>
							<select id="delayTradeType" name="obj.delayTradeType"
								class="select_widmid">
								<c:forEach items="${delayTradeMap}" var="maps">
									<option value="${maps.key}">
										${maps.value}
									</option>
								</c:forEach>
							</select>
						</td>
						<td align="left" height="40"><div id="delayTradeType_vTip" ></div></td>
					</tr>
					<tr>
						<td align="right">
							延迟成交时间:
						</td>
						<td>
							<input class="input_text" onblur="myblur('delayTradeTime')"
								style="text-align: right" type="text" id="delayTradeTime" name="obj.delayTradeTime" />
						</td>
						<td align="left" height="40"><div id="delayTradeTime_vTip" class="onFocus">以毫秒为单位</div></td>
	               </tr>
					
					<tr>
						<td align="right">
							是否滑点:
						</td>
						<td>
							<select id="isslipPoint" name="obj.isslipPoint"
								class="select_widmid">
								<c:forEach items="${isslipPointMap}" var="maps">
									<option value="${maps.key}">
										${maps.value}
									</option>
								</c:forEach>
							</select>
						</td>
						<td align="left" height="40"><div id="isslipPoint_vTip" ></div></td>
	               </tr>
				</table>
<script type="text/javascript">
function myblur(userID){
	var flag = true;
	if("commodityId"==userID){
		flag = commodityId(userID);
	}else if("firmId"==userID){
		flag = firmId(userID)
	}else if("delayTradeTime"==userID){
		flag = delayTradeTime(userID);
	}else{
		if(!commodityId("commodityId")){
			flag =false;
		}
		if(!firmId("firmId")){
			flag = false;
		}
		if(!delayTradeTime("delayTradeTime")){
			flag = false;
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
		//innerHTML = "请选择商品";
		flag =true;
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
		checkAction.existCommodityDelayTrade(commodityId, firmId, function(isExist){
			if(isExist){
				alert('信息已存在，请重新添加');
				document.getElementById("commodityId").value="";
				document.getElementById("firmId").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existDelayTradeApplyAdd(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("此申请已由"+applyView.proposer+"于"+applyView.modTimeString+"提交");
						document.getElementById("commodityId").value="";
						document.getElementById("firmId").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}
function firmId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "请选择会员";
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
		checkAction.existCommodityDelayTrade(commodityId, firmId, function(isExist){
			if(isExist){
				alert('信息已存在，请重新添加');
				document.getElementById("commodityId").value="";
				document.getElementById("firmId").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existDelayTradeApplyAdd(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("此申请已由"+applyView.proposer+"于"+applyView.modTimeString+"提交");
						document.getElementById("commodityId").value="";
						document.getElementById("firmId").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}
function delayTradeTime(userID){
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "请输入延迟成交时间";
		flag =false;
	}else if(!integer(user.value)){
		innerHTML = "请输入正确数值";
		flag =false;
	}else if(user.value>60000){
		innerHTML = "延迟时间小于60秒";
		flag =false;
	}else{
		flag =true;
		innerHTML = "以毫秒为单位";
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="onFocus";
	}else{
		vTip.className="onError";
	}
	return flag;
}
function myfocus(userID){
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	if("delayTradeTime"==userID){
		innerHTML = "请输入延迟成交时间";
	}
		innerHTML = "不能为空";
	
	vTip.innerHTML =innerHTML ;
	vTip.className="onFocus";
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

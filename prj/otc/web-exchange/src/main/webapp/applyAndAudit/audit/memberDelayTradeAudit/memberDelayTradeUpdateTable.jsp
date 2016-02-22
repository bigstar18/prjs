<%@ page contentType="text/html;charset=GBK"%>
<%
	
%>
<div class="div_cxtjmid">
	<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;会员延迟成交
</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="40">
						<td align="right" width="30%">
							商品名称:
						</td>
						<td width="30%">
							<input class="input_text_pwdmin" type="hidden" id="commodityId"
								name="obj.commodityId" value="${obj.commodityId}" readonly="readonly">
							<input class="input_text_pwdmin" type="text" id="name"
								name="obj.commodityName" value="${obj.commodityName}"
								readonly="readonly">
						</td>
					<td align="left" height="40"><div id="commodityId_vTip"></div></td>
					</tr>
				
					<tr height="40">
						<td align="right">
							会员名称:
						</td>
						<td >
						
						<input type="hidden" name="obj.f_FirmId" value="${obj.f_FirmId }">
						<input class="input_text_pwdmin" type="text" id="memberName"
							name="obj.memberName" value="${obj.memberName}" readonly="readonly">
						</td>
						<td align="left" height="40"><div id="firmId_vTip" ></div></td>
					</tr>
					
					<tr height="40">
						<td align="right">
							延迟成交类型:
						</td>
						<td>
							<select id="delayTradeType" name="obj.delayTradeType" class="select_widmid">
								<c:forEach items="${delayTradeMap}" var="maps">
									<option value="${maps.key}"<c:if test="${maps.key==obj.delayTradeType}">selected=selected</c:if> >${maps.value }
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
								style="text-align: right" type="text" id="delayTradeTime" name="obj.delayTradeTime"
								value="${obj.delayTradeTime}" />
						</td>
						<td align="left" height="40"><div id="delayTradeTime_vTip" class="onFocus">以毫秒为单位</div></td>
	               </tr>
					
					<tr>
						<td align="right">
							是否滑点成交:
						</td>
						<td>
						<select id="isslipPoint" name="obj.isslipPoint" class="select_widmid">
							<option value="N">
								否
							</option>
							<option value="Y">
								是
							</option>	
							<option value="T">
								不能成交
							</option>		
						</select>
						<script type="text/javascript">
							document.getElementById('isslipPoint').value = '${obj.isslipPoint}';
						</script>
						</td>
						<td align="left" height="40"><div id="isslipPoint_vTip" ></div></td>
	               </tr>
</table>

<script type="text/javascript">

function myblur(userID){
	var flag = true;
	if("delayTradeTime"==userID){
		flag = delayTradeTime(userID);
	}else{
		if(!delayTradeTime("delayTradeTime")){
			flag = false;
		}
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

</script>
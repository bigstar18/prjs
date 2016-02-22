<%@ page contentType="text/html;charset=GBK"%>
<%
	String oneMaxOrderQty = "";
	String oneMinOrderQty = "";
	String maxCleanQty = "";
	String maxHoldQty = "";
%>
<div class="div_cxtjd">
	<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;会员持仓数量
</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<input type="hidden" name="obj.commodityId" value="${obj.commodityId}">
	<input type="hidden" name="obj.firmId" value="${obj.firmId}">
	<tr height="40">
		<td align="right" width="20%">
			商品名称:
		</td>
		<td width="30%">
			<input type="text" id="name" class="input_text_pwdmin"
				name="obj.commodityName" value="${obj.commodityName}"
				readonly="readonly">
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			会员名称：
		</td>
		<td>
			<input type="text" id="firmId" class="input_text_pwdmin"
				name="obj.memberName" value="${obj.memberName}"
				readonly="readonly">
		</td>
	</tr>
	<tr height="40">
					<td align="right">
						单笔最大下单量:
					</td>
					<td>
						<input type="text" onblur="myblur('oneMaxOrderQty')" style="text-align:right"
							onfocus="myfocus('oneMaxOrderQty')" id="oneMaxOrderQty"
							class="input_text" name="obj.oneMaxOrderQty"
							value="${obj.oneMaxOrderQty}"><span>（手）</span>
					</td>
					<td align="left" height="40">
						<div id="oneMaxOrderQty_vTip" ><%=oneMaxOrderQty%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						单笔最小下单量:
					</td>
					<td>
						<input type="text" onblur="myblur('oneMinOrderQty')" style="text-align:right"
							onfocus="myfocus('oneMinOrderQty')" id="oneMinOrderQty"
							class="input_text" name="obj.oneMinOrderQty"
							value="${obj.oneMinOrderQty}"><span>（手）</span>
					</td>
					<td align="left" height="40">
						<div id="oneMinOrderQty_vTip" ><%=oneMinOrderQty%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						最大净持仓量:
					</td>
					<td>
						<input type="text" onblur="myblur('maxCleanQty')" style="text-align:right"
							onfocus="myfocus('maxCleanQty')" id="maxCleanQty"
							class="input_text" name="obj.maxCleanQty"
							value="${obj.maxCleanQty}"><span>（手）</span>
					</td>
					<td align="left" height="40">
						<div id="maxCleanQty_vTip" ><%=maxCleanQty%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						最大持仓量:
					</td>
					<td>
						<input type="text" onblur="myblur('maxHoldQty')" style="text-align:right"
							onfocus="myfocus('maxHoldQty')" id="maxHoldQty" class="input_text"
							name="obj.maxHoldQty" value="${obj.maxHoldQty}"><span>（手）</span>
					</td>
					<td align="left" height="40">
						<div id="maxHoldQty_vTip" ><%=maxHoldQty%></div>
					</td>
				</tr>
	<tr height="40">
		<td align="right">
			控制方式:
		</td>
		<td>
			<select id="operate" name="obj.operate" class="select_widmid">
				<option value="P">
					个性化
				</option>
				<option value="D">
					默认
				</option>
			</select>
			<script type="text/javascript">
document.getElementById('operate').value = '${obj.operate}';
</script>
		</td>
	</tr>
</table>
<script>
function myblur(userID){
	var flag = true;
	if(userID!=null && userID.indexOf("all")<0){
		flag = usersblur(userID);
	}else{
		if(!usersblur("oneMaxOrderQty")) flag = false;
		if(!usersblur("oneMinOrderQty")) flag = false;
		if(!usersblur("maxCleanQty")) flag = false;
		if(!usersblur("maxHoldQty")) flag = false;
	}
	return flag;
}
function usersblur(userID){
	var str="请输入≥1且≤20000的整数";
	var flag = false;
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if (user.value<1 || user.value > 20000){
				innerHTML = str;
	}else {
			if(userID=="oneMaxOrderQty"){
				innerHTML = "<%=oneMaxOrderQty%>";
				flag = true;
				vTip.className = "";
			}
			if(userID=="oneMinOrderQty"){
				innerHTML = "<%=oneMinOrderQty%>";
				flag = true;
				vTip.className = "";
				var oneMax = document.getElementById("oneMaxOrderQty");
				if (parseFloat(user.value) > parseFloat(oneMax.value)) {
					innerHTML = "单笔最小下单量<单笔最大下单量";
					flag = false;
				}
			}
			if(userID=="maxCleanQty"){
				innerHTML = "<%=maxCleanQty%>";
				flag = true;
				vTip.className = "";
			}
			if(userID=="maxHoldQty"){
				innerHTML = "<%=maxHoldQty%>";
				flag = true;
				vTip.className = "";
			}
		}
		if(!flag){
		vTip.className = "onError";
		}
	vTip.innerHTML = innerHTML;
	return flag;
}
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "不能为空";
	vTip.className = "onFocus";*/
}
</script>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='${basePath}/dwr/engine.js'>
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/checkCustomerNo.js' />
</script>
<%
	String oneMaxOrderQty = "";
	String oneMinOrderQty = "";
	String maxCleanQty = "";
	String maxHoldQty = "";
%>
<html>
	<head>
		<title>客户商品持仓数量修改</title>
	</head>
	<body class="st_body">
		<form name="frm"
			action="${basePath}/commodity/customerHoldQty/update.action" method="post" targetType="hidden">
			<input type="hidden" id="memberNo" name="obj.memberNo" value="${REGISTERID}"> 
			<input type="hidden" id="commodityName" name="obj.commodityName" value=""> 
			<div>
				<div class="st_title">
					<img src="<%=skinPath%>/cssimg/st_ico1.gif"
						align="absmiddle" />
					&nbsp;&nbsp;客户商品持仓数量修改
				</div>
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								align="center" class="st_bor">
								<tr height="35">
									<td align="right" width="23%">
										商品名称:
									</td>
									<td width="37%">
										<input type="hidden" name="obj.commodityId" value="${obj.commodityId}">
										<select name="commodityId" id="commodityId" disabled="disabled" class="select_widmid">
											<c:forEach items="${commodityList}" var="commodity">
												<option value="${commodity.id}">
													${commodity.name}
												</option>
											</c:forEach>
										</select>
									</td>
									</tr>
									<tr>
									<td align="right">
										交易账号:
									</td>
									<td>
										<input class="input_text_pwdmin" type="text" id="customerNo"
											name="obj.customerNo" value="${obj.customerNo}"	 readonly="readonly">
										<strong class="check_input">&nbsp;*</strong>
									</td>
								</tr>
								<tr height="35">
									<td align="right">
										单笔最大下单量:
									</td>
									<td>
										<input class="input_text" type="text" id="oneMaxOrderQty"
										onblur="myblur('oneMaxOrderQty')" onfocus="myfocus('oneMaxOrderQty')"
											style="text-align: right" name="obj.oneMaxOrderQty"
											value="${obj.oneMaxOrderQty}">
										<strong class="check_input">&nbsp;*</strong><span>（手）</span>
									</td>
									<td align="left" height="40"><div id="oneMaxOrderQty_vTip"><%=oneMaxOrderQty%></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										单笔最小下单量:
									</td>
									<td>
										<input class="input_text" type="text" id="oneMinOrderQty"
											style="text-align: right" name="obj.oneMinOrderQty"
										onblur="myblur('oneMinOrderQty')" onfocus="myfocus('oneMinOrderQty')"
											value="${obj.oneMinOrderQty}">
										<strong class="check_input">&nbsp;*</strong><span>（手）</span>
									</td>
									<td align="left" height="40"><div id="oneMinOrderQty_vTip"><%=oneMinOrderQty%></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										最大净持仓量:
									</td>
									<td>
										<input class="input_text" type="text" id="maxCleanQty"
										onblur="myblur('maxCleanQty')" onfocus="myfocus('maxCleanQty')"
											style="text-align: right" name="obj.maxCleanQty"
											value="${obj.maxCleanQty}">
										<strong class="check_input">&nbsp;*</strong><span>（手）</span>
									</td>
									<td align="left" height="40"><div id="maxCleanQty_vTip" ><%=maxCleanQty%></div></td>
								</tr>
								<tr height="35">
									<td align="right">
										最大持仓量:
									</td>
									<td>
										<input class="input_text" onblur="myblur('maxHoldQty')" 
										onfocus="myfocus('maxHoldQty')" type="text" id="maxHoldQty"
											style="text-align: right" name="obj.maxHoldQty"
											value="${obj.maxHoldQty}">
										<strong class="check_input">&nbsp;*</strong><span>（手）</span>
									</td>
									<td align="left" height="40"><div id="maxHoldQty_vTip" ><%=maxHoldQty%></div></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" id="update" onclick="updateCustomerHoldQty()">
								保存
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
function updateCustomerHoldQty() {
	if(!myblur("all")){return false;}
		var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
}
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
<%@ include file="/public/footInc.jsp"%>
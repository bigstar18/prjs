<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String tradeMargin="成交金额的8%";
	String settleMargin="";
	String holidayMargin="";
%>
<div class="div_cxtj"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;会员保证金</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<tr height="40">
		<td align="right" width="20%">
			商品名称:
		</td>
		<td width="30%">
			<input type="hidden" name="obj.commodityName" id="commodityName">
			<select id="commodityId" name="obj.commodityId" class="select_widmid" onblur="myblur('commodityId')"  onfocus="myfocus('commodityId')"
				onchange="setCommodityName();">
				<option value="">
					请选择
				</option>
				<c:forEach items="${commodityList }" var="commodity">
					<option value="${commodity.id}"
						<c:if test="${obj.commodityId==commodity.id }"> selected="selected"</c:if>>
						${commodity.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40"><div id="commodityId_vTip"></div></td>
		<tr>
		<tr height="40">
		<td align="right">
			会员名称:
		</td>
		<td>
			<input type="hidden" name="obj.firmName" id="firmName">
			<select id="firmId" name="obj.firmId" class="select_widmid" onblur="myblur('firmId')"  onfocus="myfocus('firmId')"
				onchange="setCommodityName();">
				<option value="">
					请选择
				</option>
				<c:forEach items="${memberInfoList}" var="member">
					<option value="${member.id}"
						<c:if test="${obj.firmId==member.id }">selected="selected"</c:if>>
						${member.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40"><div id="firmId_vTip"  ></div></td>
	</tr>
	<tr height="40">
					<td align="right" width="20%">
						保证金算法:
					</td>
					<td>
						<select id="marginAlgr" name="obj.marginAlgr_v" onchange="changeAlgr(this.value);" 
						class="select_widmid" >
							<c:forEach items="${commodityMarginAlgrMap }" var="maps">
								<option value="${maps.key}">${maps.value}</option>
							</c:forEach>
						</select>
						
					</td>
				</tr>
				<tr height="40">
					<td align="right" width="20%">
						即市占用/冻结:
					</td>
					<td>
						<input type="text" class="input_text" id="tradeMargin" style="text-align:right"
							name="obj.tradeMargin_v" value="${obj.tradeMargin_v}" onblur="myblur('tradeMargin')"  onfocus="myfocus('tradeMargin')">
							<span id="tradeMargin_span"></span>
					</td>
					<td align="left" height="40"><div id="tradeMargin_vTip" class="onFocus"><%= tradeMargin%></div></td>
				</tr>
				<%--<!-- <tr height="40">
					<td align="right"  height="40" width="20%">
						结算维持:
					</td>
					<td  height="40">-->
						<input type="hidden" class="input_text" id="settleMargin" onblur="myblur('settleMargin')"  onfocus="myfocus('settleMargin')"
							name="obj.settleMargin_v" value="0">
					<!--		<span id="settleMargin_span"></span>
					</td>
					<td align="left" height="40"><div id="settleMargin_vTip"  class="onFocus"><%= settleMargin%></div></td>
				</tr>
				<tr height="40">
					<td align="right"  height="40" width="20%">
						假日维持:
					</td>
					<td  height="40">-->
						<input type="hidden" class="input_text" id="holidayMargin" onblur="myblur('holidayMargin')"  onfocus="myfocus('holidayMargin')"
							name="obj.holidayMargin_v" value="0">
						<!--	<span id="holidayMargin_span"></span>
					</td>
					<td align="left" height="40"><div id="holidayMargin_vTip"  class="onFocus"><%= settleMargin%></div></td>
				</tr>
				 -->
		--%><tr>
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
function changeAlgr(fee) {
		if (fee == '1') {
		//document.getElementById("settleMargin_span").innerHTML = "%";
		document.getElementById("tradeMargin_span").innerHTML = "%";
		var vTip = document.getElementById("tradeMargin_vTip");
		vTip.innerHTML="<%= tradeMargin%>";
		vTip.className ="onFocus";
		//document.getElementById("holidayMargin_span").innerHTML = "%";
	}
	if (fee == '2') {
		//document.getElementById("settleMargin_span").innerHTML = "";
		document.getElementById("tradeMargin_span").innerHTML = "";
		var vTip = document.getElementById("tradeMargin_vTip");
		vTip.innerHTML="";
		vTip.className ="";
		//document.getElementById("holidayMargin_span").innerHTML = "";
	}
}
function myblur(userID){
	var flag = true;
	if("commodityId"==userID){
		flag = commodityId(userID);
	}else if("firmId"==userID){
		flag = firmId(userID)
	}else if("tradeMargin"==userID){
		flag = tradeMargin(userID);
	}else{
		if(!commodityId("commodityId")){
			flag =false;
		}
		if(!firmId("firmId")){
			flag = false;
		}
		if(!tradeMargin("tradeMargin")){
			flag = false;
		}
	/*	if(!settleMargin("settleMargin")){
			flag = false;
		}if(!holidayMargin("holidayMargin")){
			flag = false;
		}
		*/
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
		checkAction.existMemberMargin(commodityId, firmId, function(isExist){
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
		checkAction.existMemberMargin(commodityId, firmId, function(isExist){
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

function tradeMargin(userID){
	var innerHTML = "";      
	var str ="请输入>=0且≤100的正数";
	var marg = document.getElementById("marginAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var flag = false;
	if(isEmpty(user.value+"")){
		innerHTML =  "不能为空";
	}else if(marg==1){//百分比 
			if(!flote(user.value,2)){
				innerHTML = "最多2位小数的非负数";
			}else if(user.value<0 || user.value>100){
					innerHTML = str;
					}else {
						innerHTML = '<%=tradeMargin%>';
						flag = true;
						if(flag){
						vTip.className="onFocus";
						}
					}
			}else{//绝对值
				if(user.value<0){
				innerHTML  =  "请输入>=0正数";
				}else if(!flote(user.value,2)){
				innerHTML  ="最多2位小数的非负数";
				}else  {
					innerHTML = '';
					flag = true;
					if(flag){
						vTip.className="";
						}
				}
			}	
		vTip.innerHTML=innerHTML;
		if(!flag){
			vTip.className="onError";
		}
	return flag;
}
function holidayMargin(userID){
	var innerHTML = "";
	var marg = document.getElementById("marginAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "不能为空";
	}else if(marg==1){//百分比 
		if(!flote(user.value,2)){
		innerHTML = "最多两位小数的数字";
	}else if(user.value<0 || user.value>100){
		innerHTML = "应在0到100之间";
	}else{
		innerHTML = '<%=holidayMargin%>';
		flag = true;
	}	
	}else{//绝对值
				if(!flote(user.value,4)){
				innerHTML  ="四位小数以内的数字";
				}else if(user.value<0||user.value>10000000){
				innerHTML = "应在0到10000000之间";
				}else {
					innerHTML = '<%=holidayMargin%>';
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
function settleMargin(userID){
	var innerHTML = "";
	var marg = document.getElementById("marginAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var flag = false;
	if(isEmpty(''+user.value)){
		innerHTML = "不能为空";
	}else if(marg==1){//百分比 
		if(!flote(user.value,'2')){
		innerHTML = "请输入最多两位小数的数字";
	}else if( (user.value<0 || user.value>100)){
		innerHTML = "应在0到100之间";
	}else{
		innerHTML = '<%=settleMargin%>';
		flag = true;
	}
	}else{//绝对值
		if(!flote(user.value,4)){
			innerHTML  ="请输入四位小数以内的数字";
		}else if(user.value<0||user.value>100000){
			innerHTML  =  "应在0-100000之间";
		}else {
			innerHTML = '<%=settleMargin%>';
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
/**	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	if("commodityId"==userID){
		innerHTML = "请选择商品";
	}
	if("firmId"==userID){
		innerHTML = "请选择用户";
	}
	if("marginAlgr"==userID){
		innerHTML = "请选择类型";
	}
	if("tradeMargin"==userID){
		innerHTML = "请填写即市占用/冻结";
	}
	if("settleMargin"==userID){
		innerHTML = "请填写结算维持";
	}
	if("holidayMargin"==userID){
		innerHTML = "请填写假日维持";
	}
	vTip.innerHTML=innerHTML;
	vTip.className="onFocus";*/
}

function setCommodityName() {
	var obj = document.getElementById('commodityId');
	var m_FirmId = document.getElementById('firmId');
	var value = obj.options[obj.selectedIndex].text;
	var firmValue = m_FirmId.options[m_FirmId.selectedIndex].text;
	document.getElementById('commodityName').value = value;
	document.getElementById('firmName').value = firmValue;

}
</script>
<script type="text/javascript">
<!--
changeAlgr('${obj.marginAlgr_v}');
//-->
</script>

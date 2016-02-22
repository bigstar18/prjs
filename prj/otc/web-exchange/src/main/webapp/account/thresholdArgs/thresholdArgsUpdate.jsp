<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String c_WarnTh = "权益/持仓占用交易保证金*100%";
	String c_ForceTh = "权益/持仓占用交易保证金*100%";
	String m_WarnTh = "当前权益/期初权益*100%";
	String m_FrozenTh ="当前权益/期初权益*100%";
	String sm_WarnTh = "当前权益/期初权益*100%";
	String m_SelfTradeRate = "";
	String cm_MinRiskFund = "";
	String sm_MinRiskFund = "";
	String bm_MinRiskFund = "";
	String sm_FrozenTh ="当前权益/期初权益*100%";
	String monitorRefresh="";
%>
<body class="st_body" >
	<form name="frm" id="frm"
		action="${basePath}/account/thresholdArgs/update.action" method="post">
		<div>
			<table align="center" border="0" cellspacing="0" width="65%" cellpadding="0">
				<tr>
					<td width="800">
						&nbsp;
						<div class="div_cxtjmid">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							修改默认风险阈值
						</div>
						<div class="div_tj">
							<table border="0" cellpadding="0" cellspacing="0" align="center"
								class="table2_style" width="90%">
								<tr>
									<td>
										<div>
											<table class="table3_style" border="0" cellspacing="0" align="center"
												cellpadding="0" style="padding-right:20px;" width="90%" style="table-layout:fixed">
												<tr height="45" width="100%">
													<td align="right" width="220" class="table_borderbot">
														客户预警风险率 ：
													</td>
													<td align="left" width="250" class="table_borderbot">
														<input id="marketCode" name="obj.marketCode" type="hidden"
															class="input_text_mid" value="${obj.marketCode }">
														<input class="input_text_mid" id="c_WarnTh" name="obj.c_WarnTh_v" style="text-align:right"
															type="text"
															value="${obj.c_WarnTh_v }"
															onblur="myblur('c_WarnTh')"
															onfocus="myfocus('c_WarnTh')">%
													</td>
													<td align="left" height="40" width="300" class="table_borderbot">
														<div id="c_WarnTh_vTip" class="onFocus"><%=c_WarnTh%></div>
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														客户强平风险率 ：
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="c_ForceTh" name="obj.c_ForceTh_v" style="text-align:right"
															type="text"
															value="${obj.c_ForceTh_v }"
															onblur="myblur('c_ForceTh')"
															onfocus="myfocus('c_ForceTh')">%
													</td>
													<td align="left" height="40" class="table_borderbot">
														<div id="c_ForceTh_vTip" class="onFocus"><%=c_ForceTh%></div>
													</td>
												</tr>
												<tr height="45">
													<td align="right" style="padding-top: 10px;" class="table_borderbot">
														会员预警风险率 ：
													</td>
													<td align="left" style="padding-top: 5px;" class="table_borderbot">
														<input class="input_text_mid" id="m_WarnTh" name="obj.m_WarnTh_v" style="text-align:right"
															type="text"
															value="${obj.m_WarnTh_v }"
															onblur="myblur('m_WarnTh')"
															onfocus="myfocus('m_WarnTh')">%
													</td>
													<td align="left" height="40" class="table_borderbot">
														<div id="m_WarnTh_vTip" class="onFocus"><%=m_WarnTh%></div>
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot" style="padding-top: 10px;" >
														会员冻结风险率 ：
													</td>
													<td align="left" class="table_borderbot" style="padding-top: 5px;" >
														<input class="input_text_mid" id="m_FrozenTh" name="obj.m_FrozenTh_v" style="text-align:right"
															type="text"
															value="${obj.m_FrozenTh_v }"
															onblur="myblur('m_FrozenTh')"
															onfocus="myfocus('m_FrozenTh')">%
													</td>
													<td align="left" height="40" class="table_borderbot">
														<div id="m_FrozenTh_vTip" class="onFocus"><%=m_FrozenTh%></div>
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot" style="padding-top: 10px;">
														特别会员预警风险率 ：
													</td>
													<td align="left" class="table_borderbot" style="padding-top: 5px;">
														<input class="input_text_mid" id="sm_WarnTh" name="obj.sm_WarnTh_v" style="text-align:right"
															type="text"
															value="${obj.sm_WarnTh_v }"
															onblur="myblur('sm_WarnTh')"
															onfocus="myfocus('sm_WarnTh')">%
													</td>
													<td align="left" height="40" class="table_borderbot">
														<div id="sm_WarnTh_vTip" class="onFocus"><%=sm_WarnTh%></div>
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot" style="padding-top: 10px;">
														特别会员冻结风险率 ：
													</td>
													<td align="left" class="table_borderbot" style="padding-top: 5px;">
														<input class="input_text_mid" id="sm_FrozenTh" name="obj.sm_FrozenTh_v" style="text-align:right"
															type="text"
															value="${obj.sm_FrozenTh_v }"
															onblur="myblur('sm_FrozenTh')"
															onfocus="myfocus('sm_FrozenTh')">%
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="sm_FrozenTh_vTip" class="onFocus"><%=sm_FrozenTh%></div>
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														会员非客户头寸交易比例 ：
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="m_SelfTradeRate"
															name="obj.m_SelfTradeRate_v" type="text"
															value="${obj.m_SelfTradeRate_v }" style="text-align:right"
															onblur="myblur('m_SelfTradeRate')"
															onfocus="myfocus('m_SelfTradeRate')">%
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="m_SelfTradeRate_vTip" ><%=m_SelfTradeRate%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														会员出金阈值 ：
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="cm_MinRiskFund" style="text-align:right"
															name="obj.cm_MinRiskFund" type="text"
															value="<fmt:formatNumber value="${obj.cm_MinRiskFund }"   pattern="###,##0.00"/>"
															onblur="myblur('cm_MinRiskFund')"
															onfocus="myfocus('cm_MinRiskFund')"><span>（元）</span>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="cm_MinRiskFund_vTip"><%=cm_MinRiskFund%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														经纪会员出金阈值 ：
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="bm_MinRiskFund" style="text-align:right"
															name="obj.bm_MinRiskFund" type="text"
															value="<fmt:formatNumber value="${obj.bm_MinRiskFund }"   pattern="###,##0.00"/>"
															onblur="myblur('bm_MinRiskFund')"
															onfocus="myfocus('bm_MinRiskFund')"><span>（元）</span>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="bm_MinRiskFund_vTip" ><%=bm_MinRiskFund%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														特别会员出金阈值 ：
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="sm_MinRiskFund" style="text-align:right"
															name="obj.sm_MinRiskFund" type="text"
															value="<fmt:formatNumber value="${obj.sm_MinRiskFund }"   pattern="###,##0.00"/>"
															onblur="myblur('sm_MinRiskFund')"
															onfocus="myfocus('sm_MinRiskFund')"><span>（元）</span>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="sm_MinRiskFund_vTip" ><%=sm_MinRiskFund%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														会员状态改变时&nbsp;&nbsp;&nbsp;<br/>是否向客户发送公告 ：
													</td>
													<td align="left" class="table_borderbot">
														<select id="mchangeStatus" name="obj.mchangeStatus" style="width: 120px;">
															<option value="Y" <c:if test="${obj.mchangeStatus == 'Y' }">selected</c:if>>是</option>
															<option value="N" <c:if test="${obj.mchangeStatus == 'N' }">selected</c:if>>否</option>
														</select>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right">
														会员交易监控刷新间隔时间 ：
													</td>
													<td align="left">
														<input class="input_text_mid" id="monitorRefresh" style="text-align:right"
															name="obj.monitorRefresh" type="text" value="${obj.monitorRefresh }"
															onblur="myblur('monitorRefresh')" onfocus="myfocus('monitorRefresh')"><span>（秒）</span>
													</td>
													<td align="left" height="40">
														<div id="monitorRefresh_vTip" ><%=monitorRefresh %></div>&nbsp;
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div style="padding-top: 20px;">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
					<td align="center">
						<button class="btn_sec" id="update"
							onclick="return updateThresholdArgs()">
							保存
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function updateThresholdArgs() {
	var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;} 
			var vaild = affirm("您确定要操作吗？");
			if(vaild==true){
				    frm.submit();
	  	  }else{
       	    return false;
	   }
}

function myblur(userID) {
	var flag = true;
	if ("c_WarnTh" == userID) {
		flag = c_WarnTh(userID);
	} else if ("c_ForceTh" == userID) {
		flag = c_ForceTh(userID);
	} else if ("m_WarnTh" == userID) {
		flag = m_WarnTh(userID);
	} else if ("m_FrozenTh" == userID) {
		flag = m_FrozenTh(userID);
	}else if ("sm_FrozenTh" == userID) {
		flag = sm_FrozenTh(userID);
	}else if ("sm_WarnTh" == userID) {
		flag = sm_WarnTh(userID);
	} else if ("m_SelfTradeRate" == userID) {
		flag = m_SelfTradeRate(userID);
	} else if ("cm_MinRiskFund" == userID) {
		flag = cm_MinRiskFund(userID);
	} else if ("bm_MinRiskFund" == userID) {
		flag = bm_MinRiskFund(userID);
	}else if ("sm_MinRiskFund" == userID) {
		flag = sm_MinRiskFund(userID);
	}else if ("monitorRefresh"==userID){
		flag = monitorRefresh(userID);
	} else {
		if (!c_WarnTh("c_WarnTh"))
			flag = false;
		if (!c_ForceTh("c_ForceTh"))
			flag = false;
		if (!m_WarnTh("m_WarnTh"))
			flag = false;
		if (!m_FrozenTh("m_FrozenTh"))
			flag = false;
		if (!sm_FrozenTh("sm_FrozenTh"))
			flag = false;
		if (!sm_WarnTh("sm_WarnTh"))
			flag = false;
		if (!m_SelfTradeRate("m_SelfTradeRate"))
			flag = false;
		if (!cm_MinRiskFund("cm_MinRiskFund"))
			flag = false;
		if (!bm_MinRiskFund("bm_MinRiskFund"))
			flag = false;
		if (!sm_MinRiskFund("sm_MinRiskFund"))
			flag = false;
		if (!monitorRefresh("monitorRefresh"))
			flag=false;
	}
	return flag;
}
//客户预警风险率
function c_WarnTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 200) {
			innerHTML = "请输入≤200的正数";
		} else {
			innerHTML = '<%=c_WarnTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
//客户强平风险率 
function c_ForceTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 200) {
			innerHTML = "请输入≤200的正数";
		} else {
			innerHTML = '<%=c_ForceTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
//会员预警风险率
function m_WarnTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 200) {
			innerHTML = "请输入≤200的正数";
		} else {
			innerHTML = '<%=m_WarnTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
//会员冻结风险率
function m_FrozenTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 200) {
			innerHTML = "请输入≤200的正数";
		} else {
			innerHTML = '<%=m_FrozenTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
//特别会员冻结风险率
function sm_FrozenTh(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 200) {
			innerHTML = "请输入≤200的正数";
		} else {
			innerHTML = '<%=sm_FrozenTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
//特别会员预警风险率
function sm_WarnTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 200) {
			innerHTML = "请输入≤200的正数";
		} else {
			innerHTML = '<%=sm_WarnTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
//会员自营比例
function m_SelfTradeRate(userID) {
	var innerHTML = "";
	var marg = document.getElementById("m_SelfTradeRate").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		  if (user.value < 0) {
			innerHTML ="请输入≥0的数";
		}else if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else if(user.value > 500) {
			innerHTML = "请输入≤500的正数";
		} else {
			innerHTML = '<%=m_SelfTradeRate%>';
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
//会员最低出金阈值
function cm_MinRiskFund(userID) {
	var innerHTML = "";
	var str ="请输入>0的正数"
	var marg = document.getElementById("cm_MinRiskFund").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		 if (user.value <= 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		}else {
			innerHTML = '<%=cm_MinRiskFund%>';
			transStr(userID);
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
//经纪会员最低出金阈值
function bm_MinRiskFund(userID) {
	var innerHTML = "";
	var str ="请输入>0的正数"
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		 if (user.value <= 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		}else {
			innerHTML = '<%=bm_MinRiskFund%>';
			transStr(userID);
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
//特别会员最低出金阈值
function sm_MinRiskFund(userID) {
	var innerHTML = "";
	var str ="请输入>0的正数";
	var marg = document.getElementById("sm_MinRiskFund").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		 if (user.value <= 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "最多两位小数的数字";
		} else {
			innerHTML = '<%=sm_MinRiskFund%>';
			transStr(userID);
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
//交易监控刷新间隔时间
function monitorRefresh(userID){
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		 if (!integer(user.value)) {
			innerHTML ="请输入正确的整数";
		}else {
			innerHTML = '<%=monitorRefresh%>';
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

function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	if ('minRiskFund' == userID) {
		innerHTML = "不能为空";
	}
	if ('warnTh' == userID) {
		innerHTML = "不能为空";
	}
	if ('frozenTh' == userID) {
		innerHTML = "不能为空";
	}
	if ('cu_F_WarnTh' == userID) {
		innerHTML = "不能为空";
	}
	if ('m_SelfTradeRate' == userID) {
		innerHTML = "不能为空";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
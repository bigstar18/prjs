<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String c_WarnTh = "Ȩ��/�ֲ�ռ�ý��ױ�֤��*100%";
	String c_ForceTh = "Ȩ��/�ֲ�ռ�ý��ױ�֤��*100%";
	String m_WarnTh = "��ǰȨ��/�ڳ�Ȩ��*100%";
	String m_FrozenTh ="��ǰȨ��/�ڳ�Ȩ��*100%";
	String sm_WarnTh = "��ǰȨ��/�ڳ�Ȩ��*100%";
	String m_SelfTradeRate = "";
	String cm_MinRiskFund = "";
	String sm_MinRiskFund = "";
	String bm_MinRiskFund = "";
	String sm_FrozenTh ="��ǰȨ��/�ڳ�Ȩ��*100%";
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
							�޸�Ĭ�Ϸ�����ֵ
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
														�ͻ�Ԥ�������� ��
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
														�ͻ�ǿƽ������ ��
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
														��ԱԤ�������� ��
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
														��Ա��������� ��
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
														�ر��ԱԤ�������� ��
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
														�ر��Ա��������� ��
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
														��Ա�ǿͻ�ͷ�罻�ױ��� ��
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
														��Ա������ֵ ��
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="cm_MinRiskFund" style="text-align:right"
															name="obj.cm_MinRiskFund" type="text"
															value="<fmt:formatNumber value="${obj.cm_MinRiskFund }"   pattern="###,##0.00"/>"
															onblur="myblur('cm_MinRiskFund')"
															onfocus="myfocus('cm_MinRiskFund')"><span>��Ԫ��</span>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="cm_MinRiskFund_vTip"><%=cm_MinRiskFund%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														���ͻ�Ա������ֵ ��
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="bm_MinRiskFund" style="text-align:right"
															name="obj.bm_MinRiskFund" type="text"
															value="<fmt:formatNumber value="${obj.bm_MinRiskFund }"   pattern="###,##0.00"/>"
															onblur="myblur('bm_MinRiskFund')"
															onfocus="myfocus('bm_MinRiskFund')"><span>��Ԫ��</span>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="bm_MinRiskFund_vTip" ><%=bm_MinRiskFund%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														�ر��Ա������ֵ ��
													</td>
													<td align="left" class="table_borderbot">
														<input class="input_text_mid" id="sm_MinRiskFund" style="text-align:right"
															name="obj.sm_MinRiskFund" type="text"
															value="<fmt:formatNumber value="${obj.sm_MinRiskFund }"   pattern="###,##0.00"/>"
															onblur="myblur('sm_MinRiskFund')"
															onfocus="myfocus('sm_MinRiskFund')"><span>��Ԫ��</span>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div id="sm_MinRiskFund_vTip" ><%=sm_MinRiskFund%></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right" class="table_borderbot">
														��Ա״̬�ı�ʱ&nbsp;&nbsp;&nbsp;<br/>�Ƿ���ͻ����͹��� ��
													</td>
													<td align="left" class="table_borderbot">
														<select id="mchangeStatus" name="obj.mchangeStatus" style="width: 120px;">
															<option value="Y" <c:if test="${obj.mchangeStatus == 'Y' }">selected</c:if>>��</option>
															<option value="N" <c:if test="${obj.mchangeStatus == 'N' }">selected</c:if>>��</option>
														</select>
													</td>
													<td align="left" height="45" class="table_borderbot">
														<div></div>&nbsp;
													</td>
												</tr>
												<tr height="45">
													<td align="right">
														��Ա���׼��ˢ�¼��ʱ�� ��
													</td>
													<td align="left">
														<input class="input_text_mid" id="monitorRefresh" style="text-align:right"
															name="obj.monitorRefresh" type="text" value="${obj.monitorRefresh }"
															onblur="myblur('monitorRefresh')" onfocus="myfocus('monitorRefresh')"><span>���룩</span>
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
							����
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
			alert("û���޸�����");
			return false;} 
			var vaild = affirm("��ȷ��Ҫ������");
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
//�ͻ�Ԥ��������
function c_WarnTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = "�������200������";
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
//�ͻ�ǿƽ������ 
function c_ForceTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = "�������200������";
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
//��ԱԤ��������
function m_WarnTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = "�������200������";
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
//��Ա���������
function m_FrozenTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = "�������200������";
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
//�ر��Ա���������
function sm_FrozenTh(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = "�������200������";
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
//�ر��ԱԤ��������
function sm_WarnTh(userID) {
	var innerHTML = "";
	var marg = document.getElementById("c_WarnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = "�������200������";
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
//��Ա��Ӫ����
function m_SelfTradeRate(userID) {
	var innerHTML = "";
	var marg = document.getElementById("m_SelfTradeRate").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		  if (user.value < 0) {
			innerHTML ="�������0����";
		}else if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 500) {
			innerHTML = "�������500������";
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
//��Ա��ͳ�����ֵ
function cm_MinRiskFund(userID) {
	var innerHTML = "";
	var str ="������>0������"
	var marg = document.getElementById("cm_MinRiskFund").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		 if (user.value <= 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
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
//���ͻ�Ա��ͳ�����ֵ
function bm_MinRiskFund(userID) {
	var innerHTML = "";
	var str ="������>0������"
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		 if (user.value <= 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
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
//�ر��Ա��ͳ�����ֵ
function sm_MinRiskFund(userID) {
	var innerHTML = "";
	var str ="������>0������";
	var marg = document.getElementById("sm_MinRiskFund").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		 if (user.value <= 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
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
//���׼��ˢ�¼��ʱ��
function monitorRefresh(userID){
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		 if (!integer(user.value)) {
			innerHTML ="��������ȷ������";
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
		innerHTML = "����Ϊ��";
	}
	if ('warnTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('frozenTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('cu_F_WarnTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('m_SelfTradeRate' == userID) {
		innerHTML = "����Ϊ��";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
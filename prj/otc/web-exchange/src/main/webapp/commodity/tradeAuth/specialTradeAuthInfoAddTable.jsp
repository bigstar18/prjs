<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
			<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				�ر��Ա����Ȩ��
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<tr height="40">
					<td align="right" width="25%">
						��Ʒ����:
					</td>
					<td width="30%">
						<input type="hidden" name="obj.commodityName" id="commodityName">
						<select id="commodityId" name="obj.commodityId" class="select_widmid" style="width: 140"
						onblur="myblur('commodityId')"  onfocus="myfocus('commodityId')"	onchange="setCommodityName();">
							<option value="">
								��ѡ��
							</option>
							<c:forEach items="${commodityList}" var="commodit">
								<option value="${commodit.id}">
									${commodit.name }
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="left" height="40"><div id="commodityId_vTip"></div></td>
				</tr>
				<tr height="40">
					<td align="right">
						�ر��Ա����:
					</td>
					<td>
						<input type="hidden" name="obj.firmName" id="firmName">
						<select id="firmId" name="obj.firmId" class="select_widmid" style="width: 140"
							onblur="myblur('firmId')"  onfocus="myfocus('firmId')" onchange="setCommodityName();">
							<option value="">
								��ѡ��
							</option>
							<c:forEach items="${memberInfoList}" var="member">
								<option value="${member.id}"
									<c:if test="${obj.firmId==member.id }">selected="selected"</c:if> title ='${member.name }'>
									${member.name }
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="left" height="40">
						<div id="firmId_vTip"></div>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						��ʾȨ��:
					</td>
					<td>
						<select id="display" name="obj.display" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.display==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						�����н�:
					</td>
					<td>
						<select id="m_B_Open" name="obj.m_B_Open" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_B_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						�����н�:
					</td>
					<td>
						<select id="m_S_Open" name="obj.m_S_Open" style="width: 140">
							<c:forEach items="${authorityMap}" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_S_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>

				<tr height="20">
					<td align="right">
						������ƽ:
					</td>
					<td>
						<select id="m_B_Close" name="obj.m_B_Close" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_B_Close==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						������ƽ:
					</td>
					<td>
						<select id="m_S_Close" name="obj.m_S_Close" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_S_Close==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						����ֹ��:
					</td>
					<td>
						<select id="l_B_Open" name="obj.l_B_Open" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_B_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						����ֹ��:
					</td>
					<td>
						<select id="l_S_Open" name="obj.l_S_Open" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_S_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						����ֹ��:
					</td>
					<td>
						<select id="l_B_CloseLose" name="obj.l_B_CloseLose"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_B_CloseLose==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						����ֹ��:
					</td>
					<td>
						<select id="l_S_CloseLose" name="obj.l_S_CloseLose"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_S_CloseLose==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						����ֹӯ:
					</td>
					<td>
						<select id="l_B_CloseProfit" name="obj.l_B_CloseProfit"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_B_CloseProfit==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						����ֹӯ:
					</td>
					<td>
						<select id="l_S_CloseProfit" name="obj.l_S_CloseProfit"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_S_CloseProfit==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						����ֹ��:
					</td>
					<td>
						<select id="cancel_L_Open" name="obj.cancel_L_Open"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.cancel_L_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						����ֹ��:
					</td>
					<td>
						<select id="cancel_StopLoss" name="obj.cancel_StopLoss"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.cancel_StopLoss==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						����ֹӯ:
					</td>
					<td>
						<select id="cancel_StopProfit" name="obj.cancel_StopProfit"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.cancel_StopProfit==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						���Ʒ�ʽ:
					</td>
					<td>
						<select id="operate" name="obj.operate" style="width: 140">
							<option value="P" selected="selected">
								���Ի�
							</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
function myblur(userID){
	var flag = true;
	if("commodityId"==userID){
		flag = commodityId(userID);
	}else if("firmId"==userID){
		flag = firmId(userID)
	}else{
		if(!commodityId("commodityId")){
			flag =false;
		}
		if(!firmId("firmId")){
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
		innerHTML = "��ѡ����Ʒ";
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
	return flag;
}
function firmId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "��ѡ���ر��Ա";
		flag =false;
	}else{
		//innerHTML = "ѡ�����";
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	return flag;
}

function myfocus(userID){
/**	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	if("commodityId"==userID){
		innerHTML = "��ѡ����Ʒ";
	}
	if("firmId"==userID){
		innerHTML = "��ѡ���û�";
	}
	vTip.innerHTML=innerHTML;
	vTip.className="onFocus";*/
}
function setCommodityName() {
	var firmId = document.getElementById("firmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(commodityId && firmId){
		checkAction.existSpecialMemberTradeAuth(commodityId, firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("commodityId").value="";
				document.getElementById("firmId").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAdd(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("����������"+applyView.proposer+"��"+applyView.modTimeString+"�ύ");
						document.getElementById("commodityId").value="";
						document.getElementById("firmId").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	var m_commodityId = document.getElementById('commodityId');
	var m_commodityIdValue = m_commodityId.options[m_commodityId.selectedIndex].text;
	document.getElementById('commodityName').value = m_commodityIdValue;
	var m_FirmId = document.getElementById('firmId');
	var firmValue = m_FirmId.options[m_FirmId.selectedIndex].text;
	document.getElementById('firmName').value = firmValue;
}

</script>
<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String tradeMargin="�ɽ�����8%";
	String settleMargin="";
	String holidayMargin="";
%>
<div class="div_cxtj"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��Ա��֤��</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<tr height="40">
		<td align="right" width="20%">
			��Ʒ����:
		</td>
		<td width="30%">
			<input type="hidden" name="obj.commodityName" id="commodityName">
			<select id="commodityId" name="obj.commodityId" class="select_widmid" onblur="myblur('commodityId')"  onfocus="myfocus('commodityId')"
				onchange="setCommodityName();">
				<option value="">
					��ѡ��
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
			��Ա����:
		</td>
		<td>
			<input type="hidden" name="obj.firmName" id="firmName">
			<select id="firmId" name="obj.firmId" class="select_widmid" onblur="myblur('firmId')"  onfocus="myfocus('firmId')"
				onchange="setCommodityName();">
				<option value="">
					��ѡ��
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
						��֤���㷨:
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
						����ռ��/����:
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
						����ά��:
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
						����ά��:
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
			���Ʒ�ʽ:
		</td>
		<td>
			<select id="operate" name="obj.operate" class="select_widmid">
				<option value="P" selected="selected">
					���Ի�
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
	var firmId = document.getElementById("firmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(firmId){
		checkAction.existMemberMargin(commodityId, firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
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
		innerHTML = "��ѡ���Ա";
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
	var firmId = document.getElementById("firmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(commodityId){
		checkAction.existMemberMargin(commodityId, firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
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
	var str ="������>=0�ҡ�100������";
	var marg = document.getElementById("marginAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var flag = false;
	if(isEmpty(user.value+"")){
		innerHTML =  "����Ϊ��";
	}else if(marg==1){//�ٷֱ� 
			if(!flote(user.value,2)){
				innerHTML = "���2λС���ķǸ���";
			}else if(user.value<0 || user.value>100){
					innerHTML = str;
					}else {
						innerHTML = '<%=tradeMargin%>';
						flag = true;
						if(flag){
						vTip.className="onFocus";
						}
					}
			}else{//����ֵ
				if(user.value<0){
				innerHTML  =  "������>=0����";
				}else if(!flote(user.value,2)){
				innerHTML  ="���2λС���ķǸ���";
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
		innerHTML = "����Ϊ��";
	}else if(marg==1){//�ٷֱ� 
		if(!flote(user.value,2)){
		innerHTML = "�����λС��������";
	}else if(user.value<0 || user.value>100){
		innerHTML = "Ӧ��0��100֮��";
	}else{
		innerHTML = '<%=holidayMargin%>';
		flag = true;
	}	
	}else{//����ֵ
				if(!flote(user.value,4)){
				innerHTML  ="��λС�����ڵ�����";
				}else if(user.value<0||user.value>10000000){
				innerHTML = "Ӧ��0��10000000֮��";
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
		innerHTML = "����Ϊ��";
	}else if(marg==1){//�ٷֱ� 
		if(!flote(user.value,'2')){
		innerHTML = "�����������λС��������";
	}else if( (user.value<0 || user.value>100)){
		innerHTML = "Ӧ��0��100֮��";
	}else{
		innerHTML = '<%=settleMargin%>';
		flag = true;
	}
	}else{//����ֵ
		if(!flote(user.value,4)){
			innerHTML  ="��������λС�����ڵ�����";
		}else if(user.value<0||user.value>100000){
			innerHTML  =  "Ӧ��0-100000֮��";
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
		innerHTML = "��ѡ����Ʒ";
	}
	if("firmId"==userID){
		innerHTML = "��ѡ���û�";
	}
	if("marginAlgr"==userID){
		innerHTML = "��ѡ������";
	}
	if("tradeMargin"==userID){
		innerHTML = "����д����ռ��/����";
	}
	if("settleMargin"==userID){
		innerHTML = "����д����ά��";
	}
	if("holidayMargin"==userID){
		innerHTML = "����д����ά��";
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

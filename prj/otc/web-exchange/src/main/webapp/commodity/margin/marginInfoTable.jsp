<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
<%
	String tradeMargin="��ռ�ɽ����ı���";
	String settleMargin="";
	String holidayMargin="";
%>
<tr>
<td>
		<input type="hidden" name="obj.commodityId"
				value="${obj.commodityId}">
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<div class="div_cxtj"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;Ĭ�ϱ�֤��</div>
				<tr height="40">
					<td align="right" width="20%">
						��Ʒ����:
					</td>
					<td align="left" width="30%">
						<input type="text" id="name" class="input_text_pwdmin"
							name="obj.commodityName" value="${obj.commodityName}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right" width="20%">
						����:
					</td>
					<td colspan="2">
						<input type="hidden" name="obj.firmId" value="${obj.firmId}" />
						<input type="text" id="firmName" class="input_text_pwdmin"
							name="obj.firmName" value="${firmDisMap[obj.firmId]}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right" width="20%">
						��֤���㷨:
					</td>
					<td>
						<%--<select id="marginAlgr" name="obj.marginAlgr_v" onchange="changeAlgr(this.value)" class="select_widmid" >
							<c:forEach items="${commodityMarginAlgrMap }" var="maps">
								<option value="${maps.key}"<c:if test="${maps.key==obj.marginAlgr_v}">selected=selected</c:if> >${maps.value}</option>
							</c:forEach>
							<option value="1">����</option>
						</select>--%>
						<input type="hidden" class="input_text"  id="marginAlgr" name="obj.marginAlgr_v" value="1" >
						<input type="text" class="input_text"  id="marginAlgr_v" name="obj.marginAlgr_vv" value="����" readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right" width="20%">
						����ռ��/����:
					</td>
					<td>
						<input type="text" class="input_text" id="tradeMargin" style="text-align:right"
							name="obj.tradeMargin_v" value="${obj.tradeMargin_v}" onblur="myblur('tradeMargin')"  onfocus="myfocus('tradeMargin')"><span id="margin2"></span>
					</td>
					<td align="left" height="40"><div id="tradeMargin_vTip" class="onFocus"><%= tradeMargin%></div></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">

function changeAlgr(fee) {
	if (fee == '1') {
		document.getElementById("margin2").innerHTML = "%";
		var vTip = document.getElementById("tradeMargin_vTip");
		vTip.innerHTML="<%= tradeMargin%>";
		vTip.className ="onFocus";
		//document.getElementById("margin3").innerHTML = "%";
		//document.getElementById("margin4").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("margin2").innerHTML = "";
		var vTip = document.getElementById("tradeMargin_vTip");
		vTip.innerHTML="";
		vTip.className ="";
		//document.getElementById("margin3").innerHTML = "";
		//document.getElementById("margin4").innerHTML = "";
	}
}
function myblur(userID){
	var flag = true;
	 if("tradeMargin"==userID){
		flag = tradeMargin(userID);
	}
	 /**else if("settleMargin"==userID){
		flag = settleMargin(userID);
	}else if("holidayMargin"==userID){
		flag = holidayMargin(userID);
	}*/else{
		
		if(!tradeMargin("tradeMargin")){
			flag = false;
		}
		
		/**
		 * if(!settleMargin("settleMargin")){
			flag = false;
		}if(!holidayMargin("holidayMargin")){
			flag = false;
		}*/
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
		innerHTML = "�����������λС��������";
	}else if(user.value<0 || user.value>100){
		innerHTML = "Ӧ��0��100֮��";
	}else{
		innerHTML = '<%=holidayMargin%>';
		flag = true;
	}	
	}else{//����ֵ
				if(!flote(user.value,4)){
				innerHTML  ="��������λС�����ڵ�����";
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
	vTip.className="onFocus";
	*/
}
</script>
<script type="text/javascript" defer="defer">
    changeAlgr('${obj.marginAlgr_v}');
</script>
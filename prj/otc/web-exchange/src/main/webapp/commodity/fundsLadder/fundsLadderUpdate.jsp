<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String stepRate="";
%>
<html>
	<head><title> Ĭ�ϳ�����ֵ����</title>
	</head>
	<body class="st_body">
		<form name="frm" action="${basePath}/commodity/fundsLadder/update.action" method="post" targetType="hidden">			
			<div>
			<table border="0" width="100%" align="center">
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��ϸ��Ϣ</div>
				<tr>
				<td>
				<table border="0" cellspacing="0" cellpadding="4" width="90%"
					align="center" class="st_bor">
					<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				Ĭ�ϳ�����ֵ����
			</div>
					<tr height="40">
						<input type="hidden" name="special.memberNo" value="${fundsLadderVO.memberNo}">
						<td align="right" width="33%">
							����:
						</td>
						<td width="30%">
							<input class="input_text_pwdmin" type="text" id="memberName"
								name="special.memberName" value="${fundsLadderVO.memberName }"
								readonly="readonly">
						</td>
					</tr>
					<c:forEach items="${fundsLadderVO.fundsLadderList}" var="fundsLadder">
						<tr height="40">
								<c:forEach var="fundder" items="${ladderList}">
										<c:if test="${fundder.stepNo==fundsLadder.stepNo}">
											<c:set var="note" value="${fundder.note}"></c:set>
										</c:if>
									</c:forEach>
							<td align="right">${note}�ĳ�����ֵ����:	</td>
							<td>
							   <input class="input_text" type="text" id="stepRate${fundsLadder.stepNo}"  style="text-align:right" 
							   onblur="myblur('stepRate${fundsLadder.stepNo}')"  onfocus="myfocus('stepRate${fundsLadder.stepNo}')"
								name="special.stepRate_v" value="${fundsLadder.stepRate_v}">%<strong class="check_input">&nbsp;*</strong>
							</td>
							<td align="left" height="40" width="45%"><div id="stepRate${fundsLadder.stepNo}_vTip"  ><%=stepRate%></div></td>
						</tr>
					</c:forEach>
					</table>
				</td>
			</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" >
				<tr>
					<td align="center">
						<button  class="btn_sec" id="update" onclick="updateFundsLadder()">����</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onclick="window.close()">�ر�</button>
					</td>
				</tr>
			</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
function myblur(userID){
	var number = document.getElementsByName("special.stepRate_v").length;
	var flag = true;
	if(userID != null && userID.indexOf('stepRate')>=0){
		flag = delayFee(userID);
	}else{
		for(var i=1;i<=number;i++){
			if(!delayFee("stepRate"+i)){
				flag = false;
			}
		}
	}
	return flag;
}
function delayFee(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "����Ϊ��";
	}else if(!flote(user.value,2)){
		innerHTML = "���2λС��������";
	}else if(user.value<=0 || user.value>100){
		innerHTML = "������>0�ҡ�100������";
	}else{
		innerHTML = "<%=stepRate%>";
		flag = true;
	}
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	return flag;
}
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className="onFocus";*/
}
function updateFundsLadder() {
	var flag = myblur("all");
		if(!flag){return false;}
	if(!isFormChanged(null,null)){
			alert("û���޸�����");
			return false;}
	var stepRates=document.getElementsByName("stepRate");
	for(var i=0;i<stepRates.length;i++){
		if(stepRates[i].value==''){
			var num=i+1;
			alert('��'+num+'����ֵ������Ϊ��');
			stepRates[i].focus();
			return false;
		}
		if(isNaN(stepRates[i].value)){
			var num=i+1;
			alert('��'+num+'����ֵӦΪ����');
			stepRates[i].focus();
			return false;
		}
		
	}
	var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
}
</script>
<%@ include file="/public/footInc.jsp"%>
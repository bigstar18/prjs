<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String stepValue="";
%>
<html>
	<head><title>��Ա�ͻ��ʽ����</title>
	</head>
	<body class="st_body">
		<form name="frm" action="${basePath}/commodity/stepFundsDictionary/update.action"
			method="post" targetType="hidden">
				<input type="hidden" name="obj.ladderCode" value="${obj.ladderCode }">
				<input type="hidden" name="obj.stepNo" value="${obj.stepNo }">
				<div>
			<table border="0" width="80%" align="center">
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��ϸ��Ϣ</div>
				<tr>
					<td>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
							<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				��Ա�ʽ����
			</div>
				<!--	<tr height="35">
						<td align="right" width="38%">��������: 
						</td>
						<td>
							<input class="input_text" type="text" id="id" name="id" value="��Ա�ʽ����" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>-->
					<tr height="35">
						<td align="right">���ݽ׺�: 
						</td>
						<td>
							<!-- <select style="width: 120" name="id2" disabled="disabled">
								<option value="${obj.stepNo }" selected="selected">${obj.stepNo }</option>
						</select> -->
						<input class="input_text_pwdmin" type="text" id="no" name="obj.no" value="${obj.stepNo }" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							����ֵ:
						</td>
						<td>
							<input class="input_text" type="text" style="text-align:right"
							onblur="myblur('stepValue')"  onfocus="myfocus('stepValue')" 
							id="stepValue" name="obj.stepValue" value="${obj.stepValue}" >
						</td>
						<td align="left" height="40"><div id="stepValue_vTip"  ><%=stepValue%></div></td>
					</tr>
				</table>
			</td>
			</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<button  class="btn_sec" id="update" onclick="updateStepDictionary()">�޸�</button>
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
	var flag = true;
	if(userID != "stepValue"){
		flag = delayFee(userID);
	}else{
		if(!delayFee("stepValue")){
				flag = false;
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
	}else if(user.value<0 || user.value>10000000000){
		innerHTML = "Ӧ��0��10000000000֮��";
	}else{
		innerHTML = "<%=stepValue%>";
		flag = true;
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
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className="onFocus";*/
}
	function updateStepDictionary(){
		var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}


</script>
<%@ include file="/public/footInc.jsp"%>
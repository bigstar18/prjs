<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>

<html>
	<head>
		<title></title>
	</head>

	<body>
		<form id="formNew" name="frm" method="POST"
			action="<%=basePath%>tradeTimeController.zcjs?funcflg=tradeTimeMod">
			<fieldset width="100%">
				<legend>
					���׽���Ϣ�༭
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
						<td align="right" width="40%">
							���׽����:
						</td>
						<td align="left" width="60%">
							<input class="text" name="serialNumber" value="${tradeTime.serialNumber }" style="width: 150px;"
								reqfv="required;���׽����" readonly="readonly">
							&nbsp;
							<font color="red">*</font>&nbsp;
						</td>
					</tr>
					<tr height="30">
						<td align="right" width="40%">
							����ʱ�䣺
						</td>
						<td align="left" width="60%">
							<input class="text" name="startTime" value="${tradeTime.startTime }"
								style="width: 150px;">
							&nbsp;
							<font color="red">*(��:08:30)</font>&nbsp;
						</td>
					</tr>
					<tr height="30">
						<td align="right" width="40%">
							����ʱ��:
						</td>
						<td align="left" width="60%">
							<input class="text" name="endTime" value="${tradeTime.endTime }"
								style="width: 150px;">
							&nbsp;
							<font color="red">*(��:10:30)</font>&nbsp;
						</td>
					</tr>
					<tr height="30">

						<td colspan="2">
							<div align="center">
								<button class="smlbtn" type="button" onclick="doSubmit();">
									ȷ��
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="smlbtn" type="button" onclick="freturn()">
									����
								</button>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
<script>
	function doSubmit()
		{
			if(frm.startTime.value == "")
		{
			alert("����ʱ�䲻��Ϊ�գ�");
			frm.startTime.focus();
			return;
		}
		else if(frm.endTime.value == "")
		{
			alert("����ʱ�䲻��Ϊ�գ�");
			frm.spanTime.focus();
			return;
		}
		else if(!dateChk1(frm.startTime.value))
		{
			alert("����ʱ���ʽ����");
			frm.startTime.focus();
			return;
		}else if(!dateChk1(frm.endTime.value))
		{
			alert("����ʱ���ʽ����");
			frm.endTime.focus();
			return;
		}
		if(confirm("ȷ��ִ�д˲�����")){
			frm.submit();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>tradeTimeController.zcjs?funcflg=tradeTimeList";
		frm.submit();
	}
	function dateChk1(str){ //hh:mm
		var reg = /^(\d{2})\:(\d{2})$/;
		var r = str.match(reg);
		if(r==null){
			return false; 
		}else{
			if(r[1] < 0 || r[1] >= 24){
			return false;
		}
		if(r[2] < 0 || r[2] >= 60){
			return false;
			}
		}
		return true; 
	} 
</script>

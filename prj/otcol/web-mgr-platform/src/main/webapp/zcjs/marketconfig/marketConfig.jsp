<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<script type="text/javascript" src="javascript/common.js"></script>
		<%@ include file="../public/session.jsp"%>
	</head>
	<body onload="alertMsg()">
		<form name="frm" method="post" action="<%=basePath%>/systemConfigController.zcjs?funcflg=saveSystemConfig">
			<input type="hidden" name="marketId" value="${marketStatus.marketId }"/>
			<fieldset width="60%">
				<legend>
					�г���������
				</legend>
				<table align="center">
					<tr>
						<td align="right">
							ÿ&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�
						</td>
						<td align="center">
							<input type="text" name="weekRest" id="weekRest"
								value="${tradeRestDate.weekRest }" />
						</td>
						<td align="left">
							����6,7
						</td>
					</tr>
					<tr>
						<td align="right">
							��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�գ�
						</td>
						<td align="center">
							<input type="text" name="yearRest" id="yearRest"
								value="${tradeRestDate.yearRest }" />
						</td>
						<td align="left">
							����0501,0502,1001
						</td>
					</tr>
					<tr>
						<td align="right">
							��&nbsp;&nbsp;��&nbsp;��&nbsp;Ч&nbsp;&nbsp;�ڣ�
						</td>
						<td align="center">
							<input type="text" name="submitEndTime" id="submitEndTime"
								value="${marketConfig.submitEndTime }" />
						</td>
						<td align="left">
							����1,2,3
						</td>
					</tr>
					<tr>
						<td align="right">
							��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;״&nbsp;&nbsp;&nbsp;����
						</td>
						<td>
						<select align="center" id="financeStatus" name="financeStatus">
								<option value="1"
									<c:if test="${ marketConfig.financeStatus==1}"> selected="selected"</c:if>>
									 &nbsp; &nbsp; &nbsp; &nbsp; ����&nbsp;&nbsp; &nbsp; &nbsp;  
								</option>
								<option value="2"
									<c:if test="${ marketConfig.financeStatus==2}"> selected="selected"</c:if>>
									  &nbsp; &nbsp; &nbsp; &nbsp;������&nbsp;&nbsp;  &nbsp;
								</option>
							</select>
						</td>
						<td align="left"> 
						
						</td>
					</tr>
					<tr>
						<td align="right">
							����ģʽ�Ƿ��Զ���
						</td>
						<td align="center">
							<select align="center" id="isAuto" name="isAuto">
								<option value="Y"
									<c:if test="${ marketStatus.isAuto=='Y'}"> selected="selected"</c:if>>
									 &nbsp; &nbsp; &nbsp; &nbsp; �Զ�&nbsp;&nbsp; &nbsp; &nbsp;  
								</option>
								<option value="N"
									<c:if test="${ marketStatus.isAuto=='N'}"> selected="selected"</c:if>>
									 &nbsp; &nbsp; &nbsp; &nbsp; �ֶ�&nbsp;&nbsp; &nbsp; &nbsp;
								</option>
							</select>
						</td>
						<td align="left">

						</td>
					</tr>
				</table>
				<table align="center">
					<tr>
						<td align="left">
							<input type="button" value="����" onclick="save()"/>
						</td>
						<td>
							&nbsp; &nbsp; &nbsp;
						</td>
						<td align="left">
							<input type="button" value="ͬ��" onclick="settingSame()"/>
						</td>
						<td>
							&nbsp; &nbsp; &nbsp;
						</td>
						<td align="left">
							<input type="reset" value="����"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>

	</body>
</html>
<script type="text/javascript">

function alertMsg(){
	var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
	}
}
function SubmitEndTimetest(str){
	var number=str.split(',');
	var s=number.join(',')+",";
	for(var i=0;i<number.length;i++){
		if(s.replace(number[i]+',',"").indexOf(number[i]+',')>-1){
			document.getElementById("submitEndTime").focus();
			return true;
		}
	}
}

function save(){
	var week=document.getElementById("weekRest").value;
	if(week!=null&&week!=""){
		if(week.charAt(0)==','||week.charAt(week.length-1)==','){
		alert("ÿ����Ϣ���������ֿ�ʼ�ͽ�����");
		return false;
		}
		for(var i=0;i<week.length;i++){
			if((week.charAt(i)<'0'||week.charAt(i)>'7')&&week.charAt(i)!=','){
				alert("ÿ����Ϣ�հ����Ƿ��ַ���");
				return false;
			}
		}
	}
	
	var year=document.getElementById("yearRest").value;
	if(year!=null&&year!=""){
		if(year.charAt(0)==','||year.charAt(year.length-1)==','){
		alert("�ڼ����������ֿ�ʼ�ͽ�����");
		return false;
		}
		var flag="false";
		var arr=year.split(",");
		for(var i=0;i<arr.length;i++){
			if(arr[i].length!=4){
		
			alert("�ڼ��ո�ʽ����ȷ,��ȷ�ϣ�");
			document.getElementById("yearRest").focus();
			return false;
			}
		}
		for(var i=0;i<year.length;i++){
			if((year.charAt(i)<'0'||year.charAt(i)>'9')&&year.charAt(i)!=','){
				alert("�ڼ��ո�ʽ�����Ƿ��ַ���");
				return false;
			}
		}
	}
	
	var submitEndTime=document.getElementById("submitEndTime").value;
		if(submitEndTime.charAt(0)==','||submitEndTime.charAt(submitEndTime.length-1)==','){
		alert("������Ч���������ֿ�ʼ�ͽ�����");
		return false;
	}
	for(var i=0;i<submitEndTime.length;i++){
		if((submitEndTime.charAt(i)<'0'||submitEndTime.charAt(i)>'9')&&submitEndTime.charAt(i)!=','){
			alert("������Ч�ڰ����Ƿ��ַ���");
			return false;
		}
	}
	
	if(SubmitEndTimetest(submitEndTime)){
		alert("�ҵ���Ч�ڴ����ظ����ڣ����޸ģ�");
		return false;
	}

	if(frm.weekRest.value != "")
	{
		var b = true;
		if(isNaN(frm.weekRest.value))
		{
			var v1 = frm.weekRest.value;
			var v = v1.split(",");
			
			if(v != null && v.length > 0)
			{
				for(var i=0;i<v.length;i++)
				{	
					if(isNaN(v[i]))
					{
						b = false;
						break;
					}
				}
			}
			else
			{
				b = false;
			}
		}
		if(!b)
		{
			alert("ÿ����Ϣ�ո�ʽ����");
			frm.weekRest.focus();
			return false;
		}		
	}
	if(frm.yearRest.value != "")
	{
		
		var b = true;
		if(isNaN(frm.yearRest.value))
		{
			var v1 = frm.yearRest.value;
			var v = v1.split(",");
			if(v != null && v.length > 0)
			{
				for(var i=0;i<v.length;i++)
				{
					if(isNaN(v[i]))
					{
						b = false;
						break;
					}
				}
			}
			else
			{
				b = false;
			}
		}
		if(!b)
		{
			alert("�ڼ��ո�ʽ����");
			frm.yearRest.focus();
			return false;
		}		
	}		
	if(confirm("ȷ���޸��г�����������?")){
		frm.submit();
	}
}

function settingSame() {
		var week=document.getElementById("weekRest").value;
		if(week!=null&&week!=""){
			if(week.charAt(0)==','||week.charAt(week.length-1)==','){
				alert("ÿ����Ϣ���������ֿ�ʼ�ͽ�����");
				return false;
			}
			for(var i=0;i<week.length;i++){
				if((week.charAt(i)<'0'||week.charAt(i)>'7')&&week.charAt(i)!=','){
					alert("ÿ����Ϣ�հ����Ƿ��ַ���");
					return false;
				}
			}
		}
	
	var year=document.getElementById("yearRest").value;
	if(year!=null&&year!=""){
		if(year.charAt(0)==','||year.charAt(year.length-1)==','){
		alert("�ڼ����������ֿ�ʼ�ͽ�����");
		return false;
	}
	var flag="false";
	var arr=year.split(",");
	for(var i=0;i<arr.length;i++){
		if(arr[i].length!=4){
		
		alert("�ڼ��ո�ʽ����ȷ,��ȷ�ϣ�");
		document.getElementById("yearRest").focus();
		return false;
	}
	}
	
	for(var i=0;i<year.length;i++){
		if((year.charAt(i)<'0'||year.charAt(i)>'9')&&year.charAt(i)!=','){
			alert("�ڼ��ո�ʽ�����Ƿ��ַ���");
			return false;
		}
	}
	}
	

	if(frm.weekRest.value != "")
	{
		var b = true;
		if(isNaN(frm.weekRest.value))
		{
			var v1 = frm.weekRest.value;
			var v = v1.split(",");
			
			if(v != null && v.length > 0)
			{
				for(var i=0;i<v.length;i++)
				{	
					if(isNaN(v[i]))
					{
						b = false;
						break;
					}
				}
			}
			else
			{
				b = false;
			}
		}
		if(!b)
		{
			alert("ÿ����Ϣ�ո�ʽ����");
			frm.weekRest.focus();
			return false;
		}		
	}
	if(frm.yearRest.value != "")
	{
		var b = true;
		if(isNaN(frm.yearRest.value))
		{
			var v1 = frm.yearRest.value;
			var v = v1.split(",");
			if(v != null && v.length > 0)
			{
				for(var i=0;i<v.length;i++)
				{
					if(isNaN(v[i]))
					{
						b = false;
						break;
					}
				}
			}
			else
			{
				b = false;
			}
		}
		if(!b)
		{
			alert("�ڼ��ո�ʽ����");
			frm.yearRest.focus();
			return false;
		}		
	}
	if(confirm("ȷ��ͬ����?")){
		frm.action = "<%=basePath%>/systemConfigController.zcjs?funcflg=settingSame";
		frm.submit();
	}
}
</script>



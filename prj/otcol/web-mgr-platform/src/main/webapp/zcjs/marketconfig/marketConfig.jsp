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
					市场配置设置
				</legend>
				<table align="center">
					<tr>
						<td align="right">
							每&nbsp;&nbsp;周&nbsp;&nbsp;休&nbsp;息&nbsp;日：
						</td>
						<td align="center">
							<input type="text" name="weekRest" id="weekRest"
								value="${tradeRestDate.weekRest }" />
						</td>
						<td align="left">
							例：6,7
						</td>
					</tr>
					<tr>
						<td align="right">
							节&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;假&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日：
						</td>
						<td align="center">
							<input type="text" name="yearRest" id="yearRest"
								value="${tradeRestDate.yearRest }" />
						</td>
						<td align="left">
							例：0501,0502,1001
						</td>
					</tr>
					<tr>
						<td align="right">
							挂&nbsp;&nbsp;单&nbsp;有&nbsp;效&nbsp;&nbsp;期：
						</td>
						<td align="center">
							<input type="text" name="submitEndTime" id="submitEndTime"
								value="${marketConfig.submitEndTime }" />
						</td>
						<td align="left">
							例：1,2,3
						</td>
					</tr>
					<tr>
						<td align="right">
							财&nbsp;&nbsp;&nbsp;务&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;况：
						</td>
						<td>
						<select align="center" id="financeStatus" name="financeStatus">
								<option value="1"
									<c:if test="${ marketConfig.financeStatus==1}"> selected="selected"</c:if>>
									 &nbsp; &nbsp; &nbsp; &nbsp; 公开&nbsp;&nbsp; &nbsp; &nbsp;  
								</option>
								<option value="2"
									<c:if test="${ marketConfig.financeStatus==2}"> selected="selected"</c:if>>
									  &nbsp; &nbsp; &nbsp; &nbsp;不公开&nbsp;&nbsp;  &nbsp;
								</option>
							</select>
						</td>
						<td align="left"> 
						
						</td>
					</tr>
					<tr>
						<td align="right">
							运行模式是否自动：
						</td>
						<td align="center">
							<select align="center" id="isAuto" name="isAuto">
								<option value="Y"
									<c:if test="${ marketStatus.isAuto=='Y'}"> selected="selected"</c:if>>
									 &nbsp; &nbsp; &nbsp; &nbsp; 自动&nbsp;&nbsp; &nbsp; &nbsp;  
								</option>
								<option value="N"
									<c:if test="${ marketStatus.isAuto=='N'}"> selected="selected"</c:if>>
									 &nbsp; &nbsp; &nbsp; &nbsp; 手动&nbsp;&nbsp; &nbsp; &nbsp;
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
							<input type="button" value="保存" onclick="save()"/>
						</td>
						<td>
							&nbsp; &nbsp; &nbsp;
						</td>
						<td align="left">
							<input type="button" value="同步" onclick="settingSame()"/>
						</td>
						<td>
							&nbsp; &nbsp; &nbsp;
						</td>
						<td align="left">
							<input type="reset" value="重置"/>
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
		alert("每周休息日请以数字开始和结束！");
		return false;
		}
		for(var i=0;i<week.length;i++){
			if((week.charAt(i)<'0'||week.charAt(i)>'7')&&week.charAt(i)!=','){
				alert("每周休息日包含非法字符！");
				return false;
			}
		}
	}
	
	var year=document.getElementById("yearRest").value;
	if(year!=null&&year!=""){
		if(year.charAt(0)==','||year.charAt(year.length-1)==','){
		alert("节假日请以数字开始和结束！");
		return false;
		}
		var flag="false";
		var arr=year.split(",");
		for(var i=0;i<arr.length;i++){
			if(arr[i].length!=4){
		
			alert("节假日格式不正确,请确认！");
			document.getElementById("yearRest").focus();
			return false;
			}
		}
		for(var i=0;i<year.length;i++){
			if((year.charAt(i)<'0'||year.charAt(i)>'9')&&year.charAt(i)!=','){
				alert("节假日格式包含非法字符！");
				return false;
			}
		}
	}
	
	var submitEndTime=document.getElementById("submitEndTime").value;
		if(submitEndTime.charAt(0)==','||submitEndTime.charAt(submitEndTime.length-1)==','){
		alert("挂牌有效期请以数字开始和结束！");
		return false;
	}
	for(var i=0;i<submitEndTime.length;i++){
		if((submitEndTime.charAt(i)<'0'||submitEndTime.charAt(i)>'9')&&submitEndTime.charAt(i)!=','){
			alert("挂牌有效期包含非法字符！");
			return false;
		}
	}
	
	if(SubmitEndTimetest(submitEndTime)){
		alert("挂单有效期存在重复日期，请修改！");
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
			alert("每周休息日格式错误！");
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
			alert("节假日格式错误！");
			frm.yearRest.focus();
			return false;
		}		
	}		
	if(confirm("确定修改市场配置设置吗?")){
		frm.submit();
	}
}

function settingSame() {
		var week=document.getElementById("weekRest").value;
		if(week!=null&&week!=""){
			if(week.charAt(0)==','||week.charAt(week.length-1)==','){
				alert("每周休息日请以数字开始和结束！");
				return false;
			}
			for(var i=0;i<week.length;i++){
				if((week.charAt(i)<'0'||week.charAt(i)>'7')&&week.charAt(i)!=','){
					alert("每周休息日包含非法字符！");
					return false;
				}
			}
		}
	
	var year=document.getElementById("yearRest").value;
	if(year!=null&&year!=""){
		if(year.charAt(0)==','||year.charAt(year.length-1)==','){
		alert("节假日请以数字开始和结束！");
		return false;
	}
	var flag="false";
	var arr=year.split(",");
	for(var i=0;i<arr.length;i++){
		if(arr[i].length!=4){
		
		alert("节假日格式不正确,请确认！");
		document.getElementById("yearRest").focus();
		return false;
	}
	}
	
	for(var i=0;i<year.length;i++){
		if((year.charAt(i)<'0'||year.charAt(i)>'9')&&year.charAt(i)!=','){
			alert("节假日格式包含非法字符！");
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
			alert("每周休息日格式错误！");
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
			alert("节假日格式错误！");
			frm.yearRest.focus();
			return false;
		}		
	}
	if(confirm("确定同步吗?")){
		frm.action = "<%=basePath%>/systemConfigController.zcjs?funcflg=settingSame";
		frm.submit();
	}
}
</script>



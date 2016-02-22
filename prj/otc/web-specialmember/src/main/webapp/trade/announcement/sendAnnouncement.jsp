<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>系统用户浏览</title>

	</head>
	<body>
		<div id="main_body">
			<table width="60%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td>
						<div class="div_cxtjd">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;发送公告
						</div>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/tradeManage/announcement/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table width="750" border="0" cellspacing="0"
													cellpadding="0" style="padding-top: 15px;">
													<tr>
														<td height="10" colspan="2">
															&nbsp;
														</td>
													</tr>

													<tr>
														<td align="right">
															主题：&nbsp;
														</td>
														<td>
															<textarea rows="" cols="37" name="notice.title" id="title" 
															onblur="myblur('title')"	onfocus="myfocus('title')"
															onkeydown="checkTitle()" onkeyup="checkTitle()">${notice.title}</textarea>
															<span style="width:150px;" class="check_input"><div id="title_vTip">*(最多输入32个字)&nbsp;</div></span>
														</td>
													</tr>
													<tr>
														<td align="right" width="30%">
															<font color="gray">发送者：&nbsp; </font>
														</td>
														<td>
															<input type="input_text" name="notice.author" id="author" class="input_textmax" 
																value="${user.name}" size="50" readonly="readonly" />
														</td>
													</tr>
													<tr>
														<td align="right">
															有效天数：&nbsp;
														</td>
														<td>
															<input size="30" class="input_text"
															onblur="myblur('expiryTime')"	onfocus="myfocus('expiryTime')"
															 name="notice.expiryDates" id="expiryTime"
																value="${notice.expiryDates }" />
															<span style="width:180px;"><div id="expiryTime_vTip"><strong class="check_input">&nbsp;*</strong></div></span>
														</td>
													</tr>

													<tr>
														<td align="right">
															公告内容：&nbsp;
															<br />
														</td>
														<td>
															<textarea rows="10" cols="50" name="notice.content"
															onblur="myblur('content')"	onfocus="myfocus('content')"
																id="content" onkeydown="checkContent()" onkeyup="checkContent()">${notice.content}</textarea>
															<span style="width:95px;"><div id="content_vTip"><strong class="check_input">*(最多输入256个字)&nbsp;</strong></div></span>
														</td>
													</tr>
													<tr>
														<td colspan="2" height="10">
															&nbsp;
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
				</tr>
			</table>
			<table width="100%">
				<tr align="center">
					<td>
						<button class="btn_sec" onclick="choice();">
							下一步
						</button>
						&nbsp;&nbsp;
						<button class="btn_cz" onclick="resetValue();">
							重置
						</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
<script type="text/javascript">
function resetValue() {
	frm.title.value = "";
	frm.expiryTime.value = "";
	frm.content.value = "";
}
function myblur(userID) {
	var flag = true;

	if ("title" == userID) {
		flag = title(userID);
	} else if ("expiryTime" == userID) {
		flag = expiryTime(userID);
	} else if ("content" == userID) {
		flag = content(userID);
	} else {
		if (!title('title'))
			flag = false;
		if (!expiryTime("expiryTime"))
			flag = false;
		if (!content("content"))
			flag = false;
	}
	return flag;
}

function title(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if(isEmpty(user.value + "")) {
	innerHTML = "不能为空";
	}else {
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function expiryTime(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if(isEmpty(user.value + "")) {
	innerHTML = "不能为空";
	}else if (!integer(user.value)){
	innerHTML = "只能为正整数";
	}else {
		if(user.value<1||user.value>365){
			innerHTML = "有效天数应在365天以内";
		}else{flag = true;}
		
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	return flag;
}function content(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if(isEmpty(user.value + "")) {
	innerHTML = "不能为空";
	}else {
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function checkTitle() {
	var max = 32;
	var cur = document.getElementById('title').value.length;
	if (cur > max){
	   document.getElementById('title').value = document.getElementById('title').value.substring(0, max);
	} 
}
function checkContent(){
	var max = 256;
	var cur = document.getElementById('content').value.length;
	if (cur > max){
	   document.getElementById('content').value = document.getElementById('content').value.substring(0, max);
	} 
}
function choice() {
	if(!myblur("all")){return false;}
	var obj = document.getElementById("title").value;
	if (!isNull(obj)) {
		alert("主题不能为空！");
		frm.title.focus();
		return false;
	}
	var obj = document.getElementById("expiryTime").value;
	if (!isNull(obj)) {
		alert("有效期限不能为空！");
		frm.expiryTime.focus();
		return false;
	}
	var obj = document.getElementById("content").value;
	if (!isNull(obj)) {
		alert("正文不能为空！");
		frm.content.focus();
		return false;
	}
	if (isNaN(frm.expiryTime.value)) {
    	alert("有效天数请输入数字!");
    	frm.expiryTime.focus();
      	return false;
    }
	if (frm.expiryTime.value<=0) {
		alert("有效天数应大于0!");
		frm.expiryTime.focus();
		return false;
	}
	frm.submit();
}
</script>

<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>系统用户浏览</title>

	</head>
	<body>
		<div id="main_body">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="table1_style">
				<tr>
					<td>
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;发送公告
						</div>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/tradeManage/announcement/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" width="90%">
									<tr>
										<td>
											<div class="div2_top">
												<table width="95%" border="0" cellspacing="0" class="table3_style" 
													cellpadding="0" style="padding-top: 15px;">
													<tr>
														<td align="left" width="80">
															<font color="gray">操&nbsp;&nbsp;作&nbsp;员：&nbsp; </font>
														</td>
														<td align="left">
															<input type="text" name="notice.author" id="author"
																class="input_textmax" value="${user.id}" size="50" style="width: 800;" 
																readonly="readonly" />
														</td>
													</tr>
													<tr>
														<td align="left" width="80">
															<font color="gray">发布机构： </font>
														</td>
														<td align="left">
															<c:if test="${notice.authorOrganization != null}">
																<input type="text" name="notice.authorOrganization" id="author" readonly="readonly"
																	class="input_textmax" value="${notice.authorOrganization}" size="50" style="width: 800;" />
															</c:if>
															<c:if test="${notice.authorOrganization == null}">
																<input type="text" name="notice.authorOrganization" id="author" readonly="readonly"
																	class="input_textmax" value="${memberName}" size="50" style="width: 800;" />
															</c:if>
														</td>
													</tr>
													<tr>
														<td align="left">
															主&nbsp;&nbsp;&nbsp;&nbsp;题：&nbsp;
														</td>
														<td align="left">
															<input type="text" name="notice.title" value="${notice.title}" id="title" onblur="myblur('title')" 
																onfocus="myfocus('title')" style="width: 800;"  onkeydown="checkTitle()" onkeyup="checkTitle()"/>
															<span style="width:150px;"><div id="title_vTip"><strong class="check_input">*(最多输入32个字)&nbsp;</strong></div></span>
														</td>
													</tr>
													<tr>
														<td align="left">
															有效日期：&nbsp;
														</td>
														<td align="left">
															<c:if test="${notice.expiryTime != null}">
																<fmt:formatDate pattern="yyyy-MM-dd" value="${notice.expiryTime}" var="dteObject" />
																<input type="hidden" name="notice.expiryTime" value="${dteObject}" />
																<input type="text" style="width: 100px" id="expiryTime"
																	class="wdate" maxlength="10"
																	name="notice.expiryTime"
																	value='${dteObject }'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</c:if>
															<c:if test="${notice.expiryTime == null}">
																<input type="text" style="width: 100px" id="expiryTime"
																	class="wdate" maxlength="10"
																	name="notice.expiryTime"
																	value='${notice.expiryTime }'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</c:if>
															<span style="width:180px;"><div id="expiryTime_vTip"><strong class="check_input">&nbsp;*</strong></div></span>
														</td>
													</tr>

													<tr>
														<td align="left">
															公告内容：&nbsp;
														</td>
														<td align="left">
															<textarea rows="10" cols="112" name="notice.content".
															onblur="myblur('content')"	onfocus="myfocus('content')"
																id="content" onkeydown="checkContent()" onkeyup="checkContent()">${notice.content}</textarea>
															<span style="width:145px;"><div id="content_vTip"><strong class="check_input">*(最多输入800个字)&nbsp;</strong></div></span>
														</td>
													</tr>
													<tr>
														
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

			<table width="600px" align="center">
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
	} else if ("content" == userID) {
		flag = content(userID);
	} else {
		if (!title('title'))
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


function content(userID) {
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
	var max = 800;
	var cur = document.getElementById('content').value.length;
	if (cur > max){
	   document.getElementById('content').value = document.getElementById('content').value.substring(0, max);
	} 
}
function choice() {
	var now = new Date();
	var nowDate=new Date(Date.parse(now.format("yyyy-MM-dd").replace(/-/g,   "/")));
	var s = new Date(Date.parse(frm.expiryTime.value.replace(/-/g,   "/")));
	if(!myblur("all")){return false;}
	if(frm.expiryTime.value == ""){
		alert("有效日期不能为空！");
		frm.expiryTime.focus();
		return false;
	} else if (s < nowDate){
		alert("有效日期必须大于等于当前日期！");
		frm.expiryTime.focus();
		return false;
	}
	frm.submit();
}
Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
function myfocus(){
	
}
</script>

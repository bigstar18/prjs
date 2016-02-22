<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
var rightMap = ${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
	defer="defer">
</script>
<script type='text/javascript' src='<%=projectPath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=projectPath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=projectPath%>/dwr/interface/checkAction.js' />
</script>
<script type="text/javascript" src="<%=basePath%>/public/checkPW.js"></script>
<body style="overflow-y: hidden">
	<head>
		<title>添加系统用户</title>
	</head>
	<form name="frm" id="frm" method="post" targetType="hidden">
		<div style="overflow: auto; height: 300px;">
			<table border="0" width="95%" align="center">
				<tr>
					<td>
						<div class="st_title">
							&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;添加系统用户
						</div>
						<input type="hidden" name="obj.type" value="ADMIN">
						<table border="0" cellspacing="0" cellpadding="0" width="90%"
							align="center" class="st_bor">
							<input type="hidden" id="keyCode" name="obj.keyCode"
								value="0123456789ABCDE" style="width: 150px;">
							<input type="hidden" name="obj.memberInfo.s_memberNo" value="${REGISTERID }">
							<input type="hidden" name="obj.userId" id="userAll">
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									用户代码 ：
								</td>
								<td align="left" width="45%">
									<input type="text" id="userTop" readonly="readonly"
								style="width: 40px; background-color: #bebebe" value="${REGISTERID }_">
									<input id="userId" style="width: 160px;"
										onblur="checkUserId('userId')" 	onfocus="myfocus('userId');"
										type="text" class="input_text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right:10px;"><div id="userId_vTip">&nbsp;</di v></td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									用户名称 ：
								</td>
								<td align="left" width="45%">
									<input id="name" style="width: 160px;" name="obj.name"
										type="text" class="text" onblur="checkName('name');"
										onfocus="myfocus('name')">
									<strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right:10px;"><div id="name_vTip">&nbsp;</div></td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									用户密码 ：
								</td>
								<td align="left" width="45%">
									<input id="password" style="width: 160px;" name="obj.password"
										type="password" class="text" onblur="myblur('password');"
										onfocus="myfocus('password')">
									<strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right:10px;"><div id="password_vTip">&nbsp;</div></td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									用户密码确认 ：
								</td>
								<td align="left" width="45%">
									<input id="password1" style="width: 160px;" name="password1"
										type="password" class="text" onblur="myblur('password1');"
										onfocus="myfocus('password1')">
									<strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right:10px;"><div id="password1_vTip">&nbsp;</div></td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">
									用户描述 ：
								</td>
								<td align="left">
									<textarea id="description" name="obj.description" cols="20" rows="5"
										class="normal" onblur="myblur('description');"
										onfocus="myfocus('description')"></textarea>
								</td>
								<td><div id="description_vTip" class="onShow"></div></td>
							</tr>
							<tr><td colspan="3" height="5"></td></tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center">
				<tr height="35">
					<td align="center">
						<button class="btn_sec" onClick="frmChk()" id="add">
							添加
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick="window.close()">
							关闭
						</button>
					</td>
				</tr>
			</table>
		</div>

	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk() {
	var flag = myblur("all");
	if(flag){
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
			frm.action="<%=basePath%>/user/add.action";
			frm.submit();
   		 }else{
          return false;
		}
	}

}

//求混合字符串长度
function getLength(v){
	
	var vlen = 0;
	var str = v.split("");
	for( var a=0 ; a<str.length ; a++)
	{
		if (str[a].charCodeAt(0)<299){ 
			vlen++;
		}else{
			vlen+=2;
		}
	}
	return vlen;
}
    
    //设置key选项 showTr
   function onSelect(value)
		{
		   if(value=='Y')
		   {
		   		document.getElementById("showTr").style.display='';
		   }
		   else
		   {
		     document.getElementById("showTr").style.display='none';
		   }
		}
		
function addKey()
{
	 var str1 = "";
	 var errorCode = 0;
	 var ifInstalled = true;
	 var traderId=frm.userid.value;
	try
	{
		str1 = ePass.VerifyUser(<%=marketId%>,traderId);	
	}
	catch(err)
	{
		ifInstalled = false;														
	}	
	
	if(isNaN(str1))
	{
	}
	else
	{
		ifInstalled = true;
		errorCode = parseInt(str1);
		if(errorCode==-10)
		{
			alert("读Key驱动程序错误！");	
		}else if (errorCode==-1)
		{
			alert("请插入USB身份验证盘！");	
		}else if (errorCode==-2)
		{
			alert("非法USB身份验证盘！");	
		}else if (errorCode==-3)
		{
			alert("USB身份验证盘不正确");	
		}else if (errorCode==-4)
		{
			alert("USB身份验证盘已经损坏，请联系发放者！");	
		}
	}
	
	if(!ifInstalled)
	{
		alert("请安装交易控件以正常绑定！");	
		return false;	
	}
	else
	{	   
		if(errorCode==0)
		{
			frm.keyCode.value = str1;	
		}else
		{
			return false;	
		}			
	}
}
	function myblur(userID) {
		var flag = true;
		if ("description" == userID) {
			flag = description(userID);
		}else if("password"==userID){
			flag=password(userID);
		}
		else if ("password1" == userID) {
			flag = passwordcompare(userID,"password");
		}
		else if ("name" == userID) {
			flag = checkName(userID);
		}
		else if ("userId" == userID) {
			flag = checkUserId(userID);
		}
		else {
			if (!checkName("name")) {
				flag = false;
			}
			if (!checkUserId("userId")) {
				flag = false;
			}
			if (!description("description")) {
				flag = false;
			}
			if(!password("password")){
				flag=false;
			}
			if (!passwordcompare("password1","password")) {
				flag = false;
			}
		}
		return flag;
	}
	function description(userID) {
		var innerHTML = "";
		var marg = document.getElementById("description").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (user.value.length > 64) {
			innerHTML = "长度应不超过64位";
		} else {
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
	
	function checkUserId(userID){
		var innerHTML = "";
		var marg = document.getElementById("userId").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		} else if (!(/^\w+$/.test(frm.userId.value))) {
			innerHTML = "含有不合法字符"
		} else if (user.value.length > 9) {
			innerHTML = "长度应不超过9位";
		} else {
			flag = true;
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		var id='${REGISTERID}_'+document.getElementById("userId").value;
		if(flag){
			checkAction.existUserId(id,function(isExist){
				if(isExist){
					document.getElementById("userId").value="";
					alert('用户代码已存在，请重新添加');
					//document.getElementById("userId").value="";
					document.getElementById("userId").focus();
				}
			});
			
		}
		document.getElementById('userAll').value=id;
		return flag;
	}
	function checkName(userID){
		var innerHTML = "";
		var marg = document.getElementById("name").value;
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		} else if (user.value.length > 32) {
			innerHTML = "长度应不超过32位";
		} else if(!isStr(user.value,true,null)){
			innerHTML = "含有不合法字符";
		}else {
			flag = true;
		}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		/*var name=document.getElementById("name").value;
		checkAction.existUserName(name,function(isExist){
			if(isExist){
				document.getElementById("name").value="";
				alert('用户名已存在，请重新添加');
				//document.getElementById("name").value="";
				document.getElementById("name").focus();
			}
		});*/
		return flag;
	}

//-->
	function myfocus(userID) {
//	var vTip = document.getElementById(userID + '_vTip');
			/*var innerHTML = "";
		if ('minRiskFund' == userID) {
			innerHTML = "不能为空";
		}
		vTip.innerHTML = innerHTML;
		vTip.className = "onFocus";*/
	}
	
	function password(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (user != null) {
			var vTip = document.getElementById(userID + "_vTip");
			var flag = false;
			if (checkPassword(user.value) != "") {
				innerHTML = checkPassword(user.value);
			} else {
				flag = true;
			}
			vTip.innerHTML = innerHTML;
			if (flag) {
				vTip.className = "";
			} else {
				vTip.className = "onError";
			}
			return flag;
		} else
			return true;
	}
	
	function passwordcompare(userID, compareuserID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (user != null) {
			var comparevalue = document.getElementById(compareuserID).value;
			var vTip = document.getElementById(userID + "_vTip");
			var flag = false;
			if (checkPassword(user.value) != "") {
				innerHTML = checkPassword(user.value);
			} else {
				if (user.value != comparevalue) {
					innerHTML = "密码与确认密码不一致！";
				} else {
					flag = true;
				}
			}
			vTip.innerHTML = innerHTML;
			if (flag) {
				vTip.className = "";
			} else {
				vTip.className = "onError";
			}
			return flag;
		} else {
			return true;
		}
	}
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
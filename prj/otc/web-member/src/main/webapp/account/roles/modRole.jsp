<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<body class="st_body">
<head>
		<title>修改系统角色</title>
	</head>
<form name="frm" method="post" action="<%=basePath %>/role/updateCommonRole.action" targetType="hidden" onkeypress="if(event.keyCode==13||event.which==13){return false;}">
		<div>
			<table border="0" width="100%" align="center">
				<tr>
					<td>
				<div class="st_title">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;修改系统角色</div>
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="90%"
							align="center" class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									角色代码 ：
								</td>
								<td align="left" class="td_size" colspan="2">
									${obj.id }
									<input type="hidden" name="obj.id" id="id" value="${obj.id }">
								</td>
							</tr>
							<tr height="35">
							<input type=text style="width:0px"> 
								<td align="right" class="td_size" width="20%">
									角色名称 ：
								</td>
								<td align="left" class="td_size" width="40%">
									<input class="input_text" name="obj.name" type="text" id="name"
										onblur="myblur('name')"  onfocus="myfocus('name')"
										class="input_text" value="${obj.name }"><strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right: 10px;"><div id="name_vTip"></div></td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size">角色描述 ：</td>
								<td align="left" class="td_size">
									<textarea name="obj.description" cols="24" rows="4"
									onblur="myblur('description')"  onfocus="myfocus('description')"
										id="description" class="normal">${obj.description }</textarea>
								</td>
								<td style="padding-right: 10px;"><div id="description_vTip"></div></td>
							</tr>
							<tr><td height="2">&nbsp;</td></tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr>
					<td  align="center" id="tdId">
						<button  class="btn_sec" onClick="frmChk()" id="update">保存</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onClick="window.close()">关闭</button>
					</td>
				</tr>
		 </table>
		</div>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
var reg =/^\w*DEFAULT_+\w*|\w*TRADE_+\w*$/; 
var id='${obj.type}';
if(id.match(reg)!=null){
	var tableTd=document.getElementById("tdId");
	tableTd.style.display="none";
	document.getElementById('update').disabled='disabled';
	var regexTests = document.getElementsByTagName("input");
	var len = regexTests.length;
	for (i = 0; i < len; i++) {
		regexTests[i].readOnly = 'readOnly';
	}
	var regexselect = document.getElementsByTagName("select");
	var len2 = regexselect.length;
	for (i = 0; i < len2; i++) {
		regexselect[i].disabled='disabled';
	}
	var regexText = document.getElementsByTagName("textarea");
	var len3 = regexText.length;
	for (i = 0; i < len3; i++) {
		regexText[i].readOnly='readOnly';
	}
}
<!--
function myblur(userID){
	var flag = true;
  if("name"==userID){
		flag = name1(userID);
	}else if("description"==userID){
		flag = description(userID);
	}else{
		if(!name1("name")) flag = false;
		if(!description("description")) flag = false;
	}
	return flag;
}
function name1(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if (isEmpty(user.value + "")) {
			innerHTML = "不能为空";
		} else {
			if (user.value.length > 32) {
				innerHTML = "长度应不超过32位";
				}else if(!isStr(user.value,true,null)){
			innerHTML = "含有不合法字符";
				}else {
				flag = true;
				}
			}
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	/*var ddd=document.getElementById("name").value;
	checkAction.existRoleName('${obj.id}',ddd,'${obj.memberNo}',function(isExist){
			if(isExist){
				alert('角色名已存在，请重新添加');
				document.getElementById("name").value="";
				document.getElementById("name").focus();
			}
		});*/
	return flag;
}

function description(userID){
	var user = document.getElementById(userID).value;
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(getLength(user)>64){
		innerHTML = "长度不大于64位";
		}else{
			innerHTML = "";
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
function frmChk()
{
	var flag = myblur("all");
	if(!flag){
		return false;
	}
		var rolenamelen = getLength(frm.name.value);
		var descriptionlen = getLength(frm.description.value);
		var mark = true;
		var sign = false;
		if(rolenamelen>32 || descriptionlen>64){
			mark = false;
			alert("您提交的数据长度过大。\n请保证角色名长度不超过32，角色描述长度不超过64。");
		}
		if(frm.name.value == "") {
			alert("姓名不能为空！");
			frm.name.focus();
			return false;
		}
		if(mark){
			if(userConfirm()){
				sign = true;
			  }else{
				return false;
			  }
			}			
	//防止重复提交
	if(sign){
			frm.submit();
		}	
}
function myfocus(userID) {
//	var vTip = document.getElementById(userID + '_vTip');
			/*var innerHTML = "";
		if ('minRiskFund' == userID) {
			innerHTML = "不能为空";
		}
		vTip.innerHTML = innerHTML;
		vTip.className = "onFocus";*/
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

 function userConfirm(){
   if(affirm("您确实要操作这些数据吗？"))
   { 
     return true;
   }else{
     return false;
   }
 }
//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
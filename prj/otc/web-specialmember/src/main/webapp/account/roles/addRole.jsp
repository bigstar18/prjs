<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<head>
	<title>���ϵͳ��ɫ</title>
</head>
<body class="st_body">
	<form name="frm" id="frm"
		action="<%=basePath%>/role/addCommonRoleForward.action" method="post"
		targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
						&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;���ϵͳ��ɫ
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="95%"
							align="center" class="st_bor">
							<input type="hidden" name="obj.s_memberNo" value="${REGISTERID }">
							<input type="hidden" name="obj.type" value="${ADMIN}">
							<tr height="35">
							<input type=text style="width:0px"> 
								<td align="right" width="20%">
									��ɫ���� ��
								</td>
								<td align="left" width="45%">
									<input name="obj.name" class="input_text" id="name" type="text"
										onblur="checkName('name');" onfocus="myfocus('name')"
										class="text">
									<strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right: 10px;">
									<div id="name_vTip"></div>
								</td>
							</tr>
							<tr height="35">
								<td align="right">
									��ɫ���� ��
								</td>
								<td align="left">
									<textarea name="obj.description" id="description" cols="20" rows="3"
										class="normal" onblur="myblur('description');"
										onfocus="myfocus('description')"></textarea>
								</td>
								<td style="padding-right: 10px;">
									<div id="description_vTip"></div>
								</td>
							</tr>
							<tr><td colspan="3" height="5"></td></tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="90%"
				align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="frmChk()" id="add">
							���
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function myblur(userID) {
		var flag = true;
		if ("description" == userID) {
			flag = description(userID);
		} else {
			if (!checkName("name")) {
				flag = false;
			}
			if (!description("description")) {
				flag = false;
			}
		}
		return flag;
	}
function checkName(userID){
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (isEmpty(user.value + "")) {
			innerHTML = "����Ϊ��";
		} else {
			if (user.value.length > 32) {
				innerHTML = "����Ӧ������32λ";
				}else if(!isStr(user.value,true,null)){
			innerHTML = "���в��Ϸ��ַ�";
		}else {
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
	}
function description(userID) {
		var innerHTML = "";
		var user = document.getElementById(userID);
		var vTip = document.getElementById(userID + "_vTip");
		var flag = false;
		if (user.value.length > 64) {
			innerHTML = "����Ӧ������64λ";
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

//-->
	function myfocus(userID) {
//	var vTip = document.getElementById(userID + '_vTip');
			/*var innerHTML = "";
		if ('minRiskFund' == userID) {
			innerHTML = "����Ϊ��";
		}
		vTip.innerHTML = innerHTML;
		vTip.className = "onFocus";*/
	}
function frmChk() {
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
		alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫ�����Ȳ�����32����ɫ�������Ȳ�����64��");
	}
	if(frm.name.value == "") {
		alert("��ɫ���Ʋ���Ϊ�գ�");
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
	//��ֹ�ظ��ύ
	if(sign){
		frm.submit();
	}
}

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
   if(affirm("��ȷʵҪ������Щ������"))
   { 
     return true;
   }else{
     return false;
   }
 }
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
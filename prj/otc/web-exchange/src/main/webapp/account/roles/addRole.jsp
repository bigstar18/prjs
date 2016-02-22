<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<body class="st_body" style="overflow-y: hidden">
	<form name="frm" id="frm"
		action="<%=basePath%>/role/addCommonRoleForward.action" method="post"
		targetType="hidden">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
						&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;���ϵͳ��ɫ
						</div>
						<input type="hidden" name="obj.type" value="${ADMIN}">
						<table border="0" cellspacing="0" cellpadding="0" width="90%"
							align="center" class="st_bor">
							<tr height="35">
								<td align="right" width="20%">
									��ɫ���� ��
								</td>
								<input type=text style="width:0px"> 
								<td align="left" width="45%">
									<input name="obj.name" class="input_text" id="name" type="text"
									onblur="checkName('name');"	onfocus="myfocus('name')"	class="text" >
									<strong class="check_input">&nbsp;*</strong>
								</td>
								<td style="padding-right: 10px;"><div id="name_vTip">&nbsp;</div></td>
							</tr>
							<tr height="35">
								<td align="right">
									��ɫ���� ��
								</td>
								<td align="left">
									
									 <textarea name="obj.description" id="description" cols="21" rows="7" onblur="myblur('description');" onfocus="myfocus('description')"></textarea>
								</td>
								<td style="padding-right: 10px;"><div id="description_vTip"></div></td>
							</tr>
							<tr><td colspan="3" height="3">&nbsp;</td></tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
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
		}
			else {
				flag = true;
				}
			}
		vTip.innerHTML = innerHTML;
		if (flag) {
			vTip.className = "";
		} else {
			vTip.className = "onError";
		}
		/*var name=document.getElementById("name").value;
		checkAction.existRoleName(name,function(isExist){
			if(isExist){
				alert('�û����Ѵ��ڣ����������');
				document.getElementById("name").value="";
				document.getElementById("name").focus();
			}
		});*/
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
   if(confirm("��ȷʵҪ������Щ������"))
   { 
     return true;
   }else{
     return false;
   }
 }
 
 	function checkNameT(){
		var name=document.getElementById("name").value;
		checkAction.existRoleName(name,function(isExist){
			if(isExist){
				alert('��ɫ�����Ѵ��ڣ�����������');
				document.getElementById("name").value="";
				document.getElementById("name").focus();
			}
		});
	
	}

</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<body class="st_body" style="overflow-y: hidden">
	<form name="frm" id="frm" method="post"
		action="<%=basePath%>/role/updateRole.action" targetType="hidden" onkeypress="if(event.keyCode==13||event.which==13){return false;}">
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
							&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;ϵͳ��ɫ��Ϣ
						</div>
						<input type="hidden" name="obj.type" value="${obj.type}">
						<table border="0" cellspacing="0" cellpadding="0" width="90%"
							align="center" class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="25%">
									��ɫ���� ��
								</td>
								<td align="left">
									${obj.id }
									<input type="hidden" name="obj.id" id="id" value="${obj.id }">
								</td>
							</tr>
							<tr height="35">
							<input type=text style="width:0px"> 
								<td align="right" class="td_size">
									��ɫ���� ��
								</td>
								<td align="left">
									<input class="input_text" name="obj.name" type="text" id="name"
										onblur="myblur('name')"  onfocus="myfocus('name')"
										class="input_text" value="${obj.name }"><strong class="check_input">&nbsp;*</strong>
								</td>
									<td style="padding-right:10px;"><div id="name_vTip"></div></td>
							</tr>
							<tr>
								<td align="right" class="td_size">
									��ɫ���� ��
								</td>
								<td align="left">
									<textarea name="obj.description"
									onblur="myblur('description')" rows="6" cols="20"  onfocus="myfocus('description')"
										id="description" class="normal">${obj.description }</textarea>
								</td>
								<td style="padding-right:10px;"><div id="description_vTip"></div></td>
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
						<td align="center" id="tdId">
							<button class="btn_sec" onClick="frmChk()" id="update">
								�޸�
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
var reg =/^\w*DEFAULT_+\w*$/; 
var id='${obj.type}';
if(id.match(reg)!=null){
	var tableTd=document.getElementById("tdId");
	tableTd.style.display="none";
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
function myblur(userID){
	var flag = true;
  if("name"==userID){
		flag = name(userID);
	}else if("description"==userID){
		flag = description(userID);
	}else{
		if(!name("name")) flag = false;
		if(!description("description")) flag = false;
	}
	return flag;
}
function name(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
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
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	return flag;
}
function description(userID){
	var user = document.getElementById(userID).value;
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(getLength(user)>64){
		innerHTML = "���Ȳ�����64λ";
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
			alert("���ύ�����ݳ��ȹ���\n�뱣֤��ɫ�����Ȳ�����32����ɫ�������Ȳ�����64��");
		}
		if(frm.name.value == "") {
			alert("��������Ϊ�գ�");
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
function myfocus(userID) {
//	var vTip = document.getElementById(userID + '_vTip');
			/*var innerHTML = "";
		if ('minRiskFund' == userID) {
			innerHTML = "����Ϊ��";
		}
		vTip.innerHTML = innerHTML;
		vTip.className = "onFocus";*/
	}

//�����ַ�������
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
//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
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
	<form name="frm" id="frm"
		action="<%=basePath%>/role/addCopyCommonRoleForward.action" method="post"
		targetType="hidden">
		<input type="hidden" name="original_roleId" value="${original_roleId}" />
		<div>
			<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title">
							<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
							&nbsp;&nbsp;���ϵͳ��ɫ
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="80%"
							align="center" class="st_bor">
							<input type="hidden" name="obj.s_memberNo" value="${REGISTERID }">
							<tr height="35">
								<td align="right">
									��ɫ���� ��
								</td>
								<td align="left">
									<input name="obj.name" class="input_text" id="name" type="text"
										class="text"><strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right">
									��ɫ���� ��
								</td>
								<td align="left">
									<textarea name="obj.description" id="description"
										class="normal"></textarea>
								</td>
							</tr>
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

function frmChk() {
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
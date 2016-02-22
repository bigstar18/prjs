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
							&nbsp;&nbsp;添加系统角色
						</div>
						<table border="0" cellspacing="0" cellpadding="0" width="80%"
							align="center" class="st_bor">
							<input type="hidden" name="obj.s_memberNo" value="${REGISTERID }">
							<tr height="35">
								<td align="right">
									角色名称 ：
								</td>
								<td align="left">
									<input name="obj.name" class="input_text" id="name" type="text"
										class="text"><strong class="check_input">&nbsp;*</strong>
								</td>
							</tr>
							<tr height="35">
								<td align="right">
									角色描述 ：
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

function frmChk() {
	var rolenamelen = getLength(frm.name.value);
	var descriptionlen = getLength(frm.description.value);
	var mark = true;
	var sign = false;
	
	if(rolenamelen>32 || descriptionlen>64){
		mark = false;
		alert("您提交的数据长度过大。\n请保证角色名长度不超过32，角色描述长度不超过64。");
	}
	if(frm.name.value == "") {
		alert("角色名称不能为空！");
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

</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
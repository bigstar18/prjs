<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript">
	
</script>
<script
	src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="GBK">
	
</script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" charset="GBK"></script>
<script>
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}
		
		<%
			//mgrIsNeedKey：Y表示后台管理启用key 
			if(Global.getMarketInfoMap().get("mgrIsNeedKey").equals("Y") 
					&& Global.getMarketInfoMap().get("marketNO") != null 
					&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){
		%>
			$("#mgrIsNeedKeyCode").css("display","block");
			
			if($("#kcode").val()!="0123456789ABCDE"){
				$("#keyChecked").attr("checked",true);
				$("#showkey").css("display","");
			}else{
				$("#keyChecked").attr("checked",false);
			}
				
		<%	
		}
		%>
		

		$("#frm").validationEngine('attach');
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$("#name").val($("#name").val().trim());
				$('#frm').submit();
			}
		}
	}	);
	});
	function checkKey(inp){
		if(inp.checked){
			document.getElementById("showkey").style.display="";
			var m = initKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("userid").value,frm);
			if(!m.passed){
				alert(m.msg);
				document.getElementById("kcode").value="0123456789ABCDE";
				document.getElementById("showkey").style.display="none";
				$("#keyChecked").attr("checked",false);
			}else{
				$("#keyChecked").attr("checked",true);
				document.getElementById("showkey").style.display="";
			}
				
		}else{
			document.getElementById("kcode").value="0123456789ABCDE";
			document.getElementById("showkey").style.display="none";
		}
	}
</script>
<body style="overflow-y: hidden;">
<head>
	<title>修改系统管理员</title>
	<meta http-equiv="Pragma" content="no-cache">
</head>
<form name="frm" id="frm" method="post"
	action="${basePath }/user/update.action" targetType="hidden">
	<div class="div_tj">

		<table border="0" width="90%" align="center">
			<tr>
				<td>
					<div class="warning">
						<div class="content">
							温馨提示 :修改系统管理员
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="div_cxtj">
						<div class="div_cxtjL"></div>
						<div class="div_cxtjC">
							系统管理员信息
						</div>
						<div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
					<table border="0" cellspacing="0" cellpadding="0" width="100%"
						align="center" class="st_bor">
						<tr height="35">
							<td align="right" class="td_size" width="20%">
								管理员代码 ：
							</td>
							<td align="left" colspan="2" width="40%" class="td_size">
								${entity.userId }
								<input class="from" type="hidden" id="userid"
									name="entity.userId" value="${entity.userId}">
								<input class="from" type="hidden" name="entity.password"
									value="${entity.password}">
								<input class="from" type="hidden" name="entity.skin"
									value="${entity.skin}">
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr height="35">
							<td align="right" class="td_size" width="20%">
								<span class="required">*</span> 管理员名称 ：
							</td>
							<td align="left" width="40%" class="td_size">
								<input name="entity.name" id="name" type="text"
									value="${entity.name}" style="width: 145px;"
									class="validate[required,maxSize[<fmt:message key='userName' bundle='${PropsFieldLength}'/>]] input_text" />
							</td>
							<td>
								<div class="onfocus">
									不能为空！
								</div>
							</td>
						</tr>
						<c:if
							test="${sessionScope.CurrentUser.type=='DEFAULT_SUPER_ADMIN'}">
							<tr height="35">
								<td align="right" class="td_size" width="20%">
									管理员类型 ：
								</td>
								<td align="left" width="45%">
									<select id="type" name="entity.type" style="width: 145px;">
										<c:if test="${entity.type=='DEFAULT_SUPER_ADMIN'}">
											<option>超级管理员角色</option>
										</c:if>
										<c:if test="${!(entity.type=='DEFAULT_SUPER_ADMIN')}">
											<option value="ADMIN"
												<c:if test="${entity.type=='ADMIN'}">selected="selected"</c:if>>
												普通管理员
											</option>
											<option value="DEFAULT_ADMIN"
												<c:if test="${entity.type=='DEFAULT_ADMIN'}">selected="selected"</c:if>>
												高级管理员
											</option>
										</c:if>
									</select>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</c:if>
						<tr height="35">
							<td align="right" class="td_size" >
								是否禁用 ：
							</td>
							<td align="left" class="td_size">
								<select name="entity.isForbid" id="isForbid" style="width: 145px;"
									class="validate[required] text-input">
									<option value="">
										请选择
									</option>
									<option value="Y">
										禁用
									</option>
									<option value="N">
										不禁用
									</option>
								</select>
								<script type="text/javascript">
	document.getElementById("isForbid").value = "${entity.isForbid }";
</script>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<input type="hidden" name="enableKey" value="N" id="enableKey">
						<tr height="35" id="mgrIsNeedKeyCode" style="display: none;">
							<td align="right" class="td_size" width="20%">
								是否启用 KEY ：
							</td>
							<td align="left" colspan="2"><input type="checkbox" onclick="checkKey(this)" id="keyChecked"/>
								<span id="showkey" style="display: none;"><span class="required">*</span>
									<input id="kcode" name="entity.keyCode" style="width: 108px;" type="text" value="${entity.keyCode}" title="${entity.keyCode}" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
								</span>
							</td>
						</tr>
						
						<tr height="35">
							<td align="right" class="td_size">
								管理员描述 ：
							</td>
							<td align="left">
								<textarea name="entity.description" id="description" cols="20"
									rows="5" class="validate[maxSize[<fmt:message key='userDescription' bundle='${PropsFieldLength}'/>]]">${entity.description }</textarea>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="3" height="5"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr height="5px">
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<div class="tab_pad">
		<table border="0" cellspacing="0" cellpadding="0" width="100%"
			align="center">
			<tr height="35">
				<td width="70">
					&nbsp;
				</td>
				<td align="right" id="tdId">
					<rightButton:rightButton name="修改" onclick="" className="btn_sec"
						action="/user/update.action" id="update"></rightButton:rightButton>
					&nbsp;&nbsp;
					<button class="btn_sec" onClick=
	window.close();
>
						关闭
					</button>
					&nbsp;
				</td>
			</tr>
		</table>
	</div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	var reg = /^\w*DEFAULT_+\w*$/;
	var reg1 = /^\w*_SUPER_+\w*$/;
	var id = '${entity.type}';
	var userId = '${entity.userId}';
	var logonType = '${sessionScope.CurrentUser.type}';
	var a = false;
	var b = false;
	if (logonType.match(reg1) != null) {
		if (userId == '${sessionScope.CurrentUser.userId}') {
			a = true;
		}
	} else {
		if (id.match(reg) != null
				|| userId == '${sessionScope.CurrentUser.userId}') {
			b = true;
		}
	}
	if (a || b) {
		var tableTd = document.getElementById("tdId");
		tableTd.style.display = "none";
		var regexTests = document.getElementsByTagName("input");
		var len = regexTests.length;
		for (i = 0; i < len; i++) {
			regexTests[i].readOnly = 'readOnly';
		}
		var regexselect = document.getElementsByTagName("select");
		var len2 = regexselect.length;
		for (i = 0; i < len2; i++) {
			regexselect[i].disabled = 'disabled';
		}
		var regexText = document.getElementsByTagName("textarea");
		var len3 = regexText.length;
		for (i = 0; i < len3; i++) {
			regexText[i].readOnly = 'readOnly';
		}

	}
	//求混合字符串长度
	function getLength(v) {

		var vlen = 0;
		var str = v.split("");
		for ( var a = 0; a < str.length; a++) {
			if (str[a].charCodeAt(0) < 299) {
				vlen++;
			} else {
				vlen += 2;
			}
		}
		return vlen;
	}

	//设置key选项 showTr
	function onSelect(value) {
		if (value == 'Y') {
			document.getElementById("showTr").style.display = '';
		} else {
			document.getElementById("showTr").style.display = 'none';
		}
	}
</SCRIPT>
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>

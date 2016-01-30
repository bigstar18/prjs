<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>添加交易员</title>
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet"  href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${basePath}/mgr/app/integrated/js/firmjson.js"></script>
		<script src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script>
			//全部模块
			var modelMap = {};
			jQuery(document).ready(function() {
				//frontIsNeedKey：Y表示前台管理启用key 
				<%if(Global.getMarketInfoMap().get("frontIsNeedKey").equals("Y") 
						&& Global.getMarketInfoMap().get("marketNO") != null 
						&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){%>
					$("#frontIsNeedKeyCode").css("display","block");
				<%}%>
				if ("" != '${ReturnValue.info}' + "") {
					parent.document.frames('leftFrame').location.reload();
				}
				<c:forEach var="model" items="${tradeModuleMap}">
				modelMap[${model.key}]='${model.value.shortName}';
				</c:forEach>

				$("#firmId").focus().autocomplete(getfirmList());
				$("#traderNumber").focus(function(){
					$(this).removeClass("input_text");
				});

				//ajax验证表单
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../../ajaxcheck/checkAddTraderForm.action?t="+Math.random(),
					onAjaxFormComplete : ajaxValidationCallback,
					onBeforeAjaxFormValidation : beforeCall
				});

				//提交前事件
				function beforeCall(form, options) {
					return true;
				}

				//提交后事件
				function ajaxValidationCallback(status, form, json, options) {
					//如果返回成功
					if (status === true) {
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var addDemoUrl = $("#addtrader").attr("action");
							//全 URL 路径
							var url = "${basePath}"+addDemoUrl;
							$("#frm").attr("action",url);
							frm.submit();
						}
					}
				}

				$("#addtrader").click(function(check) {
					if ($("#frm").validationEngine('validateform')) {
					}
				});
			});
			//通过是否启用key修改key输入框
			function keyValue(value) {
				var keyV = document.getElementById("keyV");
				if('Y'==value){
					keyV.disabled=false;
					keyV.className = keyV.className.replace('input_text_pwdmin','input_text');
				}else{
					keyV.disabled="disabled";
					keyV.className = keyV.className.replace('input_text','input_text_pwdmin');
				}
			}
			var oldfirmId = "";
			//通过交易商代码生成模块选项
			function ajaxGetFirmModule(){
				var firmId = $("#firmId").val();
				if(oldfirmId != firmId){
					oldfirmId = firmId;
					//$("#traderNum").attr("value",firmId);
					$("#traderNum").html(firmId);
					var moduleIdtd = $("#moduleIdtd");
					moduleIdtd.html("");
					var url = "../../ajaxcheck/mfirm/getfirmModuleIdJson.action?firmId="+firmId+"&t="+Math.random();
					$.getJSON(url,null,function call(result){
						if(result == ''){
							moduleIdtd.html("无");
							}else{
								$.each(result,function(i,field){
									var div = "<input name='traderModules' type='checkbox' id='inp"+field+"' value='"+field+"'/><label onclick='inp"+field+".click();'>"+modelMap[field]+"</label>&nbsp;&nbsp;";
									moduleIdtd.append(div);
								});
							}
					});
				}
			}
function checkUserId(){
	var userId =$("#userId").val();
	if(!isStr(userId,true)){
		return "*包含非法字符";
	}
}
function checkKey(inp){
	if(inp.checked){
		document.getElementById("showkey").style.display="";
		var m = initKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("traderId").value,frm);
		if(!m.passed){
			alert(m.msg);
			document.getElementById("kcodech").checked = false;
			checkKey(document.getElementById("kcodech"));
		}
		document.getElementById("enableKey").value="Y";
	}else{
		document.getElementById("showkey").style.display="none";
		document.getElementById("kcode").value="0123456789ABCDE";
		document.getElementById("enableKey").value="N";
	}
}
	</script>
	</head>
	<body>
		<form name="frm" id="frm" method="post" action="${basePath }/trade/trader/addTrader.action" targetType="hidden">
			<div class="st_title" >
			    <table border="0" width="90%" align="center">
			        <tr>
			            <td>
							<div class="warning">
								<div class="content">温馨提示 :添加交易员<font color="#880000">（交易员可以使用用户名或者交易员代码登录系统）</font></div>
							</div>
						</td>
					</tr>
					<table border="0" width="90%" align="center">
						<tr>
							<td>
								<div class="div_cxtj">
							    	<div class="div_cxtjL"></div>
							        <div class="div_cxtjC">交易员信息</div>
							        <div class="div_cxtjR"></div>
							    </div>
								<div style="clear: both;"></div>
								<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
									<tr height="35">
										<td align="right" class="td_size" width="25%">
											<span class="required">*</span>所属交易商 ：
										</td>
										<td align="left" width="25%" class="td_size">
											<input id="firmId" name="entity.mfirm.firmId" class="validate[required,maxSize[${firmLen}],custom[onlyLetterNumber],ajax[checkFirmId],funcCall[ajaxGetFirmModule]] input_text datepicker"/>
										</td>
										
										<td align="right" class="td_size" width="25%">
										    <span class="required">*</span>交易员编号 ：
										</td>
										<td align="left" width="25%" class="td_size">
											<div>
											<%--<input  type="text" id="traderNum" name="traderNum" disabled="disabled" style="border: 0px;width:96px;"> --%>
											<span  id="traderNum" name="traderNum"></span>
											<input id="traderNumber" name="traderNumber" onblur="traderId.value=firmId.value+this.value;" maxlength="2" style="width: 20px;height:20px;"
												class="validate[required,maxSize[2],custom[onlyLetterNumber],ajax[checkNoTraderId]] input_text datepicker" data-prompt-position="topLeft:-77,8"/>
											<input id="traderId" name="entity.traderId" type="hidden"/>
											</div>
										</td>
									</tr>
									<tr height="35">
										<td align="right" class="td_size" width="">
											<span class="required">*</span>用户名 ：
										</td>
										<td align="left" width="" class="td_size">
											<input id="userId" name="entity.userId" class="validate[required,funcCall[checkUserId],maxSize[<fmt:message key='userId' bundle='${PropsFieldLength}' />],ajax[checkNoTraderUserId]] input_text datepicker"/>
										</td>
										<td align="right" class="td_size" width="">
											<span class="required">*</span>交易员类型 ：
										</td>
										<td align="left" width="" class="td_size">
											<select id="type" name="entity.type" class="validate[required] normal" style="width: 120px" data-prompt-position="topLeft:20,8">
													<option value="">请选择</option>
												<c:forEach items="${traderTypeMap}" var="map">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr height="35">
										<td align="right" class="td_size">
											<span class="required">*</span>交易员名称 ：
										</td>
										<td align="left" width=" " class="td_size">
											<input  name="entity.name" id="name" type="text" class="validate[required,maxSize[<fmt:message key='traderName' bundle='${PropsFieldLength}' />]] input_text"/>
										</td>
										<td align="right" class="td_size">
											<span class="required">*</span>交易员状态 ：
										</td>
										<td align="left" class="td_size">
			                				<select id="status" name="entity.status" class="validate[required] normal" style="width: 120px" data-prompt-position="topLeft:20,8">
												<option value="">请选择</option>
												<c:forEach var="status" items="${traderStatusMap}">
				                					<option value="${status.key}">${status.value}</option>
				                				</c:forEach>
											</select>
			                			</td>
			              			</tr>
									<tr height="35" id="frontIsNeedKeyCode"  style="display: none;">
										<td align="right" class="td_size" width="20%">
											是否启用 KEY ：
										</td>
										<input type="hidden" id="enableKey" name="entity.enableKey" value="N"  />
										<td align="left" colspan="2"><input type="checkbox" onclick="checkKey(this)" id="kcodech"/>
											<span id="showkey" style="display: none;"><span class="required">*</span>
												<input id="kcode" name="entity.keyCode" style="width: 122px;" type="text" value="0123456789ABCDE" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
											</span>
										</td>
									</tr>
								</table>
								<tr>
									<td>
										<div class="div_cxtj">
									    	<div class="div_cxtjL"></div>
									        <div class="div_cxtjC">密码信息</div>
									        <div class="div_cxtjR"></div>
									    </div>
										<div style="clear: both;"></div>
										<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>用户密码 ：
												</td>
												<td align="left" class="td_size">
													<input name="entity.password" id="password" type="password" class="validate[required,minSize[6],maxSize[<fmt:message key='userPassword' bundle='${PropsFieldLength}'/>]] input_text"/>
												</td>
											</tr>
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>确认密码 ：
												</td>
												<td class="td_size">
													<input type="password" name="password1" id="password1" class="validate[required,equals[password]] input_text">
												</td>
											</tr>
											<tr height="35">
											    <td align="right">权限：</td>
												<td colspan="2" id="moduleIdtd">
												</td>
											</tr>
											
										
										</table>
									<div class="tab_pad">
										<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
											<tr>
												<td align="right">
													<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/trade/trader/addTrader.action" id="addtrader"></rightButton:rightButton>
												    &nbsp;&nbsp;
													<button class="btn_sec" onClick="window.close()">
														关闭
													</button>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>

<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
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
		<%
			//frontIsNeedKey��Y��ʾǰ̨��������key 
			if(Global.getMarketInfoMap().get("frontIsNeedKey").equals("Y") 
					&& Global.getMarketInfoMap().get("marketNO") != null 
					&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){
		%>
			$("#frontIsNeedKeyCode").css("display","block");
			if($("#kcode").val()!="0123456789ABCDE"){
				$("#keyChecked").attr("checked",true);
				$("#showkey").css("display","");
			}else{
				$("#keyChecked").attr("checked",false);
			}
				
		<%	
		}
		%>
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		$("#frm").validationEngine('attach');
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("��ȷ��Ҫ������");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$("#name").val($("#name").val().trim());
					$('#frm').submit();
				}
			}
		});
	});

	//ͨ���Ƿ�����key�޸�key�����
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
	function checkKey(inp){
		if(inp.checked){
			document.getElementById("showkey").style.display="";
			var m = initKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("traderid").value,frm);
			if(!m.passed){
				alert(m.msg);
				document.getElementById("kcode").value="0123456789ABCDE";
				document.getElementById("enableKey").value="N";
				document.getElementById("showkey").style.display="none";
				$("#keyChecked").attr("checked",false);
			}else{
				$("#keyChecked").attr("checked",true);
				document.getElementById("enableKey").value="Y";
				document.getElementById("showkey").style.display="";
			}
				
		}else{
			document.getElementById("kcode").value="0123456789ABCDE";
			document.getElementById("enableKey").value="N";
			document.getElementById("showkey").style.display="none";
		}
	}
</script>
<body>
	
	<head>
		<title>����Ա��Ϣ�޸�</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
<iframe id="hiddenframe" name="hiddenframe" width=0 height=0 style="display:none" src="" application="yes"></iframe>
<form name="frm" id="frm" method="post" 
	action="${basePath }/trade/trader/updateTrader.action" target="hiddenframe">
		<div style="overflow: auto; height: 380px;" >
			<table border="0" width="90%" align="center">
			<div class="warning">
				<div class="content">
					��ܰ��ʾ :����Ա��Ϣ�޸�
				</div>
			</div>
				<tr>
					<td>
						<div class="div_cxtj">
    	<div class="div_cxtjL"></div>
        <div class="div_cxtjC">����Ա��Ϣ</div>
        <div class="div_cxtjR"></div>
    </div>
			<div style="clear: both;"></div>
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							align="center" class="st_bor">
							<tr height="35">
								<td align="right" class="td_size" width="100">
									����Ա���� ��
								</td>
								<td align="left" width="" class="td_size">
									${entity.traderId }
									<input type="hidden" id="traderid" name="entity.traderId"
										value="${entity.traderId}">
								</td>
								<td align="right" class="td_size" width="">
									���������� ��
								</td>
								<td align="left" width="" class="td_size" title="${entity.mfirm.firmId}">
									${entity.mfirm.firmId}
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="">
									�û��� ��
								</td>
								<td align="left" width=" " class="td_size">
									${entity.userId}<font color="#880000">��֧��ʹ���û�����¼��</font>
								</td>
								<td align="right" class="td_size" width="">
									<span class="required">*</span>
									����Ա���� ��
								</td>
								<td align="left" width=" " class="td_size">
									<input  name="entity.name" id="name" type="text"  value="${entity.name}"
										class="validate[required,maxSize[<fmt:message key='traderName' bundle='${PropsFieldLength}' />]] input_text" style="width: 100px"/>
								</td>
							</tr>
							<tr height="35">
								<td align="right" class="td_size" width="">
									����Ա���� ��
								</td>
								 <td align="left" class="td_size">
								 	<select id="type" name="entity.type" class="validate[required] normal" style="width: 100px">
										<option value="">��ѡ��</option>
										<c:forEach items="${traderTypeMap}" var="map">
											<option value="${map.key}" <c:if test="${entity.type eq map.key}">selected="true"</c:if>>${map.value}</option>
										</c:forEach>
									</select>
	                			</td>
	                			<td align="right" class="td_size">
									����Ա״̬ ��
								</td>
								<td align="left" class="td_size">
	                				<select id="status" name="entity.status" class="validate[required] normal" style="width: 100px" data-prompt-position="topLeft:20,8">
										<option value="">��ѡ��</option>
	                					<c:forEach var="status" items="${traderStatusMap}">
	                					<option value="${status.key}" <c:if test="${status.key==entity.status}">selected="selected"</c:if>>${status.value}</option>
	                					</c:forEach>
									</select>
	                			</td>
	                			
	                			
	              			</tr>
					  <%--	<c:if test="${entity.enableKey=='Y'}">--%>
					  		<tr height="35" id="frontIsNeedKeyCode"  style="display: none;">
								<td align="right" class="td_size" width="20%">
									�Ƿ����� KEY ��
								</td>
								<input type="hidden" id="enableKey" name="entity.enableKey" value="${entity.enableKey}">
								<td align="left" colspan="2"><input type="checkbox" onclick="checkKey(this)" id="keyChecked"/>
									<span id="showkey" style="display: none;"><span class="required">*</span>
										<input id="kcode" name="entity.keyCode" style="width: 108px;" type="text" value="${entity.keyCode}" title="${entity.keyCode}" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
									</span>
								</td>
							</tr>
							<%--<tr height="35">
								<td align="right" class="td_size">
									�Ƿ�����Key ��
								</td>
								<td align="left" class="td_size">
									<input type="radio" value="Y"  name="entity.enableKey" onclick="keyValue(this.value)" checked="checked" />��
									<input type="radio" value="N"  name="entity.enableKey" onclick="keyValue(this.value)" / >��
								</td>
								<td align="right" class="td_size">
									Keyֵ ��
								</td>
								<td align="left" class="td_size">
									<input type="text" class="validate[required,custom[onlyLetterNumber]] input_text" style="width: 100px" name="entity.keyCode" id="keyV" value="${entity.keyCode}"  maxLength="<fmt:message key='traderKeyCode' bundle='${PropsFieldLength}'/>">
								</td>
							</tr>
						</c:if>--%>
						
						<%--<c:if test="${entity.enableKey=='N'}">
							<tr height="35" id="frontIsNeedKeyCode"  style="display: none;">
								<td align="right" class="td_size" width="20%">
									�Ƿ����� KEY ��
								</td>
								<input type="hidden" id="enableKey" name="entity.enableKey" value="${entity.keyCode} />
								<td align="left" colspan="2"><input type="checkbox" onclick="checkKey(this)"/>
									<span id="showkey" style="display: none;"><span class="required">*</span>
										<input id="kcode" name="entity.keyCode" style="width: 108px;" type="text" value="${entity.keyCode}" title="${entity.keyCode}" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
									</span>
								</td>
							</tr>--%>
							<%--<tr height="35">
								<td align="right" class="td_size">
									�Ƿ�����Key ��
								</td>
								<td align="left" class="td_size">
										<input type="radio" value="Y"  name="entity.enableKey" onclick="keyValue(this.value)"/>��
										<input type="radio" value="N"  name="entity.enableKey" onclick="keyValue(this.value)" checked="checked" / >��
								</td>
								<td align="right" class="td_size">
									Keyֵ ��
								</td>
								<td align="left" class="td_size">
									<input disabled="disabled" class="validate[required,custom[onlyLetterNumber]] input_text_pwdmin" style="width: 100px" name="entity.keyCode" id="keyV" value="${entity.keyCode}"  maxLength="<fmt:message key='traderKeyCode' bundle='${PropsFieldLength}'/>">
								</td>
							</tr>
						</c:if> --%>
						
							
						<tr>
					        <td align="right">Ȩ�ޣ�</td>
						    <td colspan="4" id="moduleIdtd">
						    <c:set var="hasModule" value="N"></c:set>
						    <c:forEach var="sysModule" items="${tradeModuleMap}">
						    <c:if test="${sysModule.value.isFirmSet eq 'Y'}">
						    <c:forEach var="ftrader" items="${firmList}">
						    <c:if test="${ftrader.enabled=='Y' && ftrader.moduleId == sysModule.key}">
						  	  <c:set var="hasModule" value="Y"></c:set>
						    	<input type="checkbox" id="ch${sysModule.key}" name="traderModules" value="${sysModule.key}" <c:if test="${traderModuleMap[sysModule.key]=='Y'}">checked="checked"</c:if>/>
								<label onclick="ch${sysModule.key}.click();">${sysModule.value.shortName}</label>
						    </c:if>
						    </c:forEach>
						    </c:if>	
						    </c:forEach>
						    <c:if test="${hasModule eq 'N'}">��</c:if>
							</td>
					    </tr>
					</table>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center">
				<tr height="35">
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td align="right" id="tdId">
						<rightButton:rightButton name="�޸�" onclick="" className="btn_sec" action="/trade/trader/updateTrader.action" id="update"></rightButton:rightButton>
					</td>
					<td align="right">
						<button class="btn_sec" onClick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
</body>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>

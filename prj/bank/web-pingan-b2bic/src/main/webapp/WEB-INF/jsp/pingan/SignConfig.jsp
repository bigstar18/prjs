<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div id="SignConfigContent" class="pageContent">
	<form method="post" action="B2Bi_SignConfig_save?navTabId=${ navTabId }" class="pageForm required-validate changeSubmitForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>签名方式</dt>
				<dd>
					<s:select name="signVo.signMode" list="signModes" listKey="id" listValue="value" value="signVo.signMode"
						id="signMode_select"
						cssStyle="margin-right : 60px;"></s:select>
					<div class="button"><div class="buttonContent" style="margin-right: 10px;"><button type="button" id="signMode_chk_btn">签名测试</button></div></div>
					<div class="button"><div class="buttonContent"><button type="button" id="signMode_dnlist_btn">获取证书DN</button></div></div>
				</dd>
			</dl>
			<dl>
				<dt>证书DN</dt>
				<dd>
					<input id="signConf_certDN" type="text" name="signVo.certDn" value="${signVo.certDn}" size="80" readonly="readonly"/>
				</dd>
			</dl>
			
			<%--
			<dl>
				<dt>证书路径</dt>
				<dd>
					<input type="hidden" name="signVo.pfxPath" value="${signVo.pfxPath}" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt>证书密码</dt>
				<dd>
					<input type="hidden" name="signVo.pfxPwd" value="${signVo.pfxPwd}"/>
				</dd>
			</dl>
			<dl>
				<dt>CA证书链</dt>
				<dd>
					<input type="text" name="signVo.caCertPath" value="${signVo.caCertPath}" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt>CRL证书</dt>
				<dd>
					<input type="text" name="signVo.crlPath" value="${signVo.crlPath}" size="80"/>
				</dd>
			</dl>
			--%>
			<dl>
				<dt>签名交易代码</dt>
				<dd>
					<%--
					<button id="add_signTradeCode_btn" type="button">添加交易代码</button>
					<s:iterator value="signVo.signTradeCode">
						<div style="margin-top: 5px;">
							<input name="signTradeCodes" class="textInput" value="<s:property />"> 
							<button type="button" class="remove_signTradeCode">删除</button>
						</div>
					</s:iterator>
					--%>
					<textarea name="signTradeCodes" rows="10" cols="50" pattern="^\w+(((\r)?\n)+\w+)*$">${signTradeCodes}</textarea>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<s:if test="hasPermission('B2Bi_SignConfig_save')">
					<li><div class="buttonActive"><div class="buttonContent"><button id="signConfigSaveBtn" type="submit" disabled="disabled">保存配置</button></div></div></li>
				</s:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		
		<%--  
		<input type="hidden" name="signVo.caCertPath" value="${signVo.caCertPath}"/>
		<input type="hidden" name="signVo.crlPath" value="${signVo.crlPath}"/>
		<input type="hidden" name="signVo.pfxPath" value="${signVo.pfxPath}"/>
		<input type="hidden" name="signVo.pfxPwd" value="${signVo.pfxPwd}"/>
		--%>
	</form>
	
</div>

<script type="text/javascript">
$(function() {
	/*
	//添加签名交易代码
	$("#add_signTradeCode_btn").click(function() {
		$(this).parent().append($("<div style='margin-top: 5px;'><input name='signTradeCodes' class='textInput'> <button type='button' class='remove_signTradeCode'>删除</button></div>"));
	});
	//删除前面交易代码
	$(".remove_signTradeCode").live("click", function() {
		$(this).parent().remove();
	});
	*/
	
	//如果签名方式为soft，则证书路径和证书密码可用
	/*
	var $signMode = $("select[name='signVo.signMode']");
	var $pfxPath = $("input[name='signVo.pfxPath']");
	var $pfxPwd = $("input[name='signVo.pfxPwd']");
	var changePathAndPwdReadonly = function() {
		if($signMode.val() == "soft") {
			$pfxPath.attr("readonly", false);
			$pfxPwd.attr("readonly", false);
			$pfxPath.removeClass("readonly");
			$pfxPwd.removeClass("readonly");
		} else {
			$pfxPath.attr("readonly", true);
			$pfxPwd.attr("readonly", true);
			$pfxPath.addClass("readonly");
			$pfxPwd.addClass("readonly");
		}
	};
	$signMode.change(function() {
		changePathAndPwdReadonly();
	});
	changePathAndPwdReadonly();
	*/
	
	//签名方式信息查看
	$("#signMode_chk_btn").click(function() {
		/*
		$.pdialog.open("SignModeCheck", "signMode_chk_dialog", "签名测试", {
			width:"500",height:"280", params : "form", pageContent : $("#SignConfigContent")
		});
		*/
		ajaxTodo("SignModeCheck", null, {data : serializeParams($("#SignConfigContent"))});
	});
	
	//获取证书DN
	$("#signMode_dnlist_btn").click(function() {
		$.pdialog.open("FindDnList", "findDnList_dialog", "证书DN列表", {
			width:"500",height:"280", params : "form", pageContent : $("#SignConfigContent")
		});
	});
});
</script>
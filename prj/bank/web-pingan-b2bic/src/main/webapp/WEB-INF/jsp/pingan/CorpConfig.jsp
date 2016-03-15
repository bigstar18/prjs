<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form method="post" action="B2Bi_CorpConfig_save?navTabId=${ navTabId }" class="pageForm required-validate changeSubmitForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			<div class="panel" defH="130">
				<h1>前置接入服务配置</h1>
				<div>
					<dl>
						<dt>通讯协议</dt>
						<dd>
							<s:select name="corpVo.corpIn.protocol" list="#{ 'TCP' : 'TCP', 'http' : 'http' }" value="corpVo.corpIn.protocol"></s:select>
						</dd>
					</dl>
					<dl>
						<dt>监听端口</dt>
						<dd>
							<input type="text" name="corpVo.corpIn.port" value="${corpVo.corpIn.port}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>Socket超时(毫秒)</dt>
						<dd>
							<input type="text" name="corpVo.corpIn.soTimeout" value="${corpVo.corpIn.soTimeout}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>最大线程数</dt>
						<dd>
							<input type="text" name="corpVo.corpIn.maximumPoolSize" value="${corpVo.corpIn.maximumPoolSize}" digits="digits"/>
						</dd>
					</dl>
				</div>
			</div>
			<div class="panel" defH="230">
				<h1>接出服务配置</h1>
				<div>
					<dl>
						<dt>通讯协议</dt>
						<dd>
							<s:select name="corpVo.corpOut.protocol" list="#{ 'TCP' : 'TCP', 'http' : 'http' }" value="corpVo.corpOut.protocol"></s:select>
						</dd>
					</dl>
					<dl>
						<dt>企业服务端地址</dt>
						<dd>
							<input type="text" name="corpVo.corpOut.ips" value="${corpVo.corpOut.ips}" size="50"/>
						</dd>
					</dl>
					<dl>
						<dt>企业服务端端口</dt>
						<dd>
							<input type="text" name="corpVo.corpOut.ports" value="${corpVo.corpOut.ports}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>企业服务端URL</dt>
						<dd>
							<input type="text" name="corpVo.corpOut.url" value="${corpVo.corpOut.url}" size="50"/>
						</dd>
					</dl>
					<dl>
						<dt>Socket超时(毫秒)</dt>
						<dd>
							<input type="text" name="corpVo.corpOut.timeout" value="${corpVo.corpOut.timeout}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>下行报文规范</dt>
						<dd>
							<input type="radio" name="corpVo.downMode" value="0" <s:property value="corpVo.downMode==0 ? 'checked' : ''"/>/>222报文头+XML报文体
							<input type="radio" name="corpVo.downMode" value="1"  <s:property value="corpVo.downMode==1 ? 'checked' : ''"/>/>8位长度+XML报文体
						</dd>						
					</dl>					
					<dl>
						<dt>
							<div class="button"><div class="buttonContent"><button id="corpSocketCheck_btn" type="button">检测通讯</button></div></div>
						</dt>
					</dl>
				</div>
			</div>

			<div class="panel" defH="140">
				<h1>报文版本</h1>
				<div>
					<dl>
						<dt>是否标准报文格式</dt>
						<dd>
							<input type="radio" name="corpVo.normalFormat" value="true" <s:property value="corpVo.isNormalFormat() ? 'checked' : ''"/>/>标准
							<input type="radio" name="corpVo.normalFormat" value="false"  <s:property value="!corpVo.isNormalFormat() ? 'checked' : ''"/>/>非标准
						</dd>
					</dl>
					<dl>
						<dt>报文版本号</dt>
						<dd>
							<input type="text" name="corpVo.dataVersion" value="${corpVo.dataVersion}" class="corp_dataFormat"/>
						</dd>
					</dl>
					<dl>
						<dt>企业银企直连标准代码</dt>
						<dd>
							<input type="text" name="corpVo.corpCode" value="${corpVo.corpCode}" size="50" class="corp_dataFormat"/>
						</dd>
					</dl>
					<dl>
						<dt>默认交易代码</dt>
						<dd>
							<input type="text" name="corpVo.tranCode" value="${corpVo.tranCode}" size="50" class="corp_dataFormat"/>
						</dd>
					</dl>
				</div>
			</div>

			<div class="panel" defH="370">
				<h1>FTP服务器配置</h1>
				<div>
					<dl>
						<dt>文件上传下载结束后，是否主动通知企业</dt>
						<dd>
							<input type="radio" name="corpVo.fileNotify" value="true" <s:property value="corpVo.isFileNotify() ? 'checked' : ''"/>/>通知
							<input type="radio" name="corpVo.fileNotify" value="false"  <s:property value="!corpVo.isFileNotify() ? 'checked' : ''"/>/>不通知
						</dd>
					</dl>
					<dl>
						<dt>（企业服务器与前置系统间传文件）是否使用FTP</dt>
						<dd>
							<input type="radio" name="corpVo.useFtp" value="true" <s:property value="corpVo.isUseFtp() ? 'checked' : ''"/>/>使用
							<input type="radio" name="corpVo.useFtp" value="false"  <s:property value="!corpVo.isUseFtp() ? 'checked' : ''"/>/>不使用
						</dd>
					</dl>
					<dl>
						<dt>IP</dt>
						<dd>
							<input type="text" name="corpVo.corpFtp.hostname" value="${corpVo.corpFtp.hostname}" ipv4="ipv4" class="corp_usrFtp"/>
						</dd>
					</dl>
					<dl>
						<dt>端口</dt>
						<dd>
							<input type="text" name="corpVo.corpFtp.port" value="${corpVo.corpFtp.port}" digits="digits" class="corp_usrFtp"/>
						</dd>
					</dl>
					<dl>
						<dt>用户名</dt>
						<dd>
							<input type="text" name="corpVo.corpFtp.ftpname" value="${corpVo.corpFtp.ftpname}" class="corp_usrFtp"/>
						</dd>
					</dl>
					<dl>
						<dt>密码</dt>
						<dd>
							<input type="password" name="corpVo.corpFtp.ftppwd" value="${corpVo.corpFtp.ftppwd}" class="corp_usrFtp"/>
						</dd>
					</dl>
					<dl>
						<dt>socket读超时(ms)</dt>
						<dd>
							<input type="text" name="corpVo.corpFtp.soTimeout" value="${corpVo.corpFtp.soTimeout}" digits="digits" class="corp_usrFtp"/>
						</dd>
					</dl>
					<dl>
						<dt>企业FTP服务器默认目录或本地默认目录</dt>
						<dd>
							<input type="text" name="corpVo.corpFtp.defaultDir" value="${corpVo.corpFtp.defaultDir}" size="50" />
						</dd>
					</dl>
					<dl>
						<dt>
							<div class="button"><div class="buttonContent"><button id="corp_ftp_check_btn" type="button">检测通讯</button></div></div>
						</dt>
					</dl>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<s:if test="hasPermission('B2Bi_CorpConfig_save')">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" disabled="disabled">保存配置</button></div></div></li>
				</s:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

<script type="text/javascript">
$(function() {
	//当选择使用FTP时，FTP配置项可用，否则设置为只读状态
	$("input[name='corpVo.useFtp']").click(function() {
		if(this.value == "true") {
			$(".corp_usrFtp").attr("readonly", false);
			$(".corp_usrFtp").removeClass("readonly");
			//$("input[name='corpVo.fileNotify']").attr("disabled", false);
		} else {
			$(".corp_usrFtp").attr("readonly", true);
			$(".corp_usrFtp").addClass("readonly");
			//$("input[name='corpVo.fileNotify']").attr("disabled", true);
		}
	});
	
	$("input[name='corpVo.normalFormat']").click(function() {
		if(this.value == "false") {
			$(".corp_dataFormat").attr("readonly", false);
			$(".corp_dataFormat").removeClass("readonly");
		} else {
			$(".corp_dataFormat").attr("readonly", true);
			$(".corp_dataFormat").addClass("readonly");
		}
	});	

	//当接出配置选择http(s)时，url配置项可用，否则设置为只读状态
	var $protocol = $("select[name='corpVo.corpOut.protocol']");
	var $url = $("input[name='corpVo.corpOut.url']");
	var changeInput = function() {
		if($protocol.val().match(/^http*/)) {
			$url.attr("readonly", false);
			$url.removeClass("readonly");
		} else {
			$url.attr("readonly", true);
			$url.addClass("readonly");
		}
	};
	changeInput();
	$protocol.change(function() {
		changeInput();
	});
	
	//企业端检测通讯
	$("#corpSocketCheck_btn").click(function() {
		submitForm($(this).parents("form:first"), "CorpSocketCheck");
	});
	
	//FTP服务器检测通讯
	$("#corp_ftp_check_btn").click(function() {
		submitForm($(this).parents("form:first"), "CorpFtpCheck");
	});
});
</script>

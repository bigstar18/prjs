<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form method="post" action="B2Bi_BankConfig_save?navTabId=${ navTabId }" class="pageForm required-validate changeSubmitForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="56">
			
			<div class="panel" defH="100">
				<h1>前置接入服务配置</h1>
				<div>
					<dl>
						<dt>监听端口</dt>
						<dd>
							<input type="text" name="bankVo.bankIn.port" value="${bankVo.bankIn.port}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>Socket超时(毫秒)</dt>
						<dd>
							<input type="text" name="bankVo.bankIn.soTimeout" value="${bankVo.bankIn.soTimeout}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>最大线程数</dt>
						<dd>
							<input type="text" name="bankVo.bankIn.maximumPoolSize" value="${bankVo.bankIn.maximumPoolSize}" digits="digits"/>
						</dd>
					</dl>
				</div>
			</div>
			
			<div class="panel" defH="170">
				<h1>接出服务配置</h1>
				<div>
					<%-- 
					<dl>
						<dt>通讯协议</dt>
						<dd>
							<s:select name="bankVo.bankOut.protocol" list="#{ 'TCP' : 'TCP', 'http' : 'http', 'https' : 'https' }" value="bankVo.bankOut.protocol"></s:select>
						</dd>
					</dl>
					--%>
					<dl>
						<dt>银行服务端地址</dt>
						<dd>
							<input type="text" name="bankVo.bankOut.ips" value="${bankVo.bankOut.ips}" size="50"/>
						</dd>
					</dl>
					<dl>
						<dt>银行服务端端口</dt>
						<dd>
							<input type="text" name="bankVo.bankOut.ports" value="${bankVo.bankOut.ports}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>银行服务端URL</dt>
						<dd>
							<input type="text" name="bankVo.bankOut.url" value="${bankVo.bankOut.url}" size="50"/>
						</dd>
					</dl>
					<dl>
						<dt>Socket超时(毫秒)</dt>
						<dd>
							<input type="text" name="bankVo.bankOut.timeout" value="${bankVo.bankOut.timeout}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>
							<div class="button"><div class="buttonContent"><button id="bank_socket_check_btn" type="button">检测通讯</button></div></div>
						</dt>
					</dl>
				</div>
			</div>
			
			<div class="panel" defH="225">
				<h1>SFTP服务器配置</h1>
				<div>
					<dl>
						<dt>IP</dt>
						<dd>
							<input type="text" name="bankVo.bankFtp.hostname" value="${bankVo.bankFtp.hostname}" ipv4="ipv4"/>
						</dd>
					</dl>
					<dl>
						<dt>端口</dt>
						<dd>
							<input type="text" name="bankVo.bankFtp.port" value="${bankVo.bankFtp.port}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>用户名</dt>
						<dd>
							<input type="text" name="bankVo.bankFtp.ftpname" value="${bankVo.bankFtp.ftpname}"/>
						</dd>
					</dl>
					<dl>
						<dt>密码</dt>
						<dd>
							<input type="password" name="bankVo.bankFtp.ftppwd" value="${bankVo.bankFtp.ftppwd}"/>
						</dd>
					</dl>
					<dl>
						<dt>socket读超时(ms)</dt>
						<dd>
							<input type="text" name="bankVo.bankFtp.soTimeout" value="${bankVo.bankFtp.soTimeout}" digits="digits"/>
						</dd>
					</dl>
					<dl>
						<dt>远程FTP默认目录</dt>
						<dd>
							<input type="text" name="bankVo.bankFtp.defaultDir" value="${bankVo.bankFtp.defaultDir}" size="50"/>
						</dd>
					</dl>
					<dl>
						<dt>
							<div class="button"><div class="buttonContent"><button id="bank_sftp_check_btn" type="button">检测通讯</button></div></div>
						</dt>
					</dl>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<s:if test="hasPermission('B2Bi_BankConfig_save')">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" disabled="disabled">保存配置</button></div></div></li>
				</s:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		
		<input type="hidden" name="bankVo.bankOut.protocol" value="${ bankVo.bankOut.protocol }">
		<input type="hidden" name="bankVo.bankIn.protocol" value="${ bankVo.bankIn.protocol }">
	</form>
	
</div>

<script type="text/javascript">
$(function() {
	//当接出配置选择http(s)时，url配置项可用，否则设置为只读状态
	//var $protocol = $("select[name='bankVo.bankOut.protocol']");
	//var $url = $("input[name='bankVo.bankOut.url']");
	//var changeInput = function() {
	//	if($protocol.val().match(/^http*/)) {
	//		$url.attr("readonly", false);
	//		$url.removeClass("readonly");
	//	} else {
	//		$url.attr("readonly", true);
	//		$url.addClass("readonly");
	//	}
	//};
	//changeInput();
	//$protocol.change(function() {
	//	changeInput();
	//});
	
	//前置接入服务配置检测通讯
	$("#bank_socket_check_btn").click(function() {
		submitForm($(this).parents("form:first"), "BankSocketCheck");
	});
	
	//SFTP服务器检测通讯
	$("#bank_sftp_check_btn").click(function() {
		submitForm($(this).parents("form:first"), "BankSftpCheck");
	});
});
</script>

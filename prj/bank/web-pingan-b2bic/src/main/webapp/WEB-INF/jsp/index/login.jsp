<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:text name="ui.title" /></title>
<link href="dwz/management/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="dwz/management/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="dwz/management/themes/css/login.css" rel="stylesheet" type="text/css" />
<script src="dwz/management/js/jquery-1.7.2.min.js" type="text/javascript"></script>

<script type="text/javascript">
function toggleBox(boxId){
	var $box = $("#"+boxId);
	if ($box.is(":visible")) $box.slideUp();
	else $box.slideDown();
}
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="dwz/management/themes/default/images/logo.png" />
			</h1>

			<div class="login_headerContent">
				<div class="navList">
					<!--  
					<ul>
						<li><a href="/management/index!index.do">Home</a></li>
						<li><a href="http://weibo.com/dwzui" target="_blank">微博</a></li>
					</ul>
					-->
				</div>
				<h2 class="login_title">用户登录</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form method="post" action="app">
					<p>
						<label>用户名：</label>
						<input type="text" name="user.userId" class="login_input" value="${ requestScope.user.userId }" style="width: 140px;"/>
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;码：</label>
						<input type="password" name="user.userPwd" value="${ requestScope.user.userPwd }" class="login_input" style="width: 140px;"/>
					</p>
					
					<!--<p>
						<label>验证码：</label>
						<input class="code" type="text" size="5" />
						<span><img src="themes/default/images/header_bg.png" alt="" width="75" height="24" /></span>
					</p>-->
					
					<div class="login_bar" style="padding-left: 81px;padding-top: 12px;">
						<input class="sub" type="submit" value=""/>
					</div>
					
					<p style="padding-left: 79px;padding-top: 20px;" title="要使用此功能，请不要禁用或删除浏览器的cookie或者历史记录">
						<input name="logout" type="checkbox" value="true" <s:property value=" checkRememberPwd() ? 'checked' : '' "/>/> 
						<span class="rememberPwd" onclick="$(this).prev('input').attr('checked', !$(this).prev('input').attr('checked'))">记住用户名和密码 ?</span>
					</p>
				</form>
			</div>
			<div class="login_banner"><img src="dwz/management/themes/default/images/login_banner.jpg" /></div>
			<div class="login_main">
				<ul class="helpList">
					<li><a style="${! empty loginMsg ? 'color: red' : ''}">${ !empty loginMsg ? loginMsg : '请输入用户名和密码' }</a></li>
					<li id="forgotPwd" style="background: none; display: none">
					<form method="post" action="/myaccount/member.do?method=forgetPassword" onsubmit="return validateCallback(this);">
						<p>
							<label>Username：</label>
							<input type="text" name="userName" class="textInput required" size="20"/>
							<span class="info">Please enter your username to retrieve your password.</span>
						</p>
						<p><input type="submit" value="Submit"/></p>
					</form>
					</li>
				</ul>

				<div class="login_inner">
					<!--  
					<p>Test User: admin</p>
					<p>Password: 123456</p>
					-->
				</div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2014 PING AN BANK OF CHINA ,LTD. All Rights Reserved
		</div>
	</div>
</body>
</html>

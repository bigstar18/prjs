<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title><s:text name="ui.title" /></title>
		
		<link href="dwz/management/themes/green/style.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="dwz/management/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="dwz/management/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
		<link href="dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
		
		<!--[if IE]>
		<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
		<![endif]-->
		
		<!--[if lte IE 9]>
		<script src="dwz/management/js/speedup.js" type="text/javascript"></script>
		<![endif]-->
		
		<!--[if gt IE 6]>
		<script type="text/javascript" src="dwz/management/js/raphael-min.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.raphael.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.bar.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.line.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.pie.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.dot.js"></script>
		<![endif]-->
		
		<!--[if !IE]>
		<script type="text/javascript" src="dwz/management/js/raphael-min.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.raphael.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.bar.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.line.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.pie.js"></script>
		<script type="text/javascript" src="dwz/management/js/g.dot.js"></script>
		<![endif]--> 
		
		<script src="dwz/management/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script src="dwz/management/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="dwz/management/js/jquery.validate.min.js" type="text/javascript"></script>
		<script src="dwz/management/js/additional-methods.js" type="text/javascript"></script>
		<script src="dwz/management/js/jquery.bgiframe.js" type="text/javascript"></script>
		<script src="dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
		<script src="dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>
		
		<script src="dwz/management/js/dwz.min.js" type="text/javascript"></script>
		<script src="dwz/management/js/dwz.regional.zh.js" type="text/javascript"></script>
		
		<script src="eteller/js/comm.js" type="text/javascript"></script>
		<script src="eteller/js/app.js" type="text/javascript"></script>
		<script type="text/javascript" src="eteller/js/bodystyle.js"></script>
		
	</head>

	<body scroll="no">
		<div id="layout">
			<div id="header">
				<div class="headerNav">
					<a class="logo">Logo</a>
					<ul class="nav">
						<li><a style="line-height: 13px;" href="">主页</a></li>
						<li><a style="line-height: 13px;" href="linkPage?path=usermanager/editPwd.jsp" target="dialog" mask="true" width="505" height="200">修改密码</a></li>
						<li><a style="line-height: 13px;" href="logout">登出</a></li>
					</ul>
					<ul class="themeList" id="themeList">
						<li theme="default"><div>blue</div></li>
						<li theme="azure"><div>天蓝</div></li>
						<li theme="green"><div class="selected">green</div></li>
						<li theme="purple"><div>purple</div></li>
						<li theme="silver"><div>silver</div></li>
					</ul>
				</div>
			</div>
			
			<%@ include file="/WEB-INF/jsp/leftside.jsp" %>
			
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
							<ul class="navTab-tab">
								<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">My Home</span></span></a></li>
							</ul>
						</div>
						<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
						<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
						<div class="tabsMore">more</div>
					</div>
					<ul class="tabsMoreList">
						<li><a href="javascript:void(0)">My Home</a></li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox">
						<div>
							<div class="accountInfo">
								<div class="right">
									<p><s:property value="@cn.com.agree.eteller.generic.utils.DateUtil@getIndexDate()"/></p>
								</div>
								<p><span>欢迎, ${ sessionScope.user.username }</span></p>
							</div>
							
							<div id="mainTabContent" class="pageFormContent" layoutH="70" style="padding-left: 0;padding-right: 0px;padding-bottom: 0px;">
								<center><span style="font-size: 30px;font-weight: bold;">系统公告</span></center>
								<iframe id="notice_frame" src="NoticeDisplay?notice.id=1" frameborder="0" style="width : 100%;overflow: auto;" layoutH="101"></iframe>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="footer"><s:text name="ui.copyrights" /></div>
	
	</body>
</html>
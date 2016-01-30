<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%//前台登录界面 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" /><!-- 设定网站文本内容编码 -->
	<meta name="description" content="" /><!-- 设定网站简介 -->
	<meta name="keywords" content="" /><!-- 设定网站关键字 -->
	<meta name="generator" content="" /><!-- 设定网站使用的制作软件 -->
	<meta name="copyright" content="" /><!-- 设定网站版权所属 -->
	<title>登录系统</title>
	<link rel="stylesheet" href="../../front/logon/registerwx.css"/>
	<link href="${skinPath}/css/mgr/logon/layout.css" rel="stylesheet" type="text/css"/>
	<link href="${skinPath}/css/mgr/logon/webwidget_scroller_tab.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/template.css" type="text/css" />
	<script src="${publicPath}/js/jquery-1.6.2.min.js" type="text/javascript"></script>
	<script src="${publicPath}/js/webwidget_scroller_tab.js" type="text/javascript"></script>
	<script src="${publicPath}/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
	<script src="${publicPath}/js/jquery.validationEngine.js" type="text/javascript"></script>
	<script>
	  var phoneWidth = parseInt(window.screen.width);
	  var phoneScale = phoneWidth/360;
	  var ua = navigator.userAgent;
	  if (/Android (\d+\.\d+)/.test(ua)){
	    var version = parseFloat(RegExp.$1);
	    if(version>2.3){
	      document.write('<meta name="viewport" content="width=360,initial-scale='+phoneScale+', minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
	    }else{
	      document.write('<meta name="viewport" content="width=360, target-densitydpi=device-dpi">');
	    }
	  } else {
	    document.write('<meta name="viewport" content="width=360, user-scalable=no, target-densitydpi=device-dpi">');
	  }
	</script>
</head>
<body>
<!-- ====== Header Begin ====== -->
<!-- Top -->
<div class="top_bg">
<div class="top">
	<c:if test="${not empty CurrentUser}">
	<div class="top_left"><a href="${frontPath}/frame/index.jsp" class="link_orange">${CurrentUser.name}</a>&nbsp;您好，欢迎来到<%=Global.getMarketInfoMap().get("marketName")%><a href="${frontPath}/app/mgr/user/logout.action" class="link_blue">[退出]</a></div>
	</c:if>
	<c:if test="${empty CurrentUser}">
	<div class="top_left">您好，欢迎来到<%=Global.getMarketInfoMap().get("marketName")%><a href="${frontPath}/logon/logon.jsp" class="link_blue">[请登录]</a>
	</div>
	</c:if>
    <div class="top_right">
    	<ul>
        	<li><a>&nbsp;</a></li>
        </ul>
    </div>
</div>
</div>
<!-- End Top -->
<!-- Logo and search Begin -->
<div class="header_bg">
<div class="wrap">
<!-- Logo -->
<div class="logo"><img src="../../front/logon/logo.jpg" /></div>
<!-- End Logo -->
<!-- search -->
<!-- End search -->
<div class="clear"></div>
</div>
</div>
<!-- End Logo and search Begin -->
<!-- Nav -->
<!-- End Nav -->

<!-- ====== Header End ====== -->

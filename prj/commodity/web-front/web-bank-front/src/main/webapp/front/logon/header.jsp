<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%//ǰ̨��¼���� %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" /><!-- �趨��վ�ı����ݱ��� -->
	<meta name="description" content="" /><!-- �趨��վ��� -->
	<meta name="keywords" content="" /><!-- �趨��վ�ؼ��� -->
	<meta name="generator" content="" /><!-- �趨��վʹ�õ�������� -->
	<meta name="copyright" content="" /><!-- �趨��վ��Ȩ���� -->
	<title>��¼ϵͳ</title>
	<link href="${skinPath}/css/mgr/logon/layout.css" rel="stylesheet" type="text/css"/>
	<link href="${skinPath}/css/mgr/logon/webwidget_scroller_tab.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/template.css" type="text/css" />
	<script src="${publicPath}/js/jquery-1.6.2.min.js" type="text/javascript"></script>
	<script src="${publicPath}/js/webwidget_scroller_tab.js" type="text/javascript"></script>
	<script src="${publicPath}/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
	<script src="${publicPath}/js/jquery.validationEngine.js" type="text/javascript"></script>
</head>
<body>
<!-- ====== Header Begin ====== -->
<!-- Top -->
<div class="top_bg">
<div class="top">
	<c:if test="${not empty CurrentUser}">
	<div class="top_left"><a href="${frontPath}/frame/index.jsp" class="link_orange">${CurrentUser.name}</a>&nbsp;���ã���ӭ����<%=Global.getMarketInfoMap().get("marketName")%>�ֻ���<a href="${frontPath}/app/mgr/user/logout.action" class="link_blue">[�˳�]</a></div>
	</c:if>
	<c:if test="${empty CurrentUser}">
	<div class="top_left">���ã���ӭ����<%=Global.getMarketInfoMap().get("marketName")%>�ֻ���<a href="${frontPath}/logon/logon.jsp" class="link_blue">[���¼]</a>
	<a href="${basePath}/checkneedless/firmapply/registerforward.action" class="link_orange">���ע��</a></div>
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
<div class="logo"><img src="${frontPath}/image/logo.gif" /></div>
<div class="login_title">
<div class="phone"><img src="${skinPath}/image/logon/phone.gif" width="15" height="15" />&nbsp;���������벦�绰��<%=Global.getMarketInfoMap().get("marketPhone")%></div>
<div class="name">${pageTitle}</div>

</div>
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
<c:if test="${sitemap==null}">
<div class="wrap">
<jsp:include page="/front/frame/current.jsp"></jsp:include>
</div>
</c:if>
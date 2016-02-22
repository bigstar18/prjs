<%@ page contentType="text/html;charset=GBK"%>

<%@page import="gnnt.MEBS.base.util.SpringContextHelper"%>
<%@page import="gnnt.MEBS.account.model.Trader"%>
<%@page import="gnnt.MEBS.settlement.model.Firm"%>
<%@page import="gnnt.MEBS.account.service.TraderService"%>
<%@page import="gnnt.MEBS.settlement.service.FirmService"%>

<%@ include file="../public/common.jsp"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link rel="stylesheet" href="skin/default/css/style.css"
			type="text/css" />
		<title>大宗商品电子交易管理系统</title>
	</head>
	<%
		TraderService traderService=(TraderService)SpringContextHelper.getBean("traderService");
		FirmService firmService=(FirmService)SpringContextHelper.getBean("firmService");
		Trader trader = (Trader)traderService.getById((String) request.getSession().getAttribute("username"));
		Firm firm = (Firm) firmService.getById(trader.getFirmID());
		String type = firm.getFirmType();
		request.getSession().setAttribute("type", firm.getFirmType());
	%>
	<frameset rows="30,*" cols="*" frameborder="NO" border="0"
		framespacing="0">
		<frame src="<%=basePath%>/common/surface/reportLeftMenu.jsp"
			name="menu" scrolling="NO" noresize APPLICATION="yes">
		<c:if test="${type=='C' }">
			<frame
				src="<%=basePath%>/report/queryReport/customerReportQuery.action?LOGINID=${LOGINID}&username=${username}"
				name="leftFrame" scrolling="yes" noresize APPLICATION="yes">
		</c:if>
		<c:if test="${type=='M' }">
			<frame
				src="<%=basePath%>/report/queryReport/memberReportQuery.action?LOGINID=${LOGINID}&username=${username}"
				name="leftFrame" scrolling="yes" noresize APPLICATION="yes">
		</c:if>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！
		</body>
	</noframes>
</html>
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>	
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">	
		<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
		<title>������Ʒ���ӽ��׹���ϵͳ</title>
	</head>
	<frameset rows="55,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="noticeLeftMenu.jsp?LOGINID=${LOGINID}&username=${username}" name="menu" scrolling="NO" noresize APPLICATION="yes">
		<frame src="<%=basePath %>/tradeManage/lookAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}" name="leftFrame" scrolling="yes" noresize APPLICATION="yes">
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>
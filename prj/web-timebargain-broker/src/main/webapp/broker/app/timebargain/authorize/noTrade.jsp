<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��Ϊ����</title>
	</head>
	<frameset rows="*,0" border="0">    
	  	<frame name="ListFrame" src="<c:url value="/timebargain/authorize/chkLogin.action?mkName=noTrade"/>"  application="yes">
	  	<frame name="HiddFrame"  application="yes">
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>

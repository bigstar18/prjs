<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head><!-- ������IFRAMEʱ,�ǵ�Ҫ����Ӧ�Ķ�̬ҳ��ҳͷ���һ��P3P����Ϣ,
	����IE���Ծ��İ�IFRAME�����COOKIE����ֹ��,��������.����������Ȼ��ȡ������
	�����ʵ��FRAMESET��COOKIE������,��FRAME����IFRAME��������. -->
	<% response.addHeader("P3P","CP=CAO PSA OUR"); %>
		<title>�ֿ����ϵͳ</title>
	</head>
	<frameset id=middle cols="185,10,*" frameborder="NO" border="0"
		framespacing="0">
		<frame src="<%=basePath%>/menu/menuList.action" name="leftFrame"
			scrolling="NO" noresize APPLICATION="yes">
		<frame src="<%=framePath%>/shrinkbar.jsp" name="mainSwitch"
			scrolling="NO" noresize APPLICATION="yes" id="mainSwitch">
		<frame src="<%=framePath%>/rightframe.jsp"
			name="workspace" APPLICATION="yes">
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ���
		</body>
	</noframes>
</html>
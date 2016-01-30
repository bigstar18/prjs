<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head><!-- 当利用IFRAME时,记得要在相应的动态页的页头添加一下P3P的信息,
	否则IE会自觉的把IFRAME框里的COOKIE给阻止掉,产生问题.本身不保存自然就取不到了
	这个其实是FRAMESET和COOKIE的问题,用FRAME或者IFRAME都会遇到. -->
	<% response.addHeader("P3P","CP=CAO PSA OUR"); %>
		<title>仓库管理系统</title>
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
			对不起，您的浏览器不支持框架集！
		</body>
	</noframes>
</html>
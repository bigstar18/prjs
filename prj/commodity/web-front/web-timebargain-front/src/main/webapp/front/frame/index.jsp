<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>我的平台</title>
		<link href="${skinPath}/css/mgr/memberadmin/main.css" rel="stylesheet" type="text/css" />
		<link href="${skinPath}/css/mgr/memberadmin/mymenu.css" rel="stylesheet" type="text/css"/>
		<script src="${publicPath}/js/tool.js"></script>
		<script src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script src="${publicPath}/js/mymenu.js"></script>
		<script>
			function iframe(id,height){
				Util.IFrameAuto(id,true,false,height);
			}
			//执行主功能界面的回掉函数
			function showMsgBoxCallbak(result,msg){
				if(result==11){
					window.location.href=window.location.href;
				}
				if(typeof main.showMsgBoxCallbak == 'undefined'){
				}else{
				    main.showMsgBoxCallbak(result,msg);
				}
			}
		</script>
	</head>
	<body>
		<div class="top"><jsp:include page="header.jsp"></jsp:include></div>
		<div class="left"><jsp:include page="/menu/menuList.action"></jsp:include></div>
		<div class="right">
		<iframe frameborder="0" id="main" name="main"  width="100%" scrolling="no" onLoad="iframe(this.id,0)" src="${basePath}/menu/homepage.action"></iframe>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
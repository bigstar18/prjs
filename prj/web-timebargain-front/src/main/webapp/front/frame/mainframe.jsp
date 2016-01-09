<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%//主显示框架 %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<!-- 当利用IFRAME时,记得要在相应的动态页的页头添加一下P3P的信息,
	否则IE会自觉的把IFRAME框里的COOKIE给阻止掉,产生问题.本身不保存自然就取不到了
	这个其实是FRAMESET和COOKIE的问题,用FRAME或者IFRAME都会遇到. -->
	<% response.addHeader("P3P","CP=CAO PSA OUR"); %>
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
			function showMsgBoxCallbak(result,msg,resultType){
				if(resultType==11){
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
		<div class="left"><jsp:include page="/menu/menuList.action"></jsp:include></div>
		<div class="right">
		<iframe frameborder="0" id="main" name="main" height="1000" width="100%" scrolling="no" onLoad="iframe(this.id,0)" src="${basePath}/menu/homepage.action"></iframe>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
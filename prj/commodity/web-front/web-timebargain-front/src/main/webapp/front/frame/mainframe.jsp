<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%//����ʾ��� %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<!-- ������IFRAMEʱ,�ǵ�Ҫ����Ӧ�Ķ�̬ҳ��ҳͷ���һ��P3P����Ϣ,
	����IE���Ծ��İ�IFRAME�����COOKIE����ֹ��,��������.����������Ȼ��ȡ������
	�����ʵ��FRAMESET��COOKIE������,��FRAME����IFRAME��������. -->
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
			//ִ�������ܽ���Ļص�����
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
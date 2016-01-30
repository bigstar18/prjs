<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%//�ҵ�ƽ̨��� %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<script type="text/javascript">
			var logonUser='${sessionScope.CurrentUser.traderID}';
			var serverName='${serverName}';
		</script>
		<title>�ҵ�ƽ̨</title>
		<link href="${skinPath}/css/mgr/memberadmin/main.css" rel="stylesheet" type="text/css" />
		<script src="${publicPath}/js/tool.js"></script>
		<script src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script src="${publicPath}/js/jquerymessage/Messagedialog.js"></script>
		<script src="${publicPath}/js/jms/amq_jquery_adapter.js"></script>
		<script src="${publicPath}/js/jms/amq.js"></script>
		<script src="${publicPath}/js/jquerymessage/showmessage.js"></script>
		<script>
			//���һ����ת��moduleID
			var lastToModuleID = '${param.ModuleID}';//��ƽ̨ҳ����ת����
			if(lastToModuleID == ''){//������Ǵ�ƽ̨��ת����,���Ǵӹ��õ�¼��
				lastToModuleID = '${CurrentUser.moduleID}';
			}
			//�������ȥͷ����ĸ߶�
			var framehight = 0;
			//Ĭ�ϵ�iframeID
			var defaultiframe = "common_front1";
			$(document).ready(function() {
				framehight = document.documentElement.clientHeight-117;
				$("#"+defaultiframe).attr("height",framehight);
				//ִ��Ĭ��ϵͳ�ĵ��
				if(lastToModuleID==99){
					$("#defaulta").click();
				}else{
					var indexNow = 0 ;
					$(".ico div a").each(function(index){//��ȡλ��,������0��ʼ
						if($(this).attr("moduleid")==lastToModuleID){
							indexNow = index+1;
						}
					});
					if(indexNow>number){//��Ҫ��ʾ�Ĵ����Ѿ���ʾ�ĸ���
						var indexsum = parseInt((indexNow-1)/number);//��������
						for ( var i =  0 ; i< indexsum ;i++) {
							$("#next").click();
						}
					}
					//�л����
					$("a[moduleid='"+lastToModuleID+"']").click();
				}
			});
			//ɾ��Ĭ�ϵ� iframe
			function removedefaultiframe(){
				$(document).find("iframe[class='firstframe'][id="+defaultiframe+"]").remove();
			}
			//��ȡĬ�ϵ� iframe ����
			function getdefaultiframesize(){
				return $(document).find("iframe[class='firstframe'][id="+defaultiframe+"]").size();
			}
			/**
			 * ��·����β���ϵ�¼�û�sessionID
			 */
			function srcAddSessionID(src,moduleid){
				if(src.indexOf("?")>0){
					src = src+"&<%=Global.SESSIONID%>=${CurrentUser.sessionId}";
				}else{
					src = src+"?<%=Global.SESSIONID%>=${CurrentUser.sessionId}";
				}
				src = src + "&<%=Global.USERID%>=${CurrentUser.traderID}";
				src = src + "&<%=Global.FROMMODULEID%>="+lastToModuleID;
				src = src + "&<%=Global.FROMLOGONTYPE%>=${CurrentUser.logonType}";
				src = src + "&<%=Global.SELFLOGONTYPE%>=${CurrentUser.logonType}";
				//��ȡ��ǰ���ʵ�·���Ƿ���Է���,���������ı��ϴ�������ϵͳ
				 var srcstatus = $.ajax({url:src,async:false}).status;
				 if(srcstatus==200){
					lastToModuleID = moduleid
				 }
				return src;
			}
			/**
			 * ��ȡ����ǰ׺
			 */
			function getPrefixPath(obj){
				var prefixPath = obj.attr('prefixPath');
				if(!prefixPath){
					prefixPath = "${serverPath}";
				}
				
				if(!(prefixPath.charAt(prefixPath.length-1)=="/") && !(prefixPath.charAt(prefixPath.length-1)=="\\")){
					prefixPath = prefixPath + "/";
				}
				
				prefixPath = prefixPath + obj.attr('context');
				
				return prefixPath;
			}
			/**
			 * ÿ���л�ϵͳ��ʱ�����һ�� ajax ��������ֹ��ǰ�л���ϵͳ���ڳ�ʱ�䲻�ö���ʱ�ˡ�
			 */
			function sessionGetUser(obj){
				//��ȡ��Ŀ��·��
				var prefixPath = getPrefixPath(obj);
				//��ȡ ajax ����·��
				var src = prefixPath + "/checkneedless/communications/sessionGetUser.action?jsoncallback=?";
				//��·������ sessionID
				src = srcAddSessionID(src,obj.attr('moduleid'));
				//��ȡ ajax ԭ�����ύ��ʽ
				var oldAjaxAsync = $.ajaxSettings.async;
				//���� ajax ͬ��
				$.ajaxSettings.async = false;
				//ִ�� ajax
				$.getJSON(src,null,function call(result){
					
				});
				//���û� ajax ԭ�����ύ��ʽ
				$.ajaxSettings.async = oldAjaxAsync;
			}
			//��ȡ����ϵͳ��ҳ��url
			function getSRC(obj){
				//��ȡ��Ŀ��·��
				var prefixPath = getPrefixPath(obj);
				//��ȡҪ��ҳ���λ��
				var relativePath = obj.attr('relativePath')!=null?obj.attr('relativePath'):"";
				//�ϲ��� src ·��
				var src = prefixPath+relativePath;
				//�� src ���� sessionID ����
				src = srcAddSessionID(src,obj.attr('moduleid'));
				//����·��
				return src;
			}
			//�˳�����ϵͳ
			function logoutother(){
				$(document).find("#modulebtndiv a").each(function(){
					//��ȡ��Ŀ��·��
					var prefixPath = getPrefixPath($(this));
					//��ȡ ajax ����·��
					var src = prefixPath + "/checkneedless/communications/logout.action?jsoncallback=?";
					//��·������ sessionID
					src = srcAddSessionID(src,$(this).attr('moduleid'));
					//��ȡ ajax ԭ�����ύ��ʽ
					var oldAjaxAsync = $.ajaxSettings.async;
					//���� ajax �첽
					$.ajaxSettings.async = true;
					//ִ�� ajax
					$.getJSON(src,null,function call(result){
						
					});
					//���û� ajax ԭ�����ύ��ʽ
					$.ajaxSettings.async = oldAjaxAsync;
				});
			}

			// ��ǰѡ�еĵڼ���
			var cselect = 0;
			// ֮ǰѡ�еĵڼ���
			var pselect = 3;
			// �����л��ڼ���
			var uselect = 0;
			// ��ʼֵ
			var bb = 0;
			// ����ֵ
			var fb = 20;
			//�л�ʱ�䵥λ����
			var time = 100;
			//frame����
			var countFrame = 0;
			//��ǰѡ�е�frame
			var curSelectedFrame;
			//�жϵ�ǰ�Ƿ���ϵͳ���ڼ��أ�������ڼ���������ִ���µļ���
			var isrun = false;
			/**
			 * ����:�л�ϵͳʱ����
			 * ���� :a�Ķ���obj
			 */
			function clickChange(obj){
				if(isrun){
					return;
				}
				sessionGetUser(obj);
				isrun = true;
				//�õ�context
				var context = obj.attr('context');
				$(".icoch").attr("class","ico");
				obj.parent().parent().attr("class","icoch");
				// �����ǰ��ʾ��frame���ǵ�ǰѡ��curSelectedFrame��ֱ�ӷ���
				if($(document).find("iframe[class='firstframe'][visibility='visible'][id="+context+"]").size()>0){
					isrun=false;
					return ;
				}else if($(document).find("iframe[class='firstframe'][id="+context+"]").size()==0){
					//�����µ�frame
					var childFrame = document.createElement("iframe");
					childFrame.id = context ;
					childFrame.className="firstframe";
					childFrame.frameborder="0";
					childFrame.width="0";
					childFrame.height=framehight;
				 	//���ǰ׺�Ѿ�������ֱ��ʹ�÷����path.jsp�л�ȡbasepath��Ϊǰ׺ ��ʽ�������� http://172.16.1.115:8080/
					$('#parentdiv').append(childFrame);
					var src = getSRC(obj);
					childFrame.src= src;
					//����frame����
					if (!/*@cc_on!@*/0) {//��IE�����
						childFrame.onload = function(){
							removedefaultiframe();
							//�ҵ���ʾ��frame���ص� ȥ������
							$(document).find("iframe[class='firstframe']").each(function(){
								$(this).attr('visibility','hidden');
								$(this).attr('width',0);
								if($(this).attr('height')>0 && $(this).attr("id") != childFrame.id){
									$(this).attr('oldheight',$(this).attr('height'));
									$(this).attr('height',0);
								}
								$(this).css('visibility','hidden');
							});
							childFrame.visibility="visible";
							childFrame.width="100%";
							childFrame.style.visibility='visible';
							isrun=false;
						};
					} else {//IE�����
						childFrame.onreadystatechange = function(){
							removedefaultiframe();
							//�ҵ���ʾ��frame���ص� ȥ������
							$(document).find("iframe[class='firstframe']").each(function(){
								$(this).attr('visibility','hidden');
								$(this).attr('width',0);
								if($(this).attr('height')>0 && $(this).attr("id") != childFrame.id){
									$(this).attr('oldheight',$(this).attr('height'));
									$(this).attr('height',0);
								}
								$(this).css('visibility','hidden');
							});
							childFrame.visibility="visible";
							childFrame.width="100%";
							childFrame.style.visibility='visible';
							isrun=false;
						};
					}
				}else{
					var countFrame =$(document).find("iframe[class='firstframe']");
					for(var i=0;i<countFrame.size();i++){
						var frame = countFrame.get(i);
						if(frame.id==context){
							cselect=i;
							frame.visibility='visible';
							if(frame.src==""){
								var src = getSRC(obj);
								frame.src=src;
							}
						}else if(frame.visibility=='visible'){
							pselect=i;
							frame.visibility='hidden';
						}
						frame.width = 0;
						if(frame.height>0){
							frame.oldheight = frame.height;
							frame.height = 0;
						}
						frame.style.visibility='hidden';
					}
					$(document).find("iframe[class='firstframe'][id="+context+"]")[0].width='100%';
					$(document).find("iframe[class='firstframe'][id="+context+"]")[0].height=framehight;
					$(document).find("iframe[class='firstframe'][id="+context+"]")[0].style.visibility='visible';
					/*
					bb = 0;//��ʼ����ʼֵΪ0
					uselect = 0;//��ʼ����ʼ���л�ϵͳʹ���е�ϵͳ��λ��
					if (pselect < cselect) {//֮ǰѡ�е�С������ѡ�е�
						colsAdd();
					} else {//֮ǰѡ�еĴ�������ѡ�е�
						colsReduce();
					}
					*/
					isrun=false;
				}
			}

			//------------------�޸� Iframe ���-----��ʼ---------------
			//��ѡ���еĴ��ڵ�ǰ��ʾ��,���󻬶�
			function colsAdd() {
				countFrame = $(document).find("iframe[class='firstframe']");
				bb = fb + bb;
				if (uselect == 0) {
					uselect = pselect + 1;
				}
				if (bb <= 100) {
					for (var i = 0; i < countFrame.size(); i++) {
						if (uselect == i) {
							countFrame.get(i).width = bb + "%";
						} else if (uselect - 1 == i) {
							if(99-bb>0){
								countFrame.get(i).width = (99 - bb) + "%";
							}else{
								countFrame.get(i).width ="0";
							}
						} else {
							countFrame.get(i).width ="0";
						}
					}
					setTimeout("colsAdd()", time);
				}else{
					if ( uselect < cselect) {
						uselect = uselect + 1;
						bb = 0;
						colsAdd();
					}
				}
			}
			//��ѡ���е�С�ڵ�ǰ��ʾ��,���һ���
			function colsReduce() {
				countFrame = $(document).find("iframe[class='firstframe']");
				bb = fb + bb;
				if (uselect == 0) {
					uselect = pselect;
				}
				if (bb <= 100&&uselect > cselect) {
					for (var i = 0; i < countFrame.size(); i++) {
						if (uselect == i) {
							if(99-bb>0){
								countFrame.get(i).width = (99 - bb) + "%";
							}else{
								countFrame.get(i).width = "0";
							}
						} else if (uselect - 1 == i) {
							countFrame.get(i).width = bb + "%";
						} else {
							countFrame.get(i).width = "0";
						}
					}
					setTimeout("colsReduce()", time);
				}else{
					uselect = uselect - 1;
					bb = 0;
                    if ( uselect > cselect) {
					 colsReduce();
					}
				}
			}
			//------------------�޸� Iframe ���-----����---------------
			
		</script>
	</head>
	<body >
		<div class="top"><jsp:include page="header.jsp"></jsp:include></div>
		<div id="parentdiv">
		<iframe class="firstframe" frameborder="0" id="common_front1" visibility="visible" width="100%" src="${basePath}/front/frame/loading.jsp"></iframe>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
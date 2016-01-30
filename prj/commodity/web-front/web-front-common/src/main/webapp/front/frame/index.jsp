<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%//我的平台框架 %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<script type="text/javascript">
			var logonUser='${sessionScope.CurrentUser.traderID}';
			var serverName='${serverName}';
		</script>
		<title>我的平台</title>
		<link href="${skinPath}/css/mgr/memberadmin/main.css" rel="stylesheet" type="text/css" />
		<script src="${publicPath}/js/tool.js"></script>
		<script src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script src="${publicPath}/js/jquerymessage/Messagedialog.js"></script>
		<script src="${publicPath}/js/jms/amq_jquery_adapter.js"></script>
		<script src="${publicPath}/js/jms/amq.js"></script>
		<script src="${publicPath}/js/jquerymessage/showmessage.js"></script>
		<script>
			//最后一次跳转的moduleID
			var lastToModuleID = '${param.ModuleID}';//从平台页面跳转过来
			if(lastToModuleID == ''){//如果不是从平台跳转来的,则是从公用登录的
				lastToModuleID = '${CurrentUser.moduleID}';
			}
			//浏览器出去头部后的高度
			var framehight = 0;
			//默认的iframeID
			var defaultiframe = "common_front1";
			$(document).ready(function() {
				framehight = document.documentElement.clientHeight-117;
				$("#"+defaultiframe).attr("height",framehight);
				//执行默认系统的点击
				if(lastToModuleID==99){
					$("#defaulta").click();
				}else{
					var indexNow = 0 ;
					$(".ico div a").each(function(index){//获取位置,索引从0开始
						if($(this).attr("moduleid")==lastToModuleID){
							indexNow = index+1;
						}
					});
					if(indexNow>number){//需要显示的大于已经显示的个数
						var indexsum = parseInt((indexNow-1)/number);//滚动几下
						for ( var i =  0 ; i< indexsum ;i++) {
							$("#next").click();
						}
					}
					//切换点击
					$("a[moduleid='"+lastToModuleID+"']").click();
				}
			});
			//删除默认的 iframe
			function removedefaultiframe(){
				$(document).find("iframe[class='firstframe'][id="+defaultiframe+"]").remove();
			}
			//获取默认的 iframe 长度
			function getdefaultiframesize(){
				return $(document).find("iframe[class='firstframe'][id="+defaultiframe+"]").size();
			}
			/**
			 * 将路径结尾加上登录用户sessionID
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
				//获取当前访问的路径是否可以访问,如果正常则改变上次正常的系统
				 var srcstatus = $.ajax({url:src,async:false}).status;
				 if(srcstatus==200){
					lastToModuleID = moduleid
				 }
				return src;
			}
			/**
			 * 获取连接前缀
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
			 * 每次切换系统的时候调用一下 ajax 方法，防止以前切换的系统由于长时间不用而超时了。
			 */
			function sessionGetUser(obj){
				//获取项目根路径
				var prefixPath = getPrefixPath(obj);
				//获取 ajax 完整路径
				var src = prefixPath + "/checkneedless/communications/sessionGetUser.action?jsoncallback=?";
				//给路径增加 sessionID
				src = srcAddSessionID(src,obj.attr('moduleid'));
				//获取 ajax 原来的提交方式
				var oldAjaxAsync = $.ajaxSettings.async;
				//设置 ajax 同步
				$.ajaxSettings.async = false;
				//执行 ajax
				$.getJSON(src,null,function call(result){
					
				});
				//设置回 ajax 原来的提交方式
				$.ajaxSettings.async = oldAjaxAsync;
			}
			//获取各个系统主页面url
			function getSRC(obj){
				//获取项目根路径
				var prefixPath = getPrefixPath(obj);
				//获取要打开页面的位置
				var relativePath = obj.attr('relativePath')!=null?obj.attr('relativePath'):"";
				//合并成 src 路径
				var src = prefixPath+relativePath;
				//给 src 增加 sessionID 条件
				src = srcAddSessionID(src,obj.attr('moduleid'));
				//返回路径
				return src;
			}
			//退出其他系统
			function logoutother(){
				$(document).find("#modulebtndiv a").each(function(){
					//获取项目根路径
					var prefixPath = getPrefixPath($(this));
					//获取 ajax 完整路径
					var src = prefixPath + "/checkneedless/communications/logout.action?jsoncallback=?";
					//给路径增加 sessionID
					src = srcAddSessionID(src,$(this).attr('moduleid'));
					//获取 ajax 原来的提交方式
					var oldAjaxAsync = $.ajaxSettings.async;
					//设置 ajax 异步
					$.ajaxSettings.async = true;
					//执行 ajax
					$.getJSON(src,null,function call(result){
						
					});
					//设置回 ajax 原来的提交方式
					$.ajaxSettings.async = oldAjaxAsync;
				});
			}

			// 当前选中的第几个
			var cselect = 0;
			// 之前选中的第几个
			var pselect = 3;
			// 正在切换第几个
			var uselect = 0;
			// 起始值
			var bb = 0;
			// 浮动值
			var fb = 20;
			//切换时间单位毫秒
			var time = 100;
			//frame个数
			var countFrame = 0;
			//当前选中的frame
			var curSelectedFrame;
			//判断当前是否有系统正在加载，如果正在加载则不允许执行新的加载
			var isrun = false;
			/**
			 * 方法:切换系统时调用
			 * 参数 :a的对象obj
			 */
			function clickChange(obj){
				if(isrun){
					return;
				}
				sessionGetUser(obj);
				isrun = true;
				//拿到context
				var context = obj.attr('context');
				$(".icoch").attr("class","ico");
				obj.parent().parent().attr("class","icoch");
				// 如果当前显示的frame就是当前选中curSelectedFrame则直接返回
				if($(document).find("iframe[class='firstframe'][visibility='visible'][id="+context+"]").size()>0){
					isrun=false;
					return ;
				}else if($(document).find("iframe[class='firstframe'][id="+context+"]").size()==0){
					//创建新的frame
					var childFrame = document.createElement("iframe");
					childFrame.id = context ;
					childFrame.className="firstframe";
					childFrame.frameborder="0";
					childFrame.width="0";
					childFrame.height=framehight;
				 	//如果前缀已经配置则直接使用否则从path.jsp中获取basepath做为前缀 格式举例如下 http://172.16.1.115:8080/
					$('#parentdiv').append(childFrame);
					var src = getSRC(obj);
					childFrame.src= src;
					//设置frame排列
					if (!/*@cc_on!@*/0) {//非IE浏览器
						childFrame.onload = function(){
							removedefaultiframe();
							//找到显示的frame隐藏掉 去掉遍历
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
					} else {//IE浏览器
						childFrame.onreadystatechange = function(){
							removedefaultiframe();
							//找到显示的frame隐藏掉 去掉遍历
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
					bb = 0;//初始化起始值为0
					uselect = 0;//初始化初始化切换系统使用中的系统的位置
					if (pselect < cselect) {//之前选中的小于现在选中的
						colsAdd();
					} else {//之前选中的大于现在选中的
						colsReduce();
					}
					*/
					isrun=false;
				}
			}

			//------------------修改 Iframe 宽度-----开始---------------
			//当选项中的大于当前显示的,向左滑动
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
			//当选项中的小于当前显示的,向右滑动
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
			//------------------修改 Iframe 宽度-----结束---------------
			
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
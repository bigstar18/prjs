<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script>
			var lastToModuleID = '<%=Global.getSelfModuleID()%>';
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
			 * 将路径结尾加上登陆用户sessionID
			 */
			function srcAddSessionID(src,moduleid){
				if(src.indexOf("?")>0){
					src += "&<%=Global.SESSIONID%>=${CurrentUser.sessionId}";
				}else{
					src += "?<%=Global.SESSIONID%>=${CurrentUser.sessionId}";
				}
				src = src + "&<%=Global.USERID%>=${CurrentUser.userId}";
				src = src + "&<%=Global.FROMMODULEID%>="+lastToModuleID;
				src = src + "&<%=Global.FROMLOGONTYPE%>=${CurrentUser.logonType}";
				src = src + "&<%=Global.SELFLOGONTYPE%>=${CurrentUser.logonType}";
				lastToModuleID = moduleid
				return src;
			}
			
			/**
			 * 每次切换系统的时候调用一下 ajax 方法，防止以前切换的系统由于长时间不用而超时了。
			 */
			function sessionGetUser(obj){
				//获取项目根路径
				var prefixPath = getPrefixPath(obj);
				//获取 ajax 完整路径
				var src = prefixPath + "/ajaxcheck/communications/sessionGetUser.action?jsoncallback=?";
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
			/**
			 * 获取新 frame 的 src 路径
			 */
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
			//最外层的主框架frame
			var mainFrame;
			//获取最外层的主框架frame
			function getMainFrame(){
				if(!mainFrame){
					mainFrame = $(document);
				}
				return mainFrame;
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
			
			/*
				方法:切换系统时调用
				参数 :a的对象obj
			*/
			function clickChange(obj,allobj){
				//拿到context
				var context = obj.attr('context');
				//mainwork下的frame名称和context相同 ,根据context 查找 curSelectedFrame
				var curSelectedFrame = mainFrame.find("#mainwork frame[id="+context+"]");//查找到需要的frame
                 //改变之前选中的那个frame;
				var preCurSelectedFrame = mainFrame.find("#mainwork frame[visibility='visible']");
				var affirmrst = affirm();//设定暂时不能点击
				if(affirmrst){
					allobj.css("background-image","");
					obj.css("background-image","url(${skinPath}/image/frame/topframe/navigation/syspic/backgroup.png)");
					// 如果当前显示的frame就是当前选中curSelectedFrame则直接返回
					if(mainFrame.find("#mainwork frame[id="+context+"][visibility='visible']").attr('id')==context){
						clearSubmitCount();
						return ;
					}else if(curSelectedFrame.size()==0){
						 //找到显示的frame隐藏掉 去掉遍历
						mainFrame.find("#mainwork frame[visibility='visible']").each(function(){
							$(this).attr('visibility','hidden');
						});
						var countFrame =mainFrame.find("#mainwork frame").size();
						var hundred ="100%";
						var zero ="0";
						var cols="";
						//如果现在还存在默认frame，则要删除默认frame
						for(var i=getcommon_mgrsize();i<countFrame;i++){
							cols=cols+zero+",";
						}
						cols+=hundred;
						var childFrame = document.createElement("frame");
						 childFrame.id = context ;
					 	//如果前缀已经配置则直接使用否则从path.jsp中获取basepath做为前缀 格式举例如下 http://172.16.1.115:8080/
					 	$(childFrame).attr("visibility","visible");
						 mainFrame.find('#mainwork').append($(childFrame));
						 var src = getSRC(obj);
						childFrame.src= src;
						//设置frame排列
						if (!/*@cc_on!@*/0) {//非IE浏览器
							childFrame.onload = function(){
								removecommon_mgr();
								mainFrame.find('#mainwork').attr("cols",cols);
								clearSubmitCount();
							};
						} else {//IE浏览器
							childFrame.onreadystatechange = function(){
								removecommon_mgr();
								mainFrame.find('#mainwork').attr("cols",cols);
								clearSubmitCount();
							};
						}
					}else{
						sessionGetUser(obj);
						//找到显示的frame隐藏掉 去掉遍历
						mainFrame.find("#mainwork frame[visibility='visible']").each(function(){
							$(this).attr('visibility','hidden');
						});
						
						//把当前选中的frame设置为可见状态
						curSelectedFrame.attr('visibility','visible');
						//如果src为空,将src指向切换系统按钮中配置的路径
						if(curSelectedFrame.attr("src")==""){
							//如果前缀已经配置则直接使用否则从path.jsp中获取basepath做为前缀 格式举例如下 http://172.16.1.115:8080/
							var src = getSRC(obj);
							curSelectedFrame.attr("src",src);
						}
						
						//修改mainwork的rows
						pselect = preCurSelectedFrame.index();//之前选中的系统的位置
						cselect = curSelectedFrame.index();//当前选中的系统的位置
						bb = 0;//初始化起始值为0
						uselect = 0;//初始化初始化切换系统使用中的系统的位置
						if (pselect < cselect) {//之前选中的小于现在选中的
							colsAdd();
						} else {//之前选中的大于现在选中的
							colsReduce();
						}
						clearSubmitCount();
					}
				}
			}
			//删除指定ID的frame
			function removecommon_mgr(){
				var id="common_mgr";
				mainFrame.find("#mainwork frame[id="+id+"]").remove();
			}
			function getcommon_mgrsize(){
				var id="common_mgr";
				return mainFrame.find("#mainwork frame[id="+id+"]").size();
			}
			//------------------修改mainwork的cols-----开始---------------
			//当选项中的大于当前显示的,向左滑动
			function colsAdd() {
				countFrame = mainFrame.find("#mainwork frame").size();
				bb = fb + bb;
				var cols = "";
				if (uselect == 0) {
					uselect = pselect + 1;
				}
				if (bb <= 100) {
					for (var i = 0; i < countFrame; i++) {
						if (uselect == i) {
							cols = cols + bb + "%";
						} else if (uselect - 1 == i) {
							cols = cols + (100 - bb) + "%";
						} else {
							cols = cols + "0%";
						}
						cols = cols + (i == countFrame - 1 ? "" : ",");
					}
					mainFrame.find('#mainwork').attr("cols", cols);
					cols = "";
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
				countFrame = mainFrame.find("#mainwork frame").size();
				var cols = "";
				bb = fb + bb;
				if (uselect == 0) {
					uselect = pselect;
				}
				if (bb <= 100&&uselect > cselect) {
					for (var i = 0; i < countFrame; i++) {
						if (uselect == i) {
							cols = cols + (100 - bb) + "%";
						} else if (uselect - 1 == i) {
							cols = cols + bb + "%";
						} else {
							cols = cols + "0%";
						}
						cols = cols + (i == countFrame - 1 ? "" : ",");
					}
					mainFrame.find('#mainwork').attr("cols", cols);
					cols = "";
					setTimeout("colsReduce()", time);
				}else{
					uselect = uselect - 1;
					bb = 0;
                    if ( uselect > cselect) {
					 colsReduce();
					}
				}
			}
			//------------------修改mainwork的cols-----结束---------------
			
		</script>
	</head>
	<frameset rows="65,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=framePath %>/topframe.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset id=mainwork rows="100%,*" cols="*" frameborder="NO" border="0" framespacing="0" >
			<frame src="<%=framePath %>/waiting.jsp" id='common_mgr' visibility='visible'>
		</frameset>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>
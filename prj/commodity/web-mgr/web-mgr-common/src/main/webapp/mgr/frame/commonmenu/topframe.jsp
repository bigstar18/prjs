<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	request.setAttribute("modelContextMap",Global.modelContextMap);
	request.setAttribute("COMMONMODULEID",Global.COMMONMODULEID);
%>
<html>
	<head>
		<title>框架集top页面</title>
		<link href="${skinPath}/css/navigation/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script type="text/javascript" src="${publicPath}/js/interface.js"></script>
		<script type="text/javascript" src="${publicPath}/js/map.js"></script>
		<script language="javascript">
		<!--
		    /**
		     * 修改自己的登陆密码
		     */
			function changePwd(id){
				var url = "<%=basePath %>/self/passwordSelfMod.action?entity.userId="+id;
				if(showDialog(url, "", 500, 300)){
					frm.submit();
				}
			}

			//时间差
			var timediffer=0;
			//循环执行时间同步的时间
			var ajaxtime = 0;
			/**
			 * ajax 获取当前数据库时间
			 */
			function ajaxGetSysDate(){
				var url = "../../../sysDate/getDate.action?t="+Math.random();
				$.getJSON(url,null,function call(result){
					var dateArr=result[0].split(" ");
					var yearOrMouthOrDay=dateArr[0].split("-");
					var hourOrMinOrSen=dateArr[1].split(":");
					var sysdate=new Date(yearOrMouthOrDay[0],yearOrMouthOrDay[1]-1,yearOrMouthOrDay[2],hourOrMinOrSen[0],hourOrMinOrSen[1],hourOrMinOrSen[2]);
					var date = new Date();
					timediffer = sysdate.getTime()-date.getTime();
					clock();
				});
			}
			/**
			 * 遍历将时间增加 1 秒
			 */
			function clock() {
				ajaxtime = ajaxtime+1;
				var date = new Date();
				date.setTime(date.getTime()+timediffer);
				sysDate.innerHTML= date.toLocaleString();
				if(ajaxtime<(5*60)){
					setTimeout("clock()", 1000);
				}else{
					ajaxtime = 0;
					ajaxGetSysDate();
				}
			}

			jQuery(document).ready(function() {
				//获取系统时间
				ajaxGetSysDate();
				//拿到最顶层的window
				var mainFrame = window.parent.getMainFrame();
				//----------------------退出系统方法开始--------------
				//退出时　先退出其它系统最后退出公共系统
					//重启服务用户为空时或用户重复登录导致被迫下线时，调用的退出系统方法
					$('#logout').click(function(){
						logout_useframes();
					    top.location="${basePath}/user/logout.action";
					});
					//点击退出按钮时调用的退出方法。
					$('#logout1').click(function(){
						var vaild = window.confirm("您确定要退出吗？");
						if(vaild==true){
							logout_useframes();
						    top.location="${basePath}/user/logout.action";
					    }else{
				           return false;
					    }
					});

				//退出其它系统；使用ajax方式通知其它系统退出系统
				function  logout_useframes(){
					//遍历所有切换系统超链接
					$("a.dock-item[context!=''][visibility!=visible]").each(function(){
						var prefixPath = getPrefixPath($(this));
						logoutURL=prefixPath+"/ajaxcheck/communications/logout.action?jsoncallback=?&t="+Math.random();
						$.ajaxSettings.async = false;
						$.getJSON(logoutURL,null,function call(result){
						});
					});
				}
				//----------------------退出系统方法结束---------------//
				
				//----------------修改风格开始---------------
				/**
				 * 修改系统的样式风格
				 */
				 $('#skinStyle').click(function(){
					var url = "${framePath}/shinstyle.jsp?d="+Date();
					if(showDialog(url, "", 500, 300)){
						changeStyle();
					}
				});
				//通知其它系统修改风格
				function changeStyle(){
					var workFrameId=mainFrame.find("#mainwork frame[src!=''][visibility=visible]").attr('id');
					//遍历所有配置未显示的系统,通知服务器修改风格
					$("a.dock-item[context!="+workFrameId+"]").each(function(){
						var workFrame =$(this);
						var prefixPath = getPrefixPath(workFrame);
						var relativePath = "/ajaxcheck/communications/changeStyle.action?jsoncallback=?";
						var urlstyle = prefixPath + relativePath;
						$.ajaxSettings.async = false;
						$.getJSON(urlstyle,null,function call(result){
							mainFrame.find("#mainwork frame[id="+workFrame.attr("context")+"]").each(function(){
								$(this).attr('src','');
							});
						});
					});
					//查询当前显示的页面 frame src!=""&&visibility=visible的页面通知服务器端修改风格并把src重新加载
					mainFrame.find("#mainwork frame[src!=''][visibility=visible]").each(function(){
						var workFrame =$(this);
						var prefixPath = getPrefixPath($("a.dock-item[context="+workFrame.attr('id')+"]"));
						
						var relativePath = "/ajaxcheck/communications/changeStyle.action?jsoncallback=?";
						
						var urlstyle = prefixPath + relativePath;
						$.ajaxSettings.async = false;
				
						$.getJSON(urlstyle,null,function call(result){
							workFrame.attr('src',workFrame.attr('src'));
							frm.submit();
						});
					});
				}
				
                //-----------------修改风格结束----------------

			});
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
		//-->
		</script>
	</head>
	<body>
		<div id="haeder">
			<form name=frm id=frm>
				<div class="logo"> 
					<div class="anniu_kuang">
							<ul>
								<li>
									<a href="#" id="skinStyle">
										<img src="<%=skinPath%>/image/frame/topframe/skinStyle.png" border="0">
									</a>
								</li>
								<li>
									<a href="#" id="logout1" >
										<img src="<%=skinPath%>/image/frame/topframe/logout.png"border="0" >
									</a>
									<input type="hidden" id="logout" value="logout">
								</li>
								<li>
									<a href="#" onClick="changePwd('${CurrentUser.userId }');">
										<img src="<%=skinPath%>/image/frame/topframe/pwd.png" border="0" >
									</a>
								</li>
							</ul>
					</div>
					<!--top dock -->
					<div class="dock" id="dock">
						<div class="anniu_kuangCopy">
							<table>
								<tr>
									<td>
										管理员代码：${CurrentUser.userId }
									</td>
								</tr>
								<tr>
									<td>
										管理员名称：${CurrentUser.name }
									</td>
								</tr>
								<tr>
									<td>
										系统时间为：<font id="sysDate"></font>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="dock-container" id="allsystem_div" style="display: none;">
							 <!-- id='defaultframe'添加在哪个标签上则哪个系统为默认打开系统 -->
							 <c:set var="deffram" value="true"></c:set>
							 <c:forEach items="${modelContextMap}" var="entry"> 
							 <c:if test="${!(COMMONMODULEID eq entry.key)}">
								 <a 
								 <c:if test="${deffram}">id='defaultframe'<c:set var="deffram" value="false"></c:set></c:if>
								 <c:if test="${!(deffram)}">id="${entry.key}"</c:if>
								 class="dock-item" href="#"  prefixPath="${entry.value.SERVERPATH}" context="${entry.value.CONTEXTNAME}" relativePath="${entry.value.RELATIVEPATH}" hidefocus="true">
									<img src="<%=skinPath%>/image/frame/topframe/navigation/${entry.value.SERVERPIC}" alt="${entry.value.shortName}" /><span><%/* ${entry.value.shortName}*/%></span>
								</a>
							</c:if>
							</c:forEach>  
						</div>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>

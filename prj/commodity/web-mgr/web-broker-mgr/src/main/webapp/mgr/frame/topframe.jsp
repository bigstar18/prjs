<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>框架集top页面</title>
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script language="javascript">
		<!--
			/**
			 * 推出系统
			 */
			function logout(){
				var vaild = window.confirm("您确定要退出吗？");
				if(vaild==true){
				    parent.window.location="<%=basePath %>/user/logout.action";
			    }else{
		           return false;
			    }
		    }

			function logout1(){
				    parent.window.location="<%=basePath %>/user/logout.action";
		    }
		    /**
		     * 修改自己的登陆密码
		     */
			function changePwd(id){
				var url = "<%=basePath %>/self/passwordSelfMod.action?entity.userId="+id;
				if(showDialog(url, "", 500, 300)){
					frm.submit();
				}
			}
			/**
			 * 修改自己的样式风格
			 */
			function shinStyle(id){
				var url = "<%=framePath %>/shinstyle.jsp?d="+Date();
				if(showDialog(url, "", 500, 300)){
					frm.submit();
					parent.window.location = "<%=framePath %>/mainframe.jsp";
				}
			}
			//时间差
			var timediffer=0;
			/**
			 * ajax 获取当前数据库时间
			 */
			function ajaxGetSysDate(){
				var url = "../../sysDate/getDate.action?t="+Math.random();
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
				var date = new Date();
				date.setTime(date.getTime()+timediffer);
				sysDate.innerHTML= date.toLocaleString();
				setTimeout("clock()", 1000)
			}

			jQuery(document).ready(function() {
				ajaxGetSysDate();
			});
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
								<a href="#" onClick="shinStyle('${CurrentUser.userId }');">
									<img src="<%=skinPath%>/image/frame/topframe/skinStyle.png" border="0">
								</a>
							</li>
							<li>
								<a href="#" onClick="logout();">
									<img src="<%=skinPath%>/image/frame/topframe/logout.png"border="0" >
									<input type="hidden" id="logout" onclick="logout1()">
								</a>
							</li>
							<li>
								<a href="#" onClick="changePwd('${CurrentUser.userId }');">
									<img src="<%=skinPath%>/image/frame/topframe/pwd.png" border="0" >
								</a>
							</li>
						</ul>
					</div>
					<div class="anniu_kuangCopy">
						管理员代码:${CurrentUser.userId }&nbsp;&nbsp;&nbsp;&nbsp;
						管理员名称:${CurrentUser.name }&nbsp;&nbsp;&nbsp;&nbsp;
						系统时间为：<font id="sysDate">&nbsp;</font>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>
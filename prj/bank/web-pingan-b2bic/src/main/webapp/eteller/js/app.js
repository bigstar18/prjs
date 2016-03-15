$(function() {
	//dwz初始化
	DWZ.init("dwz/management/dwz.frag.xml", {
		//loginUrl:"/management/index!login.do", loginTitle:"Login",	// 弹出登录对话框
		//loginUrl:"/management/index!login.do",	// 跳到登录页面
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection", rollPage:"rollPage"},
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"dwz/management/themes"});
		}
	});
	
	//每分钟发送一次心跳包
	setInterval(function() {
		window.closeLoadFlag = true;
		$.post("heartBeat", {}, function(data){
			if(data == "login") {
				location.href = "index";
			}
		});
		window.closeLoadFlag = false;
	}, 60000);
});

//清理浏览器内存,只对IE起效,FF不需要
if ($.browser.msie) {window.setInterval("CollectGarbage();", 10000);}
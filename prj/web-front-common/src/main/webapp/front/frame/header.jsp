<%@ page trimDirectiveWhitespaces="true" contentType="text/html;charset=GBK"%>
<%
	request.setAttribute("modelContextMap",Global.modelContextMap);
%>
<%@ include file="/front/public/includefiles/taglib.jsp" %>
<%@ include file="/front/public/includefiles/path.jsp" %>
<style type="text/css">
a,img{border:0;}
.iphonebox{overflow:hidden;float:left;}
.iphonebox .promptumenu_window{margin:86px 0 0 25px;}
.promptumenu_window{cursor:move;position:relative;float:left;}

</style>
<div class="top_bor" style="background: url('../skinstyle/default/image/mgr/memberadmin/main/bg_top.gif');">
	<div class="logo"></div>
	<div class="login"><span class="wordcolor_yel">${CurrentUser.name}</span> 您好！欢迎进入 <%=Global.getMarketInfoMap().get("marketName")%>中心 
	<c:if test="${CurrentUser.lastTime!=null}"> 上次登录IP：<span class="wordcolor_blue_ip">${CurrentUser.lastIP }</span>  上次登录时间：<span class="wordcolor_blue_ip"><fmt:formatDate value="${CurrentUser.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if></div>
</div>
<script type="text/javascript" src="${publicPath}/js/jquery.promptu-menu.js"></script>
<div class="btn_bor" style="min-width: 1000px; overflow:hidden;background: url('../skinstyle/default/image/mgr/memberadmin/main/bg_top.gif');">
	<div class="sys_name">会员管理&nbsp;&nbsp;﹥</div>
	<div class="ico_over"><div class="png_8" id="prev" style="display: none;"><a href="#"><img src="${skinPath}/image/mgr/memberadmin/main/ico_left.png" width="29" height="57" /></a></div></div>
	<div id="modulebtndiv" class="iphonebox">
	<c:set var="defaulta" value="true"/>
	<c:forEach var="tradeModule" items="${modelContextMap}"><%//首先循环配置文件的模块，以使得模块排序按照配置文件的顺序展示 %>
		<c:forEach var="firmModule" items="${CurrentUser.belongtoFirm.firmModuleSet}">
		
			<c:if test="${tradeModule.key eq firmModule.moduleID && firmModule.enabled eq 'Y'}"><%//当交易商有本模块权限才可以展示 %>
			<c:forEach var="traderModule" items="${CurrentUser.traderModule}">
				<c:if test="${traderModule.enabled eq 'Y' and traderModule.moduleID eq tradeModule.key}"><%//当交易员有本模块权限才可以展示 %>
					<c:set var="relativepath" value="/front/frame/mainframe.jsp"/>
					<c:if test="${not empty tradeModule.value['RELATIVEPATH']}">
						<c:set var="relativepath" value="${tradeModule.value['RELATIVEPATH']}"/>
					</c:if>
					<span class="ico">
					<div class="png_1">
						<a href="#"<c:if test="${defaulta}"> id="defaulta"<c:set var="defaulta" value="false"/></c:if>
						 prefixPath="${tradeModule.value['SERVERPATH']}" 
						 relativePath="${relativepath}" 
						 context="${tradeModule.value['CONTEXTNAME']}" 
						 moduleid="${tradeModule.key}" 
						 onclick="clickChange($(this))">
						 <img src="${skinPath}/image/mgr/memberadmin/main/${tradeModule.value['SERVERPIC']}" width="98" height="57" />
						 </a>
					</div>
					</span>
					<%--以下处理有主页的系统 --%>	 
					<c:if test="${(not empty tradeModule.value['WELCOMEPAGE']) and (not empty tradeModule.value['WELCOMEPIC'])}">
						<c:if test="${empty tradeModule.value['SERVERPATH']}">
							<c:set var="path" value="${SERVERPATH}"/>
						</c:if>
						<c:if test="${not empty tradeModule.value['SERVERPATH']}">
							<c:set var="path" value="${tradeModule.value['SERVERPATH']}"/>
						</c:if>
						<span class="ico">
						<div class="png_1">
							<a href="#" 
							 mainmoduleid="${tradeModule.key}"
							onclick="gotohead('${path}/${tradeModule.value['CONTEXTNAME']}${tradeModule.value['WELCOMEPAGE']}',$(this))">
								<img src="${skinPath}/image/mgr/memberadmin/main/${tradeModule.value['WELCOMEPIC']}" width="98" height="57" />
							</a>
						</div>
						</span>
					</c:if>
				</c:if>
			</c:forEach>
			</c:if>
		</c:forEach>
	</c:forEach>
	<c:if test="${defaulta}">
	<li class="ico" style="display: none;"><div class="png_1"><a href="#" id="defaulta" prefixPath="" context="common_front" relativePath="/front/public/jsp/noright.jsp" onclick="clickChange($(this))">&nbsp;</a></div></li>
	</c:if>
	</div>
	<div class="ico_over"><div class="png_8" id="next" style="display: none;"><a href="#""><img src="${skinPath}/image/mgr/memberadmin/main/ico_right.png" width="29" height="57" /></a></div></div>
    <div class="icor"><div class="png_1"><a id="logout1" href="#" onclick="logout();"><img src="${skinPath}/image/mgr/memberadmin/main/ico_exit.png" width="98" height="57" /></a></div></div>
    <div class="icor"><div class="png_1"><a href="#" onclick=javascript:window.open("http://www.gnnt.com.cn")><img src="${skinPath}/image/mgr/memberadmin/main/ico_help.png" width="98" height="57" /></a></div></div>
    <div class="icor"><div class="png_1"><a href="#" onclick="updatePassword();"><img src="${skinPath}/image/mgr/memberadmin/main/ico_pw.png" width="98" height="57" /></a></div></div>
    <input type="hidden" id="logout" value="logout" onclick="logout1();">
</div>
<form id="headfrm" name="headfrm" target="_balance" method="post">
<input name="<%=Global.SESSIONID%>" type="hidden" value="${CurrentUser.sessionId}"/>
</form>
<script type="text/javascript">
var leftWidth = 200;<%//左侧会员管理占据的宽度 %>
var rightWidth = 310;<%//右侧修改密码、帮助、退出按钮占据的宽度 %>
var width = document.documentElement.scrollWidth - leftWidth - rightWidth-49;<%//获取当前浏览器宽度去掉左右宽度后剩余的宽度 %>
var icosize = $(".ico").size();<%//系统个数%>
var picwith = 100;
var number = width/picwith;
if(number<icosize){<%//如果头部不能排列所有图标，则使得图标之间出现间隙，并且可以滑动%>
	$("#prev").css("display","block");
	$("#next").css("display","block");
	picwith = picwith;
	number = width/picwith;<%//获取可以展示系统个数 %>
	if(number>icosize){
		number=icosize;<%//如果系统个数超过了可展示的个数，则只展示系统个数%>
	}else if(number<1){
		number=1;<%//如果一个系统都展示不出来，则认为展示一个%>
	}else{
		$("#modulebtndiv").attr("title","您可以左右拖动本部分来显示您要选择的系统按钮");
	}
	number = parseInt(number);
	
	var s1 = {
		width:number*picwith,   //自定义滚屏宽度
		height:56, //自定义滚屏搞定
		rows:1,     //自定义滚屏排列行数
		columns:number,  //自定义滚屏单行图标个数
		direction:'horizontal', //水平拖动效果
		pages:false  //是否分页显示
	}
	$('div.iphonebox').promptumenu(s1);
	
	window.s1 = s1;
	
	$("#next").bind("click",function(){
	  s1.funs[0].next_page();//下一页
	});
	$("#prev").bind("click",function(){
	  s1.funs[0].prev_page();//上一页
	});
	
}
<!--
function updatePassword(id){
	var url="${frontPath}/app/mgr/self/passwordSelfMod.action?entity.traderID=${CurrentUser.traderID}";
	showDialog(url,"",700,400);
}
//退出
function logout1(){
	//退出其他系统
	logoutother();
	window.location.href="${frontPath}/app/mgr/user/logout.action";
}
//转到主页
function gotohead(url,obj){
	$("#headfrm").attr("action",srcAddSessionID(url,obj.attr('mainmoduleid')));
	$("#headfrm").submit();
}
function logout(){
	var vaild=affirm("您确定要退出吗？");
	if(vaild){
		logout1();
	}
}
//-->
</script>
<%@ page trimDirectiveWhitespaces="true" contentType="text/html;charset=GBK"%>
<%@ include file="/front/public/includefiles/taglib.jsp" %>
<%@ include file="/front/public/includefiles/path.jsp" %>
<div class="top_bor">
	<div class="logo"></div>
	<div class="login"><span class="wordcolor_yel">${CurrentUser.name}</span> 您好！欢迎进入<%=Global.getMarketInfoMap().get("marketName")%>现货中心 
	<c:if test="${CurrentUser.lastTime!=null}"> 上次登录IP：<span class="wordcolor_blue_ip">${CurrentUser.lastIP }</span>  上次登录时间：<span class="wordcolor_blue_ip"><fmt:formatDate value="${CurrentUser.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if></div>
</div>
<div class="btn_bor">
	<div class="sys_name">会员管理&nbsp;&nbsp;﹥</div>
    <div class="ico"><div class="png_5"><a href="#" onclick="updatePassword();"><img src="${skinPath}/image/mgr/memberadmin/main/ico_pw.png" width="98" height="57" /></a></div></div>
    <div class="ico"><div class="png_6"><a href="#"><img src="${skinPath}/image/mgr/memberadmin/main/ico_help.png" width="98" height="57" /></a></div></div>
    <div class="ico"><div class="png_7"><a id="logout" href="${frontPath}/app/mgr/user/logout.action"><img src="${skinPath}/image/mgr/memberadmin/main/ico_exit.png" width="98" height="57" /></a></div></div>
    <div class="tel">咨询热线：<%=Global.getMarketInfoMap().get("marketPhone")%></div>
</div>
<<script type="text/javascript">
<!--
function updatePassword(id){
	var url="${frontPath}/app/mgr/self/passwordSelfMod.action?entity.traderID=${CurrentUser.traderID}";
	showDialog(url,"",700,400);
}
//-->
</script>
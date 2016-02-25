<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%
request.setAttribute("pageTitle","用户登录");
String preUrl = request.getParameter(Global.TOLOGINPREURL);
%>
<jsp:include page="header.jsp" flush="false"></jsp:include>
<script src="${publicPath}/js/cookie.js"></script>
<!-------------------------State Begin------------------------->
<div class="state">提醒：您目前进行的<c:if test="${msg != null}"><%=request.getAttribute("msg") %></c:if>操作，需要登录后才能继续……</div>
<script>
$(function() {
    $(".webwidget_scroller_tab").webwidget_scroller_tab({
        scroller_time_interval: '0',//自动切换时间
        scroller_window_padding: '10',
        scroller_window_width: '350',//框宽度
        scroller_window_height: '150',//框高度
        scroller_head_text_color: '#0099FF',//按钮失去焦点颜色
        scroller_head_current_text_color: '#666',//按钮获得焦点颜色
        directory: 'images'
    });
});
jQuery(document).ready(function() {
	$("#logonfrm").validationEngine('attach', {promptPosition : "centerRight"});
	//设置登录按钮的点击事件
	$("#logon").click(function(check) {
		if ($("#logonfrm").validationEngine('validate')) {
			$("#logonfrm").validationEngine('detach');
		    $("#logonfrm").submit();
		}
	});
	//设置input的回车事件
	$("input").keydown(function(){
		if(event.keyCode == 13){
			$("#logon").click();
		}
	});
	//获取默认图片
	changeloginimg('tlaim');
	<c:if test="${userIdType==1}">
		document.getElementById("liuser").click();
	</c:if>
});
/**更换验证码图片*/
function changeloginimg(id){
	if(id != 'ulaim'){
		id='tlaim';
	}
	document.getElementById("timgcode").value="";
	document.getElementById("uimgcode").value="";
	var img = document.createElement("IMG");
	img.width="80";
	img.height="20";
	img.align="absmiddle";
	img.src="${publicPath}/jsp/loginimage.jsp?"+ Math.random();
	if (!/*@cc_on!@*/0) {//非IE浏览器
		img.onload = function(){
			$("#"+id).html(img);
		};
	} else {//IE浏览器
		img.onreadystatechange = function(){
			$("#"+id).html(img);
		};
	}
}
//修改登录类型 0 代码登录 1 用户名登录
function chUserIdType(val,a){
	get('userIdType').value=val;
	if(val=='1'){
		changeloginimg('ulaim');
		get("traderId").className="";
		get("tpassword").className="";
		get("timgcode").className="";
		get("userId").className="validate[required]";
		get("upassword").className="validate[required,minSize[6]]";
		get("uimgcode").className="validate[required]";
	}else{
		changeloginimg('tlaim');
		get("traderId").className="validate[required]";
		get("tpassword").className="validate[required,minSize[6]]";
		get("timgcode").className="validate[required]";
		get("userId").className="";
		get("upassword").className="";
		get("uimgcode").className="";
	}
	a.parentNode.click();
	$("#logonfrm").validationEngine('attach', {promptPosition : "centerRight"});
	return false;
}
function get(id){
	return document.getElementById(id);
}
</script>
<div class="login">
	<form id="logonfrm" action="${basePath}/front/app/mgr/user/logon.action" method="post">
	<div class="left">
		<input type="hidden" name="<%=Global.TOLOGINPREURL %>" value="<%=preUrl%>"/>
		<input type="hidden" id="userIdType" name="userIdType" value="${userIdType}"/>
		<div class="webwidget_scroller_tab" id="webwidget_scroller_tab">
			<div class="tabContainer">
				<ul class="tabHead">
					<li><a href="javascript:;" onclick="chUserIdType(0,this);">代码登录</a></li>
					<li><a href="javascript:;" onclick="chUserIdType(1,this);" id="liuser">用户名登录</a></li>
				</ul>
			</div>
			<div class="tabBody">
				<ul>
					<li class="tabCot">
						<table width="360" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="40" align="right">交易员代码：</td>
								<td>
									<label id="tlabel"><input id="traderId" name="entity.traderID" value="${traderID}" size="30" class="validate[required]" data-prompt-position="topRight:0,0"/></label>
								</td>
							</tr>
							<tr>
								<td height="40" align="right">密码：</td>
								<td><label><input id="tpassword" type="password" name="tpassword" value="${tpassword }" size="30" value="" class="validate[required,minSize[6]]" data-prompt-position="bottomRight:3,0"/></label></td>
							</tr>
							<tr>
								<td height="40" align="right">验证码：</td>
								<td>
									<label>
										<input id="timgcode" name="timgcode" size="12" value='' class="validate[required]" data-prompt-position="bottomLeft:0,0"/>
										<label id="tlaim"></label>
										<a href="javascript:changeloginimg('tlaim')"><span style="font-size: 11px">换一张</span></a>
									</label>
								</td>
							</tr>
						</table>
					</li>
					<li class="tabCot">
						<table width="360" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="40" align="right">交易员用户名：</td>
								<td>
									<label id="ulabel"><input id="userId" name="entity.userID" value="${userID }" size="30" class="" data-prompt-position="topRight:0,0"/></label>
								</td>
							</tr>
							<tr>
								<td height="40" align="right">密码：</td>
								<td><label><input id="upassword" type="password" name="upassword" value="${upassword }" size="30" value="" class="" data-prompt-position="bottomRight:3,0"/></label></td>
							</tr>
							<tr>
								<td height="40" align="right">验证码：</td>
								<td>
									<label>
										<input id="uimgcode" name="uimgcode" size="12" value='' class="" data-prompt-position="bottomLeft:0,0"/>
										<label id="ulaim"></label>
										<a href="javascript:changeloginimg('ulaim')"><span style="font-size: 11px">换一张</span></a>
									</label>
								</td>
							</tr>
						</table>
					</li>
				</ul>
				<div style="clear:both"></div>
			</div>
			<div class="modBottom"> <span class="modABL">&nbsp;</span><span class="modABR">&nbsp;</span> </div>
		</div>
		<table width="360" border="0" cellspacing="0" cellpadding="0">
			<tr><td height="10"></td></tr>
			<tr>
				<td align="right"><a href="#" id="logon"><img src="${skinPath}/image/logon/login.gif" width="113" height="35" /></a></td>
			</tr>
		</table>
	</div>
	</form>
	<div class="right">
		<img src="${skinPath}/image/logon/regsiter.gif" width="477" height="312" border="0" usemap="#Map" />
		<map name="Map" id="Map"><area shape="rect" coords="189,101,292,131" href="${basePath}/checkneedless/firmapply/registerforward.action" /></map>
	</div>
</div>
<jsp:include page="/front/frame/footer.jsp" flush="false"></jsp:include>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
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
	<div class="login"><span class="wordcolor_yel">${CurrentUser.name}</span> ���ã���ӭ���� <%=Global.getMarketInfoMap().get("marketName")%>���� 
	<c:if test="${CurrentUser.lastTime!=null}"> �ϴε�¼IP��<span class="wordcolor_blue_ip">${CurrentUser.lastIP }</span>  �ϴε�¼ʱ�䣺<span class="wordcolor_blue_ip"><fmt:formatDate value="${CurrentUser.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if></div>
</div>
<script type="text/javascript" src="${publicPath}/js/jquery.promptu-menu.js"></script>
<div class="btn_bor" style="min-width: 1000px; overflow:hidden;background: url('../skinstyle/default/image/mgr/memberadmin/main/bg_top.gif');">
	<div class="sys_name">��Ա����&nbsp;&nbsp;��</div>
	<div class="ico_over"><div class="png_8" id="prev" style="display: none;"><a href="#"><img src="${skinPath}/image/mgr/memberadmin/main/ico_left.png" width="29" height="57" /></a></div></div>
	<div id="modulebtndiv" class="iphonebox">
	<c:set var="defaulta" value="true"/>
	<c:forEach var="tradeModule" items="${modelContextMap}"><%//����ѭ�������ļ���ģ�飬��ʹ��ģ�������������ļ���˳��չʾ %>
		<c:forEach var="firmModule" items="${CurrentUser.belongtoFirm.firmModuleSet}">
		
			<c:if test="${tradeModule.key eq firmModule.moduleID && firmModule.enabled eq 'Y'}"><%//���������б�ģ��Ȩ�޲ſ���չʾ %>
			<c:forEach var="traderModule" items="${CurrentUser.traderModule}">
				<c:if test="${traderModule.enabled eq 'Y' and traderModule.moduleID eq tradeModule.key}"><%//������Ա�б�ģ��Ȩ�޲ſ���չʾ %>
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
					<%--���´�������ҳ��ϵͳ --%>	 
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
var leftWidth = 200;<%//����Ա����ռ�ݵĿ�� %>
var rightWidth = 310;<%//�Ҳ��޸����롢�������˳���ťռ�ݵĿ�� %>
var width = document.documentElement.scrollWidth - leftWidth - rightWidth-49;<%//��ȡ��ǰ��������ȥ�����ҿ�Ⱥ�ʣ��Ŀ�� %>
var icosize = $(".ico").size();<%//ϵͳ����%>
var picwith = 100;
var number = width/picwith;
if(number<icosize){<%//���ͷ��������������ͼ�꣬��ʹ��ͼ��֮����ּ�϶�����ҿ��Ի���%>
	$("#prev").css("display","block");
	$("#next").css("display","block");
	picwith = picwith;
	number = width/picwith;<%//��ȡ����չʾϵͳ���� %>
	if(number>icosize){
		number=icosize;<%//���ϵͳ���������˿�չʾ�ĸ�������ֻչʾϵͳ����%>
	}else if(number<1){
		number=1;<%//���һ��ϵͳ��չʾ������������Ϊչʾһ��%>
	}else{
		$("#modulebtndiv").attr("title","�����������϶�����������ʾ��Ҫѡ���ϵͳ��ť");
	}
	number = parseInt(number);
	
	var s1 = {
		width:number*picwith,   //�Զ���������
		height:56, //�Զ�������㶨
		rows:1,     //�Զ��������������
		columns:number,  //�Զ����������ͼ�����
		direction:'horizontal', //ˮƽ�϶�Ч��
		pages:false  //�Ƿ��ҳ��ʾ
	}
	$('div.iphonebox').promptumenu(s1);
	
	window.s1 = s1;
	
	$("#next").bind("click",function(){
	  s1.funs[0].next_page();//��һҳ
	});
	$("#prev").bind("click",function(){
	  s1.funs[0].prev_page();//��һҳ
	});
	
}
<!--
function updatePassword(id){
	var url="${frontPath}/app/mgr/self/passwordSelfMod.action?entity.traderID=${CurrentUser.traderID}";
	showDialog(url,"",700,400);
}
//�˳�
function logout1(){
	//�˳�����ϵͳ
	logoutother();
	window.location.href="${frontPath}/app/mgr/user/logout.action";
}
//ת����ҳ
function gotohead(url,obj){
	$("#headfrm").attr("action",srcAddSessionID(url,obj.attr('mainmoduleid')));
	$("#headfrm").submit();
}
function logout(){
	var vaild=affirm("��ȷ��Ҫ�˳���");
	if(vaild){
		logout1();
	}
}
//-->
</script>
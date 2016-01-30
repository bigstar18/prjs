<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	String serverInterface = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	request.setAttribute("modelContextMap",Global.modelContextMap);
	request.setAttribute("COMMONMODULEID",Global.COMMONMODULEID);
%>
<html>
	<head>
		<title>��ܼ�topҳ��</title>
		<!-- <link href="${skinPath}/css/navigation/style.css" rel="stylesheet" type="text/css" /> -->
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<!-- <script type="text/javascript" src="${publicPath}/js/interface.js"></script> -->
		<script type="text/javascript" src="${publicPath}/js/map.js"></script>
		<script type="text/javascript" src="${publicPath}/js/jquery.promptu-menu.js"></script>
		<script language="javascript">
		var lastToModuleID = '<%=Global.getSelfModuleID()%>';
		<!--
		    /**
		     * �޸��Լ��ĵ�½����
		     */
			function changePwd(id){
				var url = "<%=basePath %>/self/passwordSelfMod.action?entity.userId="+id;
				if(showDialog(url, "", 500, 300)){
					frm.submit();
				}
			}

		  /**
	     * ��ת�����ϵͳ
	     */
		function goMonitor(moduleid){
			 <c:forEach items="${modelContextMap}" var="entry"> 
			 	<c:if test="${entry.key==29}">
			 	var url='<%=Global.modelContextMap.get(29).get("SERVERPATH")%><%=Global.modelContextMap.get(29).get("CONTEXTNAME")%>/monitor/forward/forwardMainMonitor.action?t='+Math.random();
				if('<%=Global.modelContextMap.get(29).get("SERVERPATH")%>'=='null'){
					url='<%=serverInterface%>/<%=Global.modelContextMap.get(29).get("CONTEXTNAME")%>/monitor/forward/forwardMainMonitor.action';
					url=window.parent.srcAddSessionID(url,moduleid);
				}
				mFirm.action=url;
				mFirm.submit();
			 	</c:if></c:forEach>
			
			}

			//ʱ���
			var timediffer=0;
			//ѭ��ִ��ʱ��ͬ����ʱ��
			var ajaxtime = 0;
			/**
			 * ajax ��ȡ��ǰ���ݿ�ʱ��
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
			 * ������ʱ������ 1 ��
			 */
			function clock() {
				ajaxtime = ajaxtime+1;
				var date = new Date();
				date.setTime(date.getTime()+timediffer);
				<%//update by xumiao 2014-8-7 start%>
				//document.getElementById("sysDate").innerHTML = date.toLocaleString();
				document.getElementById("sysDate").innerHTML= date.toLocaleTimeString();
				<%//update by xumiao 2014-8-7 end%>
				if(ajaxtime<(5*60)){
					setTimeout("clock()", 1000);
				}else{
					ajaxtime = 0;
					ajaxGetSysDate();
				}
				$("#userInfoSpan").attr("title","����Ա��${CurrentUser.userId}��:${CurrentUser.name} "+date.toLocaleTimeString());
			}

			
			jQuery(document).ready(function() {
				/*$('#dock').Fisheye({
					maxWidth : 12,
					items : 'a',
					itemsText : 'span',
					container : '.dock-container',
					itemWidth : 48,
					proximity : 60,
					halign : 'center'
				});*/
				
				
				//����л�ϵͳͼ���¼�
				$('span.dock-item').click(function() {
					window.parent.clickChange($(this),$('span.dock-item'));
				});
				
				//�õ�����window
				var mainFrame = window.parent.getMainFrame();
				//----------------------�˳�ϵͳ������ʼ--------------
				//�˳�ʱ�����˳�����ϵͳ����˳�����ϵͳ
					//���������û�Ϊ��ʱ���û��ظ���¼���±�������ʱ�����õ��˳�ϵͳ����
					$('#logout').click(function(){
						logout_useframes();
					    top.location="${basePath}/user/logout.action";
					});
					//����˳���ťʱ���õ��˳�������
					$('#logout1').click(function(){
						var vaild = window.confirm("��ȷ��Ҫ�˳���");
						if(vaild==true){
							logout_useframes();
						    top.location="${basePath}/user/logout.action";
					    }else{
				           return false;
					    }
					});

				//�˳�����ϵͳ��ʹ��ajax��ʽ֪ͨ����ϵͳ�˳�ϵͳ
				function  logout_useframes(){
					//���������л�ϵͳ������
					$("span.dock-item[context!=''][visibility!=visible]").each(function(){
						var prefixPath = parent.getPrefixPath($(this));
						logoutURL=prefixPath+"/ajaxcheck/communications/logout.action?jsoncallback=?&t="+Math.random();
						$.ajaxSettings.async = false;
						$.getJSON(logoutURL,null,function call(result){
						});
					});
				}
				//----------------------�˳�ϵͳ��������---------------//
				
				//----------------�޸ķ��ʼ---------------
				/**
				 * �޸�ϵͳ����ʽ���
				 */
				 $('#skinStyle').click(function(){
					var url = "${framePath}/shinstyle.jsp?d="+Date();
					if(showDialog(url, "", 500, 300)){
						changeStyle();
						window.top.location="${basePath}/mgr/frame/mainframe.jsp";
					}
				});
					//֪ͨ����ϵͳ�޸ķ��
				function changeStyle(){
						
					var workFrameId=mainFrame.find("#mainwork frame[src!=''][visibility=visible]").attr('id');
					//������������δ��ʾ��ϵͳ,֪ͨ�������޸ķ��
					$("span.dock-item[context!="+workFrameId+"]").each(function(){
						var workFrame =$(this);
						var prefixPath = parent.getPrefixPath(workFrame);
						var relativePath = "/ajaxcheck/communications/changeStyle.action?jsoncallback=?";
						var urlstyle = prefixPath + relativePath;
						$.ajaxSettings.async = false;
						$.getJSON(urlstyle,null,function call(result){
							mainFrame.find("#mainwork frame[id="+workFrame.attr("context")+"]").each(function(){
								$(this).attr('src','');
							});
						});
					});
					//��ѯ��ǰ��ʾ��ҳ�� frame src!=""&&visibility=visible��ҳ��֪ͨ���������޸ķ�񲢰�src���¼���
					mainFrame.find("#mainwork frame[src!=''][visibility=visible]").each(function(){
						var workFrame =$(this);
						var prefixPath = parent.getPrefixPath($("span.dock-item[context="+workFrame.attr('id')+"]"));
						
						var relativePath = "/ajaxcheck/communications/changeStyle.action?jsoncallback=?";
						
						var urlstyle = prefixPath + relativePath;
						$.ajaxSettings.async = false;
				
						$.getJSON(urlstyle,null,function call(result){
							workFrame.attr('src',workFrame.attr('src'));
							frm.submit();
						});
					});
					//��������frame src!=""&&visibility!=visible��ҳ��֪ͨ���������޸ķ�񲢰�src�ÿ�
				 	/**mainFrame.find("#mainwork frame[src!=''][visibility!=visible]").each(function(){
						var workFrame =$(this);
						var prefixPath = parent.getPrefixPath($("a.dock-item[context="+workFrame.attr('id')+"]"));
						var relativePath = "/ajaxcheck/communications/changeStyle.action?jsoncallback=?";
						var urlstyle = prefixPath + relativePath;
						$.ajaxSettings.async = false;
						$.getJSON(urlstyle,null,function call(result){
							workFrame.attr('src','');
						});
					});
					*/
				}
				
                //-----------------�޸ķ�����----------------

                /**
                 * ���Ĭ��ϵͳչʾ
                 */
				function adddefaultframe(defaultframe){
					var context = defaultframe.attr("context");
					var frame = mainFrame.find("#mainwork frame[id="+context+"]");
					if(frame.size()<=0){
						defaultframe.click();
					}
				}

				//��ȡϵͳʱ��
				ajaxGetSysDate();
				//ִ��Ĭ��ϵͳ�ĵ���¼�
				adddefaultframe($("#defaultframe"));
			});
		//-->
		</script>
<style type="text/css">
a,img{border:0;}
.iphonebox{overflow:hidden;float:left;}
.iphonebox .promptumenu_window{margin:0px 0 0 0px;}
.promptumenu_window{top:3px;cursor:move;position:relative;float:right;}
.dock-container {
	top:18px;
	height: 55px;
	padding-left: 10%;

}
</style>
	</head>
	<body><form name="mForm" id="mFirm" method="post" target="_blank">
	</form>
		<div id="haeder">
			<form name=frm id=frm>
				<div class="logo"> 
					<div class="anniu_kuang" style="width: 250px">
					    <div style="display: block;height: 35px">
							<ul>
								<li style="width: 42px">
									<a href="#" id="skinStyle">
										<img src="<%=skinPath%>/image/frame/topframe/skinStyle.png" width="35px" height="35px" border="0">
									</a>
								</li>
								<li style="width: 42px">
									<a href="#" id="logout1" >
										<img src="<%=skinPath%>/image/frame/topframe/logout.png"  width="35px" height="35px"  border="0" >
									</a>
									<input type="hidden" id="logout" value="logout">
								</li>
								<li style="width: 42px">
									<a href="#" onClick="changePwd('${CurrentUser.userId }');">
										<img src="<%=skinPath%>/image/frame/topframe/pwd.png" width="35px" height="35px"  border="0" >
									</a>
								</li>
								 <c:forEach items="${modelContextMap}" var="entry"> 
								 	<c:if test="${(entry.key==29)}">
									<li style="width: 42px">
										<a href="#" onClick="goMonitor(29);">
											<img src="<%=skinPath%>/image/frame/topframe/monitor.png"  width="35px" height="35px"  border="0" >
										</a>
									</li>
									</c:if>
								</c:forEach>
							</ul>
							</div>
							<div style="display: block;height: 20px">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span id="userInfoSpan">����Ա(<c:if test="${fn:length(CurrentUser.userId)>=4}">${fn:substring(CurrentUser.userId,0,3) }...</c:if>
										<c:if test="${fn:length(CurrentUser.userId)<4}">${CurrentUser.userId }</c:if>)��<c:if test="${fn:length(CurrentUser.name)>=4}">${fn:substring(CurrentUser.name,0,3) }...</c:if>
										<c:if test="${fn:length(CurrentUser.name)<4}">${CurrentUser.name }</c:if></span>&nbsp;
										<font id="sysDate"></font>
						    </div>
					</div>
					<!--top dock -->
					<div class="dock" id="dock" align="right">
						<img id="next" class="anniu_kuangCopy" style="cursor: hand;display: none;" src="<%=skinPath%>/image/frame/topframe/navigation/syspic/ico_right.png" alt="��һҳ" />
						<div class="dock-container iphonebox" id="allsystem_div" align="left">
							 <c:set var="deffram" value="true"></c:set>
							 <c:forEach items="${modelContextMap}" var="entry"> 
							 <c:if test="${!(COMMONMODULEID eq entry.key)&&(entry.key!=29)}">
							 	<c:set var="relativePath" value="/mgr/frame/mainframe_nohead.jsp"/>
							 	<c:if test="${not empty entry.value.RELATIVEPATH}"><c:set var="relativePath" value="${entry.value.RELATIVEPATH}"/></c:if>
								 <span class="dock-item" style="cursor: hand;" prefixPath="${entry.value.SERVERPATH}" context="${entry.value.CONTEXTNAME}" relativePath="${relativePath}" moduleid="${entry.key}" hidefocus="true"
								 <c:if test="${!(deffram)}">id="${entry.key}"</c:if><c:if test="${deffram}">id='defaultframe'<c:set var="deffram" value="false"></c:set></c:if>>
									<!-- modify by xumiao 2014-8-6 start -->
									<img height="55" width="72" src="<%=skinPath%>/image/frame/topframe/navigation/syspic/${entry.value.SERVERPIC}" alt="${entry.value.shortName}" />
								    <!-- modify by xumiao 2014-8-6 end -->
								</span>
							</c:if>
							</c:forEach>
						</div>
						<img id="prev" class="anniu_kuangCopy" style="cursor: hand;display: none;" src="<%=skinPath%>/image/frame/topframe/navigation/syspic/ico_left.png" alt="��һҳ" />
					</div>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
	<%/******** modify by xumiao 2014-8-6 start********/ %>
	var leftWidth = 0.24;<%//����Ա����ռ�ݵĿ�� %>
	var rightWidth = 0.33;<%//�Ҳ��޸����롢�������˳���ťռ�ݵĿ�� %>
	var width = document.documentElement.scrollWidth * (1-leftWidth - rightWidth) - 29;<%//��ȡ��ǰ��������ȥ�����ҿ�Ⱥ�ʣ��Ŀ�� %>
	var icosize = $(".dock-item").size();<%//ϵͳ����%>
	var picwith = 78;
	<%/********* modify by xumiao 2014-8-6 end *********/ %>
	var number = parseInt(width/picwith);<%//��ȡ����չʾϵͳ���� %>
	if(number<icosize){<%//���ͷ��������������ͼ�꣬��ʹ��ͼ��֮����ּ�϶�����ҿ��Ի���%>
		$("#allsystem_div").attr("class","dock-container iphonebox");
		$("#allsystem_div").attr("align","left");
		$("#prev").css("display","block");
		$("#next").css("display","block");
		if(number<1){
			number=1;<%//���һ��ϵͳ��չʾ������������Ϊչʾһ��%>
		}
		var s1 = {
			width:number*picwith,   //�Զ���������
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
		
	}else{
		$("#allsystem_div").attr("class","dock-container");
		$("#allsystem_div").attr("align","right");
	}
	</script>
</html>

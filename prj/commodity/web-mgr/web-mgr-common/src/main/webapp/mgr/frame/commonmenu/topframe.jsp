<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	request.setAttribute("modelContextMap",Global.modelContextMap);
	request.setAttribute("COMMONMODULEID",Global.COMMONMODULEID);
%>
<html>
	<head>
		<title>��ܼ�topҳ��</title>
		<link href="${skinPath}/css/navigation/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script type="text/javascript" src="${publicPath}/js/interface.js"></script>
		<script type="text/javascript" src="${publicPath}/js/map.js"></script>
		<script language="javascript">
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

			//ʱ���
			var timediffer=0;
			//ѭ��ִ��ʱ��ͬ����ʱ��
			var ajaxtime = 0;
			/**
			 * ajax ��ȡ��ǰ���ݿ�ʱ��
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
			 * ������ʱ������ 1 ��
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
				//��ȡϵͳʱ��
				ajaxGetSysDate();
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
					$("a.dock-item[context!=''][visibility!=visible]").each(function(){
						var prefixPath = getPrefixPath($(this));
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
					}
				});
				//֪ͨ����ϵͳ�޸ķ��
				function changeStyle(){
					var workFrameId=mainFrame.find("#mainwork frame[src!=''][visibility=visible]").attr('id');
					//������������δ��ʾ��ϵͳ,֪ͨ�������޸ķ��
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
					//��ѯ��ǰ��ʾ��ҳ�� frame src!=""&&visibility=visible��ҳ��֪ͨ���������޸ķ�񲢰�src���¼���
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
				
                //-----------------�޸ķ�����----------------

			});
			/**
			 * ��ȡ����ǰ׺
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
										����Ա���룺${CurrentUser.userId }
									</td>
								</tr>
								<tr>
									<td>
										����Ա���ƣ�${CurrentUser.name }
									</td>
								</tr>
								<tr>
									<td>
										ϵͳʱ��Ϊ��<font id="sysDate"></font>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="dock-container" id="allsystem_div" style="display: none;">
							 <!-- id='defaultframe'������ĸ���ǩ�����ĸ�ϵͳΪĬ�ϴ�ϵͳ -->
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

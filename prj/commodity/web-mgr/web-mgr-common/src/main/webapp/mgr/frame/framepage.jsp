<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>���׹���ϵͳ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script>
			var lastToModuleID = '<%=Global.getSelfModuleID()%>';
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
			
			/**
			 * ��·����β���ϵ�½�û�sessionID
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
			 * ÿ���л�ϵͳ��ʱ�����һ�� ajax ��������ֹ��ǰ�л���ϵͳ���ڳ�ʱ�䲻�ö���ʱ�ˡ�
			 */
			function sessionGetUser(obj){
				//��ȡ��Ŀ��·��
				var prefixPath = getPrefixPath(obj);
				//��ȡ ajax ����·��
				var src = prefixPath + "/ajaxcheck/communications/sessionGetUser.action?jsoncallback=?";
				//��·������ sessionID
				src = srcAddSessionID(src,obj.attr('moduleid'));
				//��ȡ ajax ԭ�����ύ��ʽ
				var oldAjaxAsync = $.ajaxSettings.async;
				//���� ajax ͬ��
				$.ajaxSettings.async = false;
				//ִ�� ajax
				$.getJSON(src,null,function call(result){
					
				});
				//���û� ajax ԭ�����ύ��ʽ
				$.ajaxSettings.async = oldAjaxAsync;
			}
			/**
			 * ��ȡ�� frame �� src ·��
			 */
			function getSRC(obj){
				//��ȡ��Ŀ��·��
				var prefixPath = getPrefixPath(obj);
				//��ȡҪ��ҳ���λ��
				var relativePath = obj.attr('relativePath')!=null?obj.attr('relativePath'):"";
				//�ϲ��� src ·��
				var src = prefixPath+relativePath;
				//�� src ���� sessionID ����
				src = srcAddSessionID(src,obj.attr('moduleid'));
				//����·��
				return src;
			}
			//�����������frame
			var mainFrame;
			//��ȡ�����������frame
			function getMainFrame(){
				if(!mainFrame){
					mainFrame = $(document);
				}
				return mainFrame;
			}
			
			// ��ǰѡ�еĵڼ���
			var cselect = 0;
			// ֮ǰѡ�еĵڼ���
			var pselect = 3;
			// �����л��ڼ���
			var uselect = 0;
			// ��ʼֵ
			var bb = 0;
			// ����ֵ
			var fb = 20;
			//�л�ʱ�䵥λ����
			var time = 100;
			//frame����
			var countFrame = 0;
			//��ǰѡ�е�frame
			var curSelectedFrame;
			
			/*
				����:�л�ϵͳʱ����
				���� :a�Ķ���obj
			*/
			function clickChange(obj,allobj){
				//�õ�context
				var context = obj.attr('context');
				//mainwork�µ�frame���ƺ�context��ͬ ,����context ���� curSelectedFrame
				var curSelectedFrame = mainFrame.find("#mainwork frame[id="+context+"]");//���ҵ���Ҫ��frame
                 //�ı�֮ǰѡ�е��Ǹ�frame;
				var preCurSelectedFrame = mainFrame.find("#mainwork frame[visibility='visible']");
				var affirmrst = affirm();//�趨��ʱ���ܵ��
				if(affirmrst){
					allobj.css("background-image","");
					obj.css("background-image","url(${skinPath}/image/frame/topframe/navigation/syspic/backgroup.png)");
					// �����ǰ��ʾ��frame���ǵ�ǰѡ��curSelectedFrame��ֱ�ӷ���
					if(mainFrame.find("#mainwork frame[id="+context+"][visibility='visible']").attr('id')==context){
						clearSubmitCount();
						return ;
					}else if(curSelectedFrame.size()==0){
						 //�ҵ���ʾ��frame���ص� ȥ������
						mainFrame.find("#mainwork frame[visibility='visible']").each(function(){
							$(this).attr('visibility','hidden');
						});
						var countFrame =mainFrame.find("#mainwork frame").size();
						var hundred ="100%";
						var zero ="0";
						var cols="";
						//������ڻ�����Ĭ��frame����Ҫɾ��Ĭ��frame
						for(var i=getcommon_mgrsize();i<countFrame;i++){
							cols=cols+zero+",";
						}
						cols+=hundred;
						var childFrame = document.createElement("frame");
						 childFrame.id = context ;
					 	//���ǰ׺�Ѿ�������ֱ��ʹ�÷����path.jsp�л�ȡbasepath��Ϊǰ׺ ��ʽ�������� http://172.16.1.115:8080/
					 	$(childFrame).attr("visibility","visible");
						 mainFrame.find('#mainwork').append($(childFrame));
						 var src = getSRC(obj);
						childFrame.src= src;
						//����frame����
						if (!/*@cc_on!@*/0) {//��IE�����
							childFrame.onload = function(){
								removecommon_mgr();
								mainFrame.find('#mainwork').attr("cols",cols);
								clearSubmitCount();
							};
						} else {//IE�����
							childFrame.onreadystatechange = function(){
								removecommon_mgr();
								mainFrame.find('#mainwork').attr("cols",cols);
								clearSubmitCount();
							};
						}
					}else{
						sessionGetUser(obj);
						//�ҵ���ʾ��frame���ص� ȥ������
						mainFrame.find("#mainwork frame[visibility='visible']").each(function(){
							$(this).attr('visibility','hidden');
						});
						
						//�ѵ�ǰѡ�е�frame����Ϊ�ɼ�״̬
						curSelectedFrame.attr('visibility','visible');
						//���srcΪ��,��srcָ���л�ϵͳ��ť�����õ�·��
						if(curSelectedFrame.attr("src")==""){
							//���ǰ׺�Ѿ�������ֱ��ʹ�÷����path.jsp�л�ȡbasepath��Ϊǰ׺ ��ʽ�������� http://172.16.1.115:8080/
							var src = getSRC(obj);
							curSelectedFrame.attr("src",src);
						}
						
						//�޸�mainwork��rows
						pselect = preCurSelectedFrame.index();//֮ǰѡ�е�ϵͳ��λ��
						cselect = curSelectedFrame.index();//��ǰѡ�е�ϵͳ��λ��
						bb = 0;//��ʼ����ʼֵΪ0
						uselect = 0;//��ʼ����ʼ���л�ϵͳʹ���е�ϵͳ��λ��
						if (pselect < cselect) {//֮ǰѡ�е�С������ѡ�е�
							colsAdd();
						} else {//֮ǰѡ�еĴ�������ѡ�е�
							colsReduce();
						}
						clearSubmitCount();
					}
				}
			}
			//ɾ��ָ��ID��frame
			function removecommon_mgr(){
				var id="common_mgr";
				mainFrame.find("#mainwork frame[id="+id+"]").remove();
			}
			function getcommon_mgrsize(){
				var id="common_mgr";
				return mainFrame.find("#mainwork frame[id="+id+"]").size();
			}
			//------------------�޸�mainwork��cols-----��ʼ---------------
			//��ѡ���еĴ��ڵ�ǰ��ʾ��,���󻬶�
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
			//��ѡ���е�С�ڵ�ǰ��ʾ��,���һ���
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
			//------------------�޸�mainwork��cols-----����---------------
			
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
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>
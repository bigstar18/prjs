<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�������</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<SCRIPT type="text/javascript">
		<!-- 
			//�����Ϣ��ת		
			function addF(){
			
				if(confirm("��ȷ��Ҫ������")){
					//�����ϢURL
					var furl = document.getElementById("add").action;
					//ȫ URL ·��
					frm.action = "${basePath}"+furl;
					frm.submit();
				}
			}	
			
	
			function getStatus(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var url = "${basePath}/ajaxcheck/tradeEnd/getStatusJson.action?d="+new Date().getTime();
				$.ajaxSettings.async = false;
				$.getJSON(url,null,function call(result){
					var showStatus="";
					if (result==2) {
						showStatus = "����״̬��ִ����";
					}else if (result == 3||result== 10) {
						showStatus = "����״̬���������";
					}else{
						showStatus = "����״̬��δִ��";
					}
					if (result== 1) {//ֻ�б���״̬���������׽���
						document.getElementById("add").disabled = false;
					}else{
						document.getElementById("add").disabled =true;
					}
					document.getElementById("balanceStatus").innerHTML = showStatus;
				});
				$.ajaxSettings.async = oldAjaxAsync;
				setTimeout("getStatus()",1000);
			}
		//-->
		</SCRIPT>
	</head>
	<body onload="getStatus();">
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="middle">
					<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="topFrame">
					<rightButton:rightButton name="�ֹ�����" onclick="addF();" className="anniu_btn" action="/timebargain/tradeManager/tradeEnd.action" id="add"></rightButton:rightButton>
					&nbsp;&nbsp;<font style="font-size:13px;" id="balanceStatus">����״̬��δִ��</font>
					</form>
				</td>
			</tr>
		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>
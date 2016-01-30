<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>财务结算</title>
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
			//添加信息跳转		
			function addF(){
			
				if(confirm("您确定要操作吗？")){
					//添加信息URL
					var furl = document.getElementById("add").action;
					//全 URL 路径
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
						showStatus = "结算状态：执行中";
					}else if (result == 3||result== 10) {
						showStatus = "结算状态：结算完成";
					}else{
						showStatus = "结算状态：未执行";
					}
					if (result== 1) {//只有闭市状态可以做交易结算
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
					<rightButton:rightButton name="手工结算" onclick="addF();" className="anniu_btn" action="/timebargain/tradeManager/tradeEnd.action" id="add"></rightButton:rightButton>
					&nbsp;&nbsp;<font style="font-size:13px;" id="balanceStatus">结算状态：未执行</font>
					</form>
				</td>
			</tr>
		</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>
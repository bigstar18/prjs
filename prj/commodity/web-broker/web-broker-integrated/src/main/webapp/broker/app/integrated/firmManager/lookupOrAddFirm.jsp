<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>lookupOrAddFirm</title>
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<SCRIPT type="text/javascript">
			$(function(){
				var url = "";
				if(confirm("�����̿�������ɹ����Ƿ�������룿")){
					url = "${basePath}/broker/firmManager/forwardAddFirm.action";
				}else{
					url = "${basePath}/broker/firmManager/listFirmApply.action";

				}
				location.href = url;
			});
		</SCRIPT>
	</head>
	<body>

	</body>
</html>
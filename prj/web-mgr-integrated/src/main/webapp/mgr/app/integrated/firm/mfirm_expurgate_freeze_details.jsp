<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	
<head>
		<title>交易商详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden" onload="getHidden()">
		<form id="frm" method="post"
			action="${basePath}/trade/mfirm/updateMfirm.action"
			targetType="hidden">
			<div class="div_cx">
				<table border="0" width="95%" align="center">
				
					<tr>
						<td>
							<%@include file="mfirmexpurgatefreezecommontable.jsp"%>
						</td>
					</tr>
					<!-- 当交易商类型为'个人的时候',隐藏地域编号、行业编号、组织结构代码和法人代表 -->
							<script type="text/javascript">
								function getHidden(){
									var type=document.getElementById("type").value;
									var content=document.getElementById("content");
									if(type ==3){
										content.style.display = "none";
									}else{
										content.style.display = "block";
									}
								}
							</script>
				</table>
			</div>
			<div class="div_cx">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<button class="btn_sec" onClick=window.close();>
								关闭
							</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>
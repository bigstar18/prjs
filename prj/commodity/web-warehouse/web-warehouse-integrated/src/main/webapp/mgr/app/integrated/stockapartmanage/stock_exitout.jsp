<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript"></script>
	<script
		src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<link rel="stylesheet"
		href="${skinPath }/css/autocomplete/jquery.autocomplete.css"
		type="text/css" />
<script type='text/javascript' src='${publicPath }/js/autocomplete/jquery.autocomplete.js'></script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>

	<script type="text/javascript">
	/*function checkStyle(deliverstyle){
		if(deliverstyle==0){
		//	$("#deliveryStatus").val="����";
			$("#company").hide();
			$("#logisticsOrder").hide();
		}else{
		//	$("#deliveryStatus").val="����";
			$("#company").style.display="none";
			$("#logisticsOrder").style.display="none";
		//	$("#company").val("");
		//	$("#logisticsOrder").val("");
			//document.getElementByName("company").value="-2";
		}
	}*/
	jQuery(document).ready(function() {

		$("#frm").validationEngine('attach');

		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("��ȷ��Ҫ������");
				if (vaild == true) {
					$("#frm").validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$("#frm").submit();
			}
		}
	}	)

	});
</script>
	<head>
		<title>�ֵ�����</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden" >
		<form id="frm" name="frm" action="${basePath }/stock/exitlist/stockOutReal.action" targetType="hidden" method="post">
			<div>
				<div class="div_cx">
					<table border="0" width="95%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">
										��ܰ��ʾ :�ֵ�����
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													�ֵ���Ϣ
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<tr>
														<td align="right" width="15%">
															�ֵ��ţ�
														</td>
														<td width="20%">
															<input type="hidden" id="stockId" name="stockId"
																value="${stockId }" />
															${stockId}
														</td>
													</tr>
													<!-- ��ʾ�����ʽ -->
													<tr>
														<td align="right">
																�����ʽ��
														</td>
														<td ><c:if test="${deliverstyle == 0 }">
																����
																</c:if>
																<c:if test="${deliverstyle == 1 }">
																����
																</c:if>
														</td>
													</tr>
													<tr>
														<td align="right">
															<span class='required'>*</span>�����Կ��
														</td>
														<td width="20%">
															<input id="key" type="text" class="validate[required,maxSize[30],ajax[checkKey]] input_text"
																name="key" />
														</td>
													</tr>
													<tr>
														<td align="right">
															<span class='required'>*</span>����ˣ�
														</td>
														<td width="20%">
															<input type="text" class="validate[required] input_text" id="deliveryPerson"
																name="deliveryPerson" />
														</td>
													</tr>
													<c:if test="${deliverstyle eq 1 }">
													<tr id="company" style="dispaly:none">
														<td align="right">
															<span class='required'>*</span>������˾��
														</td>
														<td width="20%">
															<input type="text" class="validate[required,maxSize[32]] input_text" id="company"
																name="company" value=""/>
														</td>
													</tr>
													<tr id="logisticsOrder" style="dispaly:none">
														<td align="right">
															<span class='required'>*</span>��ݵ��ţ�
														</td>
														<td width="20%">
															<input type="text" class="validate[required,maxSize[18],custom[onlyLetterNumber]] input_text" id="logisticsOrder"
																name="logisticsOrder" value=""/>
														</td>
													</tr>
													</c:if>
											
												</table>
											</div>
										</td>
									</tr>
								</table>

							</td>
						</tr>

						<tr>
							<td align="right">
								<rightButton:rightButton name="����" onclick=""
									className="btn_sec" action="/stock/exitlist/stockOutReal.action"
									id="update"></rightButton:rightButton>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn_sec" onClick=
	window.close();
>
									�ر�
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>
		</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript"> 		
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			$("#update").click(function(){
				//��֤��Ϣ
				if ($("#frm").validationEngine('validate')) {
					loginConsigner();
				}
			});
			
			//�ύ�¼�
			function loginConsigner() {
				var vaild = window.confirm("��ȷ��Ҫ�ύ��");
				if(vaild){	
					//�����ϢURL
					var updateUrl = $("#update").attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+updateUrl;
					$("#frm").attr("action",url);
					frm.submit();
				}
			}
		})
		
		function myReset(){
			frm.oldpwd.value="";
			frm.newpwd.value="";
			frm.confirmpwd.value="";
		}
		</script>
	</head>
	
	
	<body>
	<form id="frm" method="post" enctype="multipart/form-data" action="" target="ListFrame">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸Ĵ�Ϊί��Ա����
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
												�޸Ĵ�Ϊί��Ա����
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														��Ϊί��Ա���룺
													</td>
													<td>
														${consignerID }<input type="hidden" id="consignerId"  name="consignerId"  value="${consignerID }">
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														ԭ���
													</td>
													<td>
														<input type="password" id="oldpwd" name="oldpwd"  maxlength="64" style="ime-mode:disabled;width:105"
															class="validate[required,custom[password],maxSize[20]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														�¿��
													</td>
													<td>
														<input type="password" id="newpwd" name="newpwd"  maxlength="64" style="ime-mode:disabled;width:105"
															class="validate[required,custom[password],maxSize[20]]"/>
													</td>
												</tr>
												 <tr> 
											      <td align="right">
											      		<span class="required">*</span>
											      		ȷ���¿��
											      </td> 
											      <td>
											      		<input type="password" id="confirmpwd" name="confirmpwd"  maxlength="64" style="ime-mode:disabled;width:105"
															class="validate[required,equals[newpwd]]"/>
											      </td>
											    </tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
									<div>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
											<tr>
												<td align="center">
													<rightButton:rightButton name="ȷ��" onclick="" className="btn_sec" action="/timebargain/authorize/updatePassword.action" id="update"></rightButton:rightButton>
													&nbsp;&nbsp;
													<button class="btn_sec" onClick="myReset();" id="reset">����</button>
												</td>
											</tr>
										</table>
									</div>
									
									</td>
								
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
	</form>
	</body>
	
	
	
</html>

<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>
		��Ϊί��Ա��¼
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
		function window_onload()
		{
		  document.getElementById("traderID").focus();
		}
		
		// after save success,close
		function closewindow()
		{
		    window.dialogArguments.chkOk();
		    window.close();
		}
		
		
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			$("#login").click(function(){
				//��֤��Ϣ
				if ($("#frm").validationEngine('validate')) {
					loginConsigner();
				}
			});
			
			//�ύ�¼�
			function loginConsigner() {
				var vaild = confirm("��ȷ��Ҫ������");
				if(vaild){
					//�����ϢURL
					var loginUrl = $("#login").attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+loginUrl;
					$("#frm").attr("action",url);
					frm.submit();
				}
			}
		});
		</script>
	</head>
	<body onload="window_onload();">
	<form id="frm" method="post" enctype="multipart/form-data" action="${basePath}/mgr/app/timebargain/authorize/loginConsigner.action" target="HiddFrame">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :��Ϊί��Ա��¼
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
												��Ϊί��Ա��¼
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														��Ϊί��Ա���룺
													</td>
													<td>
														<input type="text" id="traderID" name="traderID"  maxlength="64" style="width:150;height:20"
															class="validate[required,maxSize[10]]"/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														��Ϊί��Ա���
													</td>
													<td>
														<input type="password" id="password" name="password"  maxlength="64" style="width:150;height:20"
															class="validate[required,custom[password]]"/>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
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
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="ȷ��" onclick="" className="btn_sec" action="/timebargain/authorize/loginConsigner.action" id="login"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�˳�</button>
						</td>
					</tr>
				</table>
			</div>
	</form>
	</body>
	</html>

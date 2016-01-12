<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>Ĭ��Ӷ������</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		<!-- 

		    $(document).ready(function() {
		    	jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});
				$("#back").click(function(){
					//�����ϢURL
					var updateDemoUrl = $(this).attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+updateDemoUrl;
					document.location.href = url;
				});
			});

			function changeManner(id){
				var $sel = $("#sel");
				$sel.html("");
				if (id == "1") {
					var $list_month = $sel.data("list_month");
					$list_month.clone().appendTo($sel);
				} else if (id == "2") {
					var $list_week = $sel.data("list_week");
					$list_week.clone().appendTo($sel);
				}
			}

			function vali_FirstPayRate(){
				var firstPayRate = parseFloat($("#firstPayRate").val());
						
						if(firstPayRate > 100){
							alert("������ɱ������ܴ���100%");
							$("#secondPayRate").attr("value",0);
							$("#firstPayRate").attr("value",0);
						}
						else if(firstPayRate != 0){
						$("#secondPayRate").attr("value",100-firstPayRate);
						}
				}
			
				function vali_SecondPayRate(){
				var firstPayRate = parseFloat($("#firstPayRate").val());
				var secondPayRate = parseFloat($("#secondPayRate").val());
						if(firstPayRate + secondPayRate > 100){
							alert("������ɱ������ܴ���100%");
							$("#secondPayRate").attr("value",0);
							$("#firstPayRate").attr("value",0);		
						}else if(firstPayRate + secondPayRate < 100 && firstPayRate != 0 ){
							alert("������ɱ�������С��100%");
							$("#secondPayRate").attr("value",0);
							$("#firstPayRate").attr("value",0);	
						}else if(firstPayRate == 0){
							$("#firstPayRate").attr("value",100-secondPayRate);	
						}
				}

		//-->
		</SCRIPT>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸�Ĭ��Ӷ�����
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="800" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												Ĭ��Ӷ���������
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														
														ģ�飺
													</td>
													<td>
														Ĭ��
														<input type="hidden" name="entity.moduleId" value="${entity.moduleId }"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														
														Ӷ�����ͣ�
													</td>
													<td>
														${config_rewardTypeMap[entity.rewardType] }
														<input type="hidden" name="entity.rewardType" value="${entity.rewardType }"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														
														������Ӷ�������
													</td>
													<td>
														<input id="rewardRate" name="entity.rewardRateTemp" maxlength="11" value="${entity.rewardRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>	
													<td align="right">
														
														����׸�������
													</td>
													<td>
													    <input id="firstPayRate" name="entity.firstPayRateTemp" maxlength="11" value="${entity.firstPayRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" onblur="vali_FirstPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														
														���β�������
													</td>
													<td>
														<input id="secondPayRate" name="entity.secondPayRateTemp" maxlength="11" value="${entity.secondPayRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" onblur="vali_SecondPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
													
												</tr>
												
												<tr>
												    <input type="hidden" name="entity.brokerId" value="-1"/>
												    <input type="hidden" name="entity.commodityId" value="-1"/>
													<td colspan="2" align="center">                                                  
														<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/config/default/updateDefaultParam.action" id="update"></rightButton:rightButton>
														&nbsp;&nbsp;
														<rightButton:rightButton name="����" onclick="" className="btn_sec" action="/config/default/defaultParamList.action" id="back"></rightButton:rightButton>
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
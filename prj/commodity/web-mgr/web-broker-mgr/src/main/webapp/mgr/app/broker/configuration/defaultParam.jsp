<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>默认佣金设置</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		<!-- 

		    $(document).ready(function() {
		    	jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});
				$("#back").click(function(){
					//添加信息URL
					var updateDemoUrl = $(this).attr("action");
					//全 URL 路径
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
							alert("两次提成比例不能大于100%");
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
							alert("两次提成比例不能大于100%");
							$("#secondPayRate").attr("value",0);
							$("#firstPayRate").attr("value",0);		
						}else if(firstPayRate + secondPayRate < 100 && firstPayRate != 0 ){
							alert("两次提成比例不能小于100%");
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
									温馨提示 :修改默认佣金参数
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
												默认佣金参数设置
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														
														模块：
													</td>
													<td>
														默认
														<input type="hidden" name="entity.moduleId" value="${entity.moduleId }"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														
														佣金类型：
													</td>
													<td>
														${config_rewardTypeMap[entity.rewardType] }
														<input type="hidden" name="entity.rewardType" value="${entity.rewardType }"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														
														手续费佣金比例：
													</td>
													<td>
														<input id="rewardRate" name="entity.rewardRateTemp" maxlength="11" value="${entity.rewardRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>	
													<td align="right">
														
														提成首付比例：
													</td>
													<td>
													    <input id="firstPayRate" name="entity.firstPayRateTemp" maxlength="11" value="${entity.firstPayRateTemp }"
															class="validate[required,custom[double]] input_text" style="width:120" onblur="vali_FirstPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														
														提成尾款比例：
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
														<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/config/default/updateDefaultParam.action" id="update"></rightButton:rightButton>
														&nbsp;&nbsp;
														<rightButton:rightButton name="返回" onclick="" className="btn_sec" action="/config/default/defaultParamList.action" id="back"></rightButton:rightButton>
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
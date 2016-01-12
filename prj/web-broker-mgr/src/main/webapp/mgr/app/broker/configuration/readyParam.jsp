<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>付佣金参数设置</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script src="${mgrPath }/app/broker/js/util.js" type="text/javascript" charset="GBK"></script>
		
		<SCRIPT type="text/javascript">
		
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

				var $list_month = $("<select id='payPeriodDate' name='entity.payPeriodDate' class='validate[required]' style='width:120'></select>");
				var $list_week = $("<select id='payPeriodDate' name='entity.payPeriodDate' class='validate[required]' style='width:120'></select>");
				//$list_month.append("<option value=''>请选择</option>");
				//$list_week.append("<option value=''>请选择</option>");
				
			    for (var i=1; i<31; i++) {
					$list_month.append("<option value='"+i+"'>"+i+"号</option>");
				}
				$list_week.append("<option value='1'>星期日</option>");
				$list_week.append("<option value='2'>星期一</option>");
				$list_week.append("<option value='3'>星期二</option>");
				$list_week.append("<option value='4'>星期三</option>");
				$list_week.append("<option value='5'>星期四</option>");
				$list_week.append("<option value='6'>星期五</option>");
				$list_week.append("<option value='7'>星期六</option>");

				$("#sel").data("list_month", $list_month);
				$("#sel").data("list_week", $list_week);
				
				changeManner("${entity.payPeriod}");
				$("#payPeriodDate").val("${entity.payPeriodDate}");
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

			function onChangePay(){

				if(frm.autoPay.value == "Y")
				 {		 
					 $("#payWeek").show();
					 $("#payDay").show();
					 
				
				 }
				else if(frm.autoPay.value == "N")
				 {		 
					$("#payWeek").hide();
					$("#payDay").hide();
				 }
						
			}

			function window_onload(){
				onChangePay();
			}
	
		</SCRIPT>
	</head>
	<body onload="window_onload()">
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改付佣金参数
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
												付佣金参数设置
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														是否自动付款：
													</td>
													<td>
														<select id="autoPay" name="entity.autoPay" class="validate[required]" style="width:120" onchange="onChangePay()">
															
									                          <option value="Y" <c:if test="${entity.autoPay=='Y'}">selected="selected"</c:if>>是</option>
										                      <option value="N" <c:if test="${entity.autoPay=='N'}">selected="selected"</c:if>>否</option>
														</select>
													</td>
													
													<td >
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr id="payWeek">
												    <td align="right">
												     <span class="required">*</span>
														付款周期：
													</td>
													<td>
													    <select id="payPeriod" name="entity.payPeriod" class="validate[required]" style="width:120" onchange="changeManner(this.value)">
													          <%-- <option value="">请选择</option>--%>
									                          <option value="1" <c:if test="${entity.payPeriod==1}">selected="selected"</c:if>>按月</option>
										                      <option value="2" <c:if test="${entity.payPeriod==2}">selected="selected"</c:if>>按周</option>
														</select>
													</td>
													<td >
														<div class="onfocus">不能为空！</div>
													</td>
												</tr>
												<tr id="payDay">
													<td align="right">
														<span class="required">*</span>
														付款周期日：
													</td>
													<td id="sel" >
														
													</td>
													<td >
														<div class="onfocus">不能为空！</div>
													</td>
				
												</tr>
												
												<tr>
													<td colspan="4" align="center">                                                  
														<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/config/ready/updateReadyParam.action" id="update"></rightButton:rightButton>
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